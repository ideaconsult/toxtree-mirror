package toxtree.plugins.kroes.rules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IBond.Order;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.IQueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;
import org.openscience.cdk.isomorphism.matchers.SymbolSetQueryAtom;
import org.openscience.cdk.isomorphism.matchers.smarts.AromaticQueryBond;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.tree.rules.RuleSubstructures;
import ambit2.core.io.IteratingDelimitedFileReader;

/**
 * TODO refactor with SMARTS
 * @author nina
 *
 */

public class RuleKroesFig1Q1 extends RuleSubstructures
{

    public RuleKroesFig1Q1()
    {
        init();
        readStream(getConfiguration(), config);
    }

    public RuleKroesFig1Q1(InputStream stream, String format)
    {
        init();
        readStream(stream, format);
    }

    public RuleKroesFig1Q1(File file)
    {
        init();
        logger.fine("Will be using file\t"+ file.getAbsoluteFile());
        String f = file.getPath().toLowerCase();
        if(file.exists() && (f.endsWith(".sdf") || f.endsWith(".smi") || f.endsWith(".csv")))
            try
            {
                FileInputStream fStream = new FileInputStream(file);
                readStream(fStream, file.getPath().toLowerCase());
            }
            catch(FileNotFoundException x)
            {
                logger.log(Level.SEVERE,x.getMessage(),x);
            }
        else
            logger.fine("File does not exists or unsupported format\t"+ file.getAbsoluteFile());
    }

    protected void init()
    {
    	setID("Q1");
        title = "Is the substance a non-essential metal or metal containing compound, or is it a polyhalogenated dibenzodioxin, -dibenzofuran, or -biphenyl?";
        explanation.append("<html>");
        explanation.append(title);
        explanation.append("<br>");
        explanation.append("Essential metals: ");
        for(int i = 0; i < me_essential.length; i++)
        {
            if(i > 0)
                explanation.append(',');
            explanation.append(me_essential[i]);
        }

        explanation.append("<br>");
        explanation.append("Non-essential metals: ");
        for(int i = 0; i < me_nonessential.length; i++)
        {
            if(i > 0)
                explanation.append(',');
            explanation.append(me_nonessential[i]);
        }

        explanation.append("</html>");
        examples[0] = "c1ccc2Oc3c(Oc2(c1))cccc3Cl";
        examples[1] = "O1c3cccc(c3(Oc2c1cccc2Cl))Cl";
    }

    protected void readStream(InputStream fStream, String format)
    {
        try
        {
            IIteratingChemObjectReader reader;
            if(format.endsWith(".sdf"))
                reader = new IteratingSDFReader(fStream, SilentChemObjectBuilder.getInstance());
            else
            if(format.endsWith(".csv"))
                reader = new IteratingDelimitedFileReader(fStream);
            else
            if(format.endsWith(".smi"))
            {
                reader = new IteratingSMILESReader(fStream,SilentChemObjectBuilder.getInstance());
            } else
            {
                logger.severe("Unsupported format");
                fStream.close();
                return;
            }
            int r = 0;
            while(reader.hasNext()) 
            {
                r++;
                Object o = reader.next();
                if(o instanceof IAtomContainer)
                {
                    IAtomContainer m = (IAtomContainer)o;
                    if(m.getAtomCount() > 0)
                        try
                        {
                            MolAnalyser.analyse(m);
                            IQueryAtomContainer q = createQueryContainer(m);
                            addSubstructure(q);
                            q.setID(m.getProperty("Names").toString());
                        }
                        catch(Exception exception) { }
                }
            }
            logger.finer(r + " structures read from " + format);
            reader.close();
            reader = null;
        }
        catch(IOException x)  {
            logger.log(Level.SEVERE,x.getMessage(),x);
        }
        catch(CDKException x)  {
            logger.log(Level.SEVERE,x.getMessage(),x);
        }
        
    }

    public static IQueryAtomContainer createQueryContainer(IAtomContainer container)   {
    	IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
        IQueryAtomContainer queryContainer = new QueryAtomContainer(builder);
        Iterator<IAtom> atoms = container.atoms().iterator();
        while (atoms.hasNext()) {
        	IAtom atom  = atoms.next();
        
            if(atom.getSymbol().equals("H"))
            {
                SymbolSetQueryAtom a = new SymbolSetQueryAtom(builder);
                a.setSymbol("Hal");
                a.addSymbol("Hal");
                a.addSymbol("H");
                for(int j = 0; j < halogens.length; j++)
                    a.addSymbol(halogens[j]);

                queryContainer.addAtom(a);
            } else
                queryContainer.addAtom(new SymbolQueryAtom(atom));
        }
        Iterator<IBond> bonds = container.bonds().iterator();
        while (bonds.hasNext())
        {
        	IBond bond = bonds.next();
            int index1 = container.getAtomNumber(bond.getAtom(0));
            int index2 = container.getAtomNumber(bond.getAtom(1));
            if(bond.getFlag(CDKConstants.ISAROMATIC))
            {
                AromaticQueryBond b = new AromaticQueryBond((IQueryAtom)queryContainer.getAtom(index1), (IQueryAtom)queryContainer.getAtom(index2), Order.SINGLE,builder);
                b.setFlag(CDKConstants.ISAROMATIC, true);
                queryContainer.addBond(b);
            } else
            {
                MyOrderQueryBond q = new MyOrderQueryBond((IQueryAtom)queryContainer.getAtom(index1), (IQueryAtom)queryContainer.getAtom(index2), bond.getOrder(),builder);
                q.setFlag(CDKConstants.ISAROMATIC, bond.getFlag(CDKConstants.ISAROMATIC));
                queryContainer.addBond(q);
            }
        }

        return queryContainer;
    }

    protected static InputStream getConfiguration()
    {
        return toxtree.plugins.kroes.rules.RuleKroesFig1Q1.class.getResourceAsStream(config);
    }

    public boolean isImplemented()
    {
        return true;
    }


    public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException
    {
	    IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
        List<IElement> elements = MolecularFormulaManipulator.elements(formula);
        int halogensFound = 0;
        for(int i = 0; i < elements.size(); i++)
        {
            String element = elements.get(i).getSymbol();
            for(int m = 0; m < me_essential.length; m++)
                if(element.equals(me_essential[m]))
                {
                    logger.finer("Essential metal\t"+ me_essential[m]+ "\tfound.");
                    return true;
                }

            for(int m = 0; m < me_nonessential.length; m++)
                if(element.equals(me_nonessential[m]))
                {
                    logger.finer("Non-essential metal\t"+ me_nonessential[m]+ "\tfound.");
                    return true;
                }

            for(int m = 0; m < halogens.length; m++)
                if(element.equals(halogens[m]))
                {
                    logger.finer("Halogen \t" + halogens[m]+ "\tfound.");
                    halogensFound += MolecularFormulaManipulator.getElementCount(formula,elements.get(i));
                }

        }

        if(halogensFound < 2)
        {
            logger.finer("Not a polyhalogenated compound");
            return false;
        }
        logger.finer("Polyhalogenated compound\t"+ Integer.toString(halogensFound)+ "\thalogens found");
        //print(mol);
        for(int i = 0; i < query.getAtomContainerCount(); i++)
        {
            logger.finer("Compare with "+ query.getAtomContainer(i).getID());
            if(FunctionalGroups.isSubstance(mol, query.getAtomContainer(i)))
                return true;
        }

        logger.fine("Compared with "+ Integer.toString(query.getAtomContainerCount())+ " structures");
        logger.fine("NOT a "+ explanation.toString());
        return false;
    }
    /*
    protected void print(IAtomContainer a)
    {
        logger.info(a.getID());
        for(int i = 0; i < a.getAtomCount(); i++)
        {
            System.out.print((i + 1) + "\t" + a.getAtom(i) + "\t");
            boolean f[] = a.getAtom(i).getFlags();
            for(int j = 0; j < f.length; j++)
                System.out.print(f[j] + "\t");

            System.out.println();
        }

        for(int i = 0; i < a.getBondCount(); i++)
        {
            System.out.print((i + 1) + "\t" + a.getBond(i) + "\t");
            System.out.print(a.getBond(i).getOrder() + "\t");
            boolean f[] = a.getBond(i).getFlags();
            for(int j = 0; j < f.length; j++)
                System.out.print(f[j] + "\t");

            System.out.println();
        }

        logger.info("");
    }
	*/
    public static transient String halogens[] = {
        "F", "Cl", "Br", "I"
    };
    public static transient String me_essential[] = {
        "Na", "Mg", "K", "Ca", "P", "Mn", "Fe", "Cu", "Zn", "Co", 
        "Ni"
    };
    public static transient String me_nonessential[] = {
        "Cr", "Hg", "Pb", "V", "Al", "Ag", "Cd", "As", "B", "Ti", 
        "Se", "Sn", "Sb", "Be", "Zr", "Nb", "Mo", "Te", "Ba", "W", 
        "Au", "Bi"
    };
    public static transient String config = "/kroes/KroesFig1Q1.sdf";
    private static final long serialVersionUID = 0x13adfd43291d6b05L;

}
