package com.vn.kienphung.music_52.ui.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.data.source.remote.TrackDownloadManager;
import com.vn.kienphung.music_52.ui.base.adapter.BaseAdapter;
import com.vn.kienphung.music_52.ui.base.adapter.IAdapterview;
import com.vn.kienphung.music_52.ui.base.adapter.OnLoadMoreListener;
import com.vn.kienphung.music_52.utils.StringUtil;

import java.util.List;

public class TrackAdapter extends BaseAdapter<Track> implements
        TrackDownloadManager.DownloadListener {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int mLastItemClickPosition = RecyclerView.NO_POSITION;

    public TrackAdapter(Context context, RecyclerView recyclerView, List<Track> data) {
        super(context, data);

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
                recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track,
                    parent, false);
            return new TrackViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,
                    parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TrackViewHolder) {
            TrackViewHolder trackViewHolder = (TrackViewHolder) holder;
            trackViewHolder.bind(mData.get(position));
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public int getLastItemClickPosition() {
        return mLastItemClickPosition;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public void onDownloadError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloading() {
        Toast.makeText(mContext, mContext.getString(R.string.msg_downloading),
                Toast.LENGTH_SHORT).show();
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        private LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    private class TrackViewHolder extends RecyclerView.ViewHolder implements IAdapterview<Track>,
            View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private TextView mTextTitle;
        private TextView mTextArtist;
        private ImageView mImageTrack;
        private TextView mTextDuration;
        private TextView mTextLikeCount;
        private TextView mTextPlaybackCount;
        private ImageButton mImageButtonOption;
        private Track mTrack;
        private PopupMenu popupMenu;

        public TrackViewHolder(View itemView) {
            super(itemView);
            mImageTrack = itemView.findViewById(R.id.image_song);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mTextDuration = itemView.findViewById(R.id.text_duration);
            mTextLikeCount = itemView.findViewById(R.id.text_number_favorite);
            mTextPlaybackCount = itemView.findViewById(R.id.text_number_play);
            mImageButtonOption = itemView.findViewById(R.id.img_button_options);
            mLastItemClickPosition = getAdapterPosition();
            mImageButtonOption.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(Track item) {
            if (item == null) {
                return;
            }
            mTrack = item;
            mTextTitle.setText(mTrack.getTitle());
            mTextPlaybackCount.setText(StringUtil.formatCount(mTrack.getPlaybackCount()));
            mTextArtist.setText(item.getUserName());
            mTextLikeCount.setText(StringUtil.formatCount(mTrack.getLikesCount()));
            mTextDuration.setText(StringUtil.formatDuration(mTrack.getDuration()));
            Glide.with(mContext).load(mTrack.getArtworkUrl()).into(mImageTrack);
        }

        private void setupsetupOptionsMenu() {
            popupMenu = new PopupMenu(mContext, mImageButtonOption);
            popupMenu.inflate(R.menu.option_menu_item_track);
            if (mTrack.isDownloadable()) {
                popupMenu.getMenu().getItem(0).setTitle(R.string.action_download);
            } else {
                popupMenu.getMenu().getItem(0).setTitle(R.string.msg_track_copyrighted);
            }

            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_button_options:
                    setupsetupOptionsMenu();
                    break;
                default:
                    mItemClickListener.onItemClick(mData, mLastItemClickPosition);

            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_download:
                    if (popupMenu == null) {
                        return true;
                    }
                    if (popupMenu.getMenu().getItem(0).getTitle()
                            .equals(mContext.getString(R.string.action_download))) {
                        new TrackDownloadManager(mContext,
                                TrackAdapter.this)
                                .download(mTrack);
                    }
                    return true;
                default:
                    return false;
            }
        }
    }
}
