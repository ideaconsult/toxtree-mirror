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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Loading non-default serialized classes is tricky ... <br>
 * A descendent of {@link java.io.ObjectInputStream} that uses classloader from {@link toxTree.core.Introspection} 
 * instead of default classLoader. 
 * @author Nina Jeliazkova
 *
 */
public class ToxTreeObjectInputStream extends ObjectInputStream {

	public ToxTreeObjectInputStream() throws IOException, SecurityException {
		super();
		// TODO Auto-generated constructor stub
	}

	public ToxTreeObjectInputStream(InputStream arg0) throws IOException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	/**
	 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6500212
	 * Thus, we highly recommend replacement of this code:
	 * <pre>
     * myClassLoader.loadClass(className);
	 * </pre>
	 * With this code:
	 * <pre>
  	 * Class.forName(className,false,myClassLoader);
  	 * </pre>
	 */
			
    @Override
	protected Class resolveClass(ObjectStreamClass desc) throws IOException,
    	      ClassNotFoundException
    	    {
    	      String name = desc.getName();
    	      //Class c = Introspection.loader.loadClass(name);
    	      Class c = Class.forName(name, true, Introspection.loader);
    	      return c;
    	    }


}
