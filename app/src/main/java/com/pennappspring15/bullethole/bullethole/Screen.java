package com.pennappspring15.bullethole.bullethole;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorListener;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.LinkedList;

public class Screen extends SurfaceView {
    private final static int MAX_FRAME_SKIPS = 30;
    private final static int MAX_FPS = 30;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    boolean running;
    LinkedList<Bullet> bulllist;
    Handler handler;
    Context context;
    Spaceship ship;
    final String TAG = "Screen";
    Bitmap background, bullet;
    SurfaceHolder holder;
    int count, increase = 5;

    public Screen(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.background);
        bullet = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bullet);
        holder = getHolder();
        running = true;

        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                setSpaceship(new Spaceship(holder.getSurfaceFrame().right / 2 - 43 ,
                        (holder.getSurfaceFrame().bottom - 90)/2, holder.getSurfaceFrame().right,
                        holder.getSurfaceFrame().bottom));
                bulllist = new LinkedList<Bullet>();
                Canvas c = holder.lockCanvas(null);
                draw(c);
                holder.unlockCanvasAndPost(c);

                handler = new Handler();
                count = 0;
                handler.postDelayed(startGame, 0);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        background = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.background);
        bullet = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bullet);
    }

    public void setBulletList(LinkedList<Bullet> list) {
        bulllist = list;
    }

    public void setSpaceship(Spaceship s) {
        ship = s;
    }


    Runnable startGame = new Runnable() {
        public void run() {
            long beginTime;
            long timeDiff;
            int sleepTime = 0;
            int framesSkipped;
//        while(true) {
            beginTime = System.currentTimeMillis();
            framesSkipped = 0;
            for (Bullet b : bulllist) {
                b.bulletUpdate(1);
            }
            ship.updateSS(1);
            Canvas c = getHolder().lockCanvas();
            draw(c);
            getHolder().unlockCanvasAndPost(c);
            for(Bullet b: bulllist){
                if (ship.testCollision(b.getLocation())){
                    running = false;
                    ((Activity) context).startActivity(new Intent(context, EndActivity.class));
                    ((Activity) context).finish();
                }
            }
            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int) (FRAME_PERIOD - timeDiff);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
            if (sleepTime < 0) {
                framesSkipped = Math.abs(sleepTime) / FRAME_PERIOD;
                framesSkipped = framesSkipped < MAX_FRAME_SKIPS ? framesSkipped : MAX_FRAME_SKIPS;
                for (Bullet b : bulllist) {
                    b.bulletUpdate(framesSkipped);
                }
                ship.updateSS(framesSkipped);
            }

            if (count > 40) {
                int[] array = new int[2];
                array[0] = (int) (Math.random() * getWidth());
                array[1] = (int)(Math.random() * getHeight());
                bulllist.addAll(newBullet(array));
                count -= 40;
                increase++;
            }
            count+= (int) (Math.sqrt(increase));

            if(running){
                handler.postDelayed(startGame, 40);
            }
        }

        //      }
    };

    @Override
    public void draw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        Paint paint = new Paint();
        Rect fullscreen = new Rect(0, 0, width, height);
        if (background == null) {
            background = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.background);
        }
        Rect source = new Rect(0, 0, background.getWidth(), background.getHeight());
        canvas.drawBitmap(background, source, fullscreen, paint);
        if (bullet == null) {
            bullet = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.bullet);
        }
        int bwidth = bullet.getWidth();
        int bheight = bullet.getHeight();
        if (bulllist == null) {
            bulllist = new LinkedList<Bullet>();
        }
        for (Bullet b : bulllist) {
            if (width >= bwidth + b.getLocation()[0] && b.getLocation()[0] >= 0
                    && b.getLocation()[1] + bheight < height && b.getLocation()[1] >= 0) {
                canvas.drawBitmap(bullet, b.getLocation()[0], b.getLocation()[1], new Paint());
            }
        }
        if (ship != null) {
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship)
                    , ship.getLocation()[0], ship.getLocation()[1], new Paint());
        }
    }


    public LinkedList<Bullet> newBullet(int[] array) {
        int x = array[0];
        int y = array[1];

        Bullet one = new Bullet(x, y + 30, 0, 30);
        Bullet two = new Bullet(x + 21, y + 21, 21, 21);
        Bullet three = new Bullet(x + 30, y, 30, 0);
        Bullet four = new Bullet(x + 21, y - 21, 21, -21);
        Bullet five = new Bullet(x, y - 30, 0, -30);
        Bullet six = new Bullet(x - 21, y - 21, -21, -21);
        Bullet seven = new Bullet(x - 30, y, -30, 0);
        Bullet eight = new Bullet(x - 21, y + 21, -21, 21);

        LinkedList<Bullet> bulletList = new LinkedList<Bullet>();

        bulletList.add(one);
        bulletList.add(two);
        bulletList.add(three);
        bulletList.add(four);
        bulletList.add(five);
        bulletList.add(six);
        bulletList.add(seven);
        bulletList.add(eight);

        return bulletList;
    }

    public Spaceship getShip(){return ship;}

    public void setRunning(boolean b) {running = b;}

    public boolean getRunning() {return running;}

}