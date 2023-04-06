package ru.mirea.zakharova.mirea_project.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.zakharova.mirea_project.R;


public class GalleryFragment extends Fragment {

    private List<Album> albums;
    private Button button;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        albums = getAlbums();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.album_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AlbumAdapter albumAdapter = new AlbumAdapter(albums);
        recyclerView.setAdapter(albumAdapter);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_gallery_to_slideshow);
            }
        });

        return view;
    }

    private List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Set It Off", "2000",
                "This album features a more refined sound, with a focus on hard rock and alternative metal. It includes the popular tracks \"Rawkfist\" and \"Move.\"",
                R.drawable.set_it_off));
        albums.add(new Album("Phenomenon", "2003",
                "This album features a more refined sound, with a focus on hard rock and alternative metal. It includes the popular tracks \"Rawkfist\" and \"Move.\"",
                R.drawable.phenomenon));
        albums.add(new Album("The Art of Breaking", "2005",
                "This album continues TFK's trend towards a more hard rock sound, while also incorporating some electronic and hip hop elements. Standout tracks include \"Slow Bleed\" and \"The Art of Breaking.\"",
                R.drawable.the_art_of_breaking));
        albums.add(new Album("The Flame in All of Us", "2007",
                "With this album, TFK experimented with a more melodic and mainstream-friendly sound. It includes the hit songs \"Falls Apart\" and \"What Do We Know.\"",
                R.drawable.the_flame_in));
        albums.add(new Album("Welcome to the Masquerade", "2009",
                "This album features a return to TFK's hard rock roots, with heavy guitars and aggressive vocals. Standout tracks include \"Bring Me to Life\" and \"The End Is Where We Begin.\"",
                R.drawable.welcome));
        albums.add(new Album("The End Is Where We Begin", "2012",
                "This album marks a return to TFK's rap rock sound, with plenty of heavy guitars and electronic beats. Notable tracks include \"War of Change\" and \"Be Somebody.\"",
                R.drawable.the_end));
        albums.add(new Album("Oxygen: Inhale", "2014",
                "This album features a more stripped-down and atmospheric sound, with a focus on melody and emotion. Standout tracks include \"Born This Way\" and \"Untraveled Road.\"",
                R.drawable.oxygen_inhale));
        albums.add(new Album("Exhale", "2016",
                "This album is a companion piece to Oxygen: Inhale, featuring a similar atmospheric sound and focus on melody. Notable tracks include \"Running With Giants\" and \"Push.\"",
                R.drawable.exhale));
        albums.add(new Album("Untraveled Roads", "2017",
                "This is a live album featuring performances of some of TFK's most popular songs from throughout their career. It includes a cover of \"Livin' on a Prayer\" by Bon Jovi.",
                R.drawable.untraveled_roads));
        return albums;
    }


}

