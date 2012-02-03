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
package toxTree.tree;

import java.io.Serializable;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionRule;
import ambit2.base.interfaces.IProcessor;

/**
 * Stores the result of {@link IDecisionRule} application,
 * which contains the rule applied, the boolean result and the category assigned (if any). 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-2
 */
public class RuleResult implements Serializable {
	
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7844039700306791740L;
	public static final String prefix = "http://localhost" ;
	public static final String ruleURL = String.format("%s/rule/",prefix);
	public static final String categoryURL = String.format("%s/category/",prefix);
	public static final String alertURL = String.format("%s?alerts=",prefix);
	public static final String resultURL = String.format("%s?parameters=",prefix);
	
	protected IDecisionRule rule = null;
	protected boolean result = false;
	protected IDecisionCategory category = null;
	protected transient IAtomContainer molecule = null;
	protected boolean silent = false;
	
	protected boolean web = false;
	 
	public boolean isWeb() {
		return web;
	}
	public void setWeb(boolean web) {
		this.web = web;
	}
	public RuleResult() {
		super();
	}
	public RuleResult(IDecisionRule rule, boolean result) {
		super();
		this.rule = rule;
		this.result = result;
	}
	public RuleResult(IDecisionRule rule, boolean result, IDecisionCategory category) {
		super();
		this.rule = rule;
		this.result = result;
		this.category = category;
	}	
	
	/**
	 * @return Returns the result.
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * @param result The result to set.
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * @return Returns the category.
	 */
	public synchronized IDecisionCategory getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
	 */
	public synchronized void setCategory(IDecisionCategory category) {
		this.category = category;
	}
	
	/**
	 * @return Returns the rule.
	 */
	public synchronized IDecisionRule getRule() {
		return rule;
	}
	/**
	 * @param rule The rule to set.
	 */
	public synchronized void setRule(IDecisionRule rule) {
		this.rule = rule;
	}
	public StringBuffer explain(boolean verbose) {
		return explain(verbose,-1);
	}
	protected static final String wwwAlertFormat = "<a href='#' onClick=\"changeImage('compound_uri','%s/hilight?parameters=%s')\">%s.%s</a>";
	public StringBuffer explain(boolean verbose,int ruleIndex) {
		StringBuffer b = new StringBuffer();
		String space = web?" ":"&nbsp;";
		if (!silent) 
			if (verbose) {
				if (web)  {
					if (result && (rule.isImplemented()) && (rule.getSelector()!=null))
						b.append(String.format(wwwAlertFormat,"",rule.getID(),rule.getID(),rule.getTitle()));
					else
						b.append(rule.toString());
					b.append(space);
					if (web)
						b.append(result?"Yes":"No");
					else	
						b.append(String.format("<span style='color:%s;position:relative;font-weight: bold;'>%s</span>", result?"green":"red",result?"Yes":"No"));
					b.append(space);		
					if (category != null) {
						if (web)
							b.append(String.format("Class %s",category.toString()));
						else
							b.append(String.format("Class&nbsp;<span style='color:black'>%s</span>",category.toString()));
					} else b.append(space);					
				} else {
					b.append(String.format("<a href=\"%s%s\" title='Show rule'><img src='%s' border='0' alt='Show rule' title='Show rule'></a>",
							ruleURL,rule.getTitle(),this.getClass().getResource("/toxTree/ui/tree/images/find.png").toString()));
					b.append(space);
					
					if (result && (rule.isImplemented()) && (rule.getSelector()!=null))
						if (ruleIndex<0)
							b.append(String.format("<a href=\"%s%s\"  title='Hilight structure alert'>%s</a>",alertURL,rule.getTitle(),rule.toString()));
						else
							b.append(String.format("<a href=\"%s%d\"  title='Hilight structure alert'>%s</a>",resultURL,ruleIndex,rule.toString()));
					else
						b.append(rule.toString());
					b.append(space);
			
					if (web)
						b.append(String.format(result?"Yes":"No"));
					else {	
						b.append(String.format("<span style='color:%s;position:relative;font-weight: bold;'>%s</span>", result?"green":"red",result?"Yes":"No"));
						b.append(space);
					}
					if (category != null) {
						if (web)
							b.append(String.format(category.toString()));
						else
							b.append(String.format("Class&nbsp;<span style='color:orange'><a href=\"%s%s\">%s</a></span>",categoryURL,category.getID(),category.toString()));
					} else b.append(space);
								
					if ((molecule != null) && (molecule.getID() != null)) {
						b.append("\t");
						b.append(molecule.getID());
					} else b.append("\t");
				}
			} else {
				b.append(rule.getID());
				if (result) b.append("Y");
				else b.append("N");
			}
		return b;
	}
	/**
	 * @return Returns the molecule.
	 */
	public synchronized IAtomContainer getMolecule() {
		return molecule;
	}
	/**
	 * @param molecule The molecule to set.
	 */
	public synchronized void setMolecule(IAtomContainer molecule) {
		this.molecule = molecule;
	}
	/**
	 * @return Returns the silent.
	 */
	public synchronized boolean isSilent() {
		return silent;
	}
	/**
	 * @param silent The silent to set.
	 */
	public synchronized void setSilent(boolean silent) {
		this.silent = silent;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			RuleResult r = (RuleResult) obj;
			return (result == r.result) &&
				   (rule.equals(r.rule)) && 
				   (
				   	((category == null) && (r.category==null)) 
					|| category.equals(r.category));	
		} catch (Exception x) {
			return false;
		}
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return explain(true).toString();
	}
	IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
		return getRule().getSelector();
	}
}
