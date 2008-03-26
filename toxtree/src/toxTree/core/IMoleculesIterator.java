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

*/
package toxTree.core;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMoleculeSet;

import toxTree.exceptions.ToxTreeIOException;

public interface IMoleculesIterator extends Iterator {

	public static final String MSG_UNSUPPORTEDFORMAT="Error Unsupported format\t";
	public static final String MSG_ERRORONSAVE="Error when writing file\t";
	public static final String MSG_ERRORONOPEN="Error when reading file\t";
	public static final String MSG_SAVESUCCESS="Writing to file successfull\t";
	public static final String MSG_OPENSUCCESS="Reading from file successfull\t";
	public static final String MSG_EMPTYFILE="No molecules found! Perhaps the file is empty!";

	public Object first();
	public Object last();
	public Object prev();
	
	public void clear();
	
	public boolean isReading();
	public boolean isWriting();
	public String getStatus();
	
	public int getMoleculesCount();
	public int getCurrentNo();
	public IAtomContainer setCurrentNo(int record);
	public IAtomContainer[] getMolecules();
	public void setMolecules(List molecules);
	public IAtomContainer getMolecule();
	public IMoleculeSet getMoleculeForEdit() throws Exception;
	public IAtomContainer getAtomContainer(int index);
	
	public void addMolecule(IAtomContainer mol);
	public void setMolecule(IAtomContainer mol);
	public void setReading();
	public void setWriting();
	public void setDone(boolean success);
	
	public String getFilename();
	public List openFile(File input) throws ToxTreeIOException;
	public void saveFile(File output) throws ToxTreeIOException;
}
