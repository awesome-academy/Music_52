package com.vn.kienphung.music_52.ui.service;

import android.support.annotation.Nullable;

import com.vn.kienphung.music_52.data.model.Track;

import java.util.List;

public interface IPlay {
    void setPlayList(List<Track> tracks);

    boolean play();

    boolean play(List<Track> tracks);

    boolean play(List<Track> tracks, int startIndex);

    boolean play(Track track);

    void play(int position);

    void playPrevious();

    void playNext();

    void pause();

    boolean isPlaying();

    int getProgress();

    Track getPlayingTrack();

    void seekTo(int progress);

    void setPlayMode(PlayMode playMode);

    void registerCallback(Callback callback);

    void unregisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();

    interface Callback {

        void onSwitchPrevious(@Nullable Track previous);

        void onSwitchNext(@Nullable Track next);

        void onComplete(@Nullable Track next);

        void onPlayStatusChanged(boolean isPlaying);
    }
}
