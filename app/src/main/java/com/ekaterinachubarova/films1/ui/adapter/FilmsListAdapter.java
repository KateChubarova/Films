package com.ekaterinachubarova.films1.ui.adapter;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.ui.activity.FilmActivity;
import com.ekaterinachubarova.films1.ui.activity.MainActivity;
import com.ekaterinachubarova.films1.ui.fragment.FilmFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ekaterinachubarova on 30.09.16.
 */

public class FilmsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Film> films;
    private MainActivity context;

    public FilmsListAdapter (MainActivity context, List <Film> films) {
        this.context = context;
        this.films = films;
    }

    @Override
    public int getItemViewType(int position) {
        return films.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1, parent, false);
                return new PersonViewHolder(v);
            case VIEW_TYPE_LOADING:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
                return new LoadingViewHolder(view);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                PersonViewHolder personViewHolder = (PersonViewHolder) holder;
                personViewHolder.enName.setText(films.get(position).getNameEng());
                personViewHolder.year.setText(films.get(position).getPremiere());
                Picasso.with(context)
                        .load(films.get(position).getImage())
                        .error(R.mipmap.ic_launcher)
                        .into(personViewHolder.filmImage);
                personViewHolder.progressBar.setVisibility(View.INVISIBLE);
                personViewHolder.linearLayout.setTag(position);
                break;
            case VIEW_TYPE_LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return films == null ? 0 : films.size();
    }


    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
            progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorPrimary),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item)
        protected LinearLayout linearLayout;
        @BindView(R.id.name)
        protected TextView enName;
        @BindView(R.id.small_cover)
        protected ImageView filmImage;
        @BindView(R.id.year)
        TextView year;
        @BindView(R.id.cover_progress)
        ProgressBar progressBar;

        PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.list_item)
        public void onClick() {
            Intent intent = new Intent(context, FilmActivity.class);
            intent.putExtra(FilmFragment.FILM_PARS, films.get(getAdapterPosition()));
            filmImage.setTransitionName(context.getString(R.string.fragment_image_trans));
            Pair<View, String> pair1 = Pair.create((View) filmImage, filmImage.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(context, pair1);
            context.startActivity(intent, options.toBundle());
        }
    }
}