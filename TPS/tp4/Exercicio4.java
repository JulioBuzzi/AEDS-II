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

/**
 * NoAN da arvore binaria
 * 
 * @author Max do Val Machado
 */
class NoAN {
   public boolean cor;
   public Pokemon elemento;
   public NoAN esq, dir;

   public NoAN() {
   this(null);
   }

   public NoAN(Pokemon elemento) {
   this(elemento, false, null, null);
   }

   public NoAN(Pokemon elemento, boolean cor) {
   this(elemento, cor, null, null);
   }

   public NoAN(Pokemon elemento, boolean cor, NoAN esq, NoAN dir) {
   this.cor = cor;
   this.elemento = elemento;
   this.esq = esq;
   this.dir = dir;
   }
}

/**
 * Arvore binaria de pesquisa
 * 
 * @author Max do Val Machado
 */
class Alvinegra {
   private NoAN raiz; // Raiz da arvore.

   /**
    * Construtor da classe.
    */
   public Alvinegra() {
      raiz = null;
   }

   /**
    * Metodo publico iterativo para pesquisar elemento.
    * 
    * @param elemento Elemento que sera procurado.
    * @return <code>true</code> se o elemento existir,
    *         <code>false</code> em caso contrario.
    */
   public boolean pesquisar(String elemento,ArrayList<String> movimentacoes) {
      return pesquisar(elemento, raiz,movimentacoes);
   }

   /**
    * Metodo privado recursivo para pesquisar elemento.
    * 
    * @param elemento Elemento que sera procurado.
    * @param i        NoAN em analise.
    * @return <code>true</code> se o elemento existir,
    *         <code>false</code> em caso contrario.
    */
   private boolean pesquisar(String elemento, NoAN i,ArrayList<String> movimentacoes) {
      boolean resp;
      if (i == null) {
         resp = false;
      } else if (elemento.compareTo(i.elemento.getName()) == 0) {
         resp = true;
      } else if (elemento.compareTo(i.elemento.getName()) < 0) {
         movimentacoes.add("esq");
         resp = pesquisar(elemento, i.esq,movimentacoes);
      } else {
         movimentacoes.add("dir");
         resp = pesquisar(elemento, i.dir,movimentacoes);
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
    * 
    * @param i NoAN em analise.
    */
   private void caminharCentral(NoAN i) {
      if (i != null) {
         caminharCentral(i.esq); // Elementos da esquerda.
         System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
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
    * 
    * @param i NoAN em analise.
    */
   private void caminharPre(NoAN i) {
      if (i != null) {
         System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
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
    * 
    * @param i NoAN em analise.
    */
   private void caminharPos(NoAN i) {
      if (i != null) {
         caminharPos(i.esq); // Elementos da esquerda.
         caminharPos(i.dir); // Elementos da direita.
         System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
      }
   }

   /**
    * Metodo publico iterativo para inserir elemento.
    * 
    * @param elemento Elemento a ser inserido.
    * @throws Exception Se o elemento existir.
    */
   public void inserir(Pokemon elemento) throws Exception {
      // Se a arvore estiver vazia
      if (raiz == null) {
         raiz = new NoAN(elemento);
        // System.out.println("Antes, zero elementos. Agora, raiz(" + raiz.elemento + ").");

      // Senao, se a arvore tiver um elemento
      } else if (raiz.esq == null && raiz.dir == null) {
         if (elemento.getName().compareTo(raiz.elemento.getName()) < 0) {
            raiz.esq = new NoAN(elemento);
         //   System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e esq(" + raiz.esq.elemento + ").");
         } else {
            raiz.dir = new NoAN(elemento);
         //   System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e dir(" + raiz.dir.elemento + ").");
         }

      // Senao, se a arvore tiver dois elementos (raiz e dir)
      } else if (raiz.esq == null) {
         if (elemento.getName().compareTo(raiz.elemento.getName()) < 0 ) {
            raiz.esq = new NoAN(elemento);
          //  System.out.println("Antes, dois elementos(A). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else if (elemento.getName().compareTo(raiz.dir.elemento.getName()) < 0) {
            raiz.esq = new NoAN(raiz.elemento);
            raiz.elemento = elemento;
           // System.out.println("Antes, dois elementos(B). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else {
            raiz.esq = new NoAN(raiz.elemento);
            raiz.elemento = raiz.dir.elemento;
            raiz.dir.elemento = elemento;
          //  System.out.println("Antes, dois elementos(C). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
         }
         raiz.esq.cor = raiz.dir.cor = false;

      // Senao, se a arvore tiver dois elementos (raiz e esq)
      } else if (raiz.dir == null) {
         if (elemento.getName().compareTo(raiz.elemento.getName()) > 0) {
            raiz.dir = new NoAN(elemento);
         //   System.out.println("Antes, dois elementos(D). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else if (elemento.getName().compareTo(raiz.esq.elemento.getName()) > 0) {
            raiz.dir = new NoAN(raiz.elemento);
            raiz.elemento = elemento;
         //   System.out.println("Antes, dois elementos(E). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else {
            raiz.dir = new NoAN(raiz.elemento);
            raiz.elemento = raiz.esq.elemento;
            raiz.esq.elemento = elemento;
         //   System.out.println("Antes, dois elementos(F). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
         }
         raiz.esq.cor = raiz.dir.cor = false;

      // Senao, a arvore tem tres ou mais elementos
      } else {
       //  System.out.println("Arvore com tres ou mais elementos...");
         inserir(elemento, null, null, null, raiz);
      }
      raiz.cor = false;
   }

   private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
      // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
      if (pai.cor == true) {
         // 4 tipos de reequilibrios e acoplamento
         if (pai.elemento.getName().compareTo(avo.elemento.getName()) > 0) { // rotacao a esquerda ou direita-esquerda
            if (i.elemento.getName().compareTo(pai.elemento.getName()) > 0) {
               avo = rotacaoEsq(avo);
            } else {
               avo = rotacaoDirEsq(avo);
            }
         } else { // rotacao a direita ou esquerda-direita
            if (i.elemento.getName().compareTo(pai.elemento.getName()) < 0) {
               avo = rotacaoDir(avo);
            } else {
               avo = rotacaoEsqDir(avo);
            }
         }
         if (bisavo == null) {
            raiz = avo;
         } else if (avo.elemento.getName().compareTo(bisavo.elemento.getName()) < 0) {
            bisavo.esq = avo;
         } else {
            bisavo.dir = avo;
         }
         // reestabelecer as cores apos a rotacao
         avo.cor = false;
         avo.esq.cor = avo.dir.cor = true;
        // System.out.println("Reestabeler cores: avo(" + avo.elemento + "->branco) e avo.esq / avo.dir("
            //   + avo.esq.elemento + "," + avo.dir.elemento + "-> pretos)");
      } // if(pai.cor == true)
   }

   /**
    * Metodo privado recursivo para inserir elemento.
    * 
    * @param elemento Elemento a ser inserido.
    * @param avo      NoAN em analise.
    * @param pai      NoAN em analise.
    * @param i        NoAN em analise.
    * @throws Exception Se o elemento existir.
    */
   private void inserir(Pokemon elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
      if (i == null) {
         if (elemento.getName().compareTo(pai.elemento.getName()) < 0) {
            i = pai.esq = new NoAN(elemento, true);
         } else {
            i = pai.dir = new NoAN(elemento, true);
         }
         if (pai.cor == true) {
            balancear(bisavo, avo, pai, i);
         }
      } else {
         // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
         if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
            i.cor = true;
            i.esq.cor = i.dir.cor = false;
            if (i == raiz) {
               i.cor = false;
            } else if (pai.cor == true) {
               balancear(bisavo, avo, pai, i);
            }
         }
         if (elemento.getName().compareTo(i.elemento.getName()) < 0) {
            inserir(elemento, avo, pai, i, i.esq);
         } else if (elemento.getName().compareTo(i.elemento.getName()) > 0) {
            inserir(elemento, avo, pai, i, i.dir);
         } else {
            throw new Exception("Erro inserir (elemento repetido)!");
         }
      }
   }

   private NoAN rotacaoDir(NoAN no) {
     // System.out.println("Rotacao DIR(" + no.elemento + ")");
      NoAN noEsq = no.esq;
      NoAN noEsqDir = noEsq.dir;

      noEsq.dir = no;
      no.esq = noEsqDir;

      return noEsq;
   }

   private NoAN rotacaoEsq(NoAN no) {
   //   System.out.println("Rotacao ESQ(" + no.elemento + ")");
      NoAN noDir = no.dir;
      NoAN noDirEsq = noDir.esq;

      noDir.esq = no;
      no.dir = noDirEsq;
      return noDir;
   }

   private NoAN rotacaoDirEsq(NoAN no) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
   }

   private NoAN rotacaoEsqDir(NoAN no) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
   }
}

public class Exercicio4 extends Alvinegra{
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
   public static Alvinegra selecionarPokemons(List<Pokemon> pokedex, Scanner scanner) throws Exception{
   String numero;
   Alvinegra lista = new Alvinegra();
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
      Alvinegra arvore = new Alvinegra();
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
            System.out.print("raiz" + " ");
            for (String mov : movimentacoes) {
               System.out.print(mov + " ");  // Exibe cada movimentação
         }
            System.out.println("SIM");
         }
         else {
            System.out.print("raiz" + " ");
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
      try (FileWriter fileWriter = new FileWriter("matrícula_alvinegra.txt");
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
