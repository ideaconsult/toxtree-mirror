/*
Copyright Ideaconsult Ltd.(C) 2006  
Contact: nina@acad.bg

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
package eye.rules;




/**
 * TODO	
 * Aliphatic monoalcohols
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule30 extends  Rule13_AliphaticMonoalcohols{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5991105366142502190L;
	private static final String MSG30="Aliphatic monoalcohols";
	public Rule30() {

		super();		
			setID("30");
			setTitle(MSG30);
			editable = false;
			setExplanation("Aliphatic monoalcohols with a C12-C14 chains");
			/*
			examples[0]="C(O)(CC)(CCCCCCCCCCCCCCCCC)(CCCC)";
			examples[1]="C(O)(CCCCCCCCCCCCCC)(CCC(CCCCCC)CCC)";
			*/
			examples[0] = "C(O)CCCCCCCCC";
			examples[1] = "C(O)CCCCCCCCCCCCC";					


	}
	@Override
	protected int getMaxChainLength() {
		return 14;
	}
	@Override
	protected int getMinChainLength() {
		return 12;
	}	
}
