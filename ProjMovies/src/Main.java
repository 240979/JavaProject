import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        MovieList movieList = new MovieList();
        try
        {
            movieList = DbManager.load();
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.out.println("Nastala vyjimka při načítání!");
            System.exit(404);
        }
        boolean run = true;
        int choice;
        Scanner sc = new Scanner(System.in);
        do
        {
            System.out.println(" 1 ... Vložení nového animovaného filmu");
            System.out.println(" 2 ... Vložení nového hraného filmu");
            System.out.println(" 3 ... Výpis databáze");
            System.out.println(" 4 ... Vyhledání informací podle názvu");
            System.out.println(" 5 ... Uložení vybraného filmu do souboru");
            System.out.println(" 6 ... Načtení vybraného filmu ze souboru");
            System.out.println(" 7 ... Úprava vybraného filmu");
            System.out.println(" 8 ... Smazání filmu");
            System.out.println(" 9 ... Přidání recenze k filmu");
            System.out.println("10 ... Výpis účinkujících ve více filmech");
            System.out.println("11 ... Výpis filmů účinkujícícho");
            System.out.println(" 0 ... Konec");
            choice = OtherTools.intScanner(sc);
            switch(choice)
            {
                case 1:
                    AnimatedMovie animatedMovie = new AnimatedMovie();
                    animatedMovie.consoleInput();
                    if(!movieList.addMovie(animatedMovie))
                        System.out.println("Film se stejným názvem už exsituje");
                    break;
                case 2:
                    LiveActionMovie liveActionMovie = new LiveActionMovie();
                    liveActionMovie.consoleInput();
                    if(!movieList.addMovie(liveActionMovie))
                        System.out.println("Film se stejným názvem už exsituje");
                    break;
                case 3:
                    System.out.println(movieList.toString());
                    break;
                case 4:
                    System.out.println("Zadejte název: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    Movie movie = movieList.findMovie(name);
                    if(movie.getMovieName().equals("Not found"))
                    {
                        System.out.println("Film nebyl nalezen");
                    }
                    else
                    {
                        if (movie instanceof AnimatedMovie)
                        {
                            System.out.println("Nalezen film v sekci animované");
                        }
                        else
                        {
                            System.out.println("Nalezen film v sekci hrané");
                        }
                        System.out.println(movie.toString());
                    }
                    break;
                case 5:
                    System.out.println("Zadejte název: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    if(!movieList.checkForMovie(name))
                    {
                        System.out.println("Film nebyl nalezen");
                    }
                    else
                    {
                        try
                        {
                            movieList.saveToFile(name);
                        }
                        catch (IOException  e)
                        {
                            System.out.println("Se souborem je něco špatně.");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Zadejte název: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    try
                    {
                        if(!movieList.loadFromFile(name))
                        {
                            System.out.println("Film se stejným názvem už existuje");
                        }
                    }
                    catch(IOException e)
                    {
                        System.out.println("Se souborem je něco špatně.");
                    }
                    catch (IndexOutOfBoundsException  e)
                    {
                        System.out.println("Soubor má špatnou strukturu.");
                    }
                    break;
                case 7:
                    System.out.println("Zadejte název: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    movie = movieList.findMovie(name);
                    if(movie.getMovieName().equals("Not found"))
                    {
                        System.out.println("Film nebyl nalezen");
                    }
                    else
                    {
                        boolean innerRun = true;
                        do
                        {
                            System.out.println("1 ... Upravit název");
                            System.out.println("2 ... Upravit režiséra");
                            System.out.println("3 ... Upravit rok vydání");
                            if(movie instanceof LiveActionMovie)
                            {
                                System.out.println("4 ... Přidat herce");
                                System.out.println("5 ... Smazat herce");
                                System.out.println("0 ... Zpět");
                                int innerChoice = OtherTools.intScanner(sc);
                                switch (innerChoice)
                                {
                                    case 1:
                                        System.out.println("Aktuální název: " + movie.getMovieName());
                                        System.out.println("Zadejte nový název: ");
                                        sc.nextLine();
                                        name = sc.nextLine();
                                        movie.setMovieName(name);
                                        break;
                                    case 2:
                                        System.out.println("Aktuální jméno režiséra: " + movie.getDirector());
                                        System.out.println("Zadejte nové jméno režiséra: ");
                                        sc.nextLine();
                                        String director = sc.nextLine();
                                        movie.setDirector(director);
                                        break;
                                    case 3:
                                        System.out.println("Aktuální rok vydání: " + movie.getReleaseDate());
                                        System.out.println("Zadejte nový rok vydání: ");
                                        sc.nextLine();
                                        int year = OtherTools.intScannerInterval(1900, 2023);
                                        movie.setReleaseDate(year);
                                        break;
                                    case 4:
                                        System.out.println("Aktuální seznam herců: " + ((LiveActionMovie) movie).serializeActors());
                                        System.out.println("Zadejte nového herce: ");
                                        sc.nextLine();
                                        String actor = sc.nextLine();
                                        if(!((LiveActionMovie) movie).addActor(actor))
                                            System.out.println("Akce se nezdařila");
                                        break;
                                    case 5:
                                        System.out.println("Aktuální seznam herců: " + ((LiveActionMovie) movie).serializeActors());
                                        System.out.println("Zadejte jméno pro smazání: ");
                                        sc.nextLine();
                                        actor = sc.nextLine();
                                        if(!((LiveActionMovie) movie).deleteActor(actor))
                                            System.out.println("Akce se nezdařila");
                                        break;
                                    case 0: innerRun = false;
                                        break;
                                }
                            }
                            else
                            {
                                System.out.println("4 ... Přidat animátora");
                                System.out.println("5 ... Smazat animátora");
                                System.out.println("6 ... Upravit doporučený divácký věk");
                                System.out.println("0 ... Zpět");
                                int innerChoice = OtherTools.intScanner(sc);
                                switch (innerChoice)
                                {
                                    case 1:
                                        System.out.println("Aktuální název: " + movie.getMovieName());
                                        System.out.println("Zadejte nový název: ");
                                        sc.nextLine();
                                        name = sc.nextLine();
                                        movie.setMovieName(name);
                                        break;
                                    case 2:
                                        System.out.println("Aktuální jméno režiséra: " + movie.getDirector());
                                        System.out.println("Zadejte nové jméno režiséra: ");
                                        sc.nextLine();
                                        String director = sc.nextLine();
                                        movie.setDirector(director);
                                        break;
                                    case 3:
                                        System.out.println("Aktuální rok vydání: " + movie.getReleaseDate());
                                        System.out.println("Zadejte nový rok vydání: ");
                                        sc.nextLine();
                                        int year = OtherTools.intScannerInterval(1900, 2023);
                                        movie.setReleaseDate(year);
                                        break;
                                    case 4:
                                        System.out.println("Aktuální seznam animátorů: " + ((AnimatedMovie) movie).serializeAnimators());
                                        System.out.println("Zadejte nového animátora: ");
                                        sc.nextLine();
                                        String animator = sc.nextLine();
                                        if(!((AnimatedMovie) movie).addAnimator(animator))
                                            System.out.println("Akce se nezdařila");
                                        break;
                                    case 5:
                                        System.out.println("Aktuální seznam animátorů: " + ((AnimatedMovie) movie).serializeAnimators());
                                        System.out.println("Zadejte jméno pro smazání: ");
                                        sc.nextLine();
                                        animator = sc.nextLine();
                                        if(!((AnimatedMovie) movie).deleteAnimator(animator))
                                            System.out.println("Akce se nezdařila");
                                        break;
                                    case 6:
                                        System.out.println("Aktuální doporučený divácký věk: " + ((AnimatedMovie) movie).getMinimalAge());
                                        System.out.println("Zadejte nový doporučený věk: ");
                                        sc.nextLine();
                                        int age = OtherTools.intScannerInterval(1,18);
                                        movie.setReleaseDate(age);
                                        break;
                                    case 0: innerRun = false;
                                        break;
                                }
                            }
                        }while(innerRun);
                    }
                    break;
                case 8:
                    System.out.println("Zadejte název: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    if(!movieList.deleteMovie(name))
                    {
                        System.out.println("Film nebyl nalezen");
                    }
                    break;
                case 9:
                    System.out.println("Zadejte název: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    if(!movieList.addReview(name))
                        System.out.println("Film nebyl nalezen");
                    break;
                case 10:
                    System.out.println(OtherTools.mapOfListsToString(movieList.getPerformerFromManyMovies()));
                    break;
                case 11:
                    System.out.println("Zadejte jméno: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    System.out.println(OtherTools.listOfStringsToString(movieList.getPerformersMovies(name)));
                    break;
                case 0:
                    run = false;
                    break;
            }
        }while(run);
        try
        {
            DbManager.save(movieList);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.out.println("Nastala vyjimka při ukládání!");
            System.exit(404);
        }
    }
}