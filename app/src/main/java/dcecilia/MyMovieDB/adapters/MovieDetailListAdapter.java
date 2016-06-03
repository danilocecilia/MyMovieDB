package dcecilia.MyMovieDB.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.models.MovieDetails;

public class MovieDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_DETAILS_HEADER = 1;

    private MovieDetails movieDetails;

    public MovieDetailListAdapter(MovieDetails details) {
        this.movieDetails = details;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
            return VIEW_TYPE_HEADER;
        //else if(position == 1)
        //  return VIEW_DETAILS_HEADER;

        return VIEW_TYPE_HEADER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                view = inflater.inflate(R.layout.details_header, parent, false);
                return new HeaderViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder vh = (HeaderViewHolder)viewHolder;
            //Picasso.with(vh.poster.getContext()).load("http://image.tmdb.org/t/p/w342" + movieDetails.getPoster_path()).into(vh.poster);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            //poster = (ImageView) itemView.findViewById(R.id.poster);
        }
    }
}
