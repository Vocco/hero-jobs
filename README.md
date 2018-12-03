# hero-jobs
A team project for the PA165 (Enterprise Java) course at the Faculty of Informatics, Masaryk University

### How to run
The project should be compilable by running `mvn clean install` in hero-jobs (root) directory.
To obtain a 'fat' jar file with all dependencies included, you can run `mvn package`.

When building the project, please be patient as the tests may take about a minute to complete.

## Project description
A web application, information system for an employment agency for monster slaying heroes. It keeps:
- a list of available heroes with theis personal data (names, skills, images)
- a library of known monster types (zombies, dragons, basilisks, etc.) and their attributes (size, food, resistance to fire, etc.)
- customer requests (location, number and types of monsters, offered award)
- an archive of jobs (assigned heroes, performance evaluation)

The application helps to find heroes suitable for a new job by matching their skills with monster attributes.

## The team
Seminar group PA165/05, team no. 3.

Members:
- Klang, Metodej (451570)
- ~~Strmen, Jakub (486347)~~
- Pavuk, Michal (487550)
- Krajnansky, Vojtech (423126) - team lead
