import java.io.BufferedReader;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
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

public class Exercicio9 {
    public static void main(String[] args) {
        List<Pokemon> pokedex = new ArrayList<>();
        String linha;
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/pokemon.csv"))) {
            while ((linha = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
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
        Pokemon[] pokemons = new Pokemon[pokedex.size()];
        int quantidadePokemons = 0;

        while (!(numero = scanner.nextLine()).equals("FIM")) {
            int id = Integer.parseInt(numero);
            boolean encontrado = false;

            for (Pokemon pokemon : pokedex) {
                if (pokemon.getId() == id) {
                    pokemons[quantidadePokemons++] = pokemon;
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Pokémon com ID " + id + " não encontrado.");
            }
        }

        heapsort(pokemons, quantidadePokemons);
        insercaoMesmoRate(pokemons, quantidadePokemons);
        

        for (int i = 0; i < quantidadePokemons; i++) {
            pokemons[i].imprimir();
        }

        scanner.close();
    }

    public static void heapsort(Pokemon[] array, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            construir(i, n, array);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            construir(0, i, array);
        }
    }

    public static void construir(int i, int n, Pokemon[] array) {
        int maior = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left].getHeight() > array[maior].getHeight()) {
            maior = left;
        }

        if (right < n && array[right].getHeight() > array[maior].getHeight()) {
            maior = right;
        }

        if (maior != i) {
            swap(array, i, maior);
            construir(maior, n, array);
        }
    }

    public static void swap(Pokemon[] array, int i, int j) {
        Pokemon temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static void insercaoMesmoRate(Pokemon[] pokemons, int quantidadePokemons) {
        int n = quantidadePokemons; 
        for (int i = 1; i < n; i++) {
            Pokemon key = pokemons[i];
            int j = i - 1;
            
            
            while (j >= 0 && 
                pokemons[j].getHeight()==(key.getHeight()) &&  
                pokemons[j].getName().compareTo(key.getName()) > 0) {  
                pokemons[j + 1] = pokemons[j];
                j = j - 1;
            }
            pokemons[j + 1] = key;
        }
    }
}

