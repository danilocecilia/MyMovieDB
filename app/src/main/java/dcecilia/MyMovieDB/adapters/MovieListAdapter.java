package dcecilia.MyMovieDB.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dcecilia.MyMovieDB.R;
import dcecilia.MyMovieDB.models.Movie;
import dcecilia.MyMovieDB.models.NetworkResponse;

import com.squareup.picasso.Picasso;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342";

    private NetworkResponse response;

    public void addData(NetworkResponse response) {
        if (this.response == null) {
            this.response = response;
        } else {
            this.response.getResults().addAll(response.getResults());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_most_popular, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Movie movie = response.getResults().get(position);

        //DON'T USE PALETTE
        Picasso.with(holder.poster.getContext()).load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.poster);

        //USE PALETTE
        /*Picasso.with(holder.poster.getContext()).load(IMAGE_BASE_URL + movie.getPosterPath()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.poster.setImageBitmap(bitmap);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        holder.textBack.setBackgroundColor(palette.getVibrantColor(Color.BLACK));
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });*/
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getReleaseDate().substring(0, 4));
        /*holder.header.setText("Most Popular");
        holder.description.setText("Popular Movies");*/
    }

    @Override
    public int getItemCount() {
        return response.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView poster;
        TextView title, year; // header, description;
        View parent;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.poster);
            title = (TextView) itemView.findViewById(R.id.title);
            year = (TextView) itemView.findViewById(R.id.year);
            /*header = (TextView) itemView.findViewById(R.id.header);
            description = (TextView) itemView.findViewById(R.id.description);*/
            //parent = itemView.findViewById(R.id.parent);

            parent.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
           /* int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.putExtra("ID", response.getResults().get(position).getId());
            v.getContext().startActivity(intent);*/
        }
    }
}
