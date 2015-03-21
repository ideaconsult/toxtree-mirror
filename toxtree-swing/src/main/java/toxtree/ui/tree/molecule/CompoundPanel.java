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
 * CompoundPanel
 * @author Nina Jeliazkova <br>
 * <b>Created</b> 2005-4-30
 */
package toxtree.ui.tree.molecule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

import toxTree.query.MyAssociationBond;
import toxTree.tree.ProgressStatus;
import toxtree.data.DataContainer;
import toxtree.ui.molecule.NavigationPanel;
import toxtree.ui.molecule.PropertyPanel;
import ambit2.rendering.IAtomContainerHighlights;
import ambit2.ui.Panel2D;




/**
 * A {@link javax.swing.JPanel} descendant, displaying 2D structure diagram of the molecule at the bottom
 * and a table with molecule properties read by {@link org.openscience.cdk.interfaces.Molecule#getProperties() }
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-4-30
 */
public class CompoundPanel extends JPanel implements   Observer, PropertyChangeListener  {
    
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2945504935753073616L;
    //AtomContainerPropertyTableModel tableModel = null;
    protected DataContainer dataContainer;
	protected PropertyPanel propertiesPanel;
	protected Panel2D picturePanel = null;
	protected NavigationPanel navPanel = null;
	GridBagLayout gridbag;
	
	/**
	 * 
	 */
	public CompoundPanel(DataContainer model, Color bgColor, Color fColor) {
		super();
		this.dataContainer = model;
		model.addObserver(this);
		initLayout(bgColor,fColor);
		addWidgets(bgColor,fColor);
	}
	
	@Override
    public void update(Observable o, Object arg) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
		        display();
			}
		});

    }
	private void initLayout(Color bgColor, Color fColor) {
		//gridbag = new GridBagLayout() ;
		setLayout(new BorderLayout());
		setBackground(bgColor);
		setForeground(fColor);
		setBorder(BorderFactory.createMatteBorder(5,5,5,5,bgColor));
	
	}

	protected void addWidgets(Color bgColor, Color fColor) {
		setBackground(bgColor);
        
		propertiesPanel = propertiesPanel(bgColor,fColor);
		JPanel structurePanel = structurePanel(bgColor,fColor);
		navPanel = new NavigationPanel(dataContainer,bgColor,fColor);
		
        JSplitPane splitPanel = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                propertiesPanel, structurePanel);
        splitPanel.setBackground(bgColor);
        splitPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        //splitPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Title"));
        splitPanel.setOneTouchExpandable(false);
        splitPanel.setDividerLocation(200);
        add(splitPanel, BorderLayout.CENTER);
        add(navPanel, BorderLayout.SOUTH);

        display();

	}
	protected JPanel structurePanel(Color bgColor, Color fColor) {
		JPanel strucPanel = new JPanel();
		strucPanel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("<html><b>Structure diagram</b></html>");
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setForeground(fColor);
        label.setSize(120,32);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createMatteBorder(5,0,0,0,bgColor));
        strucPanel.add(label,BorderLayout.NORTH);
        
        picturePanel = new Panel2D();
        picturePanel.setEditable(false);
        picturePanel.setBorder(BorderFactory.createLineBorder(fColor));
        //picturePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		picturePanel.setBackground(new Color(255, 255, 255));
		strucPanel.add(picturePanel,BorderLayout.CENTER);
		return strucPanel;
	}
	protected PropertyPanel propertiesPanel(Color bgColor, Color fColor) {
		return new PropertyPanel(bgColor,fColor);

	}


	
	protected void display() {
	    if (picturePanel != null) {
	    	IAtomContainer ac = dataContainer.getMolecule();
	    	picturePanel.setAtomContainer(ac,true);
	    	picturePanel.setSelector(null);
	    	try {
	    		propertiesPanel.setAtomContainer(ac);
	    		
	    	} catch (Exception x) {
	    		x.printStackTrace();
	    	}
	    }

	}
/*
    public void propertyChange(PropertyChangeEvent evt) {
        if ((evt.getNewValue() instanceof ProgressStatus) && ((ProgressStatus)evt.getNewValue()).isEstimated()) {

            display();
        } else if (evt.getPropertyName().equals(Panel2D.property_name.panel2d_selected.toString())) {
        	picturePanel.setSelector((IProcessor<IAtomContainer,IChemObjectSelection>)evt.getNewValue());	
        } else if (evt.getPropertyName().equals(Panel2D.property_name.panel2d_molecule.toString())) {
        	picturePanel.setAtomContainer((IAtomContainer)evt.getNewValue());	
        	picturePanel.setSelector(null);
        }           	
        
        
    }

*/
    public void propertyChange(final PropertyChangeEvent evt) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {

    	        if ((evt.getNewValue() instanceof ProgressStatus) && ((ProgressStatus)evt.getNewValue()).isEstimated()) {

    	            display();
    	        } else if (evt.getPropertyName().equals(Panel2D.property_name.panel2d_selected.toString())) {
    	        	picturePanel.setSelector((IAtomContainerHighlights)evt.getNewValue());	
    	        } else if (evt.getPropertyName().equals(Panel2D.property_name.panel2d_molecule.toString())) {
    	        	
    	        	IAtomContainer mol = (IAtomContainer)evt.getNewValue();
    	        	boolean showCurrentMolecule = false;
    	        	//a workaround to cope with JCP crashing on null bond order
    	        	if (mol != null) for (IBond bond: mol.bonds()) if (bond instanceof MyAssociationBond) {
    	        		showCurrentMolecule = true; break;
    	        	}
    	        	if (!showCurrentMolecule)
    	        		picturePanel.setAtomContainer((IAtomContainer)evt.getNewValue());	
    	        	picturePanel.setSelector(null);
    	        }     
    	        
    		}
    	});
    }
}
