//package com.willpower.player;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//
//public class MainActivity extends AppCompatActivity {
//    private String MEDIA_URL;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        MEDIA_URL = "android.resource://" + getPackageName() + "/" + R.raw.test;
//
//        findViewById(R.id.play_video_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PlayVideoActivity.start(MainActivity.this, MEDIA_URL);
//            }
//        });
//
//        findViewById(R.id.play_audio_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PlayAudioActivity.start(MainActivity.this, MEDIA_URL);
//            }
//        });
//
//        findViewById(R.id.record_audio_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(RecordAudioActivity.class);
//            }
//        });
//
//        findViewById(R.id.simple_video_player_surface_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SimplePlayVideoSurfaceViewActivity.start(MainActivity.this, MEDIA_URL);
//            }
//        });
//
//        findViewById(R.id.simple_video_player_texture_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SimplePlayVideoTextureViewActivity.start(MainActivity.this, MEDIA_URL);
//            }
//        });
//    }
//
//    private void startActivity(Class<? extends Activity> clazz) {
//        startActivity(new Intent(this, clazz));
//    }
//}
