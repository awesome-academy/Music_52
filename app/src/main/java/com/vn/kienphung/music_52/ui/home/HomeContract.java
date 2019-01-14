package com.vn.kienphung.music_52.ui.home;

import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.ui.base.BasePresenter;
import com.vn.kienphung.music_52.ui.base.BaseView;

import java.util.List;

interface HomeContract {

    interface View extends BaseView<Presenter> {

        void showTracks(List<Track> tracks);

        void showLoading();

        void hideLoading();

        void handleError(String msg);
    }

    interface Presenter extends BasePresenter {
        void loadTracks(String genre, int limit, int offSet);
    }
}
