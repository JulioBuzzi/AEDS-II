#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define MAX_POKEMON 1000
#define MAX_STRING 256
#define MAX_ABILITIES 6
#define MAX_TYPES 3
#define MAX_TAM 100 

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


//Pilha
typedef struct 
{
    int tam;
    Pokemon *pokemons;
    int n;
} Pilha;

void inicializarPilha(Pilha* pilha, int tam) 
{
    pilha->tam = tam;
    pilha->n = 0;
    pilha->pokemons = (Pokemon*)malloc(tam * sizeof(Pokemon));
    if (pilha->pokemons == NULL) 
    {
        printf("Erro ao alocar memÃ³ria para a pilha.\n");
        exit(1);
    }
}

void inserirFim(Pilha* pilha, Pokemon pokemon) 
{
    if (pilha->n >= pilha->tam) 
    {
        pilha->tam *= 2;
        pilha->pokemons = (Pokemon*)realloc(pilha->pokemons, pilha->tam * sizeof(Pokemon));
        if (pilha->pokemons == NULL) 
        {
            printf("Erro ao redimensionar a pilha.\n");
            exit(1);
        }
    }

    pilha->pokemons[pilha->n] = pokemon;
    pilha->n++;
}

Pokemon removerFim(Pilha* pilha) 
{
    if (pilha->n == 0) 
    {
        printf("Erro ao remover!\n");
        exit(1); 
    }

    return pilha->pokemons[--(pilha->n)];
}

void imprimirLista(Pilha* pilha) 
{
    for (int i = 0; i < pilha->n; i++) 
    {
        printf("[%d] ", i);
        imprimir(&pilha->pokemons[i]); 
    }
}

int main() 
{
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

    Pilha pokePilha;
    inicializarPilha(&pokePilha, MAX_TAM);

    char input[MAX_STRING];
    while (1) 
    {
        if (fgets(input, sizeof(input), stdin) == NULL) 
        {
            break;
        }
        trim(input);

        if (strcmp(input, "FIM") == 0) 
        {
            break;
        }

        int pokemonIndex = atoi(input) - 1;
        if (pokemonIndex >= 0 && pokemonIndex < count) 
        {
            inserirFim(&pokePilha, pokedex[pokemonIndex]);
        } 
        else 
        {
            printf("Ãndice invÃ¡lido para PokÃ©mon.\n");
        }
    }

    int N;
    scanf(" %d", &N);

    char tmp[10];
    for (int i = 0; i < N; i++) 
    {
        scanf(" %[^\n]s", tmp);

        char action = tmp[0]; 
        int pokemonIndex = 0;

        if (action == 'I') 
        {
            pokemonIndex = atoi(tmp + 2); 
            inserirFim(&pokePilha, pokedex[pokemonIndex - 1]);
        } 
        else if (action == 'R') 
        {
            Pokemon removidoFim = removerFim(&pokePilha);
            printf("(R) %s\n", removidoFim.name);
        }
    }

    imprimirLista(&pokePilha);
    free(pokePilha.pokemons);

    return 0;
}