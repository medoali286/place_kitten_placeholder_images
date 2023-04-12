package com.example.placekittenplaceholderimages.UI;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.placekittenplaceholderimages.Data.Kitten;
import com.example.placekittenplaceholderimages.Data.KittenDatabase;
import com.example.placekittenplaceholderimages.Data.KittenDAO;
import com.example.placekittenplaceholderimages.Data.KittenViewModel;
import com.example.placekittenplaceholderimages.R;

import com.example.placekittenplaceholderimages.databinding.ActivityFavouriteKittenImagesBinding;
import com.example.placekittenplaceholderimages.databinding.KittenRowBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavouriteKittenImages extends AppCompatActivity {
ActivityFavouriteKittenImagesBinding binding;

    private RecyclerView.Adapter myAdapter;
    private ArrayList<Kitten> kittenItems;
    private KittenDAO mDAO;
    private Executor thread;

    private KittenViewModel kittenModel;
    int position;
boolean IsPressed;
  TextView  tv_date;






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
       getMenuInflater().inflate(R.menu.kitten_images, menu);



        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);



        switch( item.getItemId() ) {



            case R.id.Item_1:



                AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteKittenImages.this);
                builder.setMessage(R.string.kitten_about_message ).
                        setTitle(R.string.kitten_about_title).
                        setNegativeButton(R.string.ok, (dialog, cl) -> {
                        }).create().show();


                break;


            case R.id.Item_2:


                if (kittenItems.size() != 0 && IsPressed) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(FavouriteKittenImages.this);
                    builder1.setMessage(getString(R.string.do_you_want_to_delete_this_image_with_date) + tv_date.getText().toString()).
                            setTitle(R.string.question).
                            setNegativeButton(R.string.no, (dialog, cl) -> {
                            })
                            .setPositiveButton(R.string.yes, (dialog, cl) -> {

                                Kitten removedMessage = kittenItems.get(position);
                                thread.execute(() ->
                                {

                                    mDAO.deleteKittenItem(removedMessage);

                                });
                                runOnUiThread(() -> {
                                    kittenItems.remove(position);
                                    myAdapter.notifyItemRemoved(position);
                                });


                                Snackbar.make(binding.getRoot(), String.valueOf(R.string.you_deleted_image_with_date) + tv_date.getText(), Snackbar.LENGTH_SHORT)
                                        .setAction(R.string.undo, c -> {
                                            kittenItems.add(position, removedMessage);
                                            myAdapter.notifyItemInserted(position);
                                        }).show();


                                getSupportFragmentManager().popBackStack();


                                IsPressed = false;


                            }).create().show();


                }



                break;




        }
        return true;


    }









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.favourite_kitten_images));

      binding=ActivityFavouriteKittenImagesBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        kittenModel = new ViewModelProvider(this).get(KittenViewModel.class);

        kittenItems = kittenModel.kittenItems.getValue();

        if (kittenItems == null) {


            kittenModel.kittenItems.setValue(kittenItems = new ArrayList<Kitten>());


            thread = Executors.newSingleThreadExecutor();

            thread.execute(() ->
            {


               KittenDatabase db = Room.databaseBuilder(getApplicationContext(), KittenDatabase.class, "database-name").build();
                mDAO = db.cmDAO();


                kittenItems.addAll(mDAO.getAllKittenItem()); //Once you get the data from database


                runOnUiThread(() -> {

                    binding.recycleView.setAdapter(myAdapter);

                    setContentView(binding.getRoot());


                    if (kittenItems.size() - 1 > 0) {
                        binding.recycleView.smoothScrollToPosition(kittenItems.size() - 1);
                    }

                }); //You can then load the RecyclerView
            });

        }


        kittenModel.selectedKittenItem.observe(this, (newKittenItemValue) -> {




            KittenFragment kittenFragment = new KittenFragment(newKittenItemValue);


            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, kittenFragment).addToBackStack("").commit();


        });






        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


              KittenRowBinding kittenRowBinding = KittenRowBinding.inflate(getLayoutInflater(), parent, false);
                View root = kittenRowBinding.getRoot();
                return new MyRowHolder(root);


            }


            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {


                holder.width.setText( getString(R.string.width)+" : "+kittenItems.get(position).getWidth());
                holder.height.setText(getString(R.string.height)+" : "+kittenItems.get(position).getHeight());

                holder.date.setText(kittenItems.get(position).getDate());




                    holder.Image.setImageBitmap( BitmapFactory.decodeFile(kittenItems.get(position).getImagePath()));











                Log.i("s", "onBindViewHolder: "+kittenItems.get(position).getImagePath());
            }

            @Override
            public int getItemCount() {
                return kittenItems.size();
            }

            //function to check what kind of ChatMessage object is at row position
            // If the isSend is true, then return 0
            // so that the onCreateViewHolder checks the viewType and inflates a send_message layout.
            // If isSend is false, then getItemViewType returns 1 and onCreateViewHolder checks
            // if the viewType is 1 and inflates a receive_message layout.


            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });


        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));










    }


    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView width;
        public TextView height;
        public TextView date;
        public ImageView Image;

        public MyRowHolder(@NonNull View itemView) {

            super(itemView);

            itemView.setOnClickListener(clk -> {

              position= getAbsoluteAdapterPosition();
              Kitten selected = kittenItems.get(position);

                kittenModel.selectedKittenItem.postValue(selected);
                tv_date=date;
                IsPressed=true;



            });





            Image = itemView.findViewById(R.id.kitten_row_image);
            width= itemView.findViewById(R.id.kitten_row_width);
            height = itemView.findViewById(R.id.kitten_row_height);
            date = itemView.findViewById(R.id.kitten_row_date);


        }
    }


}