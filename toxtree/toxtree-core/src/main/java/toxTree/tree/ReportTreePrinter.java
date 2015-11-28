package toxTree.tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.core.IImplementationDetails;
import toxTree.core.IProcessRule;
import toxTree.exceptions.DecisionMethodIOException;

public class ReportTreePrinter extends AbstractTreeWriter implements
		IProcessRule {
	protected String folder = "";

	public ReportTreePrinter() {
		super(System.out);
	}

	public ReportTreePrinter(OutputStream outputStream) {
		super(outputStream);
	}

	public ReportTreePrinter(File file) throws FileNotFoundException {
		super(new FileOutputStream(file));
		File dir = file.getParentFile();
		if (dir != null)
			folder = dir.getAbsolutePath();
	}

	public void init(IDecisionMethod tree) throws DecisionMethodIOException {
		try {
			writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
			writer.write("<html>");
			writer.write("<head>");
			writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">");
			writer.write("<title>");
			writer.write(tree.getTitle());
			writer.write("</title>");
			writer.write("</head>");
			writer.write("<body>");
			writer.write("<h2>");
			writer.write(tree.getTitle());
			writer.write("</h2>");
			writer.write("<h5>");
			writer.write(tree.getExplanation());
			writer.write("</h5>");

			IDecisionCategories c = tree.getCategories();
			writer.write("<h4>");
			writer.write("Categories assigned:");
			writer.write("</h4>");
			writer.write("<table width='95%' border='1'>");
			for (int i = 0; i < c.size(); i++) {
				writer.write("<tr>");
				writer.write("<th align='left' width='50%'>");
				writer.write("<a name='c");
				writer.write(c.get(i).getName());
				writer.write("'>");
				writer.write(c.get(i).getName());
				writer.write("</a>");
				writer.write("</th>");
				writer.write("<td>");
				writer.write(c.get(i).getExplanation());
				writer.write("</td>");

				writer.write("</td>");
				writer.write("</tr>");
			}
			writer.write("</table>");

			writer.write("<h3>");
			writer.write("Rules:");
			writer.write("</h3>");
			writer.write("<table width=\"95%\" border='1'>");
			writer.write("<tr><td>");
			IDecisionRuleList rules = tree.getRules();
			for (int i = 0; i < rules.size(); i++) {
				writer.write("<a href=\"#n");
				writer.write(Integer.toString(rules.get(i).getNum()));
				writer.write("\" title=\"");
				writer.write(rules.get(i).getTitle());
				writer.write("\">");
				writer.write(rules.get(i).getID());
				writer.write("</a>");
				writer.write(" ");
			}
			writer.write("</td></tr>");
			writer.write("</table>");
			writer.write("<table border='1' >");
			writer.write("<thead><th>Question</th><th>If Yes</th><th>If No</th><th>Example Yes</th><th>Example No</th><th>Implementation</th></thead><tbody>");
		} catch (Exception x) {
			throw new DecisionMethodIOException(x);
		}

	}

	@Override
	public void done() throws DecisionMethodIOException {
		try {
			writer.write("</tbody></table>");
			writer.write("</body>");
			writer.write("</html>");

		} catch (Exception x) {
			throw new DecisionMethodIOException(x);
		} finally {
			super.done();
		}

	}

	public Object process(IDecisionMethod method, IDecisionRule rule)
			throws DecisionMethodIOException {
		try {
			writer.write("\n<tr>");

			writer.write("\n<td>");
			writer.write("<a name='n");
			writer.write(Integer.toString(rule.getNum()));
			writer.write("'>");
			writer.write(rule.getID());
			writer.write(". </a>");
			writer.write(rule.getExplanation());
		
			writer.write("</td>");
			//writer.write("</tr>");

			final boolean[] answers = { true, false };
			for (int i = 0; i < answers.length; i++) {
				IDecisionCategory category = method.getCategory(rule,
						answers[i]);
				IDecisionRule nextrule = method.getBranch(rule, answers[i]);

				writer.write("\n<td>");
				writer.write(answers[i]?"If YES":"If NO");
				writer.write("<br/>");
				if (category != null) {
					writer.write("ASSIGN ");
					writer.write("<a href='#c");
					writer.write(category.getName());
					writer.write("'>");
					writer.write("<b>");
					writer.write(category.toString());
					writer.write("</b>");
					writer.write("</a>");
					writer.write("<br>");
				}
				if (nextrule != null) {
					writer.write("GO TO Rule ID ");
					writer.write("<b>");
					writer.write("<a href=\"#n");
					writer.write(Integer.toString(nextrule.getNum()));
					writer.write("\" title=\"");
					writer.write(nextrule.getTitle());
					writer.write("\">");
					writer.write(nextrule.getID());
					writer.write("</a>");
					writer.write("</b>");
					writer.write("<br>");
				}
				// rule.getExampleMolecule(ruleResult)

				writer.write("</td>");
				//writer.write("</tr>");

			}

	

			writer.write("\n<td>");
			writer.write("YES");
			try {
				String url = writeMolecule(method,rule, true);
				writer.write("<img src=\"");
				writer.write(url);
				writer.write("\" title=\"a hit\">");
			} catch (Exception x) {
				writer.write("N/A");
			}
			writer.write("</td>");
			writer.write("<td>");
			writer.write("NO");
			try {
				String url = writeMolecule(method,rule, false);
				writer.write("<img src=\"");
				writer.write(url);
				writer.write("\" title=\"not a hit\">");
			} catch (Exception x) {
				writer.write("N/A");
			}

			//
			writer.write("</td>");
			
			writer.write("\n<td style='font-size:80%'>");
			
			IImplementationDetails details = null;
			if (rule instanceof IImplementationDetails)
				details = (IImplementationDetails) rule;
			if ((rule instanceof DecisionNode)
					&& (((DecisionNode) rule).getRule() instanceof IImplementationDetails)) {
				details = (IImplementationDetails) ((DecisionNode) rule)
						.getRule();
			}
			if (details != null) {
				writer.write("<i>");
				writer.write(details.getImplementationDetails().replaceAll(
						"\n", "<br>"));
				writer.write("</i>");
			}
			writer.write("</td>");
		
			return null;
		
		} catch (Exception x) {
			throw new DecisionMethodIOException(x);
		} finally {
			try {
			writer.write("</tr>\n");
			} catch (IOException x) {}
		}

	}

	protected String writeMolecule(IDecisionMethod tree, IDecisionRule rule, boolean answer)
			throws Exception {
		String url = rule.getID() + Boolean.toString(answer) + ".png";
		File file = new File(folder + "/" + url);
		FileOutputStream out = new FileOutputStream(file);
		try {
			writeMolecule(tree,rule, answer, out);
		} finally {
			out.close();
		}
		return url;
	}

}
