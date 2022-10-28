# nudge-feedback-tool
Repo for the Nudge/Feedback Tool

# Instructions for FeedbackRecursion
## .codio file should look like this:
{
    "commands": {
        "Compile": "javac -cp junit-platform-console-standalone-1.3.2.jar:cis110.jar:jackson-annotations-2.13.3.jar:jackson-core-2.13.3.jar:jackson-databind-2.13.3.jar:. *.java",
        "Run Tests": "java FingerExerciseTests",
        "Run Feedback": "java -cp junit-platform-console-standalone-1.3.2.jar:cis110.jar:jackson-annotations-2.13.3.jar:jackson-core-2.13.3.jar:jackson-databind-2.13.3.jar:. Feedback",
        "Autoformat Files": "python3 linter.py *.java"
    }
}