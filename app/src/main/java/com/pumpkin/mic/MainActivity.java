package com.pumpkin.mic;

import java.io.File;
import java.io.IOException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";

    private Button Mic, Stop, Play;
    private static String outputFile;
    private MediaRecorder myAudioRecorder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mic = (Button)findViewById(R.id.Mic_button);
        Stop = (Button)findViewById(R.id.Stop_button);
        Play = (Button)findViewById(R.id.button3);

        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/myrecording.3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
        Play.setEnabled(false);
        Stop.setEnabled(false);

                        // Load an ad into the AdMob banner view.
                AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void Mic(View view)  {
        if (!Stop.isEnabled()) {
            Stop.setEnabled(true);
        }
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (Mic.isEnabled())
        {
            Mic.setEnabled(false);
        }


        Toast.makeText(getApplicationContext(), "Live Mic started", Toast.LENGTH_LONG).show();
    }

    public void Stop(View view) {
        if (!Play.isEnabled()) {
            Play.setEnabled(true);
        }
        if (!Mic.isEnabled()) {
            Mic.setEnabled(true);
        }
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_LONG).show();
    }

    public void Play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException{
        if (Mic.isEnabled()) {
            Mic.setEnabled(false);
        }
        if (!Stop.isEnabled()) {
            Stop.setEnabled(true);
        }
        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Mic.setEnabled(true);
         Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
    }


}
