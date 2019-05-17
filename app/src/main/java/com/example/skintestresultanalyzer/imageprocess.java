package com.example.skintestresultanalyzer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.Highgui;

public class imageprocess extends Activity{
    String path;
    String[] args;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.improcess);
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        path = extra.getString("path");
        Log.d("CREATION1", "STRING IS " +  path);

    }
}
