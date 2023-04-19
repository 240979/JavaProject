import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class Movie
{
    protected String movieName;
    protected String director;
    protected int releaseDate;
    public Movie(String movieName, String director, int releaseDate)
    {
        this.movieName = movieName;
        this.director = director;
        this.releaseDate = releaseDate;
    }
    public Movie()
    {

    }
    public abstract void consoleInput();
    public abstract boolean containPerformer(String name);

    public String getMovieName()
    {
        return this.movieName;
    }

    public String getDirector()
    {
        return this.director;
    }

    public int getReleaseDate()
    {
        return releaseDate;
    }
    public abstract List<String > getStaff();
    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public void setReleaseDate(int releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    @Override
    public abstract String toString();

}
