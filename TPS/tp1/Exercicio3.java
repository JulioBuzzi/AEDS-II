import java.util.*;

class Exercicio3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string;
        while(scanner.hasNext()){
            string = scanner.nextLine();
            System.out.println(ciframento(string));
        }
        scanner.close();
    }
    private static String ciframento(String string){
        StringBuilder novaString = new StringBuilder();
        char carac;
        char novoCarac;
        for(int i = 0; i < string.length(); i++){
            carac= string.charAt(i);
            if(carac < 1 || carac > 127){
                novaString.append(carac);
            }
            else{
                novoCarac = (char) (carac + 3);
                novaString.append(novoCarac);
            }
        }
        return novaString.toString();
    }
}