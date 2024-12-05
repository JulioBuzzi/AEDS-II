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


/**
 * No da arvore binaria
 * @author Max do Val Machado
 */

 class No {
   public Pokemon elemento; // Conteudo do no.
   public No esq, dir;  // Filhos da esq e dir.

   /**
    * Construtor da classe.
    * @param elemento Conteudo do no.
    */
   public No(Pokemon elemento) {
      this(elemento, null, null);
   }

   /**
    * Construtor da classe.
    * @param elemento Conteudo do no.
    * @param esq No da esquerda.
    * @param dir No da direita.
    */
   public No(Pokemon elemento, No esq, No dir) {
      this.elemento = elemento;
      this.esq = esq;
      this.dir = dir;
   }
}

/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */
class ArvoreBinaria extends Pokemon{
	private No raiz; // Raiz da arvore.

	/**
	 * Construtor da classe.
	 */
	public ArvoreBinaria() {
		raiz = null;
	}

	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(String x,ArrayList<String> movimentacoes) {
		return pesquisar(x, raiz,movimentacoes);
	}

	/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	private boolean pesquisar(String x, No i,ArrayList<String> movimentacoes) {
      boolean resp;
		if (i == null) {
         resp = false;

      } else if (x.compareTo(i.elemento.getName()) == 0) {
         resp = true;

      } else if (x.compareTo(i.elemento.getName()) < 0) {
         movimentacoes.add("esq");
         resp = pesquisar(x, i.esq,movimentacoes);
      } else {
         movimentacoes.add("dir");
         resp = pesquisar(x, i.dir,movimentacoes);
      }
      return resp;
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void caminharCentral() {
		System.out.print("[ ");
		caminharCentral(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq); // Elementos da esquerda.
			System.out.print(i.elemento.getName() + " "); // Conteudo do no.
			caminharCentral(i.dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void caminharPre() {
		System.out.print("[ ");
		caminharPre(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private void caminharPre(No i) {
		if (i != null) {
			System.out.print(i.elemento.getName() + " "); // Conteudo do no.
			caminharPre(i.esq); // Elementos da esquerda.
			caminharPre(i.dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void caminharPos() {
		System.out.print("[ ");
		caminharPos(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private void caminharPos(No i) {
		if (i != null) {
			caminharPos(i.esq); // Elementos da esquerda.
			caminharPos(i.dir); // Elementos da direita.
			System.out.println(i.elemento.getName() + " "); // Conteudo do no.
		}
	}


	/**
	 * Metodo publico iterativo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(Pokemon x) throws Exception {
		raiz = inserir(x, raiz);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(Pokemon x, No i) throws Exception {
		if (i == null) {
         i = new No(x);

      } else if (x.getName().compareTo(i.elemento.getName()) < 0) {
         i.esq = inserir(x, i.esq);

      } else if (x.getName().compareTo(i.elemento.getName()) > 0) {
         i.dir = inserir(x, i.dir);

      } else {
         throw new Exception("Erro ao inserir!");
      }

		return i;
	}

	public void remover(Pokemon x) throws Exception {
		raiz = remover(x, raiz);
	}

	private No remover(Pokemon x, No i) throws Exception {

		if (i == null) {
         throw new Exception("Erro ao remover!");

      } else if (x.getName().compareTo(i.elemento.getName()) < 0) {
         i.esq = remover(x, i.esq);

      } else if (x.getName().compareTo(i.elemento.getName()) > 0) {
         i.dir = remover(x, i.dir);

      // Sem no a direita.
      } else if (i.dir == null) {
         i = i.esq;

      // Sem no a esquerda.
      } else if (i.esq == null) {
         i = i.dir;

      // No a esquerda e no a direita.
      } else {
         i.esq = maiorEsq(i, i.esq);
		}

		return i;
	}

	/**
	 * Metodo para trocar o elemento "removido" pelo maior da esquerda.
	 * @param i No que teve o elemento removido.
	 * @param j No da subarvore esquerda.
	 * @return No em analise, alterado ou nao.
	 */
	private No maiorEsq(No i, No j) {

      // Encontrou o maximo da subarvore esquerda.
		if (j.dir == null) {
			i.elemento = j.elemento; // Substitui i por j.
			j = j.esq; // Substitui j por j.ESQ.

      // Existe no a direita.
		} else {
         // Caminha para direita.
			j.dir = maiorEsq(i, j.dir);
		}
		return j;
	}

	/**
	 * Metodo que retorna o maior elemento da árvore
	 * @return int maior elemento da árvore
	 */
   public String getMaior(){
      String resp = "vazio";

      if(raiz != null){
         No i;
         for(i = raiz; i.dir != null; i = i.dir);
         resp = i.elemento.getName();
      }

      return resp;
   }


	/**
	 * Metodo que retorna o menor elemento da árvore
	 * @return int menor elemento da árvore
	 */
   public String getMenor(){
      String resp = "vazio";

      if(raiz != null){
         No i;
         for(i = raiz; i.esq != null; i = i.esq);
         resp = i.elemento.getName();
      }

      return resp;
   }


   public int getAltura(){
      return getAltura(raiz, 0);
   }


	/**
	 * Metodo que retorna a altura da árvore
	 * @return int altura da árvore
	 */
   public int getAltura(No i, int altura){
      if(i == null){
         altura--;
      } else {
         int alturaEsq = getAltura(i.esq, altura + 1);
         int alturaDir = getAltura(i.dir, altura + 1);
         altura = (alturaEsq > alturaDir) ? alturaEsq : alturaDir;
      }
      return altura;
   }


	public void remover2(Pokemon x) throws Exception {
      if (raiz == null) {
         throw new Exception("Erro ao remover2!");
      } else if(x.getName().compareTo(raiz.elemento.getName()) < 0){
         remover2(x, raiz.esq, raiz);
      } else if (x.getName().compareTo(raiz.elemento.getName()) > 0){
         remover2(x, raiz.dir, raiz);
      } else if (raiz.dir == null) {
         raiz = raiz.esq;
      } else if (raiz.esq == null) {
         raiz = raiz.dir;
      } else {
         raiz.esq = maiorEsq(raiz, raiz.esq);
      }
   }

	private void remover2(Pokemon x, No i, No pai) throws Exception {
		if (i == null) {
         throw new Exception("Erro ao remover2!");
      } else if (x.getName().compareTo(i.elemento.getName()) < 0) {
         remover2(x, i.esq, i);
      } else if (x.getName().compareTo(i.elemento.getName()) > 0) {
         remover2(x, i.dir, i);
      } else if (i.dir == null) {
         pai = i.esq;
      } else if (i.esq == null) {
         pai = i.dir;
      } else {
         i.esq = maiorEsq(i, i.esq);
		}
	}

   public No getRaiz() throws Exception {
      return raiz;
   }

   public static boolean igual (ArvoreBinaria a1, ArvoreBinaria a2){
      return igual(a1.raiz, a2.raiz);
   }

   private static boolean igual (No i1, No i2){
      boolean resp;
      if(i1 != null && i2 != null){
         resp = (i1.elemento == i2.elemento) && igual(i1.esq, i2.esq) && igual(i1.dir, i2.dir);
      } else if(i1 == null && i2 == null){
         resp = true;
      } else {
         resp = false; 
      }
      return resp;
   }
}

public class Exercicio1 extends ArvoreBinaria{
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
   public static ArvoreBinaria selecionarPokemons(List<Pokemon> pokedex, Scanner scanner) throws Exception{
   String numero;
   ArvoreBinaria lista = new ArvoreBinaria();
   int id;
 //  int quantidadePokemons = 0;
      boolean encontrou;
      while(!(numero = scanner.nextLine()).equals("FIM")) {
         id = Integer.parseInt(numero);
         encontrou = false;
         int i = 0;
         while(!encontrou && i < pokedex.size()){
               if(pokedex.get(i).getId() == id){
                  Pokemon p = pokedex.get(i);
                  try {
                     lista.inserir(p);
               } catch (Exception e) {
                     System.out.println("Erro ao inserir Pokémon: " + e.getMessage());
               }
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
   public static void main(String[] args) throws Exception{
      double inicioOrdenar = now();
      Scanner scanner = new Scanner(System.in);
      List<Pokemon> pokedex = lerPokedex();
      ArvoreBinaria arvore = new ArvoreBinaria();
      try {
         arvore = selecionarPokemons(pokedex, scanner);
   } catch (Exception e) {
         System.out.println("Aconteceu um erro ao selecionar pokémon: " + e.getMessage());
         e.printStackTrace();
   }
   int somaMov = 0;
    //  arvore.caminharPre();

   //   boolean encontrou = false;
      String name = scanner.nextLine();
      while(!name.equals("FIM")) {
         ArrayList<String> movimentacoes = new ArrayList<>();
         
         System.out.println(name);
         if(arvore.pesquisar(name,movimentacoes)) {
            System.out.print("=>raiz" + " ");
            for (String mov : movimentacoes) {
               System.out.print(mov + " ");  // Exibe cada movimentação
         }
            System.out.println("SIM");
         }
         else {
            System.out.print("=>raiz" + " ");
            for (String mov : movimentacoes) {
               System.out.print(mov + " ");  // Exibe cada movimentação
         }
            System.out.println("NAO");
         }
         name = scanner.nextLine();
         somaMov += movimentacoes.size();
      }
      double fimOrdenar = now();
      int matricula = 852302;
      try (FileWriter fileWriter = new FileWriter("matrícula_arvoreBinaria.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(matricula + "\t" + somaMov + "\t" + (fimOrdenar-inicioOrdenar)/1000.0 + "s");
      } catch (IOException e) {
         //   System.out.println("Ocorreu um erro ao criar o arquivo de log.");
          //  e.printStackTrace();
      }
   }
   public static long now() {
      return new Date().getTime();
}
}
