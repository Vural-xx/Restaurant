package nl.hs_hague.restaurant;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.hs_hague.restaurant.adapter.RestaurantAdapter;
import nl.hs_hague.restaurant.model.Restaurant;

public class RestaurantListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    public static final DBMaster dbmaster = new DBMaster();
    private ListView lvRestaurants;

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        if (findViewById(R.id.restaurant_detail_container) != null) {
            mTwoPane = true;
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        lvRestaurants = (ListView) findViewById(R.id.lvRestaurants);
        lvRestaurants.setAdapter(new RestaurantAdapter(this, R.layout.restaurant_list_content, dbmaster.generalsearch(this)));
        context = this;

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

        Intent searchIntent = getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this,query, Toast.LENGTH_SHORT).show();
        }

        EditText searchText = (EditText) findViewById(R.id.mysearch);
        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    EditText editText = (EditText) v;
                    List<Restaurant> restaurants = new ArrayList<Restaurant>();
                    if(editText.getText().toString().equals("")){
                        restaurants = dbmaster.generalsearch(context);
                    }else{
                        restaurants.add(dbmaster.search(editText.getText().toString(),context));
                    }

                    lvRestaurants.setAdapter(new RestaurantAdapter(context, R.layout.restaurant_list_content, restaurants));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            //Toast.makeText(this,"Menü clicked", Toast.LENGTH_SHORT).show();
            DialogFragment newFragment = new Dialog_add();
            newFragment.show(getSupportFragmentManager(), "i don't know what is this string parameter for.");

        }
        return super.onOptionsItemSelected(item);
    }

    public void notifyListView(){
        RestaurantAdapter restaurantAdapter = (RestaurantAdapter) lvRestaurants.getAdapter();
        List<Restaurant> restaurants = dbmaster.generalsearch(this);
        lvRestaurants.setAdapter(new RestaurantAdapter(context, R.layout.restaurant_list_content, restaurants));
        restaurantAdapter.notifyDataSetChanged();
    }

}
