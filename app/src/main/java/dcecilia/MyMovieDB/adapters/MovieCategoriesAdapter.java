package dcecilia.MyMovieDB.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.activities.MovieDetailActivity;
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
        ImageView poster, overflow;
        TextView title, year, vote_average;
        CardView cvMostPopular;

        public CategoryPopularViewHolder(View view) {
            super(view);

            cvMostPopular = (CardView)view.findViewById(R.id.cvMostPopular);
            poster = (ImageView)view.findViewById(R.id.poster);
            title = (TextView)view.findViewById(R.id.title);
            year = (TextView)view.findViewById(R.id.year);
            vote_average = (TextView)view.findViewById(R.id.vote_average);
            overflow = (ImageView)view.findViewById(R.id.overflow);
            
            if( overflow != null ){
                overflow.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(overflow);
                    }
                });
            }

            /**
             * Call Detail Activity
             */
            cvMostPopular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                    intent.putExtra("ID", response.getResults().get(position).getId());
                    v.getContext().startActivity(intent);
                }
            });
        }

        /**
         * Showing popup menu when tapping on 3 dots
         */
        private void showPopupMenu(final View view) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_movie_item, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                 @Override
                                                 public boolean onMenuItemClick(MenuItem item) {

                                                     switch (item.getItemId()) {
                                                         case R.id.action_movie_rate:
                                                             Toast.makeText(view.getContext(), "Movie Rate", Toast.LENGTH_SHORT).show();
                                                             return true;
                                                         default:
                                                     }
                                                     return false;
                                                 }
                });
            popup.show();
        }
    }
}
