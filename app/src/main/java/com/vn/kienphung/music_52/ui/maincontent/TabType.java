package com.vn.kienphung.music_52.ui.maincontent;

import android.support.annotation.IntDef;

import static com.vn.kienphung.music_52.ui.maincontent.TabType.HOME;
import static com.vn.kienphung.music_52.ui.maincontent.TabType.LOCAL_MUSIC;
import static com.vn.kienphung.music_52.ui.maincontent.TabType.PLAYLIST;

@IntDef({HOME, LOCAL_MUSIC, PLAYLIST})
public @interface TabType {
    int HOME = 0;
    int LOCAL_MUSIC = 1;
    int PLAYLIST = 2;
}
