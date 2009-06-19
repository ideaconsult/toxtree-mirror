/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.test.qsar;

import java.io.File;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.MFAnalyser;

import toxTree.core.SmilesParserWrapper;
import toxTree.core.SmilesParserWrapper.SMILES_PARSER;
import toxTree.logging.TTLogger;
import toxTree.qsar.CommandShell;
import toxTree.qsar.OpenBabelShell;
import toxTree.qsar.ShellMengine;
import toxTree.qsar.ShellSmi2SDF;

public class CommandShellTest extends TestCase {
	protected CommandShell shell;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		shell = new ShellSmi2SDF();
		TTLogger.configureLog4j(true);

	}
/*
	public void testGetInstance() throws Exception {
		shell = CommandShell.getInstance();
		assertNotNull(shell);
		CommandShell shell1 = CommandShell.getInstance();
		assertNotNull(shell1);
		assertEquals(shell,shell1);
	}
*/
	public void testAddExecutable() throws Exception  {
		String name= "helper/smi23d/win/smi2sdf.exe";
		shell.addExecutable("nt",name);
		assertEquals(name,shell.getExecutable("nt"));
	}

	public void testAddExecutableMac() throws Exception {
		String name= "helper/smi23d/mac/smi2sdf";
		shell.addExecutableMac(name);
		assertEquals(name,shell.getExecutable(CommandShell.os_MAC));
	}

	public void testAddExecutableWin()  throws Exception {
		String name= "helper/smi23d/win/smi2sdf.exe";
		shell.addExecutableWin(name);
		String exec = shell.getExecutable(CommandShell.os_WINDOWS);
		assertEquals(name,exec);
		File file = new File(exec);
		assertTrue(file.exists());
	}

	public void testAddExecutableLinux() throws Exception {
		String name= "helper/smi23d/linux/smi2sdf";
		shell.addExecutableLinux(name);
		assertEquals(name,shell.getExecutable(CommandShell.os_LINUX));
	}

	public void testRunSMI2SDF() throws Exception {
		ShellSmi2SDF smi2sdf = new ShellSmi2SDF();
		SmilesParserWrapper p =  SmilesParserWrapper.getInstance(SMILES_PARSER.OPENBABEL);
		String smiles = "Nc3ccc2c(ccc1ccccc12)c3";
		//"[H]C1=C([H])C([H])=C([H])C([H])=C1([H])";
		IMolecule mol = p.parseSmiles(smiles); 
		mol.setProperty("SMILES",smiles);
		mol.setProperty("TITLE",smiles);
		smi2sdf.setReadOutput(true);
		smi2sdf.setInputFile("smi2sdf_test.smi");		
		smi2sdf.setOutputFile("smi2sdf_test.sdf");
		IMolecule newmol = smi2sdf.runShell(mol);
		assertEquals(mol.getAtomCount(),newmol.getAtomCount());
		assertEquals(mol.getBondCount(),newmol.getBondCount());
		for (int i=0; i < newmol.getAtomCount(); i++) {
			assertNotNull(newmol.getAtom(i).getPoint3d());
		}
		SmilesGenerator g = new SmilesGenerator();
		String newsmiles = g.createSMILES(newmol);
		//assertEquals(smiles,newsmiles);
		//isisomorph returns false if createSmiles was not run before; perhaps smth to do with atom types configuration
		assertTrue(UniversalIsomorphismTester.isIsomorph(mol,newmol));			
	}
	public void testRunMENGINE() throws Exception {
		ShellMengine mengine = new ShellMengine();
		IMolecule mol = MoleculeFactory.makeAlkane(3);
		mengine.runShell(mol);
	}
	
	public void testRunSMI23D() throws Exception {
		ShellSmi2SDF smi2sdf = new ShellSmi2SDF();
		IMolecule mol = MoleculeFactory.makeAlkane(3);
		smi2sdf.setOutputFile("smi23d_test.sdf");
		smi2sdf.runShell(mol);
		ShellMengine mengine = new ShellMengine();
		mengine.setInputFile("smi23d_test.sdf");
		mengine.setOutputFile("smi23d_test_opt.sdf");
		IMolecule newmol = mengine.runShell(mol);
		MFAnalyser mf = new MFAnalyser(newmol);
		IAtomContainer c = mf.removeHydrogensPreserveMultiplyBonded();
		assertTrue(UniversalIsomorphismTester.isIsomorph(mol,c));
		for (int i=0; i < newmol.getAtomCount(); i++) {
			assertNotNull(newmol.getAtom(i).getPoint3d());
//			System.out.println(newmol.getAtom(i).getSymbol() + "\t" + newmol.getAtom(i).getPoint3d());
		}	

	}
	
	public void testParseOutput() throws Exception  {
		fail("Not yet implemented");
	}
	
	public void testOpenBabel() throws Exception {
		String testfile = "babel_test.sdf";
		OpenBabelShell babel = new OpenBabelShell();
		babel.setOutputFile(testfile);
		IMolecule newmol = babel.runShell("c1ccccc1");
		MFAnalyser mf = new MFAnalyser(newmol);
		IAtomContainer c = mf.removeHydrogensPreserveMultiplyBonded();
		
		IMolecule mol = MoleculeFactory.makeBenzene();
		for (int i=0; i < newmol.getBondCount(); i++) {
			System.out.println(newmol.getBond(i).getOrder());
		}	
		assertTrue(UniversalIsomorphismTester.isIsomorph(mol,c));
		new File(babel.getOutputFile()).delete();
	}	


}

