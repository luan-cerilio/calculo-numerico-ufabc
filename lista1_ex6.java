public class lista1_ex6 {
    public static void main(String[] args) {
        int n = 11;
        double[][] interv = funInterv(n);
        double prec = 1E-12;
        int nmax = 100000;
        System.out.println("f_T" + n);
        double[] exata = funExat(n);
        double[] aprox = funNewton(n,nmax,interv,prec);
        System.out.println("Erro entre as raizes: ");
        for(int k = 0; k < n; k++) {
            double diff = exata[k] - aprox[k];
            System.out.println("[" + (k+1) + "] " + diff);
        }
    }

    /* Calcula as raizes dxatas de f_Tn para n = 11
     * Entrada: tamanho da matriz (n) 
     * Saida: vetor contendo as raizes exatas
    */
    public static double[] funExat(int n) {
        double[] raizExata = new double[n];
        System.out.println("Raizes exatas: ");
        for(int k = 0; k < n; k++) {
            raizExata[k] = -2*Math.cos((k+1)*Math.PI/12);
            System.out.println("[" + (k+1) + "] " + raizExata[k]);
        }
        return raizExata;
    }

    /* Calcula as raizes de f_Tn por aproximacao de Newton
     * Entrada: tamanho da matriz (n), num. max de iteracoes (nmax), 
     * o intervalo de busca (interv) e a precisao (prec)
     * Saida: vetor contendo as raizes aproximadas
    */
    public static double[] funNewton(int n, int nmax, double[][] interv, double prec) {
        double[] aproxNewt = new double[n];
        System.out.println("Raizes aproximadas: ");
        for(int i = 0; i < n; i++) {

            int count = 0;
            // aprox inicial
            double alfa = (interv[i][0]+interv[i][1])/(double)2;
            // intervalo de busca
            double a = interv[i][0];
            double b = interv[i][1];

            while(funDet(n,alfa-prec)*funDet(n,alfa+prec) > 0 && count < nmax) {
                count++;
                if(alfa >= a && alfa <= b) {
                    alfa = alfa - (funDet(n, alfa)/funDer(n, alfa));
                }
            }
            
            aproxNewt[i] = alfa;
            System.out.println("[" + i + "] " + aproxNewt[i]);

        }

        return aproxNewt;
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
        }

        // verificando quando ocorre a troca de sinais
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
            for(int i = 0; i < n; i++) det *= v[i][i];
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

    /* 
     * Calcula o valor da derivada de f_Tn em lambda
     * Entrada: tamanho da matriz (n) e auto-valor (lambda)
     * Saida: valor da derivada em lambda
    */ 
    public static double funDer(int n, double lamb) {
        double z = lamb/(double)2;
        double derivada = 0;
        if(lamb == 2) {
            derivada = (Math.pow(n+1,5)-Math.pow(n+1,3))/(double)3;
        } else if(lamb == -2) {
            derivada = (Math.pow(-1,n+1)*((Math.pow(n+1,5)-Math.pow(n+1,3))/(double)3));
        } else if(lamb > -2 && lamb < 2) {
            derivada = ((n+1)*Math.cos((n+1)*Math.acos(z))-z*(Math.sin((n+1)*Math.acos(z))/Math.sin(Math.acos(z))))/((double)2*(Math.pow(z,2)-1));
        }
        return derivada;
    }
    
}