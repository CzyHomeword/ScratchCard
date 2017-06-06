package com.example.yls.scratchcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yls on 2017/6/5.
 */

public class ScrapeView extends View {
    private Bitmap bpBackground;
    private Bitmap bpForeground;
    private Canvas mCanvas;
    private Paint pathPaint;
    private Path path;
    private Paint contentPaint;
    private  String content="刮刮看";

    public ScrapeView(Context context){
        super(context);
        init();
    }
    public ScrapeView(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init() {
        pathPaint = new Paint();
        pathPaint.setAlpha(0);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(50);
        pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);

        path = new Path();

        bpBackground = BitmapFactory.decodeResource(getResources(),R.drawable.koala);
        bpForeground = Bitmap.createBitmap(bpBackground.getWidth(),bpBackground.getHeight(),Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bpForeground);
        contentPaint= new Paint();
        contentPaint.setColor(Color.WHITE);
        contentPaint.setTextSize(100);
        contentPaint.setStrokeWidth(20);

        mCanvas.drawColor(Color.GRAY);
        mCanvas.drawText(content,mCanvas.getWidth()/8,mCanvas.getHeight()/2,contentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(),event.getY());
                break;
        }
        mCanvas.drawPath(path,pathPaint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bpBackground,0,0,null);
        canvas.drawBitmap(bpForeground,0,0,null);
    }
}
