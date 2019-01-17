package com.vn.kienphung.music_52.ui.playmusic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vn.kienphung.music_52.R;

public class PlayMusicFragment extends Fragment {
    private static final String TAG = "PlayMusicFragment";

    private int mCurrentPosition;

    private ViewPager mViewPager;

    public static PlayMusicFragment newInstance() {
        return new PlayMusicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mViewPager = view.findViewById(R.id.page_music);
        PlayMusicFragmentAdapter musicPagerAdapter
                = new PlayMusicFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(musicPagerAdapter);
        mViewPager.setCurrentItem(PlayType.PageType.PLAY_TRACK);
    }
}
