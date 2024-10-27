package com.example.runningapp;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.runningapp.StepsHandler.AccelerometerHandler;
import com.example.runningapp.StepsHandler.IStepsHandler;
import com.example.runningapp.StepsHandler.StepCounterHandler;

public class MainActivity extends AppCompatActivity {
    // Activity elements
    TextView sensorTextView;

    // Properties
    long startTime = 0;
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

        // Fetch activity elements
        sensorTextView = findViewById(R.id.sensorTextView);

        // Event listeners
        if (_stepHandler instanceof AccelerometerHandler) {
            sensorTextView.setText("Accelerometer");
        } else if (_stepHandler instanceof StepCounterHandler) {
            sensorTextView.setText("Step Counter");
        }
    }

    private void SetUpStepHandler() {
        SensorManager sensorManager;
        Sensor stepSensor;

        // Set up sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Check for step counter
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            // Get accelerometer if step counter does not exist
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            _stepHandler = new AccelerometerHandler(stepSensor);
        } else {
            _stepHandler = new StepCounterHandler(stepSensor);
        }
    }
}