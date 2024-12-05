#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>
#include <math.h>

// String remove um caractere de uma string
void cleanString(char *str) {
	char garbage[] = {'\'', '[', ']'};
	int garbageSize = sizeof(garbage) / sizeof(garbage[0]);
	int index = 0;

	for (int i = 0; str[i]; i++) {
		int isGarbage = 0;
		for (int j = 0; j < garbageSize; j++) {
			if (str[i] == garbage[j]) {
				isGarbage = 1;
				break;
			}
		}
		if (!isGarbage) {
			str[index++] = str[i];
		}
	}

	str[index] = '\0';
}

// StringList
typedef struct Node {
	char *string;
	struct Node *next;
} Node;

typedef struct StringList {
	Node *head;
	int tam;
} StringList;


StringList *newStringList() {
	StringList *list = malloc(sizeof(StringList));

	list->head = malloc(sizeof(Node));
	list->tam = 0;

	return list;
}

void addStringToList(StringList *list, char *string) {
	Node *curr;
	for (curr = list->head; curr->next != NULL; curr = curr->next);

	curr->next = malloc(sizeof(Node));
	curr->next->string = string;

	list->tam++;
}

char *stringListToString(StringList *list) {
	if (list->tam < 1)
		return "";

	char *str = calloc(150, sizeof(char));

	// até o penúltimo elemento
	Node *curr;
	for (curr = list->head->next; curr->next != NULL; curr = curr->next) {
		cleanString(curr->string);
		strcat(str, curr->string);
		strcat(str, ", ");
	}

	// último elemento
	cleanString(curr->string);
	strcat(str, curr->string);

	return str;
}


#define MAX_POKEMON 1000
#define MAX_STRING 256
#define MAX_ABILITIES 6
#define MAX_TYPES 3
#define MAX_TAM 100 
#define CLOCK_MONOTONIC 0

typedef struct 
{
    char id[MAX_STRING];
    int generation;
    char name[MAX_STRING];
    char description[MAX_STRING];
    char types[MAX_TYPES][MAX_STRING];
    char abilities[MAX_ABILITIES][MAX_STRING];
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    struct tm captureDate;
} Pokemon;

//prototipaÃ§Ã£o
const char* getId(Pokemon *pokemon);
const char* getName(Pokemon *pokemon);
const char* getDescription(Pokemon *pokemon);
const char* getType(Pokemon *pokemon, int index);
const char* getAbility(Pokemon *pokemon, int index);
double getWeight(Pokemon *pokemon);
double getHeight(Pokemon *pokemon);
bool getIsLegendary(Pokemon *pokemon);
int getGeneration(Pokemon *pokemon);
int getCaptureRate(Pokemon *pokemon);
struct tm getCaptureDate(Pokemon *pokemon);

void trim(char *str) 
{
    char *end;
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return;
    end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
}

void parseDate(char *dateStr, struct tm *date) 
{
    if (sscanf(dateStr, "%d/%d/%d", &date->tm_mday, &date->tm_mon, &date->tm_year) != 3) 
    {
        return;
    }
    date->tm_mon -= 1;
    date->tm_year -= 1900;
}

int split_csv_line(char *line, char **fields, int max_fields) 
{
    int field_count = 0;
    char *ptr = line;
    int in_quotes = 0;
    char *field_start = ptr;

    while (*ptr && field_count < max_fields) 
    {
        if (*ptr == '"') 
        {
            in_quotes = !in_quotes;
        } else if (*ptr == ',' && !in_quotes) 
        {
            *ptr = '\0';
            fields[field_count++] = field_start;
            field_start = ptr + 1;
        }
        ptr++;
    }
    // Adiciona o Ãºltimo campo
    if (field_count < max_fields) 
    {
        fields[field_count++] = field_start;
    }

    return field_count;
}

// FunÃ§Ã£o que duplica strings
char *my_strdup(const char *str) 
{
    char *dup = malloc(strlen(str) + 1);
    if (dup) 
    {
        strcpy(dup, str);
    }
    return dup;
}

// FunÃ§Ãµes Getters
const char* getId(Pokemon *pokemon) 
{
    return pokemon->id;
}

const char* getName(Pokemon *pokemon) 
{
    return pokemon->name;
}

const char* getDescription(Pokemon *pokemon) 
{
    return pokemon->description;
}

const char* getType(Pokemon *pokemon, int index) 
{
    if (index >= 0 && index < MAX_TYPES) 
    {
        return pokemon->types[index];
    }
    return "";
}

const char* getAbility(Pokemon *pokemon, int index) 
{
    if (index >= 0 && index < MAX_ABILITIES) 
    {
        return pokemon->abilities[index];
    }
    return "";
}

double getWeight(Pokemon *pokemon) 
{
    return pokemon->weight;
}

double getHeight(Pokemon *pokemon) 
{
    return pokemon->height;
}

bool getIsLegendary(Pokemon *pokemon) 
{
    return pokemon->isLegendary;
}

int getGeneration(Pokemon *pokemon) 
{
    return pokemon->generation;
}

int getCaptureRate(Pokemon *pokemon) 
{
    return pokemon->captureRate;
}

struct tm getCaptureDate(Pokemon *pokemon) 
{
    return pokemon->captureDate;
}

// FunÃ§Ã£o ler PokÃ©mon CSV
int lerPokemon(char *path, Pokemon *pokedex) 
{
    FILE *file = fopen(path,"r");
    char line[1024];
    int resp = 0;

    fgets(line, sizeof(line), file); // LÃª o cabeÃ§alho do CSV

    while (fgets(line, sizeof(line), file) != NULL) 
    {
        line[strcspn(line, "\n")] = '\0'; // Remove a nova linha

        Pokemon p;
        memset(&p, 0, sizeof(Pokemon)); // Zera a memÃ³ria da estrutura PokÃ©mon

        char *fields[12]; // Ajuste se houver mais campos
        int field_count = split_csv_line(line, fields, 12);

        // id
        strncpy(p.id, fields[0], MAX_STRING);

        // generation
        p.generation = atoi(fields[1]);

        // name
        strncpy(p.name, fields[2], MAX_STRING);

        // description
        strncpy(p.description, fields[3], MAX_STRING);

        // types
        strncpy(p.types[0], fields[4], MAX_STRING);
        if (strlen(fields[5]) > 0) 
        {
            strncpy(p.types[1], fields[5], MAX_STRING);
        }

        // abilities
        char *abilities_field = fields[6];
        // Remove aspas duplas
        if (abilities_field[0] == '"' && abilities_field[strlen(abilities_field) - 1] == '"') 
        {
            abilities_field[strlen(abilities_field) - 1] = '\0';
            abilities_field++;
        }
        // Remove colchetes
        if (abilities_field[0] == '[' && abilities_field[strlen(abilities_field) - 1] == ']') 
        {
            abilities_field[strlen(abilities_field) - 1] = '\0';
            abilities_field++;
        }

        // Divide as habilidades
        char *abilityToken;
        char *restAbilities = abilities_field;
        int abilityIndex = 0;
        while ((abilityToken = strtok_r(restAbilities, ",", &restAbilities)) && abilityIndex < MAX_ABILITIES) 
        {
            // Remove espaÃ§os e aspas simples
            while (*abilityToken == ' ' || *abilityToken == '\'') abilityToken++;
            char *tempEnd = abilityToken + strlen(abilityToken) - 1;
            while (tempEnd > abilityToken && (*tempEnd == ' ' || *tempEnd == '\'')) 
            {
                *tempEnd = '\0';
                tempEnd--;
            }
            if (strlen(abilityToken) > 0 && abilityIndex < MAX_ABILITIES) 
            {
                strncpy(p.abilities[abilityIndex], abilityToken, MAX_STRING);
                abilityIndex++;
            }
        }

        // weight
        p.weight = atof(fields[7]);

        // height
        p.height = atof(fields[8]);

        // captureRate
        p.captureRate = atoi(fields[9]);

        // isLegendary
        p.isLegendary = atoi(fields[10]);

        // captureDate
        struct tm captureDate = {0};
        parseDate(fields[11], &captureDate);
        p.captureDate = captureDate;

        pokedex[resp++] = p;
    }
    return resp;
}

void imprimir(Pokemon *pokemon) 
{
    char dateStr[11]; // Formato "dd/mm/yyyy"
    strftime(dateStr, sizeof(dateStr), "%d/%m/%Y", &pokemon->captureDate);
    
    printf(
        "[#%s -> %s: %s - [", 
        getId(pokemon), 
        getName(pokemon), 
        getDescription(pokemon)
    );

    // Tipos
    for (int j = 0; j < MAX_TYPES && strlen(getType(pokemon, j)) > 0; j++) {
        if (j > 0) printf(", ");
        printf("'%s'", getType(pokemon, j));
    }
    printf("] - [");

    // Habilidades
    for (int j = 0; j < MAX_ABILITIES && strlen(getAbility(pokemon, j)) > 0; j++) {
        if (j > 0) printf(", ");
        printf("'%s'", getAbility(pokemon, j));
    }
    printf("] - ");

    // Peso, altura, taxa de captura, se Ã© lendÃ¡rio, geraÃ§Ã£o e data de captura
    printf("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n", 
        getWeight(pokemon), 
        getHeight(pokemon), 
        getCaptureRate(pokemon), 
        getIsLegendary(pokemon) ? "true" : "false", 
        getGeneration(pokemon), 
        dateStr
    );
}


// FunÃ§Ã£o de busca por ID
Pokemon* findPokemonById(Pokemon* pokedex, int count, int id) 
{
    for (int i = 0; i < count; i++) 
    {
        if (atoi(pokedex[i].id) == id) 
        {
            return &pokedex[i];
        }
    }
    return NULL;
}

// FunÃ§Ã£o de busca por nome
Pokemon* findPokemonByName(Pokemon* pokedex, int count, const char* name) 
{
    for (int i = 0; i < count; i++) 
    {
        if (strcmp(pokedex[i].name, name) == 0) 
        {
            return &pokedex[i];
        }
    }
    return NULL;
}

Pokemon* buscarPersonagem(char *id, Pokemon *personagens, int qtde) {
	for (int i = 0; i < qtde; i++) {
		if (strcmp(id, personagens[i].id) == 0)
			return &personagens[i];
	}
}

typedef struct Celula {
  Pokemon *personagem;
  struct Celula *prox;
} Celula;

typedef struct Lista {
  Celula *head;
  Celula *tail;
  int qtde;
} Lista;

Lista *newLista() {
  Lista *lista = malloc(sizeof(Lista));

  lista->head = malloc(sizeof(Celula));
  lista->tail = lista->head;
  lista->qtde = 0;

  return lista;
}

void inserirLista(Lista *lista, Pokemon *p) {
  Celula *nova = malloc(sizeof(Celula));
  nova->personagem = p;
  nova->prox = NULL;

  if (lista->qtde == 0) {
    lista->head->prox = nova;
    lista->tail = nova;
  } else {
    lista->tail->prox = nova;
    lista->tail = nova;
  }

  lista->qtde++;
}

bool pesquisarLista(Lista lista, char *nome) {

	if (lista.qtde > 0) {
		Celula *atual = lista.head->prox;

		while (atual != NULL) {
			if (strcmp(atual->personagem->name, nome) == 0) {
				return true; 
			}
			atual = atual->prox;
		}
	}
	return false;
}


#define TAM_HASH 21
typedef struct Hash {
  Lista *tabela[TAM_HASH];
} Hash;

Hash newHash() {
  Hash hash;

  for (int i = 0; i < TAM_HASH; i++) {
    hash.tabela[i] = newLista();
  }

  return hash;
}

int hashF(char *name) {
  int sum = 0;
  for (int i = 0; i < strlen(name); i++) {
    sum += (int)name[i];
  }
  return sum % TAM_HASH;
}

void inserirHash(Hash *hash, Pokemon *p) {
if (p == NULL || hash == NULL)
    return;

int pos = hashF(p->name);
inserirLista(hash->tabela[pos], p);
}

int pesquisarHash(Hash *hash, char nome[]) {
int resp = -1;
int pos = hashF(nome);
if (pesquisarLista(*hash->tabela[pos], nome)) {
    resp = pos;
}
return resp;
}

long getCurrentTimeInNanos() 
{
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return ts.tv_sec * 1e9 + ts.tv_nsec;
}

int main() {
    long startTime = getCurrentTimeInNanos();
	// LER CSV
	Pokemon personagens[801];

	int tot = lerPokemon("/tmp/pokemon.csv", personagens);

	Hash hashPersonagens = newHash();

	char id[36];
	scanf("%s", id);
	while (strcmp(id, "FIM")) {
		// Busca personagem pelo id e insere no fim da lista
		inserirHash(&hashPersonagens, buscarPersonagem(id, personagens, tot));
		scanf("%s", id);
	}

char nome[36];
    scanf(" %[^\n\r]", nome);
while (strcmp(nome, "FIM")!=0) {
    // Busca personagem pelo nome
    printf("=> %s: ", nome);
    int pos = pesquisarHash(&hashPersonagens, nome);
    if (pos >= 0) {
    printf("(Posicao: %d) SIM\n", pos);
    } else {
    printf("NAO\n");
    }
    scanf(" %[^\n\r]", nome);
}
long endTime = getCurrentTimeInNanos(); // Captura o tempo final
    long executionTime = (endTime - startTime) / 1e6; // Tempo em milissegundos

    char matricula[] = "852302";
    int comparacoes = 0; 

    FILE *writer = fopen("852302_hashIndireta", "w");
    if (writer != NULL) 
    {
        fprintf(writer, "%s\t%ld\t%d\n", matricula, executionTime, comparacoes);
        fclose(writer);
    } 
    else 
    {
        printf("Erro ao criar o arquivo de log\n");
    }
	return 0;
}