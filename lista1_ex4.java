public class lista1_ex4 {
    public static void main(String[] args) {
        double[] intervalo = {-1.9, -1.7, -1.4, 0, 1, 1.5, 2};
        int n = 6;
        double[] deriv = new double[intervalo.length];
        
        for(int i = 0; i < intervalo.length; i++) {
            double lamb = intervalo[i];         
            deriv[i] = funDer(n, lamb);
            System.out.println("f'[" + intervalo[i] + "] = " + deriv[i]);
        }
    }
    
    /* 
     * Calcula o valor da derivada de f_Tn em lambda
     * Entrada: tamanho da matriz (n) e auto-valor (lambda)
     * Saida: valor da derivada em lambda
    */ 
    public static double funDer(int n, double lamb) {
        double z = lamb/(double)2;
        double result = 0;
        if(lamb == 2) {
            result = (Math.pow(n+1,5)-Math.pow(n+1,3))/(double)3;
        } else if(lamb == -2) {
            result = (Math.pow(-1,n+1)*((Math.pow(n+1,5)-Math.pow(n+1,3))/(double)3));
        } else if(lamb > -2 && lamb < 2) {
            result = ((n+1)*Math.cos((n+1)*Math.acos(z))-z*(Math.sin((n+1)*Math.acos(z))/Math.sin(Math.acos(z))))/((double)2*(Math.pow(z,2)-1));
        }
        return result;
    }
    
}
