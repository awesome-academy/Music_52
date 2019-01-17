package com.vn.kienphung.music_52.ui.playmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vn.kienphung.music_52.ui.playmusic.listtrack.ListTrackFragment;
import com.vn.kienphung.music_52.ui.playmusic.lyrics.LyricsFragment;
import com.vn.kienphung.music_52.ui.playmusic.playanim.PlayAnimationFragment;

public class PlayMusicFragmentAdapter extends FragmentPagerAdapter {
    public static final int TAB_COUNT = 3;

    public PlayMusicFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PlayType.PageType.NOW_PLAYING_LIST:
                return ListTrackFragment.newInstance();
            case PlayType.PageType.PLAY_TRACK:
                return PlayAnimationFragment.newInstance();
            case PlayType.PageType.LYRICS:
                return LyricsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
