package vts.oop.memorius.app;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    int currentClickID;
    int previousClickID;
    int memeCounter[];
    int imageButtonMemeID[];
    int clickCount;
    int overallClickCount;
    int score;
    int remainingTime;
    boolean containsImage[];
    boolean found[];
    String name;

    Handler timerHandler;
    Runnable timerRunnable;

    MediaPlayer heyaSong;
    MediaPlayer clickMusic;
    MediaPlayer winMusic;
    ImageButton imageButton[];
    ImageButton musicPauseBtn;
    Button restartButton;
    TextView currentTimeText;
    EditText scoreTextEdit;

    private enum Meme {
        BARNEY(0), FREDDY(1), BEAN(2), CAGE(3), OBAMA(4), PARKER(5), YAOMING(6), SERIOUS(7);

        int number;

        Meme(int number){
            this.number = number;
        }

        int getNumber(){
            return number;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        currentClickID = 0;
        previousClickID = 0;
        score = 0;
        clickCount = 0;
        remainingTime = 36;
        overallClickCount = 0;

        memeCounter = new int[8];
        containsImage = new boolean[16];
        imageButtonMemeID = new int[16];
        imageButton = new ImageButton[16];
        found = new boolean[16];

        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");

        scoreTextEdit = (EditText)findViewById(R.id.score_editText);
        clickMusic = MediaPlayer.create(getBaseContext(), R.raw.click);
        winMusic = MediaPlayer.create(getBaseContext(), R.raw.win);
        heyaSong = MediaPlayer.create(getBaseContext(), R.raw.heyyeyya);
        heyaSong.setLooping(true);
        currentTimeText = (TextView)findViewById(R.id.text_time_current);
        musicPauseBtn = (ImageButton)findViewById(R.id.btn_musicpause);

        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            public void run() {
                if(score >= 8)
                    roundFinished();
                else
                {
                    remainingTime--;

                    if(remainingTime > 9){
                        currentTimeText.setText("00:" + remainingTime);
                    }
                    else {
                        currentTimeText.setTextColor(Color.parseColor("#bc0004"));
                        currentTimeText.setText("00:0" + remainingTime);
                    }

                    if(remainingTime < 1){
                        currentTimeText.setText("00:00");
                        timerHandler.removeCallbacks(this);
                        roundFinished();
                    }
                    else
                        timerHandler.postDelayed(this, 1000);
                }
            }
        };

        restartButton = (Button)findViewById(R.id.btn_restart);

        for(int i = 1; i <= imageButton.length; i++){
            int resId = getResources().getIdentifier("imgbtn_" + i, "id", this.getPackageName());
            imageButton[i - 1] = (ImageButton)findViewById(resId);
            imageButton[i - 1].setImageResource(R.drawable.questionmark);

            containsImage[i - 1] = false;
            imageButtonMemeID[i - 1] = 0;
            found[i - 1] = false;
        }

        for(int i = 0; i < memeCounter.length; i++){
            memeCounter[i] = 0;
        }

        generateMemePictures();

        timerHandler.postDelayed(timerRunnable, 1000);
    }

    protected void roundFinished(){
        for (int i = 0; i < imageButton.length; i++) {
            imageButton[i].setClickable(false);
        }

        if(score >= 8)
            score = Math.abs(score * 20 + remainingTime * 2 - (overallClickCount / 4));
        else
            score = Math.abs(score * 5 + remainingTime * 2 - (overallClickCount / 4));
        scoreTextEdit.setText("" + score);
    }

    protected void generateMemePictures(){

        int rand;

        for(int i = 0; i < imageButton.length; i++){

            rand = (int)Math.round(Math.random() * 8);

            if( rand > 7)
                rand = 7;

            while(memeCounter[rand] >= 2) {
                rand = (int)Math.round(Math.random() * 8);

                if( rand > 7)
                    rand = 7;
            }

            memeCounter[rand]++;

            switch(rand)
            {
                case 0:
                    imageButtonMemeID[i] = Meme.BARNEY.getNumber();
                    break;

                case 1:
                    imageButtonMemeID[i] = Meme.FREDDY.getNumber();
                    break;

                case 2:
                    imageButtonMemeID[i] = Meme.BEAN.getNumber();
                    break;

                case 3:
                    imageButtonMemeID[i] = Meme.CAGE.getNumber();
                    break;

                case 4:
                    imageButtonMemeID[i] = Meme.OBAMA.getNumber();
                    break;

                case 5:
                    imageButtonMemeID[i] = Meme.PARKER.getNumber();
                    break;

                case 6:
                    imageButtonMemeID[i] = Meme.YAOMING.getNumber();
                    break;

                case 7:
                    imageButtonMemeID[i] = Meme.SERIOUS.getNumber();
                    break;

            }
        }
    }

    public void onClick(View v) {
        clickCount++;
        overallClickCount++;

        clickMusic.start();

        if (clickCount == 2)
            for (int i = 0; i < imageButton.length; i++)
                imageButton[i].setClickable(false);

        // current ID
        switch (v.getId()) {
            case R.id.imgbtn_1:
                currentClickID = 0;
                break;

            case R.id.imgbtn_2:
                currentClickID = 1;
                break;

            case R.id.imgbtn_3:
                currentClickID = 2;
                break;

            case R.id.imgbtn_4:
                currentClickID = 3;
                break;

            case R.id.imgbtn_5:
                currentClickID = 4;
                break;

            case R.id.imgbtn_6:
                currentClickID = 5;
                break;

            case R.id.imgbtn_7:
                currentClickID = 6;
                break;

            case R.id.imgbtn_8:
                currentClickID = 7;
                break;

            case R.id.imgbtn_9:
                currentClickID = 8;
                break;

            case R.id.imgbtn_10:
                currentClickID = 9;
                break;

            case R.id.imgbtn_11:
                currentClickID = 10;
                break;

            case R.id.imgbtn_12:
                currentClickID = 11;
                break;

            case R.id.imgbtn_13:
                currentClickID = 12;
                break;

            case R.id.imgbtn_14:
                currentClickID = 13;
                break;

            case R.id.imgbtn_15:
                currentClickID = 14;
                break;

            case R.id.imgbtn_16:
                currentClickID = 15;
                break;
        }

        // the image that goes with the imagebutton
        switch (imageButtonMemeID[currentClickID]) {
            case 0:
                imageButton[currentClickID].setImageResource(R.drawable.barney);
                break;

            case 1:
                imageButton[currentClickID].setImageResource(R.drawable.freddymercury);
                break;

            case 2:
                imageButton[currentClickID].setImageResource(R.drawable.mrbean);
                break;

            case 3:
                imageButton[currentClickID].setImageResource(R.drawable.nicolascage);
                break;

            case 4:
                imageButton[currentClickID].setImageResource(R.drawable.obama);
                break;

            case 5:
                imageButton[currentClickID].setImageResource(R.drawable.spiderman);
                break;

            case 6:
                imageButton[currentClickID].setImageResource(R.drawable.yaoming);
                break;

            case 7:
                imageButton[currentClickID].setImageResource(R.drawable.areyouserious);
                break;
        }

        if (clickCount >= 2) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (imageButtonMemeID[currentClickID] == imageButtonMemeID[previousClickID]) {
                        winMusic.start();

                        imageButton[previousClickID].setClickable(false);
                        imageButton[currentClickID].setClickable(false);

                        found[previousClickID] = true;
                        found[currentClickID] = true;

                        score++;
                        scoreTextEdit.setText("" + score);

                        for (int i = 0; i < imageButton.length; i++) {
                            if (!found[i])
                                imageButton[i].setClickable(true);
                        }
                        previousClickID = 0;
                    } else {
                        imageButton[previousClickID].setImageResource(R.drawable.questionmark);
                        imageButton[currentClickID].setImageResource(R.drawable.questionmark);

                        for (int i = 0; i < imageButton.length; i++)
                            if (!found[i])
                                imageButton[i].setClickable(true);
                        previousClickID = 0;
                    }
                }
            }, 500);
            clickCount = 0;
        } else
        {
            imageButton[currentClickID].setClickable(false);
            previousClickID = currentClickID;
        }
    }

    public void restartClick(View v){
        for (int i = 0; i < imageButton.length; i++) {
            imageButton[i].setClickable(true);
            imageButton[i].setImageResource(R.drawable.questionmark);
            imageButtonMemeID[i] = 0;
            containsImage[i] = false;
            found[i] = false;
        }

        for(int i = 0; i < memeCounter.length; i++)
            memeCounter[i] = 0;

        score = 0;
        clickCount = 0;
        previousClickID = 0;
        currentClickID = 0;
        scoreTextEdit.setText("" + 0);
        remainingTime = 35;
        overallClickCount = 0;

        currentTimeText.setTextColor(Color.BLACK);
        currentTimeText.setText("00:" + remainingTime);

        timerHandler.removeCallbacks(timerRunnable);
        timerHandler.postDelayed(timerRunnable, 1000);

        generateMemePictures();
    }

    public void musicPause(View v){
        if(heyaSong.isPlaying()){
            heyaSong.pause();
            musicPauseBtn.setBackgroundResource(R.drawable.soundoff);
        }
        else{
            heyaSong.start();
            musicPauseBtn.setBackgroundResource(R.drawable.soundon);
        }
    }

    public void submitClick(View v){
        Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
        i.putExtra("score", score);
        i.putExtra("name", name);
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }


    public void onStop(){
        heyaSong.pause();
        super.onStop();
    }

    public void onResume(){
        heyaSong = MediaPlayer.create(getBaseContext(), R.raw.heyyeyya);
        heyaSong.setLooping(true);
        heyaSong.start();
        super.onResume();
    }

    public void onPause(){
        heyaSong.pause();
        super.onPause();
    }
}