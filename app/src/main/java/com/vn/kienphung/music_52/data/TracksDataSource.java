package com.vn.kienphung.music_52.data;

import android.support.annotation.NonNull;

import com.vn.kienphung.music_52.data.model.Track;

import java.util.List;

public interface TracksDataSource {
    void searchTracks(String trackName, @NonNull LoadTracksCallback callback);

    interface LoadTracksCallback {

        void onTracksLoaded(List<Track> tracks);

        void onDataNotAvailable(String msg);
    }

    interface LocalDataSource extends TracksDataSource {

        void deleteTrack(@NonNull String taskId);

        void addTrackToPlayList(Track track);

        void getTracks();

    }

    interface RemoteDataSource extends TracksDataSource {

        void getTracks(String genre, int limit, int offSet, @NonNull LoadTracksCallback callback);
    }
}
