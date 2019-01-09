package com.vn.kienphung.music_52;

import android.app.Application;

public class MusicPlayerApplication extends Application {
    private static MusicPlayerApplication sInstance;

    public static MusicPlayerApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
