package nl.hs_hague.restaurant;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import nl.hs_hague.restaurant.model.Restaurant;

public class Dialog_add extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
//        Button accept = (Button)getDialog().findViewById(R.id.accept);


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_dialog_add, null))
                // Add action buttons
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editTextName = (EditText)getDialog().findViewById(R.id.editText1);
                        EditText editTextStreet = (EditText)getDialog().findViewById(R.id.editText2);
                        EditText editTextPlace = (EditText)getDialog().findViewById(R.id.editText3);
                        EditText editTextZip = (EditText)getDialog().findViewById(R.id.editText4);
                        EditText editTextPath = (EditText)getDialog().findViewById(R.id.editText5);
                        EditText editTextComments = (EditText)getDialog().findViewById(R.id.editText6);


                        String name = editTextName.getText().toString();
                        String street = editTextStreet.getText().toString();
                        String place = editTextPlace.getText().toString();
                        String zip = editTextZip.getText().toString();
                        String path = editTextPath.getText().toString();
                        String comments = editTextComments.getText().toString();

                        Restaurant myRestaurant = new Restaurant(1, name, street, place, zip, path, comments);
                        DBMaster db = new DBMaster();
                        db.register(myRestaurant, getContext());


                        ToasterClass toaster = new ToasterClass(getContext());
                        toaster.addedToast(name, street, place, zip, path, comments);

                        RestaurantListActivity restaurantListActivity = (RestaurantListActivity) getActivity();
                        restaurantListActivity.notifyListView();


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();

                    }
                });
        return builder.create();
    }
}
