import java.util.*;

public class Formula17 {
    
    
    public static int contarUltrapassagens(int[] largada, int[] chegada) {
        
        int n = largada.length;
        int[] posicaoChegada = new int[n + 1]; 
        for (int i = 0; i < n; i++) {
            posicaoChegada[chegada[i]] = i;
        }

        
        int[] largadaConvertida = new int[n];
        for (int i = 0; i < n; i++) {
            largadaConvertida[i] = posicaoChegada[largada[i]];
        }

        
        return mergeSortContarInversoes(largadaConvertida, 0, n - 1);
    }

    public static int mergeSortContarInversoes(int[] array, int esquerda, int direita) {
        if (esquerda >= direita) {
            return 0;
        }
        
        int meio = (esquerda + direita) / 2;
        int inversoes = 0;
        
        inversoes += mergeSortContarInversoes(array, esquerda, meio);
        inversoes += mergeSortContarInversoes(array, meio + 1, direita);
        inversoes += merge(array, esquerda, meio, direita);
        
        return inversoes;
    }

    
    public static int merge(int[] array, int esquerda, int meio, int direita) {
        int[] aux = new int[direita - esquerda + 1];
        int i = esquerda, j = meio + 1, k = 0, inversoes = 0;

        while (i <= meio && j <= direita) {
            if (array[i] <= array[j]) {
                aux[k++] = array[i++];
            } else {
                aux[k++] = array[j++];
                inversoes += (meio - i + 1); 
            }
        }

        while (i <= meio) {
            aux[k++] = array[i++];
        }

        while (j <= direita) {
            aux[k++] = array[j++];
        }

        for (i = esquerda; i <= direita; i++) {
            array[i] = aux[i - esquerda];
        }

        return inversoes;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            
            int N = scanner.nextInt();
            int[] largada = new int[N];
            int[] chegada = new int[N];

            
            for (int i = 0; i < N; i++) {
                largada[i] = scanner.nextInt();
            }

            
            for (int i = 0; i < N; i++) {
                chegada[i] = scanner.nextInt();
            }

            
            int resultado = contarUltrapassagens(largada, chegada);
            System.out.println(resultado);
        }

        scanner.close();
    }
}
