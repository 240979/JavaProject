import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AnimatedMovie extends Movie
{
    List<String> animators, score;
    int  minimalAge;
    public AnimatedMovie(String movieName, String director, int releaseDate, int age)
    {
        super(movieName, director, releaseDate);
        this.minimalAge = age;
  //      this.animators = new ArrayList<>();
  //      this.score = new ArrayList<>();
    }
    public AnimatedMovie()
    {
        super();
        this.animators = new ArrayList<>();
        this.minimalAge = 0;
        this.score = new ArrayList<>();
    }
    public String serializeAnimators()
    {
        StringBuilder sb = new StringBuilder("");
        for (String animator: this.animators)
        {
            sb.append(animator);
            sb.append(", ");
        }
        sb = OtherTools.deleteLastSymbols(sb, 2);
        return sb.toString();
    }
    public void deserializeAnimators(String serialData)
    {
        String[] split = serialData.split(", ?");
        this.animators = new ArrayList<>(Arrays.asList(split));
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
    public void addScore(int points, String review)
    {
        this.score.add(points + "/10 - " + review);
    }
    public void createConsoleReview()
    {
        int score;
        String review;
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadejte hodnocení: (1 až 10)");
        score = OtherTools.intScannerInterval(1, 10);
        System.out.println("Doplňte případnou textovou recenzi: ([ENTER] - přeskočit)");
        review = sc.nextLine();
        this.addScore(score, review);
    }
    public void consoleInput()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadejte název animovaného filmu: ");
        this.movieName = sc.nextLine();
        System.out.println("Zadejte jméno režiséra: ");
        this.director = sc.nextLine();
        System.out.println("Zadejte rok vydání: ");
        this.releaseDate = OtherTools.intScanner(sc);
        System.out.println("Zadejte jména tvůrců (Jména oddělte čárkou s mezerou): ");
        sc.nextLine();
        String tmp = sc.nextLine();
        this.deserializeAnimators(tmp);
        System.out.println("Zadejte doporučený věk diváka: ");
        this.minimalAge = OtherTools.intScanner(sc);

    }
    public boolean checkForAnimator(String animatorsName)
    {
        if(this.animators.contains(animatorsName))
        {
            return true;
        }
        return false;
    }
    public boolean addAnimator(String animatorsName)
    {
        if(!this.checkForAnimator(animatorsName))
        {
            this.animators.add(animatorsName);
        }
        return !this.checkForAnimator(animatorsName);
    }
    public boolean deleteAnimator(String animatorsName)
    {
        if(this.checkForAnimator(animatorsName))
        {
            this.animators.remove(animatorsName);
        }
        return this.checkForAnimator(animatorsName);
    }
    public List<String > getStaff()
    {
        return this.animators;
    }
    public boolean containPerformer(String name)
    {
        for(String item : this.animators)
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
        sb.append("\nDoporučený věk: ");
        sb.append(this.minimalAge);
        sb.append("\nTvůrci: ");
        sb.append(this.serializeAnimators());
        sb.append("\nHodnocení: ");
        sb.append(this.serializeScore());
        return sb.toString();
    }
    public int getMinimalAge()
    {
        return this.minimalAge;
    }
    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }
}
