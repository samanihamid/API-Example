package au.edu.unsw.infs3634.jokesapi;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, version = 1)
public abstract class JokeDatabase extends RoomDatabase {
    public abstract JokeDao jokeDao();
}
