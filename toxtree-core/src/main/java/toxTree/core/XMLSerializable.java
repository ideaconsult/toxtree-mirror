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

package toxTree.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import toxTree.exceptions.XMLDecisionMethodException;

public interface XMLSerializable {
	public static String xmltag_NAME = "name";
	public static String xmltag_ID = "id";
	public static String xmltag_EXPLANATION = "explanation";
	public static String xmltag_METHODS = "methods";
	public static String xmltag_METHOD = "method";
	public static String xmltag_CLASS = "class";
	public static String xmltag_METHODPARAMETER = "parameter";
	public static String xmltag_TREEROOT = "root";
	public static String xmltag_RULES = "rules";
	public static String xmltag_RULE = "rule";
	public static String xmltag_RULEEXAMPLE = "example";
	public static String xmltag_CATEGORIES = "categories";
	public static String xmltag_CATEGORY = "category";	
	public static String xmltag_CATEGORYTHRESHOLD = "threshold";
	public Element toXML(Document document) throws XMLDecisionMethodException;
	public void fromXML(Element xml)  throws XMLDecisionMethodException ;
	public Element toShallowXML(Document document)  throws XMLDecisionMethodException;
}


