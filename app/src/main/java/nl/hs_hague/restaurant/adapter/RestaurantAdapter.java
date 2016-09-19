package nl.hs_hague.restaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.hs_hague.restaurant.R;
import nl.hs_hague.restaurant.model.Restaurant;

/**
 * Created by vural on 18.09.16.
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    private LayoutInflater inflater;
    private int resource;
    private List<Restaurant> restaurants;

    public RestaurantAdapter(Context context, int resource, List<Restaurant> roomInfos) {
        super(context, resource, roomInfos);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.restaurants = roomInfos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Hier wird die einzelne Reihe inflated
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
        }
        TextView tvRestaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);
        Restaurant currentRestaurant = (Restaurant) restaurants.get(position);
        tvRestaurantName.setText(currentRestaurant.getName());
        notifyDataSetChanged();
        return convertView;
    }
}