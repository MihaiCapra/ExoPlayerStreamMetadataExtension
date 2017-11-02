package net.droidlabs.exoplayer.extensions.streammetadata.example;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import net.droidlabs.exoplayer.extensions.streammetadata.MetadataListener;
import net.droidlabs.exoplayer.extensions.streammetadata.OkHttpDataSourceFactory;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements MetadataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultTrackSelector());

        player.setPlayWhenReady(true);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        OkHttpDataSourceFactory okHttpDataSourceFactory = new OkHttpDataSourceFactory(
                new OkHttpClient(),
                "SomeUserAgent",
                bandwidthMeter, this);

        DefaultDataSourceFactory factory = new DefaultDataSourceFactory(this, bandwidthMeter,
                okHttpDataSourceFactory);

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse("http://195.150.20.9/RMFFM48"),
                factory,
                new DefaultExtractorsFactory(), handler, null);

        player.prepare(mediaSource, true, true);
    }

    @Override
    public void onMetadataRetrieved(String key, String value) {
        Log.d("TAG", value);
    }
}
