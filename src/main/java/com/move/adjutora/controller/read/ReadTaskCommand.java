package com.move.adjutora.controller.read;


import com.move.adjutora.controller.CliCommands;
import com.move.adjutora.model.TaskCollection;
import com.move.adjutora.model.TaskViewType;
import com.move.adjutora.service.TaskService;
import com.move.adjutora.utils.AdjutoraUtils;
import picocli.CommandLine;

import static com.move.adjutora.controller.read.ReadCommandUtility.getTaskView;
import static com.move.adjutora.controller.read.ReadCommandUtility.printTaskTreeCollection;

@CommandLine.Command(name = "read", mixinStandardHelpOptions = true, version = "1.0",
        description = {"Command that read info for task "},
        subcommands = {CommandLine.HelpCommand.class})
public class ReadTaskCommand implements Runnable {

    @CommandLine.Option(names = {"-apu", "--allParentUndone"}, description = {"Recover all task that are not done and are parent task"})
    private boolean allUndone;
    @CommandLine.Option(names = {"-apuc", "--allParentUndoneWithChild"}, description = {"Recover all task that are not done with their children"})
    private boolean allUndoneWithChild;
    @CommandLine.Option(names = {"-at", "--all"}, description = {"Recover all task with their children"})
    private boolean allTask;
    @CommandLine.Option(names = {"-tv", "--taskViewTyp"}, description = {"Set task view"})
    private TaskViewType showTaskViewType = TaskViewType.SHORT;


    @CommandLine.ParentCommand
    CliCommands parent;

    @Override
    public void run() {
        TaskService taskService = AdjutoraUtils.getConfigContext().getTaskService();
        if (allUndone) {
            System.out.println("#--------------------------------------------#");
            System.out.println(" Printing all parent tasks that are not done ");
            System.out.println("#--------------------------------------------#");
            TaskCollection taskCollection = taskService.getAllParentTasksWithoutChildrenAndUndone();
            taskCollection.getTasks().forEach(task -> System.out.printf(getTaskView(task.getNode()) + "\n"));
        }

        if (allUndoneWithChild) {
            System.out.println("#---------------------------------------------------------------#");
            System.out.println(" Printing all parent tasks that are not done with their children ");
            System.out.println("#---------------------------------------------------------------#");
            TaskCollection taskCollection = taskService.getAllParentTasksWithChildrenAndUndone();
            System.out.println(printTaskTreeCollection(taskCollection, showTaskViewType));
        }

        if (allTask) {
            System.out.println("#---------------------------------------------------------------#");
            System.out.println("                    Printing all tasks                           ");
            System.out.println("#---------------------------------------------------------------#");
            TaskCollection taskCollection = taskService.getAllTasks();
            System.out.println(printTaskTreeCollection(taskCollection, showTaskViewType));
        }
    }


}
