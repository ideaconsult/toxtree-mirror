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

package toxTree.tree.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;

public abstract class RuleRings extends RuleSubstructures {

    public RuleRings() {
        super();
    }

    protected IRingSet hasRingsToProcess(IAtomContainer  mol) throws DecisionMethodException {
        MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
        if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
        return mf.getRingset();
    }

}
