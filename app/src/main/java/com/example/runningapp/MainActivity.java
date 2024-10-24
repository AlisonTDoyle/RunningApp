package com.example.runningapp;

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

public class MainActivity extends AppCompatActivity {
    // Activity elements
    TextView stepsTextView;
    TextView timeTextView;

    Button startButton;
    Button stopButton;

    // Properties
    long startTime = 0;
    Handler handler = new Handler();
    Runnable runnable = SetUpRunnable();

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

        // Fetch activity elements
        stepsTextView = findViewById(R.id.stepsTextView);
        timeTextView = findViewById(R.id.timeTextView);
        startButton = findViewById(R.id.buttonStart);
        stopButton = findViewById(R.id.buttonStop);

        // Event listeners
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStart(view);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStop(view);
            }
        });
    }

    private Runnable SetUpRunnable() {
        Runnable newRunnable = new Runnable() {
            @Override
            public void run() {
                long milliseconds = System.currentTimeMillis() - startTime;
                int seconds = (int) (milliseconds/1000);
                int minutes = seconds/60;
                seconds = seconds % 60;

                timeTextView.setText(String.format("%02d:%02d", minutes, seconds));

                handler.postDelayed(this, 500);
            }
        };

        return  newRunnable;
    }

    public void doStop(View view) {
        handler.removeCallbacks(runnable);
    }

    public void doStart(View view) {
        startTime = System.currentTimeMillis();
        handler.postDelayed(runnable, 0);
    }
}