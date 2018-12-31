#Benigni/ Bossa rulebase (for mutagenicity and cancerogenicity)

 A decision tree for estimating carcinogenicity and mutagenicity by discriminant analysis and structural rules, based on published in  the document [Benigni 2008](https://ec.europa.eu/jrc/en/publication/eur-scientific-and-technical-research-reports/benigni-bossa-rulebase-mutagenicity-and-carcinogenicity-module-toxtree)
 
Available since [ToxTree 1.3.0](./download.html#Toxtree-v1.3.0)
  
The structure alerts have been updated since [ToxTree 2.6.0](./download.html#Toxtree-v2.6.0) according to [Benigni 2013](http://pubs.acs.org/doi/abs/10.1021/cr300206t).
  
Developed by [IdeaConsult Ltd. Sofia, Bulgaria](https://www.ideaconsult.net) and [Istituto Superiore di Sanita](http://www.iss.it) for the [EC Joint Research Centre, Computational Toxicology](https://ec.europa.eu/jrc/en/scientific-tool/toxtree-tool)  
  
Screenshots: [Main screen](./images/mutant/screen.jpg) , [Decision tree](./images/mutant/tree.jpg)

![thumb](images/mutant/thumb.jpg)

  
**References**

- [Benigni R., C. Bossa, T. Netzeva, A. Rodomonte, and I. Tsakovska (2007) Mechanistic QSAR of aromatic amines: new models for discriminating between mutagens and nonmutagens, and validation of models for carcinogens. Environ mol mutag 48:754-771](https://www.ncbi.nlm.nih.gov/pubmed/18008355) 

- [Benigni R.,  C. Bossa, N. Jeliazkova, T. Netzeva, and A. Worth. The Benigni / Bossa rulebase for mutagenicity and carcinogenicity - a module of Toxtree. European Commission report EUR 23241](https://ec.europa.eu/jrc/en/publication/eur-scientific-and-technical-research-reports/benigni-bossa-rulebase-mutagenicity-and-carcinogenicity-module-toxtree)

- [Benigni, R., Bossa, C., & Tcheremenskaia, O. (2013). Nongenotoxic carcinogenicity of chemicals: mechanisms of action and early recognition through a new set of structural alerts. Chemical Reviews, 113(5), 2940-57](http://pubs.acs.org/doi/abs/10.1021/cr300206t)
 
 
**For developers**
 
- [Source](https://sourceforge.net/p/toxtree/git/ci/master/tree/toxtree/toxtree-plugins/toxtree-mutant) 

- [Maven artifact](http://maven.apache.org/) 
   		
```xml 		
<dependency>
  <groupId>toxtree</groupId>
  <artifactId>toxtree-mutant</artifactId>
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
