package toxtree.ui.metabolites;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.html.HTMLEditorKit;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.io.Tools;
import ambit2.ui.Panel2D;

public class MetabolitesPanel extends JPanel {
	protected IAtomContainerSet products;
	protected JTextPane help = new JTextPane();
	
	public IAtomContainerSet getProducts() {
		return products;
	}
	public void setProducts(String help,IAtomContainerSet products) {
		this.products = products;
		this.help.setText(help);
		addWidgets();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 113354666505922858L;

	public MetabolitesPanel() {
		super();
		help.setEditorKit(new HTMLEditorKit());
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

		setPreferredSize(new Dimension(400,400));
	}
	protected void addWidgets() {
		removeAll();
		repaint();
		if ((products==null) || (products.getAtomContainerCount()==0))
			add(new JLabel("No metabolites predicted!"));
		else {
			add(help);
			for (IAtomContainer ac : products.atomContainers()) {
				Icon toxTreeIcon = null; 
				try {
					toxTreeIcon = Tools.getImage("molecule.png");
		        } catch (Exception x) {
		            toxTreeIcon = null;
		        }
				AbstractAction action = new AbstractAction("Copy molecule",toxTreeIcon) {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
						transferMolecule((IAtomContainer) getValue("molecule"));
						} catch (Exception x) {};
					}
				};
				action.putValue("molecule", ac);
				
				Panel2D p = new Panel2D();
				p.setEditable(false);
				p.setAtomContainer(ac, true);
				
				JToolBar toolbar = new JToolBar();
				toolbar.setBorder(BorderFactory.createRaisedBevelBorder());
				toolbar.setRollover(true);
				toolbar.setFloatable(false);
				toolbar.add(new JLabel(String.format("<html>Reaction: <b>%s</b></html>",ac.getID())));
				JButton copy = new JButton(action);
				copy.setToolTipText("Add as a new molecule into the main Toxtree set of molecules");
				toolbar.add(copy);
				add(toolbar);
				add(p);
			}
		}
	}
	public void transferMolecule(IAtomContainer ac) {
		
	}
}
