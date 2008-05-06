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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingConstants;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.tree.DecisionNode;
import toxTree.tree.DecisionNodesList;
import toxTree.ui.tree.categories.CategoriesRenderer;

/**
 * Bottom-up layout of a binary decision tree {@link IDecisionMethod}.
 * Reflects the tree changes.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-8
 */
public class TreeLayout  extends Observable implements Observer{
	protected IDecisionMethod method;
	protected ArrayList cells;
	protected Dimension d = new Dimension(20,20);
	protected Dimension offset = new Dimension(5,5);
	protected int maxLevel = 100;
	protected int allLevels = 0;
	protected ArrayList freeCells;
	int freeCellsIndex = 0;
	protected boolean[] visited;
	protected int selectedIndex = -1;
	protected CategoriesRenderer categoriesRenderer;
	protected TreeCell selectedCell = null;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3964551750517962274L;

	/**
	 * 
	 */
	public TreeLayout(IDecisionMethod method) {
		this();
		setMethod(method);
		
	}
	public TreeLayout() {
		super();
		cells = new ArrayList();
		freeCells = new ArrayList();
		categoriesRenderer = new CategoriesRenderer();
		selectedCell = new TreeCellSelected();
		selectedCell.setColor(Color.orange);
	}
	protected boolean isVisited(IDecisionRule node) {
		if (node instanceof DecisionNode) return ((DecisionNode) node).isVisited();
		else return (visited[((IDecisionRule)node).getNum()]);
	}
	protected void setVisited(IDecisionRule node,boolean value) {
		if (node instanceof DecisionNode) ((DecisionNode) node).setVisited(value);
		else visited[((IDecisionRule)node).getNum()] = true;
	}	
	protected synchronized int calculateSize(Object node, int level, int width) {
		level++;
		if (allLevels < level) allLevels = level;
		if (node == null) return 0;
			
		
		if ((level < maxLevel) && 
				(node instanceof IDecisionRule) && 
				!isVisited((IDecisionRule)node)) {
			
			setVisited((IDecisionRule)node,true);
			
			Object child  =  method.getBranch((IDecisionRule)node,false);
			if ( child == null) 
				 child  =  method.getCategory((IDecisionRule)node,false);
			int w = calculateSize(child,level,width);

			child  =  method.getBranch((IDecisionRule)node,true);
			if ( child == null) 
				 child  =  method.getCategory((IDecisionRule)node,true);
			return w + calculateSize(child,level,width);
		} else {
			freeCells.add(new Rectangle(width*freeCells.size(),level*d.height,
					d.width-offset.width,d.height-offset.height));
			return width;
		}
		
		
	}
	/*
	protected boolean isLeaf(Object node) {
		boolean outcome[] = {TRUE,FALSE};
		if (node instanceof IDecisionCategory) return true;
		else if (node instanceof IDecisionRule) {
			if (visited[((IDecisionRule)node).getNum()]) return true;
			else for (int i=0; i < )
		} else return true;
	}
	*/
	protected TreeCell tree2cells(Object node, int level) {
		level++;
		TreeCell cell = null;
		if (node == null) return null;
		
		String id = "";
		Color clr = Color.white;
		if (node instanceof IDecisionCategory) {
			int nodeId = ((IDecisionCategory)node).getID();
			id = Integer.toString(nodeId);
			clr = categoriesRenderer.getShowColor(nodeId-1);
		} else id = ((IDecisionRule)node).getID();
		cell = new TreeCell(node,id,0,0,d.width,d.height);
		cell.setColor(clr);
		cells.add(cell);

		if ((level < maxLevel) && 
				(node instanceof IDecisionRule) && 
				(!isVisited((IDecisionRule)node))) {
			
			setVisited((IDecisionRule)node,true);
			Object branch  =  method.getBranch((IDecisionRule)node,false);
			if (branch == null) 
				branch  =  method.getCategory((IDecisionRule)node,false);
			
			TreeCell newCell1 = null;
			TreeEdge edgeYes = null;
			Rectangle r = null;
			if (branch != null) {
				newCell1 = tree2cells(branch,level);
				edgeYes = new TreeEdge("No",cell,newCell1);
				if (edgeYes != null) cells.add(edgeYes);
				r = newCell1;
			}	
			
			TreeCell newCell2 = null;
			branch  =  method.getBranch((IDecisionRule)node,true);
			if (branch == null) 
				branch  =  method.getCategory((IDecisionRule)node,true);
			
			if (branch != null) {
				newCell2 = tree2cells(branch,level);
				TreeEdge edgeNo = new TreeEdge("Yes",cell,newCell2);
				if (edgeNo != null) cells.add(edgeNo);
				
				if (r != null)
					r = newCell1.union(newCell2);
				else r = newCell2;
			} 	
			
			if (r != null)
				cell.setBounds(r.x+r.width/2-(d.width-offset.width)/2,
						level*d.height,
						d.width-offset.width,d.height-offset.height);
					
		} else {
			Rectangle2D r = (Rectangle2D) freeCells.get(freeCellsIndex);
			freeCellsIndex++;
			cell.setBounds((int)r.getX(),(int)r.getY(),
					(int)r.getWidth(),(int)r.getHeight());

		}
		return cell;
	}
	
	protected void clearVisited() {
		for (int i=0; i < visited.length;i++) visited[i]=false;
		IDecisionRuleList rules = method.getRules();
		if (rules instanceof DecisionNodesList) 
			for (int i=0; i < rules.size(); i++) 
				((DecisionNode) rules.get(i)).setVisited(false);
	}
	protected synchronized Dimension updateMethod() {
		//System.out.println("Treelayout update");
		if (method != null)	categoriesRenderer.setCategories(method.getCategories());		
		
		freeCells.clear();
		cells.clear();
		int w = d.width;
		if (method.getRules().size() > 0) {
			visited = new boolean[method.getRules().size()+1];
			clearVisited();
			allLevels = 0;
			try {
				w = calculateSize(method.getRule(0),0,d.width);
				clearVisited();
				freeCellsIndex = 0;
				tree2cells(method.getRule(0),0);
				
			} catch (Exception x) {
				x.printStackTrace();

			}
			

		}
		if (selectedIndex > -1) {
			MyTreeCell cell = (MyTreeCell) cells.get(selectedIndex);
			selectedCell.setBounds(cell);
		}
		return new Dimension(w,(allLevels +1)* d.height);

	}
	public synchronized Dimension setMethod(IDecisionMethod method) {
		//if (this.method != null) ((Observable) method).deleteObserver(this);
		
		this.method = method;

		
		Dimension d = updateMethod();
		
		if (method instanceof Observable)
			((Observable) method).addObserver(this);
		
		setChanged();
		notifyObservers();
		return d;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
			  hints.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
              hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
              g2d.setRenderingHints(hints);
              
      	selectedCell.paint(g2d,true);        
		for (int i=0; i < cells.size(); i++) {
			((MyTreeCell) cells.get(i)).paint(g2d,false);
		}	

	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (o instanceof IDecisionMethod) {
			
			updateMethod();
			setChanged();
			notifyObservers();
		} 	
	}
	public Object findObject(int x, int y) {
		int tollerance = (int)Math.round(2.0*d.width/3.0);
		for (int i=0;i < cells.size();i++) 
			if (cells.get(i) instanceof MyTreeCell) { //don't bother withh edges
				MyTreeCell cell = (MyTreeCell) cells.get(i);
				if ((Math.abs(cell.x-x) <= tollerance) && 
					(Math.abs(cell.y-y) <= tollerance))  {
					selectedIndex = i;
					selectedCell.setBounds(cell);
					setChanged();
					notifyObservers();					
					if (cell instanceof TreeCell) return ((TreeCell)cell).getObject();
					else return cells.get(i).toString();

				}	
			}; 
		return null;	
	}
	/**
	 * @return Returns the method.
	 */
	public synchronized IDecisionMethod getMethod() {
		return method;
	}
}
abstract class MyTreeCell extends Rectangle {
	protected Stroke stroke=null;
	protected Shape shape = null;
	protected Point[] ports;
	protected int portOrientation = SwingConstants.VERTICAL;
	protected String name="";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7802367433809275409L;
	public MyTreeCell(String name,int x,int y,int w,int h) {
		super(x,y,w,h);
		this.name = name;
		ports = new Point[2]; //2 ports , one for incioming and one for outcoming edges
		ports[0] =  new Point();
		ports[1] =  new Point();
		setDefaultPorts();
		if (shape == null)
			setShape(this);
		stroke = new BasicStroke(1);
	}
	/**
	 * SwingConstants.HORIZONTAL orientation (o)->(o)->(o) 
	 * SwingConstants.VERTICAL  (default) 
	 * (o)
	 *  |
	 * (o)
	 */
	protected void setDefaultPorts() {
		switch (portOrientation ) {
		case (SwingConstants.HORIZONTAL): {
			for (int i=0;i<ports.length;i++)
				ports[i].y = y+height/2;
			ports[0].x = x;
			ports[1].x = x+width;			
			break;
		}
		default: {
			for (int i=0;i<ports.length;i++)
				ports[i].x = x+width/2;
			ports[1].y = y;
			ports[0].y = y+height;
			break;
		}
		}		
	}
	@Override
	public void setBounds(int x,int y,int w,int h) {
		super.setBounds(x,y,w,h);
		setShape(this);
		setDefaultPorts();
	}	
	public void paint(Graphics g) {
		paint(g,false);
	}
	public abstract void paint(Graphics g,boolean isSelected);
	public Point getPort(int index) {
		return ports[index];
	}
	
	public synchronized void setPort(int index,Point port) {
		this.ports[index] = port;
	}
	
	/**
	 * @return Returns the shape.
	 */
	public synchronized Shape getShape() {
		return shape;
	}
	/**
	 * @param shape The shape to set.
	 */
	public synchronized void setShape(Shape shape) {
		this.shape = shape;
	}
	@Override
	public String toString() {
		return name;
	}
}
class TreeCell extends MyTreeCell {
	protected Color color = Color.blue;
	protected Stroke selectedStroke = new BasicStroke(3);
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1786404329947134420L;
	protected Object object = null;
	public TreeCell(Object object,String name,int x,int y,int w,int h) {
		super(name,x,y,w,h);
		this.object = object;
	}

	/**
	 * @return Returns the object.
	 */
	public synchronized Object getObject() {
		return object;
	}
	/**
	 * @param object The object to set.
	 */
	public synchronized void setObject(Object object) {
		this.object = object;
	}
	@Override
	public void paint(Graphics g, boolean isSelected) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		
		g2d.fillOval(x,y,width,height);
		
			
		if (object != null) {
			if (isSelected) g2d.setColor(Color.orange);
			else g2d.setColor(Color.black);
			g2d.drawString(name,x+3,y+10);
		}
	}
	/**
	 * @return Returns the color.
	 */
	public synchronized Color getColor() {
		return color;
	}
	/**
	 * @param color The color to set.
	 */
	public synchronized void setColor(Color color) {
		this.color = color;
	}
}

class TreeCellSelected    extends TreeCell {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -3447357912938382589L;
	public TreeCellSelected() {
		super(null,"selected",0,0,0,0);
	}
	@Override
	public void paint(Graphics g, boolean isSelected) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		//g2d.setPaint(new GradientPaint(	));
		//g2d.fillRoundRect(x-5,y-2,width+10,height+4,5,5);
		g2d.fill3DRect(x-6,y-4,width+15,height+8,true);
		//g2d.setStroke(Str);
		//g2d.drawRoundRect(x-5,y-2,width+10,height+4,5,5);
		                                   

		/*	
		if (object != null) {
			if (isSelected) g2d.setColor(Color.orange);
			else g2d.setColor(Color.black);
			g2d.drawString(name,x+5,y+10);
		}
		*/
	}
	
}
class TreeEdge extends MyTreeCell {
	TreeCell fromCell;
	TreeCell toCell;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1487934462374023276L;
	public TreeEdge(String name,TreeCell from, TreeCell to) {
		super(name,0,0,0,0);
		this.fromCell = from;
		this.toCell = to;
		stroke = new BasicStroke(1);
	}
	@Override
	public void paint(Graphics g, boolean isSelected) {
		Graphics2D g2d = (Graphics2D) g;
		if ((fromCell != null) && (toCell != null)) {
			Point from = fromCell.getPort(0);
			Point to = toCell.getPort(1);
			g2d.setStroke(stroke);
			g2d.setColor(Color.black);
			/*
			g2d.drawRect(from.x-1,from.y-1,1,1);
			g2d.drawRect(to.x-1,to.y-1,1,1);
			*/
			g2d.drawLine(from.x,from.y,to.x,to.y);
		}
	}
}
