package com.figurativefootball.mysquadlayouts;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.figurativefootball.mysquadlayouts.database.DrinkDbSchema;
import com.figurativefootball.mysquadlayouts.database.StarbuzzDatabaseHelper;

import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.DESCRIPTION;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.FAVORITE;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.IMAGE_RESOURCE_ID;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.NAME;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz._ID;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.DB_NAME_STARBUZZ;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

            int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
            new UpdateDrinkTask().execute(drinkId);

        SQLiteOpenHelper dbHelper = new StarbuzzDatabaseHelper(this);
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.query(
                    "DRINK",
                    new String[] {NAME,DESCRIPTION,IMAGE_RESOURCE_ID,FAVORITE},
                     "_id = ?",
                    new String[]{String.valueOf(drinkId)},
                    null,null,null);

            if (cursor.moveToFirst()){
                String nameText = cursor.getString(0);
                String descText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                CheckBox favorite = findViewById(R.id.favorite);
                TextView nameView = findViewById(R.id.nameView);
                TextView descriptionView = findViewById(R.id.descriptionView);
                ImageView imageView = findViewById(R.id.imageView);

                nameView.setText(nameText);
                nameView.setTextColor(Color.MAGENTA);

                descriptionView.setText(descText);

                imageView.setImageResource(photoId);
                imageView.setContentDescription(nameText);

                favorite.setChecked(isFavorite);
            }

            cursor.close();
            db.close();

        } catch(SQLiteException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this,
                    "Database TRiPpIn!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public void onFavoriteClicked(View view){
        int drinkId = getIntent().getExtras().getInt(EXTRA_DRINKID);
        new UpdateDrinkTask().execute(drinkId);
    }

    private class UpdateDrinkTask extends AsyncTask<Integer,Void,Boolean> {
        private ContentValues drinkValues;

        @Override
        protected void onPreExecute() {
            CheckBox favBox = findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put(FAVORITE,favBox.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkId = drinks[0];

            try{
                SQLiteOpenHelper dbHelper =
                        new StarbuzzDatabaseHelper(DrinkActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.update("DRINK",drinkValues,_ID+"=?",
                    new String[] {String.valueOf(drinkId)});

            db.close();
                return true;

            } catch (SQLiteException e){
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success){
                Toast t = Toast.makeText(DrinkActivity.this,
                        "DATABASE FAILURE ASYNC",Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER,0,0);
                t.show();
            }
        }
    }
}
