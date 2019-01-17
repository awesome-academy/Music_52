package com.vn.kienphung.music_52.ui.playmusic.playanim;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.kienphung.music_52.R;

public class PlayAnimationFragment extends Fragment {
    public static PlayAnimationFragment newInstance() {
        return new PlayAnimationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_animation, container,
                false);
        return view;
    }
}
