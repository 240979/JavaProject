import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager
{
    private static Connection _connect() throws SQLException, ClassNotFoundException
    {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:movies.db");
        return con;
    }
    public static MovieList load() throws SQLException, ClassNotFoundException
    {
        List<AnimatedMovie> animatedMovieList = new ArrayList<>();
        List<LiveActionMovie> liveActionMovieList = new ArrayList<>();
        Connection con = _connect();
        String query = "SELECT * FROM animated";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next())
        {
            AnimatedMovie tmp = new AnimatedMovie(resultSet.getString("name"), resultSet.getString("director"), resultSet.getInt("release_date"), resultSet.getInt("min_age"));
            tmp.deserializeAnimators(resultSet.getString("serial_animators"));
            tmp.deserializeScore(resultSet.getString("serial_score"));
            animatedMovieList.add(tmp);
        }
        query = "SELECT * FROM live_action";
        statement = con.createStatement();
        resultSet = statement.executeQuery(query);
        while(resultSet.next())
        {
            LiveActionMovie tmp = new LiveActionMovie(resultSet.getString("name"), resultSet.getString("director"), resultSet.getInt("release_date"));
            tmp.deserializeActors(resultSet.getString("serial_actors"));
            tmp.deserializeScore(resultSet.getString("serial_score"));
            liveActionMovieList.add(tmp);
        }
        con.close();

        return new MovieList(animatedMovieList, liveActionMovieList);
    }
    public static void save(MovieList movieList) throws SQLException, ClassNotFoundException
    {
        Connection con = _connect();
        Statement statement = con.createStatement();
        statement.executeUpdate("DROP TABLE animated");
        statement.executeUpdate("CREATE TABLE animated (name VARCHAR(100) NOT NULL, director VARCHAR(100) NOT NULL, release_date INT NOT NULL, min_age INT NOT NULL, serial_animators VARCHAR(5000) NOT NULL, serial_score VARCHAR(5000) NOT NULL)");
        statement.executeUpdate("DROP TABLE live_action ");
        statement.executeUpdate("CREATE TABLE live_action (name VARCHAR(100), director VARCHAR(100) NOT NULL, release_date INT NOT NULL, serial_actors VARCHAR(5000) NOT NULL, serial_score VARCHAR(5000) NOT NULL)");
        String query = "INSERT INTO animated VALUES(?,?,?,?,?,?)";
        List<AnimatedMovie> animatedMovieList = movieList.getAnimatedMovies();
        List<LiveActionMovie> liveActionMovieList = movieList.getLiveActionMovies();
        PreparedStatement preparedStatement = con.prepareStatement(query);
        for (AnimatedMovie item : animatedMovieList)
        {
            preparedStatement.setString(1, item.getMovieName());
            preparedStatement.setString(2, item.getDirector());
            preparedStatement.setInt(3, item.getReleaseDate());
            preparedStatement.setInt(4, item.getMinimalAge());
            preparedStatement.setString(5, item.serializeAnimators());
            preparedStatement.setString(6, item.serializeScore());
            preparedStatement.executeUpdate();
        }
        query = "INSERT INTO live_action VALUES(?,?,?,?,?)";
        preparedStatement = con.prepareStatement(query);
        for(LiveActionMovie item : liveActionMovieList)
        {
            preparedStatement.setString(1, item.getMovieName());
            preparedStatement.setString(2, item.getDirector());
            preparedStatement.setInt(3, item.getReleaseDate());
            preparedStatement.setString(4, item.serializeActors());
            preparedStatement.setString(5, item.serializeScore());
            preparedStatement.executeUpdate();
        }
        con.close();
    }
}
