# JavaFX-FinalYearModuleSelector

Final Year Module Selection Tool

Welcome to the Final Year Module Selection Tool repository! This project was created as part of my second-year coursework for Computer Science at university. The goal was to develop a Java application using the JavaFX library to enable students to create profiles, select modules for their final year, and generate an output with their selected options. The project adheres to the Model View Controller (MVC) design pattern for creating a Graphical User Interface (GUI). In accordance with the brief, no data should flow directly from the model to the view; all data should be handled by the controller.
Program Outline

The project is structured into different packages, each containing relevant classes:

Controller

    - CollapsableModuleTilesAddBtnHandler
    - CollapsableModuleTilesRemoveBtnHandler
    - CreditsChangeListener
    - FinalYearOptionsController

Main

    - ApplicationLoader

Model

    - Course
    - Module
    - Name
    - RunPlan
    - StudentProfile

View

    - CollapsableModuleTiles
    - CreateStudentProfilePane
    - FinalYearOptionsMenuBar
    - FinalYearOptionsRootPane
    - Message
    - ModulePanes
    - OverviewPane

Features

    Profile Creation: Students can create profiles with relevant personal information.
    Module Selection: Users can choose modules they wish to study during their final year.
    Output Generation: The tool generates an output that displays the selected modules and other relevant information.
    Settings: Customize the program's theme (light/dark), add more modules, and include additional courses.
    Validation: Ensures that all data entered, including student email and dates, is accurate and complete.
    Exit Confirmation: Prevents accidental program closure and prompts the user for confirmation.


Releases
Version 1.0.2

This is the initial release of the Final Year Module Selection Tool. Please refer to the Releases section for future updates and enhancements.
