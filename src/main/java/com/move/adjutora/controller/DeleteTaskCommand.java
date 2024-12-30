package com.move.adjutora.controller;

import com.move.adjutora.model.TaskDto;
import com.move.adjutora.service.TaskService;
import com.move.adjutora.utils.AdjutoraUtils;
import picocli.CommandLine;

import java.util.Optional;

import static com.move.adjutora.controller.read.ReadCommandUtility.getDetailTaskView;

@CommandLine.Command(name = "delete", mixinStandardHelpOptions = true, version = "1.0",
        description = {"Command that delete a task "},
        subcommands = {CommandLine.HelpCommand.class})
public class DeleteTaskCommand implements Runnable {

    @CommandLine.Option(names = {"-ti", "--taskId"}, required = true, description = {"TaskId use for update"})
    private long taskId;

    @Override
    public void run() {
        System.out.println("Deleting task " + taskId);
        TaskService taskService = AdjutoraUtils.getConfigContext().getTaskService();

        Optional<TaskDto> deleteTask = taskService.deleteSingleTask(taskId);
        if (deleteTask.isPresent()) {
            System.out.println("Successfully deleted task " + taskId);
            System.out.println(getDetailTaskView(deleteTask.get()));
        } else {
            System.err.println("Failed to delete task " + taskId + " because it does not exist or has children");
        }
    }
}
