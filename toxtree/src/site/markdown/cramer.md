#Toxtree Module: Cramer rules

Cramer rules (Cramer G. M., R.  A. Ford, R. L. Hall, Estimation of  Toxic Hazard - A  Decision Tree Approach,  J. Cosmet. Toxicol.,  Vol.16,  pp. 255-276, Pergamon  Press, 1978);
    
Developed by [IdeaConsult Ltd. Sofia, Bulgaria](https://www.ideaconsult.net) for the [EC Joint Research Centre, Computational Toxicology](https://ec.europa.eu/jrc/en/scientific-tool/toxtree-tool)
  
Screenshots: [Main screen](./images/cramer/screen.jpg) , [Decision tree](./images/cramer/tree.jpg)

![thumb](images/cramer/thumb.jpg)

- Applicability of Cramer rules Toxtree module
 
 The Toxtree implementation of the Cramer scheme can, in principle, be applied to organic molecules, organic salts and organometallics, and
 structurally well-defined oligomers and polymers.
 
 With regards to oligomers and polymers: Despite there is no technical limitation in Toxtree in running oligomers and polymers
 (assuming their structures are well defined), these substances are generally considered out of scope of the Cramer scheme since they were not present in
 the dataset from which the scheme was derived [Cramer, 1978], with the exception of substances containing a polyoxyethylene [(-
 OCH2CH2-)x, with x no greater than 4] chain (cf. questions 20 and 32).
   
**Reference**

- [Cramer GM, Ford RA, Hall RL (1978) Estimation of Toxic Hazard - A Decision Tree Approach. J. Cosmet. Toxicol. 16, 255-276.](https://www.sciencedirect.com/science/article/pii/S0015626476805226)

- [Patlewicz G, Jeliazkova N, Safford RJ, Worth AP, Aleksiev B. (2008) An evaluation of the implementation of the Cramer classification scheme in the Toxtree software. SAR QSAR Environ Res. ;19(5-6):495-524.](https://www.ncbi.nlm.nih.gov/pubmed/18853299)
 
- [Lapenna S & Worth A (2011), Analysis of the Cramer classification scheme for oral systemic toxicity - implications for its implementation in Toxtree. EUR 24898 EN. Publications Office of the European Union, Luxembourg](https://ec.europa.eu/jrc/en/publication/eur-scientific-and-technical-research-reports/analysis-cramer-classification-scheme-oral-systemic-toxicity-implications-its-implementation)
 
**For developers**
 
- [Source](https://sourceforge.net/p/toxtree/git/ci/master/tree/toxtree/toxtree-plugins/toxtree-cramer) 

- [Maven artifact](http://maven.apache.org/) 
   		
```xml 		
<dependency>
  <groupId>toxtree</groupId>
  <artifactId>toxtree-cramer</artifactId>
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
