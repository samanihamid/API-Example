package au.edu.unsw.infs3634.jokesapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button mDisplayJoke;
    private TextView mJoke;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the UI handle to the view elements
        mDisplayJoke = findViewById(R.id.btnDisplayJoke);
        mJoke = findViewById(R.id.tvJoke);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JokeService service = retrofit.create(JokeService.class);
        JokeDatabase db = Room.databaseBuilder(getApplicationContext(), JokeDatabase.class, "joke-database")
                .build();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.jokeDao().deleteAll();
            }
        });

        // Implement onClickListener for mDisplayJoke button
        mDisplayJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Joke> responseCall = service.getRandomJoke();
                responseCall.enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        Joke joke = response.body();
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                db.jokeDao().insert(joke);
                                List<Joke> jokes = db.jokeDao().getJokes();
                                int index = 0;
                                for(Joke joke : jokes) {
                                    Log.d(TAG, "Joke " + String.valueOf(++index)+ ": "+ joke.getValue());
                                }
                            }
                        });
                        mJoke.setText(joke.getValue());
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {

                    }
                });

            }
        });
    }
}