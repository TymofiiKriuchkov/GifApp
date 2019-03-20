package com.test.gifapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.giphy.sdk.core.models.Media;

import java.util.List;

public interface MainView extends MvpView {
    void updateList(List<Media> gifArray);

    void showMessage(int messageRes);
}