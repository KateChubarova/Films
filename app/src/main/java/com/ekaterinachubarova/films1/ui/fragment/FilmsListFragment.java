package com.ekaterinachubarova.films1.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaterinachubarova.films1.FilmsApplication;
import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.eventbus.ReadingEvent;
import com.ekaterinachubarova.films1.listener.OnLoadListener;
import com.ekaterinachubarova.films1.rest.api.RetrofitService;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.ui.BaseFragment;
import com.ekaterinachubarova.films1.ui.activity.FilmActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public class FilmsListFragment extends BaseFragment{
    @BindView(R.id.rv) protected RecyclerView rv;
    private RVAdapter rvAdapter;
    private List<Film> films;
    private boolean isFirstLoading = true;
    public static final String TAG = "FilmsListFragment";
    private ActionMode mActionMode;

    @Inject
    protected RetrofitService filmService;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback()
    {

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            // TODO Auto-generated method stub
            switch(item.getItemId())
            {
                case R.id.action_search:
                    return true;
            }
            return false;
        }

        //// Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            // TODO Auto-generated method stub
            mode.getMenuInflater().inflate(R.menu.search_view, menu);
            return true;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
            // TODO Auto-generated method stub
            mActionMode = null;
        }

        //// Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            // TODO Auto-generated method stub
            return false;
        }
    };

    @Override
    public View onCreateView (final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.films_fragment, parent, false);
        ButterKnife.bind(this, v);
        setUpComponent(FilmsApplication.getAppComponent(this));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        filmService.getFilms();

        return v;
    }

    @Subscribe
    public void onEvent (ReadingEvent event) {
        if (isFirstLoading) {
            isFirstLoading = false;
            setAdapter(event);

        } else  {
            setChanges (event.getFilms());
        }
    }

    public void setChanges (List<Film> newFilms) {
        films.addAll(newFilms);
        rvAdapter.notifyDataSetChanged();
        rvAdapter.setLoaded();
    }


    public void setAdapter (ReadingEvent event) {
        if (event.isFlag() == ReadingEvent.INFORMATION_FROM_DATABASE) {
            Toast.makeText(getActivity(), "Loading data is failed. The information is old.", Toast.LENGTH_LONG).show();
        }
        if (event.isFlag() == ReadingEvent.INFORMATION_FROM_NETWORK) {
            Toast.makeText(getActivity(), "The information is updated.", Toast.LENGTH_LONG).show();
        }
        films = event.getFilms();
        rvAdapter = new RVAdapter();
        rv.setAdapter(rvAdapter);

        rvAdapter.setOnLoadMoreListener(new OnLoadListener() {
            @Override
            public void onLoadMore() {
                films.add(null);
                rvAdapter.notifyItemInserted(films.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        films.remove(films.size() - 1);
                        filmService.getFilms();
                    }
                }, 5000);
            }
        });
    }

    public void setUpComponent(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        private OnLoadListener mOnLoadMoreListener;

        private boolean isLoading;
        private int visibleThreshold = 1;
        private int lastVisibleItem, totalItemCount;

        public RVAdapter(){

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy <= 0) {
                        return;
                    }
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
        }

        public void setOnLoadMoreListener(OnLoadListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
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
            switch (rvAdapter.getItemViewType(position)){
                case VIEW_TYPE_ITEM:
                    PersonViewHolder personViewHolder = (PersonViewHolder) holder;
                    personViewHolder.enName.setText(films.get(position).getNameEng());
                    personViewHolder.year.setText(films.get(position).getPremiere());
                    Picasso.with(getActivity())
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

        public class LoadingViewHolder extends RecyclerView.ViewHolder {
            public ProgressBar progressBar;

            public LoadingViewHolder(View itemView) {
                super(itemView);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
                progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary),
                        android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }

        public class PersonViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.list_item) protected LinearLayout linearLayout;
            @BindView(R.id.name) protected TextView enName;
            @BindView(R.id.small_cover) protected ImageView filmImage;
            @BindView(R.id.year) TextView year;
            @BindView(R.id.cover_progress) ProgressBar progressBar;
            PersonViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
            @OnClick(R.id.list_item)
            public void onClick() {
                Intent intent = new Intent(getActivity(), FilmActivity.class);
                intent.putExtra(FilmFragment.FILM_PARS, films.get(getAdapterPosition()));
                filmImage.setTransitionName(getString(R.string.fragment_image_trans));
                Pair<View, String> pair1 = Pair.create((View) filmImage, filmImage.getTransitionName());
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), pair1);
                startActivity(intent, options.toBundle());
            }
        }

        @Override
        public int getItemCount() {
            return films == null ? 0 : films.size();
        }
        public void setLoaded() {
            isLoading = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


}
