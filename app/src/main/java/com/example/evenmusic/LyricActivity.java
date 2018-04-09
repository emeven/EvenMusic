package com.example.evenmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.evenmusic.service.AudioPlayService;

import java.io.File;

import me.zhengken.lyricview.LyricView;

/**
 * Created by Even.P on 2018/1/6.
 */

public class LyricActivity extends AppCompatActivity {

    private Object mLock = new Object();

    private int mCurrentTime;

    private String mPath;
    private String mTitle;
    private String mArtist;

    private TextView mTitleText;
    private TextView mArtistText;
    private LyricView mLyricView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);

        mPath = getIntent().getStringExtra(AudioPlayService.AUDIO_PATH_STR);
        mTitle = getIntent().getStringExtra(AudioPlayService.AUDIO_TITLE_STR);
        mArtist = getIntent().getStringExtra(AudioPlayService.AUDIO_ARTIST_STR);
        mCurrentTime = getIntent().getIntExtra(AudioPlayService.AUDIO_CURRENT_INT, 0);

        mTitleText = (TextView)findViewById(R.id.tv_title);
        mArtistText = (TextView)findViewById(R.id.tv_artist);
        mTitleText.setText(mTitle);
        mArtistText.setText(mArtist);
        mLyricView = (LyricView) findViewById(R.id.lyric_view);
        //载入歌词
        loadLyrics(getLyricsPath(mPath));

        updateUI(getIntent().getExtras());


    }

    private void updateUI(Bundle bundle) {
        synchronized (mLock) {
            String path = bundle.getString(AudioPlayService.AUDIO_PATH_STR);

            if (mPath == null || !mPath.equals(path)) {
                loadLyrics(getLyricsPath(mPath = path));
            }

//            int duration = bundle.getInt(AudioPlayService.AUDIO_DURATION_INT, 0);
//            int current = bundle.getInt(AudioPlayService.AUDIO_CURRENT_INT, 0);

//            mLyricView.setCurrentTimeMillis(current);
            mLyricView.setCurrentTimeMillis(mCurrentTime);
            mCurrentTime++;
        }
    }

    /**
     * 转换音乐路径为歌词路径
     * @param musicPath 音乐路径
     * @return
     */
    private String getLyricsPath(String musicPath) {
        if (musicPath == null) {
            return null;
        }
        return musicPath.replaceAll(".[^.]*$", ".lrc");
    }


    /**
     * 根据路径载入歌词
     * @param lyricsPath 歌词路径
     */
    private void loadLyrics(String lyricsPath) {
        File file = new File(lyricsPath);
        try {
            mLyricView.setLyricFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateUI(intent.getExtras());
    }
}
