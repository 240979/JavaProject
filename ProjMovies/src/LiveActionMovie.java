import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LiveActionMovie extends Movie
{
    List<String> actors;
    List<String> score;
    public LiveActionMovie(String movieName, String director, int releaseDate)
    {
        super(movieName, director, releaseDate);
    }
    public LiveActionMovie()
    {
        super();
        this.actors = new ArrayList<>();
        this.score = new ArrayList<>();
    }
    public String serializeActors()
    {
        StringBuilder sb = new StringBuilder("");
        for (String actor: actors)
        {
            sb.append(actor);
            sb.append(", ");
        }
        sb = OtherTools.deleteLastSymbols(sb, 2);
        return sb.toString();
    }
    public void addScore(int points, String review)
    {
        this.score.add(points + "/5* - " + review);
    }
    public void createConsoleReview()
    {
        int score;
        String review;
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadejte hodnocení: (1 až 5 *)");
        score = OtherTools.intScannerInterval(1, 5);
        System.out.println("Doplňte případnou textovou recenzi: ([ENTER] - přeskočit)");
        review = sc.nextLine();
        this.addScore(score, review);
    }
    public void deserializeActors(String serialData)
    {
        String[] split = serialData.split(", ?");
        //this.actors = Arrays.stream(split).toList();
        this.actors = new ArrayList<String>(Arrays.asList(split));
        //List<Foo> list = new ArrayList<Foo>(Arrays.asList(array));
    }
    public String serializeScore()
    {
        StringBuilder sb = new StringBuilder("");
        for (String rating: this.score)
        {
            sb.append(rating);
            sb.append("; ");
        }
        sb = OtherTools.deleteLastSymbols(sb, 2);
        return sb.toString();
    }
    public void deserializeScore(String serialData)
    {
        String[] split = serialData.split("; ?");
        this.score = new ArrayList<>(Arrays.asList(split));

    }
    public void consoleInput()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadejte název hraného filmu: ");
        this.movieName = sc.nextLine();
        System.out.println("Zadejte jméno režiséra: ");
        this.director = sc.nextLine();
        System.out.println("Zadejte rok vydání: ");
        this.releaseDate = OtherTools.intScanner(sc);
        System.out.println("Zadejte jména herců (Jména oddělte čárkou s mezerou): ");
        sc.nextLine();
        String tmp = sc.nextLine();
        this.deserializeActors(tmp);
    }
    public boolean checkForActor(String actorsName)
    {
        if(this.actors.contains(actorsName))
        {
            return true;
        }
        return false;
    }
    public boolean addActor(String actorsName)
    {
        if(!this.checkForActor(actorsName))
        {
            this.actors.add(actorsName);
        }
        return !this.checkForActor(actorsName);
    }
    public boolean deleteActor(String actorsName)
    {
        if(this.checkForActor(actorsName))
        {
            this.actors.remove(actorsName);
        }
        return this.checkForActor(actorsName);
    }
    public List<String> getStaff()
    {
        return this.actors;
    }
    public boolean containPerformer(String name)
    {
        for(String item : this.actors)
        {
            if(item.equals(name))
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("");
        sb.append("Název: ");
        sb.append(this.movieName);
        sb.append("\nRežisér: ");
        sb.append(this.director);
        sb.append("\nRok vydání: ");
        sb.append(this.releaseDate);
        sb.append("\nÚčinkující: ");
        sb.append(this.serializeActors());
        sb.append("\nHodnocení: ");
        sb.append(this.serializeScore());
        return sb.toString();
    }

}
