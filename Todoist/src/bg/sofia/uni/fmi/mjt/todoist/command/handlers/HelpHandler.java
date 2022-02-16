package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;

public class HelpHandler extends CommandHandler {

    public HelpHandler(Command command) {
        super(command, null);
    }

    @Override
    public String execute() {
        return """
                The following commands are supported:
                register <u> <p>                                                      registers a user with username <u> and password <p>
                login <u> <p>                                                         logs in a user with username <u> and password <p>
                add-task name=<n> *date=<d>* *due-date=<dd>* *description=<ds>*       adds a new personal task with name <n> (date <d>, due-date <dd> and description <ds>)
                update-task name=<n> *date=<d>* *due-date=<dd>* *description=<ds>*    updates the due date and/or the description of a task with name <n> (and date <d>)
                set-date name=<n> date=<d>                                            sets a date <d> to a task with name <n>
                remove-date name=<n> date=<d>                                         removes the date <d> from a task with name <n>
                change-date name=<n> date=<d> new-date=<nd>                           changes the date <d> to a <nd> for a task with name <n>
                delete-task name=<n> *date=<d>*                                       deletes a personal task with name n (and date <d>)
                get-task name=<n> *date=<d>*                                          prints information about a personal task with name n (and date <d>)     
                list-tasks *date=<d>* *completed=true/false*                          prints information about personal tasks (which are/aren't completed)  
                list-dashboard                                                        prints information about all the tasks for the day
                finish-task name=<n> *date=<d>*                                       marks a task with name <n> (and date <d>) as completed
                add-collaboration name=<n>                                            adds a collaboration with name <n>
                delete-collaboration name=<n>                                         deletes a collaboration with name <n>
                list-collaborations                                                   prints information about all collaborations
                add-user collaboration=<c> user=<u>                                   adds a user <u> as a collaborator of collaboration <c>
                assign-task collaboration=<c> user=<u> task=<t>                       assigns task <t> from collaboration <c> to user <u>
                list-collaboration-tasks name=<n>                                     print information about all the tasks in collaboration with name <n>
                list-collaboration-users name=<n>                                     print information about all the users in collaboration with name <n>
                
                NB! Commands 'add-task', 'update-task', 'set-date', 'remove-date', 'change-date', 'delete-task', 'get-task' and 'finish-task' also work for tasks in collaborations. You just have to add an optional argument collaboration=<c> where <c> is the name of the collaboration.
                NB! If any of the arguments consists of more than one word, please put it in quotes.
                NB! Arguments, surrounded by stars, are optional.
                """;
    }
}
