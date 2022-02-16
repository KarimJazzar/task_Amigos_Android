package com.example.task_amigos_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
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

public class AudioActivity extends AppCompatActivity {

    Button record, choose,delete, save, stopRecord;
    ImageButton play, stopAudio;
    SeekBar seekbar;
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
        save = findViewById(R.id.saveButton);
        play = findViewById(R.id.playButton);
        stopAudio = findViewById(R.id.stopAudioButton);
        seekbar = findViewById(R.id.seekBar);
        stopRecord = findViewById(R.id.stopButton);


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermissions() == true) {

                    /*AudioSavaPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/"+"recordingAudio.mp3";*/
                    AudioSavaPath = Environment.getDataDirectory().getAbsolutePath()
                            +"/"+"recordingAudio.mp3";
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
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
                            Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
                    },1);
                }
            }
        });

        stopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                mediaRecorder.release();
                Toast.makeText(AudioActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer = new MediaPlayer();
                try {
                    File path = android.os.Environment.getExternalStorageDirectory();
                    mediaPlayer.setDataSource(path + "/t1.mp3");

                    //mediaPlayer = MediaPlayer.create(AudioActivity.this, R.raw.sampleaudio);
                    mediaPlayer.setDataSource(AudioSavaPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(AudioActivity.this, "Start playing", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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




    }

    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int  third = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        int fourth = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED &&
                third == PackageManager.PERMISSION_GRANTED &&
                fourth == PackageManager.PERMISSION_GRANTED;
    }

    //Pending - Delete the save button, Delete recording button and choose audio button.

}