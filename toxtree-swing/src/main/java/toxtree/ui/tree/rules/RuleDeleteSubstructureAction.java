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

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import org.openscience.cdk.interfaces.IMolecule;

import toxTree.tree.rules.RuleSubstructures;

public class RuleDeleteSubstructureAction extends RuleMoleculeEditAction {
    /**
     * 
     */
    private static final long serialVersionUID = -4045206486346175089L;
    protected int selectedIndex = -1;
    public RuleDeleteSubstructureAction(IMolecule molecule) {
        this(molecule,"Delete");
    }

    public RuleDeleteSubstructureAction(IMolecule molecule, String arg0) {
        this(molecule, arg0,null);
    }

    public RuleDeleteSubstructureAction(IMolecule molecule, String arg0,
            Icon arg1) {
        super(molecule, arg0, arg1);
    }
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (selectedIndex > -1) {
            ((RuleSubstructures)getRule()).removeSubstructure(selectedIndex);
        }
    }

    public synchronized int getSelectedIndex() {
        return selectedIndex;
    }

    public synchronized void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
}
