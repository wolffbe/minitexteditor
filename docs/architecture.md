# Architecture

The Mini Text Editor is built with a layered, modular architecture, integrating **design patterns** and **REST API** principles. It consists of the following key components:

## **Table of Contents**
1. [Layered Structure](#1-layered-structure)
2. [Frontend](#2-frontend)
3. [Packages](#3-packages)
4. [REST API](#4-rest-api)

## **1. Layered Structure**

The application is designed using a layered architecture to ensure a clear separation of concerns and modularity. This structure helps improve maintainability and allows for easier future extensions. The three primary layers are:

### **a. Core Layer**
The **Core Layer** implements the core logic of the text editor and is responsible for handling key operations such as text manipulation, selection, clipboard, and buffer management. This layer is central to the functionality of the application.

### **b. Service Layer**
The **Service Layer** acts as an intermediary between the core engine and the REST API. It is responsible for invoking commands and managing the application’s state in response to incoming requests from the frontend.

- One of the primary functions of this layer is invoking commands from the **Command Pattern**. It receives high-level requests from the controller layer and delegates them to the appropriate command in the core layer for execution.
- The **Memento Design Pattern** is used in this layer to manage undo and redo operations. It stores snapshots of the editor’s state, enabling users to revert to previous states or reapply undone changes.
- This layer also provides higher-level operations for processing API requests, such as updating the text selection, handling clipboard operations, and performing undo/redo actions, ensuring that each request is processed consistently.

### **c. Controller Layer**
The **Controller Layer** is responsible for implementing the REST API using **Spring Boot**. It exposes endpoints that allow the frontend to interact with the backend and trigger specific text editing operations.

- The controller layer defines several endpoints, such as `/copy`, `/undo`, and `/redo`, that correspond to the operations in the core engine. These endpoints accept client requests and delegate the corresponding actions to the service layer.
- In response to API requests, the controller layer returns the current state of the engine, typically in the form of an `EngineDto`, which is a data transfer object that represents the state of the text buffer, selection, and other relevant information.

---

## **2. Frontend**
The frontend of the application is built using **HTML**, **CSS**, and **JavaScript**, providing a user-friendly interface for interacting with the text editor.

- The frontend is responsible for rendering the user interface, which includes a **Text Editor** where users can type, edit, and select text. The text editor interacts with the backend by sending asynchronous requests to the REST API using the `fetch` function.
- As the user performs actions (e.g., cutting, copying, or undoing changes), the frontend sends requests to the backend to perform these operations, logging each change to the engine state for consistency.
- The **Log Panel** displays real-time feedback to the user, showing the current state of the engine in JSON format, which provides insight into the application's internal state.
- The **Control Buttons** allow users to interact with the editor’s features, such as recording actions for later playback or undoing and redoing actions. These buttons are tied to the REST API endpoints, providing an intuitive way to control the editor's behavior.

---

## **3. Packages**

The application is structured into four packages:

### **Engine**
- The Engine package serves as the core component of the text editor, managing all the operations related to text manipulation, such as insertion, deletion, and modification of text. 
- It maintains the text buffer, representing the current content of the editor, and provides methods to update and retrieve text.
- The engine is responsible for handling text manipulation commands, ensuring that the state of the text editor is updated correctly based on user actions (e.g., inserting or deleting text).

### **Selection**
- The Selection package manages the user's selected portion of the text, allowing for operations like copy, cut, and paste to be applied to specific segments of the buffer.
- It maintains the start and end indices of the selected text, ensuring accurate manipulation of the editor's content.
- The selection logic is encapsulated in a dedicated class (SelectionImpl), which provides methods to update and query the selection, allowing for clear separation of concerns from the core text manipulation logic.

### **Command**
- The Command package is used to encapsulate all text editing operations (such as Cut, Copy, Paste, Undo, and Redo) as command objects, decoupling the request from the execution logic.
- Each command represents an action, and the editor invokes these commands through an Invoker (e.g., EngineInvoker), which allows for consistent execution of different operations without direct coupling to the engine's core logic.

### **Memento**
- The Memento package is used to implement undo/redo functionality in the text editor by capturing and storing snapshots of the application's state.
- It enables the application to revert to a previous state (undo) or reapply a state that was reverted (redo), making it easy to track and manage changes over time.
- Key components:
  - Memento: Encapsulates the state of the EngineImpl (text buffer and selections) at a specific point in time. Each memento object holds the snapshot of the state for later restoration.
  - Originator: Responsible for creating and restoring mementos. The EngineImpl class acts as the originator, creating a memento whenever a change is made and restoring the state from a memento during an undo or redo operation.
  - Caretaker: Manages the history of mementos, keeping track of the states for undo and redo operations. The caretaker stores mementos in a history stack and allows navigation between them, enabling the user to revert or reapply changes easily.

---

## **4. REST API**

The **REST API** serves as the communication layer between the backend and frontend. The API supports the following endpoints after `/api/engine`:

| **Method** | **Endpoint** | **Description**                           |
|------------|--------------|-------------------------------------------|
| GET        | `/`       | Returns the current state of the engine.  |
| POST       | `/select`    | Updates the text selection indices.       |
| POST       | `/cut`       | Cuts the selected text to the clipboard.  |
| POST       | `/copy`      | Copies the selected text to the clipboard. |
| POST       | `/paste`     | Pastes the clipboard content.             |
| POST       | `/insert`    | Inserts text into the buffer.             |
| DELETE     | `/delete`    | Deletes the selected text.                |
| GET        | `/replay`    | Replays recorded actions.                 |
| POST       | `/undo`      | Reverts the last action.                  |
| POST       | `/redo`      | Redoes the last undone action.            |