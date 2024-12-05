#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>
#include <math.h>

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
void lerPokemon(FILE *file, Pokemon *pokedex, int *n) 
{
    char line[1024];

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

        pokedex[*n] = p;
        (*n)++;
    }
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


typedef struct No 
{
    Pokemon *pokemon;
    struct No *esq;
    struct No *dir;
    int nivel;
} No;

No* novoNo(Pokemon *pokemon) 
{
   No* no = (No*) malloc(sizeof(No));
   no->pokemon = pokemon;
   no->esq = NULL;
   no->dir = NULL;
   no->nivel = 1;
   return no;
}

int getNivel(No* no) 
{
    return (no == NULL) ? 0 : no->nivel;
}

void setNivel(No* no) 
{
    if (no != NULL) 
    {
        int nivelEsq = getNivel(no->esq);
        int nivelDir = getNivel(no->dir);
        no->nivel = 1 + ((nivelEsq > nivelDir) ? nivelEsq : nivelDir);
    }
}

bool pesquisar(char* name, No* i) 
{
    bool resp;

    if (i == NULL) 
    {
        resp = false;
    } 
    else if (strcmp(name, i->pokemon->name) == 0) 
    {
        resp = true;
    } 
    else if (strcmp(name, i->pokemon->name) < 0) 
    {
        printf("esq ");
        resp = pesquisar(name, i->esq);
    } 
    else 
    {
        printf("dir ");
        resp = pesquisar(name, i->dir);
    }

    return resp;
}

No* rotacionarDir(No* no) 
{
    No* noEsq = no->esq;
    No* noEsqDir = noEsq -> dir;

    noEsq->dir = no;
    no->esq = noEsqDir;

    setNivel(no);
    setNivel(noEsq);

    return noEsq;
}

No* rotacionarEsq(No* no) 
{
    No* noDir = no->dir;
    No* noDirEsq = noDir->esq;

    noDir->esq = no;
    no->dir = noDirEsq;

    setNivel(no);
    setNivel(noDir);

    return noDir;
}

No* balancear(No* no) 
{
    if (no != NULL) 
    {
        int fator = getNivel(no->dir) - getNivel(no->esq);

        if (abs(fator) <= 1) 
        {
            setNivel(no);
        } 
        else if (fator == 2) 
        {
            int fatorFilhoDir = getNivel(no->dir->dir) - getNivel(no->dir->esq);
            if (fatorFilhoDir == -1) 
            {
                no->dir = rotacionarDir(no->dir);
            }
            no = rotacionarEsq(no);
        } 
        else if (fator == -2) 
        {
            int fatorFilhoEsq = getNivel(no->esq->dir) - getNivel(no->esq->esq);
            if (fatorFilhoEsq == 1) 
            {
                no->esq = rotacionarEsq(no->esq);
            }
            no = rotacionarDir(no);
        }
    }
    return no;
}

No* inserir(No* no, Pokemon* pokemon) 
{
    if (no == NULL) 
    {
        no = novoNo(pokemon);
    } 
    else if (strcmp(pokemon->name, no->pokemon->name) < 0) 
    {
        no->esq = inserir(no->esq, pokemon);
    } 
    else if (strcmp(pokemon->name, no->pokemon->name) > 0) 
    {
        no->dir = inserir(no->dir, pokemon);
    }

    return balancear(no);
}

No* maiorEsq(No* i, No* j) 
{
    if (j->dir == NULL)
    {
        i->pokemon = j->pokemon;
        No* temp = j->esq;
        free(j);
        return temp;
    } 
    else 
    {
        j->dir = maiorEsq(i, j->dir);
        j = balancear(j);
        return j;
    }
}


typedef struct AVL 
{
    No* raiz;
} AVL;

void inicializarAVL(AVL* arvore) 
{
    arvore->raiz = NULL;
}

void inserirAVL(AVL* arvore, Pokemon* pokemon) 
{
    arvore->raiz = inserir(arvore->raiz, pokemon);
}

int pesquisarAVL(AVL* arvore, char* name) 
{
    return pesquisar(name, arvore->raiz);
}

void liberarArvore(No* no) 
{
    if (no != NULL) 
    {
        liberarArvore(no->esq);
        liberarArvore(no->dir);
        free(no);
    }
}

long getCurrentTimeInNanos() 
{
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return ts.tv_sec * 1e9 + ts.tv_nsec;
}


int main() 
{
    long startTime = getCurrentTimeInNanos();
    FILE *file = fopen("/tmp/pokemon.csv", "r");
    if (file == NULL) 
    {
        perror("Erro ao abrir arquivo");
        return 1;
    }

    Pokemon pokedex[MAX_POKEMON];
    int count = 0;
    lerPokemon(file, pokedex, &count);
    fclose(file);

    AVL arvoreAVL; 
    inicializarAVL(&arvoreAVL);

    char inputId[10];
    scanf("%s", inputId);

    int j = 0;
    
    while (strcmp(inputId, "FIM") != 0) 
    {
        int id = atoi(inputId);
        Pokemon* pokemon = findPokemonById(pokedex, count, id);

        if(pokemon != NULL) 
        {
            inserirAVL(&arvoreAVL, pokemon);
        }
        
        scanf("%s", inputId); 
    }

    char name[20];
    scanf("%s", name);

    while(strcmp(name, "FIM") != 0) 
    {
        Pokemon* pokemon = findPokemonByName(pokedex, count, name);
        printf("%s\n", name);
        printf("raiz ");

        bool found = pesquisarAVL(&arvoreAVL, name);
        printf("%s\n", found ? "SIM" : "NAO");

        scanf("%s", name);
    }

    long endTime = getCurrentTimeInNanos(); // Captura o tempo final
    long executionTime = (endTime - startTime) / 1e6; // Tempo em milissegundos

    char matricula[] = "843474";
    int comparacoes = 0; 

    FILE *writer = fopen("843474_avl.txt", "w");
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