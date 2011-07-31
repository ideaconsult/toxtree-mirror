package toxtree.ui.remote;

import java.io.InputStream;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;

import toxtree.lookup.RemoteLookup;

public class RemoteCompoundLookup extends RemoteLookup<IAtomContainer> {
	
	public RemoteCompoundLookup() throws Exception {
		super();
	}

	@Override
	public IAtomContainer processStream(String uri,String input,InputStream in) throws Exception {
		IteratingMDLReader reader = new IteratingMDLReader(in, NoNotificationChemObjectBuilder.getInstance());
		while (reader.hasNext()) {
			IChemObject a = reader.next();
			if (a instanceof IAtomContainer) {
				a.setProperty(CDKConstants.COMMENT,String.format("Retrieved from %s",String.format(uri,input)));
				a.removeProperty(CDKConstants.TITLE);
				a.removeProperty(CDKConstants.REMARK);
				return ((IAtomContainer) a);
			}
			break;
		}
		if (reader != null) reader.close();
		return null;
	}
	
}
