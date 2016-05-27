package dcecilia.MyMovieDB;

import android.app.Application;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import dcecilia.MyMovieDB.networking.Key;
import dcecilia.MyMovieDB.networking.TheMovieDBMostPopularApi;
import dcecilia.MyMovieDB.networking.TheMovieDBDetailsApi;
import dcecilia.MyMovieDB.networking.TheMovieDBTopRated;

public class MyMovieDBApplication  extends Application{
    private static MyMovieDBApplication mInstance;

    public final OkHttpClient client = new OkHttpClient();

    public static synchronized MyMovieDBApplication getInstance(){return mInstance;}

    @Override
    public void onCreate(){
        super.onCreate();

        mInstance = this;

        int cacheSize = 10 * 1024 * 1024;

        Cache cache = null;

        try{
            cache = new Cache(getCacheDir(), cacheSize);
            client.setCache(cache);

        }catch  (IOException e){
            e.printStackTrace();
        }

        TheMovieDBDetailsApi.create(Key.THE_MOVIE_DB);
        TheMovieDBMostPopularApi.create(Key.THE_MOVIE_DB);
        TheMovieDBTopRated.create(Key.THE_MOVIE_DB);
    }

}
