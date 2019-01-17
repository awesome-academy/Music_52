package com.vn.kienphung.music_52.ui.playmusic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vn.kienphung.music_52.Injection;
import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.ui.main.MainActivity;

public class PlayMusicFragment extends Fragment implements ViewPager.OnPageChangeListener,
        View.OnClickListener {

    private static final String TAG = "PlayMusicFragment";
    private static final int SET_MAGIN = 8;
    private int mCurrentPosition;
    private MainActivity mMainActivity;
    private ViewPager mViewPager;
    private PlayMusicFragmentAdapter mPlayMusicPagerAdapter;
    private LinearLayout mSliderDots;
    private int mDotsCount;
    private ImageView[] mDotsView;
    private View mViewRoot;
    private TextView mTextTrackTitle;
    private TextView mTextArtist;
    private TextView mTextTotalTime;
    private TextView mTextCurrentTime;
    private ImageButton mImageButtonDown;
    private ImageButton mImageButtonPrevious;
    private ImageButton mImageButtonPlayToggle;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonDownload;
    private ImageButton mImageButtonFavorite;
    private ImageButton mImageButtonPlayModeToggle;
    private ImageButton mImageButtonAlarm;
    private SeekBar mSeekBarProgress;

    public static PlayMusicFragment newInstance() {
        return new PlayMusicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music,
                container, false);
        initView();
        return view;
    }

    private void initView() {
        mTextTrackTitle = mViewRoot.findViewById(R.id.text_track_title);
        mTextArtist = mViewRoot.findViewById(R.id.text_artist);
        mTextTotalTime = mViewRoot.findViewById(R.id.textview_total_time);
        mTextCurrentTime = mViewRoot.findViewById(R.id.textview_current_time);
        mSeekBarProgress = mViewRoot.findViewById(R.id.seek_bar_song);
        mImageButtonDown = mViewRoot.findViewById(R.id.button_down);
        mImageButtonDown.setOnClickListener(this);
        mImageButtonPrevious = mViewRoot.findViewById(R.id.button_previous);
        mImageButtonPrevious.setOnClickListener(this);
        mImageButtonPlayToggle = mViewRoot.findViewById(R.id.button_play_toggle);
        mImageButtonPlayToggle.setOnClickListener(this);
        mImageButtonNext = mViewRoot.findViewById(R.id.button_next);
        mImageButtonNext.setOnClickListener(this);
        mImageButtonDownload = mViewRoot.findViewById(R.id.button_download);
        mImageButtonDownload.setOnClickListener(this);
        mImageButtonPlayModeToggle = mViewRoot.findViewById(R.id.button_play_mode_toggle);
        mImageButtonPlayModeToggle.setOnClickListener(this);
        mImageButtonAlarm.setOnClickListener(this);
        mImageButtonFavorite = mViewRoot.findViewById(R.id.button_favorite);
        mImageButtonFavorite.setOnClickListener(this);
        mViewPager = mViewRoot.findViewById(R.id.page_music);
        mPlayMusicPagerAdapter = new PlayMusicFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPlayMusicPagerAdapter);
        mViewPager.setCurrentItem(PlayType.PageType.PLAY_TRACK);
        setUpSliderDot();
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    private void setUpSliderDot() {
        mSliderDots = mViewRoot.findViewById(R.id.slider_dots);
        mDotsCount = mPlayMusicPagerAdapter.getCount();
        mDotsView = new ImageView[mDotsCount];
        for (int i = 0; i < mDotsCount; i++) {
            mDotsView[i] = new ImageView(mMainActivity);
            mDotsView[i].setImageDrawable(ContextCompat.getDrawable(Injection.provideContext(),
                    R.drawable.inaction_dot));
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(SET_MAGIN, 0, SET_MAGIN, 0);
            mSliderDots.addView(mDotsView[i], params);
        }
        mDotsView[PlayType.PageType.PLAY_TRACK].setImageDrawable(ContextCompat.
                getDrawable(Injection.provideContext(),
                        R.drawable.action_dot));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position != PlayType.PageType.PLAY_TRACK) {
            mImageButtonDownload.setVisibility(View.GONE);
            mImageButtonAlarm.setVisibility(View.GONE);

        } else {
            mImageButtonDownload.setVisibility(View.VISIBLE);
            mImageButtonAlarm.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < mDotsCount; i++) {
            mDotsView[i].setImageDrawable(ContextCompat.getDrawable(Injection.provideContext(),
                    R.drawable.inaction_dot));
        }
        mDotsView[position].setImageDrawable(ContextCompat.getDrawable(Injection.provideContext(),
                R.drawable.action_dot));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {

    }
}
