package vts.oop.memorius.app;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ViewFlipper;

public class MenuActivity extends ActionBarActivity {
    MediaPlayer backgroundMusic;
    ViewFlipper vf;
    EditText nameText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_menu);

        vf = (ViewFlipper)findViewById(R.id.meme_flipper);
        nameText = (EditText)findViewById(R.id.edittext_name);

        backgroundMusic = MediaPlayer.create(getBaseContext(), R.raw.trololo);
        backgroundMusic.isLooping();

        vf.setAutoStart(true);
        vf.setFlipInterval(5000);
        vf.startFlipping();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_play:
                if(!nameText.getText().toString().trim().equals("")){
                    nameText.setShadowLayer(0, 0, 0, Color.WHITE);
                    Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                    i1.putExtra("name", nameText.getText().toString().trim());
                    startActivity(i1);
                }
                else{
                    nameText.setShadowLayer(5, 0, 0, Color.RED);
                }

                break;

            /*case R.id.btn_exit:
                finish();
                break;*/

            case R.id.btn_highscore:
                Intent i2 = new Intent(getApplicationContext(), HighscoreActivity.class);
                i2.putExtra("name","");
                i2.putExtra("score",0);
                startActivity(i2);
                break;
        }
    }

    public void onStop(){
        backgroundMusic.pause();
        super.onStop();
    }

    public void onResume(){
        backgroundMusic = MediaPlayer.create(getBaseContext(), R.raw.trololo);
        backgroundMusic.start();
        vf.startFlipping();
        super.onResume();
    }

    public void onPause(){
        backgroundMusic.pause();
        super.onPause();
    }
}