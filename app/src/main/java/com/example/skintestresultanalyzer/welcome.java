package com.example.skintestresultanalyzer;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class welcome extends AppCompatActivity {
    Button cameraBtn;
    String filelocate;
    ImageView imageView;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            Bitmap bitmap = BitmapFactory.decodeFile(filelocate);
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        cameraBtn = findViewById(R.id.cameraBtn);
        imageView = findViewById(R.id.image);
        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File SkinTest = null;
                SkinTest = cPhotoFile();
                if(SkinTest != null) {
                    filelocate = SkinTest.getAbsolutePath();
                    Uri imageuri = FileProvider.getUriForFile(welcome.this, "com.example.skintestresultanalyzer.fileprovider", SkinTest);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    private File cPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storage = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".png", storage);
        }catch(IOException e){
            Log.d("mylog", "Excep : " + e.toString());
        }
        return image;
    }
}
