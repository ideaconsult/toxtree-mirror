# Toxtree Module: START biodegradation  and persistence plug-in

START (Structural  Alerts for Reactivity  in Toxtree) biodegradation  and persistence plug-in  is based  on a  compilation of  structural alerts  for environmental persistence and biodegradability. These structural alerts are molecular functional groups or substructures that are known to be linked to the  environmental  persistence  or  biodegradability  of  chemicals.   The rulebase utilizes the structural alerts  in logical decision trees. If  one or more the  structural alerts embedded  in the molecular  structure of the chemical  are recognized,  the system  flags the  potential persistence  or biodegradability of the chemical.
 
Available since [ToxTree 1.60](./download.html#Toxtree-v1.60)
 
Developed by [Molecular Networks GmbH](https://www.mn-am.com/) for the [EC Joint Research Centre, Computational Toxicology](https://ec.europa.eu/jrc/en/scientific-tool/toxtree-tool)
  
Screenshots: [Main screen](./images/start/screen.jpg) , [Decision tree](./images/start/tree.jpg)

![thumb](images/start/thumb.jpg)
  
 
**Reference**

- [START Installation and user manual, 2008](https://eurl-ecvam.jrc.ec.europa.eu/laboratories-research/predictive_toxicology/doc/Toxtree_start_manual.pdf)	
 
  		
**For developers**
 
- [Source](https://sourceforge.net/p/toxtree/git/ci/master/tree/toxtree/toxtree-plugins/toxtree-biodegradation) 

- [Maven artifact](http://maven.apache.org/) 
   		
```xml 		
<dependency>
  <groupId>toxtree</groupId>
  <artifactId>toxtree-biodegradation</artifactId>
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
