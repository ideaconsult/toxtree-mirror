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

*//**
 * <b>Filename</b> MolFlags.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-3
 * <b>Project</b> toxTree
 */
package toxTree.query;



import java.util.logging.Logger;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.core.data.MoleculeTools;

/**
 * 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-3
 */
public class MolFlags {
	protected transient static Logger logger  = Logger.getLogger(MolFlags.class.getName());
    public final static String MOLFLAGS = "CRAMERFLAGS";
    public final static String PARENT = "PARENT";
    protected boolean analysed = false;
    protected boolean openChain = false;
    protected boolean aliphatic = false;
    protected boolean alicyclic = false;
    protected boolean acetylenic = false;
    protected boolean aromatic = false;
    protected boolean heteroaromatic = false;
    protected boolean heterocyclic = false;
    protected boolean heterocyclic3 = false;

    protected int aromaticRings = 0;
    protected IRingSet ringset = null;
    protected IAtomContainerSet hydrolysisProducts = null;
    protected IAtomContainerSet residues = null;
    /**
     * 
     */
    public MolFlags() {
        super();
    }
    public void clear() {
    	openChain = false;
        analysed = false;
        aliphatic = false;
        alicyclic = false;
        acetylenic = false;
        aromatic = false;
        heteroaromatic = false;
        heterocyclic = false;
        heterocyclic3 = false;
        aromaticRings = 0;
        
    }
    public boolean isAcetylenic() {
        return acetylenic;
    }
    public void setAcetylenic(boolean acetylenic) {
        this.acetylenic = acetylenic;
    }
    public boolean isAlicyclic() {
        return alicyclic;
    }
    public void setAlicyclic(boolean alicyclic) {
        this.alicyclic = alicyclic;
    }
    public boolean isAliphatic() {
        return aliphatic;
    }
    public void setAliphatic(boolean aliphatic) {
        this.aliphatic = aliphatic;
    }
    public boolean isAnalysed() {
        return analysed;
    }
    public void setAnalysed(boolean analysed) {
        this.analysed = analysed;
    }
    public boolean isAromatic() {
        return aromatic;
    }
    public void setAromatic(boolean aromatic) {
        this.aromatic = aromatic;
    }
    public boolean isHeterocyclic() {
        return heterocyclic;
    }
    public void setHeterocyclic(boolean heterocyclic) {
        this.heterocyclic = heterocyclic;
    }
    
    public IRingSet getRingset() {
        return ringset;
    }
    public void setRingset(IRingSet ringset) {
        this.ringset = ringset;
    }
    public boolean isHeteroaromatic() {
        return heteroaromatic;
    }
    public void setHeteroaromatic(boolean heteroaromatic) {
        this.heteroaromatic = heteroaromatic;
    }
    public boolean isHeterocyclic3() {
        return heterocyclic3;
    }
    public void setHeterocyclic3(boolean heterocyclic3) {
        this.heterocyclic3 = heterocyclic3;
    }
    public int getAromaticRings() {
        return aromaticRings;
    }
    public void setAromaticRings(int aromaticRings) {
        this.aromaticRings = aromaticRings;
    }
    public boolean isOpenChain() {
        return openChain;
    }
    public void setOpenChain(boolean openChain) {
        this.openChain = openChain;
    }
	/**
	 * @return Returns the hydrolysisProducts.
	 */
	public IAtomContainerSet getHydrolysisProducts() {
		return hydrolysisProducts;
	}
	/**
	 * @param hydrolysisProducts The hydrolysisProducts to set.
	 */
	public void setHydrolysisProducts(IAtomContainerSet hydrolysisProducts) {
		if (hydrolysisProducts != null) logger.fine("Hydrolysis products already exists");
		this.hydrolysisProducts = hydrolysisProducts;
		logger.fine("Hydrolysis products set");		
	}
	/**
	 * @return Returns the residues.
	 */
	public IAtomContainerSet getResidues() {
		return residues;
	}
	/**
	 * @param residues The residues to set.
	 */
	public void setResidues(IAtomContainerSet residues) {
		this.residues = residues;
	}
	public void addResidue(IAtomContainer residue) {
		if (residues == null)
			this.residues = MoleculeTools.newAtomContainerSet(SilentChemObjectBuilder.getInstance());
		residues.addAtomContainer(residue);
	}	
	@Override
	public String toString() {
		return "";
	}
}

