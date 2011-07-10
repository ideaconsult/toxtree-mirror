/* SMARTSgroups.java
 * Author: Nina Jeliazkova
 * Date: Jan 6, 2008 
 * Revision: 0.1 
 * 
 * Copyright (C) 2005-2006  Nina Jeliazkova
 * 
 * Contact: nina@acad.bg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 */

package toxtree.plugins.func.rules;


public class SMARTSgroups {
    public static String NITRO_CHARGED = "[N+](=O)[O-]";
    public static String NITRO_UNCHARGED = "N(=O)(=O)";
    public static String AROMATIC_AMINE = "[NX3H2]";
    
/*
    {"Nitro aromatic","c[N+](=O)[O-]","O=[N+]([O-])C=1C=CC=C(C=1)Cl","NA27",new Boolean(false)},
    {"Primary aromatic amine","c[N]([#1,C])([#1,C])","CNC=1C([H])=C([H])C([H])=C(Cl)C=1([H])","NA28",new Boolean(false)},
    {"Hydroxyl amine","cN([OX2H])([#1,C])","[H]ONC1=CC=CC=C1Cl","NA28",new Boolean(false)},
    {"Hydroxyl amine ester","cN([#1,C])OC=O","CCCN(OC=O)C1=CC=CC=C1(Cl)","NA28",new Boolean(false)},
    {"Aromatic mono- and dialkylamine","c[NX3v3]([#1,CH3])([#1,CH3])","CNC=1C=CC=C(Cl)C=1","NA28bis",new Boolean(false)},        
    {"Aromatic mono- and dialkylamine","c[NX3v3]([#1,CH3])([CH2][CH3])","CCN(C)C=1C=CC=C(C=1)Cl","",new Boolean(false)},
    {"Aromatic mono- and dialkylamine","c[NX3v3]([CH2][CH3])([CH2][CH3])","CCN(CC)C=1C=CC=C(Cl)C=1","",new Boolean(false)},
    {"Aromatic N-acyl amine","cNC(=O)[#1,CH3]","CC(=O)NC=1C=CC=C(C=1)Cl","NA28ter",new Boolean(false)},
    {"Aromatic diazo","cN=[N]a","C1=CC=C(C=C1)N=NC=2C=CC=C(C=2)Cl","NA29",new Boolean(false)},
    {"Biphenyls","c!@c1ccccc1","C1=CC=C(C=C1)C2=CC=CC=C2Cl","",new Boolean(false)},
    {"Diphenyls","c!@*!@c1ccccc1","c1c(Cl)c(ccc1Cc2ccc(cc2))","",new Boolean(false)},
    {"Diphenyls","c!@*!@*!@c1ccccc1","c1c(Cl)c(ccc1CCc2ccc(cc2))","",new Boolean(false)}
*/    
    protected SMARTSgroups() {
        super();
    }
    
}
