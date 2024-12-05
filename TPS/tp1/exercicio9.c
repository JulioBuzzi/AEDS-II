#include <stdio.h>
#include <stdlib.h>

int main(){
    FILE* arq = fopen("reais.txt", "w");
    int n;
    scanf("%d", &n);
    float num;
    for(int i = 0; i < n; i++){
        scanf("%f", &num);
        fwrite(&num, 4, 1, arq);    
    }
    fclose(arq);
    arq = fopen("reais.txt", "r");
    fseek(arq, 0, SEEK_END);
    long i = ftell(arq);
    float salvar;
    while(i >= 0){
        i -= sizeof(float); 
        fseek(arq, i, SEEK_SET);
        fread(&salvar, 4, 1, arq);
        printf("%g\n", salvar);
    }
    fclose(arq);
    return 0;
}