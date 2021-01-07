package com.doordash.lite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.doordash.lite.databinding.ActivityRestaurantDetailBinding;
import com.doordash.lite.viewmodel.RestaurantDetailViewModel;
import com.squareup.picasso.Picasso;

public class RestaurantDetailActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_RESTAURANT_ID = "id";
    private static final String SAVED_RESTAURANT_ID = "saved_id";
    private int id;
    private ActivityRestaurantDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // restore restaurant id from configuration change and system-initiated process death.
        if (!getIntent().hasExtra(INTENT_EXTRA_RESTAURANT_ID) && savedInstanceState != null) {
            id = savedInstanceState.getInt(SAVED_RESTAURANT_ID);
        } else {
            id = getIntent().getIntExtra(INTENT_EXTRA_RESTAURANT_ID, 0);
        }

        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RestaurantDetailViewModel restaurantDetailViewModel = new ViewModelProvider(this).get(RestaurantDetailViewModel.class);
        restaurantDetailViewModel.getRestaurantDetail(id).observe(this, restaurant -> {
            if (restaurant != null) {
                binding.contentProgressBar.setVisibility(View.INVISIBLE);
                binding.restaurantDetailsName.setText(restaurant.getName());
                binding.restaurantDetailsDescription.setText(restaurant.getDescription());
                binding.restaurantDetailsStatus.setText(restaurant.getStatus());
                binding.restaurantDetailsRating.setText(restaurant.getAverageRating() + " / 5.0" + " (" + restaurant.getNumberOfRatings() + " ratings)");
                binding.restaurantDetailsDeliveryFee.setText(String.format("$%.2f", restaurant.getDeliveryFee() / 100.0f));
                Picasso.with(this)
                        .load(restaurant.getCoverImgUrl())
                        .placeholder(android.R.color.darker_gray)
                        .error(android.R.color.darker_gray)
                        .fit()
                        .centerCrop()
                        .into(binding.restaurantImageView);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current restaurant id
        savedInstanceState.putInt(SAVED_RESTAURANT_ID, id);
        super.onSaveInstanceState(savedInstanceState);
    }
}
