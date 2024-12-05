import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Pokemon 
{
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

    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    {
        this.id = id;
    }

    public int getGeneration() 
    {
        return generation;
    }

    public void setGeneration(int generation) 
    {
        this.generation = generation;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String[] getTypes() 
    {
        return types;
    }

    public void setTypes(String[] types) 
    {
        this.types = types;
    }

    public String[] getAbilities() 
    {
        return abilities;
    }

    public void setAbilities(String[] abilities) 
    {
        this.abilities = abilities;
    }

    public double getWeight() 
    {
        return weight;
    }

    public void setWeight(double weight) 
    {
        this.weight = weight;
    }

    public double getHeight() 
    {
        return height;
    }

    public void setHeight(double height) 
    {
        this.height = height;
    }

    public int getCaptureRate() 
    {
        return captureRate;
    }

    public void setCaptureRate(int captureRate) 
    {
        this.captureRate = captureRate;
    }

    public boolean isLegendary() 
    {
        return isLegendary;
    }

    public void setIsLegendary(boolean isLegendary) 
    {
        this.isLegendary = isLegendary;
    }

    public LocalDate getCaptureDate() 
    {
        return captureDate;
    }

    public void setCaptureDate(LocalDate captureDate) 
    {
        this.captureDate = captureDate;
    }

    void ler(String csvLine) 
    {
        String[] data = csvLine.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
        
        setId(data[0].trim());
        setGeneration(Integer.parseInt(data[1].trim()));
        setName(data[2].trim());
        setDescription(data[3].trim());

        List<String> typesList = new ArrayList<>();
        typesList.add(data[4].trim());
        if (!data[5].trim().isEmpty()) typesList.add(data[5].trim());
        setTypes(typesList.toArray(new String[0]));

        // Processar habilidades sem aspas
        String abilitiesStr = data[6].replaceAll("[\\[\\]\"']", "").trim(); // Remove colchetes e aspas
        setAbilities(abilitiesStr.split(",\\s*")); // Divide as habilidades e remove espaços extras

        setWeight(data[7].trim().isEmpty() ? 0 : Double.parseDouble(data[7].trim()));
        setHeight(data[8].trim().isEmpty() ? 0 : Double.parseDouble(data[8].trim()));
        setCaptureRate(data[9].trim().isEmpty() ? 0 : Integer.parseInt(data[9].trim()));
        setIsLegendary(data[10].trim().equals("1") || data[10].trim().equalsIgnoreCase("true"));

        LocalDate date = parseDate(data[11].trim());
        setCaptureDate(date);
    }

    private LocalDate parseDate(String dateStr) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    String imprimir() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[#").append(getId()).append(" -> ").append(getName()).append(": ").append(getDescription()).append(" - [");

        String[] types = getTypes();
        for (int i = 0; i < types.length; i++) {
            sb.append("'").append(types[i]).append("'");
            if (i < types.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append("[");
        String[] abilities = getAbilities();
        for (int i = 0; i < abilities.length; i++) {
            sb.append("'").append(abilities[i]).append("'"); // Mantém as aspas
            if (i < abilities.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append(getWeight()).append("kg - ");
        sb.append(getHeight()).append("m - ");
        sb.append(getCaptureRate()).append("% - ");
        sb.append(isLegendary() ? "true" : "false").append(" - ");
        sb.append(getGeneration()).append(" gen] - ");
        sb.append(getCaptureDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return sb.toString();
    }
}

class NoArvore 
{
    public int captureRate;
    public ArvoreBinaria arvore;
    public NoArvore esq, dir;

    public NoArvore(int captureRate) 
    {
        this(captureRate, null, null, null);
    }

    public NoArvore(int captureRate, ArvoreBinaria arvore, NoArvore esq, NoArvore dir) 
    {
        this.captureRate = captureRate;
        this.arvore = arvore;
        this.esq = esq;
        this.dir = dir;
    }
}


class No 
{
    public Pokemon elemento;
    public No esq, dir;

    public No(Pokemon elemento) 
    {
        this(elemento, null, null);
    }

    public No(Pokemon elemento, No esq, No dir) 
    {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreArvore 
{
    private NoArvore raiz;

    public ArvoreArvore() 
    {
        raiz = null;
        try {
            inserir(7);
            inserir(3);
            inserir(11);
            inserir(1);
            inserir(5);
            inserir(9);
            inserir(13);
            inserir(0);
            inserir(2);
            inserir(4);
            inserir(6);
            inserir(8);
            inserir(10);
            inserir(12);
            inserir(14);
        } 
        catch (Exception e) 
        {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    public boolean pesquisar(String nome) 
    {
        System.out.print("raiz ");
        return pesquisar(nome, raiz);
    }
    
    private boolean pesquisar(String nome, NoArvore i) 
    {
        boolean resp;
    
        if (i != null) 
        {
            resp = i.arvore != null && i.arvore.pesquisar(nome);
    
            if (!resp) 
            {
                System.out.print(" ESQ ");
                resp = pesquisar(nome, i.esq);
            }
            if (!resp) 
            {
                System.out.print(" DIR ");
                resp = pesquisar(nome, i.dir);
            }
        } 
        else 
        {
            resp = false;
        }
        return resp;
    }
    
    public void inserir(Pokemon pokemon) throws Exception 
    {
        int captureRateMod = pokemon.getCaptureRate() % 15;
        raiz = inserir(pokemon, captureRateMod, raiz);
    }
    
    private NoArvore inserir(Pokemon pokemon, int captureRateMod, NoArvore i) throws Exception 
    {
        if (i == null) 
        {
            throw new Exception("CaptureRate inexistente");
        } 
        else if (captureRateMod == i.captureRate) 
        {
            if (i.arvore == null) 
            {
                i.arvore = new ArvoreBinaria();
            }
            i.arvore.inserir(pokemon);
        } 
        else if (captureRateMod < i.captureRate) 
        {
            i.esq = inserir(pokemon, captureRateMod, i.esq);
        }
        else 
        {
            i.dir = inserir(pokemon, captureRateMod, i.dir);
        }
        return i;
    }
    
    public void inserir(int captureRate) throws Exception 
    {
        raiz = inserir(captureRate, raiz);
    }
    
    private NoArvore inserir(int captureRate, NoArvore i) throws Exception 
    {
        if (i == null) 
        {
            i = new NoArvore(captureRate);
            i.arvore = new ArvoreBinaria();
        } 
        else if (captureRate < i.captureRate) 
        {
            i.esq = inserir(captureRate, i.esq);
        } 
        else if (captureRate > i.captureRate) 
        {
            i.dir = inserir(captureRate, i.dir);
        } 
        else 
        {
            throw new Exception("Número repetido");
        }
        return i;
    }
}


class ArvoreBinaria 
{
    private No raiz;

    public ArvoreBinaria() 
    {
        raiz = null;
    }

    public boolean pesquisar(String nome) 
    {
        return pesquisar(nome, raiz);
    }
    
    private boolean pesquisar(String nome, No i) 
    {
        if (i == null)
        {
            return false;
        } 
        else if (nome.equals(i.elemento.getName())) 
        {
            return true;
        } 
        else if (nome.compareTo(i.elemento.getName()) < 0) 
        {
            System.out.print("esq ");
            return pesquisar(nome, i.esq);
        } 
        else 
        {
            System.out.print("dir ");
            return pesquisar(nome, i.dir);
        }
    }
    
    public void inserir(Pokemon pokemon) throws Exception 
    {
        raiz = inserir(pokemon, raiz);
    }
    
    private No inserir(Pokemon pokemon, No i) throws Exception 
    {
        if (i == null) 
        {
            i = new No(pokemon);
        } 
        else if (pokemon.getName().compareTo(i.elemento.getName()) < 0) 
        {
            i.esq = inserir(pokemon, i.esq);
        } 
        else if (pokemon.getName().compareTo(i.elemento.getName()) > 0) 
        {
            i.dir = inserir(pokemon, i.dir);
        } 
        else 
        {
            throw new Exception("Erro ao inserir! Elemento já existe.");
        }
        return i;
    }
    
    public void inserirPai(Pokemon pokemon) throws Exception 
    {
        if (raiz == null) 
        {
            raiz = new No(pokemon);
        } 
        else if (pokemon.getId().compareTo(raiz.elemento.getId()) < 0) 
        {
            inserirPai(pokemon, raiz.esq, raiz);
        } 
        else if (pokemon.getId().compareTo(raiz.elemento.getId()) > 0) 
        {
            inserirPai(pokemon, raiz.dir, raiz);
        } 
        else 
        {
            throw new Exception("Erro ao inserirPai! Elemento já existe.");
        }
    }
    
    private void inserirPai(Pokemon pokemon, No i, No pai) throws Exception 
    {
        if (i == null) 
        {
            if (pokemon.getId().compareTo(pai.elemento.getId()) < 0) 
            {
                pai.esq = new No(pokemon);
            } 
            else 
            {
                pai.dir = new No(pokemon);
            }
        } 
        else if (pokemon.getId().compareTo(i.elemento.getId()) < 0) 
        {
            inserirPai(pokemon, i.esq, i);
        } 
        else if (pokemon.getId().compareTo(i.elemento.getId()) > 0) 
        {
            inserirPai(pokemon, i.dir, i);
        } 
        else 
        {
            throw new Exception("Erro ao inserirPai! Elemento já existe.");
        }
    }
    
    public void remover(Pokemon pokemon) throws Exception 
    {
        raiz = remover(pokemon, raiz);
    }
    
    private No remover(Pokemon pokemon, No i) throws Exception 
    {
        if (i == null) 
        {
            throw new Exception("Erro ao remover! Elemento não encontrado.");
        } 
        else if (pokemon.getId().compareTo(i.elemento.getId()) < 0) 
        {
            i.esq = remover(pokemon, i.esq);
        } 
        else if (pokemon.getId().compareTo(i.elemento.getId()) > 0) 
        {
            i.dir = remover(pokemon, i.dir);
        } 
        else 
        {
            if (i.dir == null) 
            {
                i = i.esq;
            } 
            else if (i.esq == null) 
            {
                i = i.dir;
            } 
            else 
            {
                i.esq = maiorEsq(i, i.esq);
            }
        }
        return i;
    }

    private No maiorEsq(No removido, No atual) 
    {
        if (atual.dir == null) 
        {
            removido.elemento = atual.elemento;
            return atual.esq;
        }
        atual.dir = maiorEsq(removido, atual.dir);
        return atual;
    }

    public void caminharCentral() 
    {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    private void caminharCentral(No i) 
    {
        if (i != null) 
        {
            caminharCentral(i.esq);
            System.out.print(i.elemento.getName() + " ");
            caminharCentral(i.dir);
        }
    }

    public void caminharPre() 
    {
        System.out.print("[ ");
        caminharPre(raiz);
        System.out.println("]");
    }

    private void caminharPre(No i) 
    {
        if (i != null) 
        {
            System.out.print(i.elemento.getName() + " ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    public void caminharPos() 
    {
        System.out.print("[ ");
        caminharPos(raiz);
        System.out.println("]");
    }

    private void caminharPos(No i) 
    {
        if (i != null) 
        {
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.print(i.elemento.getName() + " ");
        }
    }

    public Pokemon getMaior() 
    {
        Pokemon resp = null;
        if (raiz != null) 
        {
            No i;
            for (i = raiz; i.dir != null; i = i.dir);
            resp = i.elemento;
        }
        return resp;
    }

    public Pokemon getMenor() 
    {
        Pokemon resp = null;
        if (raiz != null) 
        {
            No i;
            for (i = raiz; i.esq != null; i = i.esq);
            resp = i.elemento;
        }
        return resp;
    }

    public int getAltura() 
    {
        return getAltura(raiz);
    }

    private int getAltura(No i) 
    {
        if (i == null) 
        {
            return -1;
        } 
        else 
        {
            int alturaEsq = getAltura(i.esq);
            int alturaDir = getAltura(i.dir);
            return 1 + Math.max(alturaEsq, alturaDir);
        }
    }

    public Pokemon getRaiz() throws Exception 
    {
        if (raiz == null) 
        {
            throw new Exception("Erro! Árvore vazia.");
        }
        return raiz.elemento;
    }
}


public class Exercicio2 
{
    public static void main(String[] args) 
    {
        long startTime = System.nanoTime(); 
        // Caminho do arquivo CSV 
        String path = "/tmp/pokemon.csv";
        ArrayList<Pokemon> pokedex = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(path))) 
        {
            sc.nextLine();  // Ignora cabeçalho
            while (sc.hasNextLine()) 
            {
                // Lê a linha do CSV e cria um objeto Pokemon a partir dela
                String csvLine = sc.nextLine();
                Pokemon pokemon = new Pokemon();
                pokemon.ler(csvLine);  
                pokedex.add(pokemon);  
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        
        ArvoreArvore arvore = new ArvoreArvore();

        String idStr = sc.nextLine();

        while (!idStr.equals("FIM")) 
        {
            try 
            {
                int id = Integer.parseInt(idStr);
                if(id <= 0 || id > pokedex.size())
                {
                    System.out.println("ID inválido: " + id);
                } 
                else 
                {
                    Pokemon p = pokedex.get(id - 1);
                    if (p != null) 
                    {
                        //int chavePrimaria = p.getCaptureRate() % 15;
                        arvore.inserir(p);
                    }
                }
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Entrada inválida: " + idStr);
            } 
            catch (Exception e) 
            {
                System.out.println("Erro ao inserir Pokemon: " + e.getMessage());
            }
            idStr = sc.nextLine();
        }
        

        String name = sc.nextLine();
        while(!name.equals("FIM")) 
        {
            System.out.println("=> " + name);
            if(arvore.pesquisar(name)) 
            {
                System.out.println(" SIM");
            } 
            else 
            {
                System.out.println(" NAO");
            }
            name = sc.nextLine();
        }

        long endTime = System.nanoTime(); 
        long executionTime = (endTime - startTime) / 1_000_000; // Tempo em milissegundos

        String matricula = "852302"; 

        try (PrintWriter writer = new PrintWriter("852302_arvoreArvore.txt")) 
        {
        writer.printf("%s\t%d\t", matricula, executionTime);
        } 
        catch (IOException e) 
        {
            System.out.println("Erro ao criar o arquivo de log: " + e.getMessage());
        }

        sc.close();
    }
}
