package com.vn.kienphung.music_52.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.ui.service.PlayMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferenceManager {
    private static final String PREFS_NAME = "config.xml";
    private static final String KEY_PLAY_MODE = "playMode";
    private static final String KEY_POSITION = "position";
    private static final String KEY_LIST_TRACK = "tracks";
    private static final String KEY_IMAGE_URL = "imageUrl";
    private static final String KEY_IS_PLAYING = "isPlaying";

    private static SharedPreferences preferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor edit(Context context) {
        return preferences(context).edit();
    }

    public static PlayMode lastPlayMode(Context context) {
        String playModeName = preferences(context).getString(KEY_PLAY_MODE, null);
        if (playModeName != null) {
            return PlayMode.valueOf(playModeName);
        }
        return PlayMode.getDefault();
    }

    public static void setPlayMode(Context context, PlayMode playMode) {
        edit(context).putString(KEY_PLAY_MODE, playMode.name()).commit();
    }

    public static String getImageUrl(Context context) {
        return preferences(context).getString(KEY_IMAGE_URL, null);
    }

    public static void setImageUrl(Context context, String url) {
        edit(context).putString(KEY_IMAGE_URL, url).commit();
    }

    public static int getLastPosition(Context context) {
        return preferences(context).getInt(KEY_POSITION, 0);
    }

    public static void setLastPosition(Context context, int position) {
        edit(context).putInt(KEY_POSITION, position).commit();
    }

    public static boolean getIsPlaying(Context context) {
        return preferences(context).getBoolean(KEY_IS_PLAYING, false);
    }

    public static void setIsPlaying(Context context, boolean isPlaying) {
        edit(context).putBoolean(KEY_IS_PLAYING, isPlaying).commit();
    }

    public static void putTracks(Context context, String tracks) {
        edit(context).putString(KEY_LIST_TRACK, tracks).commit();
    }

    public static List<Track> getListTrack(Context context) {
        String tracks = preferences(context).getString(KEY_LIST_TRACK, null);
        Type listType = new TypeToken<ArrayList<Track>>() {
        }.getType();
        return new Gson().fromJson(tracks, listType);
    }

    public static void putTracks(Context context, List<Track> tracks) {
        edit(context).putString(KEY_LIST_TRACK, new Gson().toJson(tracks)).commit();
    }
}
