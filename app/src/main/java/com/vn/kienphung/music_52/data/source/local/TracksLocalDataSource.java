package com.vn.kienphung.music_52.data.source.local;

import android.support.annotation.NonNull;

import com.vn.kienphung.music_52.data.TracksDataSource;
import com.vn.kienphung.music_52.data.model.Track;

public class TracksLocalDataSource implements TracksDataSource.LocalDataSource {
    private static TracksLocalDataSource sInstance;

    public TracksLocalDataSource() {
    }

    public static TracksLocalDataSource getInstance() {
        if (sInstance == null) {
            synchronized (TracksLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new TracksLocalDataSource();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void searchTracks(String trackName, @NonNull LoadTracksCallback callback) {

    }

    @Override
    public void deleteTrack(@NonNull String taskId) {

    }

    @Override
    public void addTrackToPlayList(Track track) {

    }

    @Override
    public void getTracks() {

    }
}
