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

/**
 * An {@link java.lang.Exception} to be used in {@link toxTree.io.batch.IBatchProcessing}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-4
 */
public class BatchProcessingException extends Exception {
	protected IBatchProcessing batch = null;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6837815972092687580L;

	/**
	 * 
	 */
	public BatchProcessingException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BatchProcessingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public BatchProcessingException(String message, IBatchProcessing batch) {
		super(message);
		this.batch = batch;
	}
	public BatchProcessingException(Throwable cause, IBatchProcessing batch) {
		super(cause);
		this.batch = batch;
	}
	/**
	 * @param cause
	 */
	public BatchProcessingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BatchProcessingException(String message, Throwable cause) {
		super(message, cause);
	}
	public BatchProcessingException(String message, Throwable cause, IBatchProcessing batch) {
		super(message, cause);
		this.batch = batch;
	}

}
