package toxtree.plugins.proteinbinding.categories;

public enum ProteinBindingAlerts {
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
		public String[][] getSMARTS() {
			return new String[][] {
					{"SNAr","Activated-benzenes",
					 "[$(c1([F,Cl,Br,$(OC(F)(F)F),$(C#N)])c([N+](=O)[O-])cc([N+](=O)[O-])cc1),$(c1(F)c([N+](=O)[O-])cc([$([CH]=O),$(C(=O)[CH3]),$(C(F)(F)F)])cc1),$(c1(Cl)c([N+](=O)[O-])cc([CH]=O)cc1),$(c1(F)c([$(C#N),$([CH]=O)])cc([N+](=O)[O-])cc1),$(c1([$(OC(F)(F)F),Br,$(C#N)])c([N+](=O)[O-])cc([CH]=O)cc1),$(c1([F,Cl,Br,$(OC(F)(F)F),$(C#N)])cc([N+](=O)[O-])c([N+](=O)[O-])cc1),$(c1([F,Cl,Br,$(OC(F)(F)F),$(C#N)])c([N+](=O)[O-])cccc1([N+](=O)[O-]))]"
					},
					{"SNAr","Activated-poly-fluorobenzenes",
						 "[$(c1([$([N+](=O)[O-]),$([CH]=O),$(C#N),$(C(=O)[CH3])])c(F)c(F)c(F)c(F)c1(F)),$(c1([$([N+](=O)[O-]),$([CH]=O),$(C#N)])c(F)c(F)c(F)c(F)c1),$(c1([$([N+](=O)[O-]),$([CH]=O),$(C#N)])c(F)c(F)c(F)cc1F),$(c1([$([N+](=O)[O-]),$([CH]=O),$(C#N)])c(F)c(F)cc(F)c1F),$(c1([N+](=O)[O-])c(F)c(F)c(F)cc1),$(c1([N+](=O)[O-])c(F)c(F)cc(F)c1),$(c1([N+](=O)[O-])c(F)c(F)ccc1F),$(c1([N+](=O)[O-])c(F)cc(F)cc1F)]"
						},
					{"SNAr","Activated-pyridines",
						 "[$(c1([F,Cl,Br,$(C#N),$(OC(F)(F)F)])ncc([$(N(=O)=O),$(C#N),$([CH]=O),$(C(=O)[CH3])])cc1),$(c1([F,Cl,Br,$(C#N),$(OC(F)(F)F)])ncccc1[$(N(=O)=O),$(C#N),$([CH]=O),$(C(=O)[CH3])]),$(c1([F,Cl,Br,$(C#N),$(OC(F)(F)F)])ccncc1[$(N(=O)=O),$(C#N),$([CH]=O),$(C(=O)[CH3])])]"
						}
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
		public String[][] getSMARTS() {
			return new String[][] {
					{
					"Direct-acting-Schiff-base-formers",	
					"Mono-aldehydes",
					"[CH2,$([CH](=O)[CX4]),$([CH](=O)c1a(*)caaa1),$([CH](=O)c1a(*)aaa1)]=O"
					},
					{
					"Direct-acting-Schiff-base-formers",
					"Disubstituted-polarised-aldehydes",
					"[$(C(=C)([#6])[#6]);!$(C(=C)([#6])C=[CH2,CH])]=C[CH]=O"
					},	
					{
					"Direct-acting-Schiff-base-formers",
					"1,2-Dicarbonyl",
					"[CH,$(C(=O)(C=O)[CX4]),$(C(=O)(C=O)c1a(*)aaaa1),$(C(=O)(C=O)c1a(*)aaa1)](=O)[CH,$(C(=O)(C=O)[#6]);!$(C(=O)(C=O)C=[CH2,CH]);!$(C(=O)(C=O)C#C)]=O"
					},
					{
					"Direct-acting-Schiff-base-formers",
					"1,3-Dicarbonyl",
					"[CH,$(C(=O)([CX4]C=O)[CX4]),$(C(=O)([CX4]C=O)c1a(*)aaaa1),$(C(=O)([CX4]C=O)c1a(*)aaa1)](=O)[CX4][CH,$(C(=O)([CX4]C=O)[#6]);!$(C(=O)([CX4]C=O)C=[CH2,CH]);!$(C(=O)([CX4]C=O)C#C)]=O"
					},
					{
					"Pro-Schiff-base-formers-(glyoxal)",
					"Ethylenediamines",
					"[$([NH2,$([NH]([CH2])[CX4])][CH2][CH2][NH2]),$([NH]1[CH2][CH2][NH][CH2][CH2]1)]"
					},						
					{
					"Pro-Schiff-base-formers-(glyoxal)",
					"Ethanolamines",
					"[$([NH2,$([NH]([CH2])[CX4])][CH2][CH2][OH]),$([NH]1[CH2][CH2]O[CH2][CH2]1)]"
					},	
					{
					"Pro-Schiff-base-formers-(mono-aldehydes)",	
					"Pro-disubstituted-polarised-aldehydes (disubstituted allyl alcohols)",
					"C([#6])([#6])=[CH,$(C(=C)([CH2][OH])[#6])][CH2][OH]"
					},						
					
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
		public String[][] getSMARTS() {
			return new String[][] {
					{
					"Polarised-alkenes",
					"Polarised-alkene-aldehydes",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])][CH]=O"
					},
					{
					"Polarised-alkenes",
					"Polarised-alkene-ketones",
					"[$([$([CH2]),$([CH][#6])]=[CH,$(C[#6])]C(=O)[#6]);!$([CH]1=CC(=O)C=CC1(=O));!$([CH]1=CC(=O)C(=O)C=C1)]"
					},			
					{
					"Polarised-alkenes",
					"Polarised-alkene-esters",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])]C(=O)O[#6]"
					},
					{
					"Polarised-alkenes",
					"Polarised-alkene-amides",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]"
					},			
					{
					"Polarised-alkenes",
					"Polarised-alkene-nitros",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])][N+](=O)[O-]"
					},		
					{
					"Polarised-alkenes",
					"Polarised-alkene-cyano",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])]C#N"
					},		
					{
					"Polarised-alkenes",
					"Polarised-alkene-sulfonate",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])]S(=O)(=O)O[#6]"
					},	
					{
					"Polarised-alkenes",
					"Polarised-alkene-sulfone",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])]S(=O)(=O)[#6]"
					},			
					{
					"Polarised-alkenes",
					"Polarised-alkene-sulfinyl",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])][SX3](=O)[#6]"
					},			
					{
					"Polarised-alkenes",
					"Polarised-alkene-oximes",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])][SX3](=O)[#6]"
					},	
					{
					"Polarised-alkenes",
					"Polarised-alkene-pyridines",
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])][$(c1ncccc1),$(c1ccncc1)]"
					},	
					{
					"Polarised-alkenes",
					"Polarised-alkene-pyrazines", //12
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])]c1nccnc1"
					},	
					{
					"Polarised-alkenes",
					"Polarised-alkene-pyrimidines", //13
					"$([CH2]),$([CH][#6])]=[CH,$(C[#6])][$(c1ncncc1),$(c1ncccn1),$(c1cncnc1)]"
					},		
					{
					"Polarised-alkenes",
					"Polarised-akene-triazines", //14
					"[$([CH2]),$([CH][#6])]=[CH,$(C[#6])][$(c1ncncn1),$(c1nncnc1),$(c1nnccn1),$(c1cnncn1),$(c1nnncc1),$(c1cnnnc1)]"
					},	
					{
					"Polarised-azo-compounds",
					"Azocarbonamides", //15
					"[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]C(=O)N=NC(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]"
					},		
					{
					"Polarised-alkynes",
					"Polarised-alkyne-aldehydes", //16
					"[CH,$(C(#C)[#6])]#C[CH]=O"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-ketones", //17
					"[CH,$(C(#C)[#6])]#CC(=O)[#6]"
					},
					{
					"Polarised-alkynes",
					"Polarised-alkyne-esters", //18
					"[CH,$(C(#C)[#6])]#CC(=O)O[#6]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-amides", //19
					"[CH,$(C(#C)[#6])]#CC(=O)[$([NH2]),$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-nitros", //20
					"[CH,$(C(#C)[#6])]#C[N+](=O)[O-]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-cyano", //21
					"[CH,$(C(#C)[#6])]#CC#N"
					},		
					{
					"Polarised-alkynes",
					"Polarised-alkyne-sulfonate", //22
					"[CH,$(C(#C)[#6])]#CS(=O)(=O)O[#6]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-sulfone", //23
					"[CH,$(C(#C)[#6])]#CS(=O)(=O)[#6]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-sulfinyl", //24
					"[CH,$(C(#C)[#6])]#C[SX3](=O)[#6]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-pyridines", //25
					"[CH,$(C(#C)[#6])]#C[$(c1ncccc1),$(c1ccncc1)]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-pyrazine", //26
					"[CH,$(C(#C)[#6])]#Cc1nccnc1"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-pyrimidine", //27
					"[CH,$(C(#C)[#6])]#C[$(c1ncccn1),$(c1cncnc1),$(c1ncncc1)]"
					},	
					{
					"Polarised-alkynes",
					"Polarised-alkyne-triazines", //28
					"[CH,$(C(#C)[#6])]#C[$(c1ncncn1),$(c1nncnc1),$(c1nnccn1),$(c1cnncn1)]"
					},	
					{
					"Quinones-and-quinone-type-chemicals",
					"Quinones", //29
					"[$(C1(=O)[#6]=,:[#6]C(=O)[CH]=C1),$(C1(=O)C(=O)[#6]=,:[#6][CH]=C1)]"
					},	
					{
					"Quinones-and-quinone-type-chemicals",
					"Quinone-methides", //30
					"[$(C1=CC(=O)[#6]=,:[#6]C1(=[CH][CX4])),$(C1(=[CH][CX4])C(=O)[#6]=,:[#6]C=C1)]"
					},		
					{
					"Quinones-and-quinone-type-chemicals",
					"Quinone-imines", //31
					"[$(C1(=O)[#6]=,:[#6][$(C=[NH]),$(C=N[CX4])][CH]=C1),$(C1(=O)[$(C=[NH]),$(C=N[CX4])][#6]=,:[#6][CH]=C1),$(C1(=O)[$(C=[NH]),$(C=N[CX4])]C=[CH][#6]=,:[#6]1)]"
					},			
					{
					"Quinones-and-quinone-type-chemicals",
					"Quinone-diimines", //32
					"[$([CH]1=C[$(C=[NH]),$(C=N[CX4])][#6]=,:[#6][$(C=[NH]),$(C=N[CX4])]1),$([CH]1=C[$(C=[NH]),$(C=N[CX4])][$(C=[NH]),$(C=N[CX4])][#6]=,:[#6]1)]"
					},	
					{
					"Quinones-and-quinone-type-chemicals",
					"Pyranones", //33
					"[$([cH]1cc(=[O,NH])cc[o,n]1),$([cH]1cc(=[O,NH])[o,n]cc1),$(c1cc(=[O,NH])[o,n][cH]c1)]"
					},	
					{
					"Acid-imides",
					"Acid-imides", //34
					"O=C1[NH,$(N(C=O)(C=O)[#6])]C(=O)[CH,$(C(C=O)(=C)[#6])]=[CH]1"
					},	
					{
					"Pre-quinones-and-quinone-type-chemicals",
					"Hydroquinones", //35
					"[$(c1([OH,$(O[CH3])])c[cH]c([OH,$(O[CH3])])cc1),$(c1([OH,$(O[CH3])])c([OH,$(O[CH3])])cc[cH]c1)]"
					},		
					{
					"Pre-quinones-and-quinone-type-chemicals",
					"Alkyl-phenols", //36
					"[$(c1([OH])ccc([CH3,$([CH2][CX4])])cc1),$(c1([OH])c([CH3,$([CH2][CX4])])cccc1)]"
					},	
					{
					"Pre-quinones-and-quinone-type-chemicals",
					"Aminophenols", //37
					"[$(c1([OH])c[cH]c([NH2])cc1),$(c1([OH])c([NH2])cc[cH]c1)]"
					},
					{
					"Pre-quinones-and-quinone-type-chemicals",
					"Benzenediamines", //38
					"[$(c1([NH2])c[cH]c([NH2])cc1),$(c1([NH2])c([NH2])cc[cH]c1)]"
					},
															{
					"Pre-polarised-alkenes",
					"Pre-polarised-alkenes-aldehydes (allyl alcohols)", //39
					"[CH2,$([CH](=C)[#6])]=[CH,$(C(=C)([CH2][OH])[#6])][CH2][OH]"
					},
															{
					"Pre-polarised-alkynes",
					"Pre-polarised-alkynes-aldehydes (propargyl alcohols)", //40
					"[CH,$(C(#C)[#6])]#C[CH2][OH]"
					},					
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
		public String[][] getSMARTS() {
			return new String[][] {
					{"Direct-acylation-involving-a-leaving-group",
					 "Acyl-halides", //1
					 "[#6,#1]C(=O)[F,Cl,Br,I]"
					},
					{"Direct-acylation-involving-a-leaving-group",
					 "Acetates", //2
					 "[#6,#1]C(=[O,SX2])[O,SX2,NX3][$([a;r6]),$([a;r5]),$(C([O,SX2,NX3])=C),$(C([O,SX2,NX3])#C)]"
					},
					{"Direct-acylation-involving-a-leaving-group",
					 "Anhydrides", //3
					 "[#6,#1]C(=[O,SX2])[O,SX2]C(=[O,SX2])[#6,#1]"
					},
					{"Direct-acylation-involving-a-leaving-group",
					 "Azlactones", //4
					 "C1=[O,SX2,NX2,CX3]CC(=[O,SX2])[O,SX2,NX3]1"
					},
					{"Direct-acylation-involving-a-leaving-group",
					 "Sulphonyl-halides", //5
					 "[#6,#1]S(=O)(=O)[F,Cl,Br,I,$(C#N)]"
					},		
					{"Direct-acylation-involving-a-leaving-group",
					 "Phosphonic-acid-halides", //6
					 "[#6]OP(=O)(O[#6])[F,Cl,Br,I,$(C#N)]"
					},
					{"Direct-acylation-involving-a-leaving-group",
					 "Dialkyl-carbamyl-halides", //7
					 "N([#6])([#6])C(=O)[F,Cl,Br,I]"
					},
					{"Ring-opening-acylation",
						 "b-Lactones", //8
						 "C1C(=O)OC1"
					},
					{"Ring-opening-acylation",
						 "Thio-lactones", //9
						 "C1C(=O)SC1"
					},
					{"Ring-opening-acylation",
						 "a-Lactams", //10
						 "C1C(=O)NC1"
					},		
					{"Ring-opening-acylation",
						 "Cyclopropenones", //11
						 "C1([#6,#1])=C([#6,#1])C1(=O)"
					},	
					{"Isocyanates-and-related",
						 "Isocyanates", //12
						 "[#6,#1]N=C=O"
					},
					{"Isocyanates-and-related",
						 "Isothiocyanates", //13
						 "[#6,#1]N=C=S"
					},		
					{"Isocyanates-and-related",
						 "Dithiocarbonamide-acid-esters", //14
						 "[#6]N=C(S[#6])S[#6]"
					},
					{"Isocyanates-and-related",
						 "Carbodiimides", //15
						 "[#6]N=C=N[#6]"
					},		
					{"Isocyanates-and-related",
						 "Ketenes", //16
						 "C([#6,#1])([#6,#1])=C=O"
					}					
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
		public String[][] getSMARTS() {
			return new String[][] {
					{"Epoxides-and-related",
						 "Epoxides", //1
						 "C1OC1"
					},
					{"Epoxides-and-related",
						 "Aziridines", //2
						 "C1[NX3]C1"
					},			
					{"Epoxides-and-related",
						 "Sulfaranes", //3
						 "C1[SX2]C1"
					},
					{"Epoxides-and-related",
						 "Epoxypropane", //
						 "C1[CH2,CH]OC1"
					},	
					{"Epoxides-and-related",
						 "Epithiopropane", //
						 "C1[CH2,CH][SX2]C1"
					},
					{"Ring-opening-SN2-reaction",
						 "Lactones", //4
						 "C1CC(=O)O1"
					},		
					{"SN2-reaction-at-a-nitrogen-atom",
						 "Nitrosoureas-(nitrogen)", //5
						 "[NX3]C(=O)[$([NH](C=O)[NX2]=O),$(N(C=O)([#6])[NX2]=O)]"
					},		
					{"SN2-reaction-at-a-nitrogen-atom",
						 "Nitrosoguanidine-(nitrogen)", //6
						 "[NX3]C(=[NH])[$([NH](C=[NH])[NX2]=O),$(N(C=[NH])([#6])[NX2]=O)]"
					},	
					{"SN2-reaction-at-a-nitrogen-atom",
						 "N-acetoxy-N-acetyl-phenyl", //7
						 "[a][NX3](O[$([CH](O)=O),$(C(=O)(O)[#6])])[$([CH](N)=O),$(C(=O)(N)[#6])]"
					},	
					{"SN2-reaction-at-a-nitrogen-atom",
						 "N-acyloxy-N-alkoxyamides", //8
						 "[#6]O[NX3](O[$([CH](O)=O),$(C(=O)(O)[#6])])[$([CH](N)=O),$(C(=O)(N)[#6])]"
					},	
					{"SN2-reaction-at-a-sulphur-atom",
						 "Isothiazol-3-ones-(sulphur)", //9
						 "[cH,$(c(c)([sX2])[#6])]1[cH,$(c(c)(c=O)[#6])]c(=O)[nH,$(n(c=O)([sX2])[#6])][sX2]1"
					},		
					{"SN2-reaction-at-a-sulphur-atom",
						 "Isothiazolin-3-ones-(sulphur)", //10
						 "[CH2,$([CH](C)([SX2])[#6])]1[CH2,$([CH](C)(C=O)[#6])]C(=O)[NH,$(N(C=O)([SX2])[#6])][SX2]1"
					},	
					{"SN2-reaction-at-a-sulphur-atom",
						 "aromatic-sulphonic-acids", //11
						 "[a][SX3](=O)[OH]"
					},				
					{"SN2-reaction-at-a-sulphur-atom",
						 "Thiocyanates", //12
						 "[#6][SX2]C#N"
					},		
					{"SN2-reaction-at-a-sulphur-atom",
						 "Thiols", //13
						 "[#6][SH]"
					},	
					{"SN2-reaction-at-a-sulphur-atom",
						 "Disulfides", //14
						 "[#6][SX2][SX2][#6]"
					},	
					{"SN2-reaction-at-a-sulphur-atom",
						 "Thiosulfonates", //15
						 "[#6][SX2][SX2][#6]"
					},		
					{"SN2-reaction-at-a-sulphur-atom",
						 "Sulfoxides-of-disulfides", //16
						 "[#6][SX2][SX4](=O)(=O)[#6]"
					},	
					{"SN2-reaction-at-a-sulphur-atom",
						 "Sulfenyl-halides", //17
						 "[#6][SX2][F,Cl,Br,I]"
					},		
					/* SN2-reaction-at-a-halo-atom  */ 
					{"SN2-reaction-at-a-halo-atom",
						 "N-chloro-sulphonamides", //18
						 "[#6][SX4](=O)(=O)[NH]Cl"
					},	
					{"SN2-reaction-at-a-halo-atom",
						 "N-haloimides", //19
						 "[CH,$(C(=O)(N)[#6])](=O)[NX3]([F,Cl,Br,I])[CH,$(C(=O)(N)[#6])]=O"
					},	
					/* SN2-reaction-at-a-sp2-carbon  */ 
					{"SN2-reaction-at-a-sp2-carbon",
						 "Polarised-alkenes-with-a-halogen-leaving-group", //20
						 "C=[CH][F,Cl,Br,I]"
					},	
					{"SN2-reaction-at-a-sp2-carbon",
						 "Polarised-alkenes-with-a-sulfonate-leaving-group", //21
						 "C=[CH]OS(=O)(=O)[#6]"
					},	
					{"SN2-reaction-at-a-sp2-carbon",
						 "Polarised-alkenes-with-a-sulfate-leaving-group", //22
						 "C=[CH]OS(=O)(=O)O[#6]"
					},	
					{"SN2-reaction-at-a-sp2-carbon",
						 "Polarised-alkenes-with-a-phosphonate-leaving-group", //23
						 "C=[CH]OP(=[O,SX])(O[#6])[#6]"
					},	
					{"SN2-reaction-at-a-sp2-carbon",
						 "Polarised-alkenes-with-a-phosphate-leaving-group", //24
						 "C=[CH]OP(=[O,SX])(O[#6])O[#6]"
					},	
					/* Episulfonium-ion-formation  */ 
					{"Episulfonium-ion-formation",
						 "Mustards", //25
						 "[NX3,SX2][CH2,$([CH]([NX3,SX2])([CX4])[CX4])][CH2,$([CH]([CX4])([Cl,Br,I])[CX4])][Cl,Br,I]"
					},			
					{"Episulfonium-ion-formation",
						 "1,2-Dihaloalkanes", //26
						 "[Cl,Br,I][CH2,$([CH]([Cl,Br,I])([CX4])[CX4])][CH2,$([CH]([Cl,Br,I])([CX4])[CX4])][Cl,Br,I]"
					},
					/* SN2-at-an-sp3-carbon-atom  */ 
					{"SN2-at-an-sp3-carbon-atom",
						 "Aliphatic-halide", //27
						 "[CH3,$([CH2]([F,Cl,Br,I])[#6]),$([CH]([F,Br,Cl,I])([#6])[#6]);!$([CH2,CH]C=O);!$([CH2,CH][CX4][NX3,SX2]);!$([CH2,CH][CX4][F,Cl,Br,I])][F,Cl,Br,I]"
					},						
					{"SN2-at-an-sp3-carbon-atom",
						 "Sulfates", //28
						 "[#6]OS(=O)(=O)O[CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "Sulfonates", //29
						 "[#6]S(=O)(=O)O[CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "Allyl-acetates-and-related", //30
						 "[#6]C(=[O,SX1])[O,SX2][CH2,$([CH]([O,SX2])[#6])][c,CX3,CX2]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "Nitrosoureas-(carbon)", //31
						 "[NH2,$([NH](C=[O,NH])[#6]),$(N(C=[O,NH])([#6])[#6])]C(=[O,NH])[NX3]([NX2]=O)[CH3,$([CH2](N)[#6]),$([CH](N)([#6])[#6])]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "a-Halocarbonyls", //32
						 "[$([CH]=[O,SX1]),$(C([#6])=[O,SX1])][CH2,$([CH](C=[O,SX1])[#6])][F,Cl,Br,I]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "Phopshates-(inc-thiophosphates)", //33
						 "[#6][O,SX2]P(=[O,SX1])([O,SX2][#6])[O,SX2][CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},						
					{"SN2-at-an-sp3-carbon-atom",
						 "Phosphonates-(inc-thiophosphonates)", //34
						 "[#6][O,SX2]P(=[O,SX1])([#6])[O,SX2][CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "a-Haloethers", //35
						 "[CX4]O[CH2,$([CH](O)([F,Cl,Br,I])[#6])][F,Cl,Br,I]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "b-Haloethers", //36
						 "[CX4]O[CH2,$([CH](O)([CX4])[CX4]),$(C(O)([CX4])([CX4])[CX4])][CH2,$([CH](O)([F,Cl,Br,I])[#6])][F,Cl,Br,I]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "Alkyl-diazo", //37
						 "[CH3,$([CH2](N=N)[#6]),$([CH](N=N)([#6])[#6])][NX2]=[NH,$(N(=N)[#6])]"
					},	
					{"SN2-at-an-sp3-carbon-atom",
						 "a-Haloalkenes", //38
						 "[CH2,$([CH](=C)[#6]),$(C(=C)([#6])[#6])]=[CH,$(C(=C)[#6])][$([CH2](C=C)[F,Cl,Br,I,$(C#N),$(OS(=O)(=O)O[#6]),$(OS(=O)(=O)[#6])]),$([CH](C=C)([#6])[F,Cl,Br,I,$(C#N),$(OS(=O)(=O)O[#6]),$(OS(=O)(=O)[#6])])]"
					},
					{"SN2-at-an-sp3-carbon-atom",
						 "a-Haloalkynes", //39
						 "[CH,$(C(#C)[#6])]#C[$([CH2](C#C)[F,Cl,Br,I,$(C#N),$(OS(=O)(=O)O[#6]),$(OS(=O)(=O)[#6])]),$([CH](C#C)([#6])[F,Cl,Br,I,$(C#N),$(OS(=O)(=O)O[#6]),$(OS(=O)(=O)[#6])])]"
					},		
					{"SN2-at-an-sp3-carbon-atom",
						 "a-Halobenzyls", //40
						 "[c][$([CH2]([c])[F,Cl,Br,I,$(C#N),$(OS(=O)(=O)O[#6]),$(OS(=O)(=O)[#6])]),$([CH]([a])([#6])[F,Cl,Br,I,$(C#N),$(OS(=O)(=O)O[#6]),$(OS(=O)(=O)[#6])])]"
					},							
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
		public String[][] getSMARTS() {
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
	public abstract String[][] getSMARTS();
	public abstract String getExample(boolean value);
	public abstract String getShortName();
	
}
