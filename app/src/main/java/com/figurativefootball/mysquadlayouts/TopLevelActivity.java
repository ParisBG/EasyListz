package com.figurativefootball.mysquadlayouts;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.figurativefootball.mysquadlayouts.database.DrinkDbSchema;
import com.figurativefootball.mysquadlayouts.database.StarbuzzDatabaseHelper;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.FAVORITE;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz.NAME;
import static com.figurativefootball.mysquadlayouts.database.DrinkDbSchema.DrinkTable.Colz._ID;

public class TopLevelActivity extends AppCompatActivity {
    private ListView listView;
    private SQLiteDatabase db;
    private Cursor favCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
        populateListView2();
        //tapTargetTutorial(this,R.id.colorButton);
    }

    public void onClickColors(View view) {
        Intent intent = new Intent(this, ColorsActivity.class);
        startActivity(intent);
    }

    private void setupOptionsListView(){
        listView = findViewById(R.id.options);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent =
                                new Intent(TopLevelActivity.this,
                                        DrinkCategoryActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        Toast toast =
                                Toast.makeText(TopLevelActivity.this,
                                        "CLICKED NUMBER #1", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0,0);
                        toast.show();
                        break;

                    case 2:
                        Toast toast2 =
                                Toast.makeText(TopLevelActivity.this,
                                        "CLICKED NUMBER #2", Toast.LENGTH_SHORT);
                        toast2.setGravity(Gravity.BOTTOM, 0,0);
                        toast2.show();
                        break;


                }
            }
        });
    }

    private void populateListView2(){
        ListView favList = findViewById(R.id.list_favorites);

        try {
            SQLiteOpenHelper dbHelper = new StarbuzzDatabaseHelper(this);
            db = dbHelper.getReadableDatabase();
            favCursor = db.query("DRINK",
                    new String[] {_ID,NAME},
                    FAVORITE + "= 1",
                    null,null,
                    null,null);

            CursorAdapter favAdapter =
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1,
                            favCursor,
                            new String[] {NAME},
                            new int[] {android.R.id.text1},0);

            favList.setAdapter(favAdapter);

        } catch(SQLiteException e){
            e.printStackTrace();
            Toast t = Toast.makeText(this,
                    "ERROR READING DATABASE",Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }

        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent =
                        new Intent(TopLevelActivity.this,
                        DrinkActivity.class);

                intent.putExtra(DrinkActivity.EXTRA_DRINKID,(int) id);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRestart(){
        super.onRestart();

        Cursor newCursor = db.query("DRINK",
                new String[] {_ID,NAME},
                FAVORITE + "=1",null,
                null,null,null);

        ListView listFav = findViewById(R.id.list_favorites);
        CursorAdapter adapter = (CursorAdapter) listFav.getAdapter();
        adapter.changeCursor(newCursor);
        favCursor = newCursor;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        favCursor.close();
        db.close();
    }


    private void singleTapTarget(final Context context,int rawView,
                                 String title,String descr) {

                TapTargetView.showFor(((AppCompatActivity)context),
                        TapTarget.forView(findViewById(rawView),
                                title,descr)
                        .outerCircleColor(R.color.black)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.black)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.purple)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.aqua)  // Specify the color of the description text
                        .textColor(R.color.orange)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.orange)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(false)                   // Whether to tint the target view's color
                        .transparentTarget(true)          // Specify whether the target is transparent (displays the content underneath)
                        //.icon(getResources().getDrawable(R.drawable.lil_tap))                     // Specify a custom drawable to draw as the target
        .targetRadius(60),

        new TapTargetView.Listener() {
                    // The listener can listen for regular clicks, long clicks or cancels
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                Toast.makeText(context, "I DID IT!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
/*
    private void theTapTargetSequence(){
        new TapTargetSequence(this).targets(
                TapTarget.forView(findViewById(R.id.colorButton), "TITLE 1 TEST"),
                        TapTarget.forView(findViewById(R.id.fakeButton1),
                                "TITLE NUMERO DOS PUTO TEST",
                                "SCRIPT #2 TEST")

                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.lime)
                                .targetCircleColor(R.color.green)
                                .textColor(android.R.color.white))

                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget,
                                               boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget) {
                        // Perfom action for the current target
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                });
    }
*/
}
