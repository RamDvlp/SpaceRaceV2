package com.example.meteoriterace;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Sensors {


    public interface CallBack_ScreenTilt {
        /**
         *
         * @param y - angular velocity of axis y.
         *          right - positive; left - negative;
         *
         *
         */
        void leftRightTilt(float y);

        /**
         *
         * @param y - the shift in gravity on y axis.
         *      front/outward negative; back/inward positive
         */
        void frontTilt(float y);
    }


    private SensorManager sensorManager;
    private Sensor sensorGyro;
    private Sensor sensorAcc;

    // Gyroscope measures the amount of change of movement(angel shift) on specific axis.
    // in other words shows if there was movement and how strong.
    // In very simple words shows angular velocity.
    // x - front (negative value) outwards of user. back (positive) toward user
    // y - right (+), left (-)
    // z - angle shift upon "table axis". Something like from \ to / by arial view on the phone on table.

    // to use front tilt properly should consider accelerometer. but for rocket shift - thats the thing.//

    // Indeed, accelerometer is good for that. The value is the actual amount of deviation from the axis.
    // You can say that it shows the representation of the angle (and not the angular acceleration) in which
    // the gravity works on the device on each axis.
    // x - left positive, right negative
    // y - front/outward negative, back/inward positive
    // z - just shows gravity, if screen facing the floor, the value is negative.

    //gyro is failed attempt. for any gameplay usability need to use accelerometer for both.

    private int sensorTypeGyro = Sensor.TYPE_GYROSCOPE;
    private int sensorTypeAcc = Sensor.TYPE_ACCELEROMETER;
    private CallBack_ScreenTilt callBack_screenTilt;

    public Sensors(Context context, CallBack_ScreenTilt callBack_screenTilt) {
        this.callBack_screenTilt = callBack_screenTilt;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorGyro = sensorManager.getDefaultSensor(sensorTypeGyro);
        sensorAcc = sensorManager.getDefaultSensor(sensorTypeAcc);

        if (sensorGyro == null || sensorAcc == null) {
            Toast.makeText(context, "No Sensor!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Maximum Range Gyro: " + String.valueOf(sensorGyro.getMaximumRange()) + "\n" +
                    "Maximum Range Accelerometer: " + String.valueOf(sensorAcc.getMaximumRange()), Toast.LENGTH_SHORT).show();
        }
    }

    public void start() {
  //      sensorManager.registerListener(sensorListenerGyro, sensorGyro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorListenerAcc, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
//        sensorManager.unregisterListener(sensorListenerGyro);
        sensorManager.unregisterListener(sensorListenerAcc);
    }

    SensorEventListener sensorListenerAcc = new SensorEventListener() {

        float y,x,sec = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == sensorTypeAcc) {
                y = event.values[1];
                x = event.values[0];

                callBack_screenTilt.leftRightTilt(x);

                if (System.currentTimeMillis() < sec + 2000) {
                    return;
                }


                callBack_screenTilt.frontTilt(y);



            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    SensorEventListener sensorListenerGyro = new SensorEventListener() {

        float y = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == sensorTypeGyro) {

                y = event.values[1];
                callBack_screenTilt.leftRightTilt(y);
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    };
}
