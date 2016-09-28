package homework.softuni.bg.homework4_1spotifyservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import homework.softuni.bg.homework4_1spotifyservice.R;
import homework.softuni.bg.homework4_1spotifyservice.listener.OnItemClickListener;
import homework.softuni.bg.homework4_1spotifyservice.model.SpotifyModel;

/**
 * Created by sogodi on 7.9.2016 г..
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

  private static ArrayList<SpotifyModel> mAdapterData;
  private static OnItemClickListener callback;

  public RecycleViewAdapter(ArrayList<SpotifyModel> mAdapterData, OnItemClickListener listener) {
    this.mAdapterData = mAdapterData;
    this.callback = listener;
  }


  public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView mTextViewSongTitle;
    private TextView mTextViewSongArtist;
    private Button mButtonDownload;
    private TextView mTextViewExplicit;

    private Button mButtonPlay;
    private Button mButtonPause;
    private Button mButtonFastForward;
    private Button mButtonFastBackward;

    private int position;

    public void setItemPosition(int position)
    {
      this.position = position;
    }

    public ViewHolder(View itemView) {
      super(itemView);

      mTextViewSongTitle = (TextView) itemView.findViewById(R.id.textview_song_title);
      mTextViewSongTitle.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          callback.onClickStartSong(mAdapterData.get(position).getUrl());
        }
      });
      mTextViewSongArtist = (TextView) itemView.findViewById(R.id.textView_artist);
      mButtonDownload = (Button) itemView.findViewById(R.id.button_download);
      mTextViewExplicit = (TextView) itemView.findViewById(R.id.textView_explicit);

      mButtonPlay = (Button) itemView.findViewById(R.id.button_play);
      mButtonPlay.setOnClickListener(this);

      mButtonPause = (Button) itemView.findViewById(R.id.button_pause);
      mButtonPause.setOnClickListener(this);

      mButtonFastForward = (Button) itemView.findViewById(R.id.button_fast_forward);
      mButtonFastForward.setOnClickListener(this);

      mButtonFastBackward = (Button) itemView.findViewById(R.id.button_fast_backward);
      mButtonFastBackward.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (callback != null) {
        switch (view.getId()) {
          case R.id.button_play:
          callback.onPlay(mAdapterData.get(position).getUrl());
            break;
          case R.id.button_pause:
            callback.onPause(mAdapterData.get(position).getUrl());
            break;
          case R.id.button_fast_forward:
            callback.onFastForward(mAdapterData.get(position).getUrl());
            break;
          case R.id.button_fast_backward:
            callback.onFastBackward(mAdapterData.get(position).getUrl());
            break;
          default:
            break;
        }
      }
    }
  }

  @Override
  public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_template, parent, false);
    ViewHolder vh = new ViewHolder(view);
    return vh;
  }

  @Override
  public void onBindViewHolder(RecycleViewAdapter.ViewHolder holder, int position) {
    if ((holder != null) && (mAdapterData != null) && (mAdapterData.size() > 0)) {
      holder.mTextViewSongTitle.setText(mAdapterData.get(position).getTitle());
      holder.mTextViewSongArtist.setText(mAdapterData.get(position).getArtist());

      displayOrHideButtons(holder.mTextViewExplicit, position % 2 == 0, holder.mButtonDownload.getContext().getString(R.string.explicit));
      displayOrHideButtons(holder.mButtonDownload, position % 2 == 0, holder.mButtonDownload.getContext().getString(R.string.download));
      holder.setItemPosition(position);
    }
  }

  private void displayOrHideButtons(TextView mButton, boolean showComponent,String translation) {
    if (showComponent) {
      mButton.setEnabled(true);
      mButton.setVisibility(View.VISIBLE);
      mButton.setText(translation);
    }  else {
      mButton.setEnabled(false);
      mButton.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return mAdapterData.size();
  }
}
