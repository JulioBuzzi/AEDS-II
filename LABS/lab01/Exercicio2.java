import java.util.Scanner;

public class Exercicio2 {

    public static int verificaMaiscula(String string) {
        int maisculas = 0;
        String fim = "FIM"; 

        if (string.equals(fim)) {
            return 0;
        } else {
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') {
                    maisculas++;
                }
            }
            return maisculas;
        }
    }

    public static void processarEntrada(Scanner scanner) {
        //System.out.print("Digite o texto: ");
        String string = scanner.nextLine();

        int resultado = verificaMaiscula(string);
        if (resultado == 0) {
            return; 
        } else {
            System.out.println(resultado);
        }

        // Recursão para continuar processando a entrada
        processarEntrada(scanner);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        processarEntrada(scanner);
        scanner.close();
    }
}
