package toxtree.data;

import java.awt.Component;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.config.Elements;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionResult;
import toxTree.core.IMoleculesIterator;
import toxTree.data.CategoryFilter;
import toxTree.exceptions.FilterException;
import toxTree.exceptions.ToxTreeIOException;
import ambit2.core.data.MoleculeTools;

public class DataContainer extends Observable {
	protected static Logger logger = null;
	protected IMoleculesIterator containers = null;
	protected File processingFile = null;
	protected boolean modified = false;
	protected boolean enabled = true;

	public DataContainer() {
		this(null);
	}
    public DataContainer(File inputFile) {
        super();
		if (logger == null) logger = Logger.getLogger(DecisionMethodData.class.getName());
        //containers = new MoleculesIterator();
		containers = new FilteredMoleculesIterator();
        if (inputFile != null) 
        	openFile(inputFile);
		setChanged();
		notifyObservers();        

    }
    public IAtomContainer getMolecule() {
        return containers.getMolecule();
    }    

    public void newMolecule() {
    	if (!enabled) return;
    	IMolecule mol = MoleculeTools.newMolecule(SilentChemObjectBuilder.getInstance());
    	//mol.addAtom(MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON));
    	mol.setProperty("SMILES","");
    	newMolecule(mol);
    }
    public void newMolecule(IAtomContainer molecule) {
    	if (!enabled) return;
    	containers.clear();
    	addMolecule(molecule);
    }
    
    public void addMolecule() {
    	if (!enabled) return;
    	IMolecule mol = MoleculeTools.newMolecule(SilentChemObjectBuilder.getInstance());
    	mol.addAtom(MoleculeTools.newAtom(SilentChemObjectBuilder.getInstance(),Elements.CARBON));
    	addMolecule(mol);
    }
    
    public void addMolecule(IAtomContainer molecule) {
    	if (!enabled) return;
        containers.addMolecule(molecule);
        modified = true;        
        containers.last();
        setChanged();
        notifyObservers();

    }
    
    public void setMolecule(IAtomContainer molecule) {
    	if (!enabled) return;
    	containers.setMolecule(molecule);
        modified = true;    	
        setChanged();
        notifyObservers();    	
    }
    public void clearFilters() throws FilterException {
    	if (containers instanceof FilteredMoleculesIterator) {
    		((FilteredMoleculesIterator) containers).deleteAllFilters();
			setChanged();
			notifyObservers();
    		
    	} else throw new FilterException("Filtering not supported!");
    }
    public void selectFilter(Component parentComponent) throws FilterException {
    	if (containers instanceof FilteredMoleculesIterator) {
    		((FilteredMoleculesIterator) containers).selectFilter(parentComponent);
			setChanged();
			notifyObservers();
    		
    	} else throw new FilterException("Filtering not supported!");
    }
    public void filter(IDecisionResult treeResult) throws FilterException {
    	if (treeResult == null) throw new FilterException("Filter not defined!");
    	IDecisionCategories categories = treeResult.getDecisionMethod().getCategories();
    	if (categories == null) throw new FilterException("Categories not defined!");
    	if (containers instanceof FilteredMoleculesIterator) {
    		/*
    		for (int i=0; i < categories.size();i++) { 
    		((FilteredMoleculesIterator) containers).addFilter(
	    				new CategoryFilter(treeResult.getResultPropertyName() , categories.get(i))
	    				);
    		}
    		*/
    		List<CategoryFilter> f = treeResult.getFilters();
    		if (f!=null) {
	    		for (int i=0; i < f.size();i++)
	    			((FilteredMoleculesIterator) containers).addFilter(f.get(i));
				setChanged();
				notifyObservers();
    		}
    		
    	} else throw new FilterException("Filtering not supported!");

    }
    public int gotoRecord(int record) {
	    if ((record >= 0) && (record < getMoleculesCount())) {
			IAtomContainer ac = containers.setCurrentNo(record-1);
			setChanged();
			notifyObservers(ac);
			return record;    	
    	} else return getCurrentNo();
    }
    public int lookup(String field, Object value) {
    	return lookup(field, value,false);
    }
    public int lookup(String field, Object value,boolean silent) {
    	if ((field == null) || (value == null)) return -1;
    	IAtomContainer  ac = null;
		for (int record =0; record < containers.getMoleculesCount(); record++) {

			try {
			    ac = containers.getAtomContainer(record);
			    if (ac.getProperty(field)==null) continue;
			    String property = ac.getProperty(field).toString().toLowerCase();
				if (value.equals(property)) {
					if (!silent) {
						containers.setCurrentNo(record);
						setChanged();
						notifyObservers(ac);
					}
					return record;
				}
			} catch (Exception x) {
				logger.log(Level.WARNING,"Error when processing record %s %s",new Object[] {record,x});
				x.printStackTrace();
			 
			}
			ac = null;
		}
		return -1;

    }
    public void openFile(File input) {
    	if (!enabled) return;
    	this.processingFile = input;
        //final toxTree.ui.GUIWorker worker = new toxTree.ui.GUIWorker() {
            //public Object construct() {
            	containers.setReading();
            	setChanged();
            	notifyObservers();
            	List molecules = null;
            	try {
	               	molecules = containers.openFile(processingFile);
	               	if (molecules != null)
	               		containers.setMolecules(molecules);
	               	
	               	containers.setDone(true);    
	                modified = false;
            	} catch (ToxTreeIOException x) {

            		containers.setDone(false);
            		logger.log(Level.SEVERE,x.getMessage(),x);
            		String msg = "";
            		if ((x.getCause() != null) && (x.getCause().getMessage() != null)) 
            			msg = x.getCause().getMessage();
            		else msg = "Error";
            		ToxTreeActions.showMsg(x.getMessage() + "\n" + x.getFilename(),msg);
            	}
            	setChanged();
            	notifyObservers();
            	/*

               	return null;
            }
            //Runs on the event-dispatching thread.
            public void finished() {
            	first();
            }
        };
        
        worker.start(); 
        
        */
    }
    public void saveFile(File output) {
    	if (!enabled) return;
    	this.processingFile = output;
    	/*
        final toxTree.ui.GUIWorker worker = new toxTree.ui.GUIWorker() {
            public Object construct() {
            */
            	containers.setWriting();
            	setChanged();
            	notifyObservers();
            	try {
            		containers.saveFile(processingFile);
                    modified =false;
                   	containers.setDone(true);
                    
            		ToxTreeActions.showMsg("File saved ",processingFile.getName());
            		
            	} catch (ToxTreeIOException x) {
            		logger.log(Level.SEVERE,x.getMessage(),x);
                   	containers.setDone(false);
            		
            		ToxTreeActions.showMsg(x.getMessage(),x.getFilename());
            		
            	}
            	setChanged();
            	notifyObservers();
               	/*
               	return null;
            }
            //Runs on the event-dispatching thread.
            public void finished() {
            	first();
                //setChanged(); redundant - first() already called these 
                //notifyObservers();
            }
        };
        worker.start(); 
        */
    }
	public int getMoleculesCount() {
		return containers.getMoleculesCount();
	}
	public int getCurrentNo() {
		return containers.getCurrentNo();
	}
	public Object first() {
		if (!enabled) return null;
		Object o = containers.first();
		setChanged();
		notifyObservers();
		return o;
	}
	public Object last() {
		if (!enabled) return null;
		Object o = containers.last();
		setChanged();
		notifyObservers();
		return o;		
	}
	public Object next() {
		if (!enabled) return null;
		Object o = containers.next();
		setChanged();
		notifyObservers();
		return o;		
	}
	public Object prev() {
		if (!enabled) return null;
		Object o = containers.prev();
		setChanged();
		notifyObservers();
		return o;		
	}
	public boolean isProcessing() {
		return containers.isReading() || containers.isWriting();
	}
	public String getStatus() {
		return containers.getStatus();
	}
	@Override
	public String toString() {
		return containers.toString();
	}
	public boolean loadedFromFile() {
		return !containers.getFilename().equals("");
	}
	public String getFileName() {
		if (modified)
			return containers.getFilename() + '*';
		else
			return containers.getFilename();
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
