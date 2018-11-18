package app.com.bakingapp.fragment;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import app.com.bakingapp.R;
import app.com.bakingapp.StepsActivity;
import app.com.bakingapp.model.Step;

public class DetailStepFragment extends Fragment {

    TextView desc;
    ScrollView scroll;
    SimpleExoPlayerView playerView;
    long playerPosition;
    boolean play;

    public static final String POSTION ="postion.player";
    public static final String PLAY_WHen ="play.player";
    SimpleExoPlayer player;
    public static final float LARGE_SCREEN = 600;

    public static DetailStepFragment factory(Step step){
        DetailStepFragment frag = new DetailStepFragment();
        Bundle b = new Bundle();
        b.putParcelable(StepsActivity.STEP_EXTRA, step);
        frag.setArguments(b);
        return frag;
    }

    public DetailStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState == null){
            playerPosition = 0;
        }else{
            playerPosition = savedInstanceState.getLong(POSTION, 0);
        }

        // Inflate the layout for this fragment
        Step step = getArguments().getParcelable(StepsActivity.STEP_EXTRA);
        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);
        desc = (TextView) rootView.findViewById(R.id.description_tv);
        playerView = (SimpleExoPlayerView)rootView.findViewById(R.id.exoplayer);
        scroll =(ScrollView) rootView.findViewById(R.id.scroll);
        desc.setText(step.getShortDescription()+"\n\n"+step.getDescription());

        if(!step.getVideoURL().equals("")){
            initializePlayer(playerView, getContext(), Uri.parse(step.getVideoURL()));
        }else if (!step.getThumbnailURL().equals("")){
            initializePlayer(playerView, getContext(), Uri.parse(step.getThumbnailURL()));
        }else{
            playerView.setVisibility(View.GONE);
        }
        updateView();
        return rootView;
    }
    public void initializePlayer(SimpleExoPlayerView playerView,  Context context, Uri uri){
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

       player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        playerView.setPlayer(player);


        DefaultHttpDataSourceFactory dataSourceFactory =
                new DefaultHttpDataSourceFactory("BakingApp");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        ExtractorMediaSource videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);

        player.prepare(videoSource);

        player.setPlayWhenReady(true);
        player.seekTo(playerPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateView();
    }

    public float getScreenWidthDp(){

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics  = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = getResources().getDisplayMetrics().density;
        return displayMetrics.widthPixels / density ;
    }

    public void updateView(){
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE && getScreenWidthDp()<LARGE_SCREEN ){

            if(scroll != null){
                scroll.setVisibility(View.GONE);
            }
        }else{
            if(scroll != null){
                scroll.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (player!=null) {
                player.release();
                player = null;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (player!=null) {
                player.release();
                player = null;
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(player != null){
            outState.putLong(POSTION, player.getContentPosition());
        }
    }
}
