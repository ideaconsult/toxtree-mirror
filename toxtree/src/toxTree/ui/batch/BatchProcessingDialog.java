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
package toxTree.ui.batch;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import toxTree.core.IDecisionMethod;
import toxTree.data.ToxTreeActions;
import toxTree.io.batch.BatchFactory;
import toxTree.io.batch.BatchProcessing;
import toxTree.io.batch.BatchProcessingException;
import toxTree.io.batch.ChemObjectBatchProcessing;
import toxTree.io.batch.IBatchProcessing;
import toxTree.io.batch.ToxTreeBatchProcessing;

/**
 * UI for batch processing {@link toxTree.io.batch.IBatchProcessing}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-5
 */
public class BatchProcessingDialog extends AbstractJobProcessingDialog implements Observer {
    JLabel batchTitle;
	JFormattedTextField inputField, outputField;
	JButton b1, b2;
	File openFile =null; File saveFile = null;
	JLabel  stateLabel, progressLabel, loadBatch, saveBatch, configLabel;
	JProgressBar progressBar;
	IBatchProcessing batch;
	IDecisionMethod decisionMethod = null;

	
	

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3314347684369161157L;

	/**
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch) throws HeadlessException {
		super();
		setBatch(batch);

	}

	/**
	 * @param owner
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch, Dialog owner) throws HeadlessException {
		super(owner);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param modal
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Dialog owner, boolean modal)
			throws HeadlessException {
		super(owner, modal);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Frame owner) throws HeadlessException {
		super(owner);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param modal
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Frame owner, boolean modal)
			throws HeadlessException {
		super(owner, modal);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param title
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Dialog owner, String title)
			throws HeadlessException {
		super(owner, title);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Dialog owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param title
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Frame owner, String title)
			throws HeadlessException {
		super(owner, title);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Frame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 * @throws HeadlessException
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		setBatch(batch);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public BatchProcessingDialog(IBatchProcessing batch,Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		setBatch(batch);
	}

	/* (non-Javadoc)
	 * @see toxTree.ui.AbstractJobProcessingDialog#createMainPanel()
	 */
	@Override
	protected JPanel createMainPanel() {
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Batch processing options"));
		Dimension d = new Dimension(400,170);
		p.setPreferredSize(d);
		p.setMinimumSize(d);
		GridBagLayout g = new GridBagLayout();
		p.setLayout(g);
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		
		Insets insets = new Insets(1,3,1,3);
		c.insets = insets;
		c.fill = GridBagConstraints.BOTH;
		
		d = new Dimension(Integer.MAX_VALUE,24);
		
        batchTitle = new JLabel("");
        if ((batch !=null) && (batch instanceof ToxTreeBatchProcessing)) {
            batchTitle.setText(((ToxTreeBatchProcessing)batch).getDecisionMethod().toString());
        }
        
		JLabel l1 = new JLabel("Process file");
		l1.setAlignmentX(LEFT_ALIGNMENT);
		l1.setPreferredSize(d);
		l1.setMinimumSize(d);
		inputField = new JFormattedTextField("");
		inputField.setEditable(false);
		inputField.setMinimumSize(d);
		inputField.setMaximumSize(d);
		inputField.setPreferredSize(d);
		b1 = new JButton(new  AbstractAction("Browse") {
			/**
			 * Comment for <code>serialVersionUID</code>
			 */
			private static final long serialVersionUID = -8159958269747554023L;

			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				//String[] ext = {".sdf",".smi",".csv"};
				update((Observable)batch,null);
				File f = ToxTreeActions.selectFile(null, 
						ChemObjectBatchProcessing.extensions,ChemObjectBatchProcessing.extensions_descr, true);
				if (f !=null) {
					openFile = f;
					inputField.setText(openFile.getAbsolutePath());
					okButton.setText("Start");
					batch = null;
				}
			} 
		});
		
		
		JLabel l2 = new JLabel("Store results in");
		l2.setAlignmentX(LEFT_ALIGNMENT);
		l2.setPreferredSize(d);
		l2.setMinimumSize(d);
		l2.setMaximumSize(d);
		
		outputField = new JFormattedTextField("");
		outputField.setEditable(false);
		outputField.setMinimumSize(d);
		outputField.setMaximumSize(d);
		outputField.setPreferredSize(d);
		b2 = new JButton(new  AbstractAction("Browse") {
			/**
			 * Comment for <code>serialVersionUID</code>
			 */
			private static final long serialVersionUID = -767018500263991989L;

			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {

				update((Observable)batch,null);
				File f = ToxTreeActions.selectFile(null, ChemObjectBatchProcessing.extensions,ChemObjectBatchProcessing.extensions_descr, false);
				setOutputFile(f);


			} 
		});
		b2.setMaximumSize(d);
		
        loadBatch = new JLabel("<html><u>Load batch configuration</u></html>");
        loadBatch.setOpaque(true);
        loadBatch.setPreferredSize(new Dimension(120,32));
        loadBatch.setAlignmentX(CENTER_ALIGNMENT);
        
		loadBatch.addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			
	   			if ((batch !=null) && batch.isRunning()) {
	   				progressLabel.setText("Can't load batch configuration while batch is running!");
	   				return;
	   			}

	   			File f = ToxTreeActions.selectFile(null, new String[] {".batch"}, new String[] {"toxTree batch files"}, true);
	   			if (f != null)
	   				try {
	   					cleanPausedBatch();
	   					
	   					ToxTreeBatchProcessing b = (ToxTreeBatchProcessing) BatchFactory.createFromConfig(f);
	   					batch = b;
                        if (b.getDecisionMethod() == null)
                            batchTitle.setText("No decision method assigned!");
                        else batchTitle.setText(b.getDecisionMethod().toString());
	   					openFile = b.getInputFile().getFile();
	   					inputField.setText(openFile.getAbsolutePath());
	   					saveFile = b.getOutputFile().getFile();
	   					outputField.setText(saveFile.getAbsolutePath());
	   					
	   					update((Observable)batch,null);
	   					if (batch.isPaused()) {
	   						okButton.setVisible(true);
	   						okButton.setText("Continue");
	   						pauseButton.setVisible(false);
	   						cancelButton.setVisible(true);
	   					}
	   					//statusLabel.setText(batch.toString());
	   					
	   				} catch (BatchProcessingException x) {
	   					setErrorMessage(x);
	   					ToxTreeActions.showMsg(f.getAbsolutePath(),"Error on loading batch configuration");
	   					batch = null;	   					
	   				}
	   			
	   		}
	    });	  		
		
        saveBatch = new JLabel("<html><u>Save batch configuration</u></html>");
        saveBatch.setOpaque(true);
        saveBatch.setPreferredSize(new Dimension(120,32));
        saveBatch.setAlignmentX(CENTER_ALIGNMENT);
        
        saveBatch.addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			if ((batch !=null) && batch.isRunning()) {
	   				progressLabel.setText("Can't save while batch is running! Pause batch to save!");
	   				return;
	   			}
	   			if ((openFile == null) || (saveFile == null)) {
	   				setErrorMessage(new BatchProcessingException("Empty input or output file!"));
	   				return;
	   			}

	   			File f = ToxTreeActions.selectFile(null,  new String[] {".batch"}, new String[] {"toxTree batch files"}, false);
	   			setVisible(true);
	   			if (f != null)
	   				try {
	   					if (batch == null) {
	   						ToxTreeBatchProcessing b = new ToxTreeBatchProcessing(openFile,saveFile);
	   						b.setDecisionMethod(decisionMethod);
	   						batch = b;
	   					} 
	   					if (batch != null) {
	   						batch.setConfigFile(f);
	   						batch.saveConfig();
	   						update((Observable) batch,null);
	   					} else setErrorMessage(new BatchProcessingException("Can't save batch configuration!"));
	   				} catch (BatchProcessingException x) {
	   					batch = null;
	   					x.printStackTrace();
	   					setErrorMessage(x);
	   					ToxTreeActions.showMsg(f.getAbsolutePath(),"Error on loading batch configuration");
	   				}
	   				
	   		}
	    });	  		
        
        configLabel = new JLabel("");
        configLabel.setOpaque(true);
        configLabel.setPreferredSize(d);
        configLabel.setAlignmentX(LEFT_ALIGNMENT);

		
        g.setConstraints(batchTitle,c);         p.add(batchTitle);
        
        g.setConstraints(l1,c);         p.add(l1);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.weightx = Integer.MAX_VALUE;
        g.setConstraints(inputField,c);
        p.add(inputField);
        
        c.weightx = 0.2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        g.setConstraints(b1,c);
        p.add(b1);
        
        c.weightx = 1;
        g.setConstraints(l2,c);        p.add(l2);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.weightx = Integer.MAX_VALUE;
        c.weightx = 0.2;
        g.setConstraints(outputField,c);
        p.add(outputField);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        g.setConstraints(b2,c);
        p.add(b2);		

        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp,BoxLayout.LINE_AXIS));
        bp.add(loadBatch);bp.add(saveBatch);
        bp.setPreferredSize(new Dimension(Integer.MAX_VALUE,24));
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        g.setConstraints(bp,c);
        p.add(bp);        

        c.gridwidth = GridBagConstraints.REMAINDER;
        g.setConstraints(configLabel,c);
        p.add(configLabel);        
        
        JPanel progressPanel = new JPanel();
        
        progressPanel.setBorder(BorderFactory.createTitledBorder("Batch status"));
        progressPanel.setLayout(new BorderLayout());

        Dimension d1 = new Dimension(200,24);
        stateLabel = new JLabel("");
        stateLabel.setMinimumSize(d1);
        stateLabel.setPreferredSize(d1);
        stateLabel.setVisible(true);
        progressLabel = new JLabel("");
        progressLabel.setMinimumSize(d1);
        progressLabel.setPreferredSize(d1);
        progressLabel.setVisible(true);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(stateLabel,BorderLayout.WEST);
        topPanel.add(progressLabel,BorderLayout.CENTER);
        topPanel.setMinimumSize(new Dimension(400,24));
        topPanel.setPreferredSize(new Dimension(400,24));
                
        
        progressPanel.add(topPanel,BorderLayout.CENTER);
        
        
        progressBar = new JProgressBar();
        progressPanel.add(progressBar,BorderLayout.SOUTH);
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        
        main.add(p,BorderLayout.CENTER);
        main.add(progressPanel,BorderLayout.SOUTH);
		return main;
	}

	/* (non-Javadoc)
	 * @see toxTree.ui.AbstractJobProcessingDialog#cancelAction()
	 */
	@Override
	protected void cancelAction() {
		
		try {
			if (batch != null) {
				batch.cancel();
				if (batch instanceof Observable)
					((Observable)batch).deleteObservers();
				ToxTreeActions.showMsg(batch.toString(),"Batch processing aborted!");
			}
		} catch (BatchProcessingException x) {
			
			ToxTreeActions.showMsg("Batch processing aborted!",x.getMessage());
			x.printStackTrace();
		}
		super.cancelAction();
	}

	/* (non-Javadoc)
	 * @see toxTree.ui.AbstractJobProcessingDialog#okAction()
	 */
	@Override
	protected void okAction() {
		super.okAction();
		pauseButton.setEnabled(true);
		enableDataControls(false);
        batch();
	}
	/* (non-Javadoc)
	 * @see toxTree.ui.AbstractJobProcessingDialog#pauseAction()
	 */
	@Override
	protected void pauseAction() {
		try {
			pauseButton.setEnabled(false);
			batch.pause();
			super.pauseAction();
		} catch (BatchProcessingException x) {
			x.printStackTrace();
		}

	}
	/* (non-Javadoc)
	 * @see toxTree.ui.AbstractJobProcessingDialog#addWidgets()
	 */
	@Override
	protected void addWidgets() {
		super.addWidgets();
		okButton.setText("Start");
	}
	/**
	 * @return Returns the decisionMethod.
	 */
	public synchronized IDecisionMethod getDecisionMethod() {
		return decisionMethod;
	}
	/**
	 * @param decisionMethod The decisionMethod to set.
	 */
	public synchronized void setDecisionMethod(IDecisionMethod decisionMethod) {
		this.decisionMethod = decisionMethod;
	}
	protected void batch() {
		try {
			if (batch == null) {
				ToxTreeBatchProcessing b = new ToxTreeBatchProcessing(openFile,saveFile);
				b.setDecisionMethod(decisionMethod);
				this.batch = b;
			} else if (batch instanceof Observable) ((Observable) batch).deleteObservers();
			if (batch instanceof Observable) ((Observable)batch).addObserver(this);
		} catch (BatchProcessingException x) {
			batch  = null;
			jobFinished(x);
			setErrorMessage(x);
			
			return;
		}
    	
        final toxTree.ui.GUIWorker worker = new toxTree.ui.GUIWorker() {
            @Override
			public Object construct() {
            	try {
            		setErrorMessage(null);
            		batch.start();
            	} catch (BatchProcessingException x) {
            		
            		batch  = null;
            		jobFinished(x);
            		setErrorMessage(x);
            		ToxTreeActions.showMsg(x.getMessage(),"Error on batch processing");
            	}               	
                return batch;
            }
            //Runs on the event-dispatching thread.
            @Override
			public void finished() {
            	try {
            		if (batch != null)
            			batch.close();
            		jobFinished(null);
            	} catch (BatchProcessingException x) {
            		ToxTreeActions.showMsg(x.getMessage(),"Error on batch processing");
            	}
        		    	
            }
        };
        worker.start(); 	
	}
	protected void jobFinished(Exception x) {
		if ((x == null) && (batch != null)) {
			if (batch.isPaused()) {
				okButton.setVisible(true);
				okButton.setText("Continue");
				pauseButton.setVisible(false);
				cancelButton.setVisible(true);
				
			} else {
				if (batch instanceof Observable) ((Observable)batch).deleteObservers();
				okButton.setVisible(true);
				pauseButton.setVisible(false);
				cancelButton.setVisible(false);
				result = JOptionPane.OK_OPTION;
				if (!batch.isCancelled())
					JOptionPane.showMessageDialog(getParent(),
							"Batch completed!",						
							batch.toString(),
						    JOptionPane.INFORMATION_MESSAGE);				
				setVisible(false);
			}
		} else { //stopped because of exception	
			if (batch != null) {
				if (batch instanceof Observable) ((Observable)batch).deleteObservers();
				batch = null;
			}
			enableDataControls(true);
			okButton.setVisible(true);
			pauseButton.setVisible(false);
			cancelButton.setVisible(true);
			
		}
	}
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (batch == null) return;
		if (o instanceof BatchProcessing) {
			BatchProcessing b = (BatchProcessing) o;
			boolean running = (b.isStatus(BatchProcessing.STATUS_RUNNING));
            if ((b !=null) && (b instanceof ToxTreeBatchProcessing)) {
                batchTitle.setText(((ToxTreeBatchProcessing)b).getDecisionMethod().toString());
            }            
			progressBar.setIndeterminate(running);
			progressBar.setVisible(running || b.isStatus(BatchProcessing.STATUS_PAUSED));
			stateLabel.setText(b.toString());
			progressLabel.setToolTipText("");
			progressLabel.setText("Records processed " + b.getWrittenRecordsCount());
			pauseButton.setVisible(running || b.isStatus(BatchProcessing.STATUS_PAUSED));
			if (b.getConfigFile() != null)
			configLabel.setText("Batch configuration: " +
					new SimpleDateFormat("M/d/y,H:m",Locale.US).format(b.getDateLastProcessed()) + 
					" " + b.getConfigFile().getName());
		}

	}
	/**
	 * @return Returns the batch.
	 */
	public synchronized IBatchProcessing getBatch() {
		return batch;
	}
	/**
	 * @param batch The batch to set.
	 */
	public synchronized void setBatch(IBatchProcessing batch) {
		this.batch = batch;
		if (batch != null) 
			if (batch instanceof ToxTreeBatchProcessing) {
				    ToxTreeBatchProcessing b = (ToxTreeBatchProcessing) batch;
					cleanPausedBatch();
   					
   					this.batch = b;
                    if (b.getDecisionMethod() == null)
                        batchTitle.setText("No decision method assigned!");                    
                    else batchTitle.setText(b.getDecisionMethod().toString());
   					openFile = b.getInputFile().getFile();
   					inputField.setText(openFile.getAbsolutePath());
   					saveFile = b.getOutputFile().getFile();
   					outputField.setText(saveFile.getAbsolutePath());
   					
   					update((Observable)batch,null);
   					if (batch.isPaused()) {
   						okButton.setVisible(true);
   						okButton.setText("Continue");
   						pauseButton.setVisible(false);
   						cancelButton.setVisible(true);
   					}

			}
	}
	@Override
	public void enableDataControls(boolean enable) {
		inputField.setEnabled(enable);
		outputField.setEnabled(enable);
		b1.setEnabled(enable);
		b2.setEnabled(enable);
		loadBatch.setEnabled(enable);
		saveBatch.setEnabled(enable);
		
	}
	protected void cleanPausedBatch() {
		if ((batch !=null) && (batch.isPaused())) {
			try {
				batch.cancel();
				batch.close();
			} catch (BatchProcessingException x) {
				setErrorMessage(x);
				x.printStackTrace();
				batch = null;
			}
		}
	}
	protected void setErrorMessage(BatchProcessingException x) {
		if (x != null) { 
			stateLabel.setText("Error");
			progressLabel.setText(x.getMessage());
			progressLabel.setToolTipText(x.getMessage());
		} else {
			
			stateLabel.setText("");
			progressLabel.setText("");
			progressLabel.setToolTipText("");			
		}
	}
	protected void setOutputFile(File f) {
		if (f != null) {
			saveFile = f;
			outputField.setText(saveFile.getAbsolutePath());
			okButton.setText("Start");
			batch = null;					
		}
	}
}
