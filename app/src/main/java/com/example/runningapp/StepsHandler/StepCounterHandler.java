package com.example.runningapp.StepsHandler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.io.Console;

public class StepCounterHandler implements IStepsHandler, SensorEventListener {
    // Properties
    private int _stepsTaken = 0;
    private SensorManager _sensorManager;
    private Sensor _stepsCounter;

    // Constructors
    public StepCounterHandler(SensorManager sensorManager, Sensor stepCounter) {
        _sensorManager = sensorManager;
        _stepsCounter = stepCounter;
    }

    // Event listeners
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        _stepsTaken = (int) sensorEvent.values[0];
        _stepsTaken++;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // Methods
    @Override
    public int GetStepsTaken() {
        return _stepsTaken;
    }

    @Override
    public void Start() {
        _sensorManager.registerListener(this, _stepsCounter, 3);
    }

    @Override
    public void Pause() {
        _sensorManager.unregisterListener(this);
    }
}
