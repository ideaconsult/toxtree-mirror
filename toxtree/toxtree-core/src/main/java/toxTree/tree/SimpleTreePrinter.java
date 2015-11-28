/*
Copyright (C) 2005-2006  

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

package toxTree.tree;

import java.io.OutputStream;
import java.io.Writer;

import org.openscience.cdk.smiles.SmilesGenerator;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IProcessRule;
import toxTree.exceptions.DecisionMethodIOException;

public class SimpleTreePrinter extends AbstractTreeWriter implements
		IProcessRule {

	protected char delimiter = '\t';
	protected SmilesGenerator g = SmilesGenerator.generic();

	public char getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	public SimpleTreePrinter() {
		this(System.out);
	}

	public SimpleTreePrinter(OutputStream outputStream) {
		super(outputStream);
	}

	public SimpleTreePrinter(Writer output) {
		super(output);
	}

	public Object process(IDecisionMethod method, IDecisionRule rule)
			throws DecisionMethodIOException {

		try {

			writer.write('"');
			writer.write(rule.getID());
			writer.write('"');
			writer.write(delimiter);
			writer.write('"');
			writer.write(rule.getTitle());
			writer.write('"');
			writer.write(delimiter);

			final boolean[] answers = { true, false };
			for (int i = 0; i < answers.length; i++) {
				IDecisionCategory category = method.getCategory(rule,
						answers[i]);
				IDecisionRule nextrule = method.getBranch(rule, answers[i]);

				if (category != null) {
					writer.write('"');
					writer.write(category.toString());
					writer.write('"');
				} else {
					writer.write(' ');
				}
				writer.write(delimiter);
				if (nextrule != null) {
					writer.write('"');
					writer.write(nextrule.getID());
					writer.write('"');
				} else {
					writer.write(' ');
				}
				writer.write(delimiter);

			}
			writer.write('\n');
			return null;
		} catch (Exception x) {
			throw new DecisionMethodIOException(getClass().getName(), x);
		}

	}

	public void init(IDecisionMethod tree) throws DecisionMethodIOException {
		try {
			String[] header = { "ID", "Title", "if YES then assign label",
					"if YES then go to rule", "if NO then assign label",
					"if NO then go to rule" };

			for (int i = 0; i < header.length; i++) {
				if (i > 0)
					writer.write(delimiter);

				writer.write(header[i]);
			}
			writer.write('\n');
		} catch (Exception x) {
			throw new DecisionMethodIOException(x);
		}
	}

}
