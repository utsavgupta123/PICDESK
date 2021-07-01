package com.example.myeditor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity
{
    private AdView mAdView;
    ImageView camera,imageView;
    ImageView gallery;
    CardView cardView;
    //AdView mAdView;
    int GET_IMAGE=23;
    int CAMERA_PIC=401;
    int RESULT_CODE=200;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         getSupportActionBar().hide();
         camera=findViewById(R.id.Camera);
         gallery=findViewById(R.id.Gallery);
         cardView=findViewById(R.id.cardView);
        // imageView=findViewById(R.id.imageView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
         // ads
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();

//        mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
//        mAdView.loadAd(adRequest);
//        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
         camera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {

                 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 try {
                     startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                 } catch (ActivityNotFoundException e) {

                 }

//                 if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                     Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                      startActivityForResult(cameraIntent, CAMERA_PIC);
//                 }
//                 else
//                 {
//                     ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 32);
//                 }

                 Toast.makeText(MainActivity.this, "Opening Camera", Toast.LENGTH_SHORT).show();
             }
         });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Opening Gallery!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,GET_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_IMAGE)
        {
            if (data.getData() != null)
            {
                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
                Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
                dsPhotoEditorIntent.setData(data.getData());
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "YOUR_OUTPUT_IMAGE_FOLDER");
                // Optional customization: hide some tools you don't need as below
                int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
                startActivityForResult(dsPhotoEditorIntent, RESULT_CODE);
            }
        }
        if(requestCode==RESULT_CODE)
        {
              Intent i=new Intent(MainActivity.this,EditDone.class);
              i.setData(data.getData());
              startActivity(i);
        }
//        if(requestCode == CAMERA_PIC && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            Uri uri = getImageUri(photo);
//            Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
//            dsPhotoEditorIntent.setData(uri);
//            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Pico");
//            int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
//            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
//            startActivityForResult(dsPhotoEditorIntent, RESULT_CODE);
//        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


//            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//            //imageView.setImageBitmap(imageBitmap);
//            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
//            String path=MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"val",null);
//            Uri uri=Uri.parse(path);

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            //knop.setVisibility(Button.VISIBLE);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);
           // File finalFile = new File(getRealPathFromURI(tempUri));

           // System.out.println(mImageCaptureUri);
            Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
            dsPhotoEditorIntent.setData(tempUri);
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Pico");
            int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
            startActivityForResult(dsPhotoEditorIntent, RESULT_CODE);
        }

//        if (requestCode == CAMERA_PIC )
//        {
//
//                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//                Uri uri=getImageUri( imageBitmap);
//                Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
//                dsPhotoEditorIntent.setData(uri);
//                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "YOUR_OUTPUT_IMAGE_FOLDER");
//                // Optional customization: hide some tools you don't need as below
//                int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
//                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
//                startActivityForResult(dsPhotoEditorIntent, RESULT_CODE);
//
//        }

    }

//    public Uri getImageUri(Bitmap bitmap) {
//        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrayOutputStream);
//        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
//        return Uri.parse(path);
//    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 10, 10,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
}