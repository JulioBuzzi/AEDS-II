import java.util.Scanner;
import java.io.RandomAccessFile;

public class Exercicio8 {
    
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
       //System.out.println("Numero inteiro: ");
        int n = scanner.nextInt();

        RandomAccessFile arq = new RandomAccessFile("reais.txt", "rw");
        double num;

        for(int i = 0;i < n; i++) {
            num = scanner.nextDouble();
            arq.writeDouble(num);
        }
        arq.close();

        arq = new RandomAccessFile("reais.txt","r") ;

        for(int i = n-1; i >= 0; i--) {
            arq.seek(i*8);
            double leitura  = arq.readDouble();

            if(leitura % 1 == 0) {
                System.out.println((int)leitura);
            } else System.out.println(leitura);
        }
        arq.close();
        scanner.close();
    }   
}

