/*
 * Created on 2005-9-25
 *
 */
package toxTree.exceptions;

/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-9-25
 */
public class ToxTreeIOException extends Exception {
	protected String filename = "";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2465688895150239988L;

	/**
	 * 
	 */
	public ToxTreeIOException() {
		super();
		
	}

	public ToxTreeIOException(Throwable cause) {
		super(cause);
	}
	/**
	 * @param message
	 */
	public ToxTreeIOException(String message,String filename) {
		super(message);
		this.filename = filename;
	}

	/**
	 * @param cause
	 */
	public ToxTreeIOException(Throwable cause,String filename) {
		super(cause);
		this.filename = filename;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ToxTreeIOException(String message, Throwable cause,String filename) {
		super(message, cause);
		this.filename = filename;
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
}
