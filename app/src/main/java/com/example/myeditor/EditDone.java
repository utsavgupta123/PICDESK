package com.example.myeditor;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;




public class EditDone extends AppCompatActivity {

    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_done);
        image=findViewById(R.id.imageView2);
         getSupportActionBar().hide();
//        Uri IMAGE_URI = getIntent().getData();
//        InputStream image_stream = null;
//        try {
//            image_stream = getContentResolver().openInputStream(IMAGE_URI);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap= BitmapFactory.decodeStream(image_stream );
//        image.setImageBitmap(bitmap);




        image.setImageURI(getIntent().getData());
        


        Toast.makeText(this, "IMAGE EDITED!!!", Toast.LENGTH_SHORT).show();
    }
}