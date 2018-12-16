package timfuzail.homesmartmirror;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText darkskyapi;
    private EditText lat;
    private EditText lon;
    private EditText newsapi;
    private EditText countrycode;
    private Button clickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        darkskyapi = findViewById(R.id.darkskyapi);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        newsapi = findViewById(R.id.newsapi);
        countrycode = findViewById(R.id.countrycode);
        clickButton = findViewById(R.id.button);

        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("darkskyapi", darkskyapi.getText().toString());
                i.putExtra("lat", lat.getText().toString());
                i.putExtra("lon", lon.getText().toString());
                i.putExtra("newsapi", newsapi.getText().toString());
                i.putExtra("countrycode", countrycode.getText().toString());
                startActivity(i);
            }
        });
    }

}
