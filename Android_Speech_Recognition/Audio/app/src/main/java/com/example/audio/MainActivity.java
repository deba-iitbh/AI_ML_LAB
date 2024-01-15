package com.example.audio;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioRecord;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.tensorflow.lite.support.audio.TensorAudio;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.audio.classifier.AudioClassifier;
import org.tensorflow.lite.task.audio.classifier.Classifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public final static int REQUEST_RECORD_AUDIO = 2033;

    String modelPath = "yamnet_classification.tflite";
    float probabilityThreshold = 0.3f;
    AudioClassifier classifier;
    private TensorAudio tensor;
    private AudioRecord record;
    private TimerTask timerTask;

    protected TextView outputTextView;
    protected Button startRecordingButton;
    protected Button stopRecordingButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputTextView = findViewById(R.id.textViewOutput);
        startRecordingButton = findViewById(R.id.buttonStartRecording);
        stopRecordingButton = findViewById(R.id.buttonStopRecording);

        stopRecordingButton.setEnabled(false);

        if (checkRecordAudioPermission()) {
            startRecordingButton.setEnabled(true);
        }

        onStartRecording(startRecordingButton);

        onStopRecording(stopRecordingButton);
    }

    private void onStopRecording(Button stopButton) {
        stopButton.setOnClickListener(view -> {
            startRecordingButton.setEnabled(true);
            stopRecordingButton.setEnabled(false);
        });


        if (timerTask != null) {
            timerTask.cancel();
        }


        if (record != null) {
            record.stop();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void onStartRecording(Button startButton) {
        startButton.setOnClickListener(view -> {
            startRecordingButton.setEnabled(false);
            stopRecordingButton.setEnabled(true);
            showToast("Recording started");

            try {
                classifier = AudioClassifier.createFromFile(this, modelPath);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle model loading error
            }

            tensor = classifier.createInputTensorAudio();

            TensorAudio.TensorAudioFormat format = classifier.getRequiredTensorAudioFormat();
            // For debugging
            String specs = "Number of channels: " + format.getChannels() + "\n" + "Sample Rate: " + format.getSampleRate();

            record = classifier.createAudioRecord();
            record.startRecording();

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    tensor.load(record);
                    List<Classifications> output = classifier.classify(tensor);
                    List<Category> finalOutput = new ArrayList<>();

                    for (Classifications classifications : output) {
                        for (Category category : classifications.getCategories()) {
                            if (category.getScore() > probabilityThreshold) {
                                finalOutput.add(category);
                            }
                        }
                    }

                    finalOutput.sort((o1, o2) -> (int) (o1.getScore() - o2.getScore()));

                    StringBuilder outputStr = new StringBuilder();
                    for (Category category : finalOutput) {
                        outputStr.append(category.getLabel()).append(": ").append(category.getScore()).append("\n");
                    }

                    runOnUiThread(() -> {
                        if (finalOutput.isEmpty()) {
                            outputTextView.setText("Could not classify");
                        } else {
                            outputTextView.setText(outputStr.toString());
                        }
                    });
                }
            };

            new Timer().scheduleAtFixedRate(timerTask, 1, 500);

        });
    }

    private boolean checkRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
            return false;
        } else {
            return true;
        }
    }

}
