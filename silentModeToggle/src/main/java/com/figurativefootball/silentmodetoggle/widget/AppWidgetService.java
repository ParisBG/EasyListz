package com.figurativefootball.silentmodetoggle.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;

import com.figurativefootball.silentmodetoggle.R;
import com.figurativefootball.silentmodetoggle.RingerHelper;

//Service that handles all widget operations!
public class AppWidgetService extends IntentService {
//The flag that you send in the intent that indicates what you want the phone to do
    private static String ACTION_DO_TOGGLE = "ActionDoToggle";
//
    AudioManager audioManager;
//CONSTRUCTORS ARE ALWAYS CALLED BEFORE onCREATE
    public AppWidgetService() {
        super("AppWidgetService");

    }
//CONSTRUCTORS ARE ALWAYS CALLED BEFORE onCREATE
    @Override
    public void onCreate() {
        super.onCreate();
//Controls app audio functions
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }
//All intent services must override onHandleIntent!!
//this is where all the heavy lifting is done
//and it is run on a separate background thread!
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getBooleanExtra(ACTION_DO_TOGGLE, false)) {
            RingerHelper.performToggle(audioManager);
        }
//reference used to update the widgets state
        AppWidgetManager mgr = AppWidgetManager.getInstance(this);
        ComponentName name = new ComponentName(this, AppWidget.class);
        mgr.updateAppWidget(name, updateUi());
    }

    private RemoteViews updateUi() {
        //inflates your app widget xml file into a remote view object
        // which is used by the widget
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.app_widget);

        int phoneImage = RingerHelper.isPhoneSilent(audioManager)
                ? R.drawable.sound_off
                : R.drawable.sound_on;
        remoteViews.setImageViewResource(R.id.phone_state, phoneImage);

        Intent intent = new Intent(this, AppWidgetService.class);
        intent.putExtra(ACTION_DO_TOGGLE, true);
/*wraps the intent into a pending intent
    this gives someone in another process permission to send
    you an intent.

    The flag one shot flag ensures that this is executed only once per interaction
*/
        PendingIntent pendingIntent =
                PendingIntent.getService(this, 0,
                        intent, PendingIntent.FLAG_ONE_SHOT);
//sets a listener to the widget button!
        remoteViews.setOnClickPendingIntent(R.id.phone_state, pendingIntent);

        return remoteViews;
    }

}
