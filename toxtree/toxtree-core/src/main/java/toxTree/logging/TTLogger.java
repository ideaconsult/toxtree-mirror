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
package toxTree.logging;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * Logging facility
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-19
 */
public class TTLogger {
    private boolean debug = false;
    private boolean tostdout = false;

    private org.apache.log4j.Category log4jLogger;
    private TTLogger logger;
    private String classname;

    //private int stackLength;  // NOPMD
    
    /** Default number of StackTraceElements to be printed by debug(Exception). */
    public final int DEFAULT_STACK_LENGTH = 5;

    
    public TTLogger() {
        this(TTLogger.class);
    }

    /**
     * Constructs a LoggingTool which produces log lines indicating them to be
     * for the Class of the <code>Object</code>.
     *
     * @param object Object from which the log messages originate
     */
    public TTLogger(Object object) {
        this(object.getClass());
    }
    public static void configureLog4j(boolean console) {

    	if (console) 
    		configureLog4j("/toxTree/config/log4.console");
    	else
    		configureLog4j("/toxTree/config/log4.properties");
    }
    public static void configureLog4j(String file) {

        try { // NOPMD
            org.apache.log4j.PropertyConfigurator.configure(
            		TTLogger.class.getResource(file));
        } catch (NullPointerException e) { // NOPMD
            System.err.println("Properties file not found: " + e.getMessage());
            System.err.println(e);
            
        } catch (Exception e) {
        	System.err.println("Unknown error occured: " + e.getMessage());
        	System.err.println(e);
        }
    }


    public TTLogger(Class classInst) {
        this.logger = this;
        //stackLength = DEFAULT_STACK_LENGTH;
        this.classname = classInst.getName();
        try {
            /* Next line is required to not have the compiler trip over the
             * the catch clause later, which in turn is needed on runtime when
             * security is checked, which is with the PluginManager, as it
             * uses the java.net.URLClassLoader. */
            if (false) throw new ClassNotFoundException(); // NOPMD
            
            log4jLogger = (org.apache.log4j.Category)org.apache.log4j.Category
                                                     .getInstance( classname );
        } catch (ClassNotFoundException e) {
            tostdout = true;
            logger.debug("Log4J class not found!");
        } catch (NoClassDefFoundError e) {
            tostdout = true;
            logger.debug("Log4J class not found!");
        } catch (NullPointerException e) { // NOPMD
            tostdout = true;
            logger.debug("Properties file not found!");
        } catch (Exception e) {
            tostdout = true;
            logger.fine("Unknown error occured: ", e.getMessage());
        }
        /****************************************************************
         * but some JVMs (i.e. MSFT) won't pass the SecurityException to
         * this exception handler. So we are going to check the JVM
         * version first
         ****************************************************************/
        debug = false;
        String strJvmVersion = System.getProperty("java.version");
        if (strJvmVersion.compareTo("1.2") >= 0) {
          // Use a try {} to catch SecurityExceptions when used in applets
          try {
            // by default debugging is set off, but it can be turned on
            // with starting java like "java -DtoxTree.debugging=true"
            if (System.getProperty("toxTree.debugging", "false").equals("true")) {
              debug = true;
            }
            if (System.getProperty("toxTree.debug.stdout", "false").equals("true")) {
              tostdout = true;
            }
          } catch (Exception e) {
            logger.debug("guessed what happened: security exception thrown by applet runner");
            logger.debug("  therefore, do not debug");
          }
        }
    }
    
    /**
     * Forces the <code>LoggingTool</code> to configurate the Log4J toolkit.
     * Normally this should be done by the application that uses the CDK library,
     * but is available for convenience.
     */

    /**
     * Outputs system properties for the operating system and the java
     * version. More specifically: os.name, os.version, os.arch, java.version
     * and java.vendor.
     */
    public void dumpSystemProperties() {
        debug("os.name        : " + System.getProperty("os.name"));
        debug("os.version     : " + System.getProperty("os.version"));
        debug("os.arch        : " + System.getProperty("os.arch"));
        debug("java.version   : " + System.getProperty("java.version"));
        debug("java.vendor    : " + System.getProperty("java.vendor"));
    }

    /**
     * Sets the number of StackTraceElements to be printed in DEBUG mode when
     * calling <code>debug(Throwable)</code>.
     * The default value is DEFAULT_STACK_LENGTH.
     *
     * @param length the new stack length
     *
     * @see #DEFAULT_STACK_LENGTH
     */
    public void setStackLength(int length) {
        //this.stackLength = length;
    }
    
    /**
     * Outputs the system property for java.class.path.
     */
    public void dumpClasspath() {
        debug("java.class.path: " + System.getProperty("java.class.path"));
    }

    /**
     * Shows DEBUG output for the Object. If the object is an instanceof
     * Throwable it will output the trace. Otherwise it will use the
     * toString() method.
     *
     * @param object Object to apply toString() too and output
     */
    public void debug(Object object) {
        if (debug) {
            if (object instanceof Throwable) {
                debugThrowable((Throwable)object);
            } else {
                debugString("" + object.toString());
            }
        }
    }
    
    private void debugString(String string) {
        if (tostdout) {
            toSTDOUT("DEBUG", string);
        } else {
            log4jLogger.debug(string);
        }
    }
    
    /**
     * Shows DEBUG output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object  Object to apply toString() too and output
     * @param object2 Object to apply toString() too and output
     */
    public void fine(Object object, Object object2) {
        if (debug) {
            debugString("" + object + object2);
        }
    }
    
    /**
     * Shows DEBUG output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number int to concatenate to object
     */
    public void debug(Object object, int number) {
        if (debug) {
            debugString("" + object + number);
        }
    }
    
    /**
     * Shows DEBUG output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number int to concatenate to object
     */
    public void debug(Object object, double number) {
        if (debug) {
            debugString("" + object + number);
        }
    }
    
    /**
     * Shows DEBUG output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param bool   boolean to concatenate to object
     */
    public void debug(Object object, boolean bool) {
        if (debug) {
            debugString("" + object + bool);
        }
    }
    
    /**
     * Shows DEBUG output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     */
    public void debug(Object obj, Object obj2, Object obj3) {
        if (debug) {
            debugString("" + obj + obj2 + obj3);
        }
    }
    
    /**
     * Shows DEBUG output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     */
    public void debug(Object obj, Object obj2, Object obj3, Object obj4) {
        if (debug) {
            debugString("" + obj + obj2 + obj3 + obj4);
        }
    }
    
    /**
     * Shows DEBUG output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     * @param obj5 Object to apply toString() too and output
     */
    public void debug(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        if (debug) {
            debugString("" + obj + obj2 + obj3 + obj4 + obj5);
        }
    }
    
    private void debugThrowable(Throwable problem) {
        if (problem != null) {
            if (problem instanceof Error) {
                debug("Error: " + problem.getMessage());
            } else {
                debug("Exception: " + problem.getMessage());
            }
            java.io.StringWriter stackTraceWriter = new java.io.StringWriter();
            problem.printStackTrace(new PrintWriter(stackTraceWriter));
            String trace = stackTraceWriter.toString();
            try {
                BufferedReader reader = new BufferedReader(new StringReader(trace));
                if (reader.ready()) {
                    String traceLine = reader.readLine();
                    while (reader.ready() && traceLine != null) {
                        debug(traceLine);
                        traceLine = reader.readLine();
                    }
                }
            } catch (Exception ioException) {
                error("Serious error in LoggingTool while printing exception stack trace: " + 
                      ioException.getMessage());
                logger.debug(ioException);
            }
        }
    }
    
    /**
     * Shows ERROR output for the Object. It uses the toString() method.
     *
     * @param object Object to apply toString() too and output
     */
    public void error(Object object) {
    	if (object instanceof Throwable)
    		debugThrowable((Throwable) object);
        if (debug) {
            errorString("" + object);
        }
    }

    /**
     * Shows ERROR output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number int to concatenate to object
     */
    public void error(Object object, int number) {
    	if (object instanceof Throwable)
    		debugThrowable((Throwable) object);
        if (debug) {
            errorString("" + object + number);
        }
    }
    
    /**
     * Shows ERROR output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number double to concatenate to object
     */
    public void error(Object object, double number) {
    	if (object instanceof Throwable)
    		debugThrowable((Throwable) object);
        if (debug) {
            errorString("" + object + number);
        }
    }
    
    /**
     * Shows ERROR output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param bool   boolean to concatenate to object
     */
    public void error(Object object, boolean bool) {
    	if (object instanceof Throwable)
    		debugThrowable((Throwable) object);
        if (debug) {
            errorString("" + object + bool);
        }
    }
    
    private void errorString(String string) {

        if (tostdout) {
            toSTDOUT("ERROR", string);
        } else {
            log4jLogger.error(string);
        }
    }
    
    /**
     * Shows ERROR output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object  Object to apply toString() too and output
     * @param object2 Object to apply toString() too and output
     */
    public void error(Object object, Object object2) {
        if (debug) {
            errorString("" + object + object2);
        }
    }
    
    /**
     * Shows ERROR output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     */
    public void error(Object obj, Object obj2, Object obj3) {
        if (debug) {
            errorString("" + obj + obj2 + obj3);
        }
    }
    
    /**
     * Shows ERROR output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     */
    public void error(Object obj, Object obj2, Object obj3, Object obj4) {
        if (debug) {
            errorString("" + obj + obj2 + obj3 + obj4);
        }
    }
    
    /**
     * Shows ERROR output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     * @param obj5 Object to apply toString() too and output
     */
    public void error(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        if (debug) {
            errorString("" + obj + obj2 + obj3 + obj4 + obj5);
        }
    }
    
    /**
     * Shows FATAL output for the Object. It uses the toString() method.
     *
     * @param object Object to apply toString() too and output
     */
    public void fatal(Object object) {
        if (debug) {
            if (tostdout) {
                toSTDOUT("FATAL", object.toString());
            } else {
                log4jLogger.fatal("" + object.toString());
            }
        }
    }

    /**
     * Shows INFO output for the Object. It uses the toString() method.
     *
     * @param object Object to apply toString() too and output
     */
    public void info(Object object) {
        if (debug) {
            infoString("" + object);
        }
    }

    /**
     * Shows INFO output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number int to concatenate to object
     */
    public void info(Object object, int number) {
        if (debug) {
            infoString("" + object + number);
        }
    }
    
    /**
     * Shows INFO output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number double to concatenate to object
     */
    public void info(Object object, double number) {
        if (debug) {
            infoString("" + object + number);
        }
    }
    
    /**
     * Shows INFO output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param bool   boolean to concatenate to object
     */
    public void info(Object object, boolean bool) {
        if (debug) {
            infoString("" + object + bool);
        }
    }
    
    private void infoString(String string) {
        if (tostdout) {
            toSTDOUT("INFO", string);
        } else {
            log4jLogger.info(string);
        }
    }
    
    /**
     * Shows INFO output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object  Object to apply toString() too and output
     * @param object2 Object to apply toString() too and output
     */
    public void info(Object object, Object object2) {
        if (debug) {
            infoString("" + object + object2);
        }
    }
    
    /**
     * Shows INFO output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     */
    public void info(Object obj, Object obj2, Object obj3) {
        if (debug) {
            infoString("" + obj + obj2 + obj3);
        }
    }
    
    /**
     * Shows INFO output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     */
    public void info(Object obj, Object obj2, Object obj3, Object obj4) {
        if (debug) {
            infoString("" + obj + obj2 + obj3 + obj4);
        }
    }
    
    /**
     * Shows INFO output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     * @param obj5 Object to apply toString() too and output
     */
    public void info(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        if (debug) {
            infoString("" + obj + obj2 + obj3 + obj4 + obj5);
        }
    }

    /**
     * Shows WARN output for the Object. It uses the toString() method.
     *
     * @param object Object to apply toString() too and output
     */
    public void warning(Object object) {
        if (debug) {
            warnString("" + object);
        }
    }
    
    private void warnString(String string) {
        if (tostdout) {
            toSTDOUT("WARN", string);
        } else {
            log4jLogger.warn(string);
        }
    }
    
    /**
     * Shows WARN output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number int to concatenate to object
     */
    public void warn(Object object, int number) {
        if (debug) {
            warnString("" + object + number);
        }
    }
    
    /**
     * Shows WARN output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param bool   boolean to concatenate to object
     */
    public void warn(Object object, boolean bool) {
        if (debug) {
            warnString("" + object + bool);
        }
    }
    
    /**
     * Shows WARN output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object Object to apply toString() too and output
     * @param number double to concatenate to object
     */
    public void warn(Object object, double number) {
        if (debug) {
            warnString("" + object + number);
        }
    }
    
    /**
     * Shows WARN output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param object  Object to apply toString() too and output
     * @param object2 Object to apply toString() too and output
     */
    public void warn(Object object, Object object2) {
        if (debug) {
            warnString("" + object + object2);
        }
    }
    
    /**
     * Shows WARN output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     */
    public void warn(Object obj, Object obj2, Object obj3) {
        if (debug) {
            warnString("" + obj + obj2 + obj3);
        }
    }
    
    /**
     * Shows WARN output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     */
    public void warn(Object obj, Object obj2, Object obj3, Object obj4) {
        if (debug) {
            warnString("" + obj + obj2 + obj3 + obj4);
        }
    }
    
    /**
     * Shows WARN output for the given Object's. It uses the
     * toString() method to concatenate the objects.
     *
     * @param obj  Object to apply toString() too and output
     * @param obj2 Object to apply toString() too and output
     * @param obj3 Object to apply toString() too and output
     * @param obj4 Object to apply toString() too and output
     * @param obj5 Object to apply toString() too and output
     */
    public void warn(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        if (debug) {
            warnString("" + obj + obj2 + obj3 + obj4 + obj5);
        }
    }

    /**
     * Use this method for computational demanding debug info.
     * For example:
     * <pre>
     * if (logger.isDebugEnabled()) {
     *   logger.info("The 1056389822th prime that is used is: ",
     *                calculatePrime(1056389822));
     * }
     * </pre>
     *
     * @return true, if debug is enabled
     */
    public boolean isDebugEnabled() {
        return debug;
    }
    
    private void toSTDOUT(String level, String message) {
        System.out.print(classname);
        System.out.print(" ");
        System.out.print(level);
        System.out.print(": ");
        System.out.println(message);
    }

    
}
