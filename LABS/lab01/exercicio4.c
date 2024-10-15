#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int verificaMaiscula(char *string) {
    int maisculas = 0;
    int n = strlen(string);
    const char *fim = "FIM"; 

    if (strcmp(string, fim) == 0) {
        return 0;
    } else {
        for (int i = 0; i < n; i++) {
            if (string[i] >= 'A' && string[i] <= 'Z') {
                maisculas++;
            }
        }
        return maisculas;
    }
}

void processarEntrada() {
    char string[50];
    const char *fim = "FIM";


    //printf("Digite o texto: ");
    scanf(" %[^\n]", string);

    int resultado = verificaMaiscula(string);
    if (resultado == 0) {
        return; 
    } else {
        printf("%d\n", resultado);
    }


    processarEntrada();
}

int main() {
    processarEntrada();
    return 0;
}