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
package toxtree.ui.molecule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

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
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.inchi.InChIToStructure;
import org.openscience.cdk.index.CASNumber;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.query.FunctionalGroups;
import toxTree.query.remote.RemoteCompoundLookup;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import ambit2.base.config.Preferences;
import ambit2.base.data.Property;
import ambit2.core.config.AmbitCONSTANTS;
import ambit2.core.data.EINECS;

/**
 * A {@link javax.swing.JPanel} to enter a SMILES. Now it supports a history of entered SMILES that can be 
 * navigated back and forward and by a drop down list.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2011-8-1
 */
public class SmilesEntryPanel extends StructureEntryPanel implements ItemListener, ActionListener {
	JComboBox smilesBox = null;
	protected JPopupMenu popup;
	protected ResourceBundle labels;
    protected StructureDiagramGenerator sdg = null;
    enum _labels {
    	go,
    	title,
    	hint,
    	forwardButton,
    	backButton,
    	forwardButton_hint,
    	backButton_hint,
    	createdByOpsin,
    	createdBySMILES,
    	createdByInChI,
    	error_cas,
    	error_einecs,
    	error_inchi,
    	error_smiles,
    	error_remote,
    	error_unknown
    }
    
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8184455436899528629L;

	/**
	 * 
	 */
	public SmilesEntryPanel() {
		super();
		labels =  ResourceBundle.getBundle(getClass().getName(),
					Locale.ENGLISH,
					getClass().getClassLoader());
		addWidgets();		
	}

	private void initLayout() {
		setLayout(new BorderLayout());
	}


	private void addWidgets() {

		initLayout();


        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top,BoxLayout.LINE_AXIS));
        
        Dimension d = new Dimension(48,24);
        JButton backButton = new JButton(new AbstractAction(labels.getString(_labels.backButton.name())) {
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
        JButton forwardButton = new JButton(new AbstractAction(labels.getString(_labels.forwardButton.name())) {
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

        forwardButton.setToolTipText(labels.getString(_labels.forwardButton_hint.name()));
        backButton.setToolTipText(labels.getString(_labels.backButton_hint.name()));
        top.add(backButton);top.add(forwardButton);

        JLabel labelSmi = new JLabel(labels.getString(_labels.title.name()));
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
        smilesBox.setToolTipText(String.format("<html>%s</html>",labels.getString(_labels.hint.name())));
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
        JButton go = new JButton(new AbstractAction(labels.getString(_labels.go.name())) {
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
	
	protected boolean createMoleculeFromSMILES(String input) {
		String errormsg = null;
		input = input.replace('\n',' ').trim();
		boolean cas = CASNumber.isValid(input);
		boolean einecs = EINECS.isValid(input);
		IAtomContainer a = null;
		if (cas || einecs) {
			if (Preferences.getProperty(Preferences.REMOTELOOKUP).equals("true")) {
				a =  retrieveRemote(input);
				if (a!=null) {
					a.setID(input);
					if (cas) a.setProperty(Property.CAS, input);
					else if (einecs) a.setProperty(Property.EC, input);
	
				} else {
					errormsg = String.format("%s entered, failed to retrieve from remote server",
								cas?"CAS":einecs?"EINECS":"Unknown query");
				}
			} else {
				errormsg = String.format("%s %s entered, but remote queries are disabled. Enable remote queries via 'Method/Decision Tree Options/Remote query' menu.",
						cas?"CAS":einecs?"EINECS":"Unknown query",input);
			}
		} else {
			try {
				a = FunctionalGroups.createAtomContainer(input,false);
			} catch (Exception x) {
				errormsg = x.getMessage();
			}
	    	if (a != null) { 
		    	a.setProperty(CDKConstants.COMMENT,labels.getString(_labels.createdBySMILES.name()));
		    	a.setProperty(AmbitCONSTANTS.SMILES,input);
		    	//a.setProperty(AmbitCONSTANTS.FORMULA,mf.analyseAtomContainer(a));
	    	} else try {
	    		errormsg = String.format(labels.getString(_labels.error_smiles.name()),input);
	    		a = isInChI(input);
	    		if (a != null) {
		    		a.setProperty(CDKConstants.COMMENT,labels.getString(_labels.createdByInChI.name()));
			    	a.setProperty(AmbitCONSTANTS.INCHI,input);
			    	a.setID(input);
	
	    		} else {
	    			errormsg = String.format(labels.getString(_labels.error_inchi.name()),input);
	    		    NameToStructure nts = NameToStructure.getInstance();
	    		    String smiles = null;
	    			try {
	    				smiles = nts.parseToSmiles(input);
	    				a = FunctionalGroups.createAtomContainer(smiles,false);
	    				a.setProperty(AmbitCONSTANTS.SMILES,smiles);
	    	    		a.setProperty(CDKConstants.COMMENT,labels.getString(_labels.createdByOpsin.name()));
	    		    	a.setProperty(AmbitCONSTANTS.NAMES,input);
	    		    	a.setID(input);
	    			} catch (Exception x) {
	    	    		a = null;
	    	    		errormsg = x.getMessage();
	    			}
	    			if (a == null) {

	    				if (Preferences.getProperty(Preferences.REMOTELOOKUP).equals("true")) {
	    					a =  retrieveRemote(input);
	    					if (a!=null) {
	    						a.setID(input);
	    						if (cas) a.setProperty(Property.CAS, input);
	    						else if (einecs) a.setProperty(Property.EC, input);
	    						else a.setProperty(Property.Names, input);
	    					} else {
	    						errormsg = String.format("%s entered, failed to retrieve from remote server",
	    									cas?"CAS":einecs?"EINECS":"Unknown query");
	    					}
	    				} else {
	    					errormsg = String.format("%s '%s' entered. Name to structure conversion failed. Please enable remote queries via 'Method/Decision Tree Options/Remote query' menu.",
	    							"Name",input);
	    				}
	    			}	
	    		}
	    	} catch (Exception x) {
	    		a = null;
	    		errormsg = x.getMessage();
	    	}
		}
    	
    	if (a != null) {
    		if (a instanceof IAtomContainer) 
	            try {
	            	if (sdg == null) sdg = new StructureDiagramGenerator();
	            	if (ConnectivityChecker.isConnected(a)) {
	                    sdg.setMolecule((IAtomContainer)a);
	                    sdg.generateCoordinates(new Vector2d(0,1));
	                    a = sdg.getMolecule();            		
	            	} else {
		            	IAtomContainerSet molecules = ConnectivityChecker.partitionIntoMolecules(a);            	
		                a.removeAllElements();
		        		for (int i=0; i< molecules.getAtomContainerCount();i++) {
		       				sdg.setMolecule(molecules.getAtomContainer(i));
		       				sdg.generateCoordinates(new Vector2d(0,1));
		       				a.add(sdg.getMolecule());
		        		}
	            	}
	
	            } catch (Exception x) {
	                System.err.println(x);
	            }
            
	        setMolecule(a);
	        
	        smilesBox.addItem(input);
	        return true;
    	} else {
			JOptionPane.showMessageDialog(getParent(),
				    //"You have entered an invalid SMILES or InChI, please try again.",						
				    errormsg,
				    "Please try again",
				    JOptionPane.ERROR_MESSAGE);
			return false;
    	} 
	}
	
	public IAtomContainer isInChI(String inchi) throws Exception {
		if (inchi.startsWith(AmbitCONSTANTS.INCHI)) {
			InChIGeneratorFactory f = InChIGeneratorFactory.getInstance();
			InChIToStructure c =f.getInChIToStructure(inchi, SilentChemObjectBuilder.getInstance());
			if ((c==null) || (c.getAtomContainer()==null) || (c.getAtomContainer().getAtomCount()==0)) 
				throw new Exception("Invalid InChI");
			return c.getAtomContainer();
		} else return null;
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


	}
	
	protected IAtomContainer retrieveRemote(String input)  {
		try {
			RemoteCompoundLookup lookup = new RemoteCompoundLookup();
			return lookup.lookup(input);
		} catch (Exception x) {
			return null;
		}
		
	}
}
