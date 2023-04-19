import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MovieList
{
    private List<LiveActionMovie> _actionList;
    private List<AnimatedMovie> _animatedList;
    public MovieList()
    {
        this._actionList = new ArrayList<>();
        this._animatedList = new ArrayList<>();
    }
    public MovieList(List<AnimatedMovie> animatedMovies, List<LiveActionMovie> liveActionMovies)
    {
        this._actionList = liveActionMovies;
        this._animatedList = animatedMovies;
    }
    public boolean addMovie(Movie movie)
    {
        if(checkForMovie(movie.getMovieName()))
            return false;
        if(movie instanceof AnimatedMovie)
        {
            this._animatedList.add((AnimatedMovie) movie);
        }
        else
        {
            this._actionList.add((LiveActionMovie) movie);
        }
        return true;
    }
    public boolean addReview(String movieName)
    {
        int index;
        if(!checkForMovie(movieName))
            return false;
        Movie movie = this.findMovie(movieName);
        if(movie instanceof AnimatedMovie)
        {
            index = this._animatedList.indexOf(movie);
            ((AnimatedMovie) movie).createConsoleReview();
            this._animatedList.set(index, (AnimatedMovie) movie);
        }
        else
        {
            index = this._actionList.indexOf(movie);
            ((LiveActionMovie)movie).createConsoleReview();
            this._actionList.set(index, (LiveActionMovie) movie);
        }
        return true;
    }
    public boolean deleteMovie(String movieName)
    {
        if(!checkForMovie(movieName))
            return false;
        Movie movie = this.findMovie(movieName);
        if(movie instanceof AnimatedMovie)
        {
            this._animatedList.remove(movie);
        }
        else
        {
            this._actionList.remove(movie);
        }
        return true;
    }
    public boolean checkForMovie(String name)
    {
        Movie tmp = this.findMovie(name);
        if(this.findMovie(name).getMovieName().equals("Not found"))
            return false;
        return true;
    }
    public Movie findMovie(String name)
    {
        for (AnimatedMovie item : _animatedList)
        {
            if(item.getMovieName().equals(name))
            {
                return item;
            }
        }
        for (LiveActionMovie item : _actionList)
        {
            if(item.getMovieName().equals(name))
            {
                return item;
            }
        }
        return new LiveActionMovie("Not found", "Not found", -1);
    }
    public Map<String, List<String>> getPerformerFromManyMovies()
    {
        HashMap<String, Integer> staffAndFreq = new HashMap<>();
        for (Movie item : this._actionList)
        {
            List<String> currentStaff = item.getStaff();
            for(String guy : currentStaff)
            {
                if(!staffAndFreq.containsKey(guy))
                {
                    staffAndFreq.put(guy, 0);
                }
                else
                {
                    int freq = staffAndFreq.get(guy);
                    freq++;
                    staffAndFreq.replace(guy, freq);
                }
            }
        }
        for (Movie item : this._animatedList)
        {
            List<String> currentStaff = item.getStaff();
            for(String guy : currentStaff)
            {
                if(!staffAndFreq.containsKey(guy))
                {
                    staffAndFreq.put(guy, 0);
                }
                else
                {
                    int freq = staffAndFreq.get(guy);
                    freq++;
                    staffAndFreq.replace(guy, freq);
                }
            }
        }
        Map<String, Integer> filtered = staffAndFreq.entrySet().stream()
                .filter(x -> x.getValue() > 0)
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
        List<String> filteredList = new ArrayList<>(filtered.keySet());
        Map<String, List<String>> listMap = new HashMap<>();
        for(String performer : filteredList)
        {
            List<String> performersMovies = this.getPerformersMovies(performer);
            listMap.put(performer, performersMovies);
        }
        return listMap;
    }
    public List<String> getPerformersMovies(String performer)
    {
        List<String> performersMovies = new ArrayList<>();
        for(Movie item : this._actionList)
        {
            if(item.containPerformer(performer))
            {
                performersMovies.add(item.getMovieName());
            }
        }
        for(Movie item : this._animatedList)
        {
            if(item.containPerformer(performer))
            {
                performersMovies.add(item.getMovieName());
            }
        }
        return performersMovies;
    }
    public List<AnimatedMovie> getAnimatedMovies()
    {
        return this._animatedList;
    }
    public List<LiveActionMovie> getLiveActionMovies()
    {
        return this._actionList;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("=======================================================\n");
        sb.append("Animované filmy: \n");
        for (AnimatedMovie item: _animatedList)
        {
            sb.append(item.toString());
            sb.append("\n----------------------------------------------------\n");
        }
        sb.append("=======================================================\n");
        sb.append("Hrané filmy: \n");
        for (LiveActionMovie item: _actionList)
        {
            sb.append(item.toString());
            sb.append("\n----------------------------------------------------\n");
        }
        return sb.toString();
    }
    public void saveToFile(String movieName) throws  IOException
    {

        Movie tmp = this.findMovie(movieName);
        FileWriter fileWriter = new FileWriter(movieName.replace(":", "") + ".txt");
        if(tmp instanceof AnimatedMovie)
        {
            fileWriter.write("Sekce: Animované filmy\n");
        }
        else
        {
            fileWriter.write("Sekce: Hrané filmy\n");
        }
        fileWriter.write(findMovie(movieName).toString());
        fileWriter.close();
    }
    public boolean loadFromFile(String fileName) throws  IOException, IndexOutOfBoundsException
    {

        File file = new File(fileName + ".txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader buff = new BufferedReader(fileReader);
        String[] lines = new String[7];
        int i =0;
        for(; i < 6; i++)
        {
            lines[i] = buff.readLine();
        }
        if (lines[0].equals("Sekce: Animované filmy"))
        {
            AnimatedMovie animatedMovie = new AnimatedMovie();
            String[] split;
            lines[i] = buff.readLine();
            split = lines[1].split("^([^:]+): ");
            if(!(this.checkForMovie(split[1])))
            {
                animatedMovie.setMovieName(split[1]);
                split = lines[2].split("^([^:]+): ");
                animatedMovie.setDirector(split[1]);
                split = lines[3].split("^([^:]+): ");
                animatedMovie.setReleaseDate(Integer.parseInt(split[1]));
                split = lines[4].split("^([^:]+): ");
                animatedMovie.setMinimalAge(Integer.parseInt(split[1]));
                split = lines[5].split("^([^:]+): ");
                animatedMovie.deserializeAnimators(split[1]);
                split = lines[6].split("^([^:]+): ");
                try
                {
                    animatedMovie.deserializeScore(split[1]);
                }
                catch (ArrayIndexOutOfBoundsException ignoreNotFoundReviews)
                {

                }
                return this._animatedList.add(animatedMovie);
            }
        }
        else
        {
            LiveActionMovie liveActionMovie = new LiveActionMovie();
            String[] split;
            lines[i] = buff.readLine();
            split = lines[1].split("^([^:]+): ");
            if(!this.checkForMovie(split[1]))
            {
                liveActionMovie.setMovieName(split[1]);
                split = lines[2].split("^([^:]+): ");
                liveActionMovie.setDirector(split[1]);
                split = lines[3].split("^([^:]+): ");
                liveActionMovie.setReleaseDate(Integer.parseInt(split[1]));
                split = lines[4].split("^([^:]+): ");
                liveActionMovie.deserializeActors(split[1]);
                split = lines[5].split("^([^:]+): ");
                try
                {
                    liveActionMovie.deserializeScore(split[1]);
                }
                catch (ArrayIndexOutOfBoundsException ignoreNotFoundReviews)
                {

                }
                return this._actionList.add(liveActionMovie);
            }
        }
        fileReader.close();
        return false;
    }
}
