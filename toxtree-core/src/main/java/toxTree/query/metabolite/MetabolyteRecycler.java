package toxTree.query.metabolite;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.Strand;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.inchi.InChIToStructure;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.io.CMLWriter;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.remote.RemoteCompoundLookup;
import ambit2.base.config.Preferences;
import ambit2.base.data.Property;
import ambit2.base.exceptions.AmbitException;
import ambit2.core.config.AmbitCONSTANTS;
import ambit2.core.data.MoleculeTools;
import ambit2.core.io.MDLWriter;
import ambit2.core.processors.structure.AtomConfigurator;
import ambit2.core.processors.structure.InchiProcessor;

public class   MetabolyteRecycler implements PropertyChangeListener {
	
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
			//quick hack for 5-valent carbon bug
			for (IAtom atom : result.atoms()) {
				if ("C".equals(atom.getSymbol()) && result.getBondOrderSum(atom)>=5){
					//System.out.println(result.getBondOrderSum(atom));
					List<IAtom> neighbors = result.getConnectedAtomsList(atom);
					for (IAtom neighbor : neighbors) {
						if ("H".equals(neighbor.getSymbol())) { 

							result.removeAtomAndConnectedElectronContainers(neighbor);
							
						      atom.setAtomTypeName((String) CDKConstants.UNSET);
					            atom.setMaxBondOrder((IBond.Order) CDKConstants.UNSET);
					            atom.setBondOrderSum((Double) CDKConstants.UNSET);
					            atom.setCovalentRadius((Double) CDKConstants.UNSET);
					            atom.setValency((Integer) CDKConstants.UNSET);
					           // atom.setFormalCharge((Integer) CDKConstants.UNSET);
					            atom.setHybridization((IAtomType.Hybridization) CDKConstants.UNSET);
					            atom.setFormalNeighbourCount((Integer) CDKConstants.UNSET);
					            atom.setFlag(CDKConstants.IS_HYDROGENBOND_ACCEPTOR, false);
					            atom.setFlag(CDKConstants.IS_HYDROGENBOND_DONOR, false);
					            atom.setProperty(CDKConstants.CHEMICAL_GROUP_CONSTANT, CDKConstants.UNSET);
					            atom.setFlag(CDKConstants.ISAROMATIC, false);
					            atom.setProperty("org.openscience.cdk.renderer.color", CDKConstants.UNSET);
					            atom.setAtomicNumber((Integer) CDKConstants.UNSET);
					            atom.setExactMass((Double) CDKConstants.UNSET);		

							break;
						}
					}
				}
			}
			result = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(result);
			
			try {
				//cfg.process(result);
				AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(result);
				CDKHueckelAromaticityDetector.detectAromaticity(result);
			} catch (Exception x) {}
			
			  
			result = quick_and_dirty_viamol(result);
			
			String inchi = null;
			String auxinfo = null;
			try {
				if (inchip!=null) {
					InChIGenerator gen = inchip.process(result);
					inchi = gen.getInchi();

		       		
		       		IAtomContainer tmp = quick_and_dirty_viainchi(inchi);
		       		if (tmp != null) {
		       			result = tmp;

			       		result.setProperty(Property.opentox_InChI_std,inchi);
			       		result.setProperty(Property.opentox_InChIAuxInfo_std,gen.getAuxInfo());
		       		}
				}
			} catch (Throwable x) {}
			
			if ((inchi!=null) && Preferences.getProperty(Preferences.REMOTELOOKUP).equals("true")) try {
				RemoteCompoundLookup lookup = new RemoteCompoundLookup();
				IAtomContainer a = lookup.lookup(inchi);
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
			} catch (Exception x) {//do nothing 
				
			}
			
			try {
				if(smigen !=null) {
		       		result.setProperty(AmbitCONSTANTS.SMILES,smigen.createSMILES(result));
				}
			} catch (Exception x) {
				x.printStackTrace();
			}				
			result.setProperty("Created by SMARTCyp metabolite prediction",products.getID());			
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
    		InChIToStructure c =inchiFactory.getInChIToStructure(inchi, NoNotificationChemObjectBuilder.getInstance());
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
