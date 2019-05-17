package com.example.skintestresultanalyzer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC1;

public class imageprocess extends Activity{
    String path;
    Point C1;
    Point C2;
    int radius;
    int diameter;
    String args;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.improcess);
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        path = extra.getString("path");
        Log.d("CREATION1", "STRING IS " +  path);
    }
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if(status == LoaderCallbackInterface.SUCCESS){
                new HoughCirclesRun().run(path);
            }else {
                super.onManagerConnected(status);
            }
        }
    };

    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
    }

    class HoughCirclesRun{
        public void run(String args){
            String file = args;
            Log.d("IMAGE ", "FILE is " + file);
            Mat src = Imgcodecs.imread(file, Imgcodecs.IMREAD_COLOR);
            Log.d("IMAGE", "SRC IS " + src.empty() + " and " + src.type());
            Mat gray = new Mat();
            Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.medianBlur(gray, gray, 5);
            //Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
            Mat circles = new Mat();
            gray.convertTo(gray, CV_8UC1);
            Log.d("IMAGE","SRC type is " + gray.type());
            Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 2, 100, 150, 500);
//            if(circles.cols() > 0){
//                for(int x=0; x<Math.min(circles.cols(), 5); x++){
//                    double circleVec[] = circles.get(0,x);
//                    if(circleVec == null){
//                        break;
//                    }
//                    Point center = new Point((int) circleVec[0], (int)circleVec[1]);
//                    int radius = (int) circleVec[2];
//                    Imgproc.circle(gray, center, 3, new Scalar(100,255,255), 5);
//                    Imgproc.circle(gray, center, radius, new Scalar(255,255, 255), 2);
//                }
//            }
            for(int i = 0; i <circles.cols(); i++){
                double[] c = circles.get(0,i);
                Point center = new Point(Math.round((c[0])), Math.round(c[1]));
                Imgproc.circle(gray,center,1,new Scalar(0,100,100), 3, 8,0);
                radius = (int) Math.round(c[2]);
                diameter = 2*radius;
                Log.d("LENGTH", "radius is " + radius);
                Imgproc.circle(gray,center, radius, new Scalar(255,0,255), 3, 8, 0);
                if(i==0){
                    C1 = center;
                }else{
                    C2 = center;
                }
            }
            Bitmap bm = Bitmap.createBitmap(gray.cols(), gray.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(gray, bm);
            ImageView imv = findViewById(R.id.imgprocessed);
            imv.setImageBitmap(bm);
        }
    }
}
