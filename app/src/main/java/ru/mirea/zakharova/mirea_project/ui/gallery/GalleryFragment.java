package ru.mirea.zakharova.mirea_project.ui.gallery;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.zakharova.mirea_project.R;


public class GalleryFragment extends Fragment {

    private List<Album> albums;
    private Button button;
    private Button button2;
    private Button button4;

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
        button2 = view.findViewById(R.id.button2);
        button4 = view.findViewById(R.id.button4);
        TextView textView = view.findViewById(R.id.textView2); // Получаем ссылку на TextView из макета
        String text = "Thousand Foot Krutch have kept their word after all - after the cancelled concerts in Russian capitals in the fall of 2015, the band announced new dates: in March the Canadians will play two big shows of their greatest hits in Moscow's Yotaspace club and St. Petersburg's Cosmonaut.\n" +
                "\n" +
                "One of the leading bands in alternative music and so-called \"Christian rock,\" Thousand Foot Krutch first visited Russia in 2014 with a four-city tour, where they presented their album Oxygen: Inhale. This time they plan to please the audience with their favorite hits - many fans have already appreciated the tour set list and called it a \"dream set list\"! In addition, the band also plans to surprise with fresh new releases, such as the single \"Born Again,\" which was released at the end of 2015.\n" +
                "\n" +
                "The concerts will take place:\n" +
                "20.03.2016 - Moscow - Yotaspace \n" +
                "21.03.2016 - St. Petersburg - Cosmonaut "; // Текст, который вы хотите отобразить

        textView.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_gallery_to_slideshow);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_gallery_to_player);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_gallery_to_mapr);
            }
        });
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> showCreateRecordDialog());
        return view;
    }
    private void showCreateRecordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Создание записи")
                .setItems(new CharSequence[]{"PDF", "TXT"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            createAndSaveFileAsPdf();
                            break;
                        case 1:
                            createAndSaveFileAsTxt();
                            break;
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
    private void createAndSaveFileAsPdf() {
        // Создание и сохранение файла в формате PDF
        StringBuilder sb = new StringBuilder();
        for (Album album : albums) {
            sb.append(album.getTitle()).append("\n");
            sb.append(album.getDescription()).append("\n");
            sb.append("\n");
        }
        String combinedText = sb.toString();

        // Создание документа PDF
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        // Разбивка текста на строки
        String[] lines = combinedText.split("\n");
        float y = 0;
        for (String line : lines) {
            canvas.drawText(line, 10, y, paint);
            y += paint.descent() - paint.ascent();
        }

        document.finishPage(page);

        // Указание пути и имени файла
        String fileName = "my_document.pdf";
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/" + fileName;

        // Сохранение документа в файл
        try {
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(requireContext(), "Файл сохранен: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
        }
    }


    private void createAndSaveFileAsTxt() {
        // Создание и сохранение файла в формате TXT
        StringBuilder sb = new StringBuilder();
        for (Album album : albums) {
            sb.append(album.getTitle()).append("\n");
            sb.append(album.getDescription()).append("\n");
            sb.append("\n");
        }
        String combinedText = sb.toString();

        // Указание пути и имени файла
        String fileName = "my_document.txt";
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/" + fileName;

        // Сохранение текста в файл
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(combinedText);
            writer.flush();
            writer.close();
            Toast.makeText(requireContext(), "Файл сохранен: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
        }
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

