package nl.hs_hague.restaurant.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.hs_hague.restaurant.R;
import nl.hs_hague.restaurant.RestaurantListActivity;
import nl.hs_hague.restaurant.model.Restaurant;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Hier wird die einzelne Reihe inflated
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
        }
        TextView tvRestaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);
        ImageView imageView =(ImageView) convertView.findViewById(R.id.lvImage);
        final Restaurant currentRestaurant = (Restaurant) restaurants.get(position);
        tvRestaurantName.setText(currentRestaurant.getName());
        if(currentRestaurant.getImage() != null){
            imageView.setImageBitmap(currentRestaurant.getImage());
        }

        ImageView trashCan=(ImageView) convertView.findViewById(R.id.lvImage2);
        trashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                new AlertDialog.Builder(context)
                        .setTitle("Delete Restaurant")
                        .setMessage("Do you really want to delete this Item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()  //delete item
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position =restaurants.indexOf(currentRestaurant);
                                restaurants.remove(position);
                                notifyDataSetChanged();
                                RestaurantListActivity.dbmaster.delete(currentRestaurant.getName(),context);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        notifyDataSetChanged();
        return convertView;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }
}