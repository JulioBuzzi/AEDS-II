//LAB05 FOI FAZER AS DUAS PRIMEIRAS ATIVIDADES DO TP2

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Pokemon {
    private String id;
    private int generation;
    private String name;
    private String description;
    private String[] types;
    private String[] abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private LocalDate captureDate;

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public int getGeneration() { return generation; }
    public void setGeneration(int generation) { this.generation = generation; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String[] getTypes() { return types; }
    public void setTypes(String[] types) { this.types = types; }
    
    public String[] getAbilities() { return abilities; }
    public void setAbilities(String[] abilities) { this.abilities = abilities; }
    
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    
    public int getCaptureRate() { return captureRate; }
    public void setCaptureRate(int captureRate) { this.captureRate = captureRate; }
    
    public boolean isLegendary() { return isLegendary; }
    public void setIsLegendary(boolean isLegendary) { this.isLegendary = isLegendary; }
    
    public LocalDate getCaptureDate() { return captureDate; }
    public void setCaptureDate(LocalDate captureDate) { this.captureDate = captureDate; }


    public void ler(String csvLine) {
        String[] data = csvLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        
        setId(data[0].trim());
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

        LocalDate date = parseDate(data[11].trim());
        setCaptureDate(date);
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    
    public void display() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("[#%s -> %s: %s - [", getId(), getName(), getDescription());

        
        for (int i = 0; i < getTypes().length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.printf("'%s'", getTypes()[i]);
        }
        System.out.print("] - [");

        
        for (int i = 0; i < getAbilities().length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.printf("'%s'", getAbilities()[i]);
        }
        System.out.print("] - ");

        
        System.out.printf("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n", 
            getWeight(), getHeight(), getCaptureRate(), 
            isLegendary() ? "true" : "false", getGeneration(),
            getCaptureDate().format(formatter));
    }
}

public class Exercicio1 {
    public static void main(String[] args) {
        List<Pokemon> pokedex = new ArrayList<>();

        
        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/pokemon.csv"))) {
            String line = br.readLine(); 
            while ((line = br.readLine()) != null) {
                Pokemon p = new Pokemon();
                p.ler(line);
                pokedex.add(p);
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
            return;
        }

        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String id = scanner.nextLine().trim();
            if (id.equalsIgnoreCase("FIM")) break;

            boolean found = false;
            for (Pokemon p : pokedex) {
                if (p.getId().equalsIgnoreCase(id)) {
                    p.display();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Pokémon com ID " + id + " não encontrado.");
            }
        }
    }
}
