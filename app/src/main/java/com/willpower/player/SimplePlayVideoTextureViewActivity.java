package com.willpower.player;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.TextureView;

import com.willpower.player.player.render.MediaRender;
import com.willpower.player.player.render.TextureViewMediaRender;


/**
 * Created by Administrator on 2018/1/30.
 */

public class SimplePlayVideoTextureViewActivity extends BaseSimplePlayVideoActivity {

    public static void start(Context context, String url) {
        start(context, url, SimplePlayVideoTextureViewActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_simple_play_video_texture;
    }

    @Override
    protected MediaRender createMediaRender() {
        TextureView textureView = (TextureView) findViewById(R.id.texture_view);
        return new TextureViewMediaRender(textureView);
    }
}
