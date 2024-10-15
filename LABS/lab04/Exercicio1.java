import java.util.ArrayList;
import java.util.Scanner;
class Exercicio1{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] vet;

        ArrayList<ArrayList<Integer>> mod;

        while(n != 0 && m != 0){
            System.out.println(n + " " + m);

            vet = new int[n];

            for(int i = 0; i < n; i++){
                vet[i] = scanner.nextInt();
            }

            mod = new ArrayList<>(2 * m);
            for(int i = 0; i < 2*m; i += 2){
                mod.add(new ArrayList<>());
                mod.add(new ArrayList<>());
                for(int j = 0; j < n; j++){
                    if(vet[j] % m == i/2){
                        if(vet[j] % 2 == 1) mod.get(i).add(vet[j]);
                        else mod.get(i+1).add(vet[j]);
                    }
                }
            }
            for(int i = 0; i < 2 * m; i++){
                if(mod.get(i) != null && !mod.get(i).isEmpty()){
                    if(i % 2 == 0) chamadorQuickSortDecrescente(mod.get(i));
                    else chamadorQuickSortCrescente(mod.get(i));
                    for(int j = 0; j < mod.get(i).size(); j++) System.out.println(mod.get(i).get(j));
                }
            }
            n = scanner.nextInt();
            m = scanner.nextInt();
            if(n == 0 && m == 0) System.out.println("0 0");
        }
        scanner.close();
    }
    public static void chamadorQuickSortDecrescente(ArrayList<Integer> arrayList){
        QuickSortDecrescente(arrayList, 0, arrayList.size() - 1);
    }
    public static void QuickSortDecrescente(ArrayList<Integer> arrayList, int esq, int dir){
        int i = esq;
        int j = dir;
        int pivo = arrayList.get((esq + dir)/2);
        while(i <= j){
            while(arrayList.get(i) > pivo) i++;
            while(arrayList.get(j) < pivo) j--;
            if(i <= j){
                int tmp = arrayList.get(j);
                arrayList.set(j, arrayList.get(i));
                arrayList.set(i, tmp);
                i++;
                j--;
            }
        }
        if(i < dir) QuickSortDecrescente(arrayList, i, dir);
        if(j > esq) QuickSortDecrescente(arrayList, esq, j);
    }
    public static void chamadorQuickSortCrescente(ArrayList<Integer> arrayList){
        QuickSortCrescente(arrayList, 0, arrayList.size() - 1);
    }
    public static void QuickSortCrescente(ArrayList<Integer> arrayList, int esq, int dir){
        int i = esq;
        int j = dir;
        
        int pivo = arrayList.get((esq + dir)/2);
        while(i <= j){
            while(arrayList.get(i) < pivo) i++;
            while(arrayList.get(j) > pivo) j--;
            if(i <= j){
                int tmp = arrayList.get(j);
                arrayList.set(j, arrayList.get(i));
                arrayList.set(i, tmp);
                i++;
                j--;
            }
        }

        if(i < dir) QuickSortCrescente(arrayList, i, dir);
        if(j > esq) QuickSortCrescente(arrayList, esq, j);
    }
}
