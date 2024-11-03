package com.example.runningapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.color.DynamicColors;

public class RunReport extends AppCompatActivity {
    // Activity elements
    private TextView _caloriesBurntTextView;
    private TextView _distanceRanTextView;
    private TextView _timeTakenTextView;

    // Properties
    private int _timeTaken = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        DynamicColors.applyToActivityIfAvailable(this);
        setContentView(R.layout.activity_run_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up instance
        int stepsTaken = getIntent().getIntExtra("steps", 0);
        int timeTaken = getIntent().getIntExtra("timeTaken", 0);

        // Fetch activity elements
        _caloriesBurntTextView = findViewById(R.id.caloriesBurntTextView);
        _distanceRanTextView = findViewById(R.id.distanceRanTextView);
        _timeTakenTextView = findViewById(R.id.timeTakenTextView);

        // Calculate run stats based on steps taken
        double caloriesBurnt = CalculateCaloriesBurnt(stepsTaken);
        double distanceRan = CalculateDistanceRan(stepsTaken);

        // Set up elements
        PopulateRunStatistics(caloriesBurnt, distanceRan, timeTaken);
    }

    private void PopulateRunStatistics(double caloriesBurnt, double distanceRan, int timeTaken) {
        _caloriesBurntTextView.setText(caloriesBurnt + " cal");
        _distanceRanTextView.setText(distanceRan + " m");
        _timeTakenTextView.setText(timeTaken + " secs");
    }

    private double CalculateCaloriesBurnt(int steps) {
        double caloriesBurnt = 0;
        final double AverageCaloriesBurntPerStep = 0.04;

        // Calculation
        caloriesBurnt = steps * AverageCaloriesBurntPerStep;

        // Format result to 2 decimal places
        caloriesBurnt = Math.round(caloriesBurnt * 100.0) / 100.0;

        return caloriesBurnt;
    }

    private double CalculateDistanceRan(int steps) {
        double distanceRan = 0;
        final double AverageDistanceCoveredByAStep = 0.8;

        // Calculation
        distanceRan = steps * AverageDistanceCoveredByAStep;

        // Format result to 2 decimal places
        distanceRan = Math.round(distanceRan * 100.0) / 100.0;

        return distanceRan;
    }
}