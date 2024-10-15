#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int verificaMaiscula(char *string) {
    int maisculas=0;
    int n = strlen(string);
    char fim[3] = "FIM";

    if(strcmp(string,fim)== 0) {
        return 0;
    }
    else {
    for(int i=0;i<n;i++) {
        if(string[i]>='A'&& string[i]<='Z'){
            maisculas++;
        }
    }
    return maisculas;
    }
}


int main() {
    char string[50],fim[3]="FIM";

    do{
    //printf("Digite o texto: ");
    scanf(" %[^\n]",string);
    if(verificaMaiscula(string)!=0){
        printf("%d\n",verificaMaiscula(string));
    }
    else return 0;
    }while(((strcmp(string,fim))!= 0));

    return 0;

}