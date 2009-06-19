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

import java.util.ArrayList;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IProcessRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionMethodIOException;

public class Tree2PrefuseGraph implements IProcessRule {
	   protected Graph graph;
	    public static final String FIELD_ID = "id";
	    public static final String FIELD_SHORT_NAME = "short";
	    public static final String FIELD_NAME = "name";
	    public static final String FIELD_EXPLANATION = "explanation";
	    public static final String FIELD_NODETYPE = "nodetype";
	    public static final String FIELD_CATEGORY = "category";
	    public static final String FIELD_ANSWER = "answer";
	    public static final String FIELD_RULE = "rule";

	    
	    public Tree2PrefuseGraph(Graph graph) {
	        this.graph = graph;
	    }
	    public void done() throws DecisionMethodIOException {
	        // TODO Auto-generated method stub
	        
	    }

	    public void init(IDecisionMethod method) throws DecisionMethodIOException {
	        graph.addColumn(Tree2PrefuseGraph.FIELD_ID, int.class);
	        graph.addColumn(Tree2PrefuseGraph.FIELD_SHORT_NAME, String.class);
	        graph.addColumn(Tree2PrefuseGraph.FIELD_NAME, String.class);
	        graph.addColumn(Tree2PrefuseGraph.FIELD_CATEGORY, String.class);
	        graph.addColumn(Tree2PrefuseGraph.FIELD_NODETYPE, int.class);
	        graph.addColumn(Tree2PrefuseGraph.FIELD_EXPLANATION, String.class);
	        graph.addColumn(Tree2PrefuseGraph.FIELD_ANSWER, String.class);
	        //graph.addColumn(Tree2PrefuseGraph.FIELD_RULE, IDecisionMethod.class);	        
	   }

	    public Object process(IDecisionMethod method, IDecisionRule rule) throws DecisionMethodIOException {
	    	for (int i=0; i < graph.getNodeCount();i++) {
	    		Node n = graph.getNode(i);
	    		if ((n.getInt(FIELD_ID) == rule.getNum()) && (n.getInt(FIELD_NODETYPE)==0))
	    			return graph.getNode(i);
	    	}
	        Node n1 = graph.addNode(); 
	        
	        n1.setInt(FIELD_ID, rule.getNum());
	        n1.setString(FIELD_SHORT_NAME, rule.getID());
	        n1.setString(FIELD_NAME,rule.getID() + '.'+ rule.getTitle());
	        n1.setInt(FIELD_NODETYPE,0);
	        n1.setString(FIELD_EXPLANATION,"<html>"+"<b>"+rule.getTitle()+ "</b><br>"+rule.getExplanation()+"</html>");
	        //n1.set(FIELD_RULE, rule);
	        return n1;
	    }
		public Graph getGraph() {
			return graph;
		}
		public void setGraph(Graph graph) {
			this.graph = graph;
		}

	   public Node  walkRules(IDecisionMethod tree)throws  DecisionMethodException {
		        ArrayList<Integer> visited = new ArrayList<Integer>();
		        init(tree);
		        Node n = walkRules(tree,tree.getTopRule(),visited);
		        done();
		        return n;
	   }
	   protected Node walkRules(IDecisionMethod tree,IDecisionRule rule, ArrayList<Integer> visited) throws DecisionMethodException {
		        if (rule == null) return null;
		        else {
		        	Node o = (Node)process(tree, rule);
		            if (visited.indexOf(rule.getNum()) == -1) {
		            	
		                visited.add(rule.getNum());
		                final boolean[] answers = {true,false};
		                
		                IDecisionRule prevrule = null;
		                for (int i=0; i < answers.length;i++) {
		                    IDecisionRule nextrule = tree.getBranch(rule, answers[i]);
		                    //if (((prevrule == null) || (prevrule != nextrule)) && (nextrule != null)) { 
		                    if ((nextrule != null)) {
		                        Node next = walkRules(tree,nextrule,visited);
		                        if (next != null) {
		                            Edge edge = getGraph().addEdge(o, next);
		                            
		                            if (answers[i])
		                            	edge.setString(FIELD_ANSWER, AbstractRule.MSG_YES);
		                            else
		                            	edge.setString(FIELD_ANSWER, AbstractRule.MSG_NO);
		                            edge.setString(FIELD_EXPLANATION,edge.getString(FIELD_ANSWER)); 
		                        }    
		                        prevrule = nextrule;
		                    }
		                    IDecisionCategory c = tree.getCategory(rule, answers[i]);
		                    if (c != null) {
		                        Node cnode = getGraph().addNode(); 
		                        cnode.setInt(FIELD_ID, c.getID()) ; //;
		                        cnode.setString(FIELD_NAME, c.getName());
		                        cnode.setString(FIELD_SHORT_NAME, Integer.toString(c.getID()));
		                        cnode.setInt(FIELD_NODETYPE,c.getID());
		                        cnode.setString(FIELD_EXPLANATION, "<html><b>"+ c.getName() + "</b><br>" + c.getExplanation()+"</html>");
		                        Edge edge = getGraph().addEdge(o, cnode);
		                        if (answers[i])
		                        	edge.setString(FIELD_ANSWER, AbstractRule.MSG_YES);
		                        else
		                        	edge.setString(FIELD_ANSWER, AbstractRule.MSG_NO);
		                        edge.setString(FIELD_EXPLANATION,edge.getString(FIELD_ANSWER));                        
		                    }
		                }             
		                return o;
		            } else {
		            	//processor.graph.getNodes().
		            	return o;
		            }
		        }
		        

     }
}


