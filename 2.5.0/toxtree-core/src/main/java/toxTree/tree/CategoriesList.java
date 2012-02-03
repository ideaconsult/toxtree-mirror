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

*//**
 * <b>Filename</b> CategoriesList.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-8
 * <b>Project</b> toxTree
 */
package toxTree.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.Introspection;
import toxTree.core.XMLSerializable;
import toxTree.exceptions.XMLDecisionMethodException;

/**
 * A list of {@link toxTree.core.IDecisionCategory}
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-8
 */
public class CategoriesList extends Observable implements IDecisionCategories, Observer,  XMLSerializable /*, Externalizable*/{
    protected ArrayList<IDecisionCategory> storage;
    protected IDecisionCategory selected = null;
    protected boolean multilabel = false;
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1984731709034516593L;
    /**
     * 
     */
    public CategoriesList() {
        super();
        storage = new ArrayList<IDecisionCategory>();
        selected = null;
    }
    public CategoriesList(IDecisionCategory[] classes) {
    	this(classes,false);
    }
    public CategoriesList(IDecisionCategory[] classes,boolean multilabel) {
    	this();
    	setMultilabel(multilabel);
   		for (int i = 0; i < classes.length; i++)
   		   addCategory(classes[i]);
    }
    public CategoriesList(String[] classes) {
    	this(classes,false);
    }
    public CategoriesList(String[] classes,boolean multilabel) {
    	this();
    	setMultilabel(multilabel);
   		for (int i = 0; i < classes.length; i++)
   		try{
   		   addCategory((IDecisionCategory) Introspection.loadCreateObject(classes[i]));
   		} catch (Exception x) { x.printStackTrace();}
    }

    public IDecisionCategory getCategory(IDecisionCategory key) {
        try {
            int index = storage.indexOf(key);
            if (index == -1) return null;
            else return (IDecisionCategory)storage.get(index);
        } catch (Exception x) {
            return null;
        }
    }    
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionCategories#addCategory(toxTree.core.IDecisionCategory)
     */
    public void addCategory(IDecisionCategory category) {
    	if (getCategory(category) != null) return; //no duplicates
    	if (category.getID() == -1) category.setID(storage.size()+1);
    	storage.add(category);
    	if (category instanceof Observable) ((Observable)category).addObserver(this);
    	setChanged();
    	notifyObservers();

    }

    /* (non-Javadoc)
     * @see toxTree.core.IDecisionCategories#size()
     */
    public int size() {
        return storage.size();
    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionCategories#clear()
     */
    public void clear() {
    	for (int i=0; i < storage.size();i++)
    		if (storage.get(i) instanceof Observable) ((Observable)storage.get(i)).deleteObserver(this);
       storage.clear();
       setChanged();
       notifyObservers();       
    }
    /* (non-Javadoc)
     * @see java.util.AbstractList#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object o) {
    	if (o instanceof CategoriesList) {
	        CategoriesList cl = (CategoriesList) o;
	        if (o == null) return false;
	        if (cl.size() != size()) return false;
	        else return storage.containsAll(cl.storage);
    	} else 
    		return false;
        /*
        Set keys = storage.keySet();
        Iterator i = keys.iterator();
	    while (i.hasNext()) {
	        Integer key = (Integer) i.next();
	        if (!cl.getCategory(key).equals(getCategory(key))) return false;
	    } 
	    */
    }
    public IDecisionCategory getSelected() {
        return selected;
    }
    
    public void setSelected(IDecisionCategory category) {
    	if (!multilabel)
	    	for (int i=0; i < storage.size();i++)
	    		storage.get(i).setSelected(false);
        if (category != null) {
            selected = getCategory(category);
            selected.setSelected(true);
        } else selected = null;
		setChanged();
		notifyObservers();    	
    }
    
    public void add(int arg0, IDecisionCategory arg1) {
    	
    	storage.add(arg0,arg1);
    	setChanged();
    	notifyObservers();    	
    }
    public boolean add(IDecisionCategory arg0) {
    		addCategory((IDecisionCategory) arg0);
    		return true;
    
    }
    public boolean addAll(Collection arg0) {
    	boolean b = storage.addAll(arg0);
    	setChanged();
    	notifyObservers();
    	return b;
    }
    public boolean addAll(int arg0, Collection arg1) {
    	boolean b =addAll(arg0,arg1);
    	setChanged();
    	notifyObservers();
    	return b;
    }
    public boolean contains(Object arg0) {
    	return storage.contains(arg0);
    }
    public boolean containsAll(Collection arg0) {
    	return storage.containsAll(arg0);
    }
    public IDecisionCategory get(int arg0) {
    	return storage.get(arg0);
    }
    public IDecisionCategory remove(int arg0) {
    	IDecisionCategory o = storage.remove(arg0);
    	if (o instanceof Observable) ((Observable)o).deleteObserver(this);
    	setChanged();
    	notifyObservers();
    	return o;
    }
    public boolean remove(Object arg0) {
    	
    	boolean o = storage.remove(arg0);
    	if (arg0 instanceof Observable) ((Observable)arg0).deleteObserver(this);
    	setChanged();
    	notifyObservers();
    	return o;
    }
    public boolean removeAll(Collection arg0) {
    	boolean o = storage.removeAll(arg0);
    	setChanged();
    	notifyObservers();
    	return o;
    }
    public boolean retainAll(Collection arg0) {
    	boolean o = storage.retainAll(arg0);
    	setChanged();
    	notifyObservers();
    	return o;
    }

    public Object[] toArray() {
    	return storage.toArray();
    }
    public Iterator<IDecisionCategory> iterator() {
    	return storage.iterator();
    }
    public IDecisionCategory set(int arg0, IDecisionCategory arg1) {
    	IDecisionCategory o= storage.set(arg0,arg1);
    	setChanged();
    	notifyObservers();
    	return o;
    }
    public int indexOf(Object arg0) {
    	return storage.indexOf(arg0);
    }
    public List<IDecisionCategory> subList(int arg0, int arg1) {
    	return storage.subList(arg0,arg1);
    }
    public ListIterator<IDecisionCategory> listIterator() {
    	return storage.listIterator();
    }
    public ListIterator<IDecisionCategory> listIterator(int arg0) {
    	return storage.listIterator(arg0);
    }
    public int lastIndexOf(Object arg0) {
    	return storage.lastIndexOf(arg0);
    }
    public boolean isEmpty() {
    	return storage.isEmpty();
    }
	public void fromXML(Element xml)  throws XMLDecisionMethodException{
		// TODO Auto-generated method stub
		
	}
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException {
		Element m = document.createElement(xmltag_CATEGORIES);
		m.setAttribute(xmltag_CLASS, getClass().getName());
		return m;
	}		
	public Element toXML(Document document)  throws XMLDecisionMethodException {
		Element e = document.createElement(XMLSerializable.xmltag_CATEGORIES);
		for (int i=0; i < size();i++) {
			IDecisionCategory category =  (IDecisionCategory)storage.get(i);
			if (category instanceof XMLSerializable) 
				e.appendChild(((XMLSerializable) category).toXML(document));
			else throw new XMLDecisionMethodException(category.getClass().getName() + " not XML serializable!");
		}
		return e;
	}
	/*
	public void readExternal(ObjectInput arg0) throws IOException, ClassNotFoundException {
		
		
	}
	public void writeExternal(ObjectOutput out) throws IOException {
		try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element e = toXML(doc);
		    doc.appendChild(e);
            Source source = new DOMSource(doc);
            Result result = new StreamResult((ObjectOutputStream)out);
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);			            
	} catch (Exception x) {
		x.printStackTrace();
	}		
	}
	*/	
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof IDecisionCategory) {
			setChanged();
			notifyObservers(arg0);
		}
		
	}
	public ArrayList getStorage() {
		return storage;
	}
	public void setStorage(ArrayList<IDecisionCategory> storage) {
		this.storage = storage;
	}
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		for (int i=0; i < size(); i++) {
			b.append(get(i).toString());
			b.append("\t");
		}	
		return b.toString();
	}
	public boolean isMultilabel() {
		return multilabel;
	}
	public void setMultilabel(boolean multilabel) {
		this.multilabel = multilabel;
	}
	public void selectAll(boolean selected) {
		for (int i=0; i < storage.size();i++)
			storage.get(i).setSelected(selected);
	}
}

