#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>
#include <sys/time.h>

#define MAX_POKEMON 1000
#define MAX_STRING 256
#define MAX_ABILITIES 6
#define MAX_TYPES 3
#define MAX_CHAR 256

typedef struct {
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

// Prototipação das funções
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

void trim(char *str) {
    char *end;
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return;
    end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
}

void parseDate(char *dateStr, struct tm *date) {
    if (sscanf(dateStr, "%d/%d/%d", &date->tm_mday, &date->tm_mon, &date->tm_year) != 3) {
        return;
    }
    date->tm_mon -= 1;
    date->tm_year -= 1900;
}

int split_csv_line(char *line, char **fields, int max_fields) {
    int field_count = 0;
    char *ptr = line;
    int in_quotes = 0;
    char *field_start = ptr;

    while (*ptr && field_count < max_fields) {
        if (*ptr == '"') {
            in_quotes = !in_quotes;
        } else if (*ptr == ',' && !in_quotes) {
            *ptr = '\0';
            fields[field_count++] = field_start;
            field_start = ptr + 1;
        }
        ptr++;
    }

    if (field_count < max_fields) {
        fields[field_count++] = field_start;
    }

    return field_count;
}

char *my_strdup(const char *str) {
    char *dup = malloc(strlen(str) + 1);
    if (dup) {
        strcpy(dup, str);
    }
    return dup;
}

const char* getId(Pokemon *pokemon) {
    return pokemon->id;
}

const char* getName(Pokemon *pokemon) {
    return pokemon->name;
}

const char* getDescription(Pokemon *pokemon) {
    return pokemon->description;
}

const char* getType(Pokemon *pokemon, int index) {
    if (index >= 0 && index < MAX_TYPES) {
        return pokemon->types[index];
    }
    return "";
}

const char* getAbility(Pokemon *pokemon, int index) {
    if (index >= 0 && index < MAX_ABILITIES) {
        return pokemon->abilities[index];
    }
    return "";
}

double getWeight(Pokemon *pokemon) {
    return pokemon->weight;
}

double getHeight(Pokemon *pokemon) {
    return pokemon->height;
}

bool getIsLegendary(Pokemon *pokemon) {
    return pokemon->isLegendary;
}

int getGeneration(Pokemon *pokemon) {
    return pokemon->generation;
}

int getCaptureRate(Pokemon *pokemon) {
    return pokemon->captureRate;
}

struct tm getCaptureDate(Pokemon *pokemon) {
    return pokemon->captureDate;
}

void lerPoke(FILE *file, Pokemon *pokedex, int *n) {
    char line[1024];

    fgets(line, sizeof(line), file); 

    while (fgets(line, sizeof(line), file) != NULL) {
        line[strcspn(line, "\n")] = '\0'; 

        Pokemon p;
        memset(&p, 0, sizeof(Pokemon)); 

        char *fields[12]; 
        int field_count = split_csv_line(line, fields, 12);

        strncpy(p.id, fields[0], MAX_STRING);
        p.generation = atoi(fields[1]);
        strncpy(p.name, fields[2], MAX_STRING);
        strncpy(p.description, fields[3], MAX_STRING);
        strncpy(p.types[0], fields[4], MAX_STRING);
        if (strlen(fields[5]) > 0) {
            strncpy(p.types[1], fields[5], MAX_STRING);
        }

        char *abilities_field = fields[6];

        if (abilities_field[0] == '"' && abilities_field[strlen(abilities_field) - 1] == '"') {
            abilities_field[strlen(abilities_field) - 1] = '\0';
            abilities_field++;
        }

        if (abilities_field[0] == '[' && abilities_field[strlen(abilities_field) - 1] == ']') {
            abilities_field[strlen(abilities_field) - 1] = '\0';
            abilities_field++;
        }

        char *abilityToken;
        char *restAbilities = abilities_field;
        int abilityIndex = 0;
        while ((abilityToken = strtok(restAbilities, ",")) && abilityIndex < MAX_ABILITIES) {
            while (*abilityToken == ' ' || *abilityToken == '\'') abilityToken++;
            char *tempEnd = abilityToken + strlen(abilityToken) - 1;
            while (tempEnd > abilityToken && (*tempEnd == ' ' || *tempEnd == '\'')) {
                *tempEnd = '\0';
                tempEnd--;
            }
            if (strlen(abilityToken) > 0 && abilityIndex < MAX_ABILITIES) {
                strncpy(p.abilities[abilityIndex], abilityToken, MAX_STRING);
                abilityIndex++;
            }
            restAbilities = NULL;
        }

        p.weight = atof(fields[7]);
        p.height = atof(fields[8]);
        p.captureRate = atoi(fields[9]);
        p.isLegendary = atoi(fields[10]);

        struct tm captureDate = {0};
        parseDate(fields[11], &captureDate);
        p.captureDate = captureDate;

        pokedex[*n] = p;
        (*n)++;
    }
}

void swap(Pokemon *a, Pokemon *b) {
    Pokemon temp = *a;
    *a = *b;
    *b = temp;
}

int partition(Pokemon *pokemons, int low, int high, int *comparacoes) {
    Pokemon pivot = pokemons[high]; 
    int i = (low - 1); 

    for (int j = low; j < high; j++) {
        (*comparacoes)++;
        if (atoi(pokemons[j].id) < atoi(pivot.id)) {
            i++; 
            swap(&pokemons[i], &pokemons[j]);
        }
    }
    swap(&pokemons[i + 1], &pokemons[high]);
    return (i + 1);
}


long long now() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return (tv.tv_sec * 1000LL) + (tv.tv_usec / 1000);
}

int getMaxLen(Pokemon *array, int n) {
    int maxLen = strlen(array[0].abilities[0]);
    for (int i = 1; i < n; i++) {
        int len = strlen(array[i].abilities[0]);
        if (len > maxLen) {
            maxLen = len;
        }
    }
    return maxLen;
}


void countSortByChar(Pokemon *array, int n, int charIndex) {
    Pokemon output[n];
    int count[MAX_CHAR] = {0};

    for (int i = 0; i < n; i++) {
        char c = charIndex < strlen(array[i].abilities[0]) ? array[i].abilities[0][charIndex] : 0;
        count[(int)c]++;
    }


    for (int i = 1; i < MAX_CHAR; i++) {
        count[i] += count[i - 1];
    }


    for (int i = n - 1; i >= 0; i--) {
        char c = charIndex < strlen(array[i].abilities[0]) ? array[i].abilities[0][charIndex] : 0;
        output[count[(int)c] - 1] = array[i];
        count[(int)c]--;
    }

    for (int i = 0; i < n; i++) {
        array[i] = output[i];
    }
}


void radixSort(Pokemon *array, int n) {
    int maxLen = getMaxLen(array, n); 

    
    for (int charIndex = maxLen - 1; charIndex >= 0; charIndex--) {
        countSortByChar(array, n, charIndex);
    }
}

int main() {
    FILE *file = fopen("/tmp/pokemon.csv", "r");
    if (file == NULL) 
    {        
        perror("Erro ao abrir arquivo");
        return 1;
    }

    Pokemon pokedex[MAX_POKEMON];
    int comparacoes = 0;
    int count = 0;
    lerPoke(file, pokedex, &count);
    fclose(file);

    char numero[MAX_STRING];
    Pokemon pokemons[MAX_POKEMON];
    int id;
    int quantidadePokemons = 0;
    bool encontrou;

    scanf("%s", numero);
    while (strcmp(numero, "FIM") != 0) {
        id = atoi(numero);
        encontrou = false;
        int i = 0;
        while (encontrou == false && i < MAX_POKEMON) {
            comparacoes++;
            if (atoi(pokedex[i].id) == id) {
                pokemons[quantidadePokemons] = pokedex[i];
                quantidadePokemons++;
                encontrou = true;
            }
            i++;
        }
        if (encontrou == false) {
            printf("Pokemos com ID %d nao encontrado.", id);
        }
        scanf("%s", numero);
    }
    
    int movimentacoes = 0;
    long long inicioSort = now();
    radixSort(pokemons, quantidadePokemons);
    int n = quantidadePokemons;
    for (int i = 1; i < n; i++) {
    Pokemon key = pokemons[i];
    int j = i - 1;
    while (j >= 0 && pokemons[j].abilities[0] == key.abilities[0] 
        && strcmp(getName(&pokemons[j]), getName(&key)) > 0) {
        pokemons[j + 1] = pokemons[j];
        j = j - 1;
    }
    
    pokemons[j + 1] = key;
}
    long long fim = now();

     //FALTA IMPRIMIR
    for (int i = 0; i < quantidadePokemons; i++) {
    char captureDate[12];
    strftime(captureDate, sizeof(captureDate), "%d/%m/%Y", &pokemons[i].captureDate);
    
    //printf("%d\n", i + 1);
    printf("[#%s -> %s: %s - [", pokemons[i].id, pokemons[i].name, pokemons[i].description);
    
    // Imprimindo tipos
    for (int j = 0; j < MAX_TYPES && strlen(pokemons[i].types[j]) > 0; j++) {
        if (j > 0) printf(", ");
        printf("'%s'", pokemons[i].types[j]);
    }
    printf("] - [");
    
    // Imprimindo habilidades
    for (int j = 0; j < MAX_ABILITIES && strlen(pokemons[i].abilities[j]) > 0; j++) {
        if (j > 0) printf(", ");
        printf("'%s'", pokemons[i].abilities[j]);
    }
    printf("] - %.1lfkg - %.1lfm - %d%% - %s - %d gen] - %s\n", 
        pokemons[i].weight, 
        pokemons[i].height, 
        pokemons[i].captureRate, 
        pokemons[i].isLegendary ? "true" : "false", 
        pokemons[i].generation, 
        captureDate);
}

    int matricula = 852302;
    FILE *arq = fopen("matricula_radixsort.txt", "w");
    if (arq == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return 1;
    }
    fprintf(arq, "%d\t%d\t%d\t%lf", matricula, comparacoes, movimentacoes, (double)(fim - inicioSort) / 1000.0);
    fclose(arq);
    return 0;
}
