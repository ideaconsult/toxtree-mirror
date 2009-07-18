package mutant.rules;

import toxTree.tree.rules.StructureAlert;
import toxTree.tree.rules.smarts.SMARTSException;

public class SA31c extends StructureAlert {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1445930404886318331L;
	public static String SA31c_title = "Halogenated dibenzodioxins  (Nongenotoxic carcinogens)";
	
	
    public SA31c() {
        super();
        try {
        	setContainsAllSubstructures(false);
        	
        	addSubstructure(SA31c_title,"c1ccc2Oc3cc(ccc3(Oc2(c1)))[Cl,Br,F,I]");
        	
            setID("SA31c");
            setTitle(SA31c_title);

            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA31c_title);
           
            e.append("<br>");
            e.append("<ul>");
            e.append("<li>");
            e.append("Only the chemicals with at least one halogen in one of the four lateral positions should fire");
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "C=1C=CC=2OC=3C=CC=CC=3(OC=2(C=1))";
            examples[1] = "C=1C=C2OC=3C=C(C=CC=3(OC2(=CC=1Cl)))Cl";   
            editable = false;
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }
}
