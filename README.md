### Custom Package Organizer

## Project Goal 
To create a locally-run app used for visualization and analysis of the high-level design of OOP applications. No commercial intents, just a personal, lerning project in Java and JavaFX

## Structure
Objects represented as free-floating resizable and movable boxes on a canvas. Each object contains fields for the object's variables/methods, which can then be connected to specific components of other objects. An "abstract" command can be used to bundle object's together in a "black box" view, in order to visualize what each method/object can "see" of other objects or packages. The canvas, along with all children is saveable in a .txt file.

## Dependencies
- Java SE 22
- JavaFX 22.0.2

## Status: Work in progress
Current sub-objective: Implement the feature of connecting methods/variables between different objects

### Completed sub-objectives:
- Basic GUI structure
- Ability to add and relocate boxes
- Ability to add a title and methods/variables in boxes
- Ability to save/load canvas states
