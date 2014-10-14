package com.apperture.shaketunes;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RemoteViews;
import android.content.ContextWrapper;
// Pradeep Singh

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AppWidget1ConfigureActivity AppWidget1ConfigureActivity}
 */
public class AppWidget1 extends AppWidgetProvider  implements SensorEventListener {
    SensorManager sm;
    Sensor proxSensor,acc;
    ContextWrapper temp=null;
    long eventtime = SystemClock.uptimeMillis();
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            AppWidget1ConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
      //  uiHelper = new UiLifecycleHelper(this, callback);
        Log.d("Set sensor"," Set started");
        sm=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.d("Set sensor"," Set initialised");
        // Enter relevant functionality for when the first widget is created
    }

    public void sensor_Trigger (boolean set)//true sets acclrometr and proximity on and opp on false
    {Log.d("Sensor switch","Started fn");
        if(set)
        {
            sm.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("Sensor switch","Started");
        }

        else{
            sm.unregisterListener(this, proxSensor);
            sm.unregisterListener(this,acc);
            Log.d("Sensor switch","stopped");
        }

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {
        Log.d("update","update");
        CharSequence widgetText = AppWidget1ConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget1);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public void play(View view){
        View view1=null;
        pause(view1);
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        //functions temp = null;
        temp.sendOrderedBroadcast(downIntent, null);

    }


    public void pause(View view){
        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        temp.sendOrderedBroadcast(upIntent, null);
    }
    /*NEXT*/
    public void next(View view){
        View view1=null;
        pause(view1);
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,   KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        temp.sendOrderedBroadcast(downIntent, null);
    }

    /*PREVIOUS*/
    public void prev(View view){
        View view1=null;
        pause(view1);
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        temp.sendOrderedBroadcast(downIntent, null);
    }



    /**
     * Called when sensor values have changed.
     * <p>See {@link android.hardware.SensorManager SensorManager}
     * for details on possible sensor types.
     * <p>See also {@link android.hardware.SensorEvent SensorEvent}.
     * <p/>
     * <p><b>NOTE:</b> The application doesn't own the
     * {@link android.hardware.SensorEvent event}
     * object passed as a parameter and therefore cannot hold on to it.
     * The object may be part of an internal pool and may be reused by
     * the framework.
     *
     * @param event the {@link android.hardware.SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    /**
     * Called when the accuracy of the registered sensor has changed.
     * <p/>
     * <p>See the SENSOR_STATUS_* constants in
     * {@link android.hardware.SensorManager SensorManager} for details.
     *
     * @param sensor
     * @param accuracy The new accuracy of this sensor, one of
     *                 {@code SensorManager.SENSOR_STATUS_*}
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


