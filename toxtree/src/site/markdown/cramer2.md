#Toxtree Module: Cramer rules with extensions

Available since [ToxTree 1.60](./download.html#Toxtree-v1.60)

  Cramer  rules with  extensions: This  plug-in is  a copy  of the original plug-in, plus minor extensions. 
  Like the Cramer plug-in, this plug-in works by assigning compounds to Class I, II, or III, 
  according to the rules  from Cramer, and some extra ones. 
  Several compounds were classified by Munro  in 1996 as Class I or Class  II compounds according to the Cramer  rules, 
  even though Munro reported low NOEL values upon oral administration  (indicating relatively high toxicity). 
  To overcome such misclassifications, five  rules have been introduced to capture the possible toxicity  of  these  compounds;
  
  
Screenshots: [Main screen](./images/cramer2/screen.jpg) , [Decision tree](./images/cramer2/tree.jpg)

![thumb](images/cramer2/thumb.jpg)

- Comparison with [Cramer rules](./cramer.html)

  This module is extending the original Cramer scheme with additional 5 questions (Q40-44) addressing functional groups such as phosphates, benzene-like substances, divalent sulphurs and unsaturated heteroatom moieties.
   
**References**

- [Cramer GM, Ford RA, Hall RL (1978) Estimation of Toxic Hazard - A Decision Tree Approach. J. Cosmet. Toxicol. 16, 255-276.](https://www.sciencedirect.com/science/article/pii/S0015626476805226)

- [Patlewicz G, Jeliazkova N, Safford RJ, Worth AP, Aleksiev B. (2008) An evaluation of the implementation of the Cramer classification scheme in the Toxtree software. SAR QSAR Environ Res. ;19(5-6):495-524.](https://www.ncbi.nlm.nih.gov/pubmed/18853299)
 
- [I.C. Munro, R.A. Ford, E. Kennepohl, and J.G. Sprenger, Correlation of structural class with No-Observed-Effect Levels: A proposal for establishing a threshold of concern, Food Chem. Toxicol. 34 (1996), pp. 829-867.](https://www.ncbi.nlm.nih.gov/pubmed/8972878)

- [User manual](https://eurl-ecvam.jrc.ec.europa.eu/laboratories-research/predictive_toxicology/doc/Toxtree_Cramer_extensions.pdf)
 
- [Lapenna S & Worth A (2011), Analysis of the Cramer classification scheme for oral systemic toxicity - implications for its implementation in Toxtree. EUR 24898 EN. Publications Office of the European Union, Luxembourg](https://ec.europa.eu/jrc/en/publication/eur-scientific-and-technical-research-reports/analysis-cramer-classification-scheme-oral-systemic-toxicity-implications-its-implementation)
 
**For developers**

- [Source](https://sourceforge.net/p/toxtree/git/ci/master/tree/toxtree/toxtree-plugins/toxtree-cramer2) 

- [Maven artifact](http://maven.apache.org/) 
   		
```xml 		
<dependency>
  <groupId>toxtree</groupId>
  <artifactId>toxtree-cramer2<artifactId>
  <version>3.1.0</version>
</dependency>
```

- Repository

```xml
   <repository>
        <id>ambit-releases</id>
        <url>https://nexus.ideaconsult.net/content/repositories/releases</url>
   </repository>
``` 
