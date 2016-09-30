package com.ekaterinachubarova.films1.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ekaterinachubarova.films1.FilmsApplication;
import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.eventbus.ReadingEvent;
import com.ekaterinachubarova.films1.eventbus.RefreshEvent;
import com.ekaterinachubarova.films1.rest.api.RetrofitService;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.ui.BaseFragment;
import com.ekaterinachubarova.films1.ui.activity.MainActivity;
import com.ekaterinachubarova.films1.ui.adapter.FilmsListAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public class FilmsListFragment extends BaseFragment{
    private static final String TAG = BaseFragment.class.getSimpleName();

    @BindView(R.id.rv)
    protected RecyclerView rv;
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    protected RetrofitService filmService;

    private FilmsListAdapter rvAdapter;
    private List<Film> films;
    private boolean isFirstLoading = true;

    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean isRefreshing;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.films_fragment, parent, false);
        ButterKnife.bind(this, v);
        setUpComponent(FilmsApplication.getAppComponent(getActivity()));

        linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);

        filmService.getFilms();

        return v;
    }

    private void onLoadMore() {
        films.add(null);
        rvAdapter.notifyItemInserted(films.size() - 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                films.remove(films.size() - 1);
                filmService.getFilms();
            }
        }, 1000);
    }

    @Subscribe
    public void onEvent(ReadingEvent event) {
        if (isFirstLoading) {
            isFirstLoading = false;
            setAdapter(event);

        } else {
            setChanges(event.getFilms());
        }
    }

    public void setChanges(List<Film> newFilms) {
        films.addAll(newFilms);
        rvAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Subscribe
    public void refreshFilms (RefreshEvent event) {
        List<Film> newFilms = random(event.getFilms());
        films.addAll(0, newFilms);
        rvAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public List<Film> random (List<Film> newFilms){
        Random random = new Random();
        return newFilms.subList(random.nextInt(newFilms.size()), newFilms.size());
    }



    public void setAdapter(ReadingEvent event) {
        if (event.isFlagNetwork() == !ReadingEvent.INFORMATION_FROM_NETWORK) {
            Toast.makeText(getActivity(), "Loading data is failed. The information is old.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "The information is updated.", Toast.LENGTH_LONG).show();
        }
        films = event.getFilms();
        rvAdapter = new FilmsListAdapter((MainActivity)getActivity(), films);
        rv.setAdapter(rvAdapter);

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
                    onLoadMore();
                    isLoading = true;
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        filmService.getRefreshFilms();
                    }
                }, 1000);

            }
        });
    }

    public void setUpComponent(AppComponent appComponent) {
        appComponent.inject(this);
    }

}
