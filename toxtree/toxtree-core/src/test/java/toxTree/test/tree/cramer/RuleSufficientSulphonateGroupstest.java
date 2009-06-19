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
package toxTree.test.tree.cramer;

import toxTree.core.IDecisionRule;
import toxTree.core.SmilesParserWrapper;
import toxTree.core.SmilesParserWrapper.SMILES_PARSER;
import toxTree.tree.cramer.RuleSufficientSulphonateGroups;

/**
 * Tests {@link RuleSufficientSulphonateGroups}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-22
 */
public class RuleSufficientSulphonateGroupstest extends AbstractRuleTest {


	@Override
	protected IDecisionRule createRule() {
		return new RuleSufficientSulphonateGroups();
	}
	@Override
	/**
	 * Uses CDK parser. There is a problem with DeduceBondOrderTool (loops forever and exits on timeout, with a null molecule)
	 * @throws Exception
	 */
	public void test() throws Exception {

	    SmilesParserWrapper parser = SmilesParserWrapper.getInstance();
	    SMILES_PARSER mode = parser.getParser();
	    parser.setParser(SMILES_PARSER.CDK);
	    Object[][] answer = {
	    		//this is ok
                {"O=C1c4cc(ccc4(NC1=C2Nc3ccc(cc3(C2(=O)))S(=O)(=O)[O-].[Na+]))S(=O)(=O)[O-].[Na+]",new Boolean(true)},  //Blue2 Appendix 1

				//have to recognise sulphonate groups in ionic form
	            {"O=S(=O)([O-])c5cccc(CN(CC)c1ccc(cc1)C(=C2C=CC(C=C2)=[N+](Cc3cccc(c3)S(=O)(=O)[O-])CC)c4ccccc4S(=O)(=O)[O-])c5",new Boolean(true)},  //Blu1 Appendix 1

                            
	            //now a single metabolic reaction is applied , but there is another one 
//	            {"CCN(Cc1cccc(c1)S(=O)(=O)O[Na])c2cccc(c2)C(=C3C=CC(C=C3)=[N+](CC)Cc4cccc(c4)S(=O)(=O)O[Na])c5ccc(O)cc5S6(=O)(=O)([O-]6)",new Boolean(true)},  //Gr3 Appendix 1 
                {"[Na+].CCN(CC=1C=CC=C(C=1)S(=O)(=O)[O-])C=2C=CC=C(C=2)C(=C3C=CC(C=C3)=[N+](CC)CC=4C=CC=C(C=4)S(=O)(=O)[O-])C=5C=CC(O)=CC=5S(=O)(=O)[O-].[Na+]",new Boolean(true)}  //Gr3 Appendix 1                
	            };
	    try {
	    	ruleTest(answer);
	    } catch (Exception x) {
	    	throw new Exception(x);
	    } finally {
	    	System.out.println("restore");
	    	parser.setParser(mode);
	    }

	}	
	public void test_with_OpenBabelParser() throws Exception{
	    SmilesParserWrapper parser = SmilesParserWrapper.getInstance();
	    SMILES_PARSER mode = parser.getParser();
	    parser.setParser(SMILES_PARSER.OPENBABEL);
	    Object[][] answer = {
	    		//this is ok
                {"O=C1c4cc(ccc4(NC1=C2Nc3ccc(cc3(C2(=O)))S(=O)(=O)[O-].[Na+]))S(=O)(=O)[O-].[Na+]",new Boolean(true)},  //Blue2 Appendix 1

				//have to recognise sulphonate groups in ionic form
	            {"O=S(=O)([O-])c5cccc(CN(CC)c1ccc(cc1)C(=C2C=CC(C=C2)=[N+](Cc3cccc(c3)S(=O)(=O)[O-])CC)c4ccccc4S(=O)(=O)[O-])c5",new Boolean(true)},  //Blu1 Appendix 1

                            
	            //now a single metabolic reaction is applied , but there is another one 
//	            {"CCN(Cc1cccc(c1)S(=O)(=O)O[Na])c2cccc(c2)C(=C3C=CC(C=C3)=[N+](CC)Cc4cccc(c4)S(=O)(=O)O[Na])c5ccc(O)cc5S6(=O)(=O)([O-]6)",new Boolean(true)},  //Gr3 Appendix 1 
                {"[Na+].CCN(CC=1C=CC=C(C=1)S(=O)(=O)[O-])C=2C=CC=C(C=2)C(=C3C=CC(C=C3)=[N+](CC)CC=4C=CC=C(C=4)S(=O)(=O)[O-])C=5C=CC(O)=CC=5S(=O)(=O)[O-].[Na+]",new Boolean(true)}  //Gr3 Appendix 1                
	            };
	    try {
	    	ruleTest(answer);
	    } catch (Exception x) {
	    	throw new Exception(x);
	    } finally {
	    	parser.setParser(mode);
	    }

	}

}
