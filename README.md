# SWFC-ART

For academic demonstration. Full description alongwith references will be provided soon.

## Credits

This project uses some code from following repositories:

KDFC-ART: https://github.com/maochy/kdfc-art

HNSW-Java: https://github.com/jelmerk/hnswlib

## Guidelines

To reproduce the results, Please refer to:
hnswlib-examples => hnswlib-examples-java => src => main => java => com.github.jelmerk.knn.examples => test.model.

Make sure to satisfy all maven dependencies for proper execution of the project.

This is maven project and we recommend to use IntelliJ IDEA for smooth experience.

## Debugging
If you face import errors or cant run the files:
1. Set the source directories, i.e., `hnswlib-examples > hnswlib-examples-java > src > main > java` ([navigate](https://github.com/ashfaq92/swfc-art/tree/main/hnswlib-utils/src/main/java)) and  `hnswlib-core > src > main > java` ([navigate](https://github.com/ashfaq92/swfc-art/tree/main/hnswlib-core/src/main/java)) by selecting the directory in the Project window, right clicking and selecting `Mark Directory As > Sources Root`, as shown in following images:
![marking hnswlib-core directory as sources root](https://github.com/ashfaq92/swfc-art/blob/main/source-root-hnswlib-core.jpg "Hnswlib Core")
![marking hnswlib-examples directory as sources root](https://github.com/ashfaq92/swfc-art/blob/main/source-root-hnswlib-examples.jpg "Hnswlib Examples")
For details, please check this [link](https://stackoverflow.com/questions/33531334/convert-directories-with-java-files-to-java-modules-in-intellij). 
2. Enable [auto import](https://blog.jetbrains.com/idea/2020/01/intellij-idea-2020-1-eap/#maven_and_gradle_importing_updates) maven plugins in IntellijIDEA.
3. Make sure your maven imports are correct. 
4. Use latest version of [IntelliJ IDEA](https://download.jetbrains.com/idea/ideaIC-2020.3.exe?_ga=2.141047602.1216986372.1608194251-233142243.1608194250)

