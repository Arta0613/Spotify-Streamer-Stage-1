package com.rushlimit.spotifystreamerstage1.model;

public class TrackAlbum {

    private String trackName;
    private String albumName;

    public TrackAlbum(String trackName, String albumName) {
        this.trackName = trackName;
        this.albumName = albumName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getAlbumName() {
        return albumName;
    }
}
