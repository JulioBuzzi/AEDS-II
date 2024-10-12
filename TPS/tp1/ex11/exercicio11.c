#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool ehPalindromoRec(char string[], int i, int j) {
bool flag = true;

    if(i<j) flag = string[i] == string[j] && ehPalindromoRec(string,i+1,j-1);

    else flag = false;
}

bool ehPalindromo(char string[]) {
    return ehPalindromoRec(string, 0, strlen(string) - 1);
}

int main() {
    char string[500];

    scanf(" %[^\n]", string);

    while (strcmp(string, "FIM") != 0) {
        if (ehPalindromo(string)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        scanf(" %[^\n]", string);
    }

    return 0;
}