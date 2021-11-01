package au.edu.unsw.infs3634.jokesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button mDisplayJoke;
    private TextView mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String jsonData = "{\"categories\":[],\"created_at\":\"2020-01-05 13:42:21.795084\",\"icon_url\":\"https://assets.chucknorris.host/img/avatar/chuck-norris.png\",\"id\":\"lmcZde9rQsetcCHjC9BoCQ\",\"updated_at\":\"2020-01-05 13:42:21.795084\",\"url\":\"https://api.chucknorris.io/jokes/lmcZde9rQsetcCHjC9BoCQ\",\"value\":\"When Chuck Norris typed \\\"LMAO\\\" on the internet, he actually did it.\"}";
//        Gson gson = new Gson();
//        Joke joke = gson.fromJson(jsonData, Joke.class);

        // Get the UI handle to the view elements
        mDisplayJoke = findViewById(R.id.btnDisplayJoke);
        mJoke = findViewById(R.id.tvJoke);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JokeService service = retrofit.create(JokeService.class);

        // Implement onClickListener for mDisplayJoke button
        mDisplayJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Joke> responseCall = service.getRandomJoke();
                responseCall.enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        Joke joke = response.body();
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