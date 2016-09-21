package com.ekaterinachubarova.films1.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ekaterinachubarova.films1.FilmsApplication;
import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.rest.api.RetrofitService;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;
import com.ekaterinachubarova.films1.ui.BaseFragment;
import com.ekaterinachubarova.films1.ui.activity.FilmActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public class MainFragment extends BaseFragment{
    @BindView(R.id.rv)
    RecyclerView rv;
    private RVAdapter rvAdapter;
    private List<Film> films;

    @Inject
    protected RetrofitService filmService;

    @Override
    public View onCreateView (final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.films_fragment, parent, false);
        ButterKnife.bind(this, v);
        setUpComponent(FilmsApplication.getAppComponent(this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        filmService.getFilms();
        setHasOptionsMenu(true);
        return v;
    }

    @Subscribe
    public void setAdapter (FilmsLab message) {
        films = message.getFilms();
        rvAdapter = new RVAdapter();
        rv.setAdapter(rvAdapter);
    }


    public void setUpComponent(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

        public RVAdapter(){

        }

        @Override
        public PersonViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1, parent, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder holder, int position) {
            holder.enName.setText(films.get(position).getNameEng());
            holder.year.setText(films.get(position).getPremiere());
            Picasso.with(getActivity())
                    .load(films.get(position).getImageUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(holder.filmImage);
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.linearLayout.setTag(position);


        }

        @Override
        public int getItemCount() {
            return films.size();
        }




        public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            LinearLayout linearLayout;
            TextView enName;
            ImageView filmImage;
            TextView year;
            ProgressBar progressBar;
            PersonViewHolder(View itemView) {
                super(itemView);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.list_item);
                enName = (TextView)itemView.findViewById(R.id.name);
                filmImage = (ImageView) itemView.findViewById(R.id.small_cover);
                year = (TextView) itemView.findViewById(R.id.year);
                progressBar = (ProgressBar) itemView.findViewById(R.id.cover_progress);

                linearLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FilmActivity.class);
                intent.putExtra(FilmFragment.FILM_PARS, films.get(getAdapterPosition()));

                filmImage.setTransitionName(getString(R.string.fragment_image_trans));
                Pair<View, String> pair1 = Pair.create((View) filmImage, filmImage.getTransitionName());
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), pair1);

                startActivity(intent, options.toBundle());


            }
        }
    }


}
