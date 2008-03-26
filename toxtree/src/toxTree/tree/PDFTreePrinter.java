/*
Copyright (C) 2005-2007  

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

import java.awt.Color;
import java.io.OutputStream;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IImplementationDetails;
import toxTree.core.IProcessRule;
import toxTree.exceptions.DecisionMethodIOException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFTreePrinter extends AbstractTreePrinter implements IProcessRule {
	protected Document pdfDoc = null;
	protected PdfWriter pdfWriter;
	public PDFTreePrinter(OutputStream outputStream) {
		super(outputStream);
	}	
	public void init(IDecisionMethod method) throws DecisionMethodIOException {
		pdfDoc = new Document(PageSize.A4, 80, 50, 30, 65);
		try {
			pdfWriter = PdfWriter.getInstance(pdfDoc,getOutputStream());
			//writer.setViewerPreferences(PdfWriter.HideMenubar| PdfWriter.HideToolbar);
			pdfWriter.setViewerPreferences(PdfWriter.PageModeUseThumbs | PdfWriter.PageModeUseOutlines);
			pdfDoc.addCreationDate();
			pdfDoc.addCreator(getClass().getName());
			pdfDoc.addKeywords(method.getCategories().toString());
			pdfDoc.addTitle(method.getTitle());
			pdfDoc.addSubject(method.getExplanation());
			pdfDoc.addAuthor("Toxtree");
			pdfDoc.open();
			
			pdfDoc.add(new Paragraph(method.getTitle()));
			pdfDoc.add(new Paragraph(method.getExplanation()));
			pdfDoc.add(new Paragraph());
		} catch (Exception x) {
			throw new DecisionMethodIOException(x);
		}
	}

	public Object process(IDecisionMethod method, IDecisionRule rule) throws DecisionMethodIOException {
		try {
			PdfPTable table = new PdfPTable(new float[]{2f,6f});
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase("RuleID"));
			cell.setBackgroundColor(new Color(221,221,221));
			
			table.addCell(cell);
			
			Chunk title = new Chunk(rule.getID());
			title.setLocalDestination(rule.getID());
			//title.setFont(bfont);
			
			
			PdfOutline root = pdfWriter.getDirectContent().getRootOutline();
			PdfDestination destination = new PdfDestination(PdfDestination.FITH);
		    PdfOutline outline = new PdfOutline(root, destination,rule.getID());
		    
			cell = new PdfPCell(new Phrase(title));
			cell.setBackgroundColor(new Color(221,221,221));
			table.addCell(cell);			

			cell = new PdfPCell(new Phrase("Title"));
			cell.setBackgroundColor(Color.white);
			table.addCell(cell);			
			cell = new PdfPCell(new Phrase(rule.getTitle()));
			cell.setBackgroundColor(Color.white);
			table.addCell(cell);			

	    	final boolean[] answers = {true,false};
	    	for (int i=0; i < answers.length;i++) {
	    		IDecisionCategory category = method.getCategory(rule, answers[i]);
	    		IDecisionRule nextrule = method.getBranch(rule, answers[i]);
				StringBuffer b = new StringBuffer();			
				b.append("If ");
				if (answers[i]) 
					b.append("YES");
				else 
					b.append("NO");

				cell = new PdfPCell(new Phrase(b.toString()));
				cell.setBackgroundColor(Color.white);
				table.addCell(cell);			    	
				
				b = new StringBuffer();		
				if (category != null) {
					b.append("ASSIGN ");

					b.append(category.toString());
				} 
				if (nextrule != null) {
					b.append("GO TO Rule ID ");
					b.append(nextrule.getID());
				}
				//rule.getExampleMolecule(ruleResult)
				cell = new PdfPCell(new Phrase(b.toString()));
				cell.setBackgroundColor(Color.white);
				table.addCell(cell);				
	
	    	}
			cell = new PdfPCell(new Phrase("Explanation"));
			cell.setBackgroundColor(Color.white);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(rule.getExplanation()));
			cell.setBackgroundColor(Color.white);
			table.addCell(cell);		

			
			IImplementationDetails details = null;
			if (rule instanceof IImplementationDetails)
				details =(IImplementationDetails) rule;
			if ((rule instanceof DecisionNode) &&
					(((DecisionNode)rule).getRule() instanceof IImplementationDetails) 
					) {
				details = (IImplementationDetails) ((DecisionNode)rule).getRule();
			}
			if (details != null)  {
				cell = new PdfPCell(new Phrase("Implementation details"));
				cell.setBackgroundColor(Color.white);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(details.getImplementationDetails()));
				cell.setBackgroundColor(Color.white);
				table.addCell(cell);	
	
			}
			pdfDoc.add(table);
			pdfDoc.add(new Paragraph(14));
            return null;
		} catch (Exception x) {
			throw new DecisionMethodIOException(x);
		}
		
	}
	@Override
	public void done() throws DecisionMethodIOException {
		pdfDoc.close();
		super.done();
	}

}


