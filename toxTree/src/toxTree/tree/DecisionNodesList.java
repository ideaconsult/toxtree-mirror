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
import java.util.Observer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.core.XMLSerializable;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.XMLDecisionMethodException;

/**
 * A list of {@link DecisionNode}, implementing {@link toxTree.core.IDecisionRuleList}.
 * Implements the tree structure of decision nodes used in {@link UserDefinedTree}.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-25
 */
public class DecisionNodesList extends Observable implements IDecisionRuleList, Observer, XMLSerializable/*, Externalizable */{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6651725002258477102L;
	protected ArrayList<IDecisionRule> list = null;
	/**
	 * 
	 */
	public DecisionNodesList() {
		super();
		list = new ArrayList<IDecisionRule>();
	}
	public DecisionNodesList(IDecisionCategories categories,
			String[] customRules,int[][] transitions) {
		this();
		setRules(customRules);
		setTransitions(categories,transitions);
	}

	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRulesList#addRule(toxTree.core.IDecisionRule)
	 */
	public void addRule(IDecisionRule rule) {
		addNode(new DecisionNode(rule));
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRulesList#getRule(int)
	 */
	public IDecisionRule getRule(int index) {
		return ((DecisionNode) list.get(index)).getRule();
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRulesList#setRules(java.lang.String[])
	 */
	public void setRules(String[] customRules) {
		for (int i=0; i < customRules.length; i++) 
			try {
				IDecisionRule rule = (IDecisionRule) AbstractRule.createRule(customRules[i]);
				rule.setNum(i+1);
				addRule(rule);
			} catch (DecisionMethodException x) {
				list.clear();
				x.printStackTrace();
			}		
	}
	protected void setTransitions(IDecisionCategories categories,
			int[][] customTransitions) {
		DefaultCategory key = new DefaultCategory("",1);
		for (int i =0;i< customTransitions.length;i++) {
			DecisionNode node = getNode(i);
			if (customTransitions[i][0] != 0) 
				node.setBranch(false,getNode(customTransitions[i][0]-1));
			if (customTransitions[i][1] != 0) 
				node.setBranch(true,getNode(customTransitions[i][1]-1));
			if (customTransitions[i][2] != 0) {
				key.setID(customTransitions[i][2]);
				node.setCategory(false, categories.getCategory(key));
			} 
			if (customTransitions[i][3] != 0) {
				key.setID(customTransitions[i][3]);
				node.setCategory(true,  categories.getCategory(key));
			}
								
		}
	}
	
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionRulesList#size()
	 */
	public int size() {
		return list.size();
	}
	public DecisionNode getNode(int index) {
		if (index < list.size()) return (DecisionNode) list.get(index);
		else return null;
	}
	public void setNode(int index, DecisionNode node) {
		set(index,node);
	}
	public void addNode(DecisionNode node) {
		add(node);
	}	
	public void removeNode(DecisionNode node) {
		remove(node);
		
	}
	public boolean addAll(Collection arg0) {
		boolean b = list.addAll(arg0);
		setChanged();
		notifyObservers();
		return b;
	}
	public void add(int arg0, IDecisionRule arg1) {
		return ;
		/*
		if (arg1 instanceof IDecisionRule) {
			list.add(arg0,arg1);
			setChanged();
			notifyObservers();
		}
		*/		
	}
	public boolean add(IDecisionRule arg0) {
		if (arg0 instanceof IDecisionRule) {
			boolean b = list.add(arg0);
			
			((IDecisionRule) arg0).setNum(size());
			
			((Observable) arg0).addObserver(this);
			
			setChanged();
			notifyObservers();
			return b;
		} return false;
		
	}
	public boolean addAll(int arg0, Collection arg1) {
		/*
		boolean b= list.addAll(arg0,arg1);
		setChanged();
		notifyObservers();
		return b;
		*/
		return false;
	}
	public void clear() {
		for (int i=0; i < list.size(); i++)
			if (list.get(i) instanceof Observable)
				((Observable) list.get(i)).deleteObserver(this);
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
	public Iterator<IDecisionRule> iterator() {
		return list.iterator();
	}
	public int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}
	public ListIterator<IDecisionRule> listIterator() {
		return list.listIterator();
	}
	public ListIterator<IDecisionRule> listIterator(int arg0) {
		return list.listIterator(arg0);
	}
	public IDecisionRule remove(int arg0) {
		IDecisionRule o = list.remove(arg0);
		if (o instanceof Observable)
			((Observable) o).deleteObserver(this);		
		setChanged();
		notifyObservers();
		return o;
	}
	public boolean remove(Object arg0) {
		if (arg0 instanceof Observable)
			((Observable) arg0).deleteObserver(this);
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
	public List<IDecisionRule> subList(int arg0, int arg1) {
		return list.subList(arg0,arg1);
		
	}
	public Object[] toArray() {
		return list.toArray();
	}
	public Object[] toArray(Object[] arg0) {
		return list.toArray(arg0);
	} 
		
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (o instanceof DecisionNode) {
			setChanged();
			notifyObservers();
		}

	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 
	protected Object clone() throws CloneNotSupportedException {
		DecisionNodesList tree = (DecisionNodesList)Introspection.createObject(getClass().getName());
		for (int i= 0;i<list.size();i++) {
			IDecisionRule rule = tree.getRule(i);
			IDecisionCategory categoryNo = getNode(i).getCategory(false);
			IDecisionCategory categoryYes = tree.getCategory(rule,true);
			if (categoryYes != null) categories.addCategory(categoryYes);
			
			DecisionNode node = new DecisionNode(rule.clone(),null,null,
					obj.getCa);
			obj.addNode(rule.clone());
		}
		return obj;
	}
	*/
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		for (int i=0;i<size();i++) {
			b.append(getNode(i));
			b.append('\n');
		}
		return b.toString();
	}
	
	public void fromXML(Element xml)  throws XMLDecisionMethodException{
		// TODO Auto-generated method stub
		
	}
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException {
		Element m = document.createElement(xmltag_RULES);
		m.setAttribute(xmltag_CLASS, getClass().getName());
		for (int i=0; i < size(); i++) {
			IDecisionRule rule = getRule(i);
			if (rule instanceof XMLSerializable) 
				m.appendChild(((XMLSerializable) rule).toShallowXML(document));
			else throw new XMLDecisionMethodException(rule.getClass().getName() + " not XML serializable!");
		}
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
	/*
	public void readExternal(ObjectInput arg0) throws IOException, ClassNotFoundException {
		
		
	}
	public void writeExternal(ObjectOutput out) throws IOException {
		try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element e = toShallowXML(doc);
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
	public ArrayList<IDecisionRule> getList() {
		return list;
	}
	public void setList(ArrayList<IDecisionRule> list) {
		this.list = list;
	}

}
