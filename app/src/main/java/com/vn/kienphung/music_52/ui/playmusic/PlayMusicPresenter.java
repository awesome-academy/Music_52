package com.vn.kienphung.music_52.ui.playmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;

import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.data.source.local.PreferenceManager;
import com.vn.kienphung.music_52.ui.service.IPlay;
import com.vn.kienphung.music_52.ui.service.MusicService;
import com.vn.kienphung.music_52.ui.service.PlayMode;
import com.vn.kienphung.music_52.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class PlayMusicPresenter implements PlayMusicContact.Presenter {
    private Context mContext;
    private PlayMusicContact.View mView;
    private List<Track> mTracks;
    private int mPosition;

    private IPlay mPlayer;
    private boolean mIsServiceBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MusicBinder musicBinder = (MusicService.MusicBinder) iBinder;
            mPlayer = musicBinder.getService();
            mView.onServiceBound((MusicService) mPlayer);
            mView.onTrackUpdated(mPlayer.getPlayingTrack());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mPlayer = null;
            mView.onServiceUnbound();
        }
    };

    public PlayMusicPresenter(Context context, List<Track> tracks, PlayMusicContact.View view) {
        mContext = context;
        mView = view;
        mTracks = tracks;
        mPosition = PreferenceManager.getLastPosition(mContext);
        mView.setPresenter(this);
    }

    @Override
    public void retrieveLastPlayMode() {
        PlayMode lastPlayMode = PreferenceManager.lastPlayMode(mContext);
        mView.updatePlayMode(lastPlayMode);
    }

    @Override
    public void bindMusicService() {
        Intent intent = new Intent(mContext, MusicService.class);
        intent.setAction(Constant.ACTION_MAIN);
        intent.putParcelableArrayListExtra(Constant.EXTRA_LIST_TRACK,
                (ArrayList<? extends Parcelable>) mTracks);
        intent.putExtra(Constant.EXTRA_POSITION, mPosition);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mContext.startService(intent);
        mIsServiceBound = true;
    }

    @Override
    public void unbindMusicService() {
        if (mIsServiceBound) {
            mContext.unbindService(mConnection);
            mIsServiceBound = false;
        }
    }

    @Override
    public void subscribe() {
        bindMusicService();
        retrieveLastPlayMode();
        if (mPlayer != null && mPlayer.isPlaying()) {
            mView.onTrackUpdated(mPlayer.getPlayingTrack());
        } else {
        }
    }

    @Override
    public void unsubscribe() {
        unbindMusicService();
        mContext = null;
        mView = null;
    }
}
