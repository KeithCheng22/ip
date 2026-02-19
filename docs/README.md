# Keef User Guide

Keef is a modified version of Duke Chatbot as part of CS2103T's individual project. 
The chatbot has a chill personality, and supports the following features:

1. Adding and deleting a task (todo, deadline and events)
2. Marking and unmarking tasks
3. Storing / loading tasks in / from a text file
4. Finding a specific task
5. Batch operations (eg. deleting / marking / unmarking multiple tasks)

**Getting Started**

Upon bootup, Keef will send a greeting message. The user may then interact with the chatbot via some specific commands.

<p>
<img width="417" height="628" alt="Screenshot 2026-02-19 at 2 43 08 PM" src="https://github.com/user-attachments/assets/3d53dd50-8334-45b5-8b38-249b95ff3015" />
</p>

**Command Validation**

Texts that do not fall under the list of commands will be rejected.

<p>
<img width="417" height="630" alt="Screenshot 2026-02-19 at 2 45 04 PM" src="https://github.com/user-attachments/assets/ad46bfb5-e23c-4266-8b27-d4934649071b" />
</p>

## Quick Start
If you're already familiar with CLI tools, here is a cheat sheet of the most common commands to get you started:

```
# Add tasks
todo CS2109S Lecture
deadline submit MA2202 Homework 2 /by 11/2/2026 1800
event CS2103T meeting /from 17/2/2026 1400 /to 18/2/2026 1600

# Manage tasks
list
mark 1
unmark 1
delete 2
find CS

# Exit
bye
```
## Detailed Guide
* Features
  * [`bye`](#bye) -- to exit the application
  * [`list`](#list) -- prints out the list of tasks
  * [`todo`](#todo) -- adds a todo task
  * [`deadline`](#deadline) -- adds a deadline task
  * [`event`](#event) -- adds an event task
  * [`delete`](#delete) -- deletes a task
  * [`mark`](#mark) -- marks a task
  * [`unmark`](#unmark) -- unmarks a task
  * [`find`](#find) -- prints out the list of tasks that contain a specific keyword
* [Saving and loading data](#saveload)
* [Troubleshooting & Error Messages](#troubleshooting)

---

<a name="bye"></a>
### bye
Keef will immediately close the application.
* **Format:** `bye`

<a name="list"></a>
### list
Displays all current tasks in your list, including their status and type.
* **Format:** `list`

<p>
<img width="419" height="246" alt="Screenshot 2026-02-19 at 3 24 17 PM" src="https://github.com/user-attachments/assets/50a6dacb-43ed-4050-b4c9-0ecb4df90c53" />
</p>

<a name="todo"></a>
### todo
Adds a basic task without any specific date or time.
* **Format:** `todo <description>`
* **Example:** `todo laundry`

<p>
<img width="419" height="242" alt="Screenshot 2026-02-19 at 3 23 22 PM" src="https://github.com/user-attachments/assets/cb67def5-fe5d-4bf8-9575-9657ca9922ef" />
</p>

<a name="deadline"></a>
### deadline
Adds a task that needs to be done by a specific date and time.
* **Format:** `deadline <description> /by <dd/MM/yyyy HHmm>`
* **Example:** `deadline MA2202 Homework 2 /by 20/12/2026 1830`

<p>
<img width="418" height="239" alt="Screenshot 2026-02-19 at 3 24 44 PM" src="https://github.com/user-attachments/assets/61b1eab0-d960-4c05-9217-1ae46c3174b3" />
</p>

<a name="event"></a>
### event
Adds a task that occurs within a specific time frame.
* **Format:** `event <description> /from <dd/MM/yyyy HHmm> /to <dd/MM/yyyy HHmm>`
* **Example:** `event CS2103T project meeting /from 20/12/2026 1830 /to 20/12/2026 2030`

<p>
<img width="417" height="238" alt="Screenshot 2026-02-19 at 3 27 01 PM" src="https://github.com/user-attachments/assets/6c7923a0-5a7d-4b6e-8850-1e5afe293cde" />
</p>

<a name="delete"></a>
### delete
Removes task(s) from the list using their 1-based index numbers.
* **Format:** `delete <index>`  
  You can also specify multiple indices, a range, or all tasks:
  - **Single task:** `delete 2`  
  - **Multiple tasks:** `delete 1 3 5`  
  - **Range of tasks:** `delete 2-5`  
  - **All tasks:** `delete all`

<p>
<img width="418" height="243" alt="Screenshot 2026-02-19 at 3 28 28 PM" src="https://github.com/user-attachments/assets/a0865e3a-b33b-439f-871b-1c8df5759c1d" />
</p>

<a name="mark"></a>
### mark
Marks a specific task as completed.
* **Format:** `mark <index>`  
  You can also specify multiple indices, a range, or all tasks:
  - **Single task:** `mark 2`  
  - **Multiple tasks:** `mark 1 3 5`  
  - **Range of tasks:** `mark 2-5`  
  - **All tasks:** `mark all`

<p>
<img width="418" height="245" alt="Screenshot 2026-02-19 at 3 27 40 PM" src="https://github.com/user-attachments/assets/f5c89e66-2d82-4e34-8edc-986dc9a9b678" />
</p>

<a name="unmark"></a>
### unmark
Reverts a completed task back to "not done".
* **Format:** `unmark <index>`  
  You can also specify multiple indices, a range, or all tasks:
  - **Single task:** `mark 2`  
  - **Multiple tasks:** `mark 1 3 5`  
  - **Range of tasks:** `mark 2-5`  
  - **All tasks:** `mark all`

<p>
<img width="417" height="239" alt="Screenshot 2026-02-19 at 3 27 57 PM" src="https://github.com/user-attachments/assets/43ef4ac5-6862-4f5c-9673-eb8f931c764e" />
</p>

<a name="find"></a>
### find
Searches for tasks that contain the specified keyword in their description.
* **Format:** `find <keyword>`
* **Example:** `find CS`

<p>
<img width="418" height="244" alt="Screenshot 2026-02-19 at 3 28 13 PM" src="https://github.com/user-attachments/assets/fdab59b4-8ee0-41c5-ab4b-ed5d4b9e6879" />
</p>

#### Usage Notes:
* Index Range: The index must be a positive integer within the range of the current list, and applies to the `delete`, `mark` and `unmark` commands (e.g., if you have 5 tasks, delete 6 will fail).

<a name="saveload"></a>
## Saving and Loading Data
You don't need to manually save your progress. 
Keef automatically saves your state every time you add, delete, mark, unmark a task.

### Storage Location
When you run the application, Keef creates a `/data` folder in the same directory as your `.jar` file. Your data is placed into a  **`keef.txt`** file

### File Structure
```text
.
├── keef.jar
└── data/
    ├── keef.txt
 ``` 

### Automatic Loading
Upon startup, Keef automatically scans the /data folder. If existing files are found, your previous tasks are restored instantly. If no files exist, Keef starts with a clean slate.

>[!WARNING]
Manual Editing: While these are text files, manual editing is discouraged. If the formatting is corrupted, Keef may be unable to load your tasks correctly!

<a name="troubleshooting"></a>
## Troubleshooting & Error Messages
If you provide an invalid input, Keef will let you know.

Refer to the table below to fix common issues:

| Issue | Typical Error Message | How to Fix                                                     |
| :--- | :--- |:---------------------------------------------------------------|
| **Invalid Command** | "Huh, what do you mean?" | Command is invalid. Refer to the list of valid commands.        |
| **Empty Todo Description** | "Bro, you left out what exactly you wanted to do! Add something!" | Ensure text follows the command (e.g., `todo laundry`).   |
| **Empty Deadline Description** | "Bro, the description and date can't be empty!" | Ensure text follows the command (e.g., `deadline MA2202 Homework 2 /by 20/12/2026 1830`).   |
| **Empty Deadline Description** | "Bro, the event description, start and end date cannot be empty!" | Ensure text follows the command (e.g., `event CS2103T meeting /from 17/2/2026 1400 /to 18/2/2026 1600`).   |
| **Missing Tag For Deadlines** | "Bro, you must include /by <date>" | Include the mandatory prefix for deadlines.         |
| **Missing Tag For Events** | "Bro, you must include /from <start> and /to <end>" | Include the mandatory prefix for events.         |
| **Invalid Index** | "Uhm bro, you only have x task(s) in your list." | Use a number corresponding to an existing item in your `list`. |
| **Wrong Date Format** | "Use date format: d/M/yyy HHmm" | Use the **dd/MM/yyyy HHmm** format.       |
