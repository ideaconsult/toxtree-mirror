/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 */
package toxTree.core;

import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import toxTree.exceptions.IntrospectionException;
import toxTree.tree.ProgressStatus;

/**
 * 
 * A singleton class with static method to provide some sort of introspection :)
 * Used in decision tree editor to provide a list of available decision trees,
 * available rules, available categories. <br>
 * This is done by searching for classes implementing
 * {@link toxTree.core.IDecisionMethod}, {@link toxTree.core.IDecisionRule}, and
 * {@link toxTree.core.IDecisionCategory} in all .jar files in a user defined
 * directory.
 * 
 * @author Nina Jeliazkova nina@acad.bg <b>Modified</b> 2005-10-18
 */
public class Introspection {
    public static final String TOXTREE_HOME = "TOXTREE";
    protected static String[] defaultLocation = { "dist", "ext", "toxTree/dist" };

    protected transient static Logger logger = Logger.getLogger(Introspection.class.getName());

    protected static ToxTreeClassLoader loader;

    protected Introspection() {
	super();
	loader = null;
    }

    public static void addDirectory(String dirName) {
	defaultLocation[0] = new String(dirName);
	return;
    }

    public static String[] getDefaultDirectories() {
	return defaultLocation;
    }

    /*
     * public static ArrayList getClassImplementing(String anInterface) {
     * ArrayList result = new ArrayList(); Package[] packages =
     * Package.getPackages();
     * 
     * for (int i =0; i<packages.length;i++) { Package self = packages[i]; if
     * ((self != null) && (self.getName().startsWith("toxTree"))) { } }
     * 
     * return result; }
     */
    /**
     * this is just a test for the concept of introspection
     */
    public static void listBaseTypes(Class cls, String prefix) {
	if (cls == Object.class)
	    return;
	Class[] itfs = cls.getInterfaces();

	for (int n = 0; n < itfs.length; n++) {
	    // System.out.println(prefix + "implements " + itfs[n]);
	    listBaseTypes(itfs[n], prefix + "\t");
	}
	Class base = cls.getSuperclass();
	if (base == null)
	    return;
	// System.out.println(prefix + "extends " + base);
	listBaseTypes(base, prefix + "\t");
    }

    /**
     * Verifies if a class implements an interface
     * 
     * @param className
     *            - the name of the class to be verified
     * @param interfaceName
     *            - the name of the interface to be searched for
     * @return Class
     */
    public static Class implementsInterface(String className, String interfaceName)
	    throws toxTree.exceptions.IntrospectionException {
	try {
	    System.out.print(".");
	    Class clazz = Class.forName(className);
	    int modifier = clazz.getModifiers();
	    if (Modifier.isAbstract(modifier))
		return null;
	    else if (Modifier.isInterface(modifier))
		return null;
	    return implementsInterface(clazz, interfaceName);
	} catch (ClassNotFoundException x) {
	    //
	    if (loader != null) {
		try {
		    // replacement reccomended by
		    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6500212
		    // Class clazz = loader.loadClass(className);
		    Class clazz = Class.forName(className, true, loader);
		    int modifier = clazz.getModifiers();
		    if (Modifier.isAbstract(modifier))
			return null;
		    else if (Modifier.isInterface(modifier))
			return null;
		    return implementsInterface(clazz, interfaceName);
		} catch (ClassNotFoundException xx) {

		    throw new toxTree.exceptions.IntrospectionException(xx);
		}
	    }
	    // x.printStackTrace();
	    return null;
	} catch (Exception x) {
	    throw new toxTree.exceptions.IntrospectionException(x);
	}
    }

    /**
     * Verifies if a class implements an interface
     * 
     * @param clazz
     *            - the class to be verified
     * @param interfaceName
     *            - the name of the interface to be searched for
     * @return Class
     */
    public static Class implementsInterface(Class clazz, String interfaceName) {
	Class[] interfaces = clazz.getInterfaces();

	for (int i = 0; i < interfaces.length; i++)
	    if (interfaces[i].getName().equals(interfaceName))
		return clazz;

	// try base class
	Class base = clazz.getSuperclass();
	if (base == null)
	    return null;
	else
	    return implementsInterface(base, interfaceName);

    }

    public static File[] enumerateJars(File directory) throws toxTree.exceptions.IntrospectionException {
	try {
	    logger.info("Looking for .jar files in\t" + directory.getAbsolutePath());
	    return directory.listFiles(new FileFilter() {
		public boolean accept(File arg0) {
		    // return arg0.getName().toLowerCase().endsWith(".jar");
		    return arg0.getName().toLowerCase().endsWith(".jar")
			    && arg0.getName().toLowerCase().startsWith("tox");
		}
	    });
	} catch (Exception x) {
	    throw new IntrospectionException(x);
	}
    }

    /**
     * Adds URLS of jars in default directories to the class loader. Calls
     * {@link #configureURLLoader(File directory)} for each of
     * {@link #defaultLocation} directories.
     * 
     * @param classLoader
     */
    public static void configureURLLoader(ClassLoader classLoader) {
	setLoader(classLoader);
	for (int i = 0; i < defaultLocation.length; i++) {
	    try {
		configureURLLoader(new File(getToxTreeRoot() + defaultLocation[i]));
	    } catch (toxTree.exceptions.IntrospectionException x) {
		x.printStackTrace();
	    }
	}

    }

    /**
     * Adds URLS of jars in specified directory to the class loader.
     * 
     * @param directory
     * @throws toxTree.exceptions.IntrospectionException
     */
    public static void configureURLLoader(File directory) throws toxTree.exceptions.IntrospectionException {
	if (loader == null)
	    throw new IntrospectionException("Class loader not set!");
	File[] jars = enumerateJars(directory);
	if (jars == null)
	    return;
	for (int i = 0; i < jars.length; i++)
	    try {
		loader.addURL(jars[i].toURL());
	    } catch (MalformedURLException x) {
		logger.log(Level.SEVERE, x.getMessage(), x);
	    }
    }

    /**
     * Adds URLS of jars specified in File[] to the class loader.
     * 
     * @param jars
     * @throws toxTree.exceptions.IntrospectionException
     */
    public static void configureURLLoader(File[] jars) throws toxTree.exceptions.IntrospectionException {
	if (loader == null)
	    throw new IntrospectionException("Class loader not set!");
	for (int i = 0; i < jars.length; i++)
	    try {
		loader.addURL(jars[i].toURL());
	    } catch (MalformedURLException x) {
		logger.log(Level.SEVERE, x.getMessage(), x);
	    }
    }

    /**
     * Finds classes implementing an interface in all .jar files in a user
     * defined directory.
     * 
     * @param directory
     *            - the directory with jar files to be searched for
     * @param interfaceName
     *            - the name of the interface to be searched for
     * @return ToxTreePackageEntries of String , each item is a class name
     */

    public static ToxTreePackageEntries implementInterface(ClassLoader classLoader, File directory, String interfaceName)
	    throws toxTree.exceptions.IntrospectionException {
	setLoader(loader);
	File[] files = enumerateJars(directory);

	ToxTreePackageEntries rules = new ToxTreePackageEntries();
	if (files == null)
	    return rules;
	for (int i = 0; i < files.length; i++)

	    try {
		logger.info("Inspecting jar file\t" + files[i] + "\t" + files[i].toURL());
		JarFile jar = new JarFile(files[i]);
		Enumeration entries = jar.entries();
		if (loader != null)
		    loader.addURL(files[i].toURL());
		logger.info(files[i].getAbsolutePath());
		while (entries.hasMoreElements()) {
		    JarEntry entry = (JarEntry) entries.nextElement();
		    if (!entry.getName().endsWith("class"))
			continue;

		    String name = entry.getName();

		    name = name.replaceAll("/", ".").substring(0, name.indexOf(".class"));

		    Class rule = null;
		    try {
			rule = Introspection.implementsInterface(name, interfaceName);
		    } catch (Exception x) {
			rule = null;
		    }

		    if (rule != null) {
			logger.fine("Class\t" + name + "\timplements\t" + interfaceName + "\tYES");
			try {

			    if (!name.endsWith("BatchDecisionResultsList"))
				rules.add(new ToxTreePackageEntry(name, files[i].getAbsolutePath(), ""));
			} catch (Exception x) {
			    logger.log(Level.SEVERE, x.getMessage(), x);
			}
		    } else
			logger.fine("Class\t" + name + "\timplements\t" + interfaceName + "\tNO");

		}

	    } catch (IOException x) {
		logger.log(Level.SEVERE, x.getMessage(), x);
	    }

	return rules;

    }

    /**
     * Returns a list with available rule names. Uses
     * {@link #getAvailableTypes(ClassLoader, String)}
     * 
     * @return an {@link ArrayList} of class names that implement
     *         {@link IDecisionRule}
     */
    public static ToxTreePackageEntries getAvailableRuleTypes(ClassLoader classLoader) {
	return getAvailableTypes(classLoader, "toxTree.core.IDecisionRule");
    }

    /**
     * Returns a list with available decision trees. Uses
     * {@link #getAvailableTypes(ClassLoader, String)}
     * 
     * @return an {@link ArrayList} of class names that implement
     *         {@link IDecisionMethod}
     */
    public static ToxTreePackageEntries getAvailableTreeTypes(ClassLoader classLoader) {
	return getAvailableTypes(classLoader, "toxTree.core.IDecisionMethod");
    }

    /**
     * This method is the core of {@link toxTree.apps.ToxTreeApp} extension
     * mechanism. Looks for classes that implement interface given by
     * interfacename parameter. Returns a list with class names. Jar files
     * within the following directories are analyzed:
     * <ul>
     * <li>The directory from where the application was started (current
     * directory "."). This is usually the directory where toxTree.jar resides.
     * <li>The directory "dist" below the current directory.
     * <li>The directory "ext" below the current directory.
     * </ul>
     * 
     * @param classLoader
     * @param interfacename
     *            the
     * @return a list with available class names , implementing the interface
     *         {@link ArrayList}
     */
    public static ToxTreePackageEntries getAvailableTypes(ClassLoader classLoader, String interfacename) {
	setLoader(classLoader);
	ToxTreePackageEntries listAll = null;
	
	for (int i = 0; i < defaultLocation.length; i++) {
	    try {
		ToxTreePackageEntries list = implementInterface(classLoader, new File(getToxTreeRoot()
			+ defaultLocation[i]), interfacename);
		if (list.size() != 0) {
		    if (listAll == null)
			listAll = new ToxTreePackageEntries();
		    listAll.addAll(list);
		}
	    } catch (toxTree.exceptions.IntrospectionException x) {
		x.printStackTrace();
	    }
	}
	return listAll;

    }

    /**
     * Returns a list with available categories
     * 
     * @return an {@link ArrayList} of class names that implement
     *         {@link IDecisionCategory}
     */
    public static ToxTreePackageEntries getAvailableCategoryTypes(ClassLoader classLoader) {
	return getAvailableTypes(classLoader, "toxTree.core.IDecisionCategory");
    }

    /**
     * Creates object given the class name
     * 
     * @param className
     * @return Object
     */
    public static Object createObject(String className) {
	Object object = null;
	try {
	    Class classDefinition = Class.forName(className);
	    object = classDefinition.newInstance();
	} catch (InstantiationException x) {
	    logger.log(Level.SEVERE, x.getMessage(), x);
	} catch (IllegalAccessException x) {
	    logger.log(Level.SEVERE, x.getMessage(), x);
	} catch (ClassNotFoundException x) {
	    logger.log(Level.SEVERE, x.getMessage(), x);
	}
	return object;
    }

    public static Object loadCreateObject(String className) throws InstantiationException, IllegalAccessException,
	    ClassNotFoundException {
	if (loader == null)
	    return createObject(className);
	Object object = null;
	Class classDefinition = loader.loadClass(className);
	return classDefinition.newInstance();
    }

    public static ClassLoader getLoader() {
	return loader;
    }

    public static void setLoader(ClassLoader classLoader) {
	// System.out.println("Current loader "+loader);
	// System.out.println("new loader "+classLoader);
	if ((loader == null) || !(loader instanceof ToxTreeClassLoader)) {
	    URL[] defaultURL = new URL[defaultLocation.length];
	    for (int i = 0; i < defaultURL.length; i++) {
		try {

		    // URL u = new URL("jar", "", plugins[i] + "!/");
		    defaultURL[i] = new URL("jar", "", getToxTreeRoot() + defaultLocation[i] + "!/");
		    // defaultURL[i] = new URL(defaultLocation[i]);

		} catch (MalformedURLException x) {
			  logger.severe(x.getMessage());
		}
	    }

	    loader = new ToxTreeClassLoader(defaultURL, classLoader);
	    logger.info("Now loader " + loader);
	}

    }

    /**
     * Loads a decision tree from an InputStream Uses Java serialization
     * mechanism
     * 
     * @param stream
     * @param newTitle
     * @return a decision tree {@link IDecisionMethod}
     */
    public static IDecisionMethod loadRules(InputStream stream, String newTitle) throws IntrospectionException {
	ToxTreeObjectInputStream in = null;
	try {

	    in = new ToxTreeObjectInputStream(stream);

	    IDecisionMethod tree = (IDecisionMethod) in.readObject();
	    tree.setModified(false);
	    if (tree != null) {
		tree.setExplanation(tree.getExplanation() + "\n" + newTitle);
	    }
	    /*
	     * JOptionPane.showMessageDialog(null, tree.toString(),
	     * "Decision tree loaded successfully.",
	     * JOptionPane.INFORMATION_MESSAGE);
	     */
	    return tree;
	} catch (Exception e) {
	    throw new IntrospectionException("Error when loading the decision tree " + newTitle, e);

	} finally {
	    try {
		if (in != null)
		    in.close();
	    } catch (Exception x) {
	    }
	}
    }

    public static IDecisionMethodsList loadForest(InputStream stream) throws IntrospectionException {
	ToxTreeObjectInputStream in = null;
	try {

	    in = new ToxTreeObjectInputStream(stream);

	    IDecisionMethodsList methods = (IDecisionMethodsList) in.readObject();
	    /*
	     * JOptionPane.showMessageDialog(null, methods.size() +
	     * " decision trees", "Decision forest loaded successfully.",
	     * JOptionPane.INFORMATION_MESSAGE);
	     */
	    return methods;
	} catch (Exception e) {
	    throw new IntrospectionException("Error when loading the decision forest", e);
	} finally {
	    try {
		if (in != null)
		    in.close();
	    } catch (Exception x) {
	    }
	}
    }

    public static void saveRulesXML(IDecisionMethod method, OutputStream out) throws IntrospectionException {
	Thread.currentThread().setContextClassLoader(Introspection.getLoader());
	XMLEncoder encoder = new XMLEncoder(out);
	encoder.setPersistenceDelegate(ProgressStatus.STATUS.class, new TypeSafeEnumPersistenceDelegate());

	encoder.writeObject(method);
	encoder.close();

    }

    public static IDecisionMethod loadRulesXML(InputStream stream, String newTitle) throws IntrospectionException {
	try {
	    logger.info("Classloader " + loader);
	    if (loader == null) {
		setLoader(null); // this will trigger creation of the right
				 // classloader
	    }
	    Thread.currentThread().setContextClassLoader(loader);

	    ExceptionListener el = new ExceptionListener() {

		public void exceptionThrown(Exception e) {

		    e.printStackTrace();
		}
	    };
	    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(stream), null, el, loader);
	    IDecisionMethod tree = (IDecisionMethod) decoder.readObject();
	    decoder.close();

	    if (tree != null) {
		tree.setExplanation(tree.getExplanation());
		tree.setModified(false);
	    }
	    return tree;
	} catch (Exception e) {
	    throw new IntrospectionException("Error when loading the decision tree " + newTitle, e);
	}
    }

    public static IDecisionMethodsList loadForestXML(InputStream stream) throws IntrospectionException {
	try {
	    if (loader == null)
		setLoader(null); // this will trigger creation of the right
				 // classloader
	    Thread.currentThread().setContextClassLoader(loader);

	    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(stream), null, null, loader);
	    IDecisionMethodsList methods = (IDecisionMethodsList) decoder.readObject();
	    decoder.close();
	    /*
	     * JOptionPane.showMessageDialog(null, methods.size() +
	     * " decision trees", "Decision forest loaded successfully.",
	     * JOptionPane.INFORMATION_MESSAGE);
	     */
	    return methods;
	} catch (Exception e) {
	    throw new IntrospectionException("Error when loading the decision forest from .fml file", e);
	}
    }

    public static String getToxTreeRoot() {
	String rootFolder = System.getenv(TOXTREE_HOME);
	if (rootFolder == null)
	    return "";
	else
	    return rootFolder + '/';
    }
}

class TypeSafeEnumPersistenceDelegate extends PersistenceDelegate {
    protected boolean mutatesTo(Object oldInstance, Object newInstance) {
	return oldInstance == newInstance;
    }

    protected Expression instantiate(Object oldInstance, Encoder out) {
	Class type = oldInstance.getClass();
	if (!Modifier.isPublic(type.getModifiers()))
	    throw new IllegalArgumentException("Could not instantiate instance of non-public class: " + oldInstance);

	for (Field field : type.getFields()) {
	    int mod = field.getModifiers();
	    if (Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod)
		    && (type == field.getDeclaringClass())) {
		try {
		    if (oldInstance == field.get(null))
			return new Expression(oldInstance, field, "get", new Object[] { null });
		} catch (IllegalAccessException exception) {
		    throw new IllegalArgumentException("Could not get value of the field: " + field, exception);
		}
	    }
	}
	throw new IllegalArgumentException("Could not instantiate value: " + oldInstance);
    }

}