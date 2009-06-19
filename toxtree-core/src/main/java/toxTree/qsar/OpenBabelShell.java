package toxTree.qsar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IMolecule;

import toxTree.core.Introspection;
import toxTree.exceptions.ShellException;
import toxTree.ui.Preferences;

/*
 * TODO update to use babel ---errorlevel 2 -h -ismi *.smi -osmi *.sdf 
 * <p>
 * add a test for babel ---errorlevel 2 -h -ismi c12-c3c(cc(N)cc3)Cc1cccc2.smi -osmi c12-c3c(cc(N)cc3)Cc1cccc2-ob.smi
 */
public class OpenBabelShell extends ShellSDFoutput<String>implements PropertyChangeListener {
	public OpenBabelShell() throws ShellException {
		super();
	}
	protected void initialize() throws ShellException {
		super.initialize();
		//addExecutable(CommandShell.os_WINDOWS, "helper/openbabel/win/babel.exe");
        addExecutable(CommandShell.os_WINDOWS, Introspection.getToxTreeRoot()+Preferences.getProperty(Preferences.OPENBABEL_WIN));        
        addExecutable(CommandShell.os_LINUX, Introspection.getToxTreeRoot()+Preferences.getProperty(Preferences.OPENBABEL_LINUX));           
		setInputFile("obabel.smi");
		setOutputFile("obabel.sdf");		
		setReadOutput(true);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.OPENBABEL_WIN, this);
        Preferences.getPropertyChangeSupport().addPropertyChangeListener(Preferences.MENGINE_WIN, this);
    }
    @Override
    protected void finalize() throws Throwable {
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.OPENBABEL_WIN, this);
        Preferences.getPropertyChangeSupport().removePropertyChangeListener(Preferences.OPENBABEL_LINUX, this);
        super.finalize();
    }
	
	@Override
	protected List<String> prepareInput(String path, String mol) throws ShellException {
		try {
			FileWriter writer = new FileWriter(path + File.separator + getInputFile());
			writer.write(mol);
			writer.write('\t');
			writer.write(mol);
			writer.write('\n');
			writer.flush();
			writer.close();
			
			List<String> list = new ArrayList<String>();
			list.add("-h");
			list.add("-ismi");
			list.add(getInputFile());
			list.add("-osdf");
			list.add(getOutputFile());
			return list;
		} catch (Exception x) {
			throw new ShellException(this,x);
		}
	}
	@Override
	protected IMolecule transform(String mol) {
		return null;
	}	
	@Override
	public String toString() {
		return "OpenBabel";
	}
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            addExecutableWin(Preferences.getProperty(Preferences.OPENBABEL_WIN));
            addExecutableLinux(Preferences.getProperty(Preferences.OPENBABEL_LINUX));
        } catch (Exception x) {
            logger.error(x);
        }
        
    }        
}
