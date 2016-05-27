package dcecilia.MyMovieDB.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.models.Movie;
import dcecilia.MyMovieDB.models.NetworkResponse;

public class MovieCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private NetworkResponse response;
    private static final int TYPE_POPULAR = 0;

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342";

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType)
        {
            case TYPE_POPULAR:
                view = inflater.inflate(R.layout.category_most_popular, parent, false);
                return new CategoryPopularViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return TYPE_POPULAR;
        }else
        return TYPE_POPULAR;
    }

    @Override
    public int getItemCount() {
        return response.getResults().size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        Movie movie = response.getResults().get(position);

        if(holder instanceof CategoryPopularViewHolder){
            CategoryPopularViewHolder vh = (CategoryPopularViewHolder)holder;
            Picasso.with(vh.poster.getContext()).load(IMAGE_BASE_URL + movie.getPosterPath()).into(vh.poster);
            vh.title.setText(movie.getTitle());
            vh.year.setText(movie.getReleaseDate().substring(0, 4));
            vh.vote_average.setText(new DecimalFormat("##.#").format(movie.getVoteAverage()));


            vh.cvMostPopular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:Call Detail Activity
                }
            });
        }
    }

    public void addData(NetworkResponse response) {
        if (this.response == null) {
            this.response = response;
        } else {
            this.response.getResults().addAll(response.getResults());
        }
    }

    private class CategoryPopularViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, year, vote_average;
        CardView cvMostPopular;

        public CategoryPopularViewHolder(View view) {
            super(view);

            cvMostPopular = (CardView)view.findViewById(R.id.cvMostPopular);
            poster = (ImageView)view.findViewById(R.id.poster);
            title = (TextView)view.findViewById(R.id.title);
            year = (TextView)view.findViewById(R.id.year);
            vote_average = (TextView)view.findViewById(R.id.vote_average);
        }
    }
}
