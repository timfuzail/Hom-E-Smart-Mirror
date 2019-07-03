package timfuzail.homesmartmirror;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText darkskyapi;
    private EditText lat;
    private EditText lon;
    private EditText newsapi;
    private EditText countrycode;
    private Button clickButton;
    private CheckBox jokeOption;
    SharedPreferences pref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        darkskyapi = findViewById(R.id.darkskyapi);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        newsapi = findViewById(R.id.newsapi);
        countrycode = findViewById(R.id.countrycode);
        clickButton = findViewById(R.id.button);
        jokeOption = findViewById(R.id.checkBox);



        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 Intent i = new Intent(MainActivity.this, Main2Activity.class);
                SharedPreferences.Editor editor = pref.edit();
                if (!darkskyapi.getText().toString().trim().isEmpty()){
                    editor.putString("darkskyapi", darkskyapi.getText().toString());
                }
                if (!lat.getText().toString().trim().isEmpty()){
                    editor.putString("lat", lat.getText().toString());
                }
                if (!lon.getText().toString().trim().isEmpty()){
                    editor.putString("lon", lon.getText().toString());
                }
                if (!newsapi.getText().toString().trim().isEmpty()){
                    editor.putString("newsapi", newsapi.getText().toString());
                }
                if (!countrycode.getText().toString().trim().isEmpty()){
                    editor.putString("countrycode", countrycode.getText().toString());
                }
                if (jokeOption.isChecked())
                {
                    editor.putInt("showjoke", 1);
                } else {
                    editor.putInt("showjoke", 0);
                }
                editor.apply();
                startActivity(i);
            }
        });
        fillData();

    }

    public void fillData(){
        String saveddarkskykey = pref.getString("darkskyapi", null); // getting String
        String savedlat= pref.getString("lat", null); // getting String
        String savedlon=pref.getString("lon", null); // getting String
        String savednewskey=pref.getString("newsapi", null); // getting String
        String savedcountrycode=pref.getString("countrycode", null); // getting String


        if(saveddarkskykey != null && savedlat != null && savedlon != null && savednewskey != null && savedcountrycode != null) {
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(i);
        } else {
            if (saveddarkskykey != null){
                darkskyapi.setText(saveddarkskykey);
            }
            if (savedlat != null){
                lat.setText(savedlat);
            }
            if (savedlon != null){
                lon.setText(savedlon);
            }
            if (savednewskey != null){
                newsapi.setText(savednewskey);
            }
            if (savedcountrycode != null){
                countrycode.setText(savedcountrycode);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        fillData();
    }

}
