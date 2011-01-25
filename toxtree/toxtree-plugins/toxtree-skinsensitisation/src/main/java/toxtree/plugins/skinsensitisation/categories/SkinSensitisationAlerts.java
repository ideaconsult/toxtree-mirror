package toxtree.plugins.skinsensitisation.categories;

public enum SkinSensitisationAlerts {
	SNAR {
		@Override
		public String getCategoryExplanation() {
			return "Substrate has structural alerts for Nucleophilic Aromatic Substitution.";
		}
		@Override
		public String getCategoryTitle() {
			return "Alert for SNAr Identified.";
		}
		@Override
		public String getTitle() {
			return "SNAr-Nucleophilic Aromatic Substitution";
		}
		@Override
		public String getExplanation() {
			return "identify a potential substrate for Nucleophilic aromatic substitution.";
		}
		
		@Override
		public String[] getSMARTS() {
			return new String[] {
					"c1([F,Cl,Br,I,$(N(=O)~O)])c([F,Cl,Br,I,$(N(=O)~O),$(C#N),$(C=O),$(C(F)(F)F),$(S=O)])cc([F,Cl,Br,I,$(N(=O)~O),$(C#N),$(C=O),$(C(F)(F)F),$(S=O)])cc1",
					"c1([F,Cl,Br,I,$(N(=O)~O)])c([F,Cl,Br,I,$(N(=O)~O),$(C#N),$(C=O),$(C(F)(F)F),$(S=O)])cccc1([F,Cl,Br,I,$(N(=O)~O),$(C#N),$(C=O),$(C(F)(F)F),$(S=O)])",
					"c1([F,Cl,Br,I,$(N(=O)~O)])ncc([F,Cl,Br,I,$(N(=O)~O),$(C#N),$(C=O),$(C(F)(F)F),$(S=O)])cc1",
					"c1([F,Cl,Br,I,$(N(=O)~O)])ncccc1([F,Cl,Br,I,$(N(=O)~O),$(C#N),$(C=O),$(C(F)(F)F),$(S=O)])",
					"c1([F,Cl,Br,I,$(N(=O)~O)])ncccn1",
					"c1([F,Cl,Br,I,$(N(=O)~O)])ncncc1",
					"c1([F,Cl,Br,I,$(N(=O)~O)])ncc([F,Cl,Br,I,$(N(=O)~O),$(C#N),$(C=O),$(C(F)(F)F),$(S=O)])nc1",
					"c1nc([F,Cl,Br,I,$(N(=O)~O)])ncn1"
			};
		}
		@Override
		public String getExample(boolean value) {
			return value?"c1(Cl)ncncc1":"C";
		}		
		@Override
		public String getShortName() {
			return "SNAR";
		}
	},	
	SHIFF_BASE {
		@Override
		public String getCategoryExplanation() {
			return "Identified a structural alert or precursor for shiff base formation.";
		}
		@Override
		public String getCategoryTitle() {
			return "Alert for Schiff base formation  identified.";
		}
		@Override
		public String getTitle() {
			return "Schiff Base Formation";
		}
		@Override
		public String getExplanation() {
			return "to be a potential substrate for Schiff base formation.";
		}
		@Override
		public String[] getSMARTS() {
			return new String[] {
				"[CH2]=O",
				"[CH2]N([CH3])[CH3]",
				"CC(C)=[CH][CH2][OH]",
				"CC=C(C)[CH2][OH]",
				"[CX4][CH]=[O,SX2]",
				"[a][CH]=O",
				"C(C)(C)=CC=[O,SX2]",
				"[C;!r5]([C;!r5])=[C;!r5](C)[C;!r5]=[O,SX2;!r5]",
				"[#6]C(=[O,SX2])C(=[O,SX2])[#6]",
				"[#6]C(=[O,SX2])[CX4]C(=[O,SX2])[#6]",
				"[#6][$([NX2]=O),$(N=C=O),$(OC#N),$(SC#N),$(N=C=S)]",
				"[CH2][NH2]"
			};
		}
		@Override
		public String getExample(boolean value) {
			return value?"[CH2]=O":"C";
		}
		@Override
		public String getShortName() {
			return "SB";
		}
	},

	MICHAEL_ACCEPTORS {
		@Override
		public String getCategoryExplanation() {
			return "A structural alert has been identified for a potential Michael Acceptor or michael acceptor precursor.";
		}
		@Override
		public String getCategoryTitle() {
			return "Alert for Michael Acceptor identified.";
		}
		@Override
		public String getExplanation() {
			return "to be a potential Michael reaction acceptor or a precursor.";
		}
		@Override
		public String getTitle() {
			return "Michael Acceptor";
		}
		@Override
		public String[] getSMARTS() {
			return new String[] {
				"[CH2,CH]=[CH][$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"C1=[C,N][$(S(=O)(=O)),$(C=[N,O]),$(S=O)][C,N]=C1c2ccccc2",
				"[CH2,CH]=C1C(=[O,SX2])**C1",
				"c1c([OH,NH2,NH])c([OH,NH2,NH,$(N=N),$(N(C)C)])ccc1",
				"c1c([OH,NH2,NH])ccc([OH,NH2,NH,$(N=N),$(N(C)C)])c1",
				"c1([OH])c(O[CH3])cccc1",
				"c1([OH])ccc(O[CH3])cc1",
				"c1c([OH])ccc(C=C[CH3])c1",
				"c1c([OH,NH2,NH])cc([OH,NH2,NH,$(N(C)C)])cc1",
				"C([F,Cl,Br,I])[CH2,CH][$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"[$([CH]=[CH2,CH]),$(C(C)=[CH2,CH]),$(C#C);!$(C(C)=CC)][CH2][OH]",
				"[CH2]=C[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"c1([OH])ccc([CH2][CH]=[CH2])cc1",
				"c1(O[CH3])ccc([CH2][CH]=[CH2])cc1",
				"[OH]c1cccc2ccccc12",
				"F[CH2,CH]C(F)[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"Cl[CH2,CH]C(Cl)[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"Br[CH2,CH]C(Br)[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"I[CH2,CH]C(I)[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"aC=C[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"C#C[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"[CH2,CH]=[CH]C=C[$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"[CHR]=[CR][$(N(=O)~O),$(C=O),$(C#N),$(S=O),$(C(=O)N),$(a)]",
				"C=C([F,Cl,Br,I])[F,Cl,Br,I]",
				"c1c(=[O,NH2,NH])c(=[O,NH2,NH])ccc1",
				"c1c(=[O,NH2,NH])ccc(=[O,NH2,NH])c1"
			};
			
		}
		@Override
		public String getExample(boolean value) {
			return value?"[OH]c1cccc2ccccc12":"C";
		}
		@Override
		public String getShortName() {
			return "MA";
		}
	},
	ACYL_TRANSFER {
		@Override
		public String getCategoryExplanation() {
			return "A structural alert for an acyl transfer agent, or a precursor, was identified.";
		}
		@Override
		public String getCategoryTitle() {
			return "Alert for Acyl Transfer agent identified.";
		}
		@Override
		public String getTitle() {
			return "Acyl Transfer Agents";
		}
		@Override
		public String getExplanation() {
			return "identifies a potential acyl transfer agent or precursor.";
		}
		@Override
		public String[] getSMARTS() {
			return new String[] {
				"[!$(C=C);!$(C#C)]C(=[O,SX1,N])[F,Cl,Br,I]",
				"[!$(C=C);!$(C#C)]C(=[O,SX1,N])[O,S,N][a]",
				"[!$(C=C);!$(C#C)]C(=[O,SX1,N])[O,S,N]C(=[O,SX1,N])",
				"[!$(C=C);!$(C#C)]C(=[O,S,N])[O,S][$(C=C),$(C=N),$(C#C),$(C#N)]",
				"[#6]1C(=[O,S])[O,N,S][#6]1",
				"[C,N,O,S,a]c1[n,o,s]c2ccccc2[n,o,s]1",
				"[#6]1[#6](=[N,O,S])[#7,#8,#16][#6][#7,#8,#16]1",
				"[C,O]=[#6]1[#7,#8,#16][#6](=[O,N,SX1])c2ccccc12",
				"[!$(C=C);!$(C#C)]C(=[O,SX1,N])[O,S,N][CX4,O,S][$(C=O),a,$(C=C),$(C#C),$(C=N),$(C#N)]"
			};
		}
		@Override
		public String getExample(boolean value) {
			return value?"Cc1oc2ccccc2n1":"C";
		}
		@Override
		public String getShortName() {
			return "acyl";
		}
	},
	SN2 {
		@Override
		public String getCategoryExplanation() {
			return "Structural Alert Identified for potential second order nucleophilic aliphatic substitution reaction.";
		}
		@Override
		public String getCategoryTitle() {
			return "Alert for SN2 identified.";
		}
		@Override
		public String getTitle() {
			return "SN2-Nucleophilic Aliphatic Substitution";
		}
		@Override
		public String getExplanation() {
			return "to be a substrate for second-order nucleophilic substitution, or a precursor for it.";
		}
		@Override
		public String[] getSMARTS() {
			return new String[] {
				"[CH,CH2,CH3;!$([CH2]CC=[O,S])][F,Cl,Br,I,$(OS(=O)(=O)[#6,#1]),$(OS(=O)(=O)O[#6,#1])]",
				"[#6]1[O,N,SX2][#6]1",
				"C1C(=[O,S])[O,S][CH2,CH]1",
				"[$(C=C),$(C#C),a][CH2,CH][O,S][$([CH]=O),$([CH]=S),$(C(C)=O),$(C(C)=S),a]",
				"[#6]1=,:[#6]C(=[O,SX1])N[SX2]1",
				"[#6][O,SX2,N][O,SX2,N][$([CH]=O),$([CH]=S),$(C(C)=O),$(C(C)=S),a]",
				"[CH2,CH3][NX3][NX2]=[O,S]",
				"c1ccc2cc3ccccc3cc2c1",
				"c1ccc2c(c1)ccc3ccccc23"
			};
		}
		@Override
		public String getExample(boolean value) {
			return value?"c1ccc2cc3ccccc3cc2c1":"C";
		}
		@Override
		public String getShortName() {
			return "SN2";
		}
	},
	NO_ALERTS {
		@Override
		public String getCategoryExplanation() {
			return "No structural alerts have been identified.";
		}
		@Override
		public String getCategoryTitle() {
			return "No skin sensitisation alerts identified.";
		}
		@Override
		public String getTitle() {
			return getTitle();
		}
		@Override
		public String getExplanation() {
			return getCategoryExplanation();
		}
		@Override
		public String[] getSMARTS() {
			return null;
		}
		@Override
		public String getExample(boolean arg0) {
			return null;
		}
		@Override
		public String getShortName() {
			return "Non";
		}
	};
	public String getID() {
		return toString();
	}
	public abstract String getTitle();
	public abstract String getExplanation();
	public abstract String getCategoryTitle();
	public abstract String getCategoryExplanation();
	public abstract String[] getSMARTS();
	public abstract String getExample(boolean value);
	public abstract String getShortName();
	
}
