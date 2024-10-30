package com.example.runningapp;

import android.os.Handler;

public class Timer {
    // Properties
    public int SecondsPassed = 0;

    private long _startTime = 0;
    private Handler _handler = new Handler();
    private Runnable _runnable = new Runnable() {
        @Override
        public void run() {
            // Counter in milliseconds
            long milliseconds = System.currentTimeMillis() - _startTime;

            // Convert to seconds
            SecondsPassed = (int)(milliseconds/1000);

            // Timer intervals
            _handler.postDelayed(this, 500);
        }
    };

    // Constructors
    public Timer() {
    }

    // Event listeners

    // Methods
    public void Start() {
        _startTime = System.currentTimeMillis();
        _handler.postDelayed(_runnable, 0);
    }

    public void Stop() {
        _handler.removeCallbacks(_runnable);
    }
}
