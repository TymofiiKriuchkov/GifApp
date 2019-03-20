package com.test.gifapp.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.giphy.sdk.core.models.enums.MediaType;
import com.giphy.sdk.core.network.api.GPHApi;
import com.giphy.sdk.core.network.response.ListMediaResponse;
import com.test.gifapp.R;
import com.test.gifapp.presentation.view.MainView;

import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private GPHApi client;

    public MainPresenter(GPHApi client) {
        this.client = client;
    }

    public void getGifImages(String searchString) {
        client.search(searchString, MediaType.gif, null, null, null,
                null, (result, e) -> operateResult(result));
    }

    void operateResult(ListMediaResponse result) {
        if (result == null) {
            Timber.d("result == null");
            getViewState().showMessage(R.string.no_results);
        } else {
            if (result.getData() != null) {
                Timber.d("%s", result.getData().size());
                getViewState().updateList(result.getData());
                if (result.getData().size() == 0) {
                    getViewState().showMessage(R.string.no_results);
                }
            } else {
                getViewState().showMessage(R.string.no_results);
                Timber.e("There is no result");
            }
        }
    }
}
