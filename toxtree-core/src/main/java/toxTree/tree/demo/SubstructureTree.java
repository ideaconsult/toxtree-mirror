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

*//**
 * <b>Filename</b> SubstructureTree.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-5
 * <b>Project</b> toxTree
 */
package toxTree.tree.demo;

import java.util.logging.Level;

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionCategory;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNode;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.categories.DefaultClass1;
import toxTree.tree.categories.DefaultClass2;
import toxTree.tree.rules.RuleAnySubstructure;


/**
 *  A demo decision tree consisting of a single decision rule , 
 *  which checks for the presence of benzene ring. 
 *  Serves as an illustration of the concept of user-defined tree.
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-5
 */
public class SubstructureTree extends UserDefinedTree {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4532635398756503555L;

    /**
     * 
     */
    public SubstructureTree() throws DecisionMethodException {
        super(new CategoriesList(),new DecisionNodesList());
        IDecisionCategory c1 = new DefaultClass1("Doesn't contain substructure",1);
        IDecisionCategory c2 = new DefaultClass2("Contains substructure",2);

        categories.add(c1);
        categories.add(c2);
        
        
        IAtomContainer m = MoleculeFactory.makeBenzene();
        try {
        	AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(m);
        	CDKHueckelAromaticityDetector.detectAromaticity(m);
        } catch (Exception x) {
        	logger.log(Level.SEVERE,x.getMessage(),x);
        }
        m.setID("benzene");
        
        /*
        IAtomContainer m = MoleculeFactory.makeAlkane(6);
        m.setID("hexane");
        */
		RuleAnySubstructure rs = new RuleAnySubstructure();
		rs.addSubstructure(m);
		rs.setID("1");
		rs.setTitle("Contains substructure");
		rs.setExampleMolecule(MoleculeFactory.makeAlkane(4),false);
		rs.setExampleMolecule(MoleculeFactory.makeBenzene(),true);
		
		DecisionNodesList nodes = (DecisionNodesList) rules;
        nodes.add(new DecisionNode(rs,null,null,c1,c2));
        
        
		setTitle("Demo substructure tree");
        setExplanation("DEMO decision tree with a single substructure rule.");
    }


}

