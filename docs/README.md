# Fishball — IP Project

Fishball is a lightweight, text-based task manager. It provides simple commands for creating, listing, marking, unmarking, deleting and searching tasks.

**Status:** work-in-progress

---

## Table of contents

- Overview
- Features
- User Guide
- Project structure
- Prerequisites
- Setting up in IntelliJ
- Building and running
- Running tests
- Contributing
- Example PR description (GitHub Flavored Markdown)

---

## Overview

Fishball is a small Java CLI task manager used as an individual project (iP) exercise. It demonstrates common Java project tasks:

- command parsing and input handling
- persistence to disk (simple CSV-like format)
- unit testing with JUnit 5
- Javadoc documentation for classes

The main entrypoint is `src/main/java/Fishball.java`.

## Features

- Create tasks: `todo`, `deadline`, `event`
- List tasks: `list`
- Mark / unmark tasks: `mark <n>` / `unmark <n>`
- Delete tasks: `delete <n>`
- Find tasks by whole-word keyword: `find <keyword>`
- View upcoming tasks: `reminder`
- Persist tasks between runs using a local file

---

## User Guide

Fishball is a CLI-first task manager designed for fast keyboard input. This section explains all available commands with examples.

### Notes about command format

- Words in `UPPER_CASE` are parameters to be supplied by the user. e.g. in `todo TASK`, `TASK` is a parameter which can be used as `todo Read book`.
- Items in square brackets are optional. e.g. `deadline TASK /by DATE` can be used as shown.
- The index `N` in commands must be a positive integer (1, 2, 3, …).

### Quick start

1. Ensure you have JDK 17 or above installed.
2. Download or clone the Fishball repository.
3. Run `./gradlew run` (or `./gradlew.bat run` on Windows) from the project root.
4. A CLI prompt will appear. Type a command and press Enter to execute it.

Try these example commands:
- `list` — shows all tasks
- `todo Buy groceries` — adds a new task
- `bye` — exits the application

Refer to the features below for details of each command.

### Features

#### Adding tasks: `todo`, `deadline`, `event`

**Todo task:**

Creates a simple task with just a description.

Format: `todo TASK`

Example: `todo Buy groceries`

**Deadline task:**

Creates a task with a deadline date and time. Date format is `dd-MM-yyyy HHmm` (e.g., 25-12-2024 1800).

Format: `deadline TASK /by DATE TIME`

Example: `deadline Submit project /by 15-03-2024 2359`

**Event task:**

Creates a task with a start and end date/time.

Format: `event TASK /from START_DATE START_TIME /to END_DATE END_TIME`

Example: `event Team meeting /from 20-02-2024 1000 /to 20-02-2024 1100`

#### Listing tasks: `list`

Shows all tasks in your task list with their status (marked or unmarked).

Format: `list`

#### Marking tasks: `mark`, `unmark`

Marks a task as done or undone.

Format: `mark N` or `unmark N`
- Marks the task at index `N` as done or not done.
- The index refers to the number shown in the task list (starting from 1).

Examples:
- `mark 1` — marks the 1st task as done
- `unmark 3` — marks the 3rd task as not done

#### Deleting tasks: `delete`

Removes a task from the list.

Format: `delete N`
- Deletes the task at index `N`.
- The index refers to the number shown in the task list (starting from 1).

Example: `delete 2` — deletes the 2nd task in the list

#### Finding tasks: `find`

Searches for tasks by a keyword (whole-word matching, case-insensitive).

Format: `find KEYWORD`
- Only full words are matched. e.g. `book` will not match `books`.
- Matching is case-insensitive.

Example: `find meeting` — shows all tasks that mention "meeting"

#### Viewing upcoming reminders: `reminder`

Shows all tasks with deadlines or event endings within the next 7 days that are not yet marked as done.

Format: `reminder`

#### Exiting: `bye`

Exits the application. All changes are automatically saved.

Format: `bye`

#### Saving data

All changes to your task list are automatically saved to disk (`data/fishball.txt`) after each command. There is no need to save manually.

### Command summary

| Command | Format | Example |
|---------|--------|---------|
| Add ToDo | `todo TASK` | `todo Buy groceries` |
| Add Deadline | `deadline TASK /by DATE TIME` | `deadline Submit project /by 15-03-2024 2359` |
| Add Event | `event TASK /from DATE TIME /to DATE TIME` | `event Meeting /from 20-02-2024 1000 /to 20-02-2024 1100` |
| List | `list` | `list` |
| Mark | `mark N` | `mark 1` |
| Unmark | `unmark N` | `unmark 1` |
| Delete | `delete N` | `delete 2` |
| Find | `find KEYWORD` | `find meeting` |
| Reminder | `reminder` | `reminder` |
| Exit | `bye` | `bye` |

---

## Project structure

A high-level view of the important folders and files:

- `src/main/java` — application source
    - `Fishball.java` — main class (application orchestrator)
    - `utils/` — `UI`, `Storage`, `TaskList`, etc.
    - `tasks/` — `Task`, `Todo`, `Deadline`, `Event`
- `src/test/java` — unit tests (JUnit 5)
- `build.gradle` — Gradle build configuration
- `data/fishball.txt` — persistent storage file (created at runtime)

---

## Prerequisites

- JDK 17
- Gradle wrapper (project includes `gradlew` and `gradlew.bat`)

---

## Setting up in IntelliJ

1. Install JDK 17 and ensure IntelliJ is up-to-date.
2. From IntelliJ welcome screen click `Open` and select this project folder.
3. Set the Project SDK to **JDK 17** and Project language level to `SDK default`.
4. Locate `src/main/java/Fishball.java`, right-click and Run `Fishball.main()` to start.

If the setup is correct you will see startup output similar to:

```
Hello, I'm Fishball!

What can I do for you?
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

**Warning:** Keep `src/main/java` as the root folder for Java files.

---

## Building and running

From the project root you can use the included Gradle wrapper.

```bash
./gradlew build   # build artifact and run checks
./gradlew run     # run the application
```

On Windows use `gradlew.bat`:

```powershell
.\gradlew.bat build
.\gradlew.bat run
```

---

## Running tests

Unit tests are written with JUnit 5. Run them with:

```bash
./gradlew test
```

Test reports are written to `build/reports/tests/test/index.html` after the test task completes.

---

## Contributing

- Follow the project style and conventions already present in the repository.
- Add Javadoc to new public classes and methods.
- Add unit tests for behaviour-critical code.
- When proposing changes, include a clear PR description using GitHub Flavored Markdown (GFMD). See the example PR template below.

---

## Example PR Description (GitHub Flavored Markdown)

Use this example as a starting template for your PR description. It demonstrates the GFMD elements required for the iP submission checklist.

### PR: iP — Add `find` command, tests, and Javadoc

**Summary:** Add `find` command to `UI`, add unit tests for `TaskList` and `UI`, and add Javadoc headers for core classes.

**Scope:** `Fishball`, `utils/UI.java`, `utils/TaskList.java`, `utils/Storage.java`, `src/test/java`.

#### What this PR includes

- A whole-word, case-insensitive `find` command in `UI`.
- Unit tests for `TaskList` and `UI` capturing output formatting.
- Javadoc comments added to `Fishball`, `UI`, `Storage`, and `TaskList`.

#### Why this change

1. Provide ability to search tasks by keyword (whole-word matching).
2. Improve project documentation and readability.
3. Increase confidence with unit tests.

#### How to test locally

```bash
# run unit tests
./gradlew test
# run the application
./gradlew run
```

#### Upcoming features

- [X] 'find' command to find specific tasks - pull up relevant tasks in an instant!
- [ ] GUI (desktop graphical user interface) — provide a visual way to manage tasks.
- [ ] Text-to-speech support — read tasks aloud and provide spoken notifications.

> "Your mind is for having ideas, not holding them." — David Allen (source)

For IntelliJ setup reference see: [JetBrains - Set up JDK](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk)

#### Example usage output

If the user runs `find book` and there are matching tasks, the UI prints:

```
find book
    ____________________________________________________________
     Here are the matching tasks in your list:
     1.[T][X] read book
     2.[D][X] return book (by: June 6th)
    ____________________________________________________________
```

#### Small notes

- The `find` implementation in this project matches whole words only (uses regex word boundaries). For multi-word phrases, use quotes in the command or extend parsing accordingly.
- If you want case-sensitive matching, update the `UI.find` method accordingly.

---