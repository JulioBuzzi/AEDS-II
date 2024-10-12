import java.util.Random;
import java.util.Scanner;

public class Exercicio4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        while(!"FIM".equals(string)) {
            System.out.println(trocarLetras(string));
            string = scanner.nextLine();
        }
        scanner.close();
    }



    private static String trocarLetras(String string) {
        Random gerador = new Random();
        gerador.setSeed(4);

        char primeira = (char) ('a' + (Math.abs(gerador.nextInt())% 26));
        char segunda = (char) ('a' + (Math.abs(gerador.nextInt())% 26));

        StringBuilder newString = new StringBuilder();

        for(int i=0;i<string.length();i++) {
            if(string.charAt(i) == primeira) newString.append(segunda);
            else newString.append(string.charAt(i));
        }
        

        return newString.toString();
    }
}
