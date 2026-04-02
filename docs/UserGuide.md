# User Guide

TutorMap is a **desktop app for private tutors to manage tutees, optimized for use via a Command Line Interface** (CLI) while still retaining mouse-based visual elements. TutorMap helps you manage tutee details in one place, including addresses, phone numbers, subjects taught, and relationships between contacts (eg. students, parents and agents).

TutorMap offers you a simple way to stay organized without complex software. If you have basic computer skills, and have been tracking tutees in spreadsheets or notes, then TutorMap is for you. It replaces messy spreadsheets so you can focus on teaching, not admin.

## Table of Contents

- [Quick start](#quick-start)
- [Features](#features)
  - [Viewing help : `help`](#viewing-help)
  - [Adding a person: `add`](#adding-person)
  - [Listing all persons : `list`](#listing-persons)
  - [Editing a person : `edit`](#editing-person)
  - [Locating persons by name: `find`](#finding-persons)
  - [Locating persons by relation: `find r/`](#finding-persons-by-relation)
  - [Adding or deleting a relation : `relate`](#relating-persons)
  - [Deleting a person : `delete`](#deleting-person)
  - [Clearing all entries : `clear`](#clearing-entries)
  - [Exiting the program : `exit`](#exiting-program)
- [Saving the data](#saving-the-data)
- [Editing the data file](#editing-the-data-file)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-W12-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TutorMap.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar tutormap.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the TutorMap.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### <span id="viewing-help"></span>Viewing help : `help`

Provides a message to the user displaying the list of different commands.

Command format: `help`

![help message](images/helpCommand.png)


### <span id="adding-person"></span>Adding a person: `add`

Adds a person to TutorMap.

Command format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [s/SUBJECT] [t/TAG]…`

Notes:
* A person can have any number of tags (including 0)
* A person can have any number of subjects (including 0)
* Person fields are case-sensitive (e.g. `John Doe` and `john doe` are different names, `Math` and `math` are different subjects)

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`
* `add n/Ceaser Chips t/student e/cc@example.com a/Mary street p/1234567 s/Math`

### <span id="listing-persons"></span>Listing all persons : `list`

Shows a list of all persons in TutorMap.

Command format: `list`

### <span id="editing-person"></span>Editing a person : `edit`

Edits an existing person in TutorMap.

Command format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [s/SUBJECT]…​`

Notes:
* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, ...
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* When editing subjects, the existing subject of the person will be removed i.e adding of subject is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.
* You can remove the person's subject by typing `s/` without specifying any subject after it.

Examples:
* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
* `edit 3 s/` Clears existing subject for the 3rd person.
* `edit 3 s/Math` Edits the subject of the 3rd person to be `Math`.
* `edit 4 s/English s/Science` Edits the subjects of the 4th person to be `English` and `Science`.

<box type="tip" seamless>

**Tip:**
Use the edit command to add tags or subjects to an existing person by including the existing tags or subjects in the edit command. 
e.g. if the person at index 1 has an existing tag `friend`, `edit 1 t/friend t/colleague` will add the tag `colleague` while keeping the existing tag `friend`.

</box>

### <span id="relating-persons"></span>Adding or deleting a relation : `relate`

Adds a relation between 2 specified people in TutorMap.

Command format (adding relation): `relate a\NAME1/NAME2/RELATION1/RELATION2`  
Command format (deleting relation): `relate d\NAME1/NAME2/RELATION1/RELATION2`

Notes:
* To add a relation, both names must exist.
* `NAME1` and `NAME2` must be different, adding relation to the person itself is not allowed.
* To delete the relation, all the names and relations must match an existing relation in the same format.
* The relation will be updated for both persons.
* Upon adding, `Person 1` and how `Person 2` is related to them will be shown on `Person 1`'s contact, and vice versa for `Person 2`.
* `RELATION1` refers to how `NAME1` is related to `NAME2`. eg. `Teacher Alex/Bernice Yu/Teacher/Student` means that `Teacher Alex` is `Bernice Yu`'s `Teacher`
* `RELATION2` refers to how `NAME2` is related to `NAME1`.  eg. `Teacher Alex/Bernice Yu/Teacher/Student` means that `Bernice Yu` is `Teacher Alex`'s `Student`
* Relations are bidirectional, `Teacher Alex/Bernice Yu/Teacher/Student` is equivalent to `Bernice Yu/Teacher Alex/Student/Teacher`.
* The command is case-sensitive for `NAME` e.g. `David` will not match `david`
* The command is case-sensitive for `RELATION` e.g. `Student` will not match `student`
* Supports multiple addition and/or deletion operations in the same command e.g. `relate a/a\NAME1/NAME2/RELATION1/RELATION2 d\NAME3/NAME4/RELATION3/RELATION4 ...`, `relate a/NAME1/NAME2/RELATION1/RELATION2 a/NAME3/NAME4/RELATION3/RELATION4 ...`

Examples:
* `relate a\Teacher Alex/Bernice Yu/Teacher/Student` will create a relation for both `Teacher Alex` and `Bernice Yu`.
* `relate d\Teacher Alex/Bernice Yu/Teacher/Student` will delete the relation for both `Teacher Alex` and `Bernice Yu`
* `relate a\Bernice Yu/Alex Yeoh/parent/child d\David Li/Charlotte Oliveiro/brother1/brother2` will add a relation for `Bernice Yu` and `Alex Yeoh` and delete the relation for `David Li` and `Charlotte Oliveiro`


### <span id="finding-persons"></span>Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Command format: `find KEYWORD [MORE_KEYWORDS]`

Notes: 
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### <span id="finding-persons-by-relation"></span>Locating persons by relation: `find r/KEYWORD`

Finds persons that have a relation containing the keyword.

Command format: `find r/KEYWORD`

Notes:
* The input being matched is the same input as the input given in the creation/deletion of relations
* The search is case-insensitive: e.g `hans` will match `Hans`
* As relations are bidirectional, searching `Bernice Yu/Alex Yeoh` is equivalent to searching `Alex Yeoh/Bernice Yu`
* Partial matches are allowed. For example, searching `r` will return results everyone that has a relation containing `r`
* Example: `r/mother` will find everyone who is a mother, or has a mother
* Example: `r/Alex Yeoh` will find everyone related to Alex Yeoh and himself
* Example: `r/a` will find everyone who has the letter `a` in the relation (matching names and/or roles)

### <span id="finding-persons-by-subject"></span>Locating persons by subject: `find s/KEYWORD`

Finds persons that have a subject containing the keyword.

Command format: `find s/KEYWORD`

Notes:
* The search is case-insensitive: For example, `Math` will match `math`
* Partial matches are allowed. For example, searching `M` will return results everyone that has a subject label containing `M`

Examples:
* `s/Math` will find everyone labelled with the subject that is or contains `Math`
* `s/C` will find everyone who is labelled with the subject that is or contains `C` (e.g. `Chemistry`, `Science`)

<box type="tip" seamless>

**Tip:**
To find persons with a specific subject, find subjects by typing the full subject name such as `s/Chinese` or `s/Chemistry`.
Simply typing `s/C` will match both Chemistry and Chinese subjects!

</box>

### <span id="relating-persons"></span>Adding or deleting a relation : `relate`

Adds a relation between 2 specified people in TutorMap.

Command format (adding relation): `relate a\NAME1/NAME2/RELATION1/RELATION2`  
Command format (deleting relation): `relate d\NAME1/NAME2/RELATION1/RELATION2`

Notes: 
* To add a relation, both names must exist.
* To delete the relation, all the names and relations must match an existing relation in the same format.
* The relation will be updated for both persons.
* Upon adding, `Person 1` and how `Person 2` is related to them will be shown on `Person 1`'s contact, and vice versa for `Person 2`.
* `RELATION1` refers to how `NAME1` is related to `NAME2`. eg. `Teacher Alex/Bernice Yu/Teacher/Student` means that `Teacher Alex` is `Bernice Yu`'s `Teacher`
* `RELATION2` refers to how `NAME2` is related to `NAME1`.  eg. `Teacher Alex/Bernice Yu/Teacher/Student` means that `Bernice Yu` is `Teacher Alex`'s `Student`
* The command is case-sensitive for `NAME` e.g. `David` will not match `david`
* The command is case-sensitive for `RELATION` e.g. `Student` will not match `student`
* Supports addition and deletion operations in the same command e.g. 

Examples:
* `relate a\Teacher Alex/Bernice Yu/Teacher/Student` will create a relation for both `Teacher Alex` and `Bernice Yu`.
* `relate d\Teacher Alex/Bernice Yu/Teacher/Student` will delete the relation for both `Teacher Alex` and `Bernice Yu`
* `relate a\Bernice Yu/Alex Yeoh/parent/child d\David Li/Charlotte Oliveiro/brother1/brother2` will add a relation for `Bernice Yu` and `Alex Yeoh` and delete the relation for `David Li` and `Charlotte Oliveiro`

### <span id="deleting-person"></span>Deleting a person : `delete`

Deletes the specified person from TutorMap.

Command format: `delete INDEX`

Notes: 
* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the tutor map.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### <span id="clearing-entries"></span>Clearing all entries : `clear`

Clears all entries from the tutor map.

Command format: `clear`

### <span id="exiting-program"></span>Exiting the program : `exit`

Exits the program.

Command format: `exit`

### Saving the data

TutorMap data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TutorMap data are saved automatically as a JSON file `[JAR file location]/data/tutormap.json`. Advanced users are welcome to update data directly by editing that data file.

**Caution:**
If your changes to the data file makes its format invalid, TutorMap will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the TutorMap to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TutorMap home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG] [s/SUBJECT]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG] [s/SUBJECT]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find (by name)**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Find (by relation)**   | `find r/KEYWORD` e.g., `find r/mother`, `find r/Alex Yeoh/Bernice Yu`
**List**   | `list`
**Help**   | `help`
**Relate** (add) | `relate a\NAME1/NAME2/RELATION1/RELATION2`<br> e.g., `relate a\Teacher Alex/Bernice Yu/Teacher/Student`
**Relate** (delete)| `relate d\NAME1/NAME2/RELATION1/RELATION2`<br> e.g., `relate d\Teacher Alex/Bernice Yu/Teacher/Student`
