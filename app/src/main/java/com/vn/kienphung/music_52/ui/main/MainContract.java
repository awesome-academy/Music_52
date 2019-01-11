package com.vn.kienphung.music_52.ui.main;

import com.vn.kienphung.music_52.ui.base.BasePresenter;
import com.vn.kienphung.music_52.ui.base.BaseView;

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void showSplashScreen();
    }

    interface Presenter extends BasePresenter {
        void startPlashScreen();
    }
}
