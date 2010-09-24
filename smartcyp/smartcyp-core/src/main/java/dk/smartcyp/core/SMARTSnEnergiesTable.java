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
		SMARTSnEnergiesTable.put("[$([CX4][N]);!$([CH0]);!$([C][N]([*^2])[*^2]);!$([C][N]=[#6X3]);!$([CX4][NX3][C]=[O])]", 39.8);
		SMARTSnEnergiesTable.put("[!$([CH0]);$([CX4]([#6^2])[#6^2]),$([CX4][#7]=[#6X3]),$([CX4]([#8])[#8]);!$([CX4]([#8])[#8][C]=[O])]", 48.6);
		SMARTSnEnergiesTable.put("[$([CX4][S]);!$([CH0]);!$([C][S]=[O])]", 57.7);
		SMARTSnEnergiesTable.put("[$([CX4][#6^2]~[#8]),$([CX4][#6^2](~[#7])~[#7]),$([CX4][#6^1]),$([CX4][C^2]=[C^2]-[#6^2]);!$([CH0]);!$([CX4][C](=[O])[NX3]);!$([CX4][#6^2](=[#8])-[#8]);!$([CX4][C^2]([C^2])=[C^2]-[#6^2])]", 60.0);
		SMARTSnEnergiesTable.put("[$([CX4][O]);!$([CH0]);!$([C][O][C]=[O]);!$([CX4]1[O][C]1)]", 62.2);
		SMARTSnEnergiesTable.put("[$([CX4][#6^2]);!$([CH0]);!$([CX4][C](=[O])[NX3])]", 66.7);
		SMARTSnEnergiesTable.put("[$([CX4][#7](~[*^2])~[*^2]);!$([CH0])]", 69.1);
		SMARTSnEnergiesTable.put("[$([CX4][S](=[O])=[O]);!$([CH0])]", 69.5);
		SMARTSnEnergiesTable.put("[CX4;CH1,CH2;!$([CX4][NX3H1][C]=[O])]", 77.7);
		SMARTSnEnergiesTable.put("[CX4H3;!$([CX4][NX3H1][C]=[O])]", 89.6);
		SMARTSnEnergiesTable.put("[$([CX4][NX3H1][C]=[O]);!$([CH0])]", 94.6);
		SMARTSnEnergiesTable.put("[$([CX3H2]);$([C]=[*^2]-[*^2])]", 40.1);
		SMARTSnEnergiesTable.put("[$([CX3H1]);$([C]=[*^2]-[*^2]);!$([C](-[*^2])=[*^2]-[*^2])]", 52.4);
		SMARTSnEnergiesTable.put("[$([cH1]);$([c](:[#7]):[#7])]", 55.8);
		SMARTSnEnergiesTable.put("[$([ch1]);!$([ch1]1:[c](-[N^3]-[*^2]):[c]:[c]:[c]:[c]1);!$([c]1:[c]:[c]:[c](-[N^3]-[*^2]):[c]:[c]1);!$([ch1]1:[c](-[N^3](~O)~O):[c]:[c]:[c]:[c]1);!$([c]1:[c]:[c]:[c](-[N^3]):[c]:[c]1);$([ch1]1:[c](-[N^3]):[c]:[c]:[c]:[c]1),$([c]1:[c]:[c]:[c](-[N^3]):[c]:[c]1)]", 59.5);
		SMARTSnEnergiesTable.put("[$([CX3]);$([CX3]=[CX3]);!$([CH0]);!$([CX3](-[*^2])=[CX3]);!$([CX3]=[CX3]-[*^2])]", 65.6);
		SMARTSnEnergiesTable.put("[$([cH1]);$([c]:[#16])]", 67.1);
		SMARTSnEnergiesTable.put("[$([ch1]);$([c]1:[c]:[c]:[c](~[#7X2H0]~[c^2,C^2]):[c]:[c]1),$([c]1:[c]:[c]:[c](~[#7X3H1]~[c^2,C^2]):[c]:[c]1);!$([c]1:[c]:[c]:[c](-[NH]-[C]=[O]):[c]:[c]1)]", 69.2);
		SMARTSnEnergiesTable.put("[$([ch1]);!$([c]1:[c]:[c]:[c](-[O]-[C]=[O]):[c]:[c]1);$([c]1:[c]:[c]:[c](-[NH]-[C]=[O]):[c]:[c]1),$([c]1:[c]:[c]:[c](-[O,SX2]):[c]:[c]1)]", 75.3);
		SMARTSnEnergiesTable.put("[$([cH1]);$([c]1:[c]:[#16]:[c]:[c]1)]", 76.7);
		SMARTSnEnergiesTable.put("[$([ch1]);$([ch1]1:[c](~[#7X2H0]~[c^2,C^2]):[c]:[c]:[c]:[c]1),$([ch1]1:[c](~[#7X3H1]~[c^2,C^2]):[c]:[c]:[c]:[c]1),$([ch1]:[c]-[O,SX2]);!$([ch1]:[c]-[O]-[C]=[O])]", 77.3);
		SMARTSnEnergiesTable.put("[$([CX3H1,cX3H1]);!$([ch1]1:[c]:[c]:[#7]:[c]:[c]1);!$([ch1]1:[c]:[#7]:[c]:[c]:[c]1);!$([ch1]1:[c]:[#7]:[c]:[c]1);!$([ch1]:[#7])]", 82.3);
		SMARTSnEnergiesTable.put("[$([ch1]1:[c]:[c]:[#7]:[c]:[c]1),$([ch1]1:[c]:[#7]:[c]:[c]:[c]1),$([ch1]1:[c]:[#7]:[c]:[c]1),$([ch1]:[#7])]", 89.6);
		SMARTSnEnergiesTable.put("[$([N^3H0]);!$([N^3][*^2])]", 41.0);
		SMARTSnEnergiesTable.put("[$([N^3]);$([H1,H2])]", 54.1);
		SMARTSnEnergiesTable.put("[$([N]([#6^2]1)[#6^2]=[#6^2][#6^3][#6^2]=1)]", 61.9);
		SMARTSnEnergiesTable.put("[$([N^3H0]);$([N^3][*^2]);!$([N^3]([*^2])[*^2])]", 63.9);
		SMARTSnEnergiesTable.put("[$([nr6]),$([N^2]=[C])]", 75.6);
		SMARTSnEnergiesTable.put("[$([N]);$([NX3H0]([*^2])[*^2]),$([N^2H1][C]=[O])]", 89.6);
		SMARTSnEnergiesTable.put("[nr5H0]", 92.1);
		
	

	}




	public static HashMap<String, Double> getSMARTSnEnergiesTable(){
		if (SMARTSnEnergiesTable==null) new SMARTSnEnergiesTable();
		return SMARTSnEnergiesTable;
	}

}




/* Matches atoms against SMARTS patterns and assigns predefined energies
Infile is two columnes 1) SMARTS pattern and 2) Energy
[CX4;!CH0]	29.7
[CX3;!CH0]	13.3
[cX3;!CH0]	0.7
[N]			999.9
[S]			44.5
...

public SMARTSnEnergiesTable(String fileNameOfSMARTSnEnergiesTable) {

	// Local Variable
	SMARTSnEnergiesTable = new HashMap<String, Double>();


	// open SMARTSnEnergiesTable infile
	BufferedReader infileSMARTSnEnergiesTable = null;
	try {infileSMARTSnEnergiesTable = new BufferedReader ( new FileReader (fileNameOfSMARTSnEnergiesTable));}
	catch (FileNotFoundException e) {e.printStackTrace();}

	// read ref_file line by line & column by column
	String line = null;
	int lineNr = 0;
	String SMARTS;
	double energy;

	while (true){				

		// Read 1 line
		try {line = infileSMARTSnEnergiesTable.readLine();}
		catch (IOException e) {System.out.println("The infile SMARTSnEnergiesTable is empty");System.exit(0);}		

		 // If this is true we have read the last line, time to break
		if (line == null){break;}


		StringTokenizer ltok = new StringTokenizer(line);

		if(ltok.countTokens() != 2){
			System.out.println("Wrong format of SMARTSnEnergiesTable" + '\n' + "It should look like:" + 
					'\n' + "[CX4;!CH0]	29.7" + '\n' + "[CX3;!CH0]	13.3" + '\n' + "[cX3;!CH0]	0.7" + 
					'\n' + "[N]	999.9" + '\n' + "[S]	44.5" + "...");
			System.out.println('\n' +"However, your files is read like this" + '\n' + line.toString());
			System.exit(0);		
		}


		SMARTS =  ltok.nextToken();							// Read SMARTS
		energy = Double.parseDouble(ltok.nextToken());		// Read Energy
		System.out.println("SMARTS " + SMARTS + " energy " + energy);


		SMARTSnEnergiesTable.put(SMARTS, energy);
		lineNr++;	// Increment the lineNr index for next iteration
	}

	// close ref_ file
	try {infileSMARTSnEnergiesTable.close();} 
	catch (IOException e) {e.printStackTrace();
	}	

}

 */
