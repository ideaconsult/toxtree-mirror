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
package toxTree.ui.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import toxTree.core.IDecisionMethod;

/**
 * Encapsulates {@link TreeLayout} in a {@link JComponent}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> 2005-10-8
 */
public class JCustomTreeComponent extends JComponent implements ITreeView,Observer {
	protected TreeLayout treeLayout;
	protected JPopupMenu popup;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6904273473967825167L;
	/**
	 * 
	 */
	public JCustomTreeComponent(IDecisionMethod method, ActionMap actions) {
		super();
		treeLayout = new TreeLayout();
		Dimension d = treeLayout.setMethod(method);
		setPreferredSize(d);
        
		setToolTipText("<html>"+method.getExplanation()+"</html>");
		treeLayout.addObserver(this);
		
		if (actions != null) {
		    popup = new JPopupMenu();
		    Object[] keys = actions.keys();
			for (int i = 0; i < keys.length; i++) {
				JMenuItem menuItem = new JMenuItem(actions.get(keys[i]));
			    popup.add(menuItem);				
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		g.setColor(Color.white);
		g.fillRect(0,0,d.width,d.height);		
		treeLayout.paint(g);
	}
	
	
	public void update(Observable o, Object arg) {
		if (o instanceof TreeLayout) { 
			repaint();
		}	

	}
	public Object objectAt(int x, int y) {
		return treeLayout.findObject(x,y);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipLocation(java.awt.event.MouseEvent)
	 */
	@Override
	public Point getToolTipLocation(MouseEvent event) {
		return new Point(event.getX(), event.getY());
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent event) {
		Object o = treeLayout.findObject(event.getX(), event.getY());
		if (o == null) return treeLayout.getMethod().getExplanation();
		else return o.toString();
	}
	public void showPopup(MouseEvent e) {
		if (popup != null)
			popup.show(e.getComponent(),e.getX(), e.getY());		
	}
    public IDecisionMethod getDecisionMethod() {
        return treeLayout.getMethod();
    }
    public JComponent getJComponent() {
        return this;
    }
    public void setDecisionMethod(IDecisionMethod method) {
            setToolTipText(method.getExplanation());        
            Dimension d = treeLayout.setMethod(method);
            setPreferredSize(d);
    }

    public JPopupMenu getPopupMenu() {
        // TODO Auto-generated method stub
        return popup;
    }
    public void addSelectRuleListener(PropertyChangeListener listener) {
        // TODO Auto-generated method stub
        
    }
    public Action getHelpAction() {
        // TODO Auto-generated method stub
        return null;
    }
}
