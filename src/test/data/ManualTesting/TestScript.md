# Manual Testing

## Load data

Either:<br>

1.

> Double click the jar to start the program.<br>
> Enter `filepath <xml filepath>` to switch to the file path<br>
> Close the program<br>
> Overwrite the xml at the saved filepath with the new data<br>
> Reopen the program

2.

> Create a folder in the same folder as the .jar named 'data'<br>
> Copy the xml into the folder and name in taskbook.xml<br>
> Double click the jar to start the program.<br>

## Help

Open user guide. Can also access by clicking on toolbar > help

`help`

> Opens our user guide.

## Add

Add tasks to the taskbook.

### Invalid format

`add`

> Invalid format, shows help

### Add basic task

`add Task`

> Basic task with name only added

### Add task with params

`add Interview p/high i/Bring documents t/school t/attire`

> Task with all possible params added

### Add dated task with params

`add Interview p/high i/Bring docu1ments t/school t/attire d/today 2pm l/1h r/7d`

> Dated Task with all possible params added

### 3 Types of dated tasks

`add Do work d/tmr 8pm l/3h`<br>
`add Tuition d/5pm de/7pm`<br>
`add Go to school d/tmr 5pm`

> Add tasks with Start date + length, start date + end date, start date only.

### Flexible order of params

`add Interview d/today 2pm l/1h r/7d p/high i/Bring documents t/school`<br>
`add Interview 2 p/high i/Bring documents t/school d/today 2pm l/1h r/7d`

> Order of params don't matter

### No duplicate tasks

`add Go to the gym d/today 2pm l/1h r/7d p/high i/Bring towel t/exercise`<br>
`add Go to the gym d/today 2pm l/1h r/7d p/high i/Bring towel t/exercise`

> Cannot add same task twice

### Different aliases for params

`add Go to school d/today 8am p/vl l/3h`<br>
`add Go to work d/today 9am p/verylow l/3hr`

> Same values added

### Flexible date parsing

`add Task 1 d/today 2pm`<br>
`add Task 2 d/11-05-2016 3:00pm`<br>
`add Task 3 d/November 5th Noon`<br>
`add Task 4 d/Next thursday 1700h`

> Different methods of parsing dates

### Recurrence

`add Go for piano lessons d/yesterday 6pm l/1h r/1w`<br>
`done <index>`

> Close the program, and reopen. Date shifts back by one week

## Edit

### Basic param editing

`add Dinner`<br>
`edit <index> i/New info`<br>
`edit <index> n/New name`<br>
`edit <index> p/high`<br>
`edit <index> d/today 6pm`<br>
`edit <index> l/2h`<br>
`edit <index> r/1d`<br>
`edit <index> t/tag`

> Corresponding info edited

### Converting to dated task

`add Watch a movie`<br>
`edit <index> d/today 2pm l/2h`

> Task becomes dated task

### Field sensitive editing

`add Buy groceries d/today 2pm`<br>
`edit <index> d/3pm`<br>
`edit <index> d/tmr`<br>
`edit <index> d/today 2pm`

> Editing with only a time field modifies only the time field and not the day field, vice versa for day. Editing with both fields changes the entire date.

### All params editing

`add Go for class`<br>
`edit <index> n/Go for tutorial d/today 2pm r/1w p/high i/Do tutorial t/school`

> Task becomes a dated task with all possible params edited. 

### Flexible order of params

`add Go school`<br>
`edit <index> i/Exam d/tmr 4pm n/Go exam hall t/school p/vh`

> Able to edit task in any order of params.

## Delete

### Delete a task

`delete 1`

> Deletes task at index

### Clear

`clear`

> Clears entire task list. Recommended to `undo` after

## Done

`done 1`

> Does task at index

### Already done

`done 1`<br>
`done 1`

> Displays error

## Undone

`undone 1`

> Undoes task at index

### Already undone

`undone 1`<br>
`undone 1`

> Displays error

## Filepath

### Basic file path

`filepath newfile.xml`

> File changed to newfile.xml in same folder<br>
> New xml file created with data<br>
> Config.json updated

### Nested file path

`filepath data\data\newfile.xml`

> File changed to data\data\newfile.xml in same folder

### Explicit file path

`filepath C:\data\newfile.xml`

> File changed to C:\data\newfile.xml

## Find

### Find all tasks

`add Read a book`<br>
`add Buy school materials i/book`<br>
`add Visit the hotel t/book`<br>
`find book`

> Searches name, tags, info

### Find specific scope

`find book s/tag`<br>
`find book s/information s/name`

> Searches in one or more categories

## List

### List all undone

`list`

> Lists all undone tasks

### List in date range

`list ds/15th November`<br>
`list de/15th November`<br>
`list ds/10th November de/20th November`

> List all tasks in range. One ended and two ended searches supported

### List by done undone

`list df/all`<br>
`list df/done`<br>
`list df/not done`

> Lists all done and undone tasks, all done tasks, all undone tasks respectively

### Sort list

`list s/name`<br>
`list s/date`<br>
`list s/priority`

> Lists all tasks sorted by name, date or priority

### Multiple sorts

`list s/priority s/name`

> List all tasks sorted by priority, then by name if priority is equal

### Reverse list

`list s/name rev/`

> Lists all tasks sorted backwards by name

### Multiple fields

`list ds/1st November de/1st December df/all s/priority rev/`

> Lists all done and undone tasks in the month of november sorted reverse by priority

## Reschedule

### Reschedule task

`add Finish project d/tmr 7pm l/2h`<br>
`reschedule <index> 30m`<br>
`reschedule <index> 2h`<br>
`reschedule <index> 1d`<br>
`reschedule <index> 1w`

> Task rescheduled correspondingly. 

### Reschedule non-dated task

`add Finish presentation`<br>
`reschedule <index> 2h`

> Gives an error

## Undo and Redo

### Undo

`add Task d/today 3pm`<br>
`edit <index> i/Hello`<br>
`done <index>`<br>
`undone <index>`<br>
`reschedule <index> 1d`<br>
`delete <index>`<br>
`clear`<br>
`undo`<br>
`undo`<br>
`undo`<br>
`undo`<br>
`undo`<br>
`undo`<br>
`undo`

> All undoable commands

### Redo

`redo`<br>
`redo`<br>
`redo`<br>
`redo`<br>
`redo`<br>
`redo`<br>
`redo`

> Redoes all undos

## Favorites Panel

### Favorite command

`favorite List all undone c/list`

> Creates new command preset with command in the favorites panel (sidebar)

### Run favorites

`favorite <index>`

> Runs the command

### Click favorites

Click on favorite in favorites panel

> Runs the command

### Display favorite command

Hover over any favorite in the favorites panel

> Tool tip shows the command

### Favorite complex commands

`favorite List some tasks c/list ds/1st November de/1st December df/all s/priority rev/`

> Creates new command preset with command that lists all done and undone tasks in the month of november sorted reverse by priority

### Favorite different commands

`favorite Find work tasks c/find work`<br>
`favorite Undo c/undo`<br>
`favorite Redo c/redo`<br>
`favorite Clear c/clear`

> Any command (except the favorite and unfavorite command) can be favorited

### Saved favorites

Close program<br>
Open program

> Favorites are saved in preferences.json

### Default favorites

Close program<br>
Delete preferences.json<br>
Open program

> Favorites are reset to the default

### Unfavorite

`unfavorite <index>`

> Removes favorite from preset

## Exit

`exit`

> Goodbye.
