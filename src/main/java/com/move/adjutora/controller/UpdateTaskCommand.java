package com.move.adjutora.controller;

import com.move.adjutora.converter.LocalDateTimeMxConverter;
import com.move.adjutora.model.TaskDto;
import com.move.adjutora.service.TaskService;
import com.move.adjutora.utils.AdjutoraUtils;
import picocli.CommandLine;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.move.adjutora.controller.read.ReadCommandUtility.getDetailTaskView;

@CommandLine.Command(name = "update", mixinStandardHelpOptions = true, version = "1.0",
        description = {"Command that update info for a task "},
        subcommands = {CommandLine.HelpCommand.class})
public class UpdateTaskCommand implements Runnable {

    @CommandLine.Option(names = {"-ti", "--taskId"}, required = true, description = {"TaskId use for update"})
    private long taskId;
    @CommandLine.Option(names = {"-pi", "--parentTaskId"}, description = {"ParentTaskId use for update"})
    private Long parentTaskId;
    @CommandLine.Option(names = {"-t", "--title"}, description = {"Title use for update"})
    private String title;
    @CommandLine.Option(names = {"-d", "--description"}, description = {"Description use for update"})
    private String description;
    @CommandLine.Option(names = {"-cd", "--creationDate"}, description = {"Creation date use for update"}, converter = LocalDateTimeMxConverter .class)
    private LocalDateTime creationdate;
    @CommandLine.Option(names = {"-dud", "--dueDate"}, description = {"Due date use for update"}, converter = LocalDateTimeMxConverter .class)
    private LocalDateTime duedate;
    @CommandLine.Option(names = {"-dod", "--doneDate"}, description = {"Done date use for update"}, converter = LocalDateTimeMxConverter .class)
    private LocalDateTime donedate;

    @Override
    public void run() {
        System.out.println("Updating task " + taskId);
        TaskDto tastToUpdate = new TaskDto(taskId, parentTaskId, title, description, creationdate, duedate, donedate);
        TaskService taskService = AdjutoraUtils.getConfigContext().getTaskService();
        Optional<TaskDto> taskOptional = taskService.updateSingleTask(tastToUpdate);
        if (taskOptional.isPresent()) {
            System.out.println("Successfully updated task " + taskId);
            System.out.println(getDetailTaskView(taskOptional.get()));
        } else {
            System.err.println("Failed to update task " + taskId + " because doesn't exist or all fields are empty or parenttaskid doesn't exist");
        }
    }
}
