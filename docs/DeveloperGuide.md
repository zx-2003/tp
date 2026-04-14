---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TutorMap Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

All thanks go to AddressBook-3 and CS2103T.

CoPilot was used in the following ways within this project:
* Used to understand the project structure and codebase.
* Used to guide implementation and generate code snippets for some of the features and fields, such as subject.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/tutor/Main.java) and [`MainApp`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/tutor/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/tutor/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/tutor/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/tutor/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `TutorMapParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `TutorMapParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `TutorMapParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/tutor/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the TutorMap data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Label` list in the `TutorMap`, which `Person` references. This allows `TutorMap` to only require one `Label` object per unique label, instead of each `Person` needing their own `Label` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S2-CS2103T-W12-3/tp/blob/master/src/main/java/seedu/tutor/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both TutorMap data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `TutorMapStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.tutor.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedTutorMap`. It extends `TutorMap` with an undo/redo history, stored internally as an `TutorMapStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedTutorMap#commit()` — Saves the current TutorMap state in its history.
* `VersionedTutorMap#undo()` — Restores the previous TutorMap state from its history.
* `VersionedTutorMap#redo()` — Restores a previously undone TutorMap state from its history.

These operations are exposed in the `Model` interface as `Model#commitTutorMap()`, `Model#undoTutorMap()` and `Model#redoTutorMap()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedTutorMap` will be initialized with the initial TutorMap state, and the `currentStatePointer` pointing to that single TutorMap state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the TutorMap. The `delete` command calls `Model#commitTutorMap()`, causing the modified state of the TutorMap after the `delete 5` command executes to be saved in the `TutorMapStateList`, and the `currentStatePointer` is shifted to the newly inserted TutorMap state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David ...` to add a new person. The `add` command also calls `Model#commitTutorMap()`, causing another modified TutorMap state to be saved into the `TutorMapStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitTutorMap()`, so the TutorMap state will not be saved into the `tutorMapStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoTutorMap()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous TutorMap state, and restores the TutorMap to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial TutorMap state, then there are no previous TutorMap states to restore. The `undo` command uses `Model#canUndoTutorMap()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoTutorMap()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the TutorMap to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `tutorMapStateList.size() - 1`, pointing to the latest TutorMap state, then there are no undone TutorMap states to restore. The `redo` command uses `Model#canRedoTutorMap()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the TutorMap, such as `list`, will usually not call `Model#commitTutorMap()`, `Model#undoTutorMap()` or `Model#redoTutorMap()`. Thus, the `tutorMapStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitTutorMap()`. Since the `currentStatePointer` is not pointing at the end of the `tutorMapStateList`, all TutorMap states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David ...` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire TutorMap.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Tutors who need to manage multiple contacts related their tutoring work (students, parents, agents)
* Prefer desktop applications over mobile or web applications
* Prefer keyboard/CLI interactions rather than heavy mouse usage

**Value proposition**: TutorMap helps private tutors manage tutoring contacts faster than typical GUI-based contact managers
by combining a visual interface with efficient CLI commands for quick data entry, tagging, and relationship tracking.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a ...                                   | I want to ...                                | So that I can...                                                            |
|----------|--------------------------------------------|----------------------------------------------|-----------------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions                       | refer to instructions when I forget how to use the App                      |
| `* * *`  | new user                                   | see an initial help message                  | see how to get started with the App                                         |
| `* * *`  | user                                       | delete a contact                             | remove contacts that I no longer need                                       |
| `* * *`  | user                                       | find a contact by different fields           | locate details of contact without having to go through the entire list      |
| `* * *`  | user                                       | add relation between two contacts            | keep track of how they are connected                                        |
| `* *`    | user                                       | find other relevant contacts given a contact | see others who are related to my contact if there is a need to contact them |
| `* *`    | user                                       | edit the subject field of a contact          | keep the contact's subject information up to date                           |
| `* *`    | user                                       | rename a subject across the contacts         | correct or standardize subject names easily                                 |
| `* *`    | user                                       | delete a subject across the contacts         | remove outdated or unnecessary subject records                              |
| `* *`    | user                                       | be able to tag contacts                      | view information relevant to this contact                                   |
| `*`      | user with many persons in the contact book | sort persons by name                         | locate a contact easily                                                     |

### Use cases

(For all use cases below, the **System** is the `TutorMap` and the **Actor** is the `user`, unless specified otherwise)

(For all use cases below, the user has launched the application and is on the main screen)

**Use case: UC1 - Listing all persons**

**MSS**

1.  User requests to list persons
2.  TutorMap shows a list of persons

**Extensions**

* 2a. The list is empty.

  Use case ends.

**Use case: UC2 - Delete a person**

**MSS**

1.  User performs Listing all Persons (UC1) or Finding a person (UC5)
2.  User requests to delete a specific person in the list
3.  TutorMap deletes the person

    Use case ends.

**Extensions**

* 1a. The list is empty.

    Use case ends.

* 2a. The given index is invalid.

    * 2a1. TutorMap shows an error message.

      Use case resumes at step 2.

**Use case: UC3 - Add a person**

**MSS**

1.  User performs Listing all Persons (UC1) or Finding a person (UC5)
2.  User requests to add a person to the list
3.  TutorMap adds the person

    Use case ends.

**Extensions**

* 2a. The user entered invalid parameters while adding

    * 2a1: TutorMap shows an error message.

      Use case resumes at step 2.

**Use case: UC4 - Edit a person**

**MSS**

1.  User performs Listing all Persons (UC1) or Finding a person (UC5)
2.  User requests to edit a person on the list
3.  TutorMap edits the person

    Use case ends.

**Extensions**

* 1a. The list is empty.

    Use case ends.

* 2a. The user entered invalid parameters/index while editing

    * 2a1: TutorMap shows an error message.

      Use case resumes at step 2.

**Use case: UC5 - Finding a person**

**MSS**

1.  User attempts to find the person based on name, subject, tags, relation, email, phone number or address
2.  TutorMap displays people found and the result count

     Use case ends.

**Extensions**

* 1a. There is no match.

     * 1a1: TutorMap only shows the result count

         Use case ends.

* 1b. The user entered invalid parameters while finding

     * 1b1: TutorMap shows an error message.

         Use case resumes at step 1.

**Use case: UC6 - Adding a relationship**

**MSS**

1.  User attempts to add a relationship between two person
2.  TutorMap displays the change in detail of those two person

    Use case ends.

**Extensions**

* 1a. Incorrect input format

    * 1a1: TutorMap shows an error message.

      Use case ends.

* 1b. person1 or person2 doesn't exist

    * 1b1: TutorMap shows an error message.

      Use case ends.

* 1c. the relationship already exist between the two person

    * 1b1: TutorMap shows an error message. 
  
      Use case ends.

**Use case: UC7 - Deleting a relationship**

**MSS**

1.  User attempts to delete a relationship between two person
2.  TutorMap displays the change in detail of those two person

    Use case ends.

**Extensions**

* 1a. Incorrect input format

    * 1a1: TutorMap shows an error message.

      Use case ends.

* 1b. person1 or person2 doesn't exist

    * 1b1: TutorMap shows an error message.

      Use case ends.

* 1c. the relationship does not exist between the two person

    * 1b1: TutorMap shows an error message.

      Use case ends.

**Use case: UC8 - Deleting a subject**

**MSS**

1.  User attempts to delete a subject across multiple person
2.  TutorMap displays successful deletion of the subject

    Use case ends.

**Extensions**

* 1a. Incorrect input format

    * 1a1: TutorMap shows an error message.

      Use case ends.

* 1b. the subject doesn't exist

    * 1b1: TutorMap shows an error message.

      Use case ends.

**Use case: UC9 - Renaming a subject**

**MSS**

1.  User attempts to rename a subject across multiple person
2.  TutorMap displays the success of renaming of the subject

    Use case ends.

**Extensions**

* 1a. Incorrect input format

    * 1a1: TutorMap shows an error message.

      Use case ends.

* 1b. the subject doesn't exist

    * 1b1: TutorMap shows an error message.

      Use case ends.

**Use case: UC10 - Editing subject field**

**MSS**

1.  User attempts to edit a subject field of a person using `subject` command
2.  TutorMap displays the result subject field of the person

    Use case ends.

**Extensions**

* 1a. Incorrect input format

    * 1a1: TutorMap shows an error message.

      Use case ends.

**Use case: UC11 - Finding persons by subject or tag keywords**

**MSS**

1.  User enters `find s/KEYWORD [MORE_KEYWORDS]` or `find t/KEYWORD [MORE_KEYWORDS]`
2.  TutorMap trims extra whitespaces between keywords before searching
3.  TutorMap matches persons whose subject or tag contains at least one keyword (OR behavior)
4.  TutorMap displays the matched persons and the result count

    Use case ends.

**Extensions**

* 1a. The prefix is provided but no valid keyword is given.

    * 1a1: TutorMap shows an error message.

      Use case resumes at step 1.

* 3a. There is no match.

    * 3a1: TutorMap displays only the result count.

      Use case ends.

### Non-Functional Requirements

1.  Should run on any mainstream OS that supports Java without requiring OS-specific setup beyond Java and JDK.
2.  All user data should be stored locally on the user’s device.
3.  Should not artificially restrict the number of contacts i.e. performance limited by user's hardware only.
4.  Should not impose a hard limit on the number of labels per contact.
5.  Saving updates should be efficient and avoid unnecessary full-file rewrites for small changes.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Tutor**: The user of TutorMap - A private tutor that teaches school subjects to students.
* **Student**: Students under the teaching of the tutor.
* **Parent**: Parents of `Students` under the teaching of the tutor.
* **Agent**: A person that helps match `Students` to the tutor.
* **Subject**: School subjects that the tutor teaches, eg. English, Math.
* **Related contact**: Contacts that are related. Examples:
    * `students` and their `parents` are related.
    * `agents` are related to the `students`/`parents` that the `agent` referred to the tutor.
    * `students`/`parents` are related to other `students`/`parents` that they referred to the tutor.
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Navigate to the directory containing `tutormap.jar` on the terminal. Run `java -jar tutormap.jar`. Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. All relations containing the first contact is also deleted. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Managing Relations

1. Testing the bidirectional relationship logic between contacts.

   1. Prerequisites: Ensure that at least two contacts exist e.g. Alex Yeoh (Index 1) and Bernice Yu (Index 2).
   
   2. Test Case (Add Relation): `relate a\Alex Yeoh/Bernice Yu/Tutor/Student`
      Expected: Success message shown. Both Alex and Bernice’s contact cards now display the relationship.
      
   3. Test Case (Duplicate/Self): `relate a\Alex Yeoh/Alex Yeoh/Self/Self`
      Expected: Error message. Relations cannot be made to the same person.
      
   4. Test Case (Delete Relation): `relate d\Alex Yeoh/Bernice Yu/Tutor/Student`
      Expected: Relationship removed from both contacts.
      
   5. Test Case (Case Sensitivity): `relate a\alex yeoh/Bernice Yu/Tutor/Student`
      Expected: Error message. Contact names are case-sensitive.

### Managing Subjects

1. Testing the ability to modify subjects across the entire list or for specific individuals.

   1. Prerequisites: A mix of contacts with `Maths`, `Physics`, `Chemistry`, `Biology`, and 1 subject with both `English` and `History`.
      
   2. Test Case (Global Rename in list): `subject r\Maths/Mathematics`
      Expected: Every contact currently *in the list* who had "Maths" now has "Mathematics".
      
   3. Test Case (Batch Delete): `subject d\Physics/Chemistry/Biology`
      Expected: These three subjects are removed from all contacts *in the current list*. If any of the subjects do not exist, the command fails.
      
   4. Test Case (Toggle Subject via Index): `subject 1 e\English/History`
      Expected: If Person 1 already had "English", it is removed. If they did not have "History", it is added.
      
   5. Test Case (Invalid Renaming): `subject r\NonExistentSubject/NewSubject`
      Expected: Error message indicating the source subject does not exist.

### Finding Contacts

1. Testing the flexibility of the search system across different prefixes.

    1. Test Case (Multiple Name Search): `find n/Sally David`
        Expected: Lists all persons whose names contain "Sally" OR "David".
       
    2. Test Case (Partial Phone Search): `find p/9123 4567`
        Expected: Lists all persons whose phone numbers contain the sequence "9123 4567".

   3. Test Case (Relation Search): `find r/Alex Yeoh`
           Expected: Displays Alex Yeoh and every person linked to him via a relation.

   4. Test Case (Case Insensitivity): `find s/mAtH`
           Expected: Should successfully find contacts with the subject "Math" (search is case-insensitive, though the add command is case-sensitive).

### Editing Contacts (Tags and Subjects)

1. Testing the replacement logic for tags and subjects.

   1. Test Case (Clear Subjects): `edit 1 s/`
       Expected: Person 1’s subjects are completely cleared.
   
   2. Test Case (Replace Tags): Person 1 has tag friend. Execute `edit 1 t/colleague`.
       Expected: Person 1 now has only the tag colleague. The tag friend is removed (tags are not cumulative).

### Saving data

1. Dealing with missing/corrupted data files

   1. If your changes to the data file makes its format invalid, TutorMap will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
   2. TutorMap does not erase all your data in case an invalid relation exists that still conforms to the relation format `name1/name2/relation1/relation2`. However, it may behave in unexpected ways.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Make `name` field more permissible.** The current `name` field only allows alphanumeric and whitespace characters, which might prevent the addition of real names like `O'Brien`. We plan to allow non-alphanumeric, as well as non-English characters.
2. **Make `subject` field more permissible.** The current `subject` field only allows alphanumeric characters, which might prevent the addition of subjects like `H2 Math`. We plan to allow non-alphanumeric, as well as non-English characters.
3. **Make `tag` field more permissible.** The current `tag` field only allows alphanumeric characters, which might prevent the addition of tags like `consult-based tutoring`. We plan to allow non-alphanumeric, as well as non-English characters.
4. **Trim `name` fields with multiple whitespace between words.** The current `name` field accepts names with different internal whitespace as separate names, which usually does not appear in regular names and are mostly likely typos. We plan to trim multiple whitespace into 1 whitespace, and treat these contacts as the same person.  
5. **Treat `name` fields with different capitalization as the same person.** The current `name` field treats names with different capitalization as separate names, which usually should be referring to the same person. We plan to treat these contacts as the same person.  
6. **Create unique index identifier for contacts.** The current app uses the `name` field as a unique contact identifier, which prevents multiple contacts of the same name from being added. We plan to add a unique numerical identifier for each contact added, to allow for duplicate names.
7. **Allow `relate` command to only be performed on people on the current display list.** The current implementation allows the relation of everyone in the app regardless of whether it is currently being filtered and displayed. We plan to only allow for relating contacts on the filtered list to align with other commands like `delete` that can only be performed on the filtered list.
8. **Input validation of `find` command.** The current implementation allows for erroneous find command such as `find s/math s/science` which may be a point of confusion if the user mistakes the syntax. Ideally, both keywords should be parsed and checked if valid, because subjects do not allow slashes and thus the second keyword would never be a valid keyword for the `find` function. This applies to other fields as well.
9. **Implementation of `find` command to support finding by multiple fields.** The current implementation allows input that would find by multiple fields, but the filter is only applied to the first field. `find s/math t/friend` would only filter by subject instead of both subject and tag.
