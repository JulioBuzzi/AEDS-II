import java.util.*;

class Exercicio1{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.nextLine();
        while(!"FIM".equals(string)){ //Loop que lê strings até o fim do arquivo (determinado pela string FIM)
            if(ehPalindromo(string)) System.out.println("SIM"); //Imprime o resultado("SIM" se for palindromo e "NAO" se nao for palindromo)
            else System.out.println("NAO");
            string = scanner.nextLine();
        }

        scanner.close();
    }

    private static boolean ehPalindromo(String string){ //Função que verifica se uma string é um palíndromo
        boolean ehPalindromo = true;
        int  i = 0;
        while(i < string.length()/2 && ehPalindromo) //Loop para comparar os caracteres da string
        {
            if(string.charAt(i) != string.charAt(string.length() -1 -i)) //Compara o caractere da posição i com o caractere da posição correspondente do final
            {
                ehPalindromo = false; //Se os caracteres não forem iguais, ou forem caracteres não letras, não é um palíndromo
            }
            i++;
        }
        return ehPalindromo;
        }
}