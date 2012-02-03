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

import toxTree.exceptions.DecisionMethodException;

public class RuleInitAlertCounter extends RuleVerifyProperty {
	public static String ALERTSCounter = "ALERTSCounter";
	public static String ALERTSMessage= "Initialize alerts counter";
	/**
	 *
	 */
	private static final long serialVersionUID = 6991889446709051896L;

	public RuleInitAlertCounter() {
		this(ALERTSCounter,"",">",0);
	}

	public RuleInitAlertCounter(String propertyName, String units,
			String condition, double value) {
		super(propertyName, units, condition, value);
		setID("Alerts");
		setTitle("Alerts");
		setExplanation(ALERTSMessage);
		setInteractive(false);

	}
	/**
	 * Assings value returned by {@link #getProperty} to molecule property with name given by {@link #getPropertyName()}. Always returns true;
	 */
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (mol == null) throw new DecisionMethodException("Molecule not assigned!");
		mol.setProperty(getPropertyName(),getProperty());
		return true;
	}
	@Override
	public boolean isImplemented() {
		return true;
	}
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append("Sets \"");
		b.append(RuleVerifyAlertsCounter.ALERTSCounter);
		b.append("\" property of the verified molecule to zero.");
		return b.toString();
	}
	/*
	@Override
	public JComponent optionsPanel(IAtomContainer atomContainer) {
		return null;
	}
	*/
}


