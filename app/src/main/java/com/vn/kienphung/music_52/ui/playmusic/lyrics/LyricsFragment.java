package com.vn.kienphung.music_52.ui.playmusic.lyrics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.kienphung.music_52.R;

public class LyricsFragment extends Fragment {

        public static LyricsFragment newInstance() {
            return new LyricsFragment();
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
                ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_lyrics, container,
                    false);
            return view;
        }
}
