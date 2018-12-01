package com.figurativefootball.mysquadlayouts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.figurativefootball.mysquadlayouts.R;

import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.DESCRIPTION;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.IMAGE_RESOURCE_ID;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.NAME;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.DB_NAME_STARBUZZ;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION = 2;

    public StarbuzzDatabaseHelper(Context context){
        super(context,DB_NAME_STARBUZZ,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDb(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        updateMyDb(db,oldVersion,newVersion);
    }

    private static void insertDrink(SQLiteDatabase db, String name,
                                   String description, int imageId,
                                   String nullColumnHack){

        ContentValues drinkValues = new ContentValues();
        drinkValues.put(NAME,name);
        drinkValues.put(DESCRIPTION,description);
        drinkValues.put(IMAGE_RESOURCE_ID, imageId);

        db.insert("DRINK",nullColumnHack,drinkValues);
    }

    private static void updateMyDb(SQLiteDatabase db,int oldVersion,int newVersion){
        if (oldVersion < 1) {

            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NAME+" TEXT,"
                    + DESCRIPTION+" TEXT,"
                    + IMAGE_RESOURCE_ID+" INTEGER);");

            insertDrink(db, "Latte", "Espresso and steamed milk",
                    R.drawable.latte, null);
            insertDrink(db, "Cappuccino", "Espresso, hot and steamed milk foam",
                    R.drawable.cappuccino, null);
            insertDrink(db, "Filter", "Our best drip coffee",
                    R.drawable.frappuccino, null);

        }

        if (oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }
}
