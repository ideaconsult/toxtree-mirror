package toxTree.query.metabolite;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.remote.RemoteCompoundLookup;
import ambit2.base.config.Preferences;
import ambit2.base.data.Property;
import ambit2.core.config.AmbitCONSTANTS;
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
			for (IAtom a : result.atoms()) {
				if ("C".equals(a.getSymbol()) && result.getBondOrderSum(a)==5){
					List<IAtom> neighbors = result.getConnectedAtomsList(a);
					for (IAtom neighbor : neighbors) {
						if ("H".equals(neighbor.getSymbol())) { 

							result.removeBond(a, neighbor);
							result.removeAtom(neighbor);
							try {cfg.process(result);} catch (Exception x) {}
							break;
						}
					}
				}
			}
			result = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(result);
			result.setProperty("Created by SMARTCyp metabolite prediction",products.getID());  
			String inchi = null;
			try {
				if (inchip!=null) {
					InChIGenerator gen = inchip.process(result);
					inchi = gen.getInchi();
		       		result.setProperty(Property.opentox_InChI_std,inchi);
		       		result.setProperty(Property.opentox_InChIAuxInfo_std,gen.getAuxInfo());
		       		result.setProperty(Property.opentox_InChIKey_std,gen.getInchiKey());
				}
			} catch (Exception x) {}
			
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
			handleproduct(result);
		}
	}
	
}
