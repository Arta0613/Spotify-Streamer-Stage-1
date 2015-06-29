package com.rushlimit.spotifystreamerstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rushlimit.spotifystreamerstage1.adapter.ArtistAdapter;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.RetrofitError;

public class MainActivity extends AppCompatActivity {

    SpotifyApi api = new SpotifyApi();
    SpotifyService spotify = api.getService();
    private ListView mArtistListView;
    private EditText artistSearch;
    private ArtistAdapter mArtistArrayAdapter;
    private ArtistsPager mArtistsPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mArtistListView = (ListView) findViewById(R.id.artistList);
        artistSearch = (EditText) findViewById(R.id.searchField);

        artistSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(count == 0))
                    new SearchArtists().execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mArtistArrayAdapter = new ArtistAdapter(this,
                R.layout.artist_search_result);
        mArtistListView.setAdapter(mArtistArrayAdapter);

        if (savedInstanceState != null) {
            artistSearch.setText(savedInstanceState.get("userSearch").toString());
        }

        mArtistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,
                        TopTenTracks.class).
                        putExtra("ArtistID", mArtistsPager.artists.items.get(position).id).
                        putExtra("ArtistName", mArtistsPager.artists.items.get(position).name));
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("userSearch", artistSearch.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class SearchArtists extends AsyncTask<String, Void, ArtistsPager> {

        @Override
        protected ArtistsPager doInBackground(String... params) {
            ArtistsPager results = null;
            try {
                results = spotify.searchArtists(params[0]);
            } catch (RetrofitError e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArtistsPager artistsPager) {
            if (artistsPager.artists.items.isEmpty())
                Toast.makeText(MainActivity.this, "No Artists Found, try another search", Toast.LENGTH_SHORT).show();
            else {
                mArtistsPager = artistsPager;
                mArtistArrayAdapter.clear();
                for (Artist artist : mArtistsPager.artists.items) {
                    mArtistArrayAdapter.add(artist);
                }
            }
        }
    }
}
