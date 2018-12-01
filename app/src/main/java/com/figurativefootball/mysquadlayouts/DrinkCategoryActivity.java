package com.figurativefootball.mysquadlayouts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.figurativefootball.mysquadlayouts.database.DrinkDbSchema;
import com.figurativefootball.mysquadlayouts.database.StarbuzzDatabaseHelper;

import java.sql.SQLData;
import java.sql.SQLException;

import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.DESCRIPTION;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.IMAGE_RESOURCE_ID;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.NAME;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz._ID;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.DB_NAME_STARBUZZ;

public class DrinkCategoryActivity extends Activity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ListView listDrinks = findViewById(R.id.moreOptions);
        SQLiteOpenHelper dbHelper = new StarbuzzDatabaseHelper(this);

        try {
             db = dbHelper.getReadableDatabase();
             cursor = db.query("DRINK",
                    new String[] {_ID,NAME},
                   null,null,null,
                    null,null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{NAME},
                    new int[] {android.R.id.text1},
                    0);

            listDrinks.setAdapter(listAdapter);

        } catch(SQLiteException e){
            e.printStackTrace();
            Toast t = Toast.makeText(this,
                    "FAWWWKK YOU MEAN!?!?!",
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }
             /*
        ArrayAdapter<Drink> theListView =
                new ArrayAdapter<Drink>(this,
                        android.R.layout.simple_list_item_1,
                        Drink.drinks);
        listView.setAdapter(theListView);
*/
        listDrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {

                    Intent intent =
                            new Intent(DrinkCategoryActivity.this,
                                    DrinkActivity.class);
                    intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);

                    startActivity(intent);
            }
        });
    }

    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
