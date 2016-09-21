package homework.softuni.bg.homework4_1spotifyservice.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.IOException;

import homework.softuni.bg.homework4_1spotifyservice.MainActivity;
import homework.softuni.bg.homework4_1spotifyservice.listener.OnItemClickListener;


/**
 * Created by Milko on 20.9.2016 г..
 */

public class SpotifyService extends Service implements MediaPlayer.OnPreparedListener{
  private IBinder binder = new FirstServiceBinder();
  private int seekTime = 5000;
  private MediaPlayer player;
  private OnItemClickListener callback;

  public void setServiceCallback(OnItemClickListener serviceCallback) {
    this.callback = serviceCallback;
  }

  @Override
  public void onPrepared(MediaPlayer mediaPlayer) {
    mediaPlayer.start();
  }

  public class FirstServiceBinder extends Binder {
    public SpotifyService getService() {
      return SpotifyService.this;
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return binder;
  }

  @Override
  public void onCreate() {
    player =  new MediaPlayer();
    initMediaPlayerPreference();
    Toast.makeText(getApplicationContext(), "Service started", Toast.LENGTH_SHORT).show();
    super.onCreate();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    String action = intent.getStringExtra(MainActivity.ACTION);
    String song = intent.getStringExtra(MainActivity.SONG_NAME);
    if (song != null) {
      if (action != null) {
        if (MainActivity.ACTION_PLAY.equals(action)) {
          playSong(song);
        } else if (MainActivity.ACTION_PAUSE.equals(action)) {
          pauseSong();
        } else if (MainActivity.ACTION_FAST_FORWARD.equals(action)) {
          fastForwardSong();
        } else if (MainActivity.ACTION_FAST_BACKWARD.equals(action)) {
          fastBackwardSong();
        }
      }
    }
    return super.onStartCommand(intent, flags, startId);
  }

  private void fastBackwardSong() {
    int startTime = player.getCurrentPosition();
    player.seekTo(startTime - seekTime);
  }

  private void fastForwardSong() {
    int startTime = player.getCurrentPosition();
    player.seekTo(startTime + seekTime);
  }

  private void pauseSong() {
    if (player.isPlaying()) {
      player.pause();;
    } else
      player.start();
  }

  public void playSong(String song) {
    AssetFileDescriptor afd = getApplication().getResources().openRawResourceFd(this.getResources().getIdentifier(song, "raw", this.getPackageName()));
    try {
      player.reset();
      player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
      player.prepare();

    } catch (IOException e) {
      e.printStackTrace();
    }
    player.setOnPreparedListener(this);
    player.start();
  }

  private void initMediaPlayerPreference() {
    player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (player != null) {
      player.stop();
      player.release();
    }
  }
}
