package com.example.runningapp.StepsHandler;

import android.hardware.Sensor;

public class StepCounterHandler implements IStepsHandler{
    // Properties
    private Sensor _stepsCounter;

    // Constructors
    public StepCounterHandler(Sensor stepCounter) {
        _stepsCounter = stepCounter;
    }

    // Methods
}
