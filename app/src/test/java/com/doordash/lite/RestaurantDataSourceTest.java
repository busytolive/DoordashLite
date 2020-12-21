package com.doordash.lite;

import androidx.paging.PositionalDataSource;

import com.doordash.lite.model.FeedResponse;
import com.doordash.lite.model.Restaurant;
import com.doordash.lite.repository.RestaurantDataSource;
import com.doordash.lite.repository.RestaurantRepository;
import com.doordash.lite.repository.RestaurantWebService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RestaurantRepository.class, Retrofit.class, retrofit2.Response.class})
public class RestaurantDataSourceTest {

    RestaurantRepository restaurantRepositoryMock;
    RestaurantWebService restaurantWebServiceMock;
    Call<FeedResponse> callMock;
    Response<FeedResponse> responseMock;
    FeedResponse feedResponseMock;
    List<Restaurant> restaurantList;
    final double LAT = 37.422740;
    final double LNG = -122.139956;
    final int OFFSET = 0;
    final int LIMIT = 50;

    @Before
    public void setUp() throws Exception {
        restaurantRepositoryMock = PowerMockito.mock(RestaurantRepository.class);
        PowerMockito.mockStatic(RestaurantRepository.class);
        PowerMockito.when(RestaurantRepository.getInstance()).thenReturn(restaurantRepositoryMock);
        restaurantWebServiceMock = PowerMockito.mock(RestaurantWebService.class);
        callMock = PowerMockito.mock(Call.class);
        PowerMockito.when(restaurantRepositoryMock.getRestaurantWebService()).thenReturn(restaurantWebServiceMock);
        PowerMockito.when(restaurantWebServiceMock.getRestaurants(LAT, LNG, OFFSET, LIMIT)).thenReturn(callMock);
        responseMock = PowerMockito.mock(Response.class);
        feedResponseMock = PowerMockito.mock(FeedResponse.class);
        PowerMockito.when(callMock.execute()).thenReturn(responseMock);
        PowerMockito.when(responseMock.isSuccessful()).thenReturn(true);
        PowerMockito.when(responseMock.body()).thenReturn(feedResponseMock);
    }


    @Test
    public void loadInitial_WhenSuccess_ShouldReturnRestaurantList() {
        restaurantList = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant();
        Restaurant restaurant2 = new Restaurant();
        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);
        PowerMockito.when(feedResponseMock.getStores()).thenReturn(restaurantList);
        PowerMockito.when(feedResponseMock.getNextOffset()).thenReturn(50);

        RestaurantDataSource dataSource = new RestaurantDataSource();
        PositionalDataSource.LoadInitialCallback<Restaurant> callback  = PowerMockito.mock(PositionalDataSource.LoadInitialCallback.class);
        PositionalDataSource.LoadInitialParams params = new PositionalDataSource.LoadInitialParams(OFFSET, LIMIT, 50, false);
        dataSource.loadInitial(params, callback);

        Mockito.verify(callback).onResult(restaurantList, 50);
    }

    @Test
    public void loadInitial_WhenNullStoreList_ShouldReturnEmptyList() {
        PowerMockito.when(feedResponseMock.getStores()).thenReturn(null);
        PowerMockito.when(feedResponseMock.getNextOffset()).thenReturn(50);

        RestaurantDataSource dataSource = new RestaurantDataSource();
        PositionalDataSource.LoadInitialCallback<Restaurant> callback  = PowerMockito.mock(PositionalDataSource.LoadInitialCallback.class);
        PositionalDataSource.LoadInitialParams params = new PositionalDataSource.LoadInitialParams(OFFSET, LIMIT, 50, false);
        dataSource.loadInitial(params, callback);

        Mockito.verify(callback).onResult(new ArrayList<>(), 50);
    }

    @Test
    public void loadInitial_WhenRetrofitFail_ShouldNotCallOnResult() {
        PowerMockito.when(responseMock.isSuccessful()).thenReturn(false);

        RestaurantDataSource dataSource = new RestaurantDataSource();
        PositionalDataSource.LoadInitialCallback<Restaurant> callback  = PowerMockito.mock(PositionalDataSource.LoadInitialCallback.class);
        PositionalDataSource.LoadInitialParams params = new PositionalDataSource.LoadInitialParams(0, 50, 50, false);
        dataSource.loadInitial(params, callback);

        Mockito.verify(callback, Mockito.never()).onResult(Mockito.anyList(), Mockito.anyInt());
    }

    @Test
    public void loadInitial_WhenRetrofitCallThrowException_ShouldNotCallOnResult() throws Exception {
        PowerMockito.when(callMock.execute()).thenThrow(new IOException());

        RestaurantDataSource dataSource = new RestaurantDataSource();
        PositionalDataSource.LoadInitialCallback<Restaurant> callback  = PowerMockito.mock(PositionalDataSource.LoadInitialCallback.class);
        PositionalDataSource.LoadInitialParams params = new PositionalDataSource.LoadInitialParams(0, 50, 50, false);
        dataSource.loadInitial(params, callback);

        Mockito.verify(callback, Mockito.never()).onResult(Mockito.anyList(), Mockito.anyInt());
    }
}
