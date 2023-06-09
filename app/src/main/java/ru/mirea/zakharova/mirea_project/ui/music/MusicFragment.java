// MusicFragment.java

package ru.mirea.zakharova.mirea_project.ui.music;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import ru.mirea.zakharova.mirea_project.R;
import ru.mirea.zakharova.mirea_project.databinding.FragmentMusicBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MusicFragment extends Fragment {
    private FragmentMusicBinding binding;
    private MediaPlayer mediaPlayer;
    private boolean play = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int currentSongIndex = 0;
    private int[] songs = {R.raw.be_somebody, R.raw.broken_wing, R.raw.courtesy_call, R.raw.falls_apart, R.raw.phenomenon, R.raw.war_of_change};
    private String[] names = {"Be Somebody", "Broken Wing", "Courtesy Call", "Falls Apart", "Phenomenon", "War of Change"};
    private int[] pics = {R.drawable.the_end, R.drawable.the_flame_in, R.drawable.the_end, R.drawable.the_flame_in, R.drawable.phenomenon, R.drawable.the_end};

    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!play) {
                    play = true;
                    binding.imageView.setImageResource(pics[0]);
                    binding.song.setText(names[0]);
                    binding.btnPlay.setImageResource(com.google.android.material.R.drawable.btn_radio_off_mtrl);
                    Intent serviceIntent = new Intent(getActivity(), PlayerService.class);
                    ContextCompat.startForegroundService(getActivity(), serviceIntent);
                } else {
                    binding.imageView.setImageResource(R.color.black);
                    binding.song.setText("Click to play");
                    getActivity().stopService(new Intent(getActivity(), PlayerService.class));
                    binding.btnPlay.setImageResource(com.google.android.material.R.drawable.btn_radio_on_mtrl);
                    play = false;
                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongIndex++;
                if (currentSongIndex >= songs.length) {
                    currentSongIndex = 0;
                }
                Intent serviceIntent = new Intent(getActivity(), PlayerService.class);
                serviceIntent.putExtra("songName", names[currentSongIndex]);
                serviceIntent.putExtra("songId", songs[currentSongIndex]);
                ContextCompat.startForegroundService(getActivity(), serviceIntent);
                binding.imageView.setImageResource(pics[currentSongIndex]);
                binding.song.setText(names[currentSongIndex]);
                binding.btnPlay.setImageResource(com.google.android.material.R.drawable.btn_radio_off_mtrl);
                play = true;
            }
        });

        binding.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongIndex--;
                if (currentSongIndex < 0) {
                    currentSongIndex = songs.length - 1;
                }
                Intent serviceIntent = new Intent(getActivity(), PlayerService.class);
                serviceIntent.putExtra("songName", names[currentSongIndex]);
                serviceIntent.putExtra("songId", songs[currentSongIndex]);
                ContextCompat.startForegroundService(getActivity(), serviceIntent);
                binding.imageView.setImageResource(pics[currentSongIndex]);
                binding.song.setText(names[currentSongIndex]);
                binding.btnPlay.setImageResource(com.google.android.material.R.drawable.btn_radio_off_mtrl);
                play = true;
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                int x = (int) Math.ceil(progress / 1000f);
                if (x != 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    onStop();
                    binding.seekBar.setProgress(0);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

        return root;
    }
}
