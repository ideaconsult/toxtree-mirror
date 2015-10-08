package toxTree.query.remote;

import java.io.InputStream;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxtree.lookup.RemoteLookup;

public class RemoteCompoundLookup extends RemoteLookup<IAtomContainer> {
	
	public RemoteCompoundLookup() throws Exception {
		super();
	}

	@Override
	public IAtomContainer processStream(String uri,String input,InputStream in) throws Exception {
		IteratingSDFReader reader = new IteratingSDFReader(in, SilentChemObjectBuilder.getInstance());
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
