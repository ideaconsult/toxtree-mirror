
/*
Copyright Ideaconsult Ltd. (C) 2005-2011 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionRule;
import toxTree.core.IMetaboliteGenerator;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.RuleResult;
import toxtree.data.ActionList;
import toxtree.data.ToxTreeActions;
import toxtree.data.ToxTreeModule;
import toxtree.ui.tree.categories.CategoriesPanel;
import toxtree.ui.tree.rules.RulePanel;


/**
 * The panel at the right of {@link toxTree.apps.ToxTreeApp} main screen.
 * Displays estimation result and details.
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-4-30
 */
public class HazardPanel extends DataModulePanel<ToxTreeModule> {
    protected Action editAction;

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8769660495123570173L;
	
	JLabel methodLabel;
	GridBagLayout gridBag;
	JButton estimateButton;
	JCheckBox explainOption;
	JEditorPane explainArea;
	TitledBorder tBorder;
	CategoriesPanel cPanel;
	JLabel metabolites;
	/**
	 * 
	 */
	public HazardPanel(ToxTreeModule toxModule) {
		super(toxModule);
		setBackground(Color.black);
		setLayout(gridBag);
		setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
	}
	/* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable o, Object arg) {
        methodLabel.setText("<html><b> by <u>" + 
                getDataModule().getRules().toString() + 
                "</u></b></html>");
        methodLabel.setToolTipText(getDataModule().getRules().getExplanation());

        tBorder.setTitle(getDataModule().getRules().toString());
        
        IDecisionCategories assignedCategories = getDataModule().getTreeResult().getAssignedCategories();
        for (int i=0; i < assignedCategories.size();i++)
        	getDataModule().getRules().getCategories().setSelected(assignedCategories.get(i));
        
        //model.getRules().getCategories().setSelected(model.getTreeResult().getCategory());
                
        cPanel.setData(getDataModule().getRules().getCategories(),getDataModule().getTreeResult());
        
        //setHazardClass(model.getClassID());
        StringBuffer b = new StringBuffer();
        try {
            b = getDataModule().getTreeResult().explain(explainOption.isSelected());
        } catch (DecisionResultException x) {
            b.append(x.getMessage());
        }
        explainArea.setText(b.toString());
        
        if (getDataModule().getRules() instanceof IMetaboliteGenerator) {
        	metabolites.setVisible(true);
        	metabolites.setToolTipText(((IMetaboliteGenerator)getDataModule().getRules()).getHelp());
        } else metabolites.setVisible(false);
        repaint();
    }
    
	@Override
	protected void addWidgets(ActionList actions) {

		gridBag = new GridBagLayout();
		//setLayout(gridBag);
		GridBagConstraints c = new GridBagConstraints();

        JLabel label = new JLabel("<html><b>Toxic Hazard</b></html>");
        label.setOpaque(true);
        label.setBackground(Color.black);
        label.setForeground(Color.white);
        label.setSize(120,32);
        label.setAlignmentX(CENTER_ALIGNMENT);
		c.weightx = 1;
		c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;        
        c.gridwidth = GridBagConstraints.RELATIVE;
		gridBag.setConstraints(label,c);
		add(label);
		
        methodLabel = new JLabel("");
        methodLabel.setOpaque(true);
        methodLabel.setBackground(Color.black);
        methodLabel.setForeground(Color.white);
        methodLabel.setSize(120,32);
        methodLabel.setAlignmentX(CENTER_ALIGNMENT);
		c.weightx = 1;
		c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;        
        c.gridwidth = GridBagConstraints.REMAINDER;
		gridBag.setConstraints(methodLabel,c);
		add(methodLabel);
		editAction = ((ToxTreeActions)actions).getViewMethodAction();
		methodLabel.addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			editAction.actionPerformed(new ActionEvent(this,0,""));
	   		}
	    });	  		
		
				
		estimateButton = new JButton(((ToxTreeActions)actions).getEstimateAction());
		//estimateButton.setBackground(Color.darkGray);
		c.weightx = 1;
		c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;        
        c.gridwidth = GridBagConstraints.REMAINDER;
		
		gridBag.setConstraints(estimateButton,c);
		add(estimateButton);		

		JPanel propertiesPanel = new JPanel(new BorderLayout());
		cPanel = new CategoriesPanel(getDataModule().getRules().getCategories(),getDataModule().getTreeResult());
		cPanel.setMinimumSize(new Dimension(256,3*48+2));
		cPanel.setPreferredSize(new Dimension(256,3*48+2));
/*
		c.weightx = 1;
		c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;        
        c.gridwidth = GridBagConstraints.REMAINDER;
		gridBag.setConstraints(cPanel,c);
        
     	add(cPanel);
     	*/
		propertiesPanel.add(cPanel,BorderLayout.CENTER);
		
     	explainOption = new JCheckBox("Verbose explanation");
     	explainOption.setSelected(true);
     	
     	JPanel bottom = new JPanel();
     	bottom.setLayout(new GridLayout(1,2));
     	bottom.add(explainOption);
     	
     	//explainOption.addMouseListener(new MetabolitesMouseAdapter());
     	
		metabolites = new JLabel("<html><u><b>Metabolites</b></u></html>");
		metabolites.setAlignmentX(RIGHT_ALIGNMENT);
        metabolites.setToolTipText("Click to view the predicted metabolites.");
        metabolites.addMouseListener(new MetabolitesMouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			try {
	   				dataModule.showMetabolites();
	   			} catch (Exception x) {
	   				JOptionPane.showMessageDialog(metabolites,String.format("%s",x.getMessage()),"Error",JOptionPane.ERROR_MESSAGE);
	   			}
	   		}
	    });	      	
     	bottom.add(metabolites);
     	metabolites.setVisible(false);
     	
     	propertiesPanel.add(bottom,BorderLayout.SOUTH);
     
     	
		
        c.fill = GridBagConstraints.BOTH;
        c.weighty = Integer.MAX_VALUE;

		explainArea = new JTextPane();
		explainArea.setAutoscrolls(true);
		explainArea.setContentType("text/html");	
		//ToolTipManager.sharedInstance().registerComponent(explainArea);

		
		HTMLEditorKit kit = new HTMLEditorKit();
		
		explainArea.setEditorKit(kit);
		explainArea.setEditable(false);
		//TODO this is a hack to parse predefined URIs.  refactor to expose models/rules as URI
		explainArea.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent he) {
				HyperlinkEvent.EventType type = he.getEventType();
				if (type == HyperlinkEvent.EventType.ENTERED) {
					explainArea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else	if (type == HyperlinkEvent.EventType.EXITED) {	
					explainArea.setCursor(Cursor.getDefaultCursor());
				} else if (type == HyperlinkEvent.EventType.ACTIVATED) {
			    	if (he.getURL().toString().startsWith(RuleResult.ruleURL)) {
			    		String name = he.getURL().toString().substring(RuleResult.ruleURL.length());
			    		
			    		IDecisionRule rule = getDataModule().getRules().getRule(name);
			    		if (rule != null) {
			    			RulePanel panel = new RulePanel(rule);
			    			panel.setRule(rule);
			    			panel.setEditable(false);
			    			panel.setPreferredSize(new Dimension(500,500));
			    			JOptionPane.showMessageDialog(cPanel,panel,"Rule:"+rule.getTitle(),JOptionPane.PLAIN_MESSAGE,null);
			    		}
			    			
			    	} else if (he.getURL().toString().startsWith(RuleResult.categoryURL)) {
			    		/*
				    	String name = he.getURL().toString().substring(RuleResult.categoryURL.length());
				    	try {
				    		IDecisionCategory category = model.getTreeResult().getDecisionMethod().getgory(Integer.parseInt(name));
				    		
				    		JOptionPane.showMessageDialog(cPanel,category.getEditor().getVisualCompoment());
				    	} catch (Exception x) {
				    		x.printStackTrace();
				    	}
				    	*/
				    } else if (he.getURL().toString().startsWith(RuleResult.alertURL)) {
			    		String name = he.getURL().toString().substring(RuleResult.alertURL.length());
			    		
			    		IDecisionRule rule = getDataModule().getRules().getRule(name);
			    		if (rule != null) try {
			    			//hilight alerts
			    			getDataModule().getTreeResult().hilightAlert(rule);
			    		} catch (Exception x) { x.printStackTrace();}
				    }  else if (he.getURL().toString().startsWith(RuleResult.resultURL)) {
			    		String id = he.getURL().toString().substring(RuleResult.resultURL.length());
			    		
			    		if (id != null) try {
				    		RuleResult ruleResult = getDataModule().getTreeResult().getRuleResult(Integer.parseInt(id));

			    			getDataModule().getTreeResult().hilightAlert(ruleResult);
			    		} catch (Exception x) { x.printStackTrace();}
				    }
				    	
			    						    	
			    }
				
			}
		});
		
        // add some styles to the html
		/*
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
        styleSheet.addRule("h1 {color: blue;}");
        styleSheet.addRule("h2 {color: #ff0000;}");
        styleSheet.addRule("pre {color : black; background-color : #fafafa; }");
        styleSheet.addRule("yes { color : green; }");
        styleSheet.addRule("no { color : red; }");	
        */
	    Document doc = kit.createDefaultDocument();
	    explainArea.setDocument(doc);

	        
		tBorder = BorderFactory.createTitledBorder("");
		explainArea.setBorder(tBorder);
        JScrollPane scrollText = new JScrollPane(explainArea);

        JSplitPane splitPanel = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                propertiesPanel, scrollText);
        splitPanel.setBackground(Color.black);
        splitPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        //splitPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Title"));
        splitPanel.setOneTouchExpandable(false);
        splitPanel.setDividerLocation(200);
        
		gridBag.setConstraints(splitPanel,c);
		add(splitPanel);		
	}
	
	public void addActionListener(ActionListener l) {
	    estimateButton.addActionListener(l);
	}

}

class MetabolitesMouseAdapter extends MouseAdapter {
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		if (e.getComponent() instanceof JLabel)
			((JLabel)e.getComponent()).setBorder(BorderFactory.createLineBorder(Color.white));
		else if (e.getComponent() instanceof JCheckBox)
			((JCheckBox)e.getComponent()).setBorder(BorderFactory.createLineBorder(Color.white));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		//e.getComponent().setBackground(Color.black);
		if (e.getComponent() instanceof JLabel)
			((JLabel)e.getComponent()).setBorder(null);
		else if (e.getComponent() instanceof JCheckBox)
			((JCheckBox)e.getComponent()).setBorder(null);
		e.getComponent().setCursor(Cursor.getDefaultCursor());
	}
}

