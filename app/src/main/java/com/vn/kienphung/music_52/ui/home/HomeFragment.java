package com.vn.kienphung.music_52.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.data.TracksRepository;
import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.data.source.local.TracksLocalDataSource;
import com.vn.kienphung.music_52.data.source.remote.TracksRemoteDataSource;
import com.vn.kienphung.music_52.ui.base.adapter.OnItemClickListener;
import com.vn.kienphung.music_52.ui.base.adapter.OnLoadMoreListener;
import com.vn.kienphung.music_52.utils.ArrayListUtil;
import com.vn.kienphung.music_52.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View,
        AdapterView.OnItemSelectedListener, OnItemClickListener, OnLoadMoreListener {
    private HomePresenter mPresenter;
    private Spinner mSpinnerGenres;
    private RecyclerView mRecyclerView;
    private TrackAdapter mTrackAdapter;
    private ProgressDialog mDialog;
    private LinearLayoutManager mLayoutManager;
    private TextView mTextViewNoInternet;
    private List<Track> mTracks;
    private List<Track> mTrackList;
    private int mSize;
    private int mCurrentListItem = 0;
    private List<ArrayList<Track>> mContactMember;
    private final int COUNT = 1;
    private final int DELAY_MILLIS = 1000;
    private final int LIMIT_ITEM = 20;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    void init(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mTextViewNoInternet = view.findViewById(R.id.text_no_internet);
        ArrayAdapter<CharSequence> genresAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.music_genres, android.R.layout.simple_spinner_item);
        genresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGenres = view.findViewById(R.id.spinner_genres);
        mSpinnerGenres.setAdapter(genresAdapter);
        mSpinnerGenres.setOnItemSelectedListener(this);
        new HomePresenter(this, TracksRepository.getInstance(
                TracksRemoteDataSource.getInstance()
                , TracksLocalDataSource.getInstance()));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTrackAdapter = new TrackAdapter(getActivity(), mRecyclerView, null);
        mTrackAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mTrackAdapter);
        mTrackAdapter.setOnLoadMoreListener(this);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage(getString(R.string.action_loading));
    }

    private List<Track> moreData() {
        mCurrentListItem++;
        return mContactMember.get(mCurrentListItem);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        switch (parent.getId()) {
            case R.id.spinner_genres:
                mPresenter.loadTracks(Constant.MUSIC_GENRES[i],
                        Constant.LIMIT_DEFAULT, Constant.OFFSET_DEFAULT);
                mTextViewNoInternet.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(List listTrack, int position) {
        if (getActivity() instanceof IPlayTrack) {
            ((IPlayTrack) getActivity()).playTrack(listTrack, position);
        }
    }

    @Override
    public void onLoadMore() {
        if (mTrackList.size() < mSize) {
            mTrackList.add(null);
            mTrackAdapter.notifyItemInserted(mTrackList.size() - COUNT);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mTrackList.remove(mTrackList.size() - COUNT);
                    mTrackAdapter.notifyItemRemoved(mTrackList.size());
                    mTrackList.addAll(moreData());
                    mTrackAdapter.notifyDataSetChanged();
                    mTrackAdapter.setLoaded();
                }
            }, DELAY_MILLIS);
        }
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mTextViewNoInternet.setVisibility(View.GONE);
        mTracks = tracks;
        mSize = tracks.size();
        mContactMember = new ArrayListUtil<Track>().setTrackforArray(mTracks, LIMIT_ITEM);
        mTrackList = new ArrayList<>();
        mTrackList.addAll(mContactMember.get(0));
        mTrackAdapter.setData(mTrackList);
        mRecyclerView.setVisibility(View.VISIBLE);
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        mDialog.dismiss();
    }

    @Override
    public void handleError(String msg) {
        mTextViewNoInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = (HomePresenter) presenter;
    }
    public interface IPlayTrack {
        void playTrack(List<Track> tracks, int position);
    }
}
