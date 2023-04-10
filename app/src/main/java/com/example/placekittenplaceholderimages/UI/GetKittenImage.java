package com.example.placekittenplaceholderimages.UI;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.placekittenplaceholderimages.Data.KittenDatabase;
import com.example.placekittenplaceholderimages.Data.KittenItem;
import com.example.placekittenplaceholderimages.Data.KittenItemDAO;
import com.example.placekittenplaceholderimages.R;
import com.example.placekittenplaceholderimages.databinding.ActivityGetKittenImageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class GetKittenImage extends AppCompatActivity {
    ActivityGetKittenImageBinding binding;

    SharedPreferences prefs;
    private Executor thread;

    private KittenItemDAO mDAO;
    String imagePath;
    Bitmap image;

    protected RequestQueue queue = null;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.get_kitten_image, menu);


        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId()) {
            case R.id.Item_1:

                Intent intent = new Intent(this, KittenImages.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                this.finish();


                break;


            case R.id.Item_2:


                AlertDialog.Builder builder = new AlertDialog.Builder(GetKittenImage.this);
                builder.setMessage("").
                        setTitle("How to use the WeatherStack?").
                        setNegativeButton("ok", (dialog, cl) -> {
                        }).create().show();


                break;


            case R.id.Item_3:


                thread.execute(()->{

                if (image!=null){
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                    String currentDatEndTime = sdf.format(new Date());

                    mDAO.insertKittenItem(new KittenItem(binding.width.getText().toString(),binding.height.getText().toString(),currentDatEndTime,imagePath));
                    Snackbar.make(binding.getRoot(), "image saved " , Snackbar.LENGTH_SHORT).show();
                }
                });



            break;
        }
        return true;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Get Kitten Image");

        binding = ActivityGetKittenImageBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.toolbar);

        queue = Volley.newRequestQueue(this);






        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
     binding.width.setText(prefs.getString("width", ""));
        binding.height.setText(prefs.getString("height", ""));

        thread = Executors.newSingleThreadExecutor();


        thread.execute(() ->
        {

            KittenDatabase db = Room.databaseBuilder(getApplicationContext(), KittenDatabase.class, "database-name").build();
            mDAO = db.cmDAO();


        });


        runOnUiThread(() -> {
            setContentView(binding.getRoot());
        });


        binding.btnGetImage.setOnClickListener(c -> {


            Toast.makeText(this, "please wait image", Toast.LENGTH_SHORT).show();

            String   width = binding.width.getText().toString();
            String  height = binding.height.getText().toString();


            String imageName = width + height + ".png";

            String imgUrl = "https://placekitten.com/" + width + "/" + height;

            imagePath = getFilesDir() + "/" + imageName;
            File file = new File(imagePath);
            if (file.exists()) {
                image = BitmapFactory.decodeFile(imagePath);
            } else {
                ImageRequest imgReq = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        image = bitmap;
                        FileOutputStream fOut = null;
                        try {
                            fOut = openFileOutput(imageName, Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                });


                queue.add(imgReq);


            }


            runOnUiThread(() -> {


                binding.kittenImage.setImageBitmap(image);


            });


        });
    }
}