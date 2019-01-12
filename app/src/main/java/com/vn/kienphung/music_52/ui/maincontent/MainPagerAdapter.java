package com.vn.kienphung.music_52.ui.maincontent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vn.kienphung.music_52.ui.home.HomeFragment;
import com.vn.kienphung.music_52.ui.local.LocalFragment;
import com.vn.kienphung.music_52.ui.playlist.PlaylistFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_COUNT = 3;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TabType.HOME:
                return HomeFragment.newInstance();
            case TabType.LOCAL_MUSIC:
                return LocalFragment.newInstance();
            case TabType.PLAYLIST:
                return PlaylistFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
