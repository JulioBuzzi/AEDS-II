import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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



class Celula {
	public Pokemon elemento; // Elemento inserido na celula.
	public Celula prox; // Aponta a celula prox.


	/**
	 * Construtor da classe.
	 */
	public Celula() {
		this.elemento = null;
	}

	/**
	 * Construtor da classe.
	 * @param elemento int inserido na celula.
	 */
	public Celula(Pokemon elemento) {
    this.elemento = elemento;
    this.prox = null;
	}
}
class Pilha {
	private Celula topo;

	/**
	 * Construtor da classe que cria uma fila sem elementos.
	 */
	public Pilha() {
		topo = null;
	}

	/**
	 * Insere elemento na pilha (politica FILO).
	 * 
	 * @param x int elemento a inserir.
	 */
	public void inserir(Pokemon x) {
		Celula tmp = new Celula(x);
		tmp.prox = topo;
		topo = tmp;
		tmp = null;
	}

	/**
	 * Remove elemento da pilha (politica FILO).
	 * 
	 * @return Elemento removido.
	 * @trhows Exception Se a sequencia nao contiver elementos.
	 */
	public Pokemon remover() throws Exception {
		if (topo == null) {
			throw new Exception("Erro ao remover!");
		}
		Pokemon resp = topo.elemento;
		Celula tmp = topo;
		topo = topo.prox;
		tmp.prox = null;
		tmp = null;
		return resp;
	}

	/**
	 * Mostra os elementos separados por espacos, comecando do topo.
	 */
	public void mostrar() {
        int contador = 0;
		for (Celula i = topo.prox; i != null; i = i.prox) {
			System.out.print("[" + contador + "] ");
            i.elemento.imprimir();
            contador++;
		}    
	}

	/*public int getSoma() {
		return getSoma(topo);
	}

	private int getSoma(Celula i) {
		int resp = 0;
		if (i != null) {
			resp += i.elemento + getSoma(i.prox);
		}
		return resp;
	} */

/* 	public int getMax() {
		int max = topo.elemento;
		for (Celula i = topo.prox; i != null; i = i.prox) {
			if (i.elemento > max)
				max = i.elemento;
		}
		return max;
	} */
    public int tamanho() {
        int tamanho = 0; 
        for(Celula i = topo; i.prox != null; i = i.prox, tamanho++);
        return tamanho;
    }

	public void mostraPilha() {
		mostraPilha(topo, tamanho());
	}

	private void mostraPilha(Celula i, int contador) {
		if (i != null) {
			mostraPilha(i.prox, contador - 1);
            System.out.print("[" + contador + "] " );
			i.elemento.imprimir();
		}
	}

}

class Exercicio9{
    public static List<Pokemon> lerPokedex() {
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
        }
        return pokedex;
    }
    public static Pilha selecionarPokemons(List<Pokemon> pokedex, Scanner scanner){
        String numero;
        Pilha pilha = new Pilha();
        int id;
        int quantidadePokemons = 0;
        boolean encontrou;
        while(!(numero = scanner.nextLine()).equals("FIM")) {
            id = Integer.parseInt(numero);
            encontrou = false;
            int i = 0;
            while(!encontrou && i < pokedex.size()){
                if(pokedex.get(i).getId() == id){
                    pilha.inserir(pokedex.get(i));
                    quantidadePokemons++;
                    encontrou = true;
                }
                i++;
            }
            if (!encontrou) {
                System.out.println("Pokémon com ID " + id + " não encontrado.");
            }
        }
        return pilha;
    }

    public static Pokemon encontrarPokemon(List<Pokemon> pokedex, int id){
        Pokemon p = null;
        boolean encontrou = false;
        int i = 0;
        while(!encontrou && i < pokedex.size()){
            if(pokedex.get(i).getId() == id){
                encontrou = true;
                p = pokedex.get(i);
            }
            i++;
        }
        if (!encontrou) {
            System.out.println("Pokémon com ID " + id + " não encontrado.");
        }
        return p;
    }
    public static void realizarComando(Pilha pilha, List<Pokemon> pokedex, List<Pokemon> pokemonsRemovidos, Scanner scanner){
        String comando = scanner.next();
        if(comando.equals("I") || comando.equals("R*")){
            String posicaoString = scanner.next();
            // int posicao = Integer.parseInt(posicaoString);
            if(comando.equals("I*")){
                String idString = scanner.next();
                int id = Integer.parseInt(idString);
                Pokemon p = encontrarPokemon(pokedex, id);
                try {
                    pilha.inserir(p);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            else{
                try {
                    Pokemon removido = pilha.remover();
                    pokemonsRemovidos.add(removido);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
        else if(comando.equals("II") || comando.equals("IF")){
            String idString = scanner.next();
            int id = Integer.parseInt(idString);
            Pokemon p = encontrarPokemon(pokedex, id);
            if(comando.equals("II")){
                pilha.inserir(p);
            }
            else{
                pilha.inserir(p);
            }
        }
        else{
            if(comando.equals("RI")){
                try {
                    Pokemon removido = pilha.remover();
                    pokemonsRemovidos.add(removido);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            else{
                try {
                    Pokemon removido = pilha.remover();
                    pokemonsRemovidos.add(removido);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        List<Pokemon> pokedex = lerPokedex();
        Pilha pilha = selecionarPokemons(pokedex, scanner);
        String pokemonsRemovidosString[] = new String[50];
        Pokemon pokemonsRemovidos = new Pokemon();
        int quantidadeComandos = scanner.nextInt();
        int pokemonRemoved=0;
        String comando;
        int id;
        for(int i = 0; i < quantidadeComandos; i++){
            //realizarComando(pilha, pokedex, pokemonsRemovidos, scanner);
            comando = scanner.next();
            if(comando.equals("R")) { 
                pokemonsRemovidos = pilha.remover();
                pokemonsRemovidosString[pokemonRemoved] = pokemonsRemovidos.getName();
                pokemonRemoved++;
            }
            else {
                    String idString = scanner.next();
                    id = Integer.parseInt(idString);
                    Pokemon p = encontrarPokemon(pokedex, id);
                    try {
                        pilha.inserir(p);
                    } catch (Exception e) {
                        System.err.println(e);
                }
            }
        }
        for(int i=0;i<pokemonRemoved;i++){
            System.out.println("(R) " + pokemonsRemovidosString[i]);
        }
        pilha.mostraPilha();
    }
}