import java.util.Scanner;

public class Exercicio2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //faz a leitura e caso true gera a sequencia espelho

        while (scanner.hasNextInt()) {
            int inicio = scanner.nextInt();

            if (scanner.hasNextInt()) {
                int fim = scanner.nextInt();

                if (inicio <= fim) {
                    String resultado = gerarSequenciaEspelho(inicio, fim);
                    System.out.println(resultado);
                }
                
            }
        }

        scanner.close(); 
    }

    //funcao espelho

    public static String gerarSequenciaEspelho(int inicio, int fim) {
        StringBuilder crescente = new StringBuilder();
        for (int i = inicio; i <= fim; i++) {
            crescente.append(i);
        }

        //reverte a funcao para formar a segunda parte da espelho
        String espelho = crescente.reverse().toString();

        
        crescente.reverse();

        return crescente.toString() + espelho;
    }
}
