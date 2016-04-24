/**
 * Created by Abdellatif on 4/23/2016.
 */
import java.lang.Class;

public class Main {
    public static void main(String[] args) {

    }

    private double inDepth(Class c){
        while (c.getSuperclass().getName() != "Object"){
            c = c.getSuperclass();
            return 1 + inDepth(c);
        }
        return 0;
    }
    private double responsibility(Class c){
        return 0;
    }
    private double instability(Class c){
        return 0;
    }
    private double workload(Class c){
        return 0;
    }

    public void displayMetrics(){

    }

    public void loadPackage(String path){

    }

}
