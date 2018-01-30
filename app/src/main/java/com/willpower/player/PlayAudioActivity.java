package com.willpower.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.willpower.player.base.MediaPlayerUtils;
import com.willpower.player.base.SimpleMediaPlayerListener;
import com.willpower.player.base.VideoInfo;
import com.willpower.player.player.BaseMediaPlayer;
import com.willpower.player.player.SystemImplMediaPlayer;

/**
 * Created by Administrator on 2018/1/30.
 */

public class PlayAudioActivity extends AppCompatActivity {

    private static final String EXTRA_AUDIO_URL = "extra_audio_url";


    public static void start(Context context, String url) {
        Intent intent = new Intent(context, PlayAudioActivity.class);
        intent.putExtra(EXTRA_AUDIO_URL, url);
        context.startActivity(intent);
    }


    private BaseMediaPlayer mMediaPlayer;
    private Button mPlayBtn;
    private SeekBar mProgressBar;
    private TextView mPlayProgressView;
    private Handler mUIHandler = new Handler(Looper.getMainLooper());
    private boolean mHasAudioPlay = false;
    private String mAudioUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);

        mAudioUrl = getIntent().getStringExtra(EXTRA_AUDIO_URL);
        TextView urlView = (TextView) findViewById(R.id.audio_url);
        if (!TextUtils.isEmpty(mAudioUrl)) {
            urlView.setText(mAudioUrl);
        } else {
            urlView.setText("地址错误！");
        }

        initAudioPlayer();
        mPlayBtn = (Button) findViewById(R.id.play_audio_btn);
        mProgressBar = (SeekBar) findViewById(R.id.audio_progress);
        mPlayProgressView = (TextView) findViewById(R.id.play_progress_view);
        mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && (mMediaPlayer.isPlaying() || mMediaPlayer.isPaused())) {
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMediaPlayer.stop();
        mMediaPlayer.destroy();
        unScheduleUpdateProgress();
    }

    private void initAudioPlayer() {
        mMediaPlayer = new SystemImplMediaPlayer(this);
        mMediaPlayer.addPlayerListener(new SimpleMediaPlayerListener() {

            @Override
            public void onStartPlay() {
                mProgressBar.setProgress(0);
                updateProgressView(0, 0);
                mPlayBtn.setText("加载中...");
            }

            @Override
            public void onFinishLoading() {
                mHasAudioPlay = true;
                mPlayBtn.setText("暂停");
                mProgressBar.setMax(mMediaPlayer.getDuration());
                scheduleUpdateProgress();
            }

            @Override
            public void onLoadFailed() {
                mHasAudioPlay = false;
                showToast("播放出错了！");
                mPlayBtn.setText("播放");
                updateProgressView(0, 0);
            }

            @Override
            public void onResumed() {
                mPlayBtn.setText("暂停");
            }

            @Override
            public void onPaused() {
                mPlayBtn.setText("播放");
            }

            @Override
            public void onPlayComplete() {
                mHasAudioPlay = false;
                showToast("播放完毕");
                mPlayBtn.setText("播放");

                updateProgressView(0, 0);
                unScheduleUpdateProgress();
            }

            @Override
            public void onStopped() {
                mHasAudioPlay = false;
                mPlayBtn.setText("播放");

                updateProgressView(0, 0);
                unScheduleUpdateProgress();
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void playAudio() {
        if (TextUtils.isEmpty(mAudioUrl)) {
            showToast("地址错误！");
            return;
        }

        if (!mHasAudioPlay) {
            mMediaPlayer.play(new VideoInfo(mAudioUrl));
        }

        if (mMediaPlayer.isPaused()) {
            mMediaPlayer.resume();
        } else {
            mMediaPlayer.pause();
        }
    }

    private void scheduleUpdateProgress() {
        mUIHandler.postDelayed(mUpdateProgressRunnable, 1000);
    }

    private void unScheduleUpdateProgress() {
        mUIHandler.removeCallbacks(mUpdateProgressRunnable);
    }

    private Runnable mUpdateProgressRunnable = new Runnable() {
        @Override
        public void run() {
            int progress = mMediaPlayer.getCurrentPosition();
            int max = mMediaPlayer.getDuration();

            updateProgressView(progress, max);
            scheduleUpdateProgress();
        }
    };

    private void updateProgressView(int progress, int max) {
        mProgressBar.setProgress(progress);
        mPlayProgressView.setText(getString(R.string.progress_format,
                MediaPlayerUtils.formatTime(progress),
                MediaPlayerUtils.formatTime(max)));
    }
}