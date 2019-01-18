package com.vn.kienphung.music_52.ui.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.data.source.local.PreferenceManager;
import com.vn.kienphung.music_52.data.source.remote.FetchBitmapFromUrl;
import com.vn.kienphung.music_52.ui.main.MainActivity;
import com.vn.kienphung.music_52.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.vn.kienphung.music_52.utils.Constant.ACTION_MAIN;
import static com.vn.kienphung.music_52.utils.Constant.ACTION_PLAY_NEXT;
import static com.vn.kienphung.music_52.utils.Constant.ACTION_PLAY_NOW;
import static com.vn.kienphung.music_52.utils.Constant.ACTION_PLAY_PREVIOUS;
import static com.vn.kienphung.music_52.utils.Constant.ACTION_PLAY_TOGGLE;
import static com.vn.kienphung.music_52.utils.Constant.ACTION_STOP_SERVICE;

public class MusicService extends Service implements IPlay, IPlay.Callback,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    public static final int NO_POSITION = -1;
    private static final int NOTIFICATION_ID = 1;
    private static final int INITIAL_CAPACITY = 2;
    private static final int COUNT = 1;
    private static final String CHANNEL_ID = "abc";

    private RemoteViews mContentViewBig, mContentViewSmall;
    private Bitmap mBitmap;
    private MediaPlayer mMediaPlayer;
    private List<Track> mTracks = new ArrayList<>();
    private PlayMode mPlayMode;
    private List<Callback> mCallbacks = new ArrayList<>(INITIAL_CAPACITY);
    private boolean isPaused;
    private int mCurretIndex = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayMode = PlayMode.LOOP;
        mMediaPlayer = new MediaPlayer();
        mTracks = new ArrayList<>();
        mMediaPlayer.setOnCompletionListener(this);
        registerCallback(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }

        String action = intent.getAction();
        if (action == null || action.isEmpty()) {
            return START_STICKY;
        }
        switch (action) {
            case ACTION_MAIN:
                actionMain(intent);
                break;
            case ACTION_PLAY_TOGGLE:
                actionPlayToggle();
                break;
            case ACTION_PLAY_NEXT:
                playNext();
                break;
            case ACTION_PLAY_PREVIOUS:
                previous();
                break;
            case ACTION_PLAY_NOW:
                actionNowPlaying(intent);
                break;
            case ACTION_STOP_SERVICE:
                actionStopService();
            default:
                break;
        }
        unregisterCallback(this);
        return START_STICKY;
    }

    public void actionMain(Intent intent) {
        List<Track> tracks = intent.getParcelableArrayListExtra(Constant.EXTRA_LIST_TRACK);
        int position = intent.getIntExtra(Constant.EXTRA_POSITION, 0);
        if (mMediaPlayer.isPlaying() && checkTrackPlaying(mTracks.get(position))) {
        } else {
            play(tracks, position);
            showNotification();
        }
    }

    public void actionPlayToggle() {
        if (isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    public void actionNowPlaying(Intent intent) {
        int position = intent.getIntExtra(Constant.EXTRA_POSITION, 0);
        if (mMediaPlayer.isPlaying()
                && checkTrackPlaying(mTracks.get(position))) {
        } else {
            play(position);
        }
    }

    public void actionStopService() {
        if (isPlaying()) {
            pause();
        }
        stopForeground(true);
        stopSelf();
    }

    public void setBitmap(Bitmap bm) {
        mBitmap = bm;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Track next = null;
        if (getPlayMode() == PlayMode.LIST && getPlayingIndex() == mTracks.size() - COUNT) {
        } else if (getPlayMode() == PlayMode.SINGLE) {
            next = getCurrentTrack();
            play(next);
        } else {
            boolean hasNext = hasNext(true);
            if (hasNext) {
                next = getTrackByNext();
                play();
            }
        }
        notifyComplete(next);
    }

    private void notifyComplete(Track track) {
        for (Callback callback : mCallbacks) {
            callback.onComplete(track);
        }
    }

    public boolean hasNext(boolean fromComplete) {
        if (mTracks.isEmpty()) return false;
        if (fromComplete) {
            if (mPlayMode == PlayMode.LIST && mCurretIndex + COUNT >= mTracks.size()) return false;
        }
        return true;
    }

    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    public int getPlayingIndex() {
        return mCurretIndex;
    }

    public Track getCurrentTrack() {
        if (mCurretIndex != NO_POSITION) {
            return mTracks.get(mCurretIndex);
        }
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
        notifyPlayStatusChanged(true);
        showNotification();
    }

    private void notifyPlayStatusChanged(boolean isPlaying) {
        for (Callback callback : mCallbacks) {
            callback.onPlayStatusChanged(isPlaying);
        }
    }

    public boolean hasPrevious() {
        return mTracks != null && mTracks.size() != 0;
    }

    @Override
    public void setPlayList(List<Track> tracks) {
        if (tracks == null) {
            tracks = new ArrayList<>();
        }
        mTracks = tracks;
    }

    public boolean prepare() {
        if (mTracks.isEmpty()) return false;

        if (mCurretIndex == NO_POSITION) {
            mCurretIndex = 0;
        }
        return true;
    }

    @Override
    public void play() {
        PreferenceManager.setLastPosition(this, mCurretIndex);
        PreferenceManager.setImageUrl(this, mTracks.get(mCurretIndex).getArtworkUrl());
        if (isPaused) {
            mMediaPlayer.start();
            notifyPlayStatusChanged(true);
            showNotification();
            return;
        }

        if (prepare()) {
            Track track = getCurrentTrack();
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(track.getUri());
                mMediaPlayer.setOnPreparedListener(this);
                mMediaPlayer.setOnCompletionListener(this);
                mMediaPlayer.prepareAsync();
                showNotification();
            } catch (IOException e) {
                showNotification();
                if (mCurretIndex < mTracks.size()) {
                    getTrackByNext();
                }
            }
        }
    }

    public Track getTrackByNext() {
        switch (mPlayMode) {
            case LOOP:
            case LIST:
            case SINGLE:

                if (mCurretIndex >= mTracks.size()) {
                    mCurretIndex = 0;
                }
                mCurretIndex ++ ;
                break;
            case SHUFFLE:
                mCurretIndex = randomPlayIndex();
                break;
        }
        return mTracks.get(mCurretIndex);
    }

    private int randomPlayIndex() {
        int randomIndex = new Random().nextInt(mTracks.size());
        if (mTracks.size() > COUNT && randomIndex == mCurretIndex) {
            randomPlayIndex();
        }
        return randomIndex;
    }


    @Override
    public void play(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        isPaused = false;
        setPlayList(tracks);
        mCurretIndex = 0;
        play();

    }

    @Override
    public void play(List<Track> tracks, int startIndex) {
        if (tracks == null || startIndex < 0 || startIndex >= tracks.size()) {
            return;
        }
        isPaused = false;
        setPlayList(tracks);
        setCurretIndex(startIndex);
        play();
    }

    @Override
    public void play(Track track) {
        if (track == null) {
            return;
        }
        mTracks.clear();
        mTracks.add(track);
        PreferenceManager.putTracks(this, mTracks);
        play();
    }

    @Override
    public void play(int position) {
        mCurretIndex = position;
        play();
        notifyPlayPrevious(mTracks.get(mCurretIndex));
    }

    @Override
    public void previous() {
        isPaused = false;
        if (hasPrevious()) {
            if (mCurretIndex - COUNT < 0) {
                mCurretIndex = mTracks.size() - COUNT;
            } else {
                mCurretIndex--;
            }
            play();
            notifyPlayPrevious(getPlayingTrack());
        }
    }

    @Override
    public void playNext() {
        isPaused = false;
        boolean hasNext = hasNext(false);
        if (hasNext) {
            getTrackByNext();
            play();
        }
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
        isPaused = true;
        notifyPlayStatusChanged(false);
        showNotification();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public Track getPlayingTrack() {
        return getCurrentTrack();
    }

    @Override
    public void seekTo(int progress) {
        if (mTracks.isEmpty()) {
            return;
        }
        Track currentTrack = getCurrentTrack();
        if (currentTrack != null) {
            if (currentTrack.getDuration() <= progress) {
                onCompletion(mMediaPlayer);
            } else {
                mMediaPlayer.seekTo(progress);
            }
        }
    }

    @Override
    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
    }

    @Override
    public void registerCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

    @Override
    public void removeCallbacks() {
        mCallbacks.clear();
    }

    @Override
    public void releasePlayer() {
        mTracks = null;
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
        super.onDestroy();
    }

    @Override
    public void onSwitchPrevious(@Nullable Track previous) {
        showNotification();
    }

    @Override
    public void onSwitchNext(@Nullable Track next) {
        showNotification();
    }

    @Override
    public void onComplete(@Nullable Track next) {
        showNotification();
    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {
        showNotification();
    }

    private void showNotification() {
        Track track = getPlayingTrack();
        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.setAction(ACTION_MAIN);
        PreferenceManager.setIsPlaying(this, isPlaying());
        notIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(getApplicationContext(),
                0,
                notIntent, 0);

        Intent previousIntent = new Intent(getApplicationContext(), MusicService.class);
        previousIntent.setAction(ACTION_PLAY_PREVIOUS);
        PendingIntent ppreviousIntent = PendingIntent.getService(getApplicationContext(),
                0,
                previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(getApplicationContext(), MusicService.class);
        playIntent.setAction(ACTION_PLAY_TOGGLE);
        PendingIntent pplayIntent = PendingIntent.getService(getApplicationContext(),
                0,
                playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(getApplicationContext(), MusicService.class);
        nextIntent.setAction(ACTION_PLAY_NEXT);
        PendingIntent pnextIntent = PendingIntent.getService(getApplicationContext(),
                0,
                nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissIntent = new Intent(getApplicationContext(), MusicService.class);
        dismissIntent.setAction(ACTION_STOP_SERVICE);
        PendingIntent pdismisIntent = PendingIntent.getService(getApplicationContext(),
                0,
                dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.remove_view_player_small);
        initRemoteView(remoteViews);
        new FetchBitmapFromUrl(this).execute(track.getArtworkUrl());
        if (mBitmap == null) {
            remoteViews.setImageViewResource(R.id.image_view_album, R.mipmap.ic_launcher);
        } else {
            remoteViews.setImageViewBitmap(R.id.image_view_album, mBitmap);
        }
        remoteViews.setTextViewText(R.id.text_view_name, track.getTitle());
        remoteViews.setTextViewText(R.id.text_view_artist, track.getUserName());

        remoteViews.setOnClickPendingIntent(R.id.button_close, pdismisIntent);
        remoteViews.setOnClickPendingIntent(R.id.img_button_play_previous, ppreviousIntent);
        remoteViews.setOnClickPendingIntent(R.id.img_button_play_next, pnextIntent);
        remoteViews.setOnClickPendingIntent(R.id.button_play_toggle, pplayIntent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(track.getTitle())
                .setContentText(track.getUserName())
                .setContentIntent(pendInt)
                .setContent(remoteViews);
        Notification not = builder.build();
        startForeground(NOTIFICATION_ID, not);
    }

    public void initRemoteView(RemoteViews remoteViews) {
        int iconPlayPause;
        if (isPlaying()) {
            iconPlayPause = R.drawable.ic_pause;
        } else {
            iconPlayPause = R.drawable.ic_play;
        }
        remoteViews.setImageViewResource(R.id.img_button_close, R.drawable.ic_remove_view_close);
        remoteViews.setImageViewResource(R.id.img_button_play_toggle, iconPlayPause);
        remoteViews.setImageViewResource(R.id.img_button_play_previous, R.drawable.ic_back);
        remoteViews.setImageViewResource(R.id.img_button_play_next, R.drawable.ic_play_next);
    }

    public boolean checkTrackPlaying(Track track) {
        Track trackCurent = mTracks.get(mCurretIndex);
        return track.getUri().equals(trackCurent.getUri());
    }

    private void notifyPlayPrevious(Track track) {
        for (Callback callback : mCallbacks) {
            callback.onSwitchPrevious(track);
        }
    }

    public void setCurretIndex(int curretIndex) {
        this.mCurretIndex = curretIndex;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
