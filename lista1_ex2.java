public class lista1_ex2 {
    public static void main(String[] args) {
        int n = 6;
        double[][] interv = funInterv(n);
        for(int j = 0; j < n; j++) {
            System.out.println("[" + interv[j][0] + ", " + interv[j][1] + "]");
        }
    }    

    /* 
     * Calcula o intervalo de troca de sinais da funcao f_Tn
     * Entrada: tamanho da matriz (n)
     * Saida: matriz n linhas x 2 colunas contendo o intervalo 
     * onde ha troca de sinais
    */ 
    public static double[][] funInterv(int n) {
        double lamb;
        double [] x = new double[3*n+1];
        double [] f = new double[3*n+1];

        // calculando f_Tn(lambda) nos 3*n+1 pontos igualmente espacados
        for(int k = 0; k < (3*n+1); k++) {
            lamb = -2 + k*(4/(double)(3*n));
            x[k] = lamb;
            f[k] = funDet(n, lamb);
            System.out.println("[" + (k+1) + "] f_T" + n + "(" + lamb + ") = " + f[k]);
        }

        // verificando quando ocorre a troca de sinais
        System.out.println("\nIntervalos de troca de sinal");
        double[][] intvTroca = new double[n][2];
        int count = 0;
        for(int i = 0; i < 3*n ; i++) {
            if(f[i]*f[i+1] < 0) {
                intvTroca[count][0] = x[i];
                intvTroca[count][1] = x[i+1];
                count++;
            }
        }
        return intvTroca;
    }

    /* 
     * Calcula o valor da funcao f_Tn em lambda
     * Entrada: tamanho da matriz (n) e auto-valor (lambda)
     * Saida: determinante da matriz
    */ 
    public static double funDet(int n, double lamb) {
        double[][] v = new double[n][n];
        // criando matriz nxn e preenchendo com zeros 
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                v[i][j] = 0;
            }
        }
        // criando a matriz V de acordo com o algoritmo 3.1 da lista
        for(int i = 0; i < n - 1; i++) {
            v[i][i+1] = -1;
            v[i+1][i] = -1;    
        }
        // acessando a diagonal e preenchendo com lambda
        for(int i = 0; i < n; i++) {
            v[i][i] = lamb;
        }
        // sinal para calculo do determinante
        int sgn = 1;
        // supondo que a matriz NAO eh singular
        boolean matrizSingular = false;
        boolean primeiraTrocaLinha = true;
        // criacao da matriz v_linha
        for(int j = 0; j < n - 1; j++) {
            if(matrizSingular == false) {
                // verificando se a diagonal eh nula
                if(v[j][j] == 0) {
                    matrizSingular = true;
                    // varrendo a coluna da diagonal ate o final
                    for(int k = j; k < n; k++) {
                        if(v[k][j] != 0) {
                            if(primeiraTrocaLinha == true) {
                                primeiraTrocaLinha = false;
                            }
                            matrizSingular = false;
                            // trocando as linhas j e k
                            double[] aux = v[k];
                            v[k] = v[j];
                            v[j] = aux;
                            sgn = sgn * (int)Math.pow(-1, k-j);
                        }
                    }
                }
            } else {
                imprimeErro();
            }
        
            if(matrizSingular == false) {
                // escalonamento
                for(int i = j + 1; i < n; i++) {
                    double mi = -(v[i][j]/v[j][j]);
                    for(int k = j; k < n; k++) {
                        v[i][k] = v[i][k] + mi*v[j][k];
                    }
                }
            }
        }
        
        // checando se o ultimo elemento da diagonal eh zero
        // necessario pois o processo de troca de linhas vai
        // somente ate n - 1 
        if(matrizSingular == true || v[n-1][n-1] == 0) {
            imprimeErro();
            return 0;
        } else {              
            // calculo do determinante
            double det = sgn;
            for(int i = 0; i < n; i++) {
                det *= v[i][i];
            }
            return det;
        }
    } 
    
    /* 
     * Imprime uma mensagem de erro caso a matriz 
     * seja singular (diagonal com zero)
    */ 
    public static void imprimeErro() {
        System.out.println("ERRO: Matriz Singular");
        System.out.println("---------------------------------");
    }

}
