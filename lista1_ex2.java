public class lista1_ex2 {
    public static void main(String[] args) {
        int n = 11;
        double lamb;
        double[][] v = new double[n][n];
        double [] x = new double[3*n+1];
        double [] f = new double[3*n+1];

        for(int m = 0; m < (3*n+1); m++) {
            lamb = -2 + m*(4/(double)(3*n));
            x[m] = lamb;
            System.out.print("[" + (m+1) + "] lambda = " + lamb + "; ");
            
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
            } else {              
                // calculo do determinante
                double det = sgn;
                for(int i = 0; i < n; i++) {
                    det *= v[i][i];
                }
                f[m] = det;
                System.out.println("f = " + det + ";");
            }
        }

        // verificando quando ocorre a troca de sinais
        System.out.println("\nIntervalos de troca de sinal");
        for(int i = 0; i < 3*n ; i++) {
            if(f[i]*f[i+1] < 0) {
                System.out.println("[" + x[i] + ", " + x[i+1] + "]");
            }
        }
    }
        
    public static void imprimeErro () {
        System.out.println("ERRO: Matriz Singular");
        System.out.println("---------------------------------");
    }
}