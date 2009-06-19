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

public class MatrixPreprocessor implements IDescriptorPreprocessor {
	protected double[][] matrix = null;
	protected String explanation;
	protected int dimension;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3014828295822399358L;

	public MatrixPreprocessor(double[][] matrix) throws Exception {
		setDimension(matrix.length);
		setMatrix(matrix);
	}
	public double[] process(double[] values) throws Exception  {
		double[] sum = new double[matrix.length];
		for (int i=0; i < matrix.length;i++) {
			sum[i] = 0;
			int n = matrix[i].length;
			for (int j=0; j < (n-1);j++)
				sum[i] += values[i]*matrix[i][j];
			sum[i] +=  matrix[i][n-1];
		}
		return sum;
	}
	public double[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(double[][] matrix) throws Exception {
		this.matrix = matrix;
		if (getDimension() != matrix.length) throw new Exception("Incompatible lengths");
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


