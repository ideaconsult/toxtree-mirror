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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.vecmath.Vector2d;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.tools.MFAnalyser;

import toxTree.query.FunctionalGroups;

/**
 * A {@link javax.swing.JPanel} to enter a SMILES. Now it supports a history of entered SMILES that can be 
 * navigated back and forward and by a drop down list.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-3
 */
public class SmilesEntryPanel extends StructureEntryPanel implements ItemListener, ActionListener {
	JComboBox smilesBox = null;
	protected JPopupMenu popup;
	protected MFAnalyser mf ;
    protected StructureDiagramGenerator sdg = null;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8184455436899528629L;

	/**
	 * 
	 */
	public SmilesEntryPanel() {
		super();
		//history = new Vector();
		addWidgets();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}


	private void addWidgets() {
		mf = new MFAnalyser("",new Molecule());
		//TODO history of entered SMILES to be persistent accross instances
		//TODO select na cialoto edit pole pri click (kakto w IE
		//TODO Go da prawi i estimate

		initLayout();
		//setBackground(Color.black);


        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top,BoxLayout.LINE_AXIS));
        
        Dimension d = new Dimension(52,24);
        JButton backButton = new JButton(new AbstractAction("<<") {
			private static final long serialVersionUID = 7046410922163328877L;
			public void actionPerformed(ActionEvent arg0) {
        		int index = smilesBox.getSelectedIndex();
        		index = index -1;
        		if (index < 0) index = 0;
        		if (smilesBox.getItemAt(index) != null)
        			smilesBox.setSelectedIndex(index);
        	}
        });
        backButton.setDefaultCapable(false);
        backButton.setPreferredSize(d);
        backButton.setMinimumSize(d);
        JButton forwardButton = new JButton(new AbstractAction(">>") {
			private static final long serialVersionUID = 8892599118153155748L;
			public void actionPerformed(ActionEvent arg0) {
        		int index = smilesBox.getSelectedIndex();
        		index = index +1 ;
        		if (index >= smilesBox.getItemCount()) index = smilesBox.getItemCount()-1;
        		if (smilesBox.getItemAt(index) != null)
        			smilesBox.setSelectedIndex(index);        		
        	}
        });     
        forwardButton.setDefaultCapable(false);
        forwardButton.setPreferredSize(d);
        forwardButton.setMinimumSize(d);
        forwardButton.setToolTipText("Forward to the next SMILES");
        backButton.setToolTipText("Back to the previous SMILES");
        top.add(backButton);top.add(forwardButton);

        JLabel labelSmi = new JLabel("<html>  Enter <b>SMILES</b>:</html>");
        labelSmi.setOpaque(true);
        //labelSmi.setBackground(Color.black);
        //labelSmi.setForeground(Color.white);
        labelSmi.setPreferredSize(new Dimension(80,24));
        labelSmi.setAlignmentX(CENTER_ALIGNMENT);

        top.add(labelSmi);
        labelSmi.setHorizontalAlignment(SwingConstants.RIGHT);
        
        smilesBox = new JComboBox();
        smilesBox.setEditable(true);
        smilesBox.setFocusable(true);
        //smilesBox.setHorizontalAlignment(JTextField.LEFT);
        smilesBox.setToolTipText("Enter SMILES of a molecule here");
        smilesBox.setPreferredSize(new Dimension(Integer.MAX_VALUE,24));
        
        //smilesEdit.addActionListener(this);
        //smilesBox.addPropertyChangeListener("value",this);
        smilesBox.addItemListener(this);
        popup = new JPopupMenu("Edit");
        JMenuItem mi = new JMenuItem("Copy");
        mi.addActionListener(this);
        popup.add(mi);
        mi = new JMenuItem("Paste");
        mi.addActionListener(this);
        popup.add(mi);
        smilesBox.addMouseListener(new MouseAdapter() {
        	//TODO towa ne raboti
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (e.getButton()==MouseEvent.BUTTON3) {
					popup.show(e.getComponent(),e.getX(),e.getY());
				}
			}

        });
        labelSmi.setLabelFor(smilesBox);
        
        //add(labelSmi,BorderLayout.WEST);
        add(top,BorderLayout.WEST);
        add(smilesBox,BorderLayout.CENTER);        
        JButton go = new JButton(new AbstractAction("<html><b> Go! </b></html>") {
			private static final long serialVersionUID = 7605151410870720861L;
			public void actionPerformed(ActionEvent e) {
				if (smilesBox.getSelectedItem() != null) {
					String s = smilesBox.getSelectedItem().toString();
					createMoleculeFromSMILES(s); 
				}
			}
        }
        		);
        
        add(go,BorderLayout.EAST);
        go.setDefaultCapable(true);
        

	}
	protected boolean createMoleculeFromSMILES(String smiles) {
		smiles = smiles.replace('\n',' ').trim();
    	IMolecule a = (IMolecule) FunctionalGroups.createAtomContainer(smiles,false);
    	if (a != null) { 
	    	a.setProperty(CDKConstants.COMMENT,"Created from SMILES");
	    	a.setProperty("SMILES",smiles);
	    	a.setProperty("FORMULA",mf.analyseAtomContainer(a));

            //Preferences.getProperties().getProperty(arg0)
            try {
            	if (sdg == null) sdg = new StructureDiagramGenerator();
            	if (ConnectivityChecker.isConnected(a)) {
                    sdg.setMolecule(a);
                    sdg.generateCoordinates(new Vector2d(0,1));
                    a = sdg.getMolecule();            		
            	} else {
	            	IMoleculeSet molecules = ConnectivityChecker.partitionIntoMolecules(a);            	
	                a.removeAllElements();
	        		for (int i=0; i< molecules.getMoleculeCount();i++) {
	       				sdg.setMolecule(molecules.getMolecule(i));
	       				sdg.generateCoordinates(new Vector2d(0,1));
	       				a.add(sdg.getMolecule());
	        		}
            	}

            } catch (Exception x) {
                System.err.println(x);
            }
            
	        setMolecule(a);
	        
	        smilesBox.addItem(smiles);
	        return true;
    	} else 
			JOptionPane.showMessageDialog(getParent(),
				    "You have entered an invalid SMILES, please try again.",						
				    "Error while parsing SMILES",
				    JOptionPane.ERROR_MESSAGE);
    	return false;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent evt) {
	    Object source = evt.getSource();
	    try {
	    	if ((source == smilesBox) && isVisible()) {
	    		if (evt.getStateChange() == ItemEvent.SELECTED) {
	    			String smiles = evt.getItem().toString();
	    	    	if (createMoleculeFromSMILES(smiles))
						smilesBox.repaint();
	    	    	
	    	    }
	    	    repaint();	    	    
	    	}
	    	
	    } catch (NullPointerException ex) {
	    	ex.printStackTrace();
	    }


	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
