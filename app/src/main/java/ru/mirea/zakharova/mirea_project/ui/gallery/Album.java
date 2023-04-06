package ru.mirea.zakharova.mirea_project.ui.gallery;

public class Album {
    private String title;
    private String year;
    private String description;
    private int coverResId;

    public Album(String title, String year, String description, int coverResId) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.coverResId = coverResId;
    }

    public String getTitle() {
        return title;
    }

    public int getCoverResId() {
        return coverResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverResId(int coverResId) {
        this.coverResId = coverResId;
    }

    public String getYear() {return year;}
    public String getDescription() {return description;}

    public int getImageResourceId() {return coverResId;}
}
