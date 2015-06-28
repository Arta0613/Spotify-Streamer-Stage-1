package com.rushlimit.spotifystreamerstage1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rushlimit.spotifystreamerstage1.adapter.TopTenTrackAdapter;
import com.rushlimit.spotifystreamerstage1.model.TopTenTracksList;
import com.rushlimit.spotifystreamerstage1.model.TrackAlbum;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

public class TopTenTracks extends AppCompatActivity {

    SpotifyApi api = new SpotifyApi();
    SpotifyService spotify = api.getService();
    private Tracks mTracks;
    private ListView mTrackListView;
    private TopTenTrackAdapter mTopTenTrackAdapter;
    private TopTenTracksList mTopTenTracksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_tracks);

        mTrackListView = (ListView) findViewById(R.id.topTenList);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Top 10 Tracks");
            actionBar.setSubtitle(getIntent().getStringExtra("ArtistName"));
        }

        new TopTen().execute(getIntent().getStringExtra("ArtistID"));
        mTopTenTrackAdapter = new TopTenTrackAdapter(this, R.layout.top_ten_track_result);
        mTrackListView.setAdapter(mTopTenTrackAdapter);
        mTrackListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TopTenTracks.this, "Feature coming soon...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_ten_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class TopTen extends AsyncTask<String, Void, Tracks> {

        @Override
        protected Tracks doInBackground(String... params) {
            Map<String, Object> countryMap = new HashMap<>();
            countryMap.put("country", Locale.getDefault().getCountry());
            return spotify.getArtistTopTrack(params[0], countryMap);
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            if (tracks.tracks.isEmpty())
                Toast.makeText(TopTenTracks.this, "No Tracks Found, try another artist", Toast.LENGTH_SHORT).show();
            else {
                mTracks = tracks;
                mTopTenTrackAdapter.clear();
                mTopTenTracksList = null;
                mTopTenTracksList = new TopTenTracksList(
                        getIntent().getStringExtra("ArtistID"),
                        getIntent().getStringExtra("ArtistName"));
                for (Track track : tracks.tracks) {
                    mTopTenTrackAdapter.add(track);
                    mTopTenTracksList.getTrackAlbums().add(new TrackAlbum(track.name, track.album.name));
                }
            }
        }
    }
}
