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

/**
 * A class, containing information about className, its package and a toString() output
 * @author Nina Jeliazkova
 *
 */
public class ToxTreePackageEntry {
	protected String className;
	protected String packageName;
	protected String objectTitle;
 	
	public ToxTreePackageEntry(String className, String packageName, String objectTitle) throws Exception {
		super();
		this.className = className;
		this.packageName = packageName;
		this.objectTitle = objectTitle;
		if (objectTitle.equals("")) {
				Object o = Introspection.loadCreateObject(className);
				this.objectTitle = o.toString();
		}
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getObjectTitle() {
		return objectTitle;
	}

	public void setObjectTitle(String objectTitle) {
		this.objectTitle = objectTitle;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	@Override
	public String toString() {
		return className;
	}
}
