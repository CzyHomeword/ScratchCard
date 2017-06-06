package com.example.yls.scratchcard;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ZoomActivity extends Activity {
    private static final int NONE= 0;
    private static final int DRAG= 1;
    private static final int ZOOM= 2;

    private  float oldDist;
    private  int mode=NONE;
    private Matrix matrix = new Matrix();
    private  Matrix saveMatrix = new Matrix();
    private PointF start = new PointF();
    private  PointF mid = new PointF();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        ImageView view = (ImageView) findViewById(R.id.image_view);
       view.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               ImageView view = (ImageView) v;
               switch(event.getAction()& MotionEvent.ACTION_MASK){
                   case MotionEvent.ACTION_DOWN:
                       saveMatrix.set(matrix);
                       start.set(event.getX(),event.getY());
                       mode = DRAG;
                       break;
                   case MotionEvent.ACTION_POINTER_UP:
                       mode = NONE;
                       break;
                   case MotionEvent.ACTION_POINTER_DOWN:
                       oldDist =spacing(event);
                       if(oldDist > 10f){
                           saveMatrix.set(matrix);
                           midPoint(mid,event);
                           mode = ZOOM;
                       }
                       break;
                   case MotionEvent.ACTION_MOVE:
                       if(mode==DRAG){
                           matrix.set(saveMatrix);
                           matrix.postTranslate(event.getX()-start.x,event.getY()-start.y);
                       }else if( mode == ZOOM) {
                           float newDist = spacing(event);
                           if(newDist >10f){
                               matrix.set(saveMatrix);
                               float scale= newDist/oldDist;
                               matrix.postScale(scale,scale,mid.x,mid.y);
                           }
                   }
                       break;
               }
               view.setImageMatrix(matrix);
               return true;
           }


    private float spacing(MotionEvent event) {
        float x =event.getX(0)-event.getX(1);
        float y =event.getY(0)-event.getY(1);
        return (float)Math.sqrt(x*x +y*y);
    }
           private void midPoint(PointF point, MotionEvent event) {
               float x =event.getX(0)+event.getX(1);
               float y =event.getY(0)+event.getY(1);
               point.set(x/2,y/2);
           }
       });
    }
}
