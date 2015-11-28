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

import javax.swing.AbstractAction;
import javax.swing.Icon;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * An abstract action to perform smth on a molecule
 * 
 * @author Nina Jeliazkova
 * 
 */
public abstract class AbstractMoleculeAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = -5270491315560823241L;
    protected IAtomContainer molecule;

    public AbstractMoleculeAction(IAtomContainer molecule) {
	super("Edit molecule");
	this.molecule = molecule;
    }

    public AbstractMoleculeAction(IAtomContainer molecule, String arg0) {
	super(arg0);
	this.molecule = molecule;
    }

    public AbstractMoleculeAction(IAtomContainer molecule, String arg0, Icon arg1) {
	super(arg0, arg1);
	this.molecule = molecule;
    }

    public IAtomContainer getMolecule() {
	return molecule;
    }

    public void setMolecule(IAtomContainer molecule) {
	this.molecule = molecule;
    }

}
