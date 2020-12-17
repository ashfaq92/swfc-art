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

## TroubleShooting

`1.` Facing import error: 

`Solution:` Set the source directories, i.e., `hnswlib-examples > hnswlib-examples-java > src > main > java` ([navigate](https://github.com/ashfaq92/swfc-art/tree/main/hnswlib-utils/src/main/java)) and  `hnswlib-core > src > main > java` ([navigate](https://github.com/ashfaq92/swfc-art/tree/main/hnswlib-core/src/main/java)) by selecting the directory in the Project window, right clicking and selecting `Mark Directory As > Sources Root`, as shown in following images: ([details](https://stackoverflow.com/questions/33531334/convert-directories-with-java-files-to-java-modules-in-intellij) )
![marking hnswlib-core directory as sources root](https://github.com/ashfaq92/swfc-art/blob/main/source-root-hnswlib-core.jpg "Hnswlib Core")
![marking hnswlib-examples directory as sources root](https://github.com/ashfaq92/swfc-art/blob/main/source-root-hnswlib-examples.jpg "Hnswlib Examples")

`2.` Cant resolve maven dependencies:

`Solution:` [Clean od maven dependencies](https://stackoverflow.com/a/19312292)

3. `Exception in thread "main" java.io.IOException: The system cannot find the path specified`

`Solution:` By default, test results are stored in `E:/temp/` directory. Please create `test` folder in `E` drive. OR Specify custom path for storing results [here] (https://github.com/ashfaq92/swfc-art/blob/5b1d40cc123743d8ad8759808f5ed7d90dedb183/hnswlib-examples/hnswlib-examples-java/src/main/java/com/github/jelmerk/knn/examples/test/model/TestEffectiveness.java#L62) and [here](https://github.com/ashfaq92/swfc-art/blob/5b1d40cc123743d8ad8759808f5ed7d90dedb183/hnswlib-examples/hnswlib-examples-java/src/main/java/com/github/jelmerk/knn/examples/test/model/TestEfficiency.java#L55)

`4.` Enable [auto import](https://blog.jetbrains.com/idea/2020/01/intellij-idea-2020-1-eap/#maven_and_gradle_importing_updates) maven plugins in IntellijIDEA.

`5.` Make sure your maven imports are correct. 

`6.` Use latest version of [IntelliJ IDEA](https://download.jetbrains.com/idea/ideaIC-2020.3.exe?_ga=2.141047602.1216986372.1608194251-233142243.1608194250)

If you are still facing problem in project configuration, feel free to [open an issue](https://github.com/ashfaq92/swfc-art/issues), we shall try our best to resolve it.
