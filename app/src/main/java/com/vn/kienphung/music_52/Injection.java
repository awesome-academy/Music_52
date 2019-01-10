package com.vn.kienphung.music_52;

import android.content.Context;

public class Injection {
    public static Context provideContext() {
        return MusicPlayerApplication.getInstance();
    }
}
