package sheron.csci4100u.labs.lab10;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlayMedia extends AppCompatActivity
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    public static final int PLAY_PAUSE_ID = R.id.ibtnPlayPause;
    public static final int STOP_ID = R.id.ibtnStop;

    MediaPlayer mediaPlayer;
    Spinner sprMediaList;
    ImageButton btnPlayPause;
    SurfaceView surface;

    List<String> mediaTitles;
    List<String> mediaFileNames;
    String filename;

    AssetManager assetManager;
    AssetFileDescriptor songFd;
    SurfaceHolder holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_media_activity);

        ((ImageButton) findViewById(STOP_ID)).setOnClickListener(this);
        btnPlayPause = (ImageButton) findViewById(PLAY_PAUSE_ID);
        btnPlayPause.setOnClickListener(this);

        sprMediaList = (Spinner) findViewById(R.id.sprMediaList);
        sprMediaList.setOnItemSelectedListener(this);

        surface = (SurfaceView) findViewById(R.id.svVideoHolder);
        holder = surface.getHolder();

        mediaTitles = Arrays.asList(getResources().getStringArray(R.array.song_titles));
        mediaFileNames = Arrays.asList(getResources().getStringArray(R.array.media_file_names));

        sprMediaList.setAdapter(new ArrayAdapter<>(this,
                R.layout.spinner_item,
                mediaTitles));

        mediaPlayer = new MediaPlayer();
        assetManager = getAssets();
    }


    //  Resets media players, releases resources and sets the data source
    public void resetMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            if (filename.contains(getString(R.string.mp4))) {
                surface.setVisibility(View.VISIBLE);
                mediaPlayer.setScreenOnWhilePlaying(true);
                mediaPlayer.setDisplay(holder);
            } else {
                surface.setVisibility(View.INVISIBLE);
            }

            try {
                songFd = assetManager.openFd(filename);
                mediaPlayer.setDataSource(songFd.getFileDescriptor(),
                        songFd.getStartOffset(),
                        songFd.getLength());

                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Spinner OnSelection Handling
    // ========================================================================
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stopMedia();
        setDataSource(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        setDataSource(3);
    }

    public void setDataSource(int id) {
        filename = mediaFileNames.get(id);
        resetMediaPlayer();
    }
    // ========================================================================


    // OnClick Handling
    // ========================================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // play button
            case PLAY_PAUSE_ID:
                playPauseMedia();
                break;
            // stop button
            case STOP_ID:
                stopMedia();
                break;
        }
    }

    public void playPauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlayPause.setImageDrawable(getDrawable(R.drawable.ic_play));
        } else {
            mediaPlayer.start();
            btnPlayPause.setImageDrawable(getDrawable(R.drawable.ic_pause));
        }
    }


    public void stopMedia() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                resetMediaPlayer();
                btnPlayPause.setImageDrawable(getDrawable(R.drawable.ic_play));
            }
        }
    }
    // ========================================================================
}
