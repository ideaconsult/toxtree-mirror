/*
Copyright (C) 2005-2006  

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

package toxtree.ui.tree.rules;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.templates.MoleculeFactory;

import toxtree.ui.molecule.MoleculeEditAction;
import ambit2.core.smiles.SmilesParserWrapper;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.ISmartsPatternFactory;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternFactory;
import ambit2.ui.Panel2D;

public class SmartsPanelEditor extends JPanel  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8958523921952590821L;
	protected ISmartsPattern smartsPattern;
	protected JTextField patternName;
	protected JTextArea pattern;
	protected JLabel status;
	protected Panel2D panel2D;
	protected IAtomContainer testMolecule = null;
	protected MoleculeEditAction moleculeEditAction;
	protected ISmartsPatternFactory smartsPatternFactory;
	
	public ISmartsPatternFactory getSmartsPatternFactory() {
		return smartsPatternFactory;
	}
	public void setSmartsPatternFactory(ISmartsPatternFactory smartsPatternFactory) {
		if (smartsPatternFactory == null)
			smartsPatternFactory = new SmartsPatternFactory();
		else	
			this.smartsPatternFactory = smartsPatternFactory;
	}
	public SmartsPanelEditor() {
		this("SMARTS","C",null);
	}
	public SmartsPanelEditor(String name, String smarts, ISmartsPatternFactory smartsPatternFactory) {
		super(new BorderLayout());
		setSmartsPatternFactory(smartsPatternFactory);
		testMolecule = MoleculeFactory.makeAlkane(6);
		try {
			smartsPattern = getSmartsPatternFactory().createSmartsPattern(smarts, false);
		} catch (SMARTSException x) {
			//x.printStackTrace();
			smartsPattern = null;
		}
		patternName = new JTextField(name);
		patternName.setBorder(BorderFactory.createTitledBorder("Title"));
//		patternName.addFocusListener(this);
		pattern = new JTextArea(5,80);
		pattern.setWrapStyleWord(true);
		//pattern.addFocusListener(this);
		pattern.setAutoscrolls(true);
		pattern.setText(smarts);
		pattern.setPreferredSize(new Dimension(400,200));
		pattern.setMinimumSize(new Dimension(128,128));
		createPopupmenu();
		JScrollPane scrollPane = new JScrollPane(pattern,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createTitledBorder("SMARTS"));
		
		status = new JLabel("");
		
 	    panel2D = new Panel2D();
 	    panel2D.setToolTipText("Click here to edit structure");
 	    panel2D.setPreferredSize(new Dimension(200,200));
 	    panel2D.setMinimumSize(new Dimension(150,150));
        panel2D.setBorder(BorderFactory.createTitledBorder("Test compound"));
        panel2D.setAtomContainer(testMolecule,true);
        createPanel2DPopupmenu();


	        
		
		JButton b = new JButton(new AbstractAction("Test SMARTS") {
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				try {
					isValidSMARTS(getSMARTS());

					int hits = smartsPattern.match(testMolecule);
						
					if (hits ==0)
						status.setText("SMARTS Valid, \nNO hit");
					else
						status.setText("SMARTS Valid, \n"+ hits + " hits");
					status.setToolTipText(status.getText());
				} catch (Exception x) {
					status.setText(x.getMessage());
					status.setToolTipText(x.toString());
				}
				
			};
		});
		b.setMinimumSize(new Dimension(64,18));
		b.setToolTipText("Click here to verify SMARTS validity");
		JPanel c = new JPanel(new BorderLayout());
		c.setBackground(getBackground());
		status.setBackground(getBackground());
		status.setBorder(BorderFactory.createEtchedBorder());
		c.add(status, BorderLayout.CENTER);
		//c.add(new JScrollPane(status,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		c.add(b,BorderLayout.EAST);
		c.setPreferredSize(new Dimension(100,32));
 	    c.setMinimumSize(new Dimension(100,24));
		
		add(patternName,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.SOUTH);
		
		JPanel np = new JPanel(new BorderLayout());
		np.add(panel2D,BorderLayout.CENTER);
		np.add(c,BorderLayout.SOUTH);
		add(np,BorderLayout.CENTER);
		
		moleculeEditAction = new MoleculeEditAction(testMolecule);
    	moleculeEditAction.setParentComponent(this);
    	moleculeEditAction.setModal(true);
    	setMinimumSize(new Dimension(300,300));
    	setPreferredSize(new Dimension(500,500));
		
	}
	public String getTitle() {
		return patternName.getText();
	}
	public String getSMARTS() {
		String smarts = pattern.getText();
		return smarts.replace("\n", "");
		
	}
	
	/**
	 * If not valid throws exception
	 * @param smarts
	 * @throws Exception
	 */
	public void isValidSMARTS(String smarts) throws Exception {
			if (smartsPattern == null)  
				smartsPattern = smartsPatternFactory.createSmartsPattern(smarts, false);
			else
				smartsPattern.setSmarts(smarts);
	}
	
    protected void createPanel2DPopupmenu() {
    	AbstractAction[] actions = new AbstractAction[2];
        actions[0] = new AbstractAction("Insert SMILES") {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
	        		String smiles = JOptionPane.showInputDialog("Enter SMILES");
	        		SmilesParserWrapper p =  SmilesParserWrapper.getInstance();
	        		testMolecule = p.parseSmiles(smiles.trim());
	        		panel2D.setAtomContainer(testMolecule,true);
        		} catch (Exception x) {
        			status.setText(x.getMessage());
        		}
        	};
        };
        actions[1] = new AbstractAction("Edit structure") {
        	public void actionPerformed(ActionEvent arg0) {
          		moleculeEditAction.setMolecule(testMolecule);
        		moleculeEditAction.actionPerformed(null);
        		testMolecule = moleculeEditAction.getMolecule();
        		panel2D.setAtomContainer(testMolecule,true);
        	};
        };

        final JPopupMenu menu = new JPopupMenu();
        for (int i=0; i<actions.length; i++) {
            menu.add(new JMenuItem(actions[i]));
        }
        //menu.add(submenu);
        
        // Add a listener to display pop-up
        panel2D.addMouseListener(new MouseAdapter() {
            	@Override
				public void mouseClicked(MouseEvent arg0) {
            
            		moleculeEditAction.setMolecule(testMolecule);
            		moleculeEditAction.actionPerformed(null);
            		testMolecule = moleculeEditAction.getMolecule();
            		panel2D.setAtomContainer(testMolecule,true);
            	};
            @Override
			public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    menu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
            @Override
			public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    menu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });
        
		//return new ActionPanel(null,psModel.getUserData(),actions,"");
    }

    protected void createPopupmenu() {
    	AbstractAction[] actions = new AbstractAction[5];
        actions[0] = new AbstractAction("Select all") {
        	public void actionPerformed(ActionEvent arg0) {
        		pattern.selectAll();
        	};
        };
        actions[1] = new AbstractAction("Cut") {
        	public void actionPerformed(ActionEvent arg0) {
        		pattern.cut();
        	};
        };
        actions[2] = new AbstractAction("Copy") {
        	public void actionPerformed(ActionEvent arg0) {
        		pattern.copy();
        	};
        };

        actions[3] = new AbstractAction("Paste") {
        	public void actionPerformed(ActionEvent arg0) {
        		pattern.paste();
        	};
        };
        actions[4] = new AbstractAction("Load from file") {
        	public void actionPerformed(ActionEvent arg0) {
        		loadFromFile();
        	};
        };

        final JPopupMenu menu = new JPopupMenu();
        for (int i=0; i<actions.length; i++) {
            menu.add(new JMenuItem(actions[i]));
        }
        //menu.add(submenu);
        
        // Add a listener to display pop-up
        pattern.addMouseListener(new MouseAdapter() {
            @Override
			public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    menu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
            @Override
			public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    menu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });
        
		//return new ActionPanel(null,psModel.getUserData(),actions,"");
    }
    protected void loadFromFile() {
    	JFileChooser fc = new JFileChooser();
	    fc.showOpenDialog(this);
	    File file = fc.getSelectedFile();
	    if (file != null) 
	    try {
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	        boolean eof = false;
	        String fileLine;
	        StringBuffer b = new StringBuffer();
	        while (!eof) {
	          fileLine = reader.readLine();
	          if (fileLine == null)
	            eof = true;
	          else {
	        	  b.append(fileLine);
	        	  b.append('\n');
	          }
	        }
	        reader.close();
	        pattern.setText(b.toString());
	    } catch (Exception x) {
	    	pattern.setText("");
	    }
    }    
}


