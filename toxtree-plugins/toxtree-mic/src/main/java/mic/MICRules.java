/*
Copyright Istituto Superiore di Sanita' 2009

Contact: rbenigni@iss.it, olga.tcheremenskaia@iss.it, cecilia.bossa@iss.it

ToxMic (Structure Alerts for the in vivo micronucleus assay in rodents) 
ToxMic plug-in is a modified version of the Benigni / Bossa  Toxtree plug-in  for mutagenicity and carcinogenicity implemented by Ideaconsult Ltd. (C) 2005-2008   
Author: Istituto Superiore di Sanita'

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package mic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;
import javax.swing.JOptionPane;


import mic.rules.MICTreeResult;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.UserDefinedTree;

/**
 * 

 *
 */
public class MICRules extends UserDefinedTree implements IDecisionInteractive{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;
    protected boolean residuesIDVisible;
 

        public final static transient String[] c_rules = { 
            "mic.rules.RuleAlertsForMIC", // Rule  1   
            "mic.rules.SA1", //2
            "mic.rules.SA2", //3
            "mic.rules.SA3", //4
            "mic.rules.SA4", //5
            "mic.rules.SA5", //6
            "mic.rules.SA6", //7
            "mic.rules.SA7", //8
            "mic.rules.SA8", //9
            "mic.rules.SA9", //10
            "mic.rules.SA10", //11
            "mic.rules.SA11", //12
            "mic.rules.SA12", //13
            "mic.rules.SA13", //14
            "mic.rules.SA14", //15
            "mic.rules.SA15", //16
            "mic.rules.SA16", //17
            "mic.rules.SA18", //18
            "mic.rules.SA19", //19
            "mic.rules.SA21", //20
            "mic.rules.SA22", //21
            "mic.rules.SA23", //22
            "mic.rules.SA24", //23
            "mic.rules.SA25", //24
            "mic.rules.SA26", //25
            "mic.rules.SA27", //26
            "mic.rules.SA28", //27
            "mic.rules.SA28bis", //28
            "mic.rules.SA28ter", //29
            "mic.rules.SA29", //30
            "mic.rules.SA30", //31
            "mic.rules.SA32", //32
            "mic.rules.SA33", //33
            "mic.rules.SA34", //34
            "mic.rules.SA35", //35
            "mic.rules.SA36", //36
            
           
            "toxTree.tree.rules.RuleVerifyAlertsCounter", //37
          
            };
    private final static transient int c_transitions[][] ={
        //{if no go to, if yes go to, assign if no, assign if yes}
        {2,2,0,0}, //Rule 1  1
        {3,3,0,0}, //2
        {4,4,0,0}, //3
        {5,5,0,0}, //4
        {6,6,0,0}, //5
        {7,7,0,0}, //6
        {8,8,0,0}, //7
        {9,9,0,0}, //8
        {10,10,0,0}, //9
        {11,11,0,0}, //10
        {12,12,0,0}, //11
        {13,13,0,0}, //12
        {14,14,0,0}, //13
        {15,15,0,0}, //14
        {16,16,0,0}, //15
        {17,17,0,0}, //16
        {18,18,0,0}, //17
        {19,19,0,0}, //18
        {20,20,0,0}, //19
        {21,21,0,0}, //20
        {22,22,0,0}, //21
        {23,23,0,0}, //22
        {24,24,0,0}, //23
        {25,25,0,0}, //24
        {26,26,0,0}, //25
        {27,27,0,0}, //26
        {28,28,0,0}, //27
        {29,29,0,0}, //28
        {30,30,0,0}, //29
        {31,31,0,0}, //30
        {32,32,0,0}, //31
        {33,33,0,0}, //32
        {34,34,0,0}, //33
        {35,35,0,0}, //34
        {36,36,0,0}, //35
        {37,37,0,0}, //35
        {0,0,2,1}, //42
       
        
    };	
	private final static transient String c_categories[] ={
		"mic.categories.micronucleusClass1",
		"mic.categories.micronucleusClass2",
		
	};
	/**
	 * 
	 */
	public MICRules() throws DecisionMethodException {
		super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
     ;
		setChanged();
		notifyObservers();
		setTitle("Structure Alerts for the in vivo micronucleus assay in rodents");
		setExplanation(
				"ToxMIC-ISS plug-in allows the identification of Structure Alerts for the in vivo micronucleus assay. These Structure Alerts provide a coarse-grain filter for a preliminary screening of potentially in vivo mutagens." 
				);
        setPriority(20);
        setFalseIfRuleNotImplemented(false);};
        
    	public DescriptorSpecification getSpecification() {
            return new DescriptorSpecification(
                    "http://toxtree.sourceforge.net/mic.html",
                    getTitle(),
                    this.getClass().getName(),                
                    "Toxtree plugin");
    	}

	/* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
        public void addPropertyChangeListener(PropertyChangeListener l) {
            if (changes == null) 		changes = new PropertyChangeSupport(this);        
            changes.addPropertyChangeListener(l);
    		for (int i=0; i < rules.size(); i++) 
    			if (rules.getRule(i) != null)
    				rules.getRule(i).addPropertyChangeListener(l);
        }
        /* (non-Javadoc)
         * @see toxTree.core.IDecisionMethod#removePropertyChangeListener(java.beans.PropertyChangeListener)
         */
        public void removePropertyChangeListener(PropertyChangeListener l) {
            if (changes == null) {
    	        changes.removePropertyChangeListener(l);
    	        for (int i=0; i < rules.size(); i++) 
    	        	if (rules.getRule(i) != null)
    	        		rules.getRule(i).removePropertyChangeListener(l);
            }	
        }	
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return getName();
        }
    	


    	/* (non-Javadoc)
         * @see toxTree.core.IDecisionMethod#getName()
         */
        public String getName() {
            return name;
        }
        /* (non-Javadoc)
         * @see toxTree.core.IDecisionMethod#setName(java.lang.String)
         */
        public void setName(String value) {
           name = value;

        }
    	public StringBuffer explainRules(IDecisionResult result,boolean verbose) throws DecisionMethodException{
    		try {
    			StringBuffer b = result.explain(verbose);
    			return b;
    		} catch (DecisionResultException x) {
    			throw new DecisionMethodException(x);
    		}
    	}


    	@Override
    	public IDecisionResult createDecisionResult() {
    		IDecisionResult result =  new MICTreeResult();
    		result.setDecisionMethod(this);
    		return result;
    	}

    	public boolean isResiduesIDVisible() {
    		return residuesIDVisible;
    	}


    	public void setResiduesIDVisible(boolean residuesIDVisible) {
    		this.residuesIDVisible = residuesIDVisible;
    		for (int i=0;i< rules.size(); i++) {
    			rules.getRule(i).hideResiduesID(!residuesIDVisible);
    		}
    	}
    	public void setEditable(boolean value) {
    		editable = value;
    		for (int i=0;i<rules.size();i++)
    			rules.getRule(i).setEditable(value);
    	}


        @Override
        public void setParameters(IAtomContainer mol) {
            if (getInteractive()) {
                JComponent c = optionsPanel(mol);
                if (c != null)
                    JOptionPane.showMessageDialog(null,c,getTitle(),JOptionPane.PLAIN_MESSAGE);
            } 
        }
    }


