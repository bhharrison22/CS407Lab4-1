package com.example.lab4_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView dlProgress;
    private volatile Boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startBtn);
        dlProgress = findViewById(R.id.dlProgress);
    }

    public void mockFileDownloader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading...");
                dlProgress.setText("");
            }
        });

        for (int i = 0; i <= 100; i = i + 10) {
            if(stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                        dlProgress.setText("");
                    }
                });
                return;
            }

            Log.d("MainActivity", "Download Progress: " + i + "%");
            int finalI = i;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dlProgress.setText("Download Progress: " + finalI + "%");
                }
            });
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
                dlProgress.setText("");
            }
        });
    }


    public void startDownload(View view) {
        //mockFileDownloader();
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view) {
        stopThread = true;
    }

    private class ExampleRunnable implements Runnable {
        @Override
        public void run(){
            mockFileDownloader();
        }
    }
}

