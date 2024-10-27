package com.example.runningapp.StepsHandler;

import android.hardware.SensorEventListener;

public interface IStepsHandler {
    // Methods
    public void Start();

    public void Pause();

    public int GetStepsTaken();
}
