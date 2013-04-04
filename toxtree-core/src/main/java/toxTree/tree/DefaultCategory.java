/*
Copyright Ideaconsult Ltd. (C) 2005-2013 

Contact: www.ideaconsult.net

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

import java.util.Observable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionCategoryEditor;
import toxTree.core.XMLSerializable;
import toxTree.exceptions.XMLDecisionMethodException;
import toxTree.ui.EditorFactory;

/**
 * The default class implementing {@link toxTree.core.IDecisionCategory} interface
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class DefaultCategory extends Observable implements IDecisionCategory , XMLSerializable {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8274171691778536402L;
    protected String name = null;
	protected int id = -1;
	protected String explanation = "";
	protected String threshold ="";
	protected boolean selected = false;
	/**
	 * Constructor
	 * 
	 */
	public DefaultCategory() {
		this("Default",-1);
	}
	/**
	 * 
	 * Constructor
	 * @param name
	 * @param id
	 */
	public DefaultCategory(String name, int id) {
		super();
		setName(name);
		setID(id);
	}
	
	/**
	 * @see toxTree.core.IDecisionCategory#getID()
	 */
	public int getID() {
		return id;
	}

	/**
	 * @see toxTree.core.IDecisionCategory#setID(int)
	 */
	public void setID(int id) {
		this.id = id;
		setChanged();
		notifyObservers();
	}

	/**
	 * @see toxTree.core.IDecisionCategory#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see toxTree.core.IDecisionCategory#setName(java.lang.String)
	 */
	public void setName(String name) {
		if (name != null) 		this.name = name; else this.name = "Default";
		setChanged();
		notifyObservers();
	}
	@Override
	public String toString() {
	    return getName();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (obj instanceof IDecisionCategory) {
			IDecisionCategory c =(IDecisionCategory) obj;
			return id == c.getID();
	    } else  if (obj instanceof Number) {
	    	return id == ((Number) obj).intValue();
	    } else return false;
	}
	
	/* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(IDecisionCategory o) {
    	if (o instanceof IDecisionCategory) {
	        IDecisionCategory c = (IDecisionCategory) o;
	        return getID()-c.getID();
    	} else if (o instanceof Number) {
    		return getID()- ((Number) o).intValue();
    	} else return -1;
    }
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		if (explanation == null) this.explanation = "";
		else this.explanation = explanation;
		setChanged();
		notifyObservers();
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		if (threshold != null) 	this.threshold = threshold;
		else threshold = "";
		setChanged();
		notifyObservers();
	}
	public IDecisionCategoryEditor getEditor() {
		return EditorFactory.getInstance().createCategoryEditor(this);
	}
	public void fromXML(Element xml)  throws XMLDecisionMethodException{
		setName(xml.getAttribute(xmltag_NAME));
		try {
			setID(Integer.parseInt(xml.getAttribute(xmltag_ID)));
		} catch (Exception x) {
			throw new XMLDecisionMethodException(x);
		}
		setExplanation(xml.getAttribute(xmltag_EXPLANATION));
		setThreshold(xml.getAttribute(xmltag_CATEGORYTHRESHOLD));
		
	}
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException {
		return toXML(document);
	}		
	public Element toXML(Document document)  throws XMLDecisionMethodException {
		Element e = document.createElement(XMLSerializable.xmltag_CATEGORY);
		e.setAttribute(xmltag_CLASS, getClass().getName());
		e.setAttribute(xmltag_NAME,getName());
		e.setAttribute(xmltag_ID,Integer.toString(getID()));
		e.setAttribute(xmltag_EXPLANATION,getExplanation());
		e.setAttribute(xmltag_CATEGORYTHRESHOLD,getThreshold());		
		return e;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
	@Override
	public void setCategoryType(CategoryType categoryType) {
	}
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.InconclusiveCategory;
	}
}
