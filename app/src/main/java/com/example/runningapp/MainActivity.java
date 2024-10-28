package com.example.runningapp;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
    // Activity elements
    TextView stepTextView;
    FullGauge timeFullGauge;

    // Properties
    long startTime = 0;
    CountDownTimer timerHandler;
    IStepsHandler _stepHandler;

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
        stepTextView = findViewById(R.id.textviewSteps);
        timeFullGauge = findViewById(R.id.timeFullGauge);
        timeFullGauge.setValue(32);

        // Set up timer
        timerHandler = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long l) {
                int steps = _stepHandler.GetStepsTaken();
                stepTextView.setText(String.valueOf(steps));
            }

            @Override
            public void onFinish() {
                _stepHandler.Pause();
            }
        };
        timerHandler.start();

        // Event listeners
    }

    private void SetUpStepHandler() {
        SensorManager sensorManager;
        Sensor stepSensor = null;

        // Set up sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Check for step counter
//        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (stepSensor == null) {
            // Get accelerometer if step counter does not exist
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            _stepHandler = new AccelerometerHandler(sensorManager, stepSensor);
        } else {
            _stepHandler = new StepCounterHandler(sensorManager, stepSensor);
        }
    }
}