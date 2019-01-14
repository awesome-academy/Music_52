package com.vn.kienphung.music_52.ui.home;

import com.vn.kienphung.music_52.data.TracksDataSource;
import com.vn.kienphung.music_52.data.TracksRepository;
import com.vn.kienphung.music_52.data.model.Track;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter,
        TracksDataSource.LoadTracksCallback {
    private final HomeContract.View mView;
    private TracksRepository mTracksRepository;

    public HomePresenter(HomeContract.View view, TracksRepository tracksRepository) {
        mView = view;
        mView.setPresenter(this);
        mTracksRepository = tracksRepository;
    }

    @Override
    public void onTracksLoaded(List<Track> tracks) {
        mView.hideLoading();
        mView.showTracks(tracks);
    }

    @Override
    public void onDataNotAvailable(String msg) {
        mView.handleError(msg);
    }

    @Override
    public void loadTracks(String genre, int limit, int offSet) {
        mView.showLoading();
        mTracksRepository.getTracks(genre, limit, offSet, this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
