package nl.hs_hague.restaurant;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import nl.hs_hague.restaurant.adapter.RestaurantAdapter;
import nl.hs_hague.restaurant.model.Restaurant;

public class RestaurantListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    public static final DBMaster dbmaster = new DBMaster();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        if (findViewById(R.id.restaurant_detail_container) != null) {
            mTwoPane = true;
        }

     dbmaster.register(new Restaurant(1,"rest1","{ñlcmpwdc","place","street","zip code","wldmdc"),this);
        dbmaster.register(new Restaurant(2,"cousine","{ñlcmpwdc","place","street","zip code","wldmdc"),this);
        dbmaster.register(new Restaurant(3,"template","{ñlcmpwdc","place","street","zip code","wldmdc"),this);

        ListView lvRestaurants = (ListView) findViewById(R.id.lvRestaurants);
        lvRestaurants.setAdapter(new RestaurantAdapter(this, R.layout.restaurant_list_content, dbmaster.generalsearch(this)));

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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        // Associate searchable configuration with the SearchView
        final SearchView searchView= (SearchView) menu.findItem(R.id.mysearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener( ) {
            @Override
            public boolean   onQueryTextChange( String newText ) {//
                return true;
            }

            @Override
            public boolean   onQueryTextSubmit(String query) {
               query= new String ((String) searchView.getQuery());
                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
               return true;
            }
        });

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
            //DBMaster dbMaster = new DBMaster();
            //dbMaster.register(generateDummyData().get(0),this);
           // dbMaster.generalsearch(this);
            Toast.makeText(this,"Menü clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
