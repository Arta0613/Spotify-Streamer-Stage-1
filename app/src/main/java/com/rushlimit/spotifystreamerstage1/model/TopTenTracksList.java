package com.rushlimit.spotifystreamerstage1.model;

import java.util.ArrayList;
import java.util.List;

public class TopTenTracksList {

    private String artistId;
    private String artistName;
    private List<TrackAlbum> mTrackAlbums;

    public TopTenTracksList(String artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
        mTrackAlbums = new ArrayList<>();
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public List<TrackAlbum> getTrackAlbums() {
        return mTrackAlbums;
    }
}
