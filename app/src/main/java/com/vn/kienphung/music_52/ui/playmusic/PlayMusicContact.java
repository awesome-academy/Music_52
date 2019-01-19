package com.vn.kienphung.music_52.ui.playmusic;

import android.support.annotation.Nullable;

import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.ui.base.BasePresenter;
import com.vn.kienphung.music_52.ui.base.BaseView;
import com.vn.kienphung.music_52.ui.service.MusicService;
import com.vn.kienphung.music_52.ui.service.PlayMode;

public class PlayMusicContact {
    interface View extends BaseView<Presenter> {

        void onServiceBound(MusicService service);

        void onServiceUnbound();

        void onTrackUpdated(@Nullable Track track);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);
    }

    interface Presenter extends BasePresenter {

        void retrieveLastPlayMode();

        void bindMusicService();

        void unbindMusicService();
    }
}
