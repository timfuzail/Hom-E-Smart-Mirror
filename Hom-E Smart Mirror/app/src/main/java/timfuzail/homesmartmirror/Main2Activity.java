package timfuzail.homesmartmirror;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    String jokeapi = "https://icanhazdadjoke.com/slack";
    String newsapi;
    String quoteapi = "http://quotes.rest/qod.json";
    String darkskyapi;
    private String TAG = Main2Activity.class.getSimpleName();
    String News1,News2,News3,News4,News5,News6,Temp,Description,Quote,Author,Sunrise,Sunset,Joke;
    int loop;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main2);

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                Intent i = getIntent();
                String darkskykey= i.getStringExtra("darkskyapi");
                String lat= i.getStringExtra("lat");
                String lon= i.getStringExtra("lon");
                String newskey= i.getStringExtra("newsapi");
                String countrycode= i.getStringExtra("countrycode");

                darkskyapi = "https://api.darksky.net/forecast/" + darkskykey + "/" + lat + "," + lon + "?units=auto&exclude=minutely,hourly,alerts,flags";
                newsapi = "http://newsapi.org/v2/top-headlines?country=" + countrycode + "&apiKey=" + newskey + "&pagesize=6";

                if(isNetworkStatusAvialable (getApplicationContext())) {
                    StringRequest newsrequest = new StringRequest(newsapi, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String string) {
                            GetNews(string);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                    rQueue.add(newsrequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                }

                if(isNetworkStatusAvialable (getApplicationContext())) {
                    StringRequest weatherrequestdark = new StringRequest(darkskyapi, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String string) {
                            GetWeatherDark(string);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                    rQueue.add(weatherrequestdark);
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                }


                final Handler date = new Handler();        //to update voltage every second
                date.postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        setDate();
                        date.postDelayed(this, 5000);
                    }
                }, 5000);

                final Handler activityrestart = new Handler();        //to update voltage every second
                activityrestart.postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        startActivity(new Intent(Main2Activity.this, Main2Activity.class));
                        activityrestart.postDelayed(this, 900000);
                    }
                }, 900000);

                final Handler activityrreset = new Handler();        //to update voltage every second
                activityrreset.postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            StringRequest newsrequest = new StringRequest(newsapi, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String string) {
                                    GetNews(string);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                            rQueue.add(newsrequest);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            //Toast.makeText(getApplicationContext(), "Internet detected", Toast.LENGTH_SHORT).show();
                            StringRequest jokeresponse = new StringRequest(jokeapi, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String string) {
                                    GetJoke(string);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                            rQueue.add(jokeresponse);

                            StringRequest quoteresponse = new StringRequest(quoteapi, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String string) {
                                    GetQuote(string);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue rQueue2 = Volley.newRequestQueue(Main2Activity.this);
                            rQueue2.add(quoteresponse);

                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

                        }
                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            StringRequest weatherrequestdark = new StringRequest(darkskyapi, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String string) {
                                    GetWeatherDark(string);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                            rQueue.add(weatherrequestdark);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                        activityrreset.postDelayed(this, 600000);
                    }
                }, 600000);

                final Handler news = new Handler();        //to update voltage every second
                news.postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        setnews();
                        news.postDelayed(this, 20000);
                    }
                }, 20000);

                        if(isNetworkStatusAvialable (getApplicationContext())) {
                            //Toast.makeText(getApplicationContext(), "Internet detected", Toast.LENGTH_SHORT).show();
                            StringRequest jokeresponse = new StringRequest(jokeapi, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String string) {
                                    GetJoke(string);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                            rQueue.add(jokeresponse);

                            StringRequest quoteresponse = new StringRequest(quoteapi, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String string) {
                                    GetQuote(string);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            RequestQueue rQueue2 = Volley.newRequestQueue(Main2Activity.this);
                            rQueue2.add(quoteresponse);


                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

                        }
            }

    @Override
    public void onPause() {
        super.onPause();
      //  startActivity(new Intent(Main2Activity.this, Main2Activity.class));
    }

    void GetWeatherDark(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            String main =object.getString("currently");
            JSONObject js2= new JSONObject(main);
            String description = js2.getString("icon");
            Description = description;
            Description = Description.replaceAll("[\\s\\-()]", "");
            String temp =js2.getString("temperature");
            Log.e(TAG, "Icon: " + Description);
            Log.e(TAG, "Response: " + temp);
            //float number = Float.valueOf(temp);
            float number = Float.valueOf(temp);
            String s = String.format("%.0f", number);
            Temp = s;
            String daily = object.getString("daily");
            JSONObject dailyobj = new JSONObject(daily);
            JSONArray contacts = dailyobj.getJSONArray("data");
            Log.e(TAG, "data: " + contacts);
                JSONObject c = contacts.getJSONObject(0);
                String sunrise =c.getString("sunriseTime");
                String sunset =c.getString("sunsetTime");
                Sunrise = sunrise;
                Sunset = sunset;
            Log.e(TAG, "sunrise: " + sunrise);
            Log.e(TAG, "sunset: " + sunset);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView temperature = (TextView)findViewById(R.id.temperature);
        temperature.setText(Temp + "°");

        ImageView WeatherImage = (ImageView)findViewById(R.id.weatherimage);
        if (Description != null){
            Resources res = getResources();;
            int resID = res.getIdentifier(Description, "drawable", getPackageName());
            Drawable drawable = res.getDrawable(resID );
            WeatherImage.setImageDrawable(drawable );
        }
        int sunr = Integer.parseInt(Sunrise);
        int t = sunr+19800;         //19800 for +5:30 GMT
        int d = (t % 86400);
        int h = (d/3600);
        int m = d % 3600;
        int m1 = m/60;
        TextView sunriseTime = (TextView)findViewById(R.id.sunriseTime);
        if (m1<10)
        {
            sunriseTime.setText(h + ":0" + m1);
        }else{
            sunriseTime.setText(h + ":" + m1);
        }

        int suns = Integer.parseInt(Sunset);
        int t1 = suns+19800;         //19800 for +5:30 GMT
        int d1 = (t1 % 86400);
        int h1 = (d1/3600);
        int m10 = d1 % 3600;
        int m11 = m10/60;
        TextView sunsetTime = (TextView)findViewById(R.id.sunsetTime);

        if (m11<10)
        {
            sunsetTime.setText(h1 + ":0" + m11);
        }else{
            sunsetTime.setText(h1 + ":" + m11);
        }
    }

    void GetNews(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray contacts = object.getJSONArray("articles");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String email = c.getString("title");
                Log.e(TAG, "Response: " + email);
                if (i == 0)
                    News1 = email;
                if (i == 1)
                    News2 = email;
                if (i == 2)
                    News3 = email;
                if (i == 3)
                    News4 = email;
                if (i == 4)
                    News5 = email;
                if (i == 5)
                    News6 = email;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        loop = loop+2;

            final TextView newstext1 = (TextView)findViewById(R.id.N1);
            newstext1.setText("➤ " + News1);
            final TextView newstext2 = (TextView)findViewById(R.id.N2);
            newstext2.setText("➤ " + News2);

    }

    void GetJoke(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            JSONArray contacts = object.getJSONArray("attachments");
            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String joke = c.getString("text");
                Log.e(TAG, "Joke Response: " + joke);
                Joke = joke;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView joke = (TextView)findViewById(R.id.joke);
        joke.setText(Joke);
    }

    void GetQuote(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            String main =object.getString("contents");
            JSONObject js2= new JSONObject(main);

            JSONArray contacts = js2.getJSONArray("quotes");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String quote = c.getString("quote");
                Quote = quote;
            }
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String author = c.getString("author");
                Author = author;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final TextView quotetext = (TextView)findViewById(R.id.quote);
        quotetext.setText(Quote + '\n' + "-" + Author);
    }

    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
            {
                return netInfos.isConnected();
            }
        }
        return false;
    }

    public void setDate (){

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        TextView dayofweek = (TextView)findViewById(R.id.dayofweek);
        TextView month = (TextView)findViewById(R.id.month);
        switch (day) {
            case Calendar.SUNDAY:
                dayofweek.setText("Sunday ");
                break;
            case Calendar.MONDAY:
                dayofweek.setText("Monday ");
                break;
            case Calendar.TUESDAY:
                dayofweek.setText("Tuesday ");
                break;
            case Calendar.WEDNESDAY:
                dayofweek.setText("Wednesday ");
                break;
            case Calendar.THURSDAY:
                dayofweek.setText("Thursday ");
                break;
            case Calendar.FRIDAY:
                dayofweek.setText("Friday ");
                break;
            case Calendar.SATURDAY:
                dayofweek.setText("Saturday ");
                break;

        }
        Calendar c = Calendar.getInstance();
        String Month = (c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ));
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        String Day = String.valueOf(dayOfMonth);
        month.setText(Day + " " + Month);
    }
    public void setnews(){
        loop = loop+1;
        if(loop==1)
        {
            final TextView newstext1 = (TextView)findViewById(R.id.N1);
            newstext1.setText("➤ " + News1);
        }
        if(loop==2)
        {
            final TextView newstext2 = (TextView)findViewById(R.id.N2);
            newstext2.setText("➤ " + News2);
        }
        if(loop==3)
        {
            final TextView newstext1 = (TextView)findViewById(R.id.N1);
            newstext1.setText("➤ " + News3);
        }
        if(loop==4)
        {
            final TextView newstext2 = (TextView)findViewById(R.id.N2);
            newstext2.setText("➤ " + News4);
        }
        if(loop==5)
        {
            final TextView newstext1 = (TextView)findViewById(R.id.N1);
            newstext1.setText("➤ " + News5);
        }
        if(loop==6)
        {
            final TextView newstext2 = (TextView)findViewById(R.id.N2);
            newstext2.setText("➤ " + News6);
            loop=0;
        }
    }
        }