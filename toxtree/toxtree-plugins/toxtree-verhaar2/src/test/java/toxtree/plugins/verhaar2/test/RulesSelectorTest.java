/*
Copyright Nina Jeliazkova (C) 2005-2011  
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
package toxtree.plugins.verhaar2.test;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.tree.AbstractTree;
import toxtree.plugins.verhaar2.VerhaarScheme2;

public class RulesSelectorTest {
	
	@Test
	public void testRulesWithSelector() throws Exception {
		 VerhaarScheme2 rules= new VerhaarScheme2();
		    int na = ((AbstractTree)rules).testRulesWithSelector();
			 
		    Assert.assertEquals(0,na);
	}
}

