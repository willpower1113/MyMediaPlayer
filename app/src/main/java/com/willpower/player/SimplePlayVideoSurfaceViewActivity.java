package com.willpower.player;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;

import com.willpower.player.player.render.MediaRender;
import com.willpower.player.player.render.SurfaceViewMediaRender;


/**
 * Created by Administrator on 2018/1/30.
 */

public class SimplePlayVideoSurfaceViewActivity extends BaseSimplePlayVideoActivity {

    public static void start(Context context, String url) {
        start(context, url, SimplePlayVideoSurfaceViewActivity.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_simple_play_video_surface;
    }

    @Override
    protected MediaRender createMediaRender() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        return new SurfaceViewMediaRender(surfaceView);
    }
}
