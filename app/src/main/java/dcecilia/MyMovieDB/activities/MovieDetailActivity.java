package dcecilia.MyMovieDB.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.adapters.MovieDetailListAdapter;
import dcecilia.MyMovieDB.models.MovieDetails;
import dcecilia.MyMovieDB.networking.TheMovieDBDetailsApi;

import com.google.android.youtube.player.YouTubeIntents;

/**
 * Created by Danilo on 27/05/2016.
 */
public class MovieDetailActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private MovieDetailListAdapter movieDetailListAdapter;
    private ImageView backdrop;
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780";
    private Toolbar mToolbar;
    private ImageButton playVideoButton;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        toolbarSetup();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        backdrop = (ImageView)findViewById(R.id.image_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        playVideoButton = (ImageButton)findViewById(R.id.btnPlayVideo);

        setData(recyclerView);
    }

    private void toolbarSetup() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(getDisplayHomeAsUpEnabled());
        } else {
            throw new NullPointerException("Layout must contain a toolbar with id 'toolbar'");
        }
    }

    @Override
    protected boolean getDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    protected void setData(final RecyclerView recyclerView) {
        int id = getIntent().getIntExtra("ID", 0);

        TheMovieDBDetailsApi.getInstance().getMovieDetails(id, new TheMovieDBDetailsApi.NetworkResponseListener() {
            @Override
            public void onSuccess(final MovieDetails details) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(recyclerView.getAdapter() == null){
                            recyclerView.setAdapter(new MovieDetailListAdapter(details));
                        } else {
                            movieDetailListAdapter.notifyDataSetChanged();
                        }

                        Picasso.with(recyclerView.getContext()).load(IMAGE_BASE_URL + details.getBackdrop_path()).into(backdrop);

                        /**
                         * Click Button to Watch the Tralier
                         */
                        playVideoButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(v.getContext(), details.getTrailers().getYoutube().get(0).getSource(), true, false);
                                startActivity(intent);
                            }
                        });

                        /**
                         * Hack to remove Title on the Collapsing Panel
                         */
                        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                            boolean isShow = false;
                            int scrollRange = -1;

                            @Override
                            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                                if (scrollRange == -1) {
                                    scrollRange = appBarLayout.getTotalScrollRange();
                                }
                                if (scrollRange + verticalOffset == 0) {
                                    collapsingToolbarLayout.setTitle("");
                                    isShow = true;
                                } else if(isShow) {
                                    collapsingToolbarLayout.setTitle("");
                                    isShow = false;
                                }

                                collapsingToolbarLayout.setTitle("");
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }
}
