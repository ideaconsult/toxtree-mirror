package toxtree.ui.metabolites;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
				Panel2D p = new Panel2D();
				p.setAtomContainer(ac, true);
				add(new JLabel(ac.getID()));
				add(p);
			}
	}
}
