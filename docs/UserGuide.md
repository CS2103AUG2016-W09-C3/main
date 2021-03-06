# User Guide

* [About](#about)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## About

   Always forgetting what to do? Have difficulties in managing your hectic timetable? If you want to plan your day efficiently, then we have just the app for you!<br>
   <br>
   ToDoIt is a task manager that keeps your daily activities in check. 
   It will notify you of all your upcoming activities for the day or the week.<br>
   <br>
   ToDoIt has a simple command-line based interface which caters to people who are more comfortable with typing instead of navigating complex user interfaces. However that is not to say ToDoIt is not for beginners, the interface is simple and commands are intuitive; beginners can start using it immediately.<br>
   <br>
   Unlike other complex task managers, ToDoIt is a minimalistic task manager where all your tasks can be managed effortlessly with just one command. In addition, it can function as a calendar to help you keep track of your schedule.<br>
   <br>
   ToDoIt has a simple command-line based interface which caters to people who are more comfortable with typing instead of navigating complex user interface. However, that is not to say ToDoIt is only for advanced users. With a simple interface and intuitive commands, beginners can also use the application with ease.<br>
   <br>
   This user guide will demonstrate the various commands that ToDoIt has to offer, so that you, as a user, can manage your daily activities.<br>
   <br>
   Are you ready? Let's get started!<br>

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `ToDoIt.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your ToDoIt task file.
3. Double-click the ToDoIt.jar to start the app. The GUI should appear in a few seconds. 
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Meeting with John d/tomorrow l/2h p/medium i/Meeting with John regarding sales` : 
     adds a task named `Meeting with John` to the task list.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>



## Features

<img src="images/prototype_ui_labelled.png" width="600"><br>

Ensure you've followed the quick start guide to downloading this program. Simply double click the app to run it. As you can see, the prototype User Interface will be shown above.

When the program is started, you will see the following:

1. The command box. This is where commands are entered. Simply type the command you want to execute, and press enter to execute it.
2. The result summary. Once a command is entered, relevant information will be logged in this box. You can view it to ensure the command has been executed correctly.
3. The task panel. This displays the tasks you have entered. Tasks retrieved from the `list` or `find` command will also be reflected here.
4. The presets panel. This displays a list of presets specified by the user. Clicking on one of them will run the corresponding command. 

###Command Format
* Each command consists of a command word (such as find, or help), followed by additional options. An option is a word or character followed by a forward slash (e.g. p/, d/, etc).
* An option may require additional data, specified after the forward slash (e.g. desc/Go to Work). `UPPER_CASE` words in the format describe the data to input.
* Options in `SQUARE_BRACKETS`"[ ]" are optional.
* Options with `...` after them can be specified multiple times (e.g. `t/Work t/School t/CS2103`).
* The order of options to specify for each command is not fixed after the task name. For example, `add Go to school p/high i/Tutorial class` is the same as `add Go to school i/Tutorial class p/high`. 

<!-- @@author A0140155U -->

### Date Specifications

ToDoIt accepts most common formats of dates. Some examples include:

* 2015-12-31
* 2015/12/31
* 12/31/2015
* 31st Dec 2015
* 31st of December
* Dec 31st
* Next Thursday 2pm
* Last Wednesday 0500h
* Tomorrow 5:30am
* 3 days from now

For a full list of accepted formats, please refer to:
http://natty.joestelmach.com/doc.jsp

<!-- @@author -->
---

<!-- @@author A0139947L -->
### Viewing help : `help`
Format: `help`

The help command links you to our User Guide to provide you with detailed explaination on how to use ToDoIt.

**Notes**
> * Help is also shown if you enter an incorrect command e.g. `abcd`
<!-- @@author -->

---

<!-- @@author A0139121R -->
### Adding a task: `add`
Format: `add TASK_NAME [d/DATE_TIME de/END_DATE_TIME l/LENGTH] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...`

ToDoIt compiles your tasks for the day efficiently with a simple line of text. The `add` command adds your tasks to the to do list, allowing you to view them any time you want.

**Options**
> 1. `d/` Date and time: Specifies the start date and time of a task. Please refer to the Date Specifications section for accepted formats.
> 2. `de/` End date and time: Specifies the end date and time of the task. Please refer to the Date Specifications section for the accepted formats. This parameter should not be used in conjunction with `l/`.
<!-- @@author A0139947L -->
> 3. `l/` Length: Specifies the length of time. Defaults to 1 hour if time and date are specified, but length is not specified. Use a number followed by a time interval (`m`, `h`, `d`, `w`, for minutes, hours, days, weeks respectively), e.g. `6d`, `1w`. You may also use `hr`, `hrs`, `day`, `days`, `week` and `weeks` for their respective interval. This parameter should not be used together with `de/`.
> 4. `r/` Recur: Specifies an interval for recurring task, if any. Use a number followed by a time interval (`m`, `h`, `d`, `w`, for minutes, hours, days, weeks respectively), e.g. `6d`, `1w`. You may also use `hr`, `hrs`, `day`, `days`, `week` and `weeks` for their respective interval. 
> 5. `p/` Priority: Specifies the priority of a task (`veryhigh`, `high`, `medium` `low`, `verylow`). You may also use `vh`, `h`, `m`, `l` or `vl` for their respective priorities.
<!-- @@author -->
<!-- @@author A0139121R -->
> 6. `i/` Information: Information to be tagged to this task. Put any extra details you want here.
> 7. `t/` Tags: Specifies tags for this task. Tags allow you to group tasks logically by assigning them a similar tag.

**Notes**
> * A task can be dated (has time, date, length), or floating. A floating task is one without any specified date, time and length which can be done at a flexible timing.<br> 
> * If a task is dated (d/ option specified) but length is not specified, the duration defaults to 1 hour.<br> 
> * Tasks can have any number of tags (including 0). Simply repeat the t/ option (e.g. `t/work t/school t/CS2103`).<br>
> * End date and time should not be used in conjunction with length.
 
**Example**
> * You have a CS2101 Lecture weekly, starting from 7th Oct at 2pm. However, the lecture is webcasted so you don't always have to attend, thus making it low priority. You simply have to run: <br>
>   `add CS2101 Lecture h/7th Oct 2pm l/2hr r/1w p/low t/webcast` <br>
>   This will add a CS2101 Lecture task which recurs every week starting with 2pm on 7th Oct, marks it as low priority and tags it with the `webcast` tag.

<!-- @@author -->

---
<!-- @@author A0139121R -->

### Listing tasks : `list`
ToDoIt displays your tasks easily with a simple command. The tasks to list can be customized to your liking simply with a few keywords.<br>
Format: `list [ds/DATE_START] [de/DATE_END] [s/SORT_BY] [df/DONE_STATUS] [rev/]`

**Options**
> 1. `ds/` Date start: If a start date is specified, program will only display tasks after this date.
> 2. `de/` Date end: If an end date is specified, program will only display tasks before this date.
> 3. `s/` Sort by: Sorts the tasks in the order specified (`date` and `time`, `alphabetical`, `priority`).
> 4. `df/` Done status: Use this flag to specify, by done status, which tasks to show. The available done flags are "done", "not done" and "all".
> 5. `rev/` Reverse: If this flag is specified, tasks will be listed in reverse order after sorting.

**Notes**
> * For start and end dates, please refer to Date Specifications section for what formats are accepted.
> * Tasks marked done will be hidden by default. To view done tasks, include the df/all or df/done option.

**Example**
> * You want to view all upcoming tasks ordered by date, so you know what needs to be done. <br>
>   `list ds/today s/date`
> * You want to see all the tasks you have completed in the past year, to celebrate what you've done with your life. <br>
>   `list ds/1st Jan 2016 de/today df/done`

---

### Finding all tasks containing a keyword: `find`
ToDoIt searches and finds the tasks you need, while filtering out the clutter. Keep your mind focused on what is important.<br>
Format: `find KEYWORD [MORE_KEYWORDS] [s/SCOPE]...`

**Options**
> 1. `s/` Scope: If you only want to search a task's name or description, specify which attributes you want to search here (`name` : name, `tag` : tag, `information` : information). By default, it searches in all three areas if no attribute is specified.

**Notes**
> * The search is not case sensitive. e.g `stuff` will match `STUFF`<br> 
> * The order of the keywords does not matter. e.g. `Do stuff` will match `Stuff do`<br> 
> * Only full words will be matched e.g. `Work` will not match `Workout`<br> 
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
    e.g. `Stuff` will match `Do stuff`<br>
> * You can specify more than one search scope (`s/name s/information`). All areas specified will be searched.
> * If no search scope parameter is provided, all attributes (name, information, tag) are searched by default

**Example**
> * You remember naming a task `meeting`, and you want to look for it. However, you don't want to bring up unnecessary tasks so you search for task names only. <br>
>   `find meeting s/name`<br>
> * You want to find all tasks that relate to your family. <br>
>   `find brother sister mother father family`
<!-- @@author -->

---
  
### Deleting a task : `delete`
Some say that cancelled plans are the introvert's source of joy. Simply remove tasks you do not need with a simple command.<br>
Format: `delete INDEX`

**Notes**
> * This deletes the task at the specified `INDEX`. <br>
    The index refers to the index number shown in the most recent listing.<br>
    The index **must be a positive integer** `1`, `2`, `3`, ...
> * Use find or list command to display tasks and use the order numbering as Index.

**Example**
> * Having displayed all tasks, you want to remove the one in the second position. 
>   `list`<br>
    `delete 2`<br>
> * After retrieving all work related tasks, you want to delete the first one. 
>   `find work`<br> 
    `delete 1`<br>
    Deletes the 1st task in the results of the `find` command.

---

### Clear all tasks : `clear`
Sometimes you just have to start afresh. Remove everything in your task list with one command.<br>
Format: `clear`

<!-- @@author A0139046E -->
---

### Editing a task: `edit`
You will never know when things might change. Life is unpredictable. ToDoIt knows this, and lets you adapt to the erratic changes you may have in your schedule. Modify your tasks with a simple command.<br>
Format: `edit INDEX [n/TASK_NAME] [d/DATE_TIME] [l/LENGTH] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...` 

**Options**
> 1. `n/` Name: Edits the name of the task.
> 2. `d/` Date and time: Edits the start date and time of a task. Please refer to the Date Specifications section for what formats are accepted.
<!-- @@author A0139947L -->
> 3. `l/` Length: Edits the length of time. Use a number followed by a time interval (`m`, `h`, `d`, `w`, for minutes, hours, days, weeks respectively), e.g. `6d`, `1w`. You may also use `hr`, `hrs`, `day`, `days`, `week` and `weeks` for their respective interval.
> 4. `r/` Recur: Edits the interval for recurring task, if any. Use a number followed by a time interval (`m`, `h`, `d`, `w`, for minutes, hours, days, weeks respectively), e.g. `6d`, `1w`. You may also use `hr`, `hrs`, `day`, `days`, `week` and `weeks` for their respective interval.
> 5. `p/` Priority: Edits the priority of a task (`veryhigh`, `high`, `medium` `low`, `verylow`). You may also use `vh`, `h`, `m`, `l` or `vl` for their respective priorities.
<!-- @@author -->
<!-- @@author A0139046E -->
> 6. `i/` Information: Edits information of this task.
> 7. `t/` Tags: Edits tags that are tagged with the task.

**Notes**
> * This edits the task at the specified `INDEX`. <br>
    The index refers to the index number shown in the most recent listing.<br>
    The index **must be a positive integer** `1`, `2`, `3`, ... <br>
> * Replaces the current task data with the specified task data.<br>
> * When editing a task's date and time, if only the date or the time is specified, then only the date or the time will be edited. For example, including `d/6th Oct` a task on 5th Oct 2pm will change the date to 6th Oct 2pm.<br>
> * For more information regarding the options, refer to `add` command.

**Example**
> * Your boss just changed the date of a meeting on the 3rd October to the 2nd. Simply find your meeting task, then run the following command: <br>
>   `find meeting`<br> 
    `edit 1 d/2nd Oct`
<!-- @@author -->

---

<!-- @@author A0139046E -->  

### Rescheduling a task: `reschedule`
ToDoIt does not expect you to be the perfect worker. When you do not feel up to the task right now, simply reschedule it to another time.<br>
Format: `reschedule INDEX INTERVAL` 

**Notes**
> * This reschedules the task at the specified `INDEX`.  <br>
    The index refers to the index number shown in the most recent listing.<br>
    The index **must be a positive integer** `1`, `2`, `3`, ... <br>
> * For interval, use a number followed by a time interval (`m`, `h`, `d`, `w`, for minutes, hours, days, weeks respectively), e.g. `6d`, `1w`. You may also use `hr`, `hrs`, `day`, `days`, `week` and `weeks` for their respective interval.
> * Negative numbers are not supported. To reschedule to an earlier time, consider using `edit` instead.
 
**Example**
> * You need to get some homework done, but you just got back from school and you need a break. Simply find your homework task, then run the following command: <br>
>   `find homework`<br> 
    `reschedule 1 1h`

<!-- @@author -->

<!-- @@author A0140155U -->
---
  
### Mark a task as done : `done`
Hooray! You've just completed a task! Celebrate your accomplishment by marking it as done.<br>
Format: `done INDEX`

**Notes**
> * This marks task at the specified `INDEX` as done. <br>
    The index refers to the index number shown in the most recent listing.<br>
    The index **must be a positive integer** `1`, `2`, `3`, ...<br>
> * Marking a task as done will stop it from showing up in any `list` command, unless the `\df` option is used.
 
**Example**
> * You just completed your homework. Simply find your homework task, then run the following command: <br>
>   `find homework`<br> 
    `done 1`

---
  
### Mark a task as undone : `undone`
Thought you were done, but there is one little thing that you forgot to do? No worries, we have all been there. Use this simple command to mark done tasks as not done. <br>
Format: `undone INDEX`

**Notes**
> * This marks task at the specified `INDEX` as undone. <br>
    The index refers to the index number shown in the most recent listing.<br>
    The index **must be a positive integer** `1`, `2`, `3`, ...<br>
 
**Example**
> * You accidentally miscounted and marked the wrong task as done. Simply revert it with the following command: <br>
>   `done 3 (mistake)`<br> 
    `undone 3`

---
  
### Undo a command : `undo`
Made a mistake? No worries, ToDoIt is forgiving. Undo your previous commands with this simple command.<br>
Format: `undo`

**Notes**
> * The program can only undo up to the 10 previous commands.

---
  
### Redo a command : `redo`
Made a mistake while undoing your mistake? Redo your `undo` commands with this simple command.<br>
Format: `redo`

---

### Changing filepath : `filepath`
Always on the go? So is ToDoIt. Move your saved files quickly with this command.<br>
Format: `filepath FILEPATH`

**Notes**
> * The saved file refers to an .xml file on your drive that stores saved task data.
> * The `FILEPATH` must be a valid file path on your drive that ends with .xml. Some examples include `file.xml`, `data/file.xml` or `C:/data/file.xml`.
 
**Example**
> * Your current file is stored at data\taskbook.xml. You want to move your saved file from data\taskbook.xml to data\todoit.xml. Simply run the command<br>
>   `filepath data\todoit.xml`

---

### Mark as favorite: `favorite`
In any task, efficiency is key. Save yourself from typing the same command over and over again by creating a command preset.<br>
Format: `favorite DESCRIPTION c/COMMAND` or `favorite INDEX`

**Options**
> 1. `c/` Command: The command associated with the preset.

**Notes**
> * The preset will be added to the preset panel on the left side of the screen. Clicking on the preset will run the specified command.
> * The command can be any executable command (e.g. `list`, `add`, `clear`, `undo`).
> * The favorite command can also be used with an integer `INDEX` to select the corresponding favorite preset. This gives the same result as clicking it.

**Example**
> * find yourself constantly having to list only done tasks, so you decide to create a preset for it:<br>
>   `favorite List only done tasks c/list df/done`<br>
> * This adds a new preset named ‘List only done tasks’ to the preset panel. <br>

---
  
### Unfavorite: `unfavorite`
You can’t work with too many saved presets cluttering up the presets panel. Use this command to remove your saved presets.<br>
Format: `unfavorite INDEX`

**Options**
> 1. `c/` Command: The command associated with the preset.

**Notes**
> * This marks task at the specified `INDEX` as undone. <br>
    The index refers to the index number shown on the preset listing.<br>
    The index **must be a positive integer** `1`, `2`, `3`, ...<br>


**Example**
> * Having added the preset previously, you’ve now changed your mind. Assuming the preset is numbered 8, remove it by running the following command:<br>
>   `unfavorite 8`<br>

<!-- @@author -->
---

<!-- @@author A0139947L -->

### Taking in different aliases:
ToDoIt also takes in different aliases for both time and priority. Instead of typing the respective command, it also recognises the aliases in the tables below.

The aliases below work for `add`, `edit`, `reschedule`, or any command that requires the input or change or time/date/priority.

Command (Date & Time) | Aliases
-------- | :-----------
m (minutes) | min, mins
h (hours) | hr, hrs
d (days) | day, days
w (weeks) | week, weeks

Command (Priority) | Aliases
-------- | :-----------
veryhigh | vh
high | h
medium | m
low | l
verylow | vl

---

### Exiting the program: `exit`
ToDoIt is sad to see you go.<br>
Format: `exit`  
<!-- @@author -->

---

<!-- @@author A0139121R -->
## FAQ

**Q**: How do I access help?<br>
**A**: Type "help" in the command line of the program and press 'enter' on keyboard.<br>
       <br>
       
**Q**: Why does the program fail to start?<br>
**A**: Ensure that your system meets the requirements stated in the quick start section
	   and that the downloaded program file is not corrupted.<br>
       <br>
       
**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Task Book folder.<br>
       <br>
       
**Q**: How do I uninstall ToDoIt?<br>
**A**: Just delete ToDoIt.jar to remove the program from your computer. 
	   You can also delete the text that stores the task.<br>
       <br>
       
**Q**: Do I require knowledge of command line to use this program?<br>
**A**: No, there is no prior command line knowledge required to use ToDoIt.
	   Instead, just follow the instructions given in the help. See access help faq.<br>
       <br>

<!-- @@author -->

<!-- @@author A0139947L -->

## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME [d/DATE_TIME de/END_DATE_TIME l/LENGTH] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...`
Edit | `edit INDEX [n/TASK_NAME] [d/DATE_TIME] [l/LENGTH] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...` 
Clear | `clear`
Delete | `delete INDEX`
Done | `done INDEX`
Undone | `undone INDEX`
List | `list [ds/DATE_START] [ds/DATE_END] [s/SORT_BY] [df/] [rev/]`
Reschedule | `reschedule INDEX INTERVAL`
Find | `find KEYWORD [MORE_KEYWORDS] [s/SCOPE]...`
Undo | `undo`
Redo | `redo`
Filepath | `filepath FILEPATH`
Favorite | `favorite c/COMMAND` (add a favorite)<br>`favorite INDEX` (select a favorite)
Unfavorite | `unfavorite INDEX`
Help | `help`
Exit | `exit`
<!-- @@author -->