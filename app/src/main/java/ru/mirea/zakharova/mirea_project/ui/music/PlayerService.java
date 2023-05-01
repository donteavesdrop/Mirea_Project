package ru.mirea.zakharova.mirea_project.ui.music;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mirea.zakharova.mirea_project.R;
import ru.mirea.zakharova.mirea_project.ui.music.MusicFragment;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private List<Integer> songs;
    private int currentSongIndex = 0;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, songs.get(currentSongIndex));
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                currentSongIndex++;
                if (currentSongIndex >= songs.size()) {
                    currentSongIndex = 0;
                }
                mediaPlayer = MediaPlayer.create(PlayerService.this, songs.get(currentSongIndex));
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(this);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public PlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize songs list here
        int[] songArray = {R.raw.be_somebody, R.raw.broken_wing, R.raw.courtesy_call, R.raw.falls_apart, R.raw.phenomenon, R.raw.war_of_change};
        songs = new ArrayList<Integer>(Arrays.asList(ArrayUtils.toObject(songArray)));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("Playing....")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("best player..."))
                .setContentTitle("Music Player");
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Student zakharova.M.I Notification", importance);

        channel.setDescription("MIREA Channel");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.createNotificationChannel(channel);
        startForeground(1, builder.build());
    }
}
//другой тестовый вариант
//public class PlayerService extends Service {
//    private MediaPlayer mediaPlayer;
//    public static final String CHANNEL_ID = "ForegroundServiceChannel";
//
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        mediaPlayer.start();
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                stopForeground(true);
//            }
//        });
//        return super.onStartCommand(intent, flags, startId);
//
//    }
//
//
//    @Override
//    public void onDestroy() {
//        stopForeground(true);
//        mediaPlayer.stop();
//    }
//
//    public PlayerService() {
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentText("Playing....")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("best player..."))
//                .setContentTitle("Music Player");
//        int importance = NotificationManager.IMPORTANCE_DEFAULT;
//
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Student zakharova.M.I Notification", importance);
//
//        channel.setDescription("MIREA Channel");
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.createNotificationChannel(channel);
//        startForeground(1, builder.build());
//        mediaPlayer= MediaPlayer.create(this, R.raw.be_somebody);
//        mediaPlayer.setLooping(false);
//    }
//}
//public class PlayerService extends Service {
//    private MediaPlayer mediaPlayer;
//    public static final String CHANNEL_ID = "ForegroundServiceChannel";
//    private List<Integer> songs = Arrays.asList(R.raw.be_somebody, R.raw.broken_wing, R.raw.courtesy_call, R.raw.falls_apart, R.raw.phenomenon, R.raw.war_of_change);
//    private int currentSongIndex = 0;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//        }
//        mediaPlayer = MediaPlayer.create(this, songs.get(currentSongIndex));
//        mediaPlayer.setLooping(false);
//        mediaPlayer.start();
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                if (mediaPlayer != null) {
//                    mediaPlayer.release();
//                }
//                currentSongIndex++;
//                if (currentSongIndex >= songs.size()) {
//                    currentSongIndex = 0;
//                }
//                mediaPlayer = MediaPlayer.create(PlayerService.this, songs.get(currentSongIndex));
//                mediaPlayer.start();
//                mediaPlayer.setOnCompletionListener(this);
//            }
//        });
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        stopForeground(true);
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//    }
//
//    public PlayerService() {
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentText("Playing....")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("best player..."))
//                .setContentTitle("Music Player");
//        int importance = NotificationManager.IMPORTANCE_DEFAULT;
//
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Student zakharova.M.I Notification", importance);
//
//        channel.setDescription("MIREA Channel");
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.createNotificationChannel(channel);
//        startForeground(1, builder.build());
//    }
//}


