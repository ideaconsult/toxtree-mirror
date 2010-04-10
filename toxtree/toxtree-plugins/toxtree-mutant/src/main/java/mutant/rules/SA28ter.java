package mutant.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

/**
 * Aromatic N-acyl amine.
<pre>
[a;!$(a(a[A;!#1])(a[A;!#1]));!$(aa[CX3](=O)[OX2H1]);!$(aa[SX4](=[OX1])(=[OX1])([O]));!$(aaa[SX4](=[OX1])(=[OX1])([O]));!$(aaaa[SX4](=[OX1])(=[OX1])([O]));!$(aaaaa[SX4](=[OX1])(=[OX1])([O]));!$(aaaaaa[SX4](=[OX1])(=[OX1])([O]))]!@[$([NX3;v3]([#1,CH3])C(=O)([#1,CH3]))]
</pre>
 * @author Nina Jeliazkova
 *
 */
public class SA28ter extends StructureAlertCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6392891031735444965L;
	public static String SA28ter_title = "Aromatic N-acyl amine";
	
    public static String[][] amines = {
        {SA28ter_title,"[NX3;v3]([#1,CH3])C(=O)([#1,CH3])"}
    };  
    
    public SA28ter() {
        super();
        try {
        	setContainsAllSubstructures(true);
            StringBuffer b = new StringBuffer();
            b.append("[a");
            for (int i=0; i < SA28.exclusion_rules.length;i++) {
                b.append(";!$(");
                b.append(SA28.exclusion_rules[i][1]);
                b.append(")");
            }
            b.append("]!@[");
            for (int i=0; i < amines.length;i++) {
                if (i>0) b.append(',');
                b.append("$(");
                b.append(amines[i][1]);
                b.append(")");
            } 
            b.append("]");
            addSubstructure(SA28ter_title,b.toString());  
            setID("SA28ter");
            setTitle(SA28ter_title);
            
            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA28ter_title);
            e.append("<br>");
            e.append("However:");
            e.append("<ul>");
            e.append("<li>");
            e.append("Aromatic amino groups with ortho-disubstitution or with a carboxylic acid substituent in ortho position should be excluded.");
            
            e.append("<li>");
            e.append("If a sulfonic acid group (-SO3H) is present on the ring that contains also the amino group, the substance should be excluded from the alert.");
            
            e.append("<li>");
            e.append("see also the two examples of exceptions for the nitro alert (alert 27)");
            
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "CCOC=1C=C(C)C(NC(C)=O)=C(C)C=1(N)";
            examples[1] = "CCOC=1C=CC(=CC=1(N))NC(C)=O";   
            editable = false;
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }	
	protected boolean isAPossibleHit(IAtomContainer mol, IAtomContainer processedObject) throws DecisionMethodException  {
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		return 
		MolecularFormulaManipulator.containsElement(formula,formula.getBuilder().newElement("N"));
	}         
}
