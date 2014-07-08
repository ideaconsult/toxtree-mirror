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
package toxtree.plugins.search.rules;

import java.util.Iterator;

import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.index.CASNumber;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;
import ambit2.base.data.LiteratureEntry;
import ambit2.base.data.Property;
import ambit2.base.data.Template;
import ambit2.base.processors.search.AbstractFinder;
import ambit2.namestructure.Name2StructureFinder;
import ambit2.pubchem.rest.PUGRestRequest;
import ambit2.pubchem.rest.PUGRestRequest.COMPOUND_DOMAIN_INPUT;
import ambit2.pubchem.rest.PubChemRestFinder;
import ambit2.search.AllSourcesFinder;
import ambit2.search.chemidplus.ChemIdPlusRequest;
import ambit2.search.csls.CSLSStringRequest;
import ambit2.search.opentox.OpenToxRequest;


/**
 * 
 * Compound lookup
 * @author Nina Jeliazkova jelaizkova.nina@gmail.com
 * <b>Modified</b>July 11, 2011
 */
public class Find extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6192036039649653201L;
	protected AbstractFinder.SITE searchSite =  AbstractFinder.SITE.OPENTOX;
	protected AbstractFinder.MODE mode;
	protected Template profile = new Template();
	
	public Find() {
		super();


		setTitle("Search for compound by an identifier");
		id = "1";
		setExplanation("by invoking remote services");
		examples[0] = "CCC";
		examples[1] = "";
		editable = false;
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {

		Object value = null;
		profile.clear();
		Iterator keys = mol.getProperties().keySet().iterator();
		while (keys.hasNext()) {
			value = mol.getProperty(keys.next());
			if ((value!=null) && CASNumber.isValid(value.toString())) {
				profile.add(Property.getCASInstance());
				break;
			}
		}
		if (profile.size()==0) throw new DecisionMethodException("No CAS number defined!");
		
		IProcessor p = configure(profile);
		try {
			Object content = p.process(value.toString());
			System.out.println(content);
			return content != null;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
		
	}

	protected IProcessor configure(Template profile) {		
		LiteratureEntry le;
		IProcessor p;
		IProcessor<String,String> request;
		switch (searchSite) {
		case PUBCHEM_CID: {
			p = new PubChemRestFinder(profile,mode,COMPOUND_DOMAIN_INPUT.cid);
			le =  new LiteratureEntry("PubChem",PUGRestRequest.PUGREST_URL);
			break;
		}
		case PUBCHEM_NAME: {
			p = new PubChemRestFinder(profile,mode,COMPOUND_DOMAIN_INPUT.name);
			le =  new LiteratureEntry("PubChem",PUGRestRequest.PUGREST_URL);
			break;
		}
		case PUBCHEM_INCHIKEY: {
			p = new PubChemRestFinder(profile,mode,COMPOUND_DOMAIN_INPUT.inchikey);
			le =  new LiteratureEntry("PubChem",PUGRestRequest.PUGREST_URL);
			break;
		}		
		case OPENTOX: {
			request = new OpenToxRequest(searchSite.getURI());
			p = new AllSourcesFinder(profile,request,mode);
			le =  new LiteratureEntry(request.toString(),request.toString());
			break;
		}		
		case NAME2STRUCTURE: {
			p = new Name2StructureFinder(profile,mode);
			le =  new LiteratureEntry(searchSite.getTitle(),searchSite.getURI());
			break;
		}
		/*
		case CHEBI: {
			p = new ChebiFinder(profile,mode);
			le =  new LiteratureEntry("ChEBI","http://www.ebi.ac.uk/chebi");
			break;
		}

		case CHEMBL: {
			p = new ChemBLFinder(profile,mode);
			le =  new LiteratureEntry(searchSite.name(),"https://www.ebi.ac.uk/chemblws");
			break;
		}	
		*/
	
		case CHEMIDPLUS: {
			request = new ChemIdPlusRequest();
			p = new AllSourcesFinder(profile,request,mode);
			le =  new LiteratureEntry(request.toString(),request.toString());
			break;
		}
		default : {
			request = new CSLSStringRequest();
			p = new AllSourcesFinder(profile,request,mode);
			le =  new LiteratureEntry(request.toString(),request.toString());
			break;
		}
		}
		return p;
	}
}
