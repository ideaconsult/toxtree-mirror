package toxTree.query.metabolite;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Vector2d;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.inchi.InChIToStructure;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.io.CMLWriter;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.remote.RemoteCompoundLookup;
import ambit2.base.config.Preferences;
import ambit2.base.data.Property;
import ambit2.core.config.AmbitCONSTANTS;
import ambit2.core.data.MoleculeTools;
import ambit2.core.io.MDLWriter;
import ambit2.core.processors.structure.AtomConfigurator;
import ambit2.core.processors.structure.InchiProcessor;

public class   MetabolyteRecycler implements PropertyChangeListener {
	protected StructureDiagramGenerator sdg = new StructureDiagramGenerator();
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
  		if (evt.getNewValue()==null) return;
		IAtomContainer products = ((IAtomContainer)evt.getNewValue());
		prepare(products);
		
	}
	protected void handleproduct(IAtomContainer product) {
		
	}
	
	protected void prepare(IAtomContainer products) {

		InchiProcessor inchip = null;
		try {
			inchip = new InchiProcessor();
		} catch (Exception x) {
			inchip = null;
		}
		

		SmilesGenerator smigen = null;
		try {
			smigen = new SmilesGenerator();
		} catch (Exception x) {
			smigen = null;
		}		
		AtomConfigurator cfg = new AtomConfigurator();        		
		IAtomContainerSet set  = ConnectivityChecker.partitionIntoMolecules(products);
		for (IAtomContainer result : set.atomContainers()) {
			
			//result = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(result);
			
			try {
				//cfg.process(result);
				AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(result);
			//	CDKHueckelAromaticityDetector.detectAromaticity(result);
			} catch (Exception x) {}
			
			/*
			for (IAtom atom : result.atoms()) {
				System.out.println(String.format("%s %s %s",atom.getSymbol(),atom.getFormalCharge(),result.getBondOrderSum(atom)));
			}
			*/
			
			//quick hack for 5-valent carbon bug

			for (IAtom atom : result.atoms()) {
				if ("C".equals(atom.getSymbol()) && result.getBondOrderSum(atom)>=5){
					//System.out.println(result.getBondOrderSum(atom));
					List<IAtom> neighbors = result.getConnectedAtomsList(atom);
					for (IAtom neighbor : neighbors) {
						if ("H".equals(neighbor.getSymbol())) { 

							result.removeAtomAndConnectedElectronContainers(neighbor);
							   atom.setFormalNeighbourCount(null);
							   atom.setAtomTypeName(null);
							   atom.setHybridization(null);
							   atom.setBondOrderSum(null);
							   atom.setValency(null);
							   
							   atom.setFlag(CDKConstants.ISAROMATIC, false);	
								try {
									//cfg.process(result);
									AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(result);
								} catch (Exception x) {}
								//dobreak = true;
							break;
						}
					}
				}
				
			}

			String inchi = null;
			String auxinfo = null;
			try {
				if (inchip!=null) {
					InChIGenerator gen = inchip.process(result);
					inchi = gen.getInchi();
					auxinfo = gen.getAuxInfo();
		       		
				}
			} catch (Throwable x) {x.printStackTrace();}
			
			if (inchi==null)  {
				result = quick_and_dirty_viainchi(inchi);
			} else 
			result = quick_and_dirty_viamol(result);
	       		
	       		
	       	if (inchi!=null)  	   {    		
	       		result.setProperty(Property.opentox_InChI_std,inchi);
	       		result.setProperty(Property.opentox_InChIAuxInfo_std,auxinfo);	       		
			}
			
			if ((inchi!=null) && Preferences.getProperty(Preferences.REMOTELOOKUP).equals("true")) try {
				RemoteCompoundLookup lookup = new RemoteCompoundLookup();
				IAtomContainer a = lookup.lookup(inchi);
				if (a !=null) {
					Iterator keys = a.getProperties().keySet().iterator();
					while (keys.hasNext()) {
						Object key = keys.next();
						if (Property.opentox_SMILES.equals(key)) continue; //we have that
						Object value = result.getProperty(key);
						if (value == null) {
							value = a.getProperty(key);
							String[] values = value.toString().split("\n");
							if (values.length==1)
								result.setProperty(key,value);
							else
								for (int i=0; i < values.length;i++)
									result.setProperty(i==0?key:String.format("%s_%d",key,i),values[i]);
						}
					}
				}
			} catch (Exception x) {//do nothing 
				x.printStackTrace();
			}
			
			try {
				if(smigen !=null) {
					smigen.setUseAromaticityFlag(true);
		       		result.setProperty(AmbitCONSTANTS.SMILES,smigen.createSMILES(result));
				}
			} catch (Exception x) {
				x.printStackTrace();
			}				
			result.setProperty("Created by SMARTCyp metabolite prediction",products.getID());	
			
        	if (sdg == null) sdg = new StructureDiagramGenerator();
               sdg.setMolecule((IAtomContainer)result);
               try {
                sdg.generateCoordinates(new Vector2d(0,1));
                result = sdg.getMolecule();
               } catch (Exception x) {}
			handleproduct(result);
		}
	}
	
	protected IAtomContainer quick_and_dirty_viamol(IAtomContainer ac) {
		try {
			StringWriter mol = new StringWriter();
			MDLWriter wri = new MDLWriter();
			wri.setWriter(mol);
			wri.write(ac);
			
			wri.close();
			//System.out.println(mol.toString());
			IAtomContainer ac1 =  MoleculeTools.readMolfile(mol.toString());
			if (ac1==null) return ac;
			else return ac1;
		} catch (Exception x) {
			x.printStackTrace();
			return ac;
		}
	}
	
	protected IAtomContainer quick_and_dirty_viainchi(String inchi) {
    	try {
    		InChIGeneratorFactory inchiFactory = InChIGeneratorFactory.getInstance();
    		InChIToStructure c =inchiFactory.getInChIToStructure(inchi, SilentChemObjectBuilder.getInstance());
    		return c.getAtomContainer();   
    	} catch (Exception x) {
    		return null;
    	}

	}
	
	protected IAtomContainer quick_and_dirty_viacml(IAtomContainer ac) {
		try {
			StringWriter cml = new StringWriter();
			CMLWriter wri = new CMLWriter();
			wri.setWriter(cml);
			wri.write(ac);
			
			wri.close();
			//System.out.println(cml.toString());
			IAtomContainer mol =  MoleculeTools.readCMLMolecule(cml.toString());
			if (mol==null) return ac;
			else return mol;
		} catch (Exception x) {
			x.printStackTrace();
			return ac;
		}
	}
	
}
