/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.ui.molecule.PropertyPanel;
import ambit2.ui.editors.Panel2D;

public class PropertyEditor extends JPanel{
    /**
     * 
     */
    private static final long serialVersionUID = 5076428184639882491L;
    protected OptionsPanel optionsPanel;
    public PropertyEditor(IAtomContainer mol, JComponent options) {
        super(new BorderLayout());
        Panel2D panel2d = new Panel2D();
        panel2d.setBorder(BorderFactory.createEtchedBorder());
        panel2d.setPreferredSize(new Dimension(200,200));
        
        IAtomContainer c = null;
        if (mol != null) {
	        try {
	            c = (IAtomContainer)mol.clone();
	            c = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(c);    
	            c.setProperties(mol.getProperties());
	        } catch (Exception x) {
	            c = mol;
	        }
	        panel2d.setAtomContainer(c,true);
        	add(panel2d,BorderLayout.CENTER);
        	
            PropertyPanel p = new PropertyPanel();
            p.setBorder(BorderFactory.createEtchedBorder());
            p.setAtomContainer(c);
            add(p,BorderLayout.WEST);
        	
        }
        
        if (options != null) {
        	if (options instanceof OptionsPanel)
        		optionsPanel = (OptionsPanel) options;
            add(options,BorderLayout.NORTH);
        }
    }
    public String getPropertyValue() {
        return optionsPanel.getPropertyValue();
    }
    public boolean isSilent() {
        return optionsPanel.isSilent();
    }
}

