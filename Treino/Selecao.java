import java.util.*;

class Selecao {

	public static void main(String[] args) {
       // Scanner scanner = new Scanner(System.in);
    //Random random = new Random();

        String array[] = {
    "Alice", "Bruno", "Carla", "Daniel", "Eduardo", "Fernanda", "Gabriel", "Helena", "Igor", "Juliana",
    "Karina", "Lucas", "Mariana", "Nicolas", "Olivia", "Paulo", "Renata", "Samuel", "Tatiana", "Vinicius",
    "Aline", "Bruna", "Carlos", "Débora", "Emerson", "Fátima", "Giovanna", "Hugo", "Isabela", "João",
    "Kleber", "Livia", "Mateus", "Nathalia", "Oscar", "Priscila", "Ricardo", "Sandra", "Thiago", "Viviane",
    "Ana", "Beatriz", "César", "Diogo", "Elaine", "Fabiano", "Gustavo", "Henrique", "Ingrid", "Jaqueline",
    "Karen", "Leonardo", "Marcelo", "Natália", "Otávio", "Patrícia", "Roberto", "Simone", "Túlio", "Valéria",
    "André", "Bárbara", "Cláudio", "Diego", "Erica", "Felipe", "Geovana", "Heitor", "Ivone", "Jéssica",
    "Kátia", "Leandro", "Michele", "Neusa", "Orlando", "Pedro", "Rodrigo", "Silvia", "Tânia", "Vitor",
    "Arthur", "Bianca", "Cristina", "Douglas", "Ester", "Flávio", "Gilberto", "Heloísa", "Ivan", "José",
    "Larissa", "Lara", "Luiza", "Mauro", "Murilo", "Rafael", "Rosa", "Sara", "Tiago", "Vanessa"
    };
        selecao(array);

            for (String tempString : array) {
                System.out.print(tempString + " ");
            }
            //  scanner.close();
    }

    public static void selecao(String array[]) {
        int menor;
        for(int i=0;i<array.length-1;i++) {
            menor = i;
            for(int j=i+1;j<array.length;j++) {
                if(array[menor].compareTo(array[j]) > 0)
                menor = j;
            }
            swap(menor,i,array);
        }
    }

    public static void swap(int i, int j,String array[]) {
		String temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}