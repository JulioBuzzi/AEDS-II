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

class Celula{
    public Pokemon elemento;
    public Celula prox;
    public Celula(){
        this.elemento = null;
        prox = null;
    }
    public Celula(Pokemon pokemon){
        this.elemento = pokemon;
        prox = null;
    }
}
class Lista {
	private Celula primeiro;
	private Celula ultimo;

	public Lista() {
		primeiro = new Celula();
		ultimo = primeiro;
	}

	public void inserirInicio(Pokemon p) {
		Celula tmp = new Celula(p);
        tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if (primeiro == ultimo) {                 
			ultimo = tmp;
		}
        tmp = null;
	}

	public void inserirFim(Pokemon p) {
		ultimo.prox = new Celula(p);
		ultimo = ultimo.prox;
	}

	public Pokemon removerInicio() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		}

        Celula tmp = primeiro;
		primeiro = primeiro.prox;
		Pokemon resp = primeiro.elemento;
        tmp.prox = null;
        tmp = null;
		return resp;
	}

	public Pokemon removerFim() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		} 

		// Caminhar ate a penultima celula:
        Celula i;
        for(i = primeiro; i.prox != ultimo; i = i.prox);

        Pokemon resp = ultimo.elemento; 
        ultimo = i; 
        i = ultimo.prox = null;
      
		return resp;
	}

   public void inserir(Pokemon p, int pos) throws Exception {

        int tamanho = tamanho();
        if(pos < 0 || pos > tamanho){
            throw new Exception("Não");
        } else if (pos == 0){
            inserirInicio(p);
        } else if (pos == tamanho){
            inserirFim(p);
        } else {
            // Caminhar ate a posicao anterior a insercao
            Celula i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            
            Celula tmp = new Celula(p);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
   }

	public Pokemon remover(int pos) throws Exception {
        Pokemon resp;
        int tamanho = tamanho();

		if (primeiro == ultimo){
			throw new Exception("Erro ao remover (vazia)!");

        } else if(pos < 0 || pos >= tamanho){
            throw new Exception("Nao");
        } else if (pos == 0){
            resp = removerInicio();
        } else if (pos == tamanho - 1){
            resp = removerFim();
        } else {
            // Caminhar ate a posicao anterior a insercao
            Celula i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            
            Celula tmp = i.prox;
            resp = tmp.elemento;
            i.prox = tmp.prox;
            tmp.prox = null;
            i = tmp = null;
        }

		return resp;
	}

	public void mostrar() {
        int contador = 0;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			System.out.print("[" + contador + "] ");
            i.elemento.imprimir();
            contador++;
		}
	}

    public int tamanho() {
        int tamanho = 0; 
        for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
        return tamanho;
    }
}

class Exercicio1{
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
    public static Lista selecionarPokemons(List<Pokemon> pokedex, Scanner scanner){
        String numero;
        Lista lista = new Lista();
        int id;
        //int quantidadePokemons = 0;
        boolean encontrou;
        while(!(numero = scanner.nextLine()).equals("FIM")) {
            id = Integer.parseInt(numero);
            encontrou = false;
            int i = 0;
            while(!encontrou && i < pokedex.size()){
                if(pokedex.get(i).getId() == id){
                    lista.inserirFim(pokedex.get(i));
               //     quantidadePokemons++;
                    encontrou = true;
                }
                i++;
            }
            if (!encontrou) {
                System.out.println("Pokémon com ID " + id + " não encontrado.");
            }
        }
        return lista;
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
    public static void realizarComando(Lista lista, List<Pokemon> pokedex, List<Pokemon> pokemonsRemovidos, Scanner scanner){
        String comando = scanner.next();
        if(comando.equals("I*") || comando.equals("R*")){
            String posicaoString = scanner.next();
            int posicao = Integer.parseInt(posicaoString);
            if(comando.equals("I*")){
                String idString = scanner.next();
                int id = Integer.parseInt(idString);
                Pokemon p = encontrarPokemon(pokedex, id);
                try {
                    lista.inserir(p, posicao);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            else{
                try {
                    Pokemon removido = lista.remover(posicao);
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
                lista.inserirInicio(p);
            }
            else{
                lista.inserirFim(p);
            }
        }
        else{
            if(comando.equals("RI")){
                try {
                    Pokemon removido = lista.removerInicio();
                    pokemonsRemovidos.add(removido);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            else{
                try {
                    Pokemon removido = lista.removerFim();
                    pokemonsRemovidos.add(removido);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        List<Pokemon> pokedex = lerPokedex();
        Lista lista = selecionarPokemons(pokedex, scanner);
        List<Pokemon> pokemonsRemovidos = new ArrayList<>();
        int quantidadeComandos = scanner.nextInt();
        for(int i = 0; i < quantidadeComandos; i++){
            realizarComando(lista, pokedex, pokemonsRemovidos, scanner);
        }
        for(Pokemon p : pokemonsRemovidos){
            System.out.println("(R) " + p.getName());
        }
        lista.mostrar();
    }
}