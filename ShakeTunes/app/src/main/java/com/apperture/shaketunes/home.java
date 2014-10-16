package com.apperture.shaketunes;

import android.app.Activity;
import com.parse.ParseInstallation;
import com.parse.PushService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import com.parse.Parse;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

//Pradeep Singh
public class home extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,SensorEventListener {
    long eventtime = SystemClock.uptimeMillis();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //pradeep
    SensorManager sm;
    Sensor proxSensor,acc;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    //
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ParseAnalytics.trackAppOpened(getIntent());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

       // updateCheck();
    }




    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void sensor_Trigger (boolean set)//true sets acclrometr and proximity on and opp on false
    {
        if(set)
        {
            sm.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
        }

        else{
            sm.unregisterListener(this, proxSensor);
            sm.unregisterListener(this,acc);
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
///////////////////////////////////////////////////////////////////////////////////////////
        /*
        // TODO Auto-generated method stub
        if(event.sensor.getName().toString().contains("Acc"))//when accelerometer is is called
        {if(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2] >125)
        {if(event.values[0]<-3){next(view2);SystemClock.sleep(40);}
        else if(event.values[0]>3){prev(view2);SystemClock.sleep(40);}

        }
        }


        else//when
        {if(event.values[0]==0)
            play(view2);
            SystemClock.sleep(25);
        }
        try{Text1.setText(event.sensor.getName().toString());
            Text2.setText("X: "+String.valueOf(event.values[0])+
                    "\nY: "+String.valueOf(event.values[1])
                    +"\nZ: "+String.valueOf(event.values[2]));
        }catch(Exception e)
        {
            // TODO Auto-generated method stub
        }*//////////////////////////////////////////////////////////////////////////////////////////////
    }


    public void updateCheck()
    {
        //
        new Thread() {
            @Override
            public void run() {


                // Create a new HttpClient and Post Header
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://destroyer/apperture/music/index.php");
                String myVersion = android.os.Build.VERSION.RELEASE;
                String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                String response="";

                //This is the data to send
                String MyName =" " ; //any data to send

                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("appKey", "paacApperture1112368"));
                    nameValuePairs.add(new BasicNameValuePair("appVersion", "1.1"));
                    nameValuePairs.add(new BasicNameValuePair("androidVersion", myVersion));
                    nameValuePairs.add(new BasicNameValuePair("mobID", android_id));
                    //nameValuePairs.add(new BasicNameValuePair("samples", Samples));
                    //nameValuePairs.add(new BasicNameValuePair("amount", Amount));
                    //nameValuePairs.add(new BasicNameValuePair("collected", Recieved));
                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    // Execute HTTP Post Request

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();

                    try {
                        response = httpclient.execute(httppost, responseHandler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //This is the response from a php application
                    //String reverseString = response;
                    //msg.setText(reverseString);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //response="Saved";
                            //display("Saved");
                            //setclr(green);
                        }
                    });

                    Log.d("post", " response" + response);
                    //Toast.makeText(this, "response" + reverseString, Toast.LENGTH_LONG).show();

                } /*catch (ClientProtocolException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //display("\nCheck server settings");
                        }
                    });
                    //msg.setText("cpe" +e.toString());
                    Log.d("post error", "cpe response"+e.toString());
                    //Toast.makeText(this, "CPE response " + e.toString(), Toast.LENGTH_LONG).show();
                    // TODO Auto-generated catch block
                } */catch (Exception e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //display(""+"\nCan't connect to server!");
                        }
                    });
                    //msg.setText("ioe" +e.toString());
                    Log.d("post error", "IOE response"+e.toString());
                    //Toast.makeText(this, "IOE response " + e.toString(), Toast.LENGTH_LONG).show();
                    // TODO Auto-generated catch block
                }


            }
        }.start();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void play(View view){
        View view1=null;
       // pause(view1);
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        sendOrderedBroadcast(downIntent, null);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((home) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
