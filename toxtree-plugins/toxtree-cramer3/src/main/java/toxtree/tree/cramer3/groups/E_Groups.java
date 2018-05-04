package toxtree.tree.cramer3.groups;

public enum E_Groups {
	alcohol {
		@Override
		public String getSMARTS() {
			return "[#6]!@[OX2H]";
		}
	},
	methanol {
		@Override
		public String getSMARTS() {
			return "[C;H3][OX2H]";
		}

		@Override
		public int natoms(int ngroups) {
			return ngroups * 2;
		}

	},
	primary_alcohol {
		@Override
		public String getSMARTS() {
			return "[#6][C;H2][OX2H]";
		}
	},
	secondary_alcohol {
		@Override
		public String getSMARTS() {
			return "[#6][C;$([C][#6])][OX2H]";
			//return "[#6][C;$([CX4H][#6])][OX2H]";
		}

		@Override
		public int natoms(int ngroups) {
			return ngroups * 2;
		}
	},
	tertiary_alcohol {
		@Override
		public String getSMARTS() {
			return "[#6][C;$([CX4H0]([#6])[#6])][OX2H]";
		}

		@Override
		public int natoms(int ngroups) {
			return ngroups * 3;
		}
	},

	aliphatic_chain {
		@Override
		public String getSMARTS() {
			return "C!@C";

		}
	},
	mercaptan {
		@Override
		public String getSMARTS() {
			return "[SX2H1][#6]";
		}
	},
	thioester {
		@Override
		public String getSMARTS() {
			return "[C;$(C[#6])](=[OX1])[S][#6]";
		}
	},
	alpha_diketone {
		@Override
		public String getSMARTS() {
			return "[#6][#6X3](=[OX1])[#6X3](=[OX1])[#6]";
		}
	},
	polysulfide {
		@Override
		public String getSMARTS() {
			return "[#6,#16][#16X2H0][#16X2H0][#6,#16]";
		}
	},
	ketone {
		@Override
		public String getSMARTS() {
			return "[#6][CX3](=O)[#6]";
		}
	},
	ketone_16B {
		@Override
		public String getSMARTS() {
			return "[C][CX3;!$([C][C][C][C;H0,H1])](=O)[C]";
			}

		@Override
		public int natoms(int ngroups) {
			return ngroups * 4;
		}
	},
	ketal {
		@Override
		public String getSMARTS() {
			//return "[$([CX4]([#6,!$([CH0]([C])[C]),!$([C][CH0]([C])[C]),!$([C][C][CH0]([C])[C])])[#6,!$([CH0]([C])[C]),!$([C][CH0]([C])[C]),!$([C][C][CH0]([C])[C])])]([O][#6])([O][#6])";
			return "[CX4;!$([C][C][C][C;H0,H1])]([#6])([#6])([O][#6])([O][#6])";
		}
	},
	sulfide {
		@Override
		public String getSMARTS() {
			return "*[SX2H0;!$([S][#6](~[O])[#6]);!$([S]=[OX1])]";
		}
	},
	primary_amine {
		// "primary amine (aromatic or aliphatic)",
		@Override
		public String getSMARTS() {
			return "[$([NX3H2])][#6]";
		}
	},
	aldehyde {
		@Override
		public String getSMARTS() {
			return "[CX3H1](=O)[#6]";
		}

		@Override
		public int natoms(int ngroups) {
			return ngroups * 3;
		}
	},
	lactone {
		@Override
		public String getSMARTS() {
			return "[C;R1](=[OX1])[O;R1]";
		}
	},
	isoprene {
		@Override
		public String getSMARTS() {
			return "[C][C]-,=[C](C)[C][C]-,=[C]-,=[C]([C])-,=[C]";
		}
	},
	acetylenic {
		@Override
		public String getSMARTS() {
			return "C#C";
		}
	},
	carboxylic_acid {
		@Override
		public String getSMARTS() {
			return "[C;H1,$(C[#6])](=[OX1])[OH]";
		}

		@Override
		public int natoms(int ngroups) {
			return ngroups * 4;
		}
	},
	ester {
		@Override
		public String getSMARTS() {
			return "[C;H1,$(C[#6])](=[OX1])[O][#6]";
		}

		@Override
		public int natoms(int ngroups) {
			return ngroups * 5;
		}
	},
	acetal {
		@Override
		public String getSMARTS() {
			return "[$([CX4H][#6]),$([CX4H2])]([O][#6])([O][#6])";
		}
	},
	polyoxyethylene {
		@Override
		public String getSMARTS() {
			return "[OX2H1][CX4H2][CX4H2][OX2;$([O][CX4H2][CX4H2][OX2H1]),$([O][CX4H2][CX4H2][OX2][CX4H2][CX4H2][OX2H1])]";
		}
	}

	;
	public abstract String getSMARTS();

	public int natoms(int ngroups) {
		return ngroups;
	}
}
