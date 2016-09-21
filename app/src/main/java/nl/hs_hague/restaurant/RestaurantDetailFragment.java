package nl.hs_hague.restaurant;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nl.hs_hague.restaurant.model.Restaurant;

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a {@link RestaurantListActivity}
 * in two-pane mode (on tablets) or a {@link RestaurantDetailActivity}
 * on handsets.
 */
public class RestaurantDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item";

    private Restaurant currentRestaurant;

    TextView tvRestaurantName;
    TextView tvRestaurantDescription;
    TextView tvRestaurantStreet;
    TextView tvRestaurantZip;
    TextView tvRestaurantPlace;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            currentRestaurant = (Restaurant) getArguments().getSerializable(ARG_ITEM);


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(currentRestaurant.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (currentRestaurant != null) {
            tvRestaurantName = (TextView) rootView.findViewById(R.id.restaurant_name);
            tvRestaurantDescription = (TextView) rootView.findViewById(R.id.restaurant_description);
            tvRestaurantStreet = (TextView) rootView.findViewById(R.id.restaurant_street);
            tvRestaurantZip = (TextView) rootView.findViewById(R.id.restaurant_zip);
            tvRestaurantPlace = (TextView) rootView.findViewById(R.id.restaurant_place);

            tvRestaurantName.setText(currentRestaurant.getName());
            if(currentRestaurant.getDescription() != null){
                tvRestaurantDescription.setText(currentRestaurant.getDescription());
            }
            if(currentRestaurant.getStreet() != null){
                tvRestaurantStreet.setText(currentRestaurant.getStreet());
            }
            if(currentRestaurant.getZip() != null){
                tvRestaurantZip.setText(currentRestaurant.getZip());
            }
            if(currentRestaurant.getPlace() != null){
                tvRestaurantPlace.setText(currentRestaurant.getPlace());
            }
        }

        Button editButton =(Button) rootView.findViewById(R.id.edit_restaurant);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Edit Button clicked", Toast.LENGTH_SHORT).show();;
            }
        });

        return rootView;
    }
}
