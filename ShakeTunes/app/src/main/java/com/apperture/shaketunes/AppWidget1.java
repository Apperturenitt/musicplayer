package com.apperture.shaketunes;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.content.ContextWrapper;
import android.widget.Toast;
import java.lang.Exception;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import static android.content.Context.*;
// Pradeep Singh

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AppWidget1ConfigureActivity AppWidget1ConfigureActivity}
 */
public class AppWidget1 extends AppWidgetProvider implements SensorEventListener {
    SensorManager sm;
    SensorEventListener sel;

    ImageButton button;
    boolean CurrTrigstate=false;
    static Sensor proxSensor,acc;
    public static SharedPreferences.Editor edit;
    public static SharedPreferences sharedPreferences;
   // ContextWrapper temp=new ContextWrapper(this);
    public static final String TOAST_ACTION = "com.com.apperture.shaketune.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.com.apperture.shaketune.EXTRA_ITEM";
    private static final String Play= "Play";
    private static final String Prev = "Prev";
    private static final String Next= "next";
    private static final String SensorTrigger="SensorTrigger";
    public static int flag2=0;
    public static Context c;AppWidgetManager awm;int[] I;
    long eventtime = SystemClock.uptimeMillis();
    public static float[] gravity={0,0,0};
    public static double mLastX=0,mLastY=0,mLastZ=0;
    public static boolean mInitialized=false;
    private final float NOISE = (float) 10.0;


    SensorEventListener myListner = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            View view2=null;
            if(event.sensor.getName().toString().contains("Acc"))
            {if(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2] >125)
            {if(event.values[0]<-3){next(view2,c);SystemClock.sleep(40);}
            else if(event.values[0]>3){prev(view2,c);SystemClock.sleep(40);}

            }
            }


            else
            {if(event.values[0]==0)
                play(view2,c);
                SystemClock.sleep(25);
            }
            //Update UI
        }

};
   int flag=0;
    @Override

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        try {
            c = context;

            awm = appWidgetManager;
            I = appWidgetIds;
        }catch(Exception e){
            Log.d("Error in onupdate",e.getMessage().toString());
        }
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
           // RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.app_widget1);
            /*Intent PlayIntent,PrevIntent,NextIntent;
           PlayIntent= new Intent(this,AppWidget1.class);
            PlayIntent.putExtra("play", "play");*/

            if(flag==0)onEnabled(c);flag++;
           // appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
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
    public void onReceive(Context context, Intent intent) {
        //ContextWrapper temp=new ContextWrapper(context);
        sharedPreferences=parsehelp.getInstance().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        super.onReceive(context, intent);
        String action = intent.getAction();
        View view=null;
        Log.d("Onrecieve",action);
        if (Play.equals(action)) play(view,context);
        else if (Prev.equals(action)) prev(view,context);
        else if (Next.equals(action)) next(view,context);
        else if(SensorTrigger.equals(action)
                ){//sm=(SensorManager)context.getSystemService(SENSOR_SERVICE);
            sharedPreferences.getBoolean("CurrTrigstate",false);
            sensor_Trigger(!sharedPreferences.getBoolean("CurrTrigstate",false));}

//updateAppWidget(c,awm,I[0]);
    }

    @Override
    public void onEnabled(Context context) {
      //  uiHelper = new UiLifecycleHelper(this, callback);
       // Log.d("Set sensor"," Set started");
        //c=context;
        //flag=0;
       // SensorEvent event=null;
        //onSensorChanged(event);
        sharedPreferences=parsehelp.getInstance().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
       // Log.d("onEnabled","context saved in c");
        edit=sharedPreferences.edit();
        edit.putBoolean("CurrTrigstate",false);
          edit.commit();
        sharedPreferences.getBoolean("CurrTrigstate",false);
        Log.d("onEnabled","shared preference saved");

/*      c

        sm=(SensorManager)context.getSystemService(SENSOR_SERVICE);

        proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Log.d("Set sensor",proxSensor.getName().toString());

        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.d("Set sensor",acc.getName().toString());
        Log.d("Set sensor"," Set initialised");*/
        // Enter relevant functionality for when the first widget is created
    }

    public void sensor_Trigger (boolean set)//true sets acclrometr and proximity on and opp on false
    {Log.d("Sensor switch trigger"," fn Started ");
        edit.remove("CurrTrigstate");
        edit.putBoolean("CurrTrigstate",set);
        edit.commit();
      //  else
        //c=get
        if(set)sm=(SensorManager)parsehelp.getInstance().getSystemService(SENSOR_SERVICE);


        if(set)
        {//button=(ImageButton)findViewById(R.id.trigger);
            try{


                proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                Log.d("Set sensor",proxSensor.getName().toString());

                acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Log.d("Set sensor",acc.getName().toString());
                Log.d("Pox check", proxSensor.getName().toString());
                Log.d("acc check", acc.getName().toString());
            }catch (Exception e){
                Log.d("error",e.toString());
                return;

            }
            proxSensor.getName();

            sm.registerListener(this , proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("Sensor switch","Started");
        }

        else{
            sm.unregisterListener(this);
            //sm.unregisterListener(this);
            //sm.unregisterListener(this, proxSensor|acc);

            //parsehelp.getInstance().u
            Log.d("Sensor switch","stopped");
        }
        CurrTrigstate=set;

    }


    @Override
    public void onDisabled(Context context) {
        sensor_Trigger(false);
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {
        Log.d("update","updateAppWidget");
        CharSequence widgetText = AppWidget1ConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget1);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        views.setOnClickPendingIntent(R.id.play, PendingIntent.getBroadcast(context, 0,
                new Intent(context, AppWidget1.class)
                        .setAction(Play),
                PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("update","Play set");
        views.setOnClickPendingIntent(R.id.prev, PendingIntent.getBroadcast(context, 0,
                new Intent(context, AppWidget1.class)
                        .setAction(Prev),
                PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("update","pREV set");
        views.setOnClickPendingIntent(R.id.next, PendingIntent.getBroadcast(context, 0,
                new Intent(context, AppWidget1.class)
                        .setAction(Next),
                PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("update","Next set");

        views.setOnClickPendingIntent(R.id.trigger, PendingIntent.getBroadcast(context, 0,
                new Intent(context, AppWidget1.class)
                        .setAction(SensorTrigger),
                PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("update", "Trigger set");

       // if(flag==0)onEnabled(c);flag++;
     //   remoteViews.setOnClickPendingIntent(R.id.widget_button, buildButtonPendingIntent(context));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public void play(View view,Context context){
        View view1=null;

        pause(view1,context);
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        //functions temp = null;
        context.sendOrderedBroadcast(downIntent, null);

    }


    public void pause(View view,Context context){
        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        context.sendOrderedBroadcast(upIntent, null);
     // context.getApplicationContext()
    }
    /*NEXT*/
    public void next(View view,Context context){
        View view1=null;
        pause(view1,context);
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,   KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        context.sendOrderedBroadcast(downIntent, null);
    }

    /*PREVIOUS*/
    public void prev(View view,Context context){
        View view1=null;
        pause(view1,context);
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        context.sendOrderedBroadcast(downIntent, null);
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

       // if(flag==0)
       // Log.d("onSensorChanged",event.toString());
        View view2 = null;

        //RemoteViews views= new RemoteViews("com.apperture.shaketunes", R.layout.app_widget1);
Context temp;
        temp=parsehelp.getInstance();
       if(sharedPreferences.getBoolean("CurrTrigstate",true)) if (event.sensor.getName().toString().contains("Acc")) {/*
            if (event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2] > 125) {
                if (event.values[0] < -3) {
                    next(view2, temp);
                    SystemClock.sleep(40);
                } else if (event.values[0] > 3) {
                    prev(view2, temp);
                    SystemClock.sleep(40);
                }

            }*/

            //final float alpha = 8/10;
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            final double alpha = 0.8; // constant for our filter below

            double[] gravity = {0,0,0};

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

// Remove the gravity contribution with the high-pass filter.
            x = event.values[0] - gravity[0];
            y = event.values[1] - gravity[1];
            z = event.values[2] - gravity[2];


            if (!mInitialized) {
                // sensor is used for the first time, initialize the last read values
                mLastX = x;
                mLastY = y;
                mLastZ = z;
                mInitialized = true;
            } else {
                // sensor is already initialized, and we have previously read values.
                // take difference of past and current values and decide which
                // axis acceleration was detected by comparing values

                double deltaX =(mLastX - x);
                double deltaY =(mLastY - y);
                double deltaZ = (mLastZ - z);
                if ( Math.abs(deltaX) < NOISE)
                    deltaX = (float) 0.0;
                if ( Math.abs(deltaY) < NOISE)
                    deltaY = (float) 0.0;
                if ( Math.abs(deltaZ) < NOISE)
                    deltaZ = (float) 0.0;
                mLastX = x;
                mLastY = y;
                mLastZ = z;

                if (deltaX > deltaY) {
                    Log.d("Acc","Hor");
                    if(deltaX<0) Log.d("Acc","next");
                    else  Log.d("Acc","prev");
                    // do something here if you like

                } else if (deltaY > deltaX) {
                    // Vertical shake
                    Log.d("Acc","ver");
                    // do something here if you like

                } else if ((deltaZ > deltaX) && (deltaZ > deltaY)) {
                    // Z shake
                    Log.d("Acc","z");

                    }
                 else {
                    // no shake detected
                }
            }

            //Log.d("Linear Acc",Double.toString(x)+" "+Double.toString(y)+" "+Double.toString(z));
        }


         else {
            if (event.values[0] == 0)
                play(view2, temp);
            SystemClock.sleep(25);
        }

       // else Log.d("listner"," called");
    }/*
        try{Text1.setText(event.sensor.getName().toString());
            Text2.setText("X: "+String.valueOf(event.values[0])+
                    "\nY: "+String.valueOf(event.values[1])
                    +"\nZ: "+String.valueOf(event.values[2]));
        }catch(Exception e)
        {
            // TODO Auto-generated method stub
        }




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
        public void onAccuracyChanged (Sensor sensor,int accuracy){


        }

    }
