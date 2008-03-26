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
package toxTree.io.batch;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * This is to be able to verify if a file has changed since it has been last processed by BatchProcessing
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class FileState implements Serializable {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -9098389880881547516L;
	protected String filename=null;
	protected long length=0;
	protected long lastModified=0;
	protected int hashCode=0;
	protected transient File file = null;
	protected long offset = 0;
	protected long currentRecord = 0;
	/**
	 * 
	 */
	public FileState() {
		super();
	}
	public FileState(String filename) {
		this(new File(filename));
	}	
	public FileState(File file) {
		this();
		setFile(file);
		
	}	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		FileState fs = (FileState) obj;
		return filename.equals(fs.getFilename()) &&
			   (length == fs.length) &&
			   (lastModified == fs.lastModified) &&
			   (hashCode == fs.hashCode) &&
			   (currentRecord == fs.currentRecord) &&
			   (offset == fs.offset)
			   ;
		
	}

	/**
	 * @return Returns the filename.
	 */
	public synchronized String getFilename() {
		return filename;
	}
	/**
	 * @param filename The filename to set.
	 */
	public synchronized void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return Returns the hashCode.
	 */
	public synchronized int getHashCode() {
		return hashCode;
	}
	/**
	 * @param hashCode The hashCode to set.
	 */
	public synchronized void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	/**
	 * @return Returns the lastModified.
	 */
	public synchronized long getLastModified() {
		return lastModified;
	}
	/**
	 * @param lastModified The lastModified to set.
	 */
	public synchronized void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	/**
	 * @return Returns the length.
	 */
	public synchronized long getLength() {
		return length;
	}
	/**
	 * @param length The length to set.
	 */
	public synchronized void setLength(long length) {
		this.length = length;
	}
/*
	private void writeObject(java.io.ObjectOutputStream out)
	     throws IOException {
		
	}
	 private void readObject(java.io.ObjectInputStream in)
	     throws IOException, ClassNotFoundException {
	 	
	 }
	 */
	 	
	/**
	 * @return Returns the file.
	 */
	public synchronized File getFile() {
		return file;
	}
	/**
	 * @param file The file to set.
	 */
	public synchronized void setFile(File file) {
		this.file = file;
		filename = file.getAbsolutePath();
		length = file.length();
		lastModified = file.lastModified();
		hashCode = file.hashCode();
		this.file = file;
		currentRecord = 0;
		offset = 0;
	}
	/**
	 * @return Returns the currentRecord.
	 */
	public synchronized long getCurrentRecord() {
		return currentRecord;
	}
	/**
	 * @param currentRecord The currentRecord to set.
	 */
	public synchronized void setCurrentRecord(long currentRecord) {
		this.currentRecord = currentRecord;
	}
	/**
	 * @return Returns the offset.
	 */
	public synchronized long getOffset() {
		return offset;
	}
	/**
	 * @param offset The offset to set.
	 */
	public synchronized void setOffset(long offset) {
		this.offset = offset;
	}
	public boolean match() throws BatchProcessingException {
		return match(createFile());
	}
	public boolean match(File file) {
		
		return filename.equals(file.getAbsolutePath()) &&
		   (length == file.length()) &&
		   (lastModified == file.lastModified()) 
		   //&&   (hashCode == file.hashCode())
		   ;
		
	}
	public File createFile()  throws BatchProcessingException {
		try {
			return new File(filename);
		} catch (Exception x) {
			throw new BatchProcessingException(x);
		}
	}
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(" [");
		b.append((currentRecord+1));
		b.append(" ] ");
		b.append(filename);
		return b.toString();
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeLong(serialVersionUID);
		out.writeObject(filename);
		out.writeLong(length);
		out.writeLong(lastModified);
		out.writeInt(hashCode);
		out.writeLong(offset);
		out.writeLong(currentRecord);
	}


	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		if (in.readLong() != serialVersionUID) throw new ClassNotFoundException();
		filename =in.readObject().toString();
		length =in.readLong();
		lastModified = in.readLong();
		hashCode = in.readInt();
		offset = in.readLong();
		currentRecord = in.readLong();
		file = new File(filename);
	}	
}
