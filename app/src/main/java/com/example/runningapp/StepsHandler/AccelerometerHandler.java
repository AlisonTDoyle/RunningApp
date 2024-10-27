package com.example.runningapp.StepsHandler;

import android.hardware.Sensor;

public class AccelerometerHandler implements IStepsHandler{
    // Properties
    private Sensor _accelerometer;

    // Constructors
    public AccelerometerHandler(Sensor accelerometer) {
        _accelerometer = accelerometer;
    }

    // Methods
}
