#include <stdio.h>
#include <stdlib.h>

int merge(int arr[], int temp[], int left, int mid, int right) {
    int i, j, k;
    int inv_count = 0;

    i = left;    
    j = mid + 1; 
    k = left;    


    while (i <= mid && j <= right) {
        if (arr[i] <= arr[j]) {
            temp[k++] = arr[i++];
        } else {
            temp[k++] = arr[j++];
            inv_count += (mid - i + 1); 
        }
    }

    while (i <= mid) {
        temp[k++] = arr[i++];
    }

    
    while (j <= right) {
        temp[k++] = arr[j++];
    }


    for (i = left; i <= right; i++) {
        arr[i] = temp[i];
    }

    return inv_count;
}

int mergeSort(int arr[], int temp[], int left, int right) {
    int mid, inv_count = 0;
    if (left < right) {
        mid = (left + right) / 2;

        inv_count += mergeSort(arr, temp, left, mid);
        inv_count += mergeSort(arr, temp, mid + 1, right);

        inv_count += merge(arr, temp, left, mid, right);
    }
    return inv_count;
    /*
    Função mergeSort:
    - Complexidade: O(n log n), onde n é o número total de competidores (ou seja, o tamanho do array).
    - O mergeSort é chamado recursivamente dividindo o array em duas metades até chegar em sublistas de tamanho 1.
    - A cada chamada, o número de inversões (ultrapassagens) é contado ao realizar a fusão das sublistas na função merge.
*/
}

int contarUltrapassagens(int largada[], int chegada[], int N) {
    int *posicaoChegada = (int *)malloc((N + 1) * sizeof(int));
    int *largadaConvertida = (int *)malloc(N * sizeof(int));
    int *temp = (int *)malloc(N * sizeof(int));

    for (int i = 0; i < N; i++) {
        posicaoChegada[chegada[i]] = i;
    }

    
    for (int i = 0; i < N; i++) {
        largadaConvertida[i] = posicaoChegada[largada[i]];
    }

    
    int resultado = mergeSort(largadaConvertida, temp, 0, N - 1);

    
    free(posicaoChegada);
    free(largadaConvertida);
    free(temp);

    return resultado;

    /*
    Função contarUltrapassagens:
    - Complexidade: O(n log n), pois a função principal usa o mergeSort para contar as ultrapassagens.
    - Primeiro, cria-se um array posicaoChegada para mapear as posições dos competidores no grid de chegada.
    - Em seguida, converte-se o array de largada para que ele reflita as posições no grid de chegada, e então o mergeSort é usado para contar inversões nesse array convertido.
*/
}

int main() {
    int N;

    while (scanf("%d", &N) != EOF) {
        int largada[N], chegada[N];

        
        for (int i = 0; i < N; i++) {
            scanf("%d", &largada[i]);
        }

        
        for (int i = 0; i < N; i++) {
            scanf("%d", &chegada[i]);
        }

        
        printf("%d\n", contarUltrapassagens(largada, chegada, N));
    }

    return 0;
}
