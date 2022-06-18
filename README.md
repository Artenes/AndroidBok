# Android BOK

Android project made to compile some solutions for problems I face daily.

# Examples

## Custom Gradle task (buildSrc folder)

*Problem*: I need to run a task that do some complex work within a gradle task so it is easier to invoke it. Also being able to write tests for it would make things easier to maintain.

*Solution*: Create a `buildSrc` folder in the root of your project and implement your custom logic there as if it was a module. Then you can call it in your `build.gradle` file.

In this repo a custom task was created to import some raw data from csv files into a local sqlite database to be added to the app's assets folder.