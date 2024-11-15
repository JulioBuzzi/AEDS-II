import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Pokemon {
    private int id;
    private int generation;
    private String name;
    private String description;
    private String[] types;
    private String[] abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private Date captureDate;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id; 
    }
    public int getGeneration(){
        return generation; 
    }
    public void setGeneration(int generation){
        this.generation = generation;
    }
    public String getName() {
    return name; 
    }
    public void setName(String name) {
        this.name = name; 
    }
    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }
    public String[] getTypes() { 
        return types; 
    }
    public void setTypes(String[] types) { 
        this.types = types; 
    }
    public String[] getAbilities() { 
        return abilities; 
    }
    public void setAbilities(String[] abilities) { 
        this.abilities = abilities; 
    }
    public double getWeight() { 
        return weight; 
    }
    public void setWeight(double weight) { 
        this.weight = weight; 
    }
    public double getHeight() { 
        return height; 
    }
    public void setHeight(double height) { 
        this.height = height; 
    }
    public int getCaptureRate() { 
        return captureRate; 
    }
    public void setCaptureRate(int captureRate) { 
        this.captureRate = captureRate; 
    }
    public boolean isLegendary() { 
        return isLegendary; 
    }
    public void setIsLegendary(boolean isLegendary) { 
        this.isLegendary = isLegendary; 
    }
    public Date getCaptureDate() { 
        return captureDate; 
    }
    public void setCaptureDate(Date captureDate) { 
        this.captureDate = captureDate; 
    }
    public void ler(String linha) {
        String[] data = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        setId(Integer.parseInt(data[0].trim()));
        setGeneration(Integer.parseInt(data[1].trim()));
        setName(data[2].trim());
        setDescription(data[3].trim());
        List<String> typesList = new ArrayList<>();
        typesList.add(data[4].trim());
        if (!data[5].trim().isEmpty()) typesList.add(data[5].trim());
        setTypes(typesList.toArray(new String[0]));
        String abilitiesStr = data[6].replaceAll("[\\[\\]\"']", "").trim();
        setAbilities(abilitiesStr.split(",\\s*"));
        setWeight(data[7].trim().isEmpty() ? 0 : Double.parseDouble(data[7].trim()));
        setHeight(data[8].trim().isEmpty() ? 0 : Double.parseDouble(data[8].trim()));
        setCaptureRate(data[9].trim().isEmpty() ? 0 : Integer.parseInt(data[9].trim()));
        setIsLegendary(data[10].trim().equals("1") || data[10].trim().equalsIgnoreCase("true"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = dateFormat.parse(data[11].trim()); 
            setCaptureDate(date);
        } catch (ParseException e) {
            System.out.println("Erro ao converter a data: " + e.getMessage());
        }
    }
    public void imprimir() {
        System.out.print("[#" + getId() + " -> " + getName() + ": " + getDescription() + " - [");
        for(int i = 0; i < getTypes().length; i++) {
            System.out.print("'" + getTypes()[i] + "'");
            if(i != getTypes().length -1) System.out.print(", ");
        }
        System.out.print("] - [");
        for(int i = 0; i < getAbilities().length; i++) {
            System.out.print("'" + getAbilities()[i] + "'");
            if(i != getAbilities().length -1) System.out.print(", ");
        }
        System.out.print("] - ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(getWeight() + "kg - " + getHeight() + "m - " + getCaptureRate() + "% - " + isLegendary() + " - " + getGeneration() + " gen] - " + dateFormat.format(getCaptureDate()));
    }
}

class CelulaDupla {
	public Pokemon elemento;
	public CelulaDupla ant;
	public CelulaDupla prox;

	/**
	 * Construtor da classe.
	 */
	public CelulaDupla() {
		this(null);
	}


	/**
	 * Construtor da classe.
	 * @param elemento int inserido na celula.
	 */
	public CelulaDupla(Pokemon elemento) {
		this.elemento = elemento;
		this.ant = this.prox = null;
	}
}

class ListaDupla extends Pokemon{
	private CelulaDupla primeiro;
	private CelulaDupla ultimo;


	/**
	 * Construtor da classe que cria uma lista dupla sem elementos (somente no cabeca).
	 */
	public ListaDupla() {
		primeiro = new CelulaDupla();
		ultimo = primeiro;
	}


	/**
	 * Insere um elemento na primeira posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirInicio(Pokemon x) {
		CelulaDupla tmp = new CelulaDupla(x);

    tmp.ant = primeiro;
    tmp.prox = primeiro.prox;
    primeiro.prox = tmp;
    if(primeiro == ultimo){
        ultimo = tmp;
    }else{
        tmp.prox.ant = tmp;
    }
    tmp = null;
	}


	/**
	 * Insere um elemento na ultima posicao da lista.
    * @param x int elemento a ser inserido.
	 */
	public void inserirFim(Pokemon x) {
		ultimo.prox = new CelulaDupla(x);
        ultimo.prox.ant = ultimo;
		ultimo = ultimo.prox;
	}


	/**
	 * Remove um elemento da primeira posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Pokemon removerInicio() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		}

    CelulaDupla tmp = primeiro;
		primeiro = primeiro.prox;
		Pokemon resp = primeiro.elemento;
    tmp.prox = primeiro.ant = null;
    tmp = null;
		return resp;
	}


	/**
	 * Remove um elemento da ultima posicao da lista.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista nao contiver elementos.
	 */
	public Pokemon removerFim() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		} 
    Pokemon resp = ultimo.elemento;
    ultimo = ultimo.ant;
    ultimo.prox.ant = null;
    ultimo.prox = null;
		return resp;
	}


	/**
    * Insere um elemento em uma posicao especifica considerando que o 
    * primeiro elemento valido esta na posicao 0.
    * @param x int elemento a ser inserido.
	 * @param pos int posicao da insercao.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
    public void inserir(Pokemon x, int pos) throws Exception {

    int tamanho = tamanho();

    if(pos < 0 || pos > tamanho){
			throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
    } else if (pos == 0){
        inserirInicio(x);
    } else if (pos == tamanho){
        inserirFim(x);
    } else {
		   // Caminhar ate a posicao anterior a insercao
        CelulaDupla i = primeiro;
        for(int j = 0; j < pos; j++, i = i.prox);
		
        CelulaDupla tmp = new CelulaDupla(x);
        tmp.ant = i;
        tmp.prox = i.prox;
        tmp.ant.prox = tmp.prox.ant = tmp;
        tmp = i = null;
    }
}


	/**
    * Remove um elemento de uma posicao especifica da lista
    * considerando que o primeiro elemento valido esta na posicao 0.
	 * @param posicao Meio da remocao.
    * @return resp int elemento a ser removido.
	 * @throws Exception Se <code>posicao</code> invalida.
	 */
	public Pokemon remover(int pos) throws Exception {
    Pokemon resp;
    int tamanho = tamanho();

		if (primeiro == ultimo){
			throw new Exception("Erro ao remover (vazia)!");

    } else if(pos < 0 || pos >= tamanho){
			throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
    } else if (pos == 0){
        resp = removerInicio();
    } else if (pos == tamanho - 1){
        resp = removerFim();
    } else {
		   // Caminhar ate a posicao anterior a insercao
        CelulaDupla i = primeiro.prox;
        for(int j = 0; j < pos; j++, i = i.prox);
		
        i.ant.prox = i.prox;
        i.prox.ant = i.ant;
        resp = i.elemento;
        i.prox = i.ant = null;
        i = null;
    }

		return resp;
	}


	/**
	 * Mostra os elementos da lista separados por espacos.
	 */
	public void mostrar() {
		//System.out.print("[ "); // Comeca a mostrar.
		for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
			i.elemento.imprimir();
		}
		//System.out.println("] "); // Termina de mostrar.
	}


	/**
	 * Mostra os elementos da lista de forma invertida 
    * e separados por espacos.
	 */
	public void mostrarInverso() {
		System.out.print("[ ");
		for (CelulaDupla i = ultimo; i != primeiro; i = i.ant){
			System.out.print(i.elemento + " ");
    }
		System.out.println("] "); // Termina de mostrar.
	}


	/**
	 * Procura um elemento e retorna se ele existe.
	 * @param x Elemento a pesquisar.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(Pokemon x) {
		boolean resp = false;
		for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
        if(i.elemento == x){
            resp = true;
            i = ultimo;
        }
		}
		return resp;
	}
public int tamanho() {
    int tamanho = 0; 
    for(CelulaDupla i = primeiro; i != ultimo; i = i.prox, tamanho++);
    return tamanho;
}

public static void sort(ListaDupla pokemons) {
    if (pokemons != null && pokemons.primeiro != null && pokemons.ultimo != null) {
        quicksort(pokemons.primeiro.prox, pokemons.ultimo);
    }
}

private static void quicksort(CelulaDupla esq, CelulaDupla dir) {
    if (esq != null && dir != null && esq != dir && esq != dir.prox) {
        
        CelulaDupla pivot = partition(esq, dir);
        

        quicksort(esq, pivot.ant);
        quicksort(pivot.prox, dir);
    }
}

private static CelulaDupla partition(CelulaDupla esq, CelulaDupla dir) {
    int pivotGen = dir.elemento.getGeneration();  
    CelulaDupla i = esq.ant;

    for (CelulaDupla j = esq; j != dir; j = j.prox) {
        if (j.elemento.getGeneration() <= pivotGen) {
            i = (i == null) ? esq : i.prox;  
            Pokemon temp = i.elemento;  
            i.elemento = j.elemento;
            j.elemento = temp;
        }
    }

    i = (i == null) ? esq : i.prox;
    Pokemon temp = i.elemento;
    i.elemento = dir.elemento;
    dir.elemento = temp;

    return i;  
}


public static void sortbyName(ListaDupla pokemons) {
    if (pokemons != null && pokemons.primeiro != null && pokemons.ultimo != null) {
        quicksortbyName(pokemons.primeiro.prox, pokemons.ultimo);
    }
}

private static void quicksortbyName(CelulaDupla esq, CelulaDupla dir) {
    if (esq != null && dir != null && esq != dir && esq != dir.prox) {
        
        CelulaDupla pivot = partitionbyName(esq, dir);
        
        
        quicksortbyName(esq, pivot.ant);
        quicksortbyName(pivot.prox, dir); 
    }
}

private static CelulaDupla partitionbyName(CelulaDupla esq, CelulaDupla dir) {
    String pivotName = dir.elemento.getName(); 
    int pivotGen = dir.elemento.getGeneration();  
    CelulaDupla i = esq.ant;

    
    for (CelulaDupla j = esq; j != dir; j = j.prox) {
        
        if (j.elemento.getGeneration() < pivotGen || 
            (j.elemento.getGeneration() == pivotGen && j.elemento.getName().compareTo(pivotName) <= 0)) {
            
            i = (i == null) ? esq : i.prox; 
            Pokemon temp = i.elemento; 
            i.elemento = j.elemento;
            j.elemento = temp;
        }
    }

    i = (i == null) ? esq : i.prox;
    Pokemon temp = i.elemento;
    i.elemento = dir.elemento;
    dir.elemento = temp;

    return i; 
}

}

public class Exercicio10 extends ListaDupla {
        public static void main(String[] args) {
            List<Pokemon> pokedex = new ArrayList<>();
        String linha;
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/pokemon.csv"))) {
            while ((linha = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                }
                else{
                    Pokemon pokemon = new Pokemon();
                    pokemon.ler(linha);
                    pokedex.add(pokemon);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String numero;
        ListaDupla pokemons = new ListaDupla();
        int id;
        //int quantidadePokemons = 0;
        int comparacoes = 0;
        boolean encontrou;
        while(!(numero = scanner.nextLine()).equals("FIM")) {
            id = Integer.parseInt(numero);
            encontrou = false;
            int i = 0;
            while(!encontrou && i < pokedex.size()){
                comparacoes++;
                if(pokedex.get(i).getId() == id){
                    pokemons.inserirFim(pokedex.get(i));
                   // quantidadePokemons++;
                    encontrou = true;
                }
                i++;
            }
            if (!encontrou) {
                System.out.println("Pokémon com ID " + id + " não encontrado.");
            }
        }
        int movimentacao = 0;
        double inicioOrdenar = now();
        sort(pokemons);
        sortbyName(pokemons);
        double fimOrdenar = now();
        pokemons.mostrar();

        scanner.close();
        int matricula = 852302;
        try (FileWriter fileWriter = new FileWriter("matricula_quicksort3.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(matricula + "\t" + comparacoes + "\t" + movimentacao + "\t" + (fimOrdenar-inicioOrdenar)/1000.0 + "s");
            

        } catch (IOException e) {
         //   System.out.println("Ocorreu um erro ao criar o arquivo de log.");
          //  e.printStackTrace();
        }
    }


        public static long now() {
            return new Date().getTime();
        }



    /*    public static void countingSortByCaptureRate(Pokemon[] pokemons,int quantidadePokemons) {
            int n = quantidadePokemons;
        
            int maxCaptureRate = 0;
            for (int i = 0; i < n; i++) {
                if (pokemons[i].getCaptureRate() > maxCaptureRate) {
                    maxCaptureRate = pokemons[i].getCaptureRate();
                }
            }
        
            
            int[] count = new int[maxCaptureRate + 1];
        
            
            for (int i = 0; i < count.length; i++) {
                count[i] = 0;
            }
        
            
            for (int i = 0; i < n; i++) {
                count[pokemons[i].getCaptureRate()]++;
            }
        
            
            for (int i = 1; i <= maxCaptureRate; i++) {
                count[i] += count[i - 1];
            }
        
            
            Pokemon[] output = new Pokemon[n];
        
            
            for (int i = n - 1; i >= 0; i--) {
                int captureRate = pokemons[i].getCaptureRate();
                output[count[captureRate] - 1] = pokemons[i];
                count[captureRate]--;
            }
        
            
            for (int i = 0; i < n; i++) {
                pokemons[i] = output[i];
            }
        }*/


}





