package com.test.gifapp.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.giphy.sdk.core.models.Media;
import com.giphy.sdk.core.network.api.GPHApiClient;
import com.test.gifapp.R;
import com.test.gifapp.adapter.GiphyAdapter;
import com.test.gifapp.presentation.MainPresenter;
import com.test.gifapp.presentation.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvGifs)
    RecyclerView rvGifs;
    @BindView(R.id.searchView)
    SearchView searchView;

    private GiphyAdapter giphyAdapter;

    @InjectPresenter
    MainPresenter mainPresenter;

    @ProvidePresenter
    MainPresenter provideMainPresenter() {
        return new MainPresenter(new GPHApiClient(getString(R.string.gif_api_key)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        giphyAdapter = new GiphyAdapter(new ArrayList<>());

        rvGifs.setLayoutManager(new LinearLayoutManager(this));
        rvGifs.setItemAnimator(new DefaultItemAnimator());
        rvGifs.setAdapter(giphyAdapter);
        rvGifs.setDrawingCacheEnabled(true);
        rvGifs.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                mainPresenter.getGifImages(query);
                return false;
            }

        });
    }

    @Override
    public void updateList(List<Media> gifArray) {
        giphyAdapter.updateAdapter(gifArray);
    }

    @Override
    public void showMessage(int messageRes) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show();
    }


}
