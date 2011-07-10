/*
Copyright Istituto Superiore di Sanita' 2009

Contact: rbenigni@iss.it, olga.tcheremenskaia@iss.it, cecilia.bossa@iss.it



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

package toxtree.plugins.func;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.UserDefinedTree;
import toxtree.plugins.func.rules.FuncTreeResult;

/**
 * 

 *
 */
public class FuncRules extends UserDefinedTree implements IDecisionInteractive{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;
    protected boolean residuesIDVisible;
 

    public final static transient String[] c_rules = { 
        "toxtree.plugins.func.rules.RuleAlertsForFunc", // Rule  1   
        
        "toxtree.plugins.func.rules.FG1", //2"
        "toxtree.plugins.func.rules.FG2", //3        
        "toxtree.plugins.func.rules.FG3_LS", //4
        "toxtree.plugins.func.rules.FG3_1", //5
        "toxtree.plugins.func.rules.FG3_2", //6        
        "toxtree.plugins.func.rules.FG4_LS", //7
        "toxtree.plugins.func.rules.FG4_1", //8
        "toxtree.plugins.func.rules.FG4_2", //9        
        "toxtree.plugins.func.rules.FG5", //10
        "toxtree.plugins.func.rules.FG6", //11
        
        "toxtree.plugins.func.rules.FG7", //12
        "toxtree.plugins.func.rules.FG8", //13
        "toxtree.plugins.func.rules.FG9", //14
        "toxtree.plugins.func.rules.FG10", //15
        "toxtree.plugins.func.rules.FG11", //16
        "toxtree.plugins.func.rules.FG12", //17
        "toxtree.plugins.func.rules.FG13", //18
        "toxtree.plugins.func.rules.FG14", //19
        "toxtree.plugins.func.rules.FG15", //20
        "toxtree.plugins.func.rules.FG16", //21
        
        "toxtree.plugins.func.rules.FG17", //22
        "toxtree.plugins.func.rules.FG18", //23
        "toxtree.plugins.func.rules.FG19", //24
        "toxtree.plugins.func.rules.FG20", //25
        "toxtree.plugins.func.rules.FG21", //26
        "toxtree.plugins.func.rules.FG22", //27
        "toxtree.plugins.func.rules.FG23_LS", //28
        "toxtree.plugins.func.rules.FG23_1", //29
        "toxtree.plugins.func.rules.FG23_2", //30
        "toxtree.plugins.func.rules.FG23_3", //31
        
        "toxtree.plugins.func.rules.FG23_4", //32
        "toxtree.plugins.func.rules.FG23_5", //33
        "toxtree.plugins.func.rules.FG23_6", //34
        "toxtree.plugins.func.rules.FG23_7", //35
        "toxtree.plugins.func.rules.FG23_8", //36
        "toxtree.plugins.func.rules.FG24_LS", //37
        "toxtree.plugins.func.rules.FG24_1", //38
        "toxtree.plugins.func.rules.FG24_2", //39
        "toxtree.plugins.func.rules.FG24_3", //40     
        "toxtree.plugins.func.rules.FG25", //41
        
        "toxtree.plugins.func.rules.FG26", //42
        "toxtree.plugins.func.rules.FG27", //43
        "toxtree.plugins.func.rules.FG28", //44
        "toxtree.plugins.func.rules.FG29", //45
        "toxtree.plugins.func.rules.FG30", //46        
       "toxtree.plugins.func.rules.FG31_LS", //47
       "toxtree.plugins.func.rules.FG31_1", //48
       "toxtree.plugins.func.rules.FG31_2", //49
       "toxtree.plugins.func.rules.FG31_3", //50
       "toxtree.plugins.func.rules.FG31_4", //51
       
       "toxtree.plugins.func.rules.FG31_5", //52
       "toxtree.plugins.func.rules.FG31_6", //53
       "toxtree.plugins.func.rules.FG31_7", //54
       "toxtree.plugins.func.rules.FG31_8", //55
       "toxtree.plugins.func.rules.FG31_9", //56        
       "toxtree.plugins.func.rules.FG32", //57        
       "toxtree.plugins.func.rules.FG33_LS", //58
       "toxtree.plugins.func.rules.FG33_1", //59
       "toxtree.plugins.func.rules.FG33_2", //60
       "toxtree.plugins.func.rules.FG33_3", //61
       
       "toxtree.plugins.func.rules.FG33_4", //62
       "toxtree.plugins.func.rules.FG33_5", //63
       "toxtree.plugins.func.rules.FG33_6", //64
       "toxtree.plugins.func.rules.FG33_7", //65
       "toxtree.plugins.func.rules.FG33_8", //66     
       "toxtree.plugins.func.rules.FG34_LS", //67
       "toxtree.plugins.func.rules.FG34_1", //68
       "toxtree.plugins.func.rules.FG34_2", //69      
        "toxtree.plugins.func.rules.FG35_LS", //70
        "toxtree.plugins.func.rules.FG35_1", //71
        
        "toxtree.plugins.func.rules.FG35_2", //72
        "toxtree.plugins.func.rules.FG35_3", //73
        "toxtree.plugins.func.rules.FG35_4", //74
        "toxtree.plugins.func.rules.FG35_5", //75
        "toxtree.plugins.func.rules.FG35_6", //76
        "toxtree.plugins.func.rules.FG35_7", //77
        "toxtree.plugins.func.rules.FG35_8", //78
        "toxtree.plugins.func.rules.FG35_9", //79
        "toxtree.plugins.func.rules.FG35_10", //80
        "toxtree.plugins.func.rules.FG35_11", //81
        
        "toxtree.plugins.func.rules.FG35_12", //82
        "toxtree.plugins.func.rules.FG35_13", //83   
        "toxtree.plugins.func.rules.FG36", //84   
        "toxtree.plugins.func.rules.FG37_LS", //85
        "toxtree.plugins.func.rules.FG37_1", //86
        "toxtree.plugins.func.rules.FG37_2", //87
        "toxtree.plugins.func.rules.FG37_3", //88
        "toxtree.plugins.func.rules.FG37_4", //89    
        "toxtree.plugins.func.rules.FG38", //90
        "toxtree.plugins.func.rules.FG39", //91
        
        "toxtree.plugins.func.rules.FG40", //92    
        "toxtree.plugins.func.rules.FG41_LS", //93
        "toxtree.plugins.func.rules.FG41_1", //94
        "toxtree.plugins.func.rules.FG41_2", //95
        "toxtree.plugins.func.rules.FG41_3", //96
        "toxtree.plugins.func.rules.FG41_4", //97
        "toxtree.plugins.func.rules.FG41_5", //98        
        "toxtree.plugins.func.rules.FG42", //99
        "toxtree.plugins.func.rules.FG43", //100
        "toxtree.plugins.func.rules.FG44", //101
        
        "toxtree.plugins.func.rules.FG45", //102    
        "toxtree.plugins.func.rules.FG46_LS", //103
        "toxtree.plugins.func.rules.FG46_1", //104
        "toxtree.plugins.func.rules.FG46_2", //105       
        "toxtree.plugins.func.rules.FG47", //106        
        "toxtree.plugins.func.rules.FG48_LS", //107
        "toxtree.plugins.func.rules.FG48_1", //108
        "toxtree.plugins.func.rules.FG48_2", //109        
        "toxtree.plugins.func.rules.FG49", //110        
        "toxtree.plugins.func.rules.FG50_LS", //111
        
        "toxtree.plugins.func.rules.FG50_1", //112
        "toxtree.plugins.func.rules.FG50_2", //113
        "toxtree.plugins.func.rules.FG50_3", //114   
        "toxtree.plugins.func.rules.FG51_LS", //115
        "toxtree.plugins.func.rules.FG51_1", //116
        "toxtree.plugins.func.rules.FG51_2", //117
        "toxtree.plugins.func.rules.FG51_3", //118        
        "toxtree.plugins.func.rules.FG52_LS", //119
        "toxtree.plugins.func.rules.FG52_1", //120
        "toxtree.plugins.func.rules.FG52_2", //121
        
        "toxtree.plugins.func.rules.FG52_3", //122        
        "toxtree.plugins.func.rules.FG53_LS", //123
        "toxtree.plugins.func.rules.FG53_1", //124
        "toxtree.plugins.func.rules.FG53_2", //125
        "toxtree.plugins.func.rules.FG53_3", //126    
        "toxtree.plugins.func.rules.FG54", //127
        "toxtree.plugins.func.rules.FG55", //128
        "toxtree.plugins.func.rules.FG56", //129
        "toxtree.plugins.func.rules.FG57", //130
        "toxtree.plugins.func.rules.FG58", //131
        
        "toxtree.plugins.func.rules.FG59", //132
        "toxtree.plugins.func.rules.FG60", //133
        "toxtree.plugins.func.rules.FG61", //134
        "toxtree.plugins.func.rules.FG62", //135
        "toxtree.plugins.func.rules.FG63", //136
        "toxtree.plugins.func.rules.FG64", //137
        "toxtree.plugins.func.rules.FG65", //138
        "toxtree.plugins.func.rules.FG66", //139
        "toxtree.plugins.func.rules.FG67", //140
        "toxtree.plugins.func.rules.FG68", //141
        
        "toxtree.plugins.func.rules.FG69", //142
        "toxtree.plugins.func.rules.FG70", //143
        "toxtree.plugins.func.rules.FG71", //144
        "toxtree.plugins.func.rules.FG72", //145
        "toxtree.plugins.func.rules.FG73", //146       
        "toxtree.plugins.func.rules.FG74_LS", //147
        "toxtree.plugins.func.rules.FG74_1", //148
        "toxtree.plugins.func.rules.FG74_2", //149
        "toxtree.plugins.func.rules.FG74_3", //150
        "toxtree.plugins.func.rules.FG74_4", //151
        
        "toxtree.plugins.func.rules.FG74_5", //152
        "toxtree.plugins.func.rules.FG74_6", //153
        "toxtree.plugins.func.rules.FG74_7", //154      
        "toxtree.plugins.func.rules.FG75_LS", //155
        "toxtree.plugins.func.rules.FG75_1", //156
        "toxtree.plugins.func.rules.FG75_2", //157
        "toxtree.plugins.func.rules.FG75_3", //158
        "toxtree.plugins.func.rules.FG75_4", //159     
        "toxtree.plugins.func.rules.FG76", //160
        "toxtree.plugins.func.rules.FG77", //161
        
        "toxtree.plugins.func.rules.FG78_LS", //162
        "toxtree.plugins.func.rules.FG78_1", //163
        "toxtree.plugins.func.rules.FG78_2", //164
        "toxtree.plugins.func.rules.FG78_3", //165
        "toxtree.plugins.func.rules.FG78_4", //166        
        "toxtree.plugins.func.rules.FG79_LS", //167
        "toxtree.plugins.func.rules.FG79_1", //168
        "toxtree.plugins.func.rules.FG79_2", //169
        "toxtree.plugins.func.rules.FG79_3", //170
        "toxtree.plugins.func.rules.FG79_4", //171
        
        "toxtree.plugins.func.rules.FG80_LS", //172
        "toxtree.plugins.func.rules.FG80_1", //173
        "toxtree.plugins.func.rules.FG80_2", //174   
        "toxtree.plugins.func.rules.FG81_LS", //175
        "toxtree.plugins.func.rules.FG81_1", //176
        "toxtree.plugins.func.rules.FG81_2", //177
        "toxtree.plugins.func.rules.FG81_3", //178
        "toxtree.plugins.func.rules.FG81_4", //179       
        "toxtree.plugins.func.rules.FG82_LS", //180
        "toxtree.plugins.func.rules.FG82_1", //181
        
        "toxtree.plugins.func.rules.FG82_2", //182
        "toxtree.plugins.func.rules.FG82_3", //183
        "toxtree.plugins.func.rules.FG82_4", //184        
        "toxtree.plugins.func.rules.FG83_LS", //185
        "toxtree.plugins.func.rules.FG83_1", //186
        "toxtree.plugins.func.rules.FG83_2", //187
        "toxtree.plugins.func.rules.FG83_3", //188
        "toxtree.plugins.func.rules.FG83_4", //189        
        "toxtree.plugins.func.rules.FG84_LS", //190
        "toxtree.plugins.func.rules.FG84_1", //191
        
        "toxtree.plugins.func.rules.FG84_2", //192      
        "toxtree.plugins.func.rules.FG85", //193
        "toxtree.plugins.func.rules.FG86", //194
        "toxtree.plugins.func.rules.FG87", //195
        "toxtree.plugins.func.rules.FG88", //196
        "toxtree.plugins.func.rules.FG89", //197
        "toxtree.plugins.func.rules.FG90", //198
  
        
        
        "toxTree.tree.rules.RuleVerifyAlertsCounter", //last
      
        };
    private final static transient int c_transitions[][] ={
        //{if no go to, if yes go to, assign if no, assign if yes}
        {2,2,0,0}, //Rule 1  1
        {3,3,0,0}, //2
        {4,4,0,0}, //3
        
        
        {5,5,0,0}, //4
        {6,6,0,0}, //5
        {7,7,0,0}, //6
        
        {8,8,0,0}, //7
        {9,9,0,0}, //8
        {10,10,0,0}, //9
        
        {11,11,0,0}, //10
        {12,12,0,0}, //11
        {13,13,0,0}, //12
        {14,14,0,0}, //13
        {15,15,0,0}, //14
        {16,16,0,0}, //15
        {17,17,0,0}, //16
        {18,18,0,0}, //17
        {19,19,0,0}, //18
        {20,20,0,0}, //19
        {21,21,0,0}, //20
        {22,22,0,0}, //21
        {23,23,0,0}, //22
        {24,24,0,0}, //23
        {25,25,0,0}, //24
        {26,26,0,0}, //25
        {27,27,0,0}, //26
        {28,28,0,0}, //27
        
        {29,29,0,0}, //28
        {30,30,0,0}, //29
        {31,31,0,0}, //30
        {32,32,0,0}, //31
        {33,33,0,0}, //32
        {34,34,0,0}, //33
        {35,35,0,0}, //34
        {36,36,0,0}, //35
        {37,37,0,0}, //36
        
        {38,38,0,0}, //37
        {39,39,0,0}, //38
        {40,40,0,0}, //39
        {41,41,0,0}, //40
        
        {42,42,0,0}, //41
        {43,43,0,0}, //42
        {44,44,0,0}, //43
        {45,45,0,0}, //44
        {46,46,0,0}, //45
        {47,47,0,0}, //46
        
        {48,48,0,0}, //47
        {49,49,0,0}, //48
        {50,50,0,0}, //49        
        {51,51,0,0}, //50
        {52,52,0,0}, //51
        {53,53,0,0}, //52
        {54,54,0,0}, //53
        {55,55,0,0}, //54
        {56,56,0,0}, //55
        {57,57,0,0}, //56
        
        {58,58,0,0}, //57
        
        {59,59,0,0}, //58
        {60,60,0,0}, //59        
        {61,61,0,0}, //60
        
        
        {62,62,0,0}, //61
        {63,63,0,0}, //62
        {64,64,0,0}, //63
        {65,65,0,0}, //64
        {66,66,0,0}, //65
        {67,67,0,0}, //66
        
        {68,68,0,0}, //67
        {69,69,0,0}, //68
        {70,70,0,0}, //69
        
        
        {71,71,0,0}, //70
        {72,72,0,0}, //71
        {73,73,0,0}, //72
        {74,74,0,0}, //73
        {75,75,0,0}, //74
        {76,76,0,0}, //75
        {77,77,0,0}, //76
        {78,78,0,0}, //77
        {79,79,0,0}, //78
        {80,80,0,0}, //79        
        {81,81,0,0}, //80
        {82,82,0,0}, //81
        {83,83,0,0}, //82
        {84,84,0,0}, //83
        
        {85,85,0,0}, //84 
        
        {86,86,0,0}, //85
        {87,87,0,0}, //86
        {88,88,0,0}, //87
        {89,89,0,0}, //88
        {90,90,0,0}, //89   
        
        {91,91,0,0}, //90
        {92,92,0,0}, //91
        {93,93,0,0}, //92
        
        {94,94,0,0}, //93
        {95,95,0,0}, //94
        {96,96,0,0}, //95
        {97,97,0,0}, //96
        {98,98,0,0}, //97
        {99,99,0,0}, //98
        
        {100,100,0,0}, //99        
        {101,101,0,0}, //100
        {102,102,0,0}, //101
        {103,103,0,0}, //102
        
        
        {104,104,0,0}, //103
        {105,105,0,0}, //104
        {106,106,0,0}, //105
        
        {107,107,0,0}, //106
        
        {108,108,0,0}, //107
        {109,109,0,0}, //108
        {110,110,0,0}, //109 
        
        {111,111,0,0}, //110
        
        {112,112,0,0}, //111
        {113,113,0,0}, //112
        {114,114,0,0}, //113
        {115,115,0,0}, //114
        
        {116,116,0,0}, //115
        {117,117,0,0}, //116
        {118,118,0,0}, //117
        {119,119,0,0}, //118
        
        {120,120,0,0}, //119        
        {121,121,0,0}, //120
        {122,122,0,0}, //121
        {123,123,0,0}, //122
        
        {124,124,0,0}, //123
        {125,125,0,0}, //124
        {126,126,0,0}, //125
        {127,127,0,0}, //126
        
        
        {128,128,0,0}, //127
        {129,129,0,0}, //128
        {130,130,0,0}, //129       
        {131,131,0,0}, //130
        {132,132,0,0}, //131
        {133,133,0,0}, //132
        {134,134,0,0}, //133
        {135,135,0,0}, //134
        {136,136,0,0}, //135
        {137,137,0,0}, //136
        {138,138,0,0}, //137
        {139,139,0,0}, //138
        {140,140,0,0}, //139       
        {141,141,0,0}, //140
        {142,142,0,0}, //141
        {143,143,0,0}, //142
        {144,144,0,0}, //143
        {145,145,0,0}, //144
        {146,146,0,0}, //145
        {147,147,0,0}, //146
        
        
        
        {149,149,0,0}, //147
        {149,149,0,0}, //149
        {150,150,0,0}, //149       
        {151,151,0,0}, //150
        {152,152,0,0}, //151
        {153,153,0,0}, //152
        {154,154,0,0}, //153
        {155,155,0,0}, //154
        
        
        {156,156,0,0}, //155
        {157,157,0,0}, //156
        {158,158,0,0}, //157
        {159,159,0,0}, //158
        {160,160,0,0}, //159
        
        {161,161,0,0}, //160
        {162,162,0,0}, //161
        
        {163,163,0,0}, //162
        {164,164,0,0}, //163
        {165,165,0,0}, //164
        {166,166,0,0}, //165
        {167,167,0,0}, //166
        
        {168,168,0,0}, //167
        {169,169,0,0}, //168
        {170,170,0,0}, //169        
        {171,171,0,0}, //170
        {172,172,0,0}, //171
        
        {173,173,0,0}, //172
        {174,174,0,0}, //173
        {175,175,0,0}, //174
        
        {176,176,0,0}, //175
        {177,177,0,0}, //176
        {178,178,0,0}, //177
        {179,179,0,0}, //178
        {180,180,0,0}, //179 
        
        {181,181,0,0}, //180
        {182,182,0,0}, //181
        {183,183,0,0}, //182
        {184,184,0,0}, //183
        {185,185,0,0}, //184
        
        {186,186,0,0}, //185
        {187,187,0,0}, //186
        {188,188,0,0}, //187
        {189,189,0,0}, //188
        {190,190,0,0}, //189  
        
        {191,191,0,0}, //190
        {192,192,0,0}, //191
        {193,193,0,0}, //192
        
        {194,194,0,0}, //193
        {195,195,0,0}, //194
        {196,196,0,0}, //195
        {197,197,0,0}, //196
        {198,198,0,0}, //197
        {199,199,0,0}, //198
        
        
        {0,0,2,1}, //last
       
        
    };	
    private final static transient String c_categories[] ={
		"toxtree.plugins.func.categories.FuncCat1",
		"toxtree.plugins.func.categories.FuncCat2",
		
	};
	/**
	 * 
	 */
	public FuncRules() throws DecisionMethodException {
		super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
     ;
		setChanged();
		notifyObservers();
		setTitle("Structure Alerts for the functional groups identification");
		setExplanation(
				"Func_Groups-ISS plug-in allows the identification of functional groups." 
				);
        setPriority(20);
        setFalseIfRuleNotImplemented(false);};
        
    	public DescriptorSpecification getSpecification() {
            return new DescriptorSpecification(
                    "http://toxtree.sourceforge.net/func.html",
                    getTitle(),
                    this.getClass().getName(),                
                    "Toxtree plugin");
    	}

	/* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
        public void addPropertyChangeListener(PropertyChangeListener l) {
            if (changes == null) 		changes = new PropertyChangeSupport(this);        
            changes.addPropertyChangeListener(l);
    		for (int i=0; i < rules.size(); i++) 
    			if (rules.getRule(i) != null)
    				rules.getRule(i).addPropertyChangeListener(l);
        }
        /* (non-Javadoc)
         * @see toxTree.core.IDecisionMethod#removePropertyChangeListener(java.beans.PropertyChangeListener)
         */
        public void removePropertyChangeListener(PropertyChangeListener l) {
            if (changes == null) {
    	        changes.removePropertyChangeListener(l);
    	        for (int i=0; i < rules.size(); i++) 
    	        	if (rules.getRule(i) != null)
    	        		rules.getRule(i).removePropertyChangeListener(l);
            }	
        }	
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return getName();
        }
    	


    	/* (non-Javadoc)
         * @see toxTree.core.IDecisionMethod#getName()
         */
        public String getName() {
            return name;
        }
        /* (non-Javadoc)
         * @see toxTree.core.IDecisionMethod#setName(java.lang.String)
         */
        public void setName(String value) {
           name = value;

        }
    	public StringBuffer explainRules(IDecisionResult result,boolean verbose) throws DecisionMethodException{
    		try {
    			StringBuffer b = result.explain(verbose);
    			return b;
    		} catch (DecisionResultException x) {
    			throw new DecisionMethodException(x);
    		}
    	}


    	@Override
    	public IDecisionResult createDecisionResult() {
    		IDecisionResult result =  new FuncTreeResult();
    		result.setDecisionMethod(this);
    		return result;
    	}

    	public boolean isResiduesIDVisible() {
    		return residuesIDVisible;
    	}


    	public void setResiduesIDVisible(boolean residuesIDVisible) {
    		this.residuesIDVisible = residuesIDVisible;
    		for (int i=0;i< rules.size(); i++) {
    			rules.getRule(i).hideResiduesID(!residuesIDVisible);
    		}
    	}
    	public void setEditable(boolean value) {
    		editable = value;
    		for (int i=0;i<rules.size();i++)
    			rules.getRule(i).setEditable(value);
    	}


        @Override
        public void setParameters(IAtomContainer mol) {
            if (getInteractive()) {
                JComponent c = optionsPanel(mol);
                if (c != null)
                    JOptionPane.showMessageDialog(null,c,getTitle(),JOptionPane.PLAIN_MESSAGE);
            } 
        }
    }


