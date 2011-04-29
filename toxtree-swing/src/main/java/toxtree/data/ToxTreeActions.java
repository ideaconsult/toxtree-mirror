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

*//**
 * <b>Filename</b> ToxTreeActions.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-1
 * <b>Project</b> toxTree
 */
package toxtree.data;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.text.DefaultEditorKit;

import toxTree.io.Tools;
import toxtree.ui.actions.BatchAction;
import toxtree.ui.actions.ClearResultAction;
import toxtree.ui.actions.EditCompoundAction;
import toxtree.ui.actions.EstimateAction;
import toxtree.ui.actions.EstimateAllAction;
import toxtree.ui.actions.ExplainAction;
import toxtree.ui.actions.InfoAction;
import toxtree.ui.actions.NewMoleculeAction;
import toxtree.ui.actions.OpenFileAction;
import toxtree.ui.actions.QuitAction;
import toxtree.ui.actions.SaveFileAction;
import toxtree.ui.molecule.GoToRecordAction;
import toxtree.ui.molecule.LookupCompoundAction;
import toxtree.ui.molecule.ShowFilteredFileAction;
import toxtree.ui.tree.actions.LoadAndEditTreeAction;
import toxtree.ui.tree.actions.NewTreeAction;
import toxtree.ui.tree.actions.SelectAndEditTreeAction;
import toxtree.ui.tree.actions.SelectTreeAction;
import toxtree.ui.tree.actions.TreeOptionsAction;
import toxtree.ui.tree.actions.ViewTreeAction;



/**
 * Actions used in {@link toxTree.apps.ToxTreeApp}
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-1
 */
public class ToxTreeActions extends ActionList {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8971882886147441682L;
    protected ToxTreeModule model = null;
    protected ArrayList estimateActions = null;
    protected ArrayList fileActions = null;
    protected ArrayList helpActions = null;
    
    protected Action estimateAction = null;
    protected Action estimateAllAction = null;
    protected Action explainAction = null;
    protected Action viewMethodAction = null;
    protected Action editMethodAction = null;    
    protected Action editCompoundAction = null;    
    protected BatchAction batchAction = null;
    
	
	public static final String _aSkipNotImplemented = "Skip not implemented ruiles";
	public static final String _FileAction = "File";
	public static final String _EditAction = "Edit";
	public static final String _CompoundAction = "Chemical Compounds";
	public static final String _HazardAction = "Toxic Hazard";	
	
	public static final String _aEditMethod = "Edit decision tree";
	

    /**
     * 
     */
    public ToxTreeActions(JFrame mainFrame, ToxTreeModule model) {
        super(mainFrame);
        this.model = model;
        estimateActions = new ArrayList();
        fileActions = new ArrayList();
        
        addFileActions(_FileAction,"F");
        addEditActions(_EditAction,"E");
        addCompoundActions(_CompoundAction,"P");
        addHazardActions(_HazardAction,"Z");
        addRulesActions("Method","M");
        addHelpActions("Help","H");
        UIManager.put("Button.showMnemonics", Boolean.TRUE);
    }
    protected void addEditActions(String title,String mnemonic) {
        Action a = new DefaultEditorKit.CutAction();
        a.putValue(AbstractAction.NAME,"Cut (Ctr-X)");
        a.putValue(AbstractAction.MNEMONIC_KEY,new Integer(KeyEvent.VK_X));
        addAction(a,title,mnemonic);
        
        a = new DefaultEditorKit.CopyAction();
        a.putValue(AbstractAction.NAME,"Copy (Ctr-C)");        
        a.putValue(AbstractAction.MNEMONIC_KEY,new Integer(KeyEvent.VK_C));
        addAction(a,title,mnemonic);

        a = new DefaultEditorKit.PasteAction();
        a.putValue(AbstractAction.NAME,"Paste (Ctr-V)");        
        a.putValue(AbstractAction.MNEMONIC_KEY,new Integer(KeyEvent.VK_V));
        addAction(a,title,mnemonic);
        
    }
    protected void addHelpActions(String title,String mnemonic) {
        addAction(new toxtree.ui.actions.AboutAction(model),title,mnemonic);
        addAction(new InfoAction(model),title,mnemonic);        
        addAction(new toxtree.ui.actions.HelpAction(),title,mnemonic);        
    }    
    protected void addFileActions(String title,String mnemonic) {
        addAction(new NewMoleculeAction(model.getDataContainer()),title,mnemonic);
        addAction(new OpenFileAction(model),title,mnemonic);
        addAction(new SaveFileAction(model),title,mnemonic);
        batchAction = new BatchAction(model);
        addAction(batchAction,title,mnemonic);
        addAction(new QuitAction(model),title,mnemonic);
       
    }

    protected void addCompoundActions(String title, String mnemonic) {
        editCompoundAction =  new EditCompoundAction(model);
        addAction(editCompoundAction,title,mnemonic);
        
        
        addAction(new GoToRecordAction(model),title,mnemonic);
        addAction(new LookupCompoundAction(model),title,mnemonic);
        
        
        addAction(new ShowFilteredFileAction(model),title,mnemonic);
        
    }
    protected void addHazardActions(String title,String mnemonic) {
        estimateAction = new EstimateAction(model); 
        addAction(estimateAction,title,mnemonic);
        explainAction = new ExplainAction(model); 
        addAction(explainAction,title,mnemonic);
        estimateAllAction = new EstimateAllAction(model);
        addAction(estimateAllAction,title,mnemonic);
        
        addAction(new ClearResultAction(model),title,mnemonic);
    }

    protected void addRulesActions(String title,String mnemonic) {
        viewMethodAction = new ViewTreeAction(model); 
        addAction(viewMethodAction,title,mnemonic);
        addAction(new TreeOptionsAction(model),title,mnemonic);
        
        addAction(new SelectTreeAction(model),title,mnemonic);
        AbstractAction a = new AbstractAction(_aEditMethod) {
			private static final long serialVersionUID = 6216133032495179820L;
			public void actionPerformed(ActionEvent e) {
			}
        };
        a.putValue(Action.SMALL_ICON, Tools.getImage("plugin_edit.png"));

        ActionList editTreeActions = new ActionList();
        editTreeActions.addAction(new NewTreeAction(model),title,mnemonic);
        editTreeActions.addAction(new SelectAndEditTreeAction(model),title,mnemonic);
        editTreeActions.addAction(new LoadAndEditTreeAction(model),title,mnemonic);
        editTreeActions.setParent(this);
        
        addAction(a,editTreeActions,title,mnemonic);
    }
    public Action getEstimateAction() {
        return estimateAction;
    }
    public Action getExplainAction() {
        return explainAction;
    }
    public Action getViewMethodAction() {
        return viewMethodAction;
    }
    public Action getEditCompoundAction() {
        return editCompoundAction;
    }
    
    public void startBatch(File input) {
    	batchAction.startBatch(input);
    }
    
}

