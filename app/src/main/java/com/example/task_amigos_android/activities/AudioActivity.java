package com.example.task_amigos_android.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.task_amigos_android.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AudioActivity extends AppCompatActivity {

    Button record, choose, delete, stopRecord;
    ImageButton play, stopAudio;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String AudioSavaPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        record = findViewById(R.id.recordButton);
        choose = findViewById(R.id.chooseButton);
        delete = findViewById(R.id.deleteButton);
        play = findViewById(R.id.playButton);
        stopAudio = findViewById(R.id.stopAudioButton);
        stopRecord = findViewById(R.id.stopButton);


        //Record button method to start and save recording
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions() == true) {

                    AudioSavaPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/"+"recordingAudio.mp3";
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    mediaRecorder.setOutputFile(AudioSavaPath);

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        Toast.makeText(AudioActivity.this, "Recording started", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    ActivityCompat.requestPermissions(AudioActivity.this,new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },1);
                }
            }
        });

        //Stop button to stop recording
        stopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                mediaRecorder.release();
                Toast.makeText(AudioActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
            }
        });


        //Play button to play the recording
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer = new MediaPlayer();

                //Testing for media player
                /*mediaPlayer = MediaPlayer.create(AudioActivity.this, R.raw.sampleaudio);
                mediaPlayer.start();
                Toast.makeText(AudioActivity.this, "Start playing", Toast.LENGTH_SHORT).show();*/

                if (AudioSavaPath != null){
                    try {
                        mediaPlayer.setDataSource(AudioSavaPath);
                        //mediaPlayer.prepare();
                        mediaPlayer.start();
                        Toast.makeText(AudioActivity.this, "Start playing", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    //Change this to play any existing audio from choose button
                    mediaPlayer = MediaPlayer.create(AudioActivity.this, R.raw.sampleaudio);
                    mediaPlayer.start();
                    Toast.makeText(AudioActivity.this, "Start playing", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Stop button to stop the playing
        stopAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    Toast.makeText(AudioActivity.this, "Stopped playing", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //Delete button to delete the recording
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });

        //Choose button to choose an existing audio
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });
    }

    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int third = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED &&
                third == PackageManager.PERMISSION_GRANTED;
    }

    //Pending - Delete button and choose audio button.

}