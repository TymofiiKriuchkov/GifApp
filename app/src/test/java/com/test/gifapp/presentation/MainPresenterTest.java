package com.test.gifapp.presentation;

import com.giphy.sdk.core.models.Media;
import com.giphy.sdk.core.network.api.GPHApiClient;
import com.giphy.sdk.core.network.response.ListMediaResponse;
import com.test.gifapp.R;
import com.test.gifapp.presentation.view.MainView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class MainPresenterTest {

    private MainPresenter mainPresenter;
    private List<Media> mediaList;

    @Mock
    ListMediaResponse result;

    @Mock
    MainView$$State mainView$$State;

    @Mock
    GPHApiClient gphApiClient;


    @Before
    public void init(){
        mainPresenter = new MainPresenter(gphApiClient);
        mainPresenter.setViewState(mainView$$State);
        mediaList = new ArrayList<>();
    }

    @Test
    public void checkNullResult() {
        mainPresenter.operateResult(null);
        verify(mainView$$State).showMessage(R.string.no_results);
    }

    @Test
    public void checkNullResultData() {
        when(result.getData()).thenReturn(mediaList);
        mainPresenter.operateResult(result);
        verify(mainView$$State).updateList(mediaList);
    }

    @Test
    public void checkEmptySize() {
        when(result.getData()).thenReturn(mediaList);
        mainPresenter.operateResult(result);
        verify(mainView$$State).showMessage(R.string.no_results);
    }

    @Test
    public void resultDataIsNull() {
        when(result.getData()).thenReturn(null);
        mainPresenter.operateResult(result);
        verify(mainView$$State).showMessage(R.string.no_results);
    }
}