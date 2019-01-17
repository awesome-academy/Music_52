package com.vn.kienphung.music_52.ui.playmusic.playanim;

import com.vn.kienphung.music_52.ui.base.BasePresenter;
import com.vn.kienphung.music_52.ui.base.BaseView;

public class PlayAnimContract {
    interface View extends BaseView<Presenter> {
        void setImage();

        void startAnimation();
        void cancelAnimation();
    }

    interface Presenter extends BasePresenter {
    }
}
