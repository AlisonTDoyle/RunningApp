package com.example.runningapp.StepsHandler;

import static java.lang.Math.round;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerHandler implements IStepsHandler, SensorEventListener {
    // Properties
    private int _stepsTaken = 0;
    private SensorManager _sensorManager;
    private Sensor _accelerometer;
    private boolean _highLimit = false;

    // Constructors
    public AccelerometerHandler(SensorManager sensorManager, Sensor accelerometer) {
        _sensorManager = sensorManager;
        _accelerometer = accelerometer;
    }

    // Methods
    @Override
    public int GetStepsTaken() {
        return _stepsTaken;
    }

    public void Start() {
        _sensorManager.registerListener(this, _accelerometer, 3);
    }

    public void Pause() {
        _sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Vairables
        final double UPPER_MAG_LIMIT = 11.0;
        final double LOWER_MAG_LIMIT = 8.0;

        // Get sensor values
        float x = sensorEvent.values[0];    // get x value from sensor
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        // Get magnitude w/ pythagorus's theorem
        double magnitude = round(Math.sqrt((x*x) + (y*y) + (z*z)));

        // Calculate if step was taken
        if ((magnitude > UPPER_MAG_LIMIT) && (_highLimit == false)) {
            _highLimit = true;
        } else if ((magnitude < LOWER_MAG_LIMIT) && (_highLimit == true)) {
            _stepsTaken++;
            _highLimit = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
