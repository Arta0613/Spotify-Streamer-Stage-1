package com.rushlimit.spotifystreamerstage1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rushlimit.spotifystreamerstage1.R;
import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.models.Track;

public class TopTenTrackAdapter extends ArrayAdapter<Track> {

    public TopTenTrackAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Track track = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.top_ten_track_result, parent, false);
        }

        ImageView trackImage = (ImageView) convertView.findViewById(R.id.trackImage);
        TextView songName = (TextView) convertView.findViewById(R.id.songName);
        TextView songAlbum = (TextView) convertView.findViewById(R.id.songAlbum);

        if (!track.album.images.isEmpty() && track.album.images.size() > 1)
            Picasso.with(getContext()).load(track.album.images.get(track.album.images.size() - 2).url).into(trackImage);
        else if (!track.album.images.isEmpty() && track.album.images.size() == 1)
            Picasso.with(getContext()).load(track.album.images.get(track.album.images.size() - 1).url).into(trackImage);
        else
            Picasso.with(getContext()).load("http://placehold.it/200x200?text=spotify").into(trackImage);

        songName.setText(track.name);
        songAlbum.setText(track.album.name);

        return convertView;
    }
}
