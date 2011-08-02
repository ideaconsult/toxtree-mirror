package toxTree.query.remote;

import java.io.InputStream;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;

import ambit2.core.io.MyIteratingMDLReader;

import toxtree.lookup.RemoteLookup;

public class RemoteCompoundLookup extends RemoteLookup<IAtomContainer> {
	
	public RemoteCompoundLookup() throws Exception {
		super();
	}

	@Override
	public IAtomContainer processStream(String uri,String input,InputStream in) throws Exception {
		MyIteratingMDLReader reader = new MyIteratingMDLReader(in, NoNotificationChemObjectBuilder.getInstance());
		while (reader.hasNext()) {
			Object o = reader.next();
			if (o instanceof IAtomContainer) {
				IAtomContainer a = (IAtomContainer) o; 
				a.setProperty(CDKConstants.COMMENT,String.format("Retrieved from %s",String.format(uri,input)));
				a.removeProperty(CDKConstants.TITLE);
				a.removeProperty(CDKConstants.REMARK);
				return a;
			}
			break;
		}
		if (reader != null) reader.close();
		return null;
	}
	
}
