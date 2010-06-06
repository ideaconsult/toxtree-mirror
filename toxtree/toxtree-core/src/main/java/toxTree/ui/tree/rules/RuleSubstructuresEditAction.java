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
package toxTree.ui.tree.rules;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.core.IRuleSubstructures;
import ambit2.core.data.MoleculeTools;

/**
 * substructure rule
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-23
 */
public class RuleSubstructuresEditAction extends RuleMoleculeEditAction {
	protected boolean createNewMolecule = false;
	protected int index = -1;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -9152027843437776751L;

	/**
	 * @param molecule
	 */
	public RuleSubstructuresEditAction(IMolecule molecule) {
		this(molecule,"New substructures");

	}

	/**
	 * @param molecule
	 * @param arg0
	 */
	public RuleSubstructuresEditAction(IMolecule molecule, String arg0) {
		this(molecule, arg0,null);
	}

	/**
	 * @param molecule
	 * @param arg0
	 * @param arg1
	 */
	public RuleSubstructuresEditAction(IMolecule molecule, String arg0, Icon arg1) {
		super(molecule, arg0, arg1);
		putValue(SHORT_DESCRIPTION, "Draw new substructure");
		setModal(true);
	}
	/* (non-Javadoc)
	 * @see toxTree.ui.molecule.MoleculeEditAction#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
			if (createNewMolecule) {
				IMolecule m = new org.openscience.cdk.Molecule();
				m.addAtom(MoleculeTools.newAtom(DefaultChemObjectBuilder.getInstance(),Elements.CARBON));
				setMolecule(m);
			}
			super.actionPerformed(arg0);
			
			
	}
    @Override
    protected void updateMolecule(IMolecule mol) {
        super.updateMolecule(mol);
        if (rule instanceof IRuleSubstructures)  {
            IAtomContainer a = getMolecule();
            if (a != null) {
                if (createNewMolecule) {
                    getMolecule().setID(Integer.toString(((IRuleSubstructures) rule).getSubstructuresCount()+1));
                    ((IRuleSubstructures) rule).addSubstructure(getMolecule());
                } else {
                    if (a instanceof QueryAtomContainer) {
                        JOptionPane.showMessageDialog(null,"Edit not allowed!");
                    } else {
                        getMolecule().setID(Integer.toString(index+1));
                        ((IRuleSubstructures) rule).setSubstructure(index,getMolecule());
                    }   
                }   
                setMolecule(null);
            }
        }
    }
	public void setAtomContainer(IAtomContainer atomContainer, int index) {
		try {
			this.index = index;
			molecules = getMoleculeForEdit(atomContainer);
		} catch (Exception x) {
			x.printStackTrace();
			molecules = null;
		}
	}

	public boolean isCreateNewMolecule() {
		return createNewMolecule;
	}

	public void setCreateNewMolecule(boolean createNewMolecule) {
		this.createNewMolecule = createNewMolecule;
	}
}
