package com.vn.kienphung.music_52.ui.playmusic;

import android.support.annotation.IntDef;

import static com.vn.kienphung.music_52.ui.playmusic.PlayType.PageType.NOW_PLAYING_LIST;
import static com.vn.kienphung.music_52.ui.playmusic.PlayType.PageType.LYRICS;
import static com.vn.kienphung.music_52.ui.playmusic.PlayType.PageType.PLAY_TRACK;

public @interface PlayType {
    @IntDef({NOW_PLAYING_LIST, PLAY_TRACK, LYRICS})
    public @interface PageType {
        int NOW_PLAYING_LIST = 0;
        int PLAY_TRACK = 1;
        int LYRICS = 2;
    }
}
