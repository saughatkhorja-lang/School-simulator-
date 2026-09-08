# School-simulator-

A Java-based school simulation developed as part of my Programming coursework at the University of Southampton.

## Project Overview

This project models the day-to-day operation of a school, including students, instructors, subjects and courses. The simulation manages course enrolment, instructor allocation, student progression and changes to the school population over time.

The project was designed to develop my understanding of object-oriented programming, data structures, simulation logic and file handling in Java.

## Features

- Models students, instructors, subjects and courses using separate classes.
- Uses inheritance and polymorphism to represent different instructor types and teaching specialisms.
- Simulates the admission of new students and instructors.
- Allocates instructors and enrols eligible students onto courses.
- Tracks course progression, completion and cancellation.
- Awards certificates when students complete courses.
- Simulates students and instructors leaving the school.
- Saves simulation state to a text file and loads previously saved simulations.
- Provides a command-line interface for running the simulation for a specified number of days.

## Technologies

- Java
- Java Collections Framework
- File I/O
- Object-Oriented Programming

## Programming Concepts

- Classes and objects
- Inheritance and polymorphism
- Abstract classes and method overriding
- Encapsulation
- ArrayLists and arrays
- Conditional logic and loops
- Randomised simulation
- File reading and writing
- Exception handling

## Project Structure

- `Administrator.java` — controls the simulation, admissions, departures, and saving/loading.
- `School.java` — manages students, instructors, subjects and courses.
- `Course.java` — handles enrolment, instructor assignment and course progression.
- `Student.java` — represents students and their certificates.
- `Instructor.java` — abstract base class for instructor types.
- `Teacher.java`, `Demonstrator.java`, `OOTrainer.java`, `GUITrainer.java` — specialised instructor implementations.
- `Person.java` — base class for people in the simulation.
- `Subject.java` — represents subjects and their teaching requirements.

## How to Run

1. Clone the repository.
2. Open the project in a Java IDE such as IntelliJ IDEA.
3. Compile the Java source files.
4. Run `Administrator` with the required input filename and number of simulation days.

Example:

```bash
java Administrator <input-file> <number-of-days>
