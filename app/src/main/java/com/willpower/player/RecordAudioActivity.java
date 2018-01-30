package com.willpower.player;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.willpower.player.recorder.BaseAudioRecorder;
import com.willpower.player.recorder.RecordPermissionRequester;
import com.willpower.player.recorder.RecorderListener;
import com.willpower.player.recorder.SystemImplAudioRecorder;

import java.io.File;

/**
 * Created by Administrator on 2018/1/30.
 */

public class RecordAudioActivity extends AppCompatActivity implements RecordPermissionRequester {

    private static final int REQUEST_CODE_AUDIO_PERMISSIONS = 100;


    private Button mRecordAudioBtn;
    private Button mPlayAudioBtn;
    private BaseAudioRecorder mAudioRecorder;
    private Callback mPermissionCallback;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record_audio);

        initAudioRecorder();

        mPlayAudioBtn = (Button) findViewById(R.id.play_audio_btn);
        mRecordAudioBtn = (Button) findViewById(R.id.record_audio_btn);
        mRecordAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAudioRecorder.isRecording()) {
                    mAudioRecorder.stopRecord();
                } else {
                    startRecord();
                }
            }
        });

        mPlayAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayAudioActivity.start(RecordAudioActivity.this, getAudioFilePath());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAudioRecorder.destroy();
    }

    private String getAudioFilePath() {
        String filePath = getFilesDir().getPath() + "/record_audio/";
        new File(filePath).mkdirs();
        return filePath + "record.aac";
    }

    private void startRecord() {
        String filePath = getAudioFilePath();
        TextView filePathView = (TextView) findViewById(R.id.record_file_path);
        filePathView.setText(filePath);

        mAudioRecorder.setOutputFile(filePath);
        mAudioRecorder.startRecord(this);
    }

    private void initAudioRecorder() {
        mAudioRecorder = new SystemImplAudioRecorder(new RecorderListener() {
            @Override
            public void onError(Error error) {
                showToast("Record Audio ERROR! " + error.toString());
                mPlayAudioBtn.setEnabled(false);
            }

            @Override
            public void onStartRecord() {
                mPlayAudioBtn.setEnabled(false);
                mRecordAudioBtn.setText("停止");
            }

            @Override
            public void onStopRecord() {
                mRecordAudioBtn.setText("停止");
                mPlayAudioBtn.setEnabled(true);
            }
        }, this);
    }

    private void showToast(int msgId) {
        Toast.makeText(this, msgId, Toast.LENGTH_SHORT).show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestRecordPermission(String[] permissions, Callback callback) {
        mPermissionCallback = callback;
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_AUDIO_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_AUDIO_PERMISSIONS && mPermissionCallback != null) {
            mPermissionCallback.onPermissionRequested();
        }
    }
}
