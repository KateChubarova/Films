package com.ekaterinachubarova.films1.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ekaterinachubarova.films1.FilmsApplication;
import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.eventbus.ReadingEvent;
import com.ekaterinachubarova.films1.eventbus.RefreshEvent;
import com.ekaterinachubarova.films1.rest.api.RetrofitService;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.service.FilmCountService;
import com.ekaterinachubarova.films1.ui.BaseFragment;
import com.ekaterinachubarova.films1.ui.activity.MainActivity;
import com.ekaterinachubarova.films1.ui.adapter.FilmsListAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public class FilmsListFragment extends BaseFragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private List<Film> filteredList = new ArrayList<>();
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
    private ActionMode actionMode;
    private FilmsListAdapter adapterForSearch;
    //InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
    private android.view.ActionMode.Callback callback = new android.view.ActionMode.Callback() {

        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context, menu);
            RelativeLayout m = (RelativeLayout) menu.findItem(
                    R.id.edt_mySearch).getActionView();
            EditText mSearchView = (EditText) m
                    .findViewById(R.id.edt_search);

            mSearchView.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    String newText = s.toString().toLowerCase();


                    final List<Film> filteredList = new ArrayList<>();

                    for (int i = 0; i < films.size(); i++) {

                        final String text = films.get(i).getNameEng().toLowerCase();
                        if (text.contains(newText)) {
                            filteredList.add(films.get(i));
                        }
                    }
                    adapterForSearch = new FilmsListAdapter((MainActivity) getActivity(), filteredList);
                    rv.setAdapter(adapterForSearch);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });
            return true;
        }

        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
            return false;
        }

        public void onDestroyActionMode(android.view.ActionMode mode) {
            actionMode = null;
            rv.setAdapter(rvAdapter);
            rvAdapter.notifyDataSetChanged();
            toggle(getActivity());
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item11) {
            if (actionMode == null) {
                actionMode = getActivity().startActionMode(callback);
                toggle(getActivity());
                //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            } else {
                actionMode.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.films_fragment, parent, false);
        ButterKnife.bind(this, v);
        setUpComponent(FilmsApplication.getAppComponent(getActivity()));

        linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);

        adapterForSearch = new FilmsListAdapter((MainActivity) getActivity(), filteredList);

        filmService.getFilms();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
            getActivity().startService(new Intent(getActivity(), FilmCountService.class));

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
    public void refreshFilms(RefreshEvent event) {
        List<Film> newFilms = random(event.getFilms());
        films.addAll(0, newFilms);
        rvAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

    public List<Film> random(List<Film> newFilms) {
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
        rvAdapter = new FilmsListAdapter((MainActivity) getActivity(), films);
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

    public static void toggle(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        } else {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
        }
    }//end method

    public void setUpComponent(AppComponent appComponent) {
        appComponent.inject(this);
    }

}
