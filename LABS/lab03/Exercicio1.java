import java.util.*;

class Exercicio1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

      //System.out.println("Digite a expressão booleana: ");
        String string = scanner.nextLine();

        while(!"FIM".equals(string)) { 
        if(confereParenteses(string)) System.out.println("correto");
        else System.out.println("incorreto");
        
        string = scanner.nextLine();
    }
        scanner.close();
    }

    private static boolean confereParenteses(String string) {
        boolean flag = false;
        int contaAbre = 0, contaFecha = 0;

        for(int i=0;i<string.length();i++) {
            if( string.charAt(i) == ')') {
                if(contaAbre <= contaFecha) return flag;
                else contaFecha++;
            }
            if(string.charAt(i) == '(') contaAbre++;
        }

        if(contaAbre == contaFecha) flag = true;
        return flag;
    }


}