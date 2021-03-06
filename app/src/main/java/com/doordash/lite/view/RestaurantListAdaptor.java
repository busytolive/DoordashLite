package com.doordash.lite.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.doordash.lite.databinding.RestaurantListItemBinding;
import com.doordash.lite.model.Restaurant;

/**
 * Present the PagedList data of Restaurants properly in the RecyclerView
 */
public class RestaurantListAdaptor extends PagedListAdapter<Restaurant, RestaurantItemViewHolder> {

    private OnRestaurantClickEventListener mOnRestaurantClickListener;

    private static final DiffUtil.ItemCallback<Restaurant> diffCallback = new DiffUtil.ItemCallback<Restaurant>() {
        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.equals(newItem);
        }
    };

    public RestaurantListAdaptor() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RestaurantItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantListItemBinding binding = RestaurantListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RestaurantItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemViewHolder holder, int position) {
        Restaurant restaurant = getItem(position);
        if (restaurant != null) {
            holder.bind(restaurant);
            // invoke RestaurantClickListener with the restaurant information that user clicks on.
            holder.itemView.setOnClickListener(view -> {
                if (mOnRestaurantClickListener != null) {
                    mOnRestaurantClickListener.onRestaurantClick(restaurant);
                }
            });
        }
    }

    // Provide a way for client(Activity) to specify how to response user click on a item
    public void setOnRestaurantClickListener(OnRestaurantClickEventListener listener) {
        mOnRestaurantClickListener = listener;
    }

    public interface OnRestaurantClickEventListener {
        void onRestaurantClick(Restaurant restaurant);
    }
}
