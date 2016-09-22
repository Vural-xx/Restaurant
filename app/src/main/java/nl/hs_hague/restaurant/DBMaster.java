package nl.hs_hague.restaurant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import nl.hs_hague.restaurant.model.Restaurant;
/*
This is the class with the methods to handle the DB.
I suggest that when we need to delete o modify a restaurant we must make a search, to make it easier
Also because you need to send all the info about the restaurant when you make a change.
To save the image I decided to only save its' path, so that is the only thing you need to send when you are saving an image
I did not handle the methods to convert the image into bytes or something like that, I think that is Amanda's part.
*/

public class DBMaster{

/*The restaurant must have a unique name (PRIMARY KEY), so you can no register two restaurants with the same name*/
    public void register(Restaurant restaurant, Context context) {
        DBHandler admin = new DBHandler(context,"admin", null, 3);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            String name = restaurant.getName();
            String street = restaurant.getStreet();
            String place = restaurant.getPlace();
            String ZIP = restaurant.getZip();
            Bitmap path = restaurant.getImage();
            String comments = restaurant.getDescription();
            byte[] data = getBitmapAsByteArray(path);
            if(!(name.equals("Name:") || name.equals("name:") || (name.length()==0)) &&!(street.equals("Street:") && street.equals("street:") || (street.length()==0)) && !(place.equals("Place:") || place.equals("place:")|| (place.length()==0)) && !(ZIP.equals("ZIP:") || ZIP.equals("zip:")|| (ZIP.length()==0)) && !(comments.equals("Comments:") || comments.equals("comments:") || (comments.length()==0))) {
                bd.execSQL("INSERT INTO restaurants (name,street,place,zip,image,desc)VALUES ('" + name + "','" + street + "', '" + place + "','" + ZIP + "','" + data + "','" + comments + "')");
                bd.close();                
                System.out.println("The restaurant has been successfully registered");
            }
            else{

                    System.out.println("Please insert new values");
            }
        }
        catch(Exception e){
            if(e.toString().equals("android.database.sqlite.SQLiteConstraintException: column name is not unique (code 19)")) {
                System.out.println("You have already registered that restaurant");
            }
            else
            {
                System.out.println("There was a problem");
                e.printStackTrace();
            }
        }
    }
	
/*If you want to delete a restaurant you only need to send the name*/	
    public void delete(String name, Context context) {
      DBHandler admin = new DBHandler(context,"admin", null, 3);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            bd.execSQL("DELETE FROM restaurants WHERE name = '" + name + "'");
            bd.close();
            System.out.println("The restaurant was successfully deleted");
        }catch(Exception e){
            System.out.println("I can not delete that");
            e.printStackTrace();
        }
    }
/*This a particular search, like in the delete method you only need to send the name of the restaurant*/	
    public Restaurant search(String naam, Context context) {
        DBHandler admin = new DBHandler(context,"admin", null, 3);
        Vector result = new Vector();
        String conv="";
        String results[];
        Restaurant restaurant = null;
        Bitmap image;
        byte[] bimage =null;
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            Cursor cursor = bd.rawQuery("SELECT name,street,place,zip,desc,image FROM restaurants WHERE name ='"+naam+"'", null);
            while (cursor.moveToNext()){
                result.add(cursor.getString(0)+"-.-"+cursor.getString(1)+"-.-"+cursor.getString(2)+"-.-"+cursor.getString(3)+"-.-"+cursor.getString(4));
                bimage = cursor.getBlob(5);
            }
            results = new String[12];
            conv = result.firstElement().toString();
            results = conv.split("-.-");
            image = BitmapFactory.decodeByteArray(bimage, 0, bimage.length);
            restaurant = new Restaurant(1,results[0],results[1],results[2],results[3],results[4],image);

            cursor.close();
            bd.close();
            System.out.println("I found the restaurant");
        }catch(Exception e){
            System.out.println("I can not found that");
            e.printStackTrace();
        }
        return restaurant;
    }
	/*You need to send all the info about a restaurant when you update it.*/
    public void update(Restaurant restaurant, Context context) {
        DBHandler admin = new DBHandler(context,"admin", null, 3);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            String name = restaurant.getName();
            String street = restaurant.getStreet();
            String place = restaurant.getPlace();
            String ZIP = restaurant.getZip();
            Bitmap path = restaurant.getImage();
            String comments = restaurant.getDescription();
            bd.execSQL("UPDATE restaurants SET street ='"+street+"',place ='"+place+"', zip ='"+ZIP+"', image ='"+path+"', desc ='"+comments+"' WHERE name ='"+name+"'");
            bd.close();
            System.out.println("The restaurant was successfully updated");
        }catch(Exception e){
            System.out.println("I can not update that");
            e.printStackTrace();
        }
    }
	/*This method must be called when we start the app, in order to show all the restaurants we have, just add them to the list view*/
    public List<Restaurant> generalsearch(Context context) {
        DBHandler admin = new DBHandler(context,"admin", null, 3);
        Vector result1 = new Vector();
        String conv="";
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        SQLiteDatabase bd = admin.getWritableDatabase();

        List <byte[]> bimage =new ArrayList<byte[]>();
        try {
            Cursor cursor = bd.rawQuery("SELECT * FROM restaurants", null);
            while (cursor.moveToNext()){
                result1.add(cursor.getString(0)+"-.-"+cursor.getString(1)+"-.-"+cursor.getString(2)+"-.-"+cursor.getString(3)+"-.-"+cursor.getString(4));
                bimage.add(cursor.getBlob(5));
            }
            cursor.close();
            bd.close();

            for(int i=0; i <result1.size(); i++){
                Bitmap image;
                image = BitmapFactory.decodeByteArray(bimage.get(i), 0, bimage.get(i).length);
                String [] results = new String[12];
                conv = result1.get(i).toString();
                results = conv.split("-.-");
                restaurants.add(new Restaurant(1,results[0],results[1],results[2],results[3],results[4],image));

            }

        }catch(Exception e){
           System.out.println("I can not do that");
            e.printStackTrace();
        }
        return restaurants;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


}
