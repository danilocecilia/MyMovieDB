package dcecilia.MyMovieDB.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.models.MovieDetails;

public class MovieDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private LayoutInflater mInflater;
    private MovieDetails movieDetails;

    public MovieDetailListAdapter(MovieDetails details) {
        this.movieDetails = details;
    }

    @Override
    public int getItemCount() {
        return movieDetails.getGenres().size() + movieDetails.getCredits().getCast().size() + 5;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        /*if (viewType == VIEW_TYPE_HEADER) {
            final View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header, null);

            headerView.post(new Runnable() {
                @Override
                public void run() {
                    headerView.getLayoutParams().height = mFlexibleSpaceImageHeight;
                }
            });
            return new HeaderViewHolder(mHeaderView);
        } else {
        */
            return new ItemViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, parent, false));
        //}
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).textView.setText(movieDetails.getTitle());
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(android.R.id.text1);
        }
    }
}
