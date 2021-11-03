package au.edu.unsw.infs3634.jokesapi;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JokeDao {

    @Query("SELECT * FROM joke")
    List<Joke> getJokes();

    @Insert
    void insert(Joke... joke);

    @Query("DELETE FROM joke")
    void deleteAll();
}
