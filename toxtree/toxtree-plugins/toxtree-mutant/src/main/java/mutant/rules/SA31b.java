package mutant.rules;

import toxTree.tree.rules.StructureAlert;
import toxTree.tree.rules.smarts.SMARTSException;

public class SA31b extends StructureAlert {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8299877660995850022L;
	public static String SA31b_title = "Halogenated PAH (naphthalenes, biphenyls, diphenyls)  (Nongenotoxic carcinogens)";
	
	
    public SA31b() {
        super();
        try {
        	setContainsAllSubstructures(false);
        	
        	addSubstructure("naphtalenes","[Cl,Br,F,I]c1ccc2ccccc2(c1)");
        	//addSubstructure("biphenyls1","c1cc(ccc1c2ccc(cc2)[Cl,Br,F,I])[Cl,Br,F,I]");
        	addSubstructure("biphenyls1","[Cl,Br,F,I]c1ccc(cc1)!@c2ccc(cc2)[Cl,Br,F,I]");
        	

        	/*
        	addSubstructure("biphenyls2","c1cc(ccc1c2c([Cl,Br,F,I])cc(cc2))[Cl,Br,F,I]");
        	addSubstructure("biphenyls3","c1cc(ccc1c2cc([Cl,Br,F,I])c(cc2))[Cl,Br,F,I]");
        	addSubstructure("biphenyls4","c1c([Cl,Br,F,I])c(ccc1c2cc([Cl,Br,F,I])c(cc2))");
        	addSubstructure("biphenyls5","c([Cl,Br,F,I])1cc(ccc1c2cc([Cl,Br,F,I])c(cc2))");
        	*/
        	addSubstructure("diphenyls","c1cc(ccc1[!R]c2ccc(cc2)[Cl,Br,F,I])[Cl,Br,F,I]");
        	

    	
            setID("SA31b");
            setTitle(SA31b_title);

            StringBuffer e = new StringBuffer();
            e.append("<html>");
            e.append(SA31b_title);
     
            e.append("<br>");
            e.append("<ul>");
            e.append("<li>");
            e.append("Naphthalenes: should fire with any halogenation pattern");
            
            e.append("<li>");
            e.append("Diphenyls and biphenyls: should fire only if both 4 and 4’ positions are occupied by halogens");
            
            e.append("</ul>");
            e.append("</html>");
            
            setExplanation(e.toString());
            
            examples[0] = "CC1C=3C=C(F)C=CC=3(C2=CC=C(F)C=C12)";
//"NC2=CC=C(CC1=CC=C(N)C(=C1)Cl)C=C2Cl";
            examples[1] = "C=1C=C(C=CC=1C2=CC=C(C=C2)Cl)Cl";   
            editable = false;
        } catch (SMARTSException x) {
            logger.error(x);
        }
    }
}
