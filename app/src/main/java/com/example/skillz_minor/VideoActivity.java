package com.example.skillz_minor;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity {

    Button nextbtn,prevbtn;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    public String link;
    Boolean nextclicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        youTubePlayerView = findViewById(R.id.youtube_view);
        nextbtn  = findViewById(R.id.nextbtn);
        prevbtn = findViewById(R.id.previousbtn);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextclicked = true;
                Toast.makeText(VideoActivity.this,"button working",Toast.LENGTH_SHORT).show();
            }
        });
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize("AIzaSyAeblw3-lZUHFDaameo2RfLBXQV_q7Y9do", onInitializedListener);
        /*playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }
    private String getIncomingIntent(){
        String videonum = null;
        if(getIntent().hasExtra("videonumber")){
            videonum = getIntent().getStringExtra("videonumber");
        }
        return videonum;
    }
}