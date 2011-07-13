/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

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
package toxtree.plugins.verhaar2.rules;


import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Contain one of the following structural entities:
 * <pre>
 *          {"acid anhydrides","[*]C(=O)OC(=O)[*]"},
            {"lactones",""},
            {"acid halides","[*]C(=O)Cl"},
            {"carbamohalides","[*]NC(=O)Cl"},
            {"ketenes","[*]C=C=O"},
            {"aldehydes","[*]C(=O)[H]"},
            {"isocyanates","[*]N=C=O"},
            {"thiocyanates","[*]SC#N"},
            {"isothiocyanates","[*]N=C=S"},
            {"disulphides","[*]SS[*]"},
            {"sulphonic esters","[*]S(=O)(=O)O[*]"},
            {"sulphiric esters","[H]OS(=O)(=O)O[*]"},
            {"cyclic sulphonic esters",""},
            {"cyclic sulphuric esters",""},
            {"alpha haloethers","[*]OC(Cl)[*]"},
            {"alpha haloethers","[*]OC(F)[*]"},
            {"alpha haloethers","[*]OC(Br)[*]"},
            {"alpha haloethers","[*]OC(I)[*]"},
            {"beta haloethers","[*]OCC(Cl)[*]"},
            {"beta haloethers","[*]OCC(Br)[*]"},
            {"beta haloethers","[*]OCC(F)[*]"},
            {"beta haloethers","[*]OCC(I)[*]"},
            {"nitrogen mustards","[*]NCC(Cl)[*]"},
            {"nitrogen mustards","[*]NCC(F)[*]"},
            {"nitrogen mustards","[*]NCC(Br)[*]"},
            {"nitrogen mustards","[*]NCC(I)[*]"},
            {"sulphur mustards","[*]SCC(Cl)[*]"},
            {"sulphur mustards","[*]SCC(F)[*]"},
            {"sulphur mustards","[*]SCC(Br)[*]"},
            {"sulphur mustards","[*]SCC(I)[*]"}

 * </pre>
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule38 extends RuleSMARTSSubstructureAmbit {
	protected String[][] smarts = {
			{"acid anhydrides","C(=O)OC(=O)"},
			{"lactones","[C;R][O;R][C;R](=O)[O;R]"},
			{"acid halides","C(=O)Cl"},
			{"carbamohalides","NC(=O)Cl"},
			{"ketenes","C=C=O"},
			{"aldehydes","[CX3H1](=O)"},
			{"aldehyde","[CX3H2](=O)"},
			{"isocyanates","N=C=O"},
			{"thiocyanates","SC#N"},
			{"isothiocyanates","N=C=S"},
			{"disulphides","[S;X2][S;X2]"},
			{"sulphonic esters","S(=O)(=O)[OX2H0]"},
			{"sulphiric esters","[O;H1]S(=O)(=O)[OX2H]"},
			{"cyclic sulphonic esters","[C;R][S;R]([O;R])(=O)(=O)"},
			{"cyclic sulphuric esters","[O;R][S;R]([O;R])(=O)(=O)"},
			{"alpha haloethers","[O;!R]C([Cl,Br,F,I])[!$([Cl,Br,F,I])]"},
			{"beta haloethers","[O;!R]CC([Cl,Br,F,I])[!$([Cl,Br,F,I])]"},
			{"nitrogen mustards","[N;!R][C;!R][C;!R]([Cl,F,Br,I])"},
			{"sulphur mustards","[S;!R][C;!R][C;!R]([Cl,F,Br,I])"},

	};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -673417256329929944L;

	public Rule38() {
		super();
		id = "3.8";
		setTitle("Contain one of the following structural entities");
		explanation = new StringBuffer();

		
		explanation.append("<html><ul>");
		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
			explanation.append(String.format("<li>%s SMARTS: %s",smart[0],smart[1]));

		} catch (Exception x) {
			x.printStackTrace();
		}
		examples[1] = "O=C(NC(C)CC)Cl";
		examples[0] = "CCCCCCCCCCCC";
		editable = false;
	}


	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	public boolean isImplemented() {

		return true;
	}
}
