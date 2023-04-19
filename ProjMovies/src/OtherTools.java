import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OtherTools
{
    public static int intScanner(Scanner scanner)
    {
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (Exception e)
        {
            System.out.println("Špatný formát!");
            scanner.nextLine();
            input = intScanner(scanner);
        }
        return input;
    }
    public static int intScannerInterval(int min, int max)
    {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        input = intScanner(sc);
        if(input < min || input > max)
        {
            System.out.println("Číslo není v zadaném intervalu!");
            input = intScannerInterval(min, max);
        }
        return input;
    }
    public static StringBuilder deleteLastSymbols(StringBuilder sb, int count)
    {
        if(sb.length() > count)
        {
            for(int i = 0; i < count; i++)
                sb.deleteCharAt(sb.length()-1);
        }
        return sb;
    }
    public static String deleteLastSymbols(String s, int count)
    {
        StringBuilder sb = new StringBuilder(s);
        sb = deleteLastSymbols(sb, count);
        return sb.toString();
    }
    public static String mapOfListsToString(Map<String, List<String>> map)
    {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, List<String>> item : map.entrySet())
        {
            sb.append(item.getKey());
            sb.append(": \n");
            sb.append(listOfStringsToString(item.getValue()));
        }
        return sb.toString();
    }
    public static String listOfStringsToString(List<String> list)
    {
        StringBuilder sb = new StringBuilder();
        for(String item : list)
        {
            sb.append(" - ");
            sb.append(item);
            sb.append("\n");
        }
        return sb.toString();
    }
}
