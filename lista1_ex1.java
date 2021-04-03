public class lista1_ex1 {
    public static void main(String[] args) {
        int n = 7;
        int lamb;
        double[][] v = new double[n][n];

        // faz para lambda igual a 0, 1, 2, 3 e 4
        for(lamb = 0; lamb < 5; lamb++) {
            System.out.println("lambda = " + lamb + "\n");
            // criando matriz nxn e preenchendo com zeros
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    v[i][j] = 0;
                }
            }
    
            // criando a matriz V de acordo com o algoritmo
            //  3.1 da lista
            for(int i = 0; i < n - 1; i++) {
                v[i][i+1] = -1;
                v[i+1][i] = -1;    
            }
            
            // acessando a diagonal e preenchendo com lambda
            for(int i = 0; i < n; i++) {
                v[i][i] = lamb;
            }
            
            // printando a matriz V
            System.out.println("Matriz V");
            imprimeMatriz(v,n);
            
            // eliminacao de Gauss - algoritmo 3.2 da lista
            int sgn = 1;
            
            // supondo que a matriz NAO eh singular
            boolean matrizSingular = false;
            boolean primeiraTrocaLinha = true;
    
            for(int j = 0; j < n - 1; j++) {
    
                if(matrizSingular == false) {
                    // verificando se a diagonal eh nula
                    if(v[j][j] == 0) {
                        matrizSingular = true;
                        // varrendo a coluna da diagonal ate o final
                        for(int k = j; k < n; k++) {
                            if(v[k][j] != 0) {
                                if(primeiraTrocaLinha == true) {
                                    System.out.println("trocaLinhas");
                                    primeiraTrocaLinha = false;
                                }
                                matrizSingular = false;
                                // trocando as linhas j e k
                                double[] aux = v[k];
                                v[k] = v[j];
                                v[j] = aux;
                                
                                sgn = sgn * (int)Math.pow(-1, k-j);
                                System.out.println(j + " " + k);
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
            System.out.println();
            
            // checando se o ultimo elemento da diagonal eh zero
            // necessario pois o processo de troca de linhas vai
            // somente ate n - 1 
            if(matrizSingular == true || v[n-1][n-1] == 0) {
                imprimeErro();
            } else {
                // printando a matriz V_linha
                System.out.println("Matriz V_linha");
                imprimeMatriz(v, n);
                
                // determinante
                double det = sgn;
                for(int i = 0; i < n; i++) {
                    det *= v[i][i];
                }
                System.out.println("det = " + det);
                System.out.println("---------------------------------");
            }
        }
    }
        
    public static void imprimeErro () {
        System.out.println("ERRO: Matriz Singular");
        System.out.println("---------------------------------");
    }

    public static void imprimeMatriz(double[][] v, int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(v[i][j] == -1 ) {
                    System.out.print(v[i][j] + " ");
                } else {
                    System.out.print(" " + v[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
