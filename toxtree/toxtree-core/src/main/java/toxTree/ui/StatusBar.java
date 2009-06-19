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
 * <b>Filename</b> StatusBar.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-2
 * <b>Project</b> toxTree
 */
package toxTree.ui;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import toxTree.core.IDecisionMethod;
import toxTree.data.DataContainer;
import toxTree.tree.ProgressStatus;

/**
 * The statusbar of {@link toxTree.apps.ToxTreeApp}
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-2
 */
public class StatusBar extends JPanel implements Observer, PropertyChangeListener {
    //protected IDecisionResult model = null;
    protected DataContainer toxData = null;
    protected JLabel label;
    protected JProgressBar progressBar= null;
    protected boolean modelProcessing = false;
    protected boolean dataProcessing = false;
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4316933074189008374L;

    /**
     * 
     */
    public StatusBar() {
        super();
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        addWidgets();        
    }

 
    protected void addWidgets() {
        label = new JLabel();
        add(label);
        label.setAlignmentY(JLabel.LEFT);
        label.setPreferredSize(new Dimension(300,18));        
        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(100,18));
        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setIndeterminate(false);
        add(progressBar);
        progressBar.setVisible(false);
        /*
        PropertyChangeListener l = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(TreeResult._pRuleResult)) {
                    label.setText((evt.getNewValue().toString()));
                }

            }
        };
        model.addPropertyChangeListener(l);
        */
        
    }
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable o, Object arg) {
    	if (o == null) return;
        if (o==toxData) {
        	if (toxData.isProcessing()) {
        		dataProcessing = true;
                if (!progressBar.isIndeterminate()) { 
                    progressBar.setIndeterminate(true);
                    progressBar.setVisible(true);                   
                }
                progressBar.setString(toxData.getStatus());
                label.setText(toxData.getStatus());            
            } else {
            	if (dataProcessing) { 
		            progressBar.setVisible(false);                
		            if (progressBar.isIndeterminate()) progressBar.setIndeterminate(false);
		            progressBar.setString(toxData.getStatus());
		            dataProcessing = false;
            	}
	        }        	
        }

    }

	/**
	 * @param toxData The toxData to set.
	 */
	public synchronized void setDataContainer(DataContainer toxData) {
		this.toxData = toxData;
		toxData.addObserver(this);
	}
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() != null)
            if (evt.getNewValue() instanceof ProgressStatus)
                updateStatus(((ProgressStatus)evt.getNewValue()));
            else if (evt.getNewValue() instanceof String)
                label.setText(evt.getNewValue().toString());
            else if (evt.getNewValue() instanceof IDecisionMethod)
                label.setText(evt.getNewValue().toString());        
        //System.out.println("NEW STATUS" + evt.getPropertyName() + " " + evt.getNewValue().getClass().getName() + " " + evt.getNewValue());
      
    }
    protected void updateStatus(ProgressStatus status) {
        
        if (status.isEstimating()) {
            modelProcessing = true;
            if (!progressBar.isIndeterminate()) { 
                progressBar.setIndeterminate(status.getPercentEstimated()>0);
                progressBar.setValue(status.getPercentEstimated());
                progressBar.setVisible(true);                   
            } else 
                progressBar.setString("");      
            
         } else if (status.isEstimated()) {
            if (modelProcessing) {
                    progressBar.setVisible(false);                
                    if (progressBar.isIndeterminate()) progressBar.setIndeterminate(false);
                    progressBar.setString("");
                    modelProcessing = false;
            }    
         } else {
            if (modelProcessing) {              
                    progressBar.setVisible(false);                
                    if (progressBar.isIndeterminate()) progressBar.setIndeterminate(false);
                    progressBar.setString("");
                    modelProcessing = false;
            } 
         }
             
    }
}

