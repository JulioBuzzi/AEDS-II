import java.util.*;
public class Exercicio14 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        while(!"FIM".equals(string)){
            if(soVogal(string)) System.out.print("SIM ");
            else System.out.print("NAO ");
            if(soConsoante(string)) System.out.print("SIM ");
            else System.out.print("NAO ");
            if(ehInteiro(string)) System.out.print("SIM ");
            else System.out.print("NAO ");
            if(ehFloat(string)) System.out.println("SIM");
            else System.out.println("NAO");
            string = scanner.nextLine();
        }
        scanner.close();
    }
    private static boolean soVogal(String string){
        return soVogalRec(string, 0);
    }
    private static boolean soVogalRec(String string, int i){
        boolean flag = true;
        if(i < string.length()){
            flag = (string.charAt(i) == 'A' || string.charAt(i) == 'E' || string.charAt(i) == 'I' || string.charAt(i) == 'O' || string.charAt(i) == 'U' || string.charAt(i) == 'a' || string.charAt(i) == 'e' || string.charAt(i) == 'i' || string.charAt(i) == 'o' || string.charAt(i) == 'u') && soVogalRec(string, i + 1);
        }
        return flag;
    }
    private static boolean soConsoante(String string){
        return soConsoanteRec(string, 0);
    }
    private static boolean soConsoanteRec(String string, int i){
        boolean flag = true;
        if(i < string.length()){
            flag = ((string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') || (string.charAt(i) >= 'a' && string.charAt(i) <= 'z')) && (string.charAt(i) != 'A' && string.charAt(i) != 'E' && string.charAt(i) != 'I' && string.charAt(i) != 'O' && string.charAt(i) != 'U' && string.charAt(i) != 'a' && string.charAt(i) != 'e' && string.charAt(i) != 'i' && string.charAt(i) != 'o' && string.charAt(i) != 'u') && soConsoanteRec(string, i + 1);
        }
        return flag;
    }
    private static boolean ehInteiro(String string){
        return ehInteiroRec(string, 0);
    }
    private static boolean ehInteiroRec(String string, int i){
        boolean flag = true;
        if(i < string.length()){
            flag = (string.charAt(i) >= 48 && string.charAt(i) <= 58) && ehInteiroRec(string, i + 1) && ehInteiroRec(string, i+1);
            }
        return flag;
    }
    private static boolean ehFloat(String string){
        return ehFloatRec(string, 0, 0);
    }
    private static boolean ehFloatRec(String string, int i, int conta){
        boolean flag = true;
        if(i < string.length()){
            if(string.charAt(i) >= 48 && string.charAt(i) <= 57){
                flag = conta <= 1 & ehFloatRec(string, i + 1, conta);
            }
            else if(string.charAt(i) == 44 || string.charAt(i) == 46) flag = conta <= 1    && ehFloatRec(string, i + 1, conta + 1);
            else flag = false;
            }
        return flag;
    }
}