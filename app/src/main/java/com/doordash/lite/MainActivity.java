package com.doordash.lite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doordash.lite.databinding.ActivityMainBinding;
import com.doordash.lite.view.RestaurantListAdaptor;
import com.doordash.lite.viewmodel.RestaurantListViewModel;

import static com.doordash.lite.RestaurantDetailActivity.INTENT_EXTRA_RESTAURANT_ID;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RestaurantListAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adaptor = new RestaurantListAdaptor();
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adaptor);
        // launch RestaurantDetailActivity with id of the clicked restaurant
        adaptor.setOnRestaurantClickListener(restaurant -> {
            Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
            intent.putExtra(INTENT_EXTRA_RESTAURANT_ID, restaurant.getId());
            startActivity(intent);
        });

        // Observe restaurant list LiveData. when list is updated, submit to RecyclerView to display.
        RestaurantListViewModel viewModel = new ViewModelProvider(this).get(RestaurantListViewModel.class);
        viewModel.getRestaurantList().observe(this, restaurants -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            adaptor.submitList(restaurants);
        });
    }
}
