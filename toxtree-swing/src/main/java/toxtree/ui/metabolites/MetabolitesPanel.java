package toxtree.ui.metabolites;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import ambit2.ui.Panel2D;

public class MetabolitesPanel extends JPanel {
	protected IAtomContainerSet products;
	public IAtomContainerSet getProducts() {
		return products;
	}
	public void setProducts(IAtomContainerSet products) {
		this.products = products;
		addWidgets();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 113354666505922858L;

	public MetabolitesPanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

		setPreferredSize(new Dimension(300,300));
	}
	protected void addWidgets() {
		removeAll();
		repaint();
		if ((products==null) || (products.getAtomContainerCount()==0))
			add(new JLabel("No metabolites predicted!"));
		else
			for (IAtomContainer ac : products.atomContainers()) {
				Panel2D p = new Panel2D() {
					protected void launchEditor(java.awt.Component parentComponent) {
						//transferMolecule(atomContainer);
					};
				};
				p.setEditable(true);
				p.setAtomContainer(ac, true);
				
				JToolBar toolbar = new JToolBar();
				toolbar.setFloatable(false);
				toolbar.add(new JLabel(ac.getID()));
								
				add(toolbar);
				add(p);
			}
	}
	public void transferMolecule(IAtomContainer ac) {
		
	}
}
