package vts.oop.memorius.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;


public class HighscoreActivity extends ActionBarActivity {
    SQLiteDatabase db;
    Bundle extras;

    int score;
    String name;

    TextView highscoreTextName;
    TextView highscoreTextScore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_highscore);

        highscoreTextName = (TextView)findViewById(R.id.textview_highscore_name);
        highscoreTextScore = (TextView)findViewById(R.id.textview_highscore_score);

        db = openOrCreateDatabase("highscore", MODE_PRIVATE, null);

        extras = getIntent().getExtras();

        name = extras.getString("name");
        score = extras.getInt("score");

        db.execSQL("CREATE TABLE IF NOT EXISTS highscore (hs_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, score INTEGER);");

        if(score != 0 && !name.equals(""))
            db.execSQL("INSERT INTO highscore(name, score) VALUES ('"+ name + "', " + score + ");");

        Cursor resultSet = db.rawQuery("SELECT name, score FROM highscore ORDER BY score DESC LIMIT 0,5", null);

        String currentText;
        if(resultSet.getCount() > 0){
            resultSet.moveToFirst();
            do{
                currentText = highscoreTextName.getText().toString().trim();
                highscoreTextName.setText(currentText + "\n" + resultSet.getString(0));

                currentText = highscoreTextScore.getText().toString().trim();
                highscoreTextScore.setText(currentText + "\n" + resultSet.getString(1));
            }while(resultSet.moveToNext());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.highscore, menu);
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
}
