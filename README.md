# SWFC-ART

For academic demonstration. Full description alongwith references will be provided soon.

## Credits

This project uses some code from following repositories:

KDFC-ART: https://github.com/maochy/kdfc-art

HNSW-Java: https://github.com/jelmerk/hnswlib

## Guidelines

To reproduce the results, Please refer to:
hnswlib-examples => hnswlib-examples-java => src => main => java => com.github.jelmerk.knn.examples => test.model 

Make sure to satisfy all maven dependencies for proper execution of the project

This is maven project and we recommend to use IntelliJ IDEA for smooth experience

## Debugging
If you face import errors or cant run the files:
1. Use latest version of [IntelliJ IDEA](https://download.jetbrains.com/idea/ideaIC-2020.3.exe?_ga=2.141047602.1216986372.1608194251-233142243.1608194250)
1. make sure your maven imports are correct. Enable auto import in IntellijIDEA
2. Set the source directories, i.e., `hnswlib-examples > hnswlib-examples-java > src > main > java` [navigate](https://github.com/ashfaq92/swfc-art/tree/main/hnswlib-utils/src/main/java) and  `hnswlib-core > src > main > java` [navigate](https://github.com/ashfaq92/swfc-art/tree/main/hnswlib-core/src/main/java) by selecting the directory in the Project window, right clicking and selecting `Mark Directory As > Sources Root`. [Details](https://stackoverflow.com/questions/33531334/convert-directories-with-java-files-to-java-modules-in-intellij)
