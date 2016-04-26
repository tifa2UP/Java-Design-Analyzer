/**
 * Created by Abdellatif on 4/23/2016.
 */
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import demo.Demo;
import sun.reflect.Reflection;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.Class;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;


public class Main {

    Class[] loadedPackage = null;
    String[] classNames= null;

    public void loadPackage(String path) throws IOException {
        System.out.println("Gathering class files in " + path);
        FilenameFilter classFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".class");
            }
        };
        File f = new File(path); // the directory, really!
        for (File file : f.listFiles(classFilter) )
            System.out.println(file.getName());
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
        for (Method m: c.getMethods()){
           Type[] mTypes = m.getGenericParameterTypes();
            for (Type t: mTypes){
               for (int i = 0; i < classNames.length; i++){
                   if (t.getTypeName() == classNames[i]){
                       clientsNumber++;
                   }
               }
            }
        }
        return clientsNumber/classNames.length;
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
        Method[] classMethods = c.getMethods();
//        Method[] packageMethods = loadedPackage.getMethods();

        return classMethods.length;
    }

    public void displayMetrics() {

    }


}


