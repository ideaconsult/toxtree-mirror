package toxTree.io;

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import toxTree.core.Introspection;
import ambit2.base.io.DownloadTool;

public class Tools {
	protected Tools() {}
	
	/**
	 * Extract a file from resource and copies it into temporary directory. Returns the file.
	 * @param resource
	 * @return
	 */
	public static File getFileFromResource(String resource) throws Exception {
			final String defaultVersion = "unknown";
			String version;
			try {
				Package self = Package.getPackage("toxTree.core");
				version = self==null?defaultVersion:self.getImplementationVersion();
				if ((version==null) || "".equals(version.trim())) 
					version = defaultVersion;
			
			} catch (Exception x) {
				//just in case
				version = defaultVersion;
			}
			File file = new File(String.format("%s/.toxtree.%s",System.getProperty("java.io.tmpdir"),version.trim()));
			file.mkdir();
			file = new File(file.getAbsolutePath()+"/"+resource);
			if (!file.exists())
				DownloadTool.download(resource, file);
			return file;
	}
	/**
	 * Same as {@link #getFileFromResource(String)}  but hides the exception
	 * @param resource
	 * @return
	 */
	public static File getFileFromResourceSilent(String resource) {
		try {
			return getFileFromResource(resource);
		} catch (Exception x) {
			return null;
		}
	}
		
	/**
	 * Loads image 
	 */
		
	    public static ImageIcon getImage(String filename)  {
	        try {
	           URL iconURL = Introspection.getLoader().getResource("toxTree/ui/tree/images/"+filename);
	           if (iconURL != null) 
	                  return new ImageIcon(iconURL);
	           else return null;
	        } catch (Exception x) {
	            return null;
	        }
	    }		

}
