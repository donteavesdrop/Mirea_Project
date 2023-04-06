package ru.mirea.zakharova.mirea_project.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.zakharova.mirea_project.MainActivity;
import ru.mirea.zakharova.mirea_project.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<Album> albums;

    public AlbumAdapter(List<Album> albums) {
        this.albums = albums;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.albumTitle.setText(album.getTitle());
        holder.albumYear.setText(album.getYear());
        holder.description.setText(album.getDescription());
        holder.albumImage.setImageResource(album.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        public TextView albumTitle;
        public TextView albumYear;
        public TextView description;
        public ImageView albumImage;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            albumTitle = itemView.findViewById(R.id.album_title);
            albumYear = itemView.findViewById(R.id.album_year);
            albumImage = itemView.findViewById(R.id.album_image);
            description = itemView.findViewById(R.id.description);
        }
    }

}
