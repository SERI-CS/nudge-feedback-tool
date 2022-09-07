---
layout: default
---

# SERICS Nudge-Feedback Tool Documentation

The SERICS Nudge-Feedback Tool is a program designed to provide additional feedback to students in introductory computer science courses when they encounter runtime errors in their programs.

In _Feedback.java_, the main method of the desired program is run with the arguments that are passed into _Feedback.java_, and in _FeedbackTestCase.java_, all test cases in the specified test file are run. Any runtime errors that occur during the execution of the desired program will be caught, and feedback will be printed into the terminal along with the standard error messages produced by Java.


* * *


## Setup
Begin with directory containing the program you are using the tool on, along with any additional files that may be needed to run the program that you want feedback on, such as .txt files, other Java classes, other .jar files, etc.

To get started with the tool, import the following files into the directory with your program:
* Error.java
* ErrorMap.json
* Feedback.java (for running main methods of classes)
* FeedbackTestCase.java (for running all test cases in a file)
* jackson-annotations-*.jar
* jackson-core-*.jar
* jackson-databind-*.jar


> **Note:** \\
> The jackson jars (_jackson-annotations-*jar_, _jackson-core-*.jar_, and _jackson-databind-*.jar_ can be any version, as long as they are all the same version, **i.e. _jackson-annotations-2.13.3.jar_, _jackson-core-2.13.3.jar_, and _jackson-databind-2.13.3.jar_**


Below is an example of the directory contents when using the file to test on **Homework 3: Caesar Cipher**:

```
// File that we want to receive feedback on
Caesar.java    

// Associated files needed to run Caesar.java
encrypted_message.txt
english.txt

// Files needed to run the feedback tool
Error.java
ErrorMap.json
Feedback.java
cis110.jar
jackson-annotations-2.13.3.jar
jackson-core-2.13.3.jar
jackson-databind-2.13.3.jar
junit-platform-console-standalone-1.3.2.jar
nio.jar
```

* * *

## Configure with Codio
To add the option of running a class or methods with feedback, we need to modify the _.codio_ file. This file is a .json file listing the run options and the command that will be executed when that option is selected. 

```json
{
    "commands": {
        "Compile": "javac -cp cis110.jar:jackson-annotations-2.13.3.jar:jackson-core-2.13.3.jar:jackson-databind-2.13.3.jar:. *.java",
        "Run Hello World": "java HelloWorld",
        "Run MySketch": "java MySketch",
        "Run MyHouse": "java MyHouse",
        "Run Feedback": "java -cp cis110.jar:jackson-annotations-2.13.3.jar:jackson-core-2.13.3.jar:jackson-databind-2.13.3.jar:. Feedback",
        "Autoformat Files": "python3 linter.py *.java"
    },
    "preview": {
        {% raw %}"View Running Program": "https://{{domain3000}}/"{% endraw %}
    }
}
```

To run files with feedback, the _“Compile”_ command needs to be modified to include all the jars we added. \\
\\
We also need to add a “Run Feedback” option similar to the other run options. \\
Once again we need to include all the listed jars, as _Feedback.java_ requires these jars to run. At the end, we write “Feedback” as that is the file we are running with this command. 

> **Note:** \\
> Each jar in each of the example commands is on the same line. This is required in order for the command to work properly.
>
> Also, the above example is for **Homework 0**, which **does not** have any student-written test cases. To run feedback on test cases, we need to also include **junit-platform-console-standalone-1.3.2.jar** in our list of jars for the _"Compile"_ and _"Run"_ commands, as junit is needed to run our tests:
```json
"Compile": "javac -cp cis110.jar:jackson-annotations-2.13.3.jar:jackson-core-2.13.3.jar:jackson-databind-2.13.3.jar:junit-platform-console-standalone-1.3.2.jar:. *.java"
"Run Feedback": "java -cp junit-platform-console-standalone-1.3.2.jar:cis110.jar:jackson-annotations-2.13.3.jar:jackson-core-2.13.3.jar:jackson-databind-2.13.3.jar:. Feedback"
```


* * *

## Configuration
To configure the tool to work with your current program, open _Feedback.java_ and in the run method, replace _“Test.main(arguments);”_ with the file name of your desired program (i.e. _Caesar_ for _Caesar.java_) followed by “_.main(arguments);_”.
  
```java
// Original Feedback.java
ExecutorService execute = Executors.newSingleThreadExecutor();
Future<?> future = executor.submit(() -> {
	try {
		Test.main(arguments);
	}

// Feedback.java configured to run Caesar.java
ExecutorService execute = Executors.newSingleThreadExecutor();
Future<?> future = executor.submit(() -> {
	try {
		Caesar.main(arguments);
	}
```

* * *

## Command Line Arguments
The command line arguments that are passed into _Feedback.java_ will be the command line arguments that are passed into your desired program.

```java
// Executes Caesar.java with crack encrypted_message.txt english.txt // as command line arguments
java Feedback crack encrypted_message.txt english.txt

args[] = ["crack", "encrypted_message.txt", "english.txt"]
```

* * *


## Configuration for Test Cases 
To run all the test cases in a file, run _FeebackTestCase.java_ instead of _Feedback.java_. \\
\\
In the try block, replace all instances of “CLASSNAME” in the example code with the name of the class where the tests are located. Below is an example of running all the tests in _FingerExercisesTests.java._ 

```java
Future<?> future = executor.submit(() -> {
    // runs tests in specified file
    try {
        Class testObj = CLASSNAME.class;
        CLASSNAME test = new CLASSNAME();
        for (Method method : testObj.getMethods()) {
            method.invoke(test, new Object[0]);
        }
    }


    // FeedbackTestCase.java configured to run all 
    // the tests in file FingerExercisesTests.java
    try {
        Class testObj = FingerExercisesTests.class;
        FingerExercisesTests test = new FingerExercisesTests();
        for (Method method : testObj.getMethods()) {
            method.invoke(test, new Object[0]);
        }
    }
})
```
> **Note:** \\
> The feedback code for running test cases runs every test case in the file regardless if methods used in the test cases have been implemented or not. \\
> To avoid a NoSuchMethodException from not having implemented certain functions yet, comment out test cases that use such methods. Additionally, you will get an AssertionError if your test case fails. This means your code produced the incorrect output and not necessarily because there was a runtime error. 

* * *

## Changing Error Messages
To change the feedback that the program gives upon a given error, edit the “msg” parameter that corresponds to the given error in the ErrorMap.json file:

```java
// Original error and message
{"err" :  "ArrayIndexOutOfBoundsException",
	"msg" : "UNIMPLEMENTED"}

// Updated error and message
{"err" :  "ArrayIndexOutOfBoundsException",
	"msg" : "Review chapter on arrays. Check the indices of the arrays you are accessing!"}
```


  









