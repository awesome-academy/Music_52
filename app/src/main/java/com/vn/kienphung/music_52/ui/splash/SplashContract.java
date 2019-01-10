package com.vn.kienphung.music_52.ui.splash;

import com.vn.kienphung.music_52.ui.base.BasePresenter;
import com.vn.kienphung.music_52.ui.base.BaseView;

public interface SplashContract {
    interface View extends BaseView<Presenter> {

        void showMainApp();
    }

    interface Presenter extends BasePresenter {

        void startingDelay(long millisecond);
    }
}
