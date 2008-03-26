/* QSAR6Applicable.java
 * Author: Nina Jeliazkova
 * Date: Feb 17, 2008 
 * Revision: 0.1 
 * 
 * Copyright (C) 2005-2008  Nina Jeliazkova
 * 
 * Contact: nina@acad.bg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 */

package mutant.rules;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.MFAnalyser;

import toxTree.core.IDecisionInteractive;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.ApplyRuleOptions;
import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;

public class QSAR6Applicable extends RuleSMARTSubstructureCDK implements IDecisionInteractive{
    /**
     * 
     */
    private static final long serialVersionUID = 1840810866408347284L;
    protected static String aromatic_amine="For QSAR6 calculation of mutagenicity of aromatic amines, molecules that contain also NA_27 (aromatic nitro), should be excluded.";
    protected ApplyRuleOptions options = new ApplyRuleOptions(false,false);
    
    public QSAR6Applicable() throws Exception {

            setContainsAllSubstructures(true);
            
            StringBuffer b = new StringBuffer();
            StringBuffer a = new StringBuffer();
            a.append("c");
            
            b.append("[");
            b.append("c");
            
            for (int i=0; i < 6; i++) {
                a.append("c");

                b.append(";");
                b.append("!$(");
                b.append(a.toString());
                b.append("[N+]([O-])=O");
                b.append(")");
            }
            b.append("]");
            b.append("[");
            b.append("NX3v3");

            b.append("]");            
            //System.out.println(b.toString());
            addSubstructure(aromatic_amine,b.toString());
            /*
[c;!$(cc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(cc[n,o,s,p]);!$(ccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(ccc[n,o,s,p]);!$(cccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(cccc[n,o,s,p]);!$(ccccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(ccccc[n,o,s,p]);!$(cccccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(cccccc[n,o,s,p]);!$(ccccccc[SX4](=[OX1])(=[OX1])([O-,OX2H1]));!$(ccccccc[n,o,s,p])][NX3v3;!$(N[CX4H2][OX2H1]);!$(N(CC[Cl,Br,F,I])(CC[Cl,Br,F,I]));!$(NN);!$(N([#1,C])N=O);!$(NN=N);!$(N[OX2H1])]
            */
            setID("QSAR6 applicable?");
            setTitle("Aromatic amines without nitro group");
            StringBuffer e = new StringBuffer();
            e.append(aromatic_amine);
/**
 
 */
            setExplanation(e.toString());
            examples[0] = "c1ccc(N)cc1[N+](=O)[O-]";
            examples[1] = "c1ccccc1N"; 

    }
    @Override
    protected boolean isAPossibleHit(IAtomContainer mol, IAtomContainer processedObject) throws DecisionMethodException  {
        MFAnalyser mfa = new MFAnalyser(mol);
        
        Map<String,Integer> elements = mfa.getFormulaHashtable();
        if (elements.containsKey("N")) {
            return true;
        }
        return false;
    }
    
	public JComponent optionsPanel(IAtomContainer atomContainer) {
		return options.optionsPanel("Skip this rule?","If yes, the answer of the rule will always be YES, regardless of the structure.",  getID(), atomContainer);
		/*
		JPanel p = new JPanel();
		JCheckBox b = new JCheckBox(new AbstractAction("Skip this rule") {
			public void actionPerformed(ActionEvent e) {
	            JCheckBox cb = (JCheckBox)e.getSource();
	            setSkipRule(cb.isSelected());
			}
		});		
		b.setToolTipText("If checked, the answer of the rule will always be YES (might be usefull for testing)");
		p.add(b);
		b = new JCheckBox(new AbstractAction("Don't ask anymore!") {
			public void actionPerformed(ActionEvent e) {
	            JCheckBox cb = (JCheckBox)e.getSource();
	            setInteractive(cb.isSelected());
			}
		});
		b.setToolTipText("Don't bring this question while applying the rule.");
		p.add(b);
		return p;
		*/
	}

	public void setInteractive(boolean value) {
		options.setInteractive(value);
		
	}
	public void setSkipRule(boolean value) {
		options.setAnswer(value);
		
	}
	public boolean isSkipRule() {
		return options.isAnswer();
	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (getInteractive()) 
			JOptionPane.showMessageDialog(null, optionsPanel(mol),getTitle(),JOptionPane.PLAIN_MESSAGE,null);
		if (isSkipRule()) {
			logger.info("Skip the rule");
			return true;			
		} else return super.verifyRule(mol);
	}
	public boolean getInteractive() {
		return options.getInteractive();
	}
}    