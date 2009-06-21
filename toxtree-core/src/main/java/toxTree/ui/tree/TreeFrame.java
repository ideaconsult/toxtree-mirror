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
 * <b>Filename</b> TreeFrame.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-3
 * <b>Project</b> toxTree
 */
package toxTree.ui.tree;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.JFrame;

import toxTree.core.IDecisionMethod;

/**
 * A frame to visualise a decision tree {@link toxTree.core.IDecisionMethod}
 * by {@link TreeDrawing}.
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-3
 */
public class TreeFrame extends JFrame {
	protected boolean editable = false;
	//protected TreePanel treePanel;
	protected TreeDrawing treePanel;

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6805866718068055323L;

    /**
     * @throws java.awt.HeadlessException
     */
    public TreeFrame(IDecisionMethod treeMethod) throws HeadlessException {
        super();
        setTitle(treeMethod.toString());
        addWidgets(treeMethod);
        
    }
    /**
     * @throws java.awt.HeadlessException
     */
    public TreeFrame() throws HeadlessException {
        super();
        addWidgets(null);
    }
    
    /**
     * @param gc
     */
    public TreeFrame(GraphicsConfiguration gc) {
        super(gc);
        addWidgets(null);
    }

    /**
     * @param title
     * @throws java.awt.HeadlessException
     */
    public TreeFrame(String title) throws HeadlessException {
        super(title);
        addWidgets(null);
    }

    /**
     * @param title
     * @param gc
     */
    public TreeFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        addWidgets(null);
    }
    protected void addWidgets(IDecisionMethod treeMethod) {
   	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //treePanel = new TreePanel(treeMethod);
        treePanel = new TreeDrawing(treeMethod,null);
        //treePanel = new TreePrefuse(treeMethod,null);
        
        /*
        JSplitPane split = new JSplitPane();
        split.setLeftComponent(treePanel.getJComponent());
        //split.setRightComponent(treePanel.getOverview());
        split.setOneTouchExpandable(true);
        split.setContinuousLayout(false);
        split.setDividerLocation(700);
*/
        getContentPane().add(treePanel);
        
        pack();
        setVisible(true);
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2 + 100,
		      (dim.height - abounds.height) / 2);        
    }

    public void setDecisionMethod(IDecisionMethod decisionMethod) {
        //treePanel.setDecisionMethod(decisionMethod);
    	treePanel.setDecisionMethod(decisionMethod);
    }
    /* (non-Javadoc)
     * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
     */


}

