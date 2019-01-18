package com.vn.kienphung.music_52.ui.service;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.widget.RemoteViews;
import com.vn.kienphung.music_52.data.model.Track;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.callback.Callback;

public class MusicService {

    public static final int NO_POSITION = -1;
    private static final int NOTIFICATION_ID = 1;
    private static final int INITIAL_CAPACITY = 2;

    private RemoteViews mContentViewBig, mContentViewSmall;
    private Bitmap mBitmap;
    private MediaPlayer mMediaPlayer;
    private List<Track> mTracks = new ArrayList<>();
    private PlayMode mPlayMode;
    private List<Callback> mCallbacks = new ArrayList<>(INITIAL_CAPACITY);
    private boolean isPaused;
    private int mPlayingindex = -1;

    public void setBitmap(Bitmap bm) {
        mBitmap = bm;
    }
}
