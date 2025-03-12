import javax.swing.*;

public class Main {
    static int N=0;
    static double a=-6;
    static double b=4;
    static double eps=0.2;
    static double l=0.5;
    static int k=0;
    static double y=-6+((3-Math.sqrt(5))/2)*10;
    static double  z=-2-y;

    public static double function(double x){
        return x*x+6*x+13;
    }

    public static double R(int x){
        return Math.pow(0.618,N);
    }

//    public static void interval(){
//        y=(a+b-eps)/2;
//        z=(a+b+eps)/2;
//    }

    public static boolean sravn(){
        N+=2;
        return function(y)<=function(z);

    }

    public static boolean accuracy(){
        return (b-a)<l;
    }

    public static double ans(){
        return (a+b)/2;
    }

    public static void main(String[] args) {
        System.out.println("Начальный интервал: [ "+a+" ; "+b+ " ]" );
        while(!accuracy()){
            if(k!=0){
                System.out.println("Новый интервал: [ "+a+" ; "+b+ " ]" );
            }

            if(sravn()){
                b=z;
                z=y;
                y=a+b-y;
            }else {
                a=y;
                y=z;
                z=a+b-z;
            }
            if(accuracy()){
                break;
            }
            k++;
        }
        System.out.println("x = "+ans());
        System.out.println("f(x)= "+ function(ans()));
        System.out.println("N = "+N);
        System.out.println("R = "+R(N));
        System.out.println("Решение найдено за "+k+" шагов");
        double absoluteError = Math.abs(function(ans()) - function(1));
        System.out.println("Абсолютная погрешность = "+ absoluteError);
        SwingUtilities.invokeLater(() -> new FunctionGraph());
    }
}