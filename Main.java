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
        return providersNumber;
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

    }


}


