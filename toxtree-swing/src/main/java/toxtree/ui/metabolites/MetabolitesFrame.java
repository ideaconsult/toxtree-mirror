package toxtree.ui.metabolites;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.JFrame;

import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.core.IDecisionMethod;

public class MetabolitesFrame extends JFrame {
	protected MetabolitesPanel metabolitesPanel;

/**
	 * 
	 */
	private static final long serialVersionUID = -6393108313154870081L;

	public MetabolitesFrame() throws HeadlessException {
       super();
       addWidgets(null);
       setTitle("Metabolites");
   }
	protected void addWidgets(IAtomContainerSet products) {
		   	
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        //
			metabolitesPanel = new MetabolitesPanel();
	        
	        getContentPane().add(metabolitesPanel);
	        
	        pack();
	        setVisible(true);
			Dimension dim = getToolkit().getScreenSize();
			Rectangle abounds = getBounds();
			setLocation((dim.width - abounds.width) / 2 + 100,
			      (dim.height - abounds.height) / 2);        
	}
	

	   public IAtomContainerSet getProducts() {
			return metabolitesPanel.getProducts();
		}
		public void setProducts(IAtomContainerSet products) {
			metabolitesPanel.setProducts(products);
		}
}
