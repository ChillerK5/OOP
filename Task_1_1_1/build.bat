mkdir .\bin\classes
mkdir .\bin\docs
mkdir .\bin\jar\
javac -d .\bin\classes .\src\main\java\ru\nsu\kbagryantsev\Heapsort.java
javadoc -d .\bin\doc\ -charset utf-8 -sourcepath .\src\main\java\ -subpackages ru.nsu.kbagryantsev
jar cf .\bin\jar\HeapSort.jar .\src\main\java\ru\nsu\kbagryantsev\Heapsort.java