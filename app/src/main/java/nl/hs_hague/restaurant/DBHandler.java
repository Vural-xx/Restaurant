package nl.hs_hague.restaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 15/09/2016.
 * This is the class where I only create the DB and define the update method
 */
public class DBHandler extends SQLiteOpenHelper{
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE restaurants (name text primary key,street text, place text, zip text, image text, desc text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("drop table if exists restaurants");
        db.execSQL("CREATE TABLE restaurants (name text primary key,street text, place text, zip text, image text, desc text )");
    }
    }


