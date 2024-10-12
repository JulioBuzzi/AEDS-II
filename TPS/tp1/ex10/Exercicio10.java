import java.util.Scanner;

public class Exercicio10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        while(!"FIM".equals(string)) {
            if(palindromoRec(string)) System.out.println("SIM");
            else System.out.println("NAO");

            string = scanner.nextLine();
        }

        scanner.close();
    }

    private static boolean palindromoRec(String string) {
        return palindromoRec2(string, 0,string.length()-1);
    }

    private static boolean palindromoRec2(String string, int i, int j) {
        boolean flag;

        if(i < j) flag = string.charAt(i) == string.charAt(j) && palindromoRec2(string, i+1, j-1);
        else flag = true;

        return flag;
    }

}
