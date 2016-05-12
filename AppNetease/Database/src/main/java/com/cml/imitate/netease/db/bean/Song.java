package com.cml.imitate.netease.db.bean;

/**
 * Created by cmlBeliever on 2016/5/12.
 */
public class Song {
    public int id;
    public String tilte;
    public String album;
    public String artist;
    public String url;
    public String name;
    public int duration;

    public Song() {
    }

    public Song(int id, String tilte, String album, String artist, String url, String name, int duration) {
        this.id = id;
        this.tilte = tilte;
        this.album = album;
        this.artist = artist;
        this.url = url;
        this.name = name;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", tilte='" + tilte + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
