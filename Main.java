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
import java.util.HashSet;


public class Main {

    Class[] loadedPackage = null;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        System.out.println("test");
        Main main = new Main();
        main.loadPackage("C:\\Users\\Abdellatif\\Desktop\\151\\Java-Design-Analyzer\\demo");
    }

    private double inDepth(Class c) {
        while (c.getSuperclass().getName() != "Object") {
            c = c.getSuperclass();
            return 1 + inDepth(c);
        }
        return 0;
    }

    private double responsibility(Class c) {
        return 0;
    }

    private double instability(Class c) {
        return 0;
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


