package com.example.runningapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ekn.gruzer.gaugelibrary.FullGauge;
import com.example.runningapp.StepsHandler.AccelerometerHandler;
import com.example.runningapp.StepsHandler.IStepsHandler;
import com.example.runningapp.StepsHandler.StepCounterHandler;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    // Activity elements
//    private FullGauge _timeFullGauge;
    private TextView _stepTextView;
    private TextView _timerTextView;
    private FloatingActionButton _startFab;
    private FloatingActionButton _stopFab;
    private ExtendedFloatingActionButton _restartRunExtendedFab;
    private ExtendedFloatingActionButton _viewRunDetailsExtendedFab;
    private ProgressBar _timerProgressBar;

    // Properties
    private int _secondsPassed = 0;
    private long _startTime = 0;
    private int _steps = 0;

    private Handler _timerHandler = new Handler();
    private Runnable _timerRunnable = new Runnable() {
        @Override
        public void run() {
            // Counter in milliseconds
            long milliseconds = System.currentTimeMillis() - _startTime;

            // Convert to seconds
            _secondsPassed = (int)(milliseconds/1000);

            // Display steps
            _steps = _stepHandler.GetStepsTaken();
            _stepTextView.setText(String.valueOf(_steps));

            // Display time passed
            _timerTextView.setText(String.valueOf(_secondsPassed));
            if (_secondsPassed < 60) {
                // Increment progressbar
                _timerProgressBar.setProgress(_secondsPassed);
            } else {
                // When progress bar goes above 1 minute, reset display
                int visualProgress = _secondsPassed % 60;
                _timerProgressBar.setProgress(visualProgress);
            }

            // Timer intervals
            _timerHandler.postDelayed(this, 500);
        }
    };
    private IStepsHandler _stepHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up sensors
        SetUpStepHandler();
        _stepHandler.Start();

        // Fetch activity elements
        _stepTextView = findViewById(R.id.textviewSteps);
        _timerTextView = findViewById(R.id.textviewTime);
        _startFab = findViewById(R.id.startTimerFab);
        _stopFab = findViewById(R.id.stopTimerFab);
        _restartRunExtendedFab = findViewById(R.id.restartRunExtendedFab);
        _viewRunDetailsExtendedFab = findViewById(R.id.viewRunDetailsExtendedFab);
        _timerProgressBar = findViewById(R.id.timerProgressBar);
        _timerProgressBar.setMax(60);

        // Set up elements
        _stopFab.setEnabled(false);
        _restartRunExtendedFab.setEnabled(false);
        _viewRunDetailsExtendedFab.setEnabled(false);

        // Event listeners
        _startFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTimer();
            }
        });

        _stopFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopTimer();
            }
        });

        _viewRunDetailsExtendedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create run results activity
                Intent resultsActivity = new Intent(MainActivity.this, RunReport.class);
                resultsActivity.putExtra("steps", _steps);
                resultsActivity.putExtra("timeTaken", _secondsPassed);

                // Display activity
                startActivity(resultsActivity);
            }
        });

        _restartRunExtendedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetTimer();
            }
        });
    }

    private void SetUpStepHandler() {
        SensorManager sensorManager;
        Sensor stepSensor = null;

        // Set up sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Check for step counter
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (stepSensor == null) {
            // Get accelerometer if step counter does not exist
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            _stepHandler = new AccelerometerHandler(sensorManager, stepSensor);
        } else {
            _stepHandler = new StepCounterHandler(sensorManager, stepSensor);
        }
    }

    private void StartTimer() {
        _startTime = System.currentTimeMillis();
        _timerHandler.postDelayed(_timerRunnable, 0);

        // Update buttons status
        _startFab.setEnabled(false);
        _stopFab.setEnabled(true);
        _viewRunDetailsExtendedFab.setEnabled(false);
        _restartRunExtendedFab.setEnabled(false);
    }

    private void StopTimer() {
        _timerHandler.removeCallbacks(_timerRunnable);

        // Update buttons status
        _startFab.setEnabled(true);
        _stopFab.setEnabled(false);
        _viewRunDetailsExtendedFab.setEnabled(true);
        _restartRunExtendedFab.setEnabled(true);
    }

    private void ResetTimer() {
        _stepTextView.setText("0");
        _timerTextView.setText("0");
        _timerProgressBar.setProgress(0);
    }
}