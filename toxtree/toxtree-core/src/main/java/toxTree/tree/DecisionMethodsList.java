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
package toxTree.tree;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodPrority;
import toxTree.core.IDecisionMethodsList;
import toxTree.core.Introspection;
import toxTree.core.XMLSerializable;
import toxTree.exceptions.XMLDecisionMethodException;

/**
 * 
 * A list of {@link toxTree.core.IDecisionMethod}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-18
 */
public class DecisionMethodsList extends Observable implements	IDecisionMethodsList, XMLSerializable {

	protected transient static Logger logger = Logger.getLogger(DecisionMethodsList.class.getName());

	protected ArrayList list;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3252685260154432751L;

	public DecisionMethodsList() {
		super();
		list = new ArrayList();
	}

	public void addDecisionMethod(IDecisionMethod method) {
		list.add(method);
		setChanged();
		notifyObservers();
		
	}
	
	public boolean add(Object arg0) {
		if (arg0 instanceof IDecisionMethod) {
			addDecisionMethod((IDecisionMethod) arg0);
			return true;
		} else return false;
	}
	
	public IDecisionMethod getMethod(int index) {
		return (IDecisionMethod) list.get(index);
	}

	public void setMethods(String[] methods) {
		// TODO Auto-generated method stub

	}

	public int size() {
		return list.size();
	}

	public void clear() {
		list.clear();
		setChanged();
		notifyObservers();

	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public Object get(int arg0) {
		return list.get(arg0);
	}

	public Object remove(int arg0) {
		Object o = list.remove(arg0);
		setChanged();
		notifyObservers();
		return o;
	}

	public void add(int arg0, Object arg1) {
		list.add(arg0,arg1);
		setChanged();
		notifyObservers();		
	}

	public int indexOf(Object arg0) {
		return list.indexOf(arg0);
	}

	public int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}


	public boolean contains(Object arg0) {
		return list.contains(arg0);
	}

	public boolean remove(Object arg0) {
		if (list.remove(arg0)) {
			setChanged();
			notifyObservers();
			return true;
		} return false;
	}

	public boolean addAll(int arg0, Collection arg1) {
		if (list.addAll(arg0,arg1)) {
			setChanged();
			notifyObservers();
			return true;
		} else return false;
	}

	public boolean addAll(Collection arg0) {
		if (list.addAll(arg0)) {
			setChanged();
			notifyObservers();
			return true;
		} else return false;
	}

	public boolean containsAll(Collection arg0) {
		
		return containsAll(arg0);
	}

	public boolean removeAll(Collection arg0) {
		if (list.removeAll(arg0)) {
			setChanged();
			notifyObservers();
			return true;
		} else return false;
	}

	public boolean retainAll(Collection arg0) {
		if (list.retainAll(arg0)) {
			setChanged();
			notifyObservers();
			return true;			
		} else return false;
	}

	public Iterator iterator() {
		return list.iterator();
	}

	public List subList(int arg0, int arg1) {
		return list.subList(arg0,arg1);
	}

	public ListIterator listIterator() {
		return list.listIterator();
	}

	public ListIterator listIterator(int arg0) {
		return list.listIterator(arg0);
	}

	public Object set(int arg0, Object arg1) {
		Object o = list.set(arg0,arg1);
		setChanged();
		notifyObservers();
		return o;
	}

	public Object[] toArray(Object[] arg0) {
		return list.toArray(arg0);
	}

	public void loadAllFromPlugins() {
		List packageEntries = Introspection.getAvailableTreeTypes(getClass().getClassLoader());
		if (packageEntries == null) return;
		Object o;
		for (int i=0;i<packageEntries.size();i++) {
			try {
				o = Introspection.loadCreateObject(packageEntries.get(i).toString());
				if (o instanceof IDecisionMethod) {
					if (((IDecisionMethod) o).getRules().size() > 0) {
						((IDecisionMethod)o).setEditable(false);
						((IDecisionMethod)o).setModified(false);
						addDecisionMethod((IDecisionMethod)o);
					}
				}	
			} catch (Exception x) {
				x.printStackTrace();
			}
		}
		try {
		    FileFilter fileFilter = new FileFilter() {
		        public boolean accept(File file) {
		            return !file.isDirectory() &&
		            	file.getName().endsWith(".tml");	
		        }
		    };		
			File dir = new File(String.format("%s/ext",Introspection.getToxTreeRoot()));
			File[] trees = dir.listFiles(fileFilter);
			for (File tree : trees) {
				InputStream in = null;
				try {
					in =  new FileInputStream(tree);
					Introspection.loadRulesXML(in, tree.getAbsolutePath());
				} catch (Exception x) {
					logger.info(x.getMessage());
				} finally {
					try { in.close();} catch (Exception x) {}
				}
			}
		} catch (Exception x) {
			logger.info(x.getMessage());
		}
        Collections.sort(list,new PriorityComparator());
	}
	public void loadFromPlugins(String className) {
		Introspection.setLoader(getClass().getClassLoader());
			try {
				Object o = Introspection.loadCreateObject(className);

				if (o instanceof IDecisionMethod) {
					if (((IDecisionMethod) o).getRules().size() > 0) {
						((IDecisionMethod)o).setEditable(false);
						((IDecisionMethod)o).setModified(false);
						addDecisionMethod((IDecisionMethod)o);
					}
				}	
			} catch (Exception x) {
				x.printStackTrace();
			}
			Collections.sort(list,new PriorityComparator());
	}
	public void fromXML(Element xml)  throws XMLDecisionMethodException{
		// TODO Auto-generated method stub
		
	}
	public Element toXML(Document document)  throws XMLDecisionMethodException {
		Element e = document.createElement(xmltag_METHODS);
		for (int i=0; i < size(); i++) {
			IDecisionMethod method = getMethod(i);
			if (method instanceof XMLSerializable) 
			try {
				e.appendChild(((XMLSerializable) method).toXML(document));
			} catch (XMLDecisionMethodException x) {
				x.printStackTrace();
				e.appendChild(((XMLSerializable) method).toShallowXML(document));
			}
		}
		return e;
	}
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException {
		Element m = document.createElement(xmltag_METHODS);
		m.setAttribute(xmltag_CLASS, getClass().getName());
		return m;
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}	
}

class PriorityComparator implements Comparator<IDecisionMethodPrority> {
    public int compare(IDecisionMethodPrority o1, IDecisionMethodPrority o2) {
        return o1.getPriority()-o2.getPriority();
    }
}
