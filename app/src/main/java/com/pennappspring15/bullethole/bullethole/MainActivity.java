package com.pennappspring15.bullethole.bullethole;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.LinkedList;


public class MainActivity extends ActionBarActivity implements SensorEventListener {
    private final static int MAX_FRAME_SKIPS = 30;
    private final static int MAX_FPS = 10;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    LinkedList<Bullet> bulletlist;
    Spaceship ship;
    boolean running = true;
    private int max_height, max_width;
    Screen screen;
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bulletlist = new LinkedList<Bullet>();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        screen =  new Screen(this);
        setContentView(screen);
        max_height = screen.getHeight();
        max_width = screen.getWidth();
        screen.setBulletList(bulletlist);
        screen.setSpaceship(ship);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
                , SensorManager.SENSOR_DELAY_FASTEST);
        ship = screen.getShip();
        screen.setRunning(true);
    }

    @Override
    protected void onPause(){
        super.onPause();
        screen.setRunning(false);
    }

    @Override
    protected void onStop(){
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    public void run(){

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public boolean gameOver(LinkedList<Bullet>list, Spaceship ship) {
        for(Bullet bullets: list) {
            int coord[] = bullets.getLocation();
            if(ship.testCollision(coord)) return true;
        }
        return false;
    }

    float[] components;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event == null){
            return;
        }
        if(components == null){
            components = new float[3];
            components[0] =  event.values[0];
            components[1] =  event.values[1];
            components[2] =  event.values[2];
            return;
        }
        int[] velocity = new int[2];
        velocity[1] = (int) ((event.values[1] - components[1])* 30);
        velocity[0] = (int) ((components[0] - event.values[0]) * 30);
        screen.getShip().setVelocities(velocity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
