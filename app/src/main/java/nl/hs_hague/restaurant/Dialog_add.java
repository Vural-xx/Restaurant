package nl.hs_hague.restaurant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import nl.hs_hague.restaurant.adapter.RestaurantAdapter;
import nl.hs_hague.restaurant.model.Restaurant;

public class Dialog_add extends DialogFragment {

    private static int TAKE_PICTURE = 1;
    private static int SELECT_PICTURE = 2;
    private View convertView;
    String name;
    private Bitmap image;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
//        Button accept = (Button)getDialog().findViewById(R.id.accept);
         convertView = inflater.inflate(R.layout.activity_dialog_add, null);

        Button pct = (Button) convertView.findViewById(R.id.btnPicture);
        name = Environment.getExternalStorageDirectory() + "/test.jpg";

        pct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "heeeee", Toast.LENGTH_LONG).show();
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                int code = TAKE_PICTURE;
               startActivityForResult(intent, code);
                //Toast.makeText(v.getContext(), "heeeee", Toast.LENGTH_LONG).show();

            }
        });
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(convertView)
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
                        //String path = editTextPath.getText().toString();
                        String comments = editTextComments.getText().toString();

                        Restaurant myRestaurant = new Restaurant(1, name, comments, place, street, zip,image);
                        DBMaster db = new DBMaster();
                        db.register(myRestaurant, getContext());


                       /* ToasterClass toaster = new ToasterClass(getContext());
                        toaster.addedToast(name, street, place, zip, path, comments);*/

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

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKE_PICTURE) {

            if (data != null) {
                /**
                 *mostramos en el ImageView
                 **/
                if (data.hasExtra("data")) {
                    ImageView iv = (ImageView)convertView.findViewById(R.id.imgView);
                    iv.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                    image = data.getParcelableExtra("data");


                }

            } else {
                /**
                 * buscamos y creamos el bitmap para el ImageView
                 **/
                ImageView iv = (ImageView)convertView.findViewById(R.id.imgView);
                iv.setImageBitmap(BitmapFactory.decodeFile(name));
                /**
                 * guardar la imagen en la galería
                 **/
                new MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null; {
                        msc = new MediaScannerConnection(convertView.getContext(), this); msc.connect();
                    }
                    public void onMediaScannerConnected() {
                        msc.scanFile(name, null);
                    }
                    public void onScanCompleted(String path, Uri uri) {
                        msc.disconnect();
                    }
                };
            }

        } /*else if (requestCode == SELECT_PICTURE){
            Uri selectedImage = data.getData();
            InputStream is;
            try {
                is = convertView.getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                ImageView iv = (ImageView)findViewById(R.id.imgView);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {}*/
        }
}
