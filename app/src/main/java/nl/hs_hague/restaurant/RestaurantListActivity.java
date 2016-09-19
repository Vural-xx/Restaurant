package nl.hs_hague.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.hs_hague.restaurant.adapter.RestaurantAdapter;
import nl.hs_hague.restaurant.model.Restaurant;

/**
 * An activity representing a list of Restaurants. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RestaurantDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RestaurantListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.restaurant_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        ListView lvRestaurants = (ListView) findViewById(R.id.lvRestaurants);
        lvRestaurants.setAdapter(new RestaurantAdapter(this, R.layout.restaurant_list_content, generateDummyData()));

        lvRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Restaurant restaurant = (Restaurant) parent.getItemAtPosition(position);
                if (mTwoPane) {
                    RestaurantDetailFragment fragment = new RestaurantDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(RestaurantDetailFragment.ARG_ITEM,restaurant);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.restaurant_detail_container, fragment)
                            .commit();

                } else {
                    // Beim klciken auf das Listenelement wird die neue Activity geöffnet
                    Intent intent = new Intent(getApplicationContext(), RestaurantDetailActivity.class);
                    intent.putExtra(RestaurantDetailFragment.ARG_ITEM, restaurant);
                    startActivity(intent);
                }
            }
        });
    }

    public List<Restaurant> generateDummyData(){
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant(1, "Siezo"));
        restaurants.add(new Restaurant(2, "KFC"));
        return  restaurants;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, RestaurantListActivity.class));
            return true;
        }else if (id == R.id.action_create){
            // Here comes create Dialog
            Toast.makeText(this,"Menü clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
