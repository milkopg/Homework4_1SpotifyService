package homework.softuni.bg.homework4_1spotifyservice.listener;

/**
 * Created by Milko on 20.9.2016 г..
 */

public interface OnItemClickListener {
  public void onClickStartSong(String songName);
  public void onPlay(String songName);
  public void onPause(String songName);
  public void onFastForward(String songName);
  public void onFastBackward(String songName);
}
