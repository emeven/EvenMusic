package com.example.evenmusic.util;

import android.content.Context;


import com.example.evenmusic.entity.Audio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Even.P on 2018/1/6.
 */

public class AudioList {
    private static Object mLock = new Object();
    private static List<Audio> mAudioList = null;
    private AudioList() {}

    public static List<Audio> getAudioList(Context context) {
        if (mAudioList == null) {
            synchronized (mLock) {
                if (mAudioList == null) {
                    mAudioList = new ArrayList<>();
                    for (Audio audio : MediaUtils.getAudioList(context)) {
                        /*if (!audio.isMusic()) {
                            Log.d("musiclist", "title: " + audio.getTitle() + ", isMusic: " + audio.isMusic()
                                    + ", isAlarm: " + audio.isAlarm() + ", isNotification: " + audio.isNotification() + ", isRingtone: " + audio.isRingtone());
                        }*/
                        if (audio.isMusic() && audio.getDuration() > 40 * 1000) {
                            mAudioList.add(audio);
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(mAudioList);
    }

    public static List<Audio> getAudioList(Context context, Comparator<? super Audio> cmp) {
        List<Audio> newList = new ArrayList<>(getAudioList(context));
        Collections.sort(newList, cmp);
        return newList;
    }
}
