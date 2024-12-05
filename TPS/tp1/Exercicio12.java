import java.util.*;

public class Exercicio12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.nextLine();
        
        while(!"FIM".equals(string)){
            System.out.println(ciframento(string));
            string = scanner.nextLine();
        }
        scanner.close();
    }
    private static String ciframento(String string){
        return ciframentoRec(string,0,new StringBuilder());
    }

    private static String ciframentoRec(String string,int i, StringBuilder newString) {
        if(i >= string.length()) {
            return newString.toString();
        }
        
        char carac = string.charAt(i);
        if(carac < 1 || carac > 127) {
            newString.append(carac);
        } else {
            char novoCarac = (char) (carac + 3);
            newString.append(novoCarac); 
        }

        return ciframentoRec(string,i+1,newString);
    }
}

