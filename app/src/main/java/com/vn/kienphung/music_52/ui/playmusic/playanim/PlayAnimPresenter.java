package com.vn.kienphung.music_52.ui.playmusic.playanim;

public class PlayAnimPresenter implements PlayAnimContract.Presenter {

    private PlayAnimContract.View mView;

    public PlayAnimPresenter(PlayAnimContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }
}
