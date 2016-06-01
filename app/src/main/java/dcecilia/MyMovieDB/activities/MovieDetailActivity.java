package dcecilia.MyMovieDB.activities;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.adapters.MovieDetailListAdapter;
import dcecilia.MyMovieDB.models.MovieDetails;
import dcecilia.MyMovieDB.networking.TheMovieDBDetailsApi;

/**
 * Created by Danilo on 27/05/2016.
 */
public class MovieDetailActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private int mActionBarSize;

    private float MAX_TEXT_SCALE_DELTA = 0.3f;

    private MovieDetailListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_detail);

        mActionBarSize = getActionBarSize();

        ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.recycler);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        setDummyDataWithHeader(recyclerView);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image

    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
    }

    @Override
    protected boolean getDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.movie_detail;
    }

    protected void setDummyDataWithHeader(final RecyclerView recyclerView) {
        int id = getIntent().getIntExtra("ID", 0);

        TheMovieDBDetailsApi.getInstance().getMovieDetails(id, new TheMovieDBDetailsApi.NetworkResponseListener() {
            @Override
            public void onSuccess(final MovieDetails details) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new MovieDetailListAdapter(details);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }
}
