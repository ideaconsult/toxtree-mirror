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
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openscience.cdk.ChemModel;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.applications.jchempaint.JChemPaintEditorPanel;
import org.openscience.cdk.applications.jchempaint.JChemPaintModel;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.smiles.SmilesGenerator;

/**
 * A {@link javax.swing.JDialog} containing a {@link JChemPaintEditorPanel}.
 * The intention was to launch JChempaint editor in a modal dialog, so that the molecule in the main
 * application is updated only after the dialog is closed. It turned out that   {@link JChemPaintEditorPanel}
 * don't handle well modal behaviour. Now it is launched as a nonmodal dialog.
 * @author Nina Jeliazkova
 *
 */
public class JChemPaintDialog extends JDialog {
	protected JChemPaintEditorPanel jcpep;
	protected int result;	
	//protected DataModule data;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8382571970782141100L;

	public JChemPaintDialog(JChemPaintModel jcpm) throws HeadlessException {
		super();
		addWidgets(jcpm);
	}

	public JChemPaintDialog(Dialog arg0,JChemPaintModel jcpm) throws HeadlessException {
		super(arg0);
		addWidgets(jcpm);
	}

	public JChemPaintDialog(Dialog arg0, boolean arg1,JChemPaintModel jcpm) throws HeadlessException {
		super(arg0, arg1);
		addWidgets(jcpm);
	}

	public JChemPaintDialog(Frame arg0,JChemPaintModel jcpm) throws HeadlessException {
		super(arg0);
		addWidgets(jcpm);
	}

	public JChemPaintDialog(Frame arg0, boolean arg1,JChemPaintModel jcpm) throws HeadlessException {
		super(arg0, arg1);
		addWidgets(jcpm);
	}
/*
	public JChemPaintDialog( Frame arg0,String arg1,JChemPaintModel jcpm) throws HeadlessException {
		super(arg0, arg1);
		addWidgets(jcpm);
	}
*/
	public JChemPaintDialog(Dialog arg0, String arg1, boolean arg2,JChemPaintModel jcpm)
			throws HeadlessException {
		super(arg0, arg1, arg2);
		addWidgets(jcpm);
	}

	public JChemPaintDialog(Frame arg0, String arg1,JChemPaintModel jcpm) throws HeadlessException {
		super(arg0, arg1);
		addWidgets(jcpm);
	}

	public JChemPaintDialog(Frame arg0, String arg1, boolean arg2,JChemPaintModel jcpm)
			throws HeadlessException {
		super(arg0, arg1, arg2);
		addWidgets(jcpm);
	}

	//protected void addWidgets(JChemPaintModel jcpm,DataModule toxdata) {
    protected void addWidgets(JChemPaintModel jcpm) {
        
		//this.data = toxdata;
		
        //setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE ); 
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jcpep = new JChemPaintEditorPanel(2,new Dimension(400,400),false,"stable");
		Dimension dcp = new Dimension(400,400);
		jcpep.setPreferredSize(dcp);
		jcpep.setEmbedded();
		jcpep.registerModel(jcpm);
		jcpep.setJChemPaintModel(jcpm,dcp);
		getContentPane().add(jcpep);
		setTitle(jcpm.getTitle());
		
		JButton cancelButton = new JButton("Cancel");
		JButton okButton = new JButton("OK");
		cancelButton.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
		 	cancelAction();
		 }
		});
		okButton.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
		     okAction();
		 }
		});
		getRootPane().setDefaultButton(okButton);

//		Lay out the buttons from left to right.
		JPanel buttonPane = new JPanel();
		
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(okButton);

		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(cancelButton);
		
		Dimension d = new Dimension(400,48);
		buttonPane.setPreferredSize(d);
		buttonPane.setMinimumSize(d);
//		Put everything together, using the content pane's BorderLayout.
		Container contentPane = getContentPane();
		JPanel panel = new JPanel();
		
		
		panel.setLayout(new BorderLayout());
		panel.setMinimumSize(new Dimension(400,200));		
		
		panel.add(buttonPane, BorderLayout.SOUTH);
		panel.add(jcpep, BorderLayout.CENTER);
		
		contentPane.add(panel);
		pack();
		
        
	}
    @Override
    public void setVisible(boolean b) {
        getParent().setEnabled(!b);
        super.setVisible(b);
    }
	public void cancelAction() {
	    result = JOptionPane.CANCEL_OPTION;
		setVisible(false);
        
		
	}
	public IMolecule okAction() {
	    result = JOptionPane.OK_OPTION;
		setVisible(false);
        
        IMolecule updatedMolecule = new Molecule(); 
        
        IMoleculeSet m = jcpep.getJChemPaintModel().getChemModel().getMoleculeSet();  
        
        for (int i=0; i < m.getAtomContainerCount(); i++) 
                updatedMolecule.add(m.getMolecule(i));


        /* TODO
        MoleculeSetManipulator.getAllInOneContainer(
                    jcpep.getJChemPaintModel().getChemModel().getMoleculeSet());
        */
        
        SmilesGenerator g = new SmilesGenerator();
        updatedMolecule.setProperty("SMILES",g.createSMILES(updatedMolecule));
        return updatedMolecule;
	}

	public int getResult() {
		return result;
	}
	public void cleanup() {
        jcpep.scaleAndCenterMolecule((ChemModel)jcpep.getChemModel());   
    }
    /*
    public static void editMolecule(IMolecule mol boolean editable, JFrame frame) {
        ISetOfMolecules molecule4edit = null;
        try {
            molecule4edit = dataContainer.containers.getMoleculeForEdit();
        } catch (Exception x) {
            x.printStackTrace();
            return;
        }
        if (molecule4edit != null) { 
            if (jcpDialog == null) {

                jcpModel.getChemModel().setSetOfMolecules(molecule4edit );
                
                jcpDialog = new JChemPaintDialog(frame,false,jcpModel) {
                    private static final long serialVersionUID = -492805673357520991L;

                    @Override
                    public IMolecule okAction() {
                        IMolecule updatedMolecule = super.okAction();
 
                        updatedMolecule.setProperties(dataContainer.getMolecule().getProperties());
                        getDataContainer().setEnabled(true);
                        getDataContainer().setMolecule(updatedMolecule);
                        getActions().allActionsEnable(true);

                        dispose();
                        jcpDialog = null;
                        return updatedMolecule;
                    };
                    
                    @Override
                    public void cancelAction() {
                        super.cancelAction();
                        
                        getDataContainer().setEnabled(true);
                        getActions().allActionsEnable(true);
                        //data.getDataContainer().setEnabled(true);
                        //data.getActions().allActionsEnable(true);
                        dispose();
                        jcpDialog = null;
                        
                    }
                    
                };
                jcpDialog.setTitle("JChemPaint structure diagram editor");
                jcpDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent arg0) {
                        super.windowClosing(arg0);
                        getDataContainer().setEnabled(true);
                        getActions().allActionsEnable(true);
                        jcpDialog = null;
                    }
                });
                //TODO center it 
                //TODO nonmodal
            } else jcpModel.getChemModel().setSetOfMolecules(molecule4edit);
            


            jcpDialog.cleanup();
            jcpDialog.toFront();            
            dataContainer.setEnabled(false);
            getActions().allActionsEnable(false);
            jcpDialog.show();
                  
        }
    }
    */

}
