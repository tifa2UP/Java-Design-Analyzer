/**
 * Created by Abdellatif on 4/23/2016.
 */
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import demo.Demo;
import sun.reflect.Reflection;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.Class;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;


public class Main {

    Class[] loadedPackage = null;
    String[] classNames= null;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        System.out.println("test");
        Main main = new Main();
        main.loadPackage("Demo");
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

    public void loadPackage(String path) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        HashSet packageSet = new HashSet();
        File[] packageFiles = finder(path);
        Class[] packageClasses = new Class[packageFiles.length];
        for (int i = 0; i < packageFiles.length; i++){
            File f = packageFiles[i];
            String fileName = f.getName().substring(0, f.getName().length() - 6);
            packageClasses[i] = Class.forName(fileName);
        }
        loadedPackage = packageClasses;
    }

    private File[] finder(String dirName) {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".class");
            }
        });

    }
}


