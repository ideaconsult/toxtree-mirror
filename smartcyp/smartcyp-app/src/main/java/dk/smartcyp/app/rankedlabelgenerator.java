package dk.smartcyp.app;

import java.awt.Color;
import java.util.List;

import javax.vecmath.Point2d;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.jchempaint.renderer.RendererModel;
import org.openscience.jchempaint.renderer.elements.ElementGroup;
import org.openscience.jchempaint.renderer.elements.IRenderingElement;
import org.openscience.jchempaint.renderer.elements.OvalElement;
import org.openscience.jchempaint.renderer.generators.IGenerator;
import org.openscience.jchempaint.renderer.generators.IGeneratorParameter;

import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;

public class rankedlabelgenerator implements IGenerator {
	       
	       public IRenderingElement generate(IAtomContainer ac, RendererModel model) {
	    	   IAtom atom;
	           // this contains the rendering elements
	           ElementGroup rankedCircles = new ElementGroup();
	           
	           // most parameters are in screen space, and have to be scaled
	           double r = model.getAtomRadius() / model.getScale();
	           
	           // make a circle at each ranked atom
	           for (int atomIndex=0; atomIndex < ac.getAtomCount(); atomIndex++) {
	        	   atom = ac.getAtom(atomIndex);
	        	   if (SMARTCYP_PROPERTY.Ranking.get(atom) != null && SMARTCYP_PROPERTY.Ranking.get(atom).intValue() == 1){
	        		   Point2d p = atom.getPoint2d();
		               
		               IRenderingElement oval = 
		                   new OvalElement(p.x, p.y, r, true, new Color(255,204,102));
		               
		               rankedCircles.add(oval);
	        	   }
	        	   if (SMARTCYP_PROPERTY.Ranking.get(atom) != null && SMARTCYP_PROPERTY.Ranking.get(atom).intValue() == 2){
	        		   Point2d p = atom.getPoint2d();
		               
		               IRenderingElement oval = 
		                   new OvalElement(p.x, p.y, r, true, new Color(223,189,174));
		               
		               rankedCircles.add(oval);
	        	   }
	        	   if (SMARTCYP_PROPERTY.Ranking.get(atom) != null && SMARTCYP_PROPERTY.Ranking.get(atom).intValue() == 3){
	        		   Point2d p = atom.getPoint2d();
		               
		               IRenderingElement oval = 
		                   new OvalElement(p.x, p.y, r, true, new Color(214,227,181));
		               
		               rankedCircles.add(oval);
	        	   }

	           }
	           return rankedCircles;
	       }

		@Override
		public List<IGeneratorParameter> getParameters() {
			// TODO Auto-generated method stub
			return null;
		}        
	   }
