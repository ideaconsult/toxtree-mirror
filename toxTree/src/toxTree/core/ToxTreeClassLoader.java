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

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ToxTreeClassLoader extends URLClassLoader {

	public ToxTreeClassLoader(URL[] arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ToxTreeClassLoader(URL[] arg0, ClassLoader arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ToxTreeClassLoader(URL[] arg0, ClassLoader arg1,
			URLStreamHandlerFactory arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void addURL(URL arg0) {
		super.addURL(arg0);
	}
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(getClass().getName());
		b.append('\t');
		
		URL[] url = getURLs();
		for (int i=0; i < url.length;i++) {
			b.append(url[i]);
			b.append('\t');
		}	
		return b.toString();
	}
}
