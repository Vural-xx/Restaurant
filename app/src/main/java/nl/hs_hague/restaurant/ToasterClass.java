package nl.hs_hague.restaurant;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jakub on 2016-09-20.
 */
public class ToasterClass {
    public Context context;


    public ToasterClass(Context context) {
        this.context = context;
    }

    public void addedToast(String _name, String _street, String _place, String _ZIP, String _path, String _comments) {
        String text_to_show = ("Added Row! \nname: " + _name
                + "\nstreet: " + _street
                + "\nplace: " + _place
                + "\nzip code: " + _ZIP
                + "\nimage path: " + _path
                + "\ncomments: " + _comments);
        Toast.makeText(context, text_to_show, Toast.LENGTH_LONG).show();
    }


    public void deletedToast(String _name) {
        String text_to_show = (_name + " deleted");
        Toast.makeText(context, text_to_show, Toast.LENGTH_LONG).show();
    }

    public void updatedToast(String _name, String _street, String _place, String _ZIP, String _path, String _comments) {
        String text_to_show = ( _name + " updated"
                + "\nstreet: " + _street
                + "\nplace: " + _place
                + "\nzip code: " + _ZIP
                + "\nimage path: " + _path
                + "\ncomments: " + _comments);
        Toast.makeText(context, text_to_show, Toast.LENGTH_LONG).show();
    }

    public void selectedToast(String _name){
        String text_to_show = (_name + " found");
        Toast.makeText(context, text_to_show, Toast.LENGTH_LONG).show();
    }

    public void cannotToast(){
        String text_to_show = "I cannot do this.";
        Toast.makeText(context, text_to_show, Toast.LENGTH_LONG).show();
    }
}