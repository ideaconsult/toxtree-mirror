# Toxtree Module: Verhaar scheme for predicting toxicity mode of action (modified)

 Verhaar scheme for predicting toxicity mode of action (modified). Reuses rules from [Verhaar scheme module](/verhaar.html), and is partially updated, according to recommendations in [Enoch 2008](https://www.sciencedirect.com/science/article/pii/S0045653508008369)
 
Available since [ToxTree 2.5.0](./download.html#Toxtree-v2.5.0)
 
Developed by [IdeaConsult Ltd. Sofia, Bulgaria](https://www.ideaconsult.net). 
  
Screenshots: [Main screen](./images/verhaar2/screen.jpg) , [Decision tree](./images/verhaar2/tree.jpg)

![thumb](images/verhaar2/thumb.jpg)
   
**References**

- [Verhaar HJM, van Leeuwen CJ and Hermens JLM (1992) Classifying environmental pollutants. 1. Structure-activity relationships for prediction of aquatic toxicity. Chemosphere 25, 471-491.](https://www.sciencedirect.com/science/article/pii/0045653592902805) 
 
- [Verhaar HJM, Solbe J, Speksnijder J, van Leeuwen CJ and Hermens JLM (2000) Classifying environmental pollutants: Part 3. External validation of the classification system. Chemosphere 40, 875-883](https://www.ncbi.nlm.nih.gov/pubmed/10718581) 

- [S.J. Enoch, M. Hewitt, M.T.D. Cronin, S. Azam, J.C. Madden, Classification of chemicals according to mechanism of aquatic toxicity: An evaluation of the implementation of the Verhaar scheme in Toxtree, Chemosphere 73 (2008) 243-248](https://www.sciencedirect.com/science/article/pii/S0045653508008369)
 

**For developers**
 
- [Source](https://sourceforge.net/p/toxtree/git/ci/master/tree/toxtree/toxtree-plugins/verhaar2) 

- [Maven artifact](http://maven.apache.org/) 
   		
```xml 		
<dependency>
  <groupId>toxtree</groupId>
  <artifactId>toxtree-verhaar2</artifactId>
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