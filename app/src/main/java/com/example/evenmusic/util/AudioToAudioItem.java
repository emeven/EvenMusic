package com.example.evenmusic.util;

import com.example.evenmusic.entity.Audio;
import com.example.evenmusic.entity.AudioItem;

/**
 * Created by Even.P on 2018/1/6.
 */

public interface AudioToAudioItem {
    AudioItem apply(Audio audio);
}
