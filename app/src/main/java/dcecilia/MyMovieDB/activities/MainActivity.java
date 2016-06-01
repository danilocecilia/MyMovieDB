package dcecilia.MyMovieDB.activities;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.okhttp.Request;

import java.io.IOException;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.adapters.MovieCategoriesAdapter;
import dcecilia.MyMovieDB.adapters.MovieTopRatedAdapter;
import dcecilia.MyMovieDB.models.NetworkResponse;
import dcecilia.MyMovieDB.networking.TheMovieDBMostPopularApi;
import dcecilia.MyMovieDB.networking.TheMovieDBTopRated;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView, rvTopRated;
    private MovieCategoriesAdapter movieCategoriesAdapter;
    private MovieTopRatedAdapter movieTopRatedAdapter;
    private int currentPage = 1;
    private int currentPageTopRated = 1;
    private CardView cvMostPopular;

    @Override
    protected boolean getDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(R.color.primaryDark);

        recyclerView = (RecyclerView) findViewById(R.id.rvMostPopular);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        populateMostPopular();


        rvTopRated = (RecyclerView) findViewById(R.id.rvTopRated);
        LinearLayoutManager trLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false );
        rvTopRated.setLayoutManager(trLinearLayoutManager);
        rvTopRated.setItemAnimator(new DefaultItemAnimator());

        populateTopRated();

       /* recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                populateMostPopular();
            }
        });*/
    }

    private void populateMostPopular() {
        TheMovieDBMostPopularApi.getInstance().getMovies(currentPage, new TheMovieDBMostPopularApi.NetworkResponseListener() {
            @Override
            public void onSuccess(final NetworkResponse response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadMostPopularList(response);
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }

    private void populateTopRated(){
        TheMovieDBTopRated.getInstance().getTopRatedMovies(currentPageTopRated, new TheMovieDBTopRated.NetworkResponseListener() {
            @Override
            public void onSuccess(final NetworkResponse response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadTopRatedList(response);
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }

    private void loadMostPopularList(NetworkResponse response) {
        if (movieCategoriesAdapter == null) {
            movieCategoriesAdapter = new MovieCategoriesAdapter();
        }
        movieCategoriesAdapter.addData(response);
        currentPage++;
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(movieCategoriesAdapter);
        } else {
            movieCategoriesAdapter.notifyDataSetChanged();
        }
    }

    private void loadTopRatedList(NetworkResponse response){
        if(movieTopRatedAdapter == null){
            movieTopRatedAdapter = new MovieTopRatedAdapter();
        }
        movieTopRatedAdapter.addData(response);
        currentPageTopRated++;
        if(rvTopRated.getAdapter() == null){
            rvTopRated.setAdapter(movieTopRatedAdapter);
        }else {
            movieTopRatedAdapter.notifyDataSetChanged();
        }
    }
}
