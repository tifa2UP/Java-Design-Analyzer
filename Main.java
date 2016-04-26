/**
 * Created by Abdellatif on 4/23/2016.
 */

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.Class;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;


public class Main {

    private ArrayList<Class> loadedPackage = new ArrayList<>();


    public void loadPackage(String path) throws IOException, ClassNotFoundException {
        System.out.println("Gathering class files in " + path);
        FilenameFilter classFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".class");
            }
        };
        File f = new File(path); // the directory, really!
        String fileName = "";
        for (File file : f.listFiles(classFilter) )
            fileName = file.getName();
        fileName.replace(".class", "");
        fileName = path + "." + fileName;
        loadedPackage.add(Class.forName(fileName));
        System.out.println(fileName);
    }
    public static void main(String[] args) throws IOException {
        FileLoader fl = new FileLoader();
        if (args.length != 1) {
            System.out.println("Usage FileLoader <path>");
        } else {
            fl.loadPackage(args[0]);
        }
        Main main = new Main();
        main.displayMetrics();
    }

    private double inDepth(Class c) {
        while (c.getSuperclass().getName() != "Object") {
            c = c.getSuperclass();
            return 1 + inDepth(c);
        }
        return 1;
    }

    /**
     * Given the definition that

     responsibility(A) = #clients(A)/#p

     where A is any class, clients of A are other classes that use A and #p is the total number of classes

     */
    private double responsibility(Class c) {
        int clientsNumber = 0;
        for (Class d: loadedPackage){
            boolean isResponsible = false;
//            check each method of that class
            for (Method m: d.getMethods()){
                Class[] params = m.getParameterTypes();
                for (Class e: params){
                    if (e.getName() == c.getName()){
                   isResponsible = true;
                        break;
                    }
                }
            }

//            check the instance variables
            Field[] fields = d.getFields();
            for (Field f: fields){
                if (f.getType().getName() == c.getName()){
                   isResponsible = true;
                    break;
                }
            }
            if (isResponsible){
                clientsNumber++;
            }
        }

        return clientsNumber/loadedPackage.size();
    }

    /**
     *Given the definition that
     instability(A) = #providers(A)/#p
     where A is any class and providers of A are the classes that A depend on
     (either through inheritance or composition). and #p is the total number of classes.
     */
    private double instability(Class c) {
        int providersNumber = 0;
        //through inheritance:
        providersNumber += inDepth(c) - 1;
        Field[] fields = c.getFields();
        for (Field f: fields){
                if (loadedPackage.contains(f)){
                    providersNumber++;
                }
        }
        return providersNumber/loadedPackage.size();
    }

    private double workload(Class c) {
        int packageMethodsNumber = 0;
        Method[] classMethods = c.getMethods();
        for (Class d: loadedPackage){
            packageMethodsNumber += d.getMethods().length;
        }
        return classMethods.length/packageMethodsNumber;
    }

    public void displayMetrics() {
        String[][] displayArray = new String[loadedPackage.size() + 1][5];
        displayArray[0][0] = "C";
        displayArray[0][1] = "inDepth(C)";
        displayArray[0][2] = "inStability(C)";
        displayArray[0][3] = "responsibility(C)";
        displayArray[0][4] = "workload(C)";
        int k = 1;
        for (Class c: loadedPackage){
            displayArray[k][0] = c.getName();
            displayArray[k][1] = "" + Math.round(inDepth(c) * 100.00) / 100.00;
            displayArray[k][2] = "" + Math.round(instability(c) * 100.00) / 100.00;
            displayArray[k][3] = "" + Math.round(responsibility(c) * 100.00) / 100.00;
            displayArray[k][4] = "" + Math.round(workload(c) * 100.00) / 100.00;
            k++;
        }

        for (int i = 0; i < displayArray.length; i++){
            for (int j = 0; j < displayArray[i].length; j++){
                System.out.printf("%-20s",displayArray[i][j]);
            }
            System.out.println();
        }
    }


}


