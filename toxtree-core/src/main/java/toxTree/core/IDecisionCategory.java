/*
Copyright Ideaconsult Ltd. (C) 2005-2013  
Contact: jeliazkova.nina@gmail.com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
/**
 * Created on 2005-5-2

 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 *
 * Project : toxtree
 * Package : toxTree.core
 * Filename: IDecisionClass.java
 */
package toxTree.core;

import java.io.Serializable;

import ambit2.base.interfaces.ICategory;

/**
 * An interface definition to represent a chemical category
 * The application of a decision tree {@link toxTree.core.IDecisionMethod} 
 * results in assigning a category to the query chemical. All categories 
 * should implement IDecisionCategory interface. 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public interface IDecisionCategory extends Serializable, Comparable<IDecisionCategory>, ICategory {
	int getID();
	void setID(int id);
	String getName();
	void setName(String name);
	void setExplanation(String name);
	String getExplanation();

	String getThreshold();
	void setThreshold(String threshold);
	IDecisionCategoryEditor getEditor();
	boolean isSelected();
	void setSelected(boolean value);
}
