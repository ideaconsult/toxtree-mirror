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
package toxtree.ui.tree;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;

/**
 * A {@link TreeModel}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-3
 */
public class DecisionTreeModel implements TreeModel {
	protected IDecisionMethod decisionMethod = null;
	protected EventListenerList listeners;	
	/**
	 * 
	 */
	public DecisionTreeModel(IDecisionMethod decisionMethod) {
		super();
		this.decisionMethod = decisionMethod;
		listeners = new EventListenerList();
	}

	public DecisionTreeModel() {
		this(null);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot() {
		//TODo provide getTopRule
	    if (decisionMethod == null) return "NA";
	    else return decisionMethod.getRule(0);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int index) {
//		String m = " ";
	    if (decisionMethod == null) return "NA"; 
		if (parent instanceof IDecisionRule) {	    
			IDecisionRule rule = (IDecisionRule) parent;
			Object o;
			boolean answer = false; 
			switch (index) {
			//no 
			case 0: {
				//m = "[NO ].";
				answer = false; break;
			}
			//yes
			case 1: {
	//			m = "[YES].";
				answer = true; break;
			}
			default : return null;
			}
			
		    o = decisionMethod.getBranch(rule,answer);
		    if (o == null)  return decisionMethod.getCategory(rule,answer);
		    else return o;
			
		} else return null;	
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object parent) {
		if (parent instanceof IDecisionRule) return 2;
		else return 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object node) {
		return (node instanceof IDecisionCategory);
	}

	/* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
                + path + " --> " + newValue);
    }
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
	 */
	public int getIndexOfChild(Object parent, Object child) {
	    if (decisionMethod == null) return 0;	    
	    //int nNo, nYes, nObject;
	    if (parent instanceof IDecisionRule) {
	        IDecisionRule rule = (IDecisionRule) parent;
	        if (child instanceof IDecisionRule) {
		        Object o = decisionMethod.getBranch(rule,false);
		        if ((o != null) && (o == child)) return 0;
		        o = decisionMethod.getBranch(rule,true);
		        if ((o != null) && (o == child)) return 1;
	        } else if (child instanceof IDecisionCategory) {
		        Object o = decisionMethod.getCategory(rule,false);
		        if ((o != null) && (o == child)) return 0;		        
		        o = decisionMethod.getCategory(rule,true);
		        if ((o != null) && (o == child)) return 1;		        
	        }
	        
	    }
	    return 0;
	}    
    /*
	public int getIndexOfChild(Object parent, Object child) {
	    if (decisionMethod == null) return 0;	    
	    int nNo, nYes, nObject;
	    if (parent instanceof IDecisionRule) {
	        IDecisionRule rule = (IDecisionRule) parent;
	        if (child instanceof IDecisionRule) {
	            nObject = ((IDecisionRule) child).getNum();
		        Object o = decisionMethod.getBranch(rule,false);
		        if (o != null) {
		            nNo = ((IDecisionRule) o).getNum();
		            if (nNo == nObject) return 0;		            
		        }
		        o = decisionMethod.getBranch(rule,true);
		        if (o!= null) {
		            nYes = ((IDecisionRule) o).getNum();
		            if (nYes == nObject) return 1;				            
		        }
	        } else if (child instanceof IDecisionCategory) {
		        Object o = decisionMethod.getCategory(rule,false);
		        nObject = ((IDecisionCategory) child).getID();
		        if (o != null) {
		            nNo = ((IDecisionCategory) o).getID();
		            if (nNo == nObject) return 0;		            
		        }
		        o = decisionMethod.getCategory(rule,true);
		        if (o!= null) {
		            nYes = ((IDecisionCategory) o).getID();
		            if (nYes == nObject) return 1;				            
		        }	            
	        }
	        
	    }
	    return 0;
	}
	*/

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

    public void setDecisionMethod(IDecisionMethod decisionMethod) {
        this.decisionMethod = decisionMethod;

    }
    /** Call when there is a new root, which may be null, i.e. not existent. */
    protected void fireNewRoot()
    {
        Object[] pairs = listeners.getListenerList();

        Object root = getRoot();

        /* Undocumented. I think it is the only reasonable/possible solution
           to use use null as path if there is no root. TreeModels without
           root aren't important anyway, since JTree doesn't support them (yet).
        */
        TreePath path = (root != null) ? new TreePath(root) : null;
        
        TreeModelEvent e = null;
        
        for (int i = pairs.length - 2; i >= 0; i -= 2)
        {
            if (pairs[i] == TreeModelListener.class)
            {
                if (e == null)
                    e = new TreeModelEvent(this, path, null, null);
                
                ((TreeModelListener)pairs[i + 1]).treeStructureChanged(e);
            }
        }
    }
    
    /** Call when everything but the root has changed. Only may be called
        when the root is not null. Otherwise there isn't a structure to have
        changed.
    */
    protected void fireStructureChanged()
    {
        fireTreeStructureChanged(new TreePath(getRoot()));
    }
    
    /** Call when a node has changed its leaf state. */
    protected void firePathLeafStateChanged(TreePath path)
    {
        fireTreeStructureChanged(path);
    }
    
    /** Call when the tree structure below the path has completely changed. */
    protected void fireTreeStructureChanged(TreePath parentPath)
    {
        Object[] pairs = listeners.getListenerList();
        
        TreeModelEvent e = null;
        
        for (int i = pairs.length - 2; i >= 0; i -= 2)
        {
            if (pairs[i] == TreeModelListener.class)
            {
                if (e == null)
                    e = new TreeModelEvent(this, parentPath, null, null);
                
                ((TreeModelListener)pairs[i + 1]).treeStructureChanged(e);
            }
        }
     }
    
    
}
