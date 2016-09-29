package com.blogspot.chunkingz.countdowntimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    ImageView go;
    ImageView stop;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        //timerTextView.setText("0:30");
        controllerButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        //timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft){
        int mins =  secondsLeft / 60;
        int secs =  secondsLeft - mins * 60;

        String mins2 = String.valueOf(mins);
        String secs2 = String.valueOf(secs);

        if (secs <= 9){
            secs2 = "0" + secs2;
        }
               /* if (mins2.equals("0")){
                    mins2 = "00";
                }*/


        assert timerTextView != null;
        timerTextView.setText(mins2 +":"+ secs2);
    }

    public void controlTimer(View view){
        //Log.i("button pressed", "pressed");

        if (counterIsActive == false) {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");
            go.setVisibility(View.VISIBLE);
            stop.setVisibility(View.INVISIBLE);

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    go.setVisibility(View.INVISIBLE);
                    stop.setVisibility(View.VISIBLE);
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }else {
            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(),"Countdown Timer \nCreated by Kingston Fortune",Toast.LENGTH_LONG).show();

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        go = (ImageView)findViewById(R.id.imageView);
        stop = (ImageView)findViewById(R.id.imageView2);
        controllerButton = (Button)findViewById(R.id.controllerButton);
        assert timerSeekBar != null;
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
