## Table of Contents

- [Task Manager](#task-manager)
- [Set Up Instructions](#set-up-instructions)
  - [Windows](#windows)
  - [MacOS](#macos)
- [Features Implemented](#features-implemented)
- [Known Issues](#known-issues)
- [Future Improvements](#future-improvements)

## Task Manager

The Task Manager is a productivity and organization app created in Java. It enables the user to add, edit or delete different tasks, presented in a clear and organized GUI. The Task Manager also includes different functionalities, such as priority implementation, sorting functionalities or task filtering.

## Set up Instructions

Below you can find the instructions to set up your enviroment for the Task Manager.

[ ! ] __*Note:*__ Some instructions may be specific to Visual Studio Code 2 code editor.

## Windows *⊞*

__*Coming soon...*__

## MacOS 

[ ! ] __*Note:*__ The following instructions were written based on the **MacOS Sequoia 15.0.1**. The following steps could be susceptible to errors due to future upgrades of the MacOS operating system.

**1. Open your code editor and create a new proyect** 

In Visual Studio Code 2, press **⇧ (Shift) + ⌘ (Command) + P** and select **"Create Java Project..."**.
After this, select **"No build tools"** and establish a *path* and *title* for the new project.

**2. Download JavaFX**

Navigate to the [JavaFX download page](https://gluonhq.com/products/javafx/); Insert your *architecture* and *OS* and download the __*.SDK*__ file.

[ ! ] __*Note:*__ Current date version for JavaFX is **23.0.1**.

Once the file is downloaded, **open the .zip** file, which will decompress into a folder **(javafx-sdk-23.0.1)**.

Insert the folder in the **same path where your project is located**. Once inserted, it should appear and be available in your code editor.

[ ! ] __*Note:*__ Remember the current path for the **javafx-sdk-23.0.1** folder, as you will need it for the next step.

**3. Install JavaFX libraries** 

Once the **javafx-sdk-23.0.1** folder is visible on your code editor, go inside the code editor and search for the __*Explorer*__ window, which is normally located in the left side of your screen. 

After locating the __*Explorer*__ tab, click on __*Java Projects*__ > __*Reference Libraries*__ and click on the __[ + ]__ button. 

Navigate to the path where you located the **javafx-sdk-23.0.1** folder and open it, then click on the __*lib*__ folder; Click and select the following __*.jar*__ files:
- javafx-swt.jar
- javafx.base.jar
- javafx.controls.jar
- javafx.fxml.jar
- javafx.graphics.jar
- javafx.media.jar
- javafx.swing.jar
- javafx.web.jar

Once selected, click on **"Select Jar Libraries"**.

**4. Add Configurations**

Once the libraries are in place and installed, go to the upper menu bar in your macOS and click on __*Run > Add Configuration*__.

A __*launch.json*__ file should have opened, with different configurations for all __*.java*__ files.

Replace the __*launch.json*__ file with the one provided.

Open the already replaced __*launch.json*__ file, and search for the following lines of code.

```json
{
"type": "java",
"name": "TaskManagerApp",
"request": "launch",
"vmArgs": "--module-path /Users/user/path/to/file/javafx-sdk-21.0.5/lib --add-modules javafx.controls",
"mainClass": "TaskManagerApp",
"projectName": "Project_d99e908"
},
{
"type": "java",
"name": "Task",
"request": "launch",
"vmArgs": "--module-path /Users/user/path/to/file/javafx-sdk-21.0.5/lib --add-modules javafx.controls",
"mainClass": "Task",
"projectName": "Project_d99e908"
}
```

Once located, change the path found on the **"vmArgs"** argument for your own path to the __*/lib*__ folder, in the **(javafx-sdk-23.0.1)** folder.

[ ! ] __*Note:*__ This has to be done for both __*TaskManagerApp*__ and __*Task*__.

BEFORE: (Example)
```json
{
"name": "TaskManagerApp",
"vmArgs": "--module-path /Users/user/path/to/file/javafx-sdk-21.0.5/lib --add-modules javafx.controls",

},
{
"name": "Task",
"vmArgs": "--module-path /Users/user/path/to/file/javafx-sdk-21.0.5/lib --add-modules javafx.controls",
}
```

AFTER: (Example)
```json
{
"name": "TaskManagerApp",
"vmArgs": "--module-path /Users/user/path/to/my/file/javafx-sdk-21.0.5/lib --add-modules javafx.controls",

},
{
"name": "Task",
"vmArgs": "--module-path /Users/user/path/to/my/file/javafx-sdk-21.0.5/lib --add-modules javafx.controls",
}
```

Once this step is completed, the **Task Manager set up is completed** and should be ready to use!

Enjoy!

## Features Implemented



## Known Issues

- Once you sort the tasks, you cannot unsort the tasks

## Future Improvements
