package com.example.runningapp.StepsHandler;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepCounterHandler implements IStepsHandler{
    /*
    References:
    - https://developer.android.com/health-and-fitness/guides/basic-fitness-app/read-step-count-data
    */

    // Properties
    private int _stepsTaken = 0;
    private SensorManager _sensorManager;
    private Sensor _stepsCounter;
    private SensorEventListener _stepCounterListener;

    // Constructors
    public StepCounterHandler(SensorManager sensorManager, Sensor stepCounter) {
        _sensorManager = sensorManager;
        _stepsCounter = stepCounter;
    }

    // Event listeners

    // Methods
    @Override
    public int GetStepsTaken() {
        return _stepsTaken;
    }

    @Override
    public void Start() {

    }

    @Override
    public void Pause() {

    }
}
