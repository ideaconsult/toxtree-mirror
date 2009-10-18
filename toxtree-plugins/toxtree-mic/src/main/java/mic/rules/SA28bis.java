package mic.rules;

import java.util.Map;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.MFAnalyser;

import toxTree.exceptions.DecisionMethodException;
import ambit2.smarts.query.SMARTSException;

/**
 * Aromatic mono- and dialkylamine (with exceptions). TODO A �SO3H sub-rule
 * should be implemented
 * 
 * @author Nina Jeliazkova
 * 
 */
public class SA28bis extends StructureAlertCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3084959606481108406L;
	public static String SA28bis_title = "Aromatic mono- and dialkylamine";

	public static String[][] amines = {
			{ SA28bis_title, "[NX3;v3]([#1,CH3])([CH3])" },
			{ SA28bis_title, "[NX3;v3]([#1,CH3])([CH2][CH3])" },
			{ SA28bis_title, "[NX3;v3]([CH2][CH3])([CH2][CH3])" },

	};

	/*
	 * public static Object[][] exclusion_rules = {
	 * {"Ortho-disubstitution","a(a[A;!#1])(a[A;!#1])"
	 * ,"[H]C=1C([H])=C(C)C(=C(C)C=1([H]))N([H])OC=O","",new Boolean(false)},
	 * {"Carboxylic acid substituent at ortho position"
	 * ,"aa[CX3](=O)[OX2H1]","O=C(O)C1=CC=CC=C1(N)","",new Boolean(false)},
	 * {"-SO3H on the same ring"
	 * ,"aa[SX4](=[OX1])(=[OX1])([O-])","NC=1C=CC=CC=1S(=O)(=O)[O-]","",new
	 * Boolean(false)},
	 * {"-SO3H on the same ring","aaa[SX4](=[OX1])(=[OX1])([O-])"
	 * ,"NC=1C=CC=C(C=1)S(=O)(=O)[O-]","",new Boolean(false)},
	 * {"-SO3H on the same ring"
	 * ,"aaaa[SX4](=[OX1])(=[OX1])([O-])","O=S(=O)([O-])C1=CC=C(N)C=C1","",new
	 * Boolean(false)},
	 * {"-SO3H on the same ring","aaaaa[SX4](=[OX1])(=[OX1])([O-])","","",new
	 * Boolean(false)},
	 * {"-SO3H on the same ring","aaaaaa[SX4](=[OX1])(=[OX1])([O-])","","",new
	 * Boolean(false)}, };
	 */
	public SA28bis() throws SMARTSException {
		super();
		setContainsAllSubstructures(false);
		StringBuffer b = new StringBuffer();
		b.append("[a");
		for (int i = 0; i < SA28.exclusion_rules.length; i++) {
			b.append(";!$(");
			b.append(SA28.exclusion_rules[i][1]);
			b.append(")");
		}
		b.append("]!@[");
		for (int i = 0; i < amines.length; i++) {
			if (i > 0)
				b.append(',');
			b.append("$(");
			b.append(amines[i][1]);
			b.append(")");
		}
		b.append("]");
		addSubstructure(SA28bis_title, b.toString());

		setID("SA28bis");
		setTitle(SA28bis_title);

		StringBuffer e = new StringBuffer();
		e.append("<html>");
		e
				.append("Mono- or di- methyl or ethyl aromatic amines, are included.However:");
		e.append("<ul>");
		e.append("<li>");
		e
				.append("Aromatic amino groups with ortho-disubstitution or with a carboxylic acid substituent in ortho position should be excluded.");

		e.append("<li>");
		e
				.append("If a sulfonic acid group (-SO3H) is present on the ring that contains also the amino group, the substance should be excluded from the alert.");

		e.append("<li>");
		e
				.append("see also the two examples of exceptions for the nitro alert (alert 27))");

		e.append("</ul>");
		e.append("</html>");

		setExplanation(e.toString());

		examples[0] = "CNC(=O)OC=1C=C(C)C(=C(C)C=1)N(C)C"; // ortho
															// disubstituted
		examples[1] = "CN(C)C=1C=CC=CC=1";
		editable = false;
	}

	protected boolean isAPossibleHit(IAtomContainer mol,
			IAtomContainer processedObject) throws DecisionMethodException {
		MFAnalyser mfa = new MFAnalyser(mol);
		Map<String, Integer> elements = mfa.getFormulaHashtable();
		return elements.containsKey("N");
	}
}
