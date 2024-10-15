#include <stdio.h>
#include <string.h>

int main() {
    char s1[100], s2[100], resultado[200];
    int len1, len2, i, j;

    //compara pra ver se ainda esta salvando algo no arquivo (EOF)

    while(scanf("%s",s1)==1 && scanf("%s",s2)==1) {


    len1 = strlen(s1);
    len2 = strlen(s2);

    //monta a string

    for (i = 0, j = 0; i < len1 && i < len2; i++, j += 2) {
        resultado[j] = s1[i];
        resultado[j + 1] = s2[i];
    }

    //completa a string com o resto da maior

    while (i < len1) {
        resultado[j++] = s1[i++];
    }
    while (i < len2) {
        resultado[j++] = s2[i++];
    }

    resultado[j] = '\0';

    printf("%s\n", resultado);

    }

    return 0;
}