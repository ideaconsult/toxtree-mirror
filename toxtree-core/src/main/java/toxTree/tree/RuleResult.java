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

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionRule;

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
	public static String ruleURL = "http://localhost/rule/";
	public static String categoryURL = "http://localhost/category/";
	public static String alertURL = "http://localhost/alerts/";
	protected IDecisionRule rule = null;
	protected boolean result = false;
	protected IDecisionCategory category = null;
	protected transient IAtomContainer molecule = null;
	protected boolean silent = false;
	/**
	 * 
	 */
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
		StringBuffer b = new StringBuffer();
		if (!silent) 
			if (verbose) {
				b.append(String.format("<a href=\"%s%s\"><img src='%s' border='0' alt='Hilight structure alert' title='Hilight structure alert'></a>",alertURL,rule.getTitle(),this.getClass().getResource("/toxTree/ui/tree/images/find.png").toString()));
				b.append("&nbsp;");

				b.append(String.format("<a href=\"%s%s\">%s</a>",ruleURL,rule.getTitle(),rule.toString()));
				b.append("&nbsp;");
				b.append(String.format("<span style='color:%s;position:relative;font-weight: bold;'>%s</span>", result?"green":"red",result?"Yes":"No"));
				b.append("&nbsp;");
				if (category != null) {
					b.append(String.format("Class&nbsp;<span style='color:orange'><a href=\"%s%s\">%s</a></span>",categoryURL,category.getID(),category.toString()));
				} else b.append("&nbsp;");
							
				if ((molecule != null) && (molecule.getID() != null)) {
					b.append("\t");
					b.append(molecule.getID());
				} else b.append("\t");
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
}