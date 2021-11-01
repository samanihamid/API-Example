package au.edu.unsw.infs3634.jokesapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeService {
    @GET("jokes/random")
    Call<Joke> getRandomJoke();
}
