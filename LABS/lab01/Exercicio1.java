import java.util.Scanner;

public class Exercicio1 {

    public static int contarMaiusculas(String texto) {
        int contador = 0;
        for (char caractere : texto.toCharArray()) {
            if (Character.isUpperCase(caractere)) {
                contador++;
            }
        }
        return contador;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada;

        while (true) {
            entrada = scanner.nextLine();
            if (entrada.equals("FIM")) {
                break; 
            }

            int maiusculas = contarMaiusculas(entrada);
            System.out.println(maiusculas);
        }

        scanner.close();
    }
}
