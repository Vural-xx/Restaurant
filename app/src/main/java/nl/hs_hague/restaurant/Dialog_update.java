package nl.hs_hague.restaurant;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nl.hs_hague.restaurant.model.Restaurant;

public class Dialog_update extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.activity_dialog_update, null);


        Restaurant current = (Restaurant) getArguments().getSerializable("restaurant");


        final TextView editTextName = (TextView) convertView.findViewById(R.id.editText1);
        final EditText editTextStreet = (EditText)convertView.findViewById(R.id.editText2);
        final EditText editTextPlace = (EditText)convertView.findViewById(R.id.editText3);
        final EditText editTextZip = (EditText)convertView.findViewById(R.id.editText4);
        final EditText editTextPath = (EditText)convertView.findViewById(R.id.editText5);
        final EditText editTextComments = (EditText)convertView.findViewById(R.id.editText6);



        editTextName.setText(current.getName());
        editTextStreet.setText(current.getStreet());
        editTextPlace.setText(current.getPlace());
        editTextZip.setText(current.getZip());
        editTextComments.setText(current.getDescription());
        editTextPath.setText(current.getImage());

        Toast.makeText(getContext(), current.getName(), Toast.LENGTH_LONG).show();




        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(convertView)
                // Add action buttons
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        String name = editTextName.getText().toString();
                        String street = editTextStreet.getText().toString();
                        String place = editTextPlace.getText().toString();
                        String zip = editTextZip.getText().toString();
                        String path = editTextPath.getText().toString();
                        String comments = editTextComments.getText().toString();

                        Restaurant myRestaurant = new Restaurant(1, name, street, place, zip, path, comments);
                        DBMaster db = new DBMaster();
                        db.update(myRestaurant, getContext());

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
