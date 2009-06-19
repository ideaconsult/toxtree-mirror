/*
Copyright (C) 2005-2008  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.qsar;



public class LinearPreprocessor implements IDescriptorPreprocessor {
	protected double[] scale;
	protected double[] translation;
	protected String explanation = "";
	protected int dimension;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8354221117667838300L;

	public LinearPreprocessor(double[] scale, double[] translation) throws Exception {
		if (scale.length != translation.length) throw new Exception("Incompatible lengths");
		setDimension(scale.length);
		setScale(scale);
		setTranslation(translation);
	}
	public double[] process(double[] values) throws Exception  {
		if ((scale == null) && (translation ==null)) return values;
		
		if (values.length != getDimension())
			throw new Exception("Incompatible lengths: Vector of length "+scale.length + " expected.");
		
		for (int i=0; i < values.length;i++) 
			values[i] =  scale[i]*values[i] + translation[i];
		return values;
	}
	public double[] getScale() {
		return scale;
	}
	public void setScale(double[] scale) throws Exception {
		if (getDimension() != scale.length) throw new Exception("Incompatible lengths");
		this.scale = scale;
	}
	public double[] getTranslation() {
		return translation;
	}
	public void setTranslation(double[] translation) throws Exception {
		if (getDimension() != translation.length) throw new Exception("Incompatible lengths");
		this.translation = translation;
	}
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(getExplanation());
		b.append(System.getProperty("line.separator"));
		for (int i=0; i < scale.length;i++) {
			b.append(scale[i]);
			b.append('*');
			b.append('X');
			b.append(i+1);
			if (translation[i] >= 0)
				b.append('+');
			b.append(translation[i]);
			b.append(System.getProperty("line.separator"));
		}
		return b.toString();
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
}


