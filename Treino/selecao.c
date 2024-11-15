#include <stdlib.h>
#include <stdio.h>
#include <time.h>

void swap(int x, int y, int array[]) {
    int temp = array[x];
    array[x] = array[y];
    array[y] = temp;
}

void selecao(int array[]) {
    int menor;
    for(int i=0;i<500-1;i++) {
        menor = i;
        for(int j=i+1;j<500;j++) {
            if(array[menor] > array[j]) menor = j;
        }
        swap(menor,i,array);
    }
}



int main() {
    int tam = 500;
    int array[tam];

srand(time(NULL));
    for(int i=0;i<tam;i++){
        array[i] = rand() %1000;
    }
selecao(array);

for(int i=0;i<500;i++) {
    printf("%d ",array[i]);
}

return 0;

}

