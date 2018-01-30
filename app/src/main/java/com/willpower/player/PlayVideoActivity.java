package com.willpower.player;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.willpower.player.base.VideoInfo;
import com.willpower.player.widgets.JungleMediaPlayer;
import com.willpower.player.widgets.SimpleJungleMediaPlayerListener;

/**
 * Created by Administrator on 2018/1/30.
 * 视频播放界面
 */
public class PlayVideoActivity extends AppCompatActivity {
    private static final String EXTRA_VIDEO_URL = "extra_video_url";
    private static final String EXTRA_VIDEO_TITLE = "extra_video_title";
    TextView urlView;

    /**
     * @param context
     * @param url -- 视频的Url，path
     */
    public static void start(Context context, String url) {
        start(context, url, "");
    }

    /**
     * @param context
     * @param url--       视频的Url，path
     * @param title--视频标题
     */
    public static void start(Context context, String url,String title) {
        Intent intent = new Intent(context, PlayVideoActivity.class);
        intent.putExtra(EXTRA_VIDEO_URL, url);
        intent.putExtra(EXTRA_VIDEO_TITLE, title);
        context.startActivity(intent);
    }


    private JungleMediaPlayer mMediaPlayer;
    private boolean mIsFullScreenNow = false;
    private int mVideoZoneNormalHeight = 0;
    private String mVideoUrl;
    private String mTitle;
    private int startMillSeconds = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常量
        setContentView(R.layout.activity_play_video);

        initMediaPlayer();

        mVideoUrl = getIntent().getStringExtra(EXTRA_VIDEO_URL);
        mTitle = getIntent().getStringExtra(EXTRA_VIDEO_TITLE);
        urlView = (TextView) findViewById(R.id.video_url);
        if (!TextUtils.isEmpty(mVideoUrl)) {
            urlView.setText(mTitle);
        } else {
            urlView.setText("无效的视频路径！");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(mVideoUrl)) {
            mMediaPlayer.playMedia(new VideoInfo(mVideoUrl), startMillSeconds);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.stop();
        startMillSeconds = mMediaPlayer.getCurrentPosition();//记录当前位置
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            switchVideoContainer(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            switchVideoContainer(false);
        }
    }

    private void switchVideoContainer(boolean fullScreen) {
        if (mIsFullScreenNow == fullScreen) {
            return;
        }

        mIsFullScreenNow = fullScreen;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (mIsFullScreenNow) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }

        updateVideoZoneSize(fullScreen);
    }

    private void updateVideoZoneSize(final boolean fullScreen) {
        ViewGroup.LayoutParams params = mMediaPlayer.getLayoutParams();
        params.height = fullScreen
                ? ViewGroup.LayoutParams.MATCH_PARENT
                : mVideoZoneNormalHeight;
        mMediaPlayer.setLayoutParams(params);
    }

    private void initMediaPlayer() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mVideoZoneNormalHeight = (int) (metrics.widthPixels / 1.77f);

        FrameLayout panel = (FrameLayout) findViewById(R.id.adjust_panel_container);
        mMediaPlayer = (JungleMediaPlayer) findViewById(R.id.media_player);
        mMediaPlayer.setAdjustPanelContainer(panel);
        mMediaPlayer.setAutoReloadWhenError(false);
        mMediaPlayer.setPlayerListener(new SimpleJungleMediaPlayerListener() {

            @Override
            public void onTitleBackClicked() {
                if (mMediaPlayer.isFullscreen()) {
                    mMediaPlayer.switchFullScreen(false);
                    return;
                }

                finish();
            }
        });

        updateVideoZoneSize(false);
    }
}
