/**
 * <b>Filename</b> ListFilesDoclet.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-6-24
 * <b>Project</b> ambit
 */
//package ambit.doc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;

/**
 * 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-6-24
 */
public class ListFilesDoclet {
    private Hashtable toxtreePackages;
    protected String outputDir = "";

    public ListFilesDoclet() {
        toxtreePackages = new Hashtable();
    }

    private String toAPIPath(String className) {
        StringBuffer sb = new StringBuffer();
        
        for (int i=0; i<className.length(); i++) {
            if (className.charAt(i) == '.') {
                sb.append('/');
            } else {
                sb.append(className.charAt(i));
            }
        }
        return sb.toString();
    }
    /**
     * 
     * @param className
     * @return third level package
     */
    private String toPackageName(ClassDoc classDoc) {
        String className = classDoc.qualifiedName();
        ClassDoc s = classDoc.superclass();
        if ((s != null) && ((s.name().equals("CoreApp")))) {
            System.out.println(s.name());
            return classDoc.name();
        } else {    
        
	        int p1 = className.indexOf(".",0);
	       // System.out.println(p1);
	        int p2 = className.indexOf(".",p1+1);
	        if (p2>0) {
		        //System.out.println(p2);
		        //if (className.substring(p1+1,p2).equals("stats")) return "stats";
	
		        int p3 = className.indexOf(".",p2+1);
		        //System.out.println(p3);
		        if (p3 > 0) 
			        return className.substring(p1+1,p3);
		        else
			        return className.substring(p1+1,p2);
		} else return className.substring(p1+1,className.length());
        }     
    }
    
    public void process(RootDoc root) throws IOException {
    	String[][] options = root.options();
        for (int i = 0; i < options.length; i++) {
            String[] opt = options[i];
            System.out.println();
		    if (opt[0].equals("-sourcepath")) { 
		    	outputDir = opt[1] + "/";
		    	
		    }	
		 
        }
        System.out.println("Output dir "+outputDir);
        processPackages(root.specifiedPackages());

        // output information in .javafiles and .classes files
        Enumeration keys = toxtreePackages.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            
            // create one file for each package = key
            PrintWriter outJava = new PrintWriter((Writer)new FileWriter(outputDir+ key + ".javafiles"));
            PrintWriter outClass = new PrintWriter((Writer)new FileWriter(outputDir+ key + ".classes"));
            Vector packageClasses = (Vector)toxtreePackages.get(key);
            Enumeration classes = packageClasses.elements();
            while (classes.hasMoreElements()) {
                String packageClass = (String)classes.nextElement();
                outJava.println(toAPIPath(packageClass) + ".java");
                outClass.println(toAPIPath(packageClass) + "*.class");
            }
            outJava.flush(); outJava.close();
            outClass.flush(); outClass.close();
        }
    }

    private void processPackages(PackageDoc[] pkgs) throws IOException {
        for (int i=0; i < pkgs.length; i++) {
            processClasses(pkgs[i].allClasses());
        }
    }

    private void addClassToPackage(String packageClass, String toxTreePackageName) {
        Vector packageClasses = (Vector)toxtreePackages.get(toxTreePackageName);
        if (packageClasses == null) {
            packageClasses = new Vector();
            toxtreePackages.put(toxTreePackageName, packageClasses);
        }
        packageClasses.addElement(packageClass);
    }
    
    private void processClass(ClassDoc classDoc) throws IOException {
        String className = classDoc.qualifiedName();
        // first deal with modules
        //Tag[] tags = classDoc.tags(javaDocModuleTag);
        addClassToPackage(className, toPackageName(classDoc));
    }
    
    private void processClasses(ClassDoc[] classes) throws IOException {
        for (int i=0; i<classes.length; i++) {
            ClassDoc doc = classes[i];
            processClass(doc);
        }
    }

    public static boolean start(RootDoc root) {
        try {
            ListFilesDoclet doclet = new ListFilesDoclet();
            doclet.process(root);
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            return false;
        }
    }

}

