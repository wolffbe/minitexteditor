# The Mini Text Editor Project

by **Benedict Wolff**, M1 Informatique CNI at the University of Rennes ISTIC for ACO 2024 under the supervision of **Adrian Le Roch**.

![editor.png](docs/editor.png)

Provided below is an overview of the editor's [features](#Features), what [commands](#Commands) it supports, and instructions on [how to build and run](#how-to-build-and-run-the-editor) the application. Further documentation can be found here:
- [Application design](./docs/design.md)
- [Application architecture](./docs/architecture.md)
- [Javadoc](./docs/javadoc/index.html)

---

## Features

- **Graphical user interface (GUI)**:
    - The GUI supports inserting, deleting, selecting, copying, cutting, and pasting text as well as undoing and redoing actions.
    - Actions can be recorded and replayed.
    - Every action is logged in the frontend to give the user feedback on changes to the engine's state.


- **Modular Backend**:
    - The application is built with Spring Boot, the industry standard for production-grade Java applications.
    - The backend is seperated into the core engine, the service, and the controller layer.
    - The Command Design Pattern is used for invoking commands.
    - The Memento Design Pattern is used for the undo/redo functionality.


- **REST API**:
    - Ten REST endpoints are available for operating the editor engine.
    - All endpoints are managed by the engine controller for easy API maintenance and extension.


- **Testing**:
    - Over 100 JUnit tests have been written to ensure the correct functionality of the editor.
    - The tests cover 93% test of all classes, branches and lines of the backend.

## Commands

| **Command** | **Description**                                                                                  |
|-------------|--------------------------------------------------------------------------------------------------|
| Insert      | any single character                                                                             |
| Select      | `SHIFT` + `→`, `SHIFT` + `←` or use the mouse                                                    |
| Select all  | `SHIFT` + `A`                                                                                    |
| Delete      | `Backspace`                                                                                      |
| Copy        | `CTRL` + `C`                                                                                     |
| Cut         | `CTRL` + `X`                                                                                     |
| Paste       | `CTRL` + `V`                                                                                     |
| Undo        | `CTRL` + `Z`                                                                                     |
| Redo        | `CTRL` + `Y`                                                                                     |
| Record      | The editor starts recording every further action.                                                |
| Replay      | The editor loops through every recorded action individually in the backend until its last state. |

## How to build and run the editor

### Prerequisites

- Java 17 or later
- Maven
- A browser (e.g., Chrome, Firefox).

### Build

_This step can be skipped, and an existing .jar file [run](#run) right away._

Clone the repository:

    git clone https://github.com/bjpw/minitexteditor.git
    cd minitexteditor

Build the Spring application:

    mvn clean install

### Run

Run the unit tests:

    mvn test

Run the Spring application OR

    mvn spring-boot:run

Execute the Java jar:

    cd target
    java -jar editor-3.0.jar

Open the frontend in your browser of choice:

    http://localhost:8080