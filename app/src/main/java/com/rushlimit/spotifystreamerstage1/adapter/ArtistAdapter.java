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

import kaaes.spotify.webapi.android.models.Artist;

/*
    https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView

    Used above link to refresh on Custom ArrayAdapter
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {

    public ArtistAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Artist artist = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_search_result, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.artistImage);
        TextView textView = (TextView) convertView.findViewById(R.id.artistName);

        if (!artist.images.isEmpty() && artist.images.size() > 1)
            Picasso.with(getContext()).load(artist.images.get(artist.images.size() - 2).url).into(imageView);
        else if (!artist.images.isEmpty() && artist.images.size() == 1)
            Picasso.with(getContext()).load(artist.images.get(artist.images.size() - 1).url).into(imageView);
        else
            Picasso.with(getContext()).load("http://placehold.it/200x200?text=spotify").into(imageView);
        textView.setText(artist.name);

        return convertView;
    }
}
