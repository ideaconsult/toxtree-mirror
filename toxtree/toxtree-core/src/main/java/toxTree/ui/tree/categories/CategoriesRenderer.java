/*
Copyright (C) 2005-2006  

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
package toxTree.ui.tree.categories;

import java.awt.Color;

import toxTree.core.IDecisionCategories;

/**
 * Used mainly to provide an unique color per a category. Used by {@link toxtree.ui.tree.categories.CategoriesPanel}
 * and {@link toxtree.ui.tree.TreeLayout}  
 * @author Nina Jeliazkova
 *
 */
public class CategoriesRenderer {
    protected Color[] hideColors = null;
    protected Color[] showColors = null;
    
	public CategoriesRenderer() {
		super();
	}
	public CategoriesRenderer(int numberofcategories,int transparency) {
		super();
		setCategories(numberofcategories,transparency);
	}
	public CategoriesRenderer(IDecisionCategories categories) {
		this(categories.size());
	}	
	public CategoriesRenderer(int numberofcategories) {
		this(numberofcategories,200);
		
	}
	public void setCategories(IDecisionCategories categories) {
		setCategories(categories.size());

	}
	public void setCategories(int numberofcategories) {
		setCategories(numberofcategories,200);
	}
	public void setCategories(int numberofcategories, int transparency) {
        hideColors = getColors(numberofcategories,60,50);
        showColors = getColors(numberofcategories,180,transparency);		
	}
	private Color[] getColors(int size, int intensity, int alpha) {
	    Color[] colors = null;
		colors = new Color[size];
		int n = size;
		if (n <= 3)  {
			if (n > 0) colors[0] = new Color(0,intensity,0,alpha);
			if (n > 1) colors[1] = new Color(intensity,intensity,0,alpha);
			if (n > 2) colors[2] = new Color(intensity,0,0,alpha);
		} else {
			float sat = intensity / 255.0f;
			float bri = alpha / 255.0f;
			for (int i = 0; i < n; i++) 
				
			        colors[i] = Color.getHSBColor(((float)i/n),sat,bri);
		}	
		return colors;
	}
	public Color getHideColor(int index) {
		return hideColors[index % hideColors.length];
	}
	public Color getShowColor(int index) {
        if ((index >= 0) && (index<showColors.length))
        		return showColors[index % showColors.length];
        else return Color.white;
	}	

}
