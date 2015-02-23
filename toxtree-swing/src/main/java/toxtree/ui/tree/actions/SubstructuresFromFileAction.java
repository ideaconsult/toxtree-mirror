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

package toxtree.ui.tree.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionRule;
import toxTree.core.IRuleSubstructures;
import toxTree.data.MoleculesIterator;
import toxTree.exceptions.ToxTreeIOException;
import toxTree.io.MolFileFilter;
import toxtree.data.ToxTreeActions;
import ambit2.core.helper.CDKHueckelAromaticityDetector;

public class SubstructuresFromFileAction extends AbstractRuleAction {

    /**
     * 
     */
    private static final long serialVersionUID = 7289392201061470542L;

    public SubstructuresFromFileAction(IDecisionRule rule) {
        this(rule,"Substructures from file");
    }

    public SubstructuresFromFileAction(IDecisionRule rule, String arg0) {
        this(rule, arg0,null);
    }

    public SubstructuresFromFileAction(IDecisionRule rule, String arg0,
            Icon arg1) {
        super(rule, arg0, arg1);
    }

    public void actionPerformed(ActionEvent arg0) {
        if (rule instanceof IRuleSubstructures) {
            IRuleSubstructures r = (IRuleSubstructures) rule;

            JFrame frame = getParentFrame();
            File file = ToxTreeActions.selectFile(frame, 
                    MolFileFilter.supported_extensions,MolFileFilter.supported_exts_description
                    , true);
            if (file != null) {
                MoleculesIterator mi = new MoleculesIterator();
                try {
                    List c = mi.openFile(file);
                    for (int i=0; i < c.size(); i++) 
                    	if (c.get(i) instanceof IAtomContainer) {
                    		IAtomContainer ac = (IAtomContainer) c.get(i);
	                        Object title = ac.getProperty(CDKConstants.TITLE);
	                        if ((title!=null) && (!"".equals(title)))
	                        	ac.setID(title.toString());
	                        else
	                        	ac.setID(Integer.toString(i+1));
	                        try {
	                        	AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
	                            CDKHueckelAromaticityDetector.detectAromaticity(ac);
	                        } catch (CDKException x) {
	                            JOptionPane.showMessageDialog(frame,x.getMessage());
	                        }    
	                        r.addSubstructure(ac);    
                    	}
                    mi.clear();
                    mi = null;
                     
                } catch (ToxTreeIOException x) {
                    JOptionPane.showMessageDialog(frame,x.getMessage());
                }
                /*
                if (o != null) {
                    r.addSubstructure((IAtomContainer)o);
                } 
                */   
            }
            
        }

    }

}
