import javax.swing.*;

public class Main {
    static double a=-6;
    static double b=4;
    static double eps=0.2;
    static double l=0.5;
    static int k=0;
    static double y;
    static double  z;

    public static double function(double x){
        return x*x+6*x+13;
    }

    public static void intervalxz(){
        y=(a+b-eps)/2;
        z=(a+b+eps)/2;
    }

    public static boolean equalsfunc(){
        return function(y)<=function(z);
    }

    public static boolean accuracy(){
        return (b-a)<l;
    }

    public static double otvet(){
        return (a+b)/2;
    }

    public static void main(String[] args) {
        System.out.println("Начальный интервал: [ "+a+" ; "+b+ " ]" );
        intervalxz();
        while(!accuracy()){
            if(k!=0){
                intervalxz();
                System.out.println("Новый интервал: [ "+a+" ; "+b+ " ]" );
            }

            if(equalsfunc()){
                b=z;
            }else {
                a=y;
            }
            if(accuracy()){
                break;
            }
            k++;
        }
        System.out.println("x = "+otvet());
        System.out.println("f(x^*)= "+ function(otvet()));
        System.out.println("Ответ найден за "+k+" итерации(й)");
        double absoluteError = Math.abs(function(otvet()) - function(1));
        System.out.println("Абсолютная погрешность = "+ absoluteError);
        SwingUtilities.invokeLater(() -> new FunctionGraph());
    }
}