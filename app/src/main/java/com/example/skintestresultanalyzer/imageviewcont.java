package com.example.skintestresultanalyzer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class imageviewcont extends Activity {
    ImageView imageView;
    String path;
    Button nextbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.imagev);
        nextbtn = findViewById(R.id.nextbtn);
        imageView = findViewById(R.id.image);
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        Bundle extra = getIntent().getExtras();
        path = extra.getString("path");
        Bitmap b = BitmapFactory.decodeFile(path);
        Log.d("CREATION", "path is" + path);

        b = rotateImage(b);

        imageView.setImageBitmap(b);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(imageviewcont.this, imageprocess.class);
                intent.putExtra("path",path);
                startActivity(intent);
            }
        });

    }

    public static Bitmap rotateImage(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
