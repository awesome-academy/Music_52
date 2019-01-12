package com.vn.kienphung.music_52.ui.maincontent;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.ui.main.MainActivity;

public class MainFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    public static final int ONE = 1 ;
    public static final int TWO = 1 ;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainActivity mMainActivity;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        setupTabIcons();
        return view;
    }

    private void initView(View view) {
        mViewPager = view.findViewById(R.id.view_pager);
        mTabLayout = view.findViewById(R.id.tablayout);
        MainPagerAdapter adapter = new MainPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.addOnTabSelectedListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    private void setupTabIcons() {
        mTabLayout.getTabAt(TabType.HOME).setIcon(R.drawable.ic_home_selected);
        mTabLayout.getTabAt(TabType.LOCAL_MUSIC).setIcon(R.drawable.ic_local_unselected);
        mTabLayout.getTabAt(TabType.PLAYLIST).setIcon(R.drawable.ic_playlist_unselected);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case TabType.HOME:
               showHomeTab();
                break;
            case TabType.LOCAL_MUSIC:
                showLocalMusicTab();
                break;
            case TabType.PLAYLIST:
                showPlaylistTab();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(mMainActivity, R.color.color_icon_tablayout);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public void showHomeTab() {
        mMainActivity.setTitle(mMainActivity.getResources().getString(R.string.title_home));
        mTabLayout.getTabAt(TabType.HOME).setIcon(R.drawable.ic_home_selected);
        mViewPager.setCurrentItem(0);
    }
    public void showLocalMusicTab(){
        mMainActivity.setTitle(mMainActivity.getResources().getString(R.string.title_local_music));
        mTabLayout.getTabAt(TabType.LOCAL_MUSIC).setIcon(R.drawable.ic_local_selected);
        mViewPager.setCurrentItem(ONE);
    }
    public void showPlaylistTab(){
        mMainActivity.setTitle(mMainActivity.getResources().getString(R.string.title_playlist));
        mTabLayout.getTabAt(TabType.PLAYLIST).setIcon(R.drawable.ic_playlist_selected);
        mViewPager.setCurrentItem(TWO);
    }

}
