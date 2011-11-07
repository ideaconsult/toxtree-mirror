package toxtree.plugins.dnabinding.categories;

public enum DNABindingAlerts {
	SN1 {
		@Override
		public String getCategoryExplanation() {
			return "SN1";
		}
		@Override
		public String getCategoryTitle() {
			return "Alert for SN1 Identified.";
		}
		@Override
		public String getTitle() {
			return "SN1";
		}
		@Override
		public String getExplanation() {
			return "???????";
		}
		
		@Override
		public String[][] getSMARTS() {
			return new String[][] {
					{"SNA1","Hydrazine",
					 "[NH][NH2]"
					},
					{"SNA1","Aliphatic-N-nitro",
						 "[CH3,$([CH2]([NX3])[#6]),$([CH]([NX3])([#6])[#6])][NH,$(N([CX4])([NX3])[#6])][N+](=O)[O-]"
						},
					{"SNA1","Hydrazine",
						 "[NH][NH2]"
					},
					{"SNA1","Triazines",
						"[NH2,$([NH](N=N)[#6]),$(N(N=N)([#6])[#6])]N=N[CX4]",
						
					},
					{"SNA1","Diazoalkanes",
						 "[$([C-][N+]#N),$(C=N=N)]"
					},
					{"SNA1","PAHs",
						 "[cH]1[cH][cH]c2c(ccc3ccccc23)[cH]1"
						},
					{"SNA1","Nitroso",
						 "[CH3,$([CH2]([NX3])[#6]),$([CH]([NX3])([#6])[#6])][NH,$(N([CX4])([NX3])[#6])][NX2]=O"
							},
					{"SNA1","Allyl-benzenes",
						 "C=C[CH2]c1ccccc1"
					},	
					{"SNA1","Pyrroloizidine-alkaloids",
						 "[$([CX4]C(=O)OCc1ccn2CCC(OC([CX4])=O)c12),$([CX4]C(=O)OCC1=CCN2CCC(OC([CX4])=O)C12),$([CX4]C(=O)OC\\C1=C\\CN([CH3])CCC(OC([CX4])=O)C1=O)]"
					},
					{"SNA1","a-Halo-ethers",
						 "[#6][O,S][CH2,$([CH]([O,S])([F,Cl,Br,I])[CX4]),$(C([O,S])([F,Cl,Br,I])([CX4])[CX4])][F,Cl,Br,I]"
						},
					{"SNA1","Primary-aromatic-amines",
						 "[$([c][NH2]);r6;!$([c]([a]C)([a]C)[NH2]);!$([c]([c]C(=O)[OH])[NH2]);!$([c]([c]S(=O)(=O)[OH])[NH2]);!$([c]([a][c]S(=O)(=O)[OH])[NH2]);!$([c]([a][a][c]S(=O)(=O)[OH])[NH2])]"
							},
					{"SNA1","Secondary-aromatic-amines",
						 "[$([c][NH][#6]);r6;!$([c]([a]C)([a]C)[NH][#6]);!$([c]([c]C(=O)[OH])[NH][#6]);!$([c]([c]S(=O)(=O)[OH])[NH][#6]);!$([c]([a][c]S(=O)(=O)[OH])[NH][#6]);!$([c]([a][a][c]S(=O)(=O)[OH])[NH][#6])]"
					},
					{"SNA1","Tertiary-aromatic-amines",
						 "[$([c]N([CH3])[CH3]);r6;!$([c]([a]C)([a]C)N([CH3])[CH3]);!$([c]([c]C(=O)[OH])N([CH3])[CH3]);!$([c]([c]S(=O)(=O)[OH])N([CH3])[CH3]);!$([c]([a][c]S(=O)(=O)[OH])N([CH3])[CH3]);!$([c]([a][a][c]S(=O)(=O)[OH])N([CH3])[CH3])]"
					},
					{"SNA1","Aromatic-nitros",
						 "[$([c][N+](=O)[O-]);r6;!$([c]([a]C)([a]C)[N+](=O)[O-]);!$([c]([c]C(=O)[OH])[N+](=O)[O-]);!$([c]([c]S(=O)(=O)[OH])[N+](=O)[O-]);!$([c]([a][c]S(=O)(=O)[OH])[N+](=O)[O-]);!$([c]([a][a][c]S(=O)(=O)[OH])[N+](=O)[O-])]"
						},
					{"SNA1","Aromatic-nitrosos",
						 "[$([c][NX2]=O);r6;!$([c]([a]C)([a]C)[NX2]=O);!$([c]([c]C(=O)[OH])[NX2]=O);!$([c]([c]S(=O)(=O)[OH])[NX2]=O);!$([c]([a][c]S(=O)(=O)[OH])[NX2]=O);!$([c]([a][a][c]S(=O)(=O)[OH])[NX2]=O)]"
							},
					{"SNA1","Aromatic-N-hydroxylamines",
						 "[$(c[NH][OH]);r6]"
					},
					{"SNA1","Aromatic-azos",
						 "[$([c]N=[NH,$(N(=N)[#6])]);r6;!$([c]([a]C)([a]C)N=[NH,$(N(=N)[#6])]);!$([c]([c]C(=O)[OH])N=[NH,$(N(=N)[#6])]);!$([c]([c]S(=O)(=O)[OH])N=[NH,$(N(=N)[#6])]);!$([c]([a][c]S(=O)(=O)[OH])N=[NH,$(N(=N)[#6])]);!$([c]([a][a][c]S(=O)(=O)[OH])N=[NH,$(N(=N)[#6])])]"
					},
					{"SNA1","Aromatic-phenylureas",
						 "[$([c][NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]);r6;!$([c]([a]C)([a]C)[NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]);!$([c]([c]C(=O)[OH])[NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]);!$([c]([c]S(=O)(=O)[OH])[NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]);!$([c]([a][c]S(=O)(=O)[OH])[NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]);!$([c]([a][a][c]S(=O)(=O)[OH])[NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])])]"
					},
					{"SNA1","Aromatic-ester-hydroxylamines",
						 "[$([c][NH]OC(=O)[#6]);r6;!$([c]([a]C)([a]C)[NH]OC(=O)[#6]);!$([c]([c]C(=O)[OH])[NH]OC(=O)[#6]);!$([c]([c]S(=O)(=O)[OH])[NH]OC(=O)[#6]);!$([c]([a][c]S(=O)(=O)[OH])[NH]OC(=O)[#6]);!$([c]([a][a][c]S(=O)(=O)[OH])[NH]OC(=O)[#6])]"
					},
					{"SNA1","Primary-heterocyclic-amines",
						 "[$(c[NH2]);r5]"
					},
					{"SNA1","Secondary-heterocyclic-amines",
						 "[$(c[NH][#6]);r5]"
					},
					{"SNA1","Tertiary-heterocyclic-amines",
						 "[$(cN([CH3])[CH3]);r5]"
					},
					{"SNA1","Heterocyclic-nitro",
						 "[$(c[N+](=O)[O-]);r5]"
					},
					{"SNA1","Heterocyclic-nitroso",
						 "[$(c[NX2]=O);r5]"
					},
					{"SNA1","Heterocyclic-N-hydroxylamines",
						 "[$(c[NH][OH]);r5]"
					},
					{"SNA1","Heterocyclic-azos",
						 "[$(cN=[NH,$(N(=N)[#6])]);r5]"
					},
					{"SNA1","Heterocyclic-phenylureas",
						 "[$(c[NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]);r5]"
					},
					{"SNA1","Heterocyclic-ester-hydroxylamine",
						 "[$(c[NH]OC(=O)[#6]);r5]"
					},
					{"SNA1","Nitroso_SN1",
						 "N(c)(c)[NX2]=O"
					},
					{"SNA1","Aliphatic-tertiary-amines",
						 "[NX3](C)(C)C"
					}
			};
		}
		@Override
		public String getExample(boolean value) {
			return value?"N(C)(C)C":"C";
		}		
		@Override
		public String getShortName() {
			return "SN1";
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
					"Direct acting Schiff base formers",	
					"Mono-aldehydes",
					"[CH2,$([CH](=O)[CX4])]=O"
					},
					{
					"Direct acting Schiff base formers",	
					"a,b-Dicarbonyl",
					"[CH,$(C(=O)[#6]);!$(C(=O)C=C)](=O)[CH,$(C(=O)[#6]);!$(C(=O)C=C)]=O"
					},	
					{
					"Chemicals activated by P450 to glyoxal",	
					"Ethylenediamines",
					"[$([NH2,$([NH]([CH2])[CX4])][CH2][CH2][NH2]),$([NH]1[CH2][CH2][NH][CH2][CH2]1)]"
					},
					{
					"Chemicals activated by P450 to glyoxal",	
					"Ethanolamines",
					"[$([NH2,$([NH]([CH2])[CX4])][CH2][CH2][OH]),$([NH]1[CH2][CH2]O[CH2][CH2]1)]"
					},
					{
					"Chemicals activated by P450 to mono-aldehydes",
					"N-methylol-derivatives",
					"[NH2,$([NH]([CH2])[#6]),$(N([CH2])([#6])[#6])][CH2][OH]"
					},						
					{
					"Chemicals activated by P450 to mono-aldehydes",
					"Thiazoles",
					"[cH]1ncs[cH]1"
					},	
					{
					"Chemicals activated by P450 to mono-aldehydes",
					"Benzylamines",
					"c1ccccc1[CH2][NH2]"
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
					"Polarised alkenes",
					"a,b-Unsaturated-aldehydes",
					"[$([CH2]),$([CH]C)]=[CH,$(C[CX4])][CH]=O"
					},
					{
					"Polarised alkenes",
					"a,b-Unsaturated-ketones",
					"[$([$([CH2]),$([CH]C)]=[CH,$(C[CX4])]C(=O)[#6]);!$([CH]1=CC(=O)C=CC1(=O));!$([CH]1=CC(=O)C(=O)C=C1)]"
					},			
					{
					"Polarised alkenes",
					"a,b-Unsaturated-esters",
					"[$([CH2]),$([CH]C)]=[CH,$(C[CX4])]C(=O)O[#6]"
					},
					{
					"Polarised alkenes",
					"a,b-Unsaturated-amides",
					"[$([CH2]),$([CH]C)]=[CH,$(C[CX4])]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]"
					},			
					{
					"Polarised azo compounds",
					"Azocarbonamides", //15
					"[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]C(=O)N=NC(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]"
					},		
				
					{
					"Quinones and quinone-type chemicals",
					"Quinones", //29
					"[$(C1(=O)[#6]=,:[#6]C(=O)[CH]=C1),$(C1(=O)C(=O)[#6]=,:[#6][CH]=C1)]"
					},	
					{
					"Quinones and quinone-type chemicals",
					"Quinone-methides", //30
					"[$(C1=CC(=O)[#6]=,:[#6]C1(=[CH][CX4])),$(C1(=[CH][CX4])C(=O)[#6]=,:[#6]C=C1)]"
					},		
					{
					"Quinones and quinone-type chemicals",
					"Quinone-imines", //31
					"[$(C1(=O)[#6]=,:[#6][$(C=[NH]),$(C=N[CX4])][CH]=C1),$(C1(=O)[$(C=[NH]),$(C=N[CX4])][#6]=,:[#6][CH]=C1),$(C1(=O)[$(C=[NH]),$(C=N[CX4])]C=[CH][#6]=,:[#6]1)]"
					},			
					{
					"Quinones and quinone-type chemicals",
					"Quinone-diimines", //32
					"[$([CH]1=C[$(C=[NH]),$(C=N[CX4])][#6]=,:[#6][$(C=[NH]),$(C=N[CX4])]1),$([CH]1=C[$(C=[NH]),$(C=N[CX4])][$(C=[NH]),$(C=N[CX4])][#6]=,:[#6]1)]"
					},	
					{
					"P450 mediated activation to quinones and quinone-type chemicals",
					"Hydroquinones", //33
					"[$(c1([OH,$(O[CH3])])c[cH]c([OH,$(O[CH3])])cc1),$(c1([OH,$(O[CH3])])c([OH,$(O[CH3])])cc[cH]c1)]"
					},	
					{
					"P450 mediated activation to quinones and quinone-type chemicals",
					"Alkyl-phenols", //34
					"[$(c1([OH])ccc([CH3,$([CH2][CX4])])cc1),$(c1([OH])c([CH3,$([CH2][CX4])])cccc1)]"
					},	
					{
					"P450 mediated activation to quinones and quinone-type chemicals",
					"Methylenedioxyphenyl", //35
					"[cH]1ccc(OCO2)c2c1"
					},		
					{
					"P450 mediated activation to quinones and quinone-type chemicals",
					"Arenes", //36
					"[cH,$(c(c)(c)[CX4])]1[cH][cH][cH,$(c(c)(c)[CX4])][cH][cH,$(c(c)(c)[CX4])]1"
					},	
					{
					"P450 mediated activation to quinones and quinone-type chemicals",
					"PAHs", //37
					"[cH]1c[cH]c2cc3ccccc3cc2[cH]1"
					},
					{
					"P450 mediated activation to quinones and quinone-type chemicals",
					"5-Alkoxyindoles", //38
					"[cH]1cc([OH,$(O[CH3])])cc2ccnc12"
					},
					{
					"P450 mediated activation to quinones and quinone-type chemicals",
					"3-Methylindoles", //38
					"[CH3]c1c[nH]c2ccccc12"
					},
					{
					"P450 mediated activation of heterocyclic ring systems",
					"Furans", //39
					"[cH]1[cH,$(c(c)(o)C)]o[cH,$(c(c)(o)C)][cH,$(c(c)(c)C)]1"
					},
					{
					"P450 mediated activation of heterocyclic ring systems",
					"Thiophenes_MA", //39
					"[cH]1[cH,$(c(c)(o)C)]s[cH,$(c(c)(o)C)][cH,$(c(c)(c)C)]1"
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
					{"Direct addition of an acyl halide",
					 "Acyl halide", //1
					 "[CH,$(C(=O)[#6])](=O)[F,Cl,Br,I]"
					},
					{"Direct addition of an acyl halide",
					 "Alkyl carbamyl halides", //2
					 "[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]C(=O)[F,Cl,Br,I]"
					},
					{"Isocyanates and isothiocyanates",
					 "Isocyanates", //3
					 "[NH,$(N(=C=O)[#6])]=C=O"
					},
					{"Isocyanates and isothiocyanates",
						"Isothiocyanates", //3
						"[NH,$(N(=C=[SX1])[#6])]=C=[SX1]"
					},
						
					{"P450 mediated activation to isocyanates or isothiocyanates",
					 "Thiazolidinediones", //5
					 "C1C(=O)[NH]C(=O)S1"
					},		
					{"P450 mediated activation to isocyanates or isothiocyanates",
					 "Formamides", //6
					 "[NH2,$([NH]([CH]=O)[#6]),$(N([CH]=O)([#6])[#6])][CH](=O)"
					},
					{"P450 mediated activation to isocyanates or isothiocyanates",
					 "Sulfonylureas", //7
					 "[#6]S(=O)(=O)[NH]C(=O)[NH2,$([NH](C=O)[#6]),$(N(C=O)([#6])[#6])]"
					},
					{"P450 mediated activation to isocyanates or isothiocyanates",
						 "Thioureas_AC", //8
						 "c[NH]C(=O)[NH]c"
					},
					{"P450 mediated activation to isocyanates or isothiocyanates",
						 "Benzylamines", //9
						 "c1ccccc1[CX4][NH2]"
					},
					{"P450 mediated activation to acyl halides",
						 "1,1-Dihaloalkanes", //10
						 "[CX4][CH]([F,Cl,Br,I])[F,Cl,Br,I]"
					},		
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
					{"P450 mediated epoxidation",
						 "Thiophenes_SN2", //1
						 "[cH,$(c(c)(c)C)]1[cH,$(c(c)(c)C)]s[cH][cH]1"
					},
					{"P450 mediated epoxidation",
						 "Coumarins", //2
						 "[cH]1[cH]c(=O)oc2ccccc12"
					},			
					{"Episulfonium ion formation",
						 "Mustards", //3
						 "[NX3,SX2][CH2,$([CH]([NX3,SX2])([CX4])[CX4])][CH2,$([CH]([CX4])([Cl,Br,I])[CX4])][Cl,Br,I]"
					},
					{"Episulfonium ion formation",
						 "1,2-Dihaloalkanes", //
						 "[Cl,Br,I][CH2,$([CH]([Cl,Br,I])([CX4])[CX4])][CH2,$([CH]([Cl,Br,I])([CX4])[CX4])][Cl,Br,I]"
					},	
					{"SN2 at an sp3 carbon atom",
						 "Alkyl-nitrate", //
						 "[CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]O[NX2]=O"
					},
					{"SN2 at an sp3 carbon atom",
						 "Alkyl-carbamates", //4
						 "[CH3,$([CH2]([NX3])[CX4]),$([CH]([NX3])([CX4])[CX4])][NX3]([CH3])C(=[O,SX2])[O,SX2][CX4]"
					},		
					{"SN2 at an sp3 carbon atom",
						 "Aliphatic-halide", //5
						 "[CH3,$([CH2]([F,Cl,Br,I])[#6]),$([CH]([F,Br,Cl,I])([#6])[#6]);!$([CH2,CH]C=O);!$([CH2,CH][CX4][NX3,SX2]);!$([CH2,CH][CX4][F,Cl,Br,I])][F,Cl,Br,I]"
					},		
					{"SN2 at an sp3 carbon atom",
						 "Sulfonates", //6
						 "[#6]S(=O)(=O)O[CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},	
					{"SN2 at an sp3 carbon atom",
						 "Sulfates", //7
						 "[#6]OS(=O)(=O)O[CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},	
					{"SN2 at an sp3 carbon atom",
						 "Phopshates", //8
						 "[#6][O,SX2]P(=[O,SX1])([O,SX2][#6])[O,SX2][CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},	
					{"SN2 at an sp3 carbon atom",
						 "Phosphonates", //9
						 "[#6][O,SX2]P(=[O,SX1])([#6])[O,SX2][CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},		
					{"SN2 at an sp3 carbon atom",
						 "Phosphonic-esters", //10
						 "[#6][PX4](=O)O[CH3,$([CH2](O)[#6]),$([CH](O)([#6])[#6])]"
					},	
					{"Ring opening SN2",
						 "Lactones", //11
						 "C1CC(=O)O1"
					},				
					{"Ring opening SN2",
						 "Sultones", //12
						 "[$(C1OS(=O)(=O)CC1),$(C1OS(=O)(=O)CCC1)]"
					},		
					{"Nitrosation",
						 "Nitrosos_SN2", //13
						 "[CX4][NX3]([#6])[NX2]=O"
					},	
					{"Direct acting epoxides and related",
						 "Epoxides", //14
						 "C1OC1"
					},	
					{"Direct acting epoxides and related",
						 "Aziridines", //14
						 "C1[NX3]C1"
					},
					{"Direct acting epoxides and related",
						 "Sulfaranes", //14
						 "C1[SX2]C1"
					},					
					{"Epoxidation of aliphatic alkenes",
						 "Halogenated-polarised-alkenes", //15
						 "[CH2,$([CH](=[CH])[#6,F,Cl,Br,I])]=[CH][F,Cl,Br,I,$(O(C=C)[a])]"
					},		
					{"SN2 at a nitrogen atom",
						 "N-acyloxy-N-alkoxyamides", //16
						 "[a]C(=O)N(O[#6])OC(=O)[#6]"
					},	
					{"P450 mediated sulfoxidation ",
						 "Thioureas_SN2", //17
						 "[#6][NH]C(=S)[NH][#6]"
					}	
					};
		}
		@Override
		public String getExample(boolean value) {
			return value?"c1ccsc1":"C";  //thiophene
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
			return "No protein binding alerts identified.";
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
