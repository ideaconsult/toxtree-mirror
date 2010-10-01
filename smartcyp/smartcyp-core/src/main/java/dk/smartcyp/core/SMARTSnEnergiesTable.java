package dk.smartcyp.core;

import java.util.HashMap;


public class SMARTSnEnergiesTable {

	// Local Variables
	private static HashMap<String, Double> SMARTSnEnergiesTable;		// Lookup table in which the SMARTS are key values to retrieve Energies


	/* Matches atoms against SMARTS patterns and assigns predefined energies
	Input is two columnes 1) SMARTS pattern and 2) Energy
	[CX4;!CH0]	29.7
	[CX3;!CH0]	13.3
	[cX3;!CH0]	0.7
	[N]			999.9
	[S]			44.5
	...
	 */
	protected SMARTSnEnergiesTable() {

		// Local Variable
		SMARTSnEnergiesTable = new HashMap<String, Double>();
		
		SMARTSnEnergiesTable.put("[SX2H1]", 41.5);
		SMARTSnEnergiesTable.put("[$([SX2H0]);!$([S][*^2]);!$([S][CX4H0])]", 26.3);
		SMARTSnEnergiesTable.put("[$([SX2H0][*^2]);!$([S](~[^2])[^2]);!$([S][CX4H0])]", 34.4);
		SMARTSnEnergiesTable.put("[$([S][*D4H0]);$([SX2H0])]", 44.4);
		SMARTSnEnergiesTable.put("[$([SX2H0]([*^2])[*^2]);!$([S][CX4H0])]", 46.9);
		SMARTSnEnergiesTable.put("[sX2r5]", 70.0);
		SMARTSnEnergiesTable.put("[$([#16X3](=[OX1]));$([#16]);!$([#16X3](=[OX1])[#6^2](~[#7^2]))]", 30.4);
		SMARTSnEnergiesTable.put("[$([#16X3](=[OX1]));$([#16]);$([#16X3](=[OX1])[#6^2](~[#7^2]))]", 46.9);
		SMARTSnEnergiesTable.put("[$([CX3H1](=O)[#6])]", 40.2);
		SMARTSnEnergiesTable.put("[$([PX4]);$([P]=[S])]", 13.3);
		SMARTSnEnergiesTable.put("[$([CX4]([#6^2])([#6^2])[#6^2]);!$([CH0])]", 33.1);
		SMARTSnEnergiesTable.put("[$([CX4][N]);!$([CH0]);!$([C][N]([*^2])[*^2]);!$([C][N]=[#6X3]);!$([CX4][NX3][C]=[O]);!$([CX4][NX3][#16X4](=[OX1])(=[OX1]));!$([CX4][NX3][N]=[O]);!$([CX4]1[C][C]2[C][C][N]1[C][C]2)]", 39.8);
		SMARTSnEnergiesTable.put("[!$([CH0]);$([CX4]([C^2]~[C])[C^2]~[C]),$([CX4][#7]=[#6X3]),$([CX4]([#8])[#8]);!$([CX4]([#8])[#8][C]=[O])]", 48.5);
		SMARTSnEnergiesTable.put("[$([CX4][S]);!$([CH0]);!$([C][S]=[O])]", 57.7);
		SMARTSnEnergiesTable.put("[$([CX4][#6^2]~[#8]),$([CX4][cr5]),$([CX4]([c])[c]),$([CX4][#6^1]),$([CX4][C^2]=[C^2]-[#6^2]),$([CX4][NX3][N]=[O]);!$([CH0]);!$([CX4][C](=[O])[NX3]);!$([CX4][#6^2](=[#8])-[#8]);!$([CX4][C^2]([C^2])=[C^2]-[#6^2]);!$([CX4][#6^2](=[#8])[#6^2])]", 59.7);
		SMARTSnEnergiesTable.put("[$([CX4][O]);!$([CH0]);!$([C][O][C]=[O]);!$([CX4]1[O][C]1)]", 62.2);
		SMARTSnEnergiesTable.put("[$([CX4][NX3H1][C]=[O]),$([CX4][#7](~[*^2])~[*^2]);!$([CH0]);!$([CX4][NX3H0][C]=[O])]", 63.9);
		SMARTSnEnergiesTable.put("[$([CX4][#6^2]);!$([CH0]);!$([CX4][C](=[O])[NX3])]", 66.4);
		SMARTSnEnergiesTable.put("[$([CX4][S](=[O])=[O]),$([CX4][NX3][#16X4](=[OX1])(=[OX1])),$([CX4][#6^2](=[#8])[#6^2]);!$([CH0])]", 72.3);
		SMARTSnEnergiesTable.put("[CX4;CH1,CH2,$([CH3][NX3,C^2])]", 76.1);
		SMARTSnEnergiesTable.put("[CX4H3]", 89.6);
		SMARTSnEnergiesTable.put("[$([CX3H2]);$([C]=[*^2]-[*^2])]", 40.1);
		SMARTSnEnergiesTable.put("[$([CX3H1]);$([C]=[*^2]-[*^2]);!$([C](-[*^2,a])=[*^2]-[*^2])]", 52.4);
		SMARTSnEnergiesTable.put("[$([ch1r5]:[#8]),$([ch1r5](:[c]):[nH1])]", 52.9);
		SMARTSnEnergiesTable.put("[$([ch1r5](:[nX3]):[nX2])]", 57.9);
		SMARTSnEnergiesTable.put("[$([ch1]1:[c](-[N^3]([C^3])[C^3]):[c]:[c]:[c]:[c]1),$([ch1]1:[c]:[c]:[c](-[N^3]([C^3])[C^3]):[c]:[c]1),$([ch1]1:[c](-[N^3H1][C^3]):[c]:[c]:[c]:[c]1),$([ch1]1:[c]:[c]:[c](-[N^3H1][C^3]):[c]:[c]1),$([ch1]1:[c](-[N^3H2]):[c]:[c]:[c]:[c]1),$([ch1]1:[c]:[c]:[c](-[N^3H2]):[c]:[c]1)]", 59.5);
		SMARTSnEnergiesTable.put("[$([CX3]);$([CX3]=[CX3]);!$([CH0]);!$([CX3](-[*^2])=[CX3]);!$([CX3]=[CX3]-[*^2])]", 65.6);
		SMARTSnEnergiesTable.put("[$([ch1]1:[c]:[c]:[cr6]([#7]~[#6^2]):[c]:[c]1),$([ch1r6]:[cr5]:[nX3r5]);!$([c]1:[c]:[c]:[c](-[N]-[C]=[O]):[c]:[c]1)]", 68.2);
		SMARTSnEnergiesTable.put("[$([ch1r5]:[#16X2]:[c]),$([ch1r5]:[c]:[nX3])]", 69.4);
		SMARTSnEnergiesTable.put("[$([ch1]);!$([c]1:[c]:[c]:[c](-[O]-[C^2]~[O]):[c]:[c]1);$([c]1:[c]:[c]:[c](-[NH]-[C]=[O]):[c]:[c]1),$([c]1:[c]:[c]:[c](-[O,SX2]):[c]:[c]1),$([ch1r6]:[cr6]:[cr5]:[nX3r5]),$([ch1r6]:[cr5]:[cr5]:[nX3r5,oX2r5,sX2r5])]", 74.1);
		SMARTSnEnergiesTable.put("[$([ch1]);$([ch1]1:[c](~[#7X2]~[#6^2]):[c]:[c]:[c]:[c]1),$([ch1r6]:[cr6]-[O,SX2]),$([ch1r6]:[cr5]:[sX2r5]),$([ch1r6]:[cr6]:[cr5]:[or5,sX2r5]),$([ch1r6]:[cr6]:[cr6]:[cr5]:[nX3r5]);!$([ch1r6]:[cr6]-[O]-[C^2]~[O])]", 77.2);
		SMARTSnEnergiesTable.put("[$([ch1r5]);!$([ch1r5]:[nX2]:[#16X2])]", 78.1);
		SMARTSnEnergiesTable.put("[$([ch1]1:[c]([#6^2,#6^1,O]):[c]:[c]:[c]:[c]1),$([ch1]1:[c]:[c]:[c]([#6^2,#6^1,O]):[c]:[c]1),$([ch1]1:[c]([NX3](~[O])~[O]):[c]:[c]:[c]:[c]1),$([ch1]1:[c]:[c]:[c]([NX3](~[O])~[O]):[c]:[c]1),$([C^2h1](-[C^2])=[C^2]),$([ch1r6]:[cr5]:[or5]),$([ch1r6]:[cr6]:[cr6]:[cr5]:[or5,sX2r5])]", 80.8);
		SMARTSnEnergiesTable.put("[$([ch1]1:[c]([F,Cl,I,Br]):[c]:[c]:[c]:[c]1),$([ch1]1:[c]:[c]:[c]([F,Cl,I,Br]):[c]:[c]1),$([ch1]1:[c]:[c]([NX3](~[O])~[O]):[c]:[c]:[c]1),$([ch1]1:[c]:[c]:[c]([S]=O):[c]:[c]1)]", 84.1);
		SMARTSnEnergiesTable.put("[$([#6^2h1]);!$([ch1r6]:[nr6]);!$([ch1]:[c]-[S](=O)(=O)-[NX3])]", 86.3);
		SMARTSnEnergiesTable.put("[$([ch1r6]:[nr6]),$([ch1]:[c]-[S](=O)(=O)-[NX3])]", 92.0);
		SMARTSnEnergiesTable.put("[$([N^3H0]);!$([N^3][*^2]);!$([NX3][#16X4](=[OX1])(=[OX1]))]", 41.0);
		SMARTSnEnergiesTable.put("[$([N^3]);$([H1,H2]);!$([NX3][#16X4](=[OX1])(=[OX1]))]", 54.1);
		SMARTSnEnergiesTable.put("[$([NX3H0]([#6^2]1)[#6^2]=[#6^2][#6^3][#6^2]=1)]", 61.9);
		SMARTSnEnergiesTable.put("[$([N]([C^3])=[C^2])]", 62.7);
		SMARTSnEnergiesTable.put("[$([N^3H0]);$([N^3][*^2]);!$([N^3]([*^2])[*^2]);!$([NX3][#16X4](=[OX1])(=[OX1]))]", 63.9);
		SMARTSnEnergiesTable.put("[$([nr6]),$([N](-[#6^2])=[#6^2]);!$([nr5H0])]", 75.6);
		SMARTSnEnergiesTable.put("[$([N]);$([NX3H0]([*^2])[*^2]),$([N^2][C]=[O])]", 91.1);
		SMARTSnEnergiesTable.put("[nr5H0]", 92.1);
		SMARTSnEnergiesTable.put("[$([NX3]);$([NX3][#16X4](=[OX1])(=[OX1]))]", 94.4);
		SMARTSnEnergiesTable.put("[$([NX3H1]([#6^2]1)[#6^2]=[#6^2][#6^3][#6^2]=1)]", 10.4);
		
	}




	public static HashMap<String, Double> getSMARTSnEnergiesTable(){
		if (SMARTSnEnergiesTable==null) new SMARTSnEnergiesTable();
		return SMARTSnEnergiesTable;
	}

}
