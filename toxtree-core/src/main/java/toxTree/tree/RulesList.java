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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.core.XMLSerializable;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.XMLDecisionMethodException;

/**
 * List of {@link toxTree.core.IDecisionRule}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-2
 */
public class RulesList extends Observable implements IDecisionRuleList, XMLSerializable{
	protected ArrayList<IDecisionRule> list;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -15443828098893778L;
	/**
	 * 
	 */
	public RulesList() {
		super();
		list = new ArrayList<IDecisionRule>();
	}
	/**
	 * Creates RulesList given the names of {@link IDecisionRule} classes
	 * Example :
	 * RulesList(new String[] {
	 * 			"toxTree.tree.rules.RuleSubstructure",
     *           "toxTree.tree.rules.RuleAromatic"});
     * Rules are created from corresponding class names    
	 * @param customRules
	 */
	public RulesList(String[] customRules) throws DecisionMethodException {
		this();
		setRules(customRules);
	}
	/**
	 * Sets RulesList given the names of {@link IDecisionRule} classes
	 * Example :
	 * setRules(new String[] {
	 * 			"toxTree.tree.rules.RuleSubstructure",
     *           "toxTree.tree.rules.RuleAromatic"});
     * Rules are created from corresponding class names    
	 * @param customRules
	 */
	public void setRules(String[] customRules) throws DecisionMethodException  {
		for (int i=0; i < customRules.length; i++) { 
			IDecisionRule rule = (IDecisionRule) AbstractRule.createRule(customRules[i]);
			add(rule);
	    }	
		setChanged();
		notifyObservers();
	}
	/**
	 * adds a rule {@link IDecisionRule}
	 */
	public void addRule(IDecisionRule rule) {
		list.add(rule);
		rule.setNum(size());
		setChanged();
		notifyObservers();
	}
	/**
	 * returns a rule {@link IDecisionRule}
	 */
	public IDecisionRule getRule(int index) {
		return (IDecisionRule) list.get(index);
	}
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		for (int i=0; i < size();i++) {
			b.append(getRule(i));
			b.append("\t");
		}
		return b.toString();
			
	}
	public int size() {
		return list.size();
	}
	/**
	 * Adds all objects from the Collection
	 * TODO verify if objects are {@link IDecisionRule}
	 */
	public boolean addAll(Collection arg0) {
		boolean b = list.addAll(arg0);
		setChanged();
		notifyObservers();
		return b;
	}
	/**
	 * not implemented
	 */
	public void add(int arg0, IDecisionRule arg1) {
		return ;
		/*
		list.add(arg0,arg1);
		setChanged();
		notifyObservers();
		*/
		
	}
	/**
	 * Adds an object only if it is a  rule {@link IDecisionRule}
	 * otherwise returns false
	 */
	public boolean add(IDecisionRule arg0) {
		if (arg0 instanceof IDecisionRule) { 
			addRule((IDecisionRule) arg0);
			return true;
		}	else return false;

	}
	public boolean addAll(int arg0, Collection arg1) {
		boolean b= list.addAll(arg0,arg1);
		setChanged();
		notifyObservers();
		return b;
	}
	public void clear() {
		list.clear();
		setChanged();
		notifyObservers();
	}
	public boolean contains(Object arg0) {
		return list.contains(arg0);
	}
	public boolean containsAll(Collection arg0) {
		return list.contains(arg0);
	}
	@Override
	public boolean equals(Object arg0) {
		return list.equals(arg0);
	}
	public IDecisionRule get(int arg0) {
		return list.get(arg0);
	}
	@Override
	public int hashCode() {
		return list.hashCode();
	}
	public int indexOf(Object arg0) {
		return list.indexOf(arg0);
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public Iterator iterator() {
		return list.iterator();
	}
	public int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}
	public ListIterator listIterator() {
		return list.listIterator();
	}
	public ListIterator listIterator(int arg0) {
		return list.listIterator(arg0);
	}
	public IDecisionRule remove(int arg0) {
		IDecisionRule o = list.remove(arg0);
		setChanged();
		notifyObservers();
		return o;
	}
	public boolean remove(Object arg0) {
		boolean b= list.remove(arg0);
		setChanged();
		notifyObservers();
		return b;		
	}
	public boolean removeAll(Collection arg0) {
		boolean b =  list.removeAll(arg0);
		setChanged();
		notifyObservers();
		return b;			
	}
	public boolean retainAll(Collection arg0) {
		boolean b = list.retainAll(arg0);
		setChanged();
		notifyObservers();
		return b;			
	}
	public IDecisionRule set(int arg0, IDecisionRule arg1) {
		IDecisionRule o =list.set(arg0,arg1);
		setChanged();
		notifyObservers();
		return o;			
	}
	public List subList(int arg0, int arg1) {
		return list.subList(arg0,arg1);
		
	}
	public Object[] toArray() {
		return list.toArray();
	}
	public Object[] toArray(Object[] arg0) {
		return list.toArray(arg0);
	} 
	public void fromXML(Element xml)  throws XMLDecisionMethodException{
		// TODO Auto-generated method stub
		
	}
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException {
		Element m = document.createElement(xmltag_RULES);
		m.setAttribute(xmltag_CLASS, getClass().getName());
		return m;
	}		
	public Element toXML(Document document)  throws XMLDecisionMethodException {
		Element e = document.createElement(XMLSerializable.xmltag_RULES);
		for (int i=0; i < size(); i++) {
			IDecisionRule rule = getRule(i);
			if (rule instanceof XMLSerializable) 
				e.appendChild(((XMLSerializable) rule).toXML(document));
			else throw new XMLDecisionMethodException(rule.getClass().getName() + " not XML serializable!");
		}
		return e;
	}
	public ArrayList getList() {
		return list;
	}
	public void setList(ArrayList list) {
		this.list = list;
	}	
}
