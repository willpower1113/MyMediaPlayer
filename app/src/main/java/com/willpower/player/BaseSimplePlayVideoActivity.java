package com.willpower.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.willpower.player.base.SimpleMediaPlayerListener;
import com.willpower.player.base.VideoInfo;
import com.willpower.player.player.BaseMediaPlayer;
import com.willpower.player.player.SystemImplMediaPlayer;
import com.willpower.player.player.render.MediaRender;

/**
 * Created by Administrator on 2018/1/30.
 */

public abstract class BaseSimplePlayVideoActivity extends AppCompatActivity {

    protected static final String EXTRA_VIDEO_URL = "extra_video_url";


    public static void start(Context context, String url,
                             Class<? extends BaseSimplePlayVideoActivity> clazz) {

        Intent intent = new Intent(context, clazz);
        intent.putExtra(EXTRA_VIDEO_URL, url);
        context.startActivity(intent);
    }


    protected BaseMediaPlayer mMediaPlayer;
    protected String mVideoUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewResId());
        initMediaPlayer();

        mVideoUrl = getIntent().getStringExtra(EXTRA_VIDEO_URL);
        TextView urlView = (TextView) findViewById(R.id.video_url);
        if (!TextUtils.isEmpty(mVideoUrl)) {
            urlView.setText(mVideoUrl);
            mMediaPlayer.play(new VideoInfo(mVideoUrl));
        } else {
            urlView.setText("错误！");
        }
    }

    protected abstract int getContentViewResId();

    protected abstract MediaRender createMediaRender();

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.destroy();
    }

    private void initMediaPlayer() {
        MediaRender render = createMediaRender();
        final View renderView = render.getRenderView();
        renderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mMediaPlayer.updateMediaRenderSize(
                                renderView.getMeasuredWidth(),
                                renderView.getMeasuredHeight(),
                                false);
                    }
                });

        mMediaPlayer = new SystemImplMediaPlayer(this, render);
        mMediaPlayer.addPlayerListener(new SimpleMediaPlayerListener() {
            @Override
            public void onPlayComplete() {
                Toast.makeText(BaseSimplePlayVideoActivity.this,
                        "播放完毕！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
