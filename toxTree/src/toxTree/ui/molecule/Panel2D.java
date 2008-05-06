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
package toxTree.ui.molecule;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.openscience.cdk.event.ICDKChangeListener;
import org.openscience.cdk.geometry.GeometryTools;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.Renderer2D;
import org.openscience.cdk.renderer.Renderer2DModel;

import toxTree.data.ListOfAtomContainers;
import toxTree.ui.Preferences;


/**
 * 2D structure diagram
 * @author Nina Jeliazkova
 *
 */
public class Panel2D extends JPanel implements ICDKChangeListener, ComponentListener, PropertyChangeListener
{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5065125205427781493L;
    
    public ListOfAtomContainers molecules;
	public Renderer2DModel r2dm;
	public Renderer2D renderer;
	public String title = "Molecule Viewer";

    private Dimension preferredSize;
    private StructureDiagramGenerator sdg;
    protected AbstractMoleculeAction editAction = null;
    protected JLabel editLabel = null;
    
    
	/**
	 *  Constructs a MoleculeViewer with a molecule to display and a Renderer2DModel containing the information on how to display it.
	 *
	 * @param  r2dm           The rendere settings determining how the molecule is displayed
	 */
	public Panel2D(IAtomContainer atomContainer, Renderer2DModel r2dm)
	{
		setLayout(new BorderLayout());
		editLabel = new JLabel("<html><u>Edit</u></html>");
		editLabel.setVisible(editAction != null);
		editLabel.addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			editAction.actionPerformed(new ActionEvent(this,0,""));
	   		}
	    });	  		
		
		
		add(editLabel,BorderLayout.SOUTH);
		
		sdg = new StructureDiagramGenerator();
		molecules = new ListOfAtomContainers();
		if (atomContainer != null) {
			setAtomContainers(ConnectivityChecker.partitionIntoMolecules(atomContainer));
		} ;
        preferredSize = new Dimension(200, 200);
		this.r2dm = r2dm;
		
        r2dm.setBackgroundDimension(preferredSize);
        boolean showAromaticity = false;
        try {
        	showAromaticity = Boolean.parseBoolean(Preferences.getProperty(Preferences.SHOW_AROMATICITY));
        } catch (Exception x) {
        	x.printStackTrace();
        	showAromaticity = false;
        }
        
        r2dm.setShowAromaticity(showAromaticity);

		r2dm.addCDKChangeListener(this);
		renderer = new Renderer2D(r2dm);
		
		addComponentListener(this);
		
		Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.SHOW_AROMATICITY,this);

	}


	/**
	 *  Constructs a MoleculeViewer with a molecule to display
	 */
	public Panel2D(IAtomContainer atomContainer)
	{
		this(atomContainer, new Renderer2DModel());
		
	}


	/**
	 *  Constructs a MoleculeViewer with a molecule to display
	 */
	public Panel2D()
	{
		this(null, new Renderer2DModel());

	}

	/**
	 *  Sets a Renderer2DModel which determins the way a molecule is displayed
	 *
	 * @param  r2dm  The Renderer2DModel
	 */
	public void setRenderer2DModel(Renderer2DModel r2dm)
	{
		this.r2dm = r2dm;
		r2dm.addCDKChangeListener(this);
		renderer = new Renderer2D(r2dm);
	}


	/**
	 *  Sets the AtomContainer to be displayed
	 *
	 * @param  atomContainer  The AtomContainer to be displayed
	 */
	
	protected void setAtomContainers(IAtomContainerSet atomContainers)
	{
		molecules.clear();
		if (atomContainers != null)
			for (int i=0; i < atomContainers.getAtomContainerCount();i++) 
				molecules.add(atomContainers.getAtomContainer(i));
		repaint();
	}

	public void setAtomContainer(IAtomContainer molecule,boolean generateCoordinates)
	{
		if (molecule != null) {
			if ((molecule ==null) || (molecule.getAtomCount() == 0)) 
				generateCoordinates=false;
			//else if (GeometryTools.has2DCoordinates(molecule))   
			  //  generateCoordinates=false;
			//System.out.println("panel 2D\t"+Boolean.toString(generateCoordinates));
			
			IAtomContainerSet c =  ConnectivityChecker.partitionIntoMolecules(molecule);		
			try
			{
				molecules.clear();
				IMolecule m = null;
				for (int i=0; i < c.getAtomContainerCount();i++) { 
					if (generateCoordinates) {
						sdg.setMolecule((IMolecule)c.getAtomContainer(i));
						m = null;
						sdg.generateCoordinates(new Vector2d(0,1));
						m = sdg.getMolecule();
					} else m = (IMolecule)c.getAtomContainer(i);
					molecules.add(m);
				}
				repaint();
			}
			catch(Exception exc)
			{
				System.out.println("*** Exit due to an unexpected error during coordinate generation ***");
				exc.printStackTrace();
				setAtomContainers(null);
				molecules.clear();
			}
		} else {
			molecules.clear();
			repaint(); 	
		}
	}

	/**
	 *  
	 *
	 * @return    The Renderer2DModel value
	 */
	public Renderer2DModel getRenderer2DModel()
	{
		return renderer.getRenderer2DModel();
	}


	/**
	 *  Paints the molecule onto the JPanel
	 *
	 * @param  g  The graphics used to paint with.
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if (molecules != null) {

			setBackground(r2dm.getBackColor());
			/*
			SetOfMolecules c = new SetOfMolecules();
			for (int i=0;i<molecules.size();i++) c.addAtomContainer((Molecule)molecules.get(i));
			
			GeometryTools.translateAllPositive(c);
			GeometryTools.scaleMolecule(c, r2dm.getBackgroundDimension(), 0.8);
			GeometryTools.center(c, r2dm.getBackgroundDimension());
			
			renderer.paintSetOfMolecules(c, (Graphics2D)g);
			*/
			Dimension d = r2dm.getBackgroundDimension();
			
			Point2d dd[] = new Point2d[molecules.size()];
			
			switch (molecules.size()) {
			case 1: { dd[0] = new Point2d(0,0); 
				GeometryTools.translateAllPositive((IMolecule)molecules.get(0),r2dm.getRenderingCoordinates()); 
				break;}
			case 2: {
				dd[0] = new Point2d(d.width/4,d.height/2);
				dd[1] = new Point2d(3*d.width/4,d.height/2);
				break;
			}
			case 3: {
				dd[2] = new Point2d(d.width/4,d.height/4);
				dd[1] = new Point2d(3*d.width/4,d.height/4);
				dd[0] = new Point2d(d.width/4,3*d.height/4);				
				break;
			}
			case 4: {
				dd[3] = new Point2d(d.width/4,d.height/4);
				dd[2] = new Point2d(3*d.width/4,d.height/4);
				dd[1] = new Point2d(d.width/4,3*d.height/4);				
				dd[0] = new Point2d(3*d.width/4,3*d.height/4);				
				break;
			} 
			default : {
				for (int i=0;i<dd.length;i++) dd[i] = new Point2d(d.width/2,d.height/2);
				break;
			}
			}
			if (molecules.size() >= 3) 
				d = new Dimension(d.width/2,d.height/2);
			else if (molecules.size() >= 2) 
				d = new Dimension(d.width/2,d.height);
			try {
				for (int i=0;i<molecules.size();i++) {
					IAtomContainer c = molecules.getAtomContainer(i);
					//GeometryTools.translateAllPositive(c);
					//
					GeometryTools.scaleMolecule(c, d, 0.8,r2dm.getRenderingCoordinates());
					GeometryTools.center(c, d,r2dm.getRenderingCoordinates());
					if (molecules.size() > 1) {
						GeometryTools.translateAllPositive(c,r2dm.getRenderingCoordinates());
						GeometryTools.translate2DCentreOfMassTo(c,dd[i],r2dm.getRenderingCoordinates());
						
					}
					renderer.paintMolecule(c, (Graphics2D)g,false,true);
				}
			} catch (Exception x) {
				x.printStackTrace();
				g.clearRect(0,0,r2dm.getBackgroundDimension().width,r2dm.getBackgroundDimension().height);
			}
			
		} else {
			g.clearRect(0,0,r2dm.getBackgroundDimension().width,r2dm.getBackgroundDimension().height);
		}
	}



	/**
	 *  Method to notify this CDKChangeListener if something has changed in another object
	 *
	 * @param  e  The EventObject containing information on the nature and source of the event
	 */
	public void stateChanged(EventObject e)
	{
		repaint();
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent e) {
		preferredSize = getSize();
		r2dm.setBackgroundDimension(preferredSize);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	public AbstractMoleculeAction getEditAction() {
		return editAction;
	}


	public void setEditAction(AbstractMoleculeAction editAction) {
		this.editAction = editAction;
		editLabel.setVisible(editAction != null);
	}


	public void propertyChange(PropertyChangeEvent evt) {
			r2dm.setShowAromaticity(
			Boolean.parseBoolean(evt.getNewValue().toString())
			);
	}

}


