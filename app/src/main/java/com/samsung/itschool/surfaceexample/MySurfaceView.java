package com.samsung.itschool.surfaceexample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap image;
    Paint paint;
    float iX, iY, tX = 0, tY = 0;
    float dx = 0, dy = 0;
    Resources res;
    MyThread myThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        res = getResources();
        image = BitmapFactory.decodeResource(res, R.drawable.frankenshtain);
        iX = 0;
        iY = 0;
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(5);
        setAlpha(0);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
         myThread = new MyThread(getHolder(), this);
         myThread.setRunning(true);
         myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        myThread.setRunning(false);
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(image, iX, iY, paint);
        canvas.drawLine(iX, iY, tX, tY, paint);
        if(tx != 0)
            delta();
        iX += dx;
        iY += dy;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            tX = event.getX();
            tY = event.getY();

        }
        return true;
    }
    //расчет смещения картинки по x и y
    void delta(){
        double ro = Math.sqrt(Math.pow(tX-iX, 2)+Math.pow(tY-iY, 2));
        double k = 5;
        dx = (float) (k * (tX - iX)/ro);
        dy = (float) (k * (tY - iY)/ro);
    }
}
