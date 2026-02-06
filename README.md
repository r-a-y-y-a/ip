# Fishball (Duke) — IP Project

Fishball is a lightweight, text-based task manager inspired by the Duke project template. It provides simple commands for creating, listing, marking, unmarking, deleting and searching tasks. This repository contains the application source, unit tests, and utilities for storing tasks on disk.

**Status:** work-in-progress

---

## Table of contents

- Overview
- Features
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
- Persist tasks between runs using a local file

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

If you want this PR template to be added as a GitHub PR template file (e.g. `.github/pull_request_template.md`), I can add it for you.
