#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool isPalindromo (char palavra[]) {
    int i=0, j=strlen(palavra) - 1;
    bool flag = true;

while(i < j) {
    if(palavra[i] != palavra[j]) {
        flag = false;
    }
    i++;
    j--;
}

return flag;
}

int main() {
    char palavra[500];

    scanf(" %[^\n]",palavra);

    do {
        if(isPalindromo(palavra)) printf("SIM\n");
        else printf("NAO\n") ;

        scanf(" %[^\n]",palavra);
    } while(strcmp(palavra,"FIM")!=0);


    return 0;
}