package homework.softuni.bg.homework4_1spotifyservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import homework.softuni.bg.homework4_1spotifyservice.adapter.RecycleViewAdapter;
import homework.softuni.bg.homework4_1spotifyservice.listener.OnItemClickListener;
import homework.softuni.bg.homework4_1spotifyservice.model.SpotifyModel;
import homework.softuni.bg.homework4_1spotifyservice.service.SpotifyService;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{
  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private ArrayList<SpotifyModel> mData;
  private ServiceConnection connection;
  private Intent spotifyServiceIntent;

  public static final String SONG_NAME  = "song_name";
  public static final String ACTION = "action";
  public static final String ACTION_PLAY = "action_play";
  public static final String ACTION_PAUSE = "action_pause";
  public static final String ACTION_FAST_FORWARD = "action_fast_forward";
  public static final String ACTION_FAST_BACKWARD = "action_fast_backward";

  @Override
  public void onClickStartSong(String songName) {
    Toast.makeText(this, "onCustomerClick listneer", Toast.LENGTH_SHORT).show();
    Log.d (this.getClass().getSimpleName(), String.valueOf(this.getResources().getIdentifier(songName, "raw", this.getPackageName())));
  }

  @Override
  public void onPlay(String songName) {
    onPlayerAction(ACTION_PLAY, songName);
  }

  @Override
  public void onPause(String songName) {
    onPlayerAction(ACTION_PAUSE, songName);
  }

  @Override
  public void onFastForward(String songName) {
    onPlayerAction(ACTION_FAST_FORWARD, songName);
  }

  @Override
  public void onFastBackward(String songName) {
    onPlayerAction(ACTION_FAST_BACKWARD, songName);
  }

  public void onPlayerAction(String action, String songName) {
    spotifyServiceIntent = new Intent(this, SpotifyService.class);
    spotifyServiceIntent.putExtra(SONG_NAME, songName);
    spotifyServiceIntent.putExtra(ACTION, action);
    bindService(spotifyServiceIntent, connection, Context.BIND_AUTO_CREATE);
    startService(spotifyServiceIntent);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mData = fillData();
    mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setHasFixedSize(false);

    mAdapter = new RecycleViewAdapter(mData, this);
    mRecyclerView.setAdapter(mAdapter);

    connection = new ServiceConnection() {
      @Override
      public void onServiceConnected(ComponentName componentName, IBinder service) {
        SpotifyService.FirstServiceBinder serviceToOperate = (SpotifyService.FirstServiceBinder) service;
        serviceToOperate.getService().setServiceCallback(MainActivity.this);
      }

      @Override
      public void onServiceDisconnected(ComponentName componentName) {
        Toast.makeText(MainActivity.this, "Service is out of the game", Toast.LENGTH_SHORT).show();
      }
    };

    spotifyServiceIntent = new Intent(this, SpotifyService.class);
    bindService(spotifyServiceIntent, connection, Context.BIND_AUTO_CREATE);
    startService(spotifyServiceIntent);
  }

  @Override
  protected void onDestroy() {
    unbindService(connection);
    stopService(spotifyServiceIntent);
    super.onDestroy();
  }

  private ArrayList<SpotifyModel> fillData() {
    mData = new ArrayList<>();
    int counter = 1;
    SpotifyModel model = new SpotifyModel(counter++, "Ain't My Fault", "Zara Larsson - Ain't My Fault", "zara_larsson");
    mData.add(model);

    model = new SpotifyModel(counter++, "Blow Your Mind (Mwah)", "Dua Lipa - Blow your Mind(Mwah)", "dua_lipa_blow_your_mind");
    mData.add(model);

    model = new SpotifyModel(counter++, "Carry me", "Kygo, Julia Michaels - Cloud Nine", "kygo_carry_me");
    mData.add(model);

    model = new SpotifyModel(counter++, "Suburbia", "Kilian & Jo, Erik Rapp - Suburbia", "kilian_suburbia");
    mData.add(model);

    model = new SpotifyModel(counter++, "Cold Water (feat. Justing Bieber & MO)", "Major Lazer, MO, Justin Bieber - Cold Water", "major_lazer_cold_water");
    mData.add(model);

    model = new SpotifyModel(counter++, "Dynamite (feat. Pretty Sister)", "Nause, Pretty Sister - Dynamite (feat Pretty Sister)", "nause_dynamite");
    mData.add(model);

    model = new SpotifyModel(counter++, "Threat You Better", "Shawn Mendes - Threat you better", "shawn_mendes_threat_you_better");
    mData.add(model);

    model = new SpotifyModel(counter++, "Lose control", "Missy Elliot - Lose Control", "missy_elliott_lose_control");
    mData.add(model);
    return mData;
  }
}
