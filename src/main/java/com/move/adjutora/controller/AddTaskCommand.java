package com.move.adjutora.controller;

import com.move.adjutora.converter.LocalDateTimeMxConverter;
import com.move.adjutora.model.database.Task;
import com.move.adjutora.service.TaskService;
import com.move.adjutora.utils.AdjutoraUtils;
import picocli.CommandLine;

import java.time.LocalDateTime;

@CommandLine.Command(name = "add", mixinStandardHelpOptions = true, version = "1.0",
        description = {"Command that add info for pending task "},
        subcommands = {CommandLine.HelpCommand.class})
public class AddTaskCommand implements Runnable {

    @CommandLine.Option(names = {"-t", "--title"}, required = true, description = {"Title for task"})
    private String title;
    @CommandLine.Option(names = {"-d", "--description"}, description = {"Description for task"})
    private String description;
    @CommandLine.Option(names = {"-du", "--dueDate"}, description = {"Due date for task"}, converter = LocalDateTimeMxConverter.class)
    private LocalDateTime dueDate;
    @CommandLine.Option(names = {"-pt", "--parentTaskId"}, description = {"ParentTaskId for task"})
    private Long parentTaskId;

    @Override
    public void run() {
        TaskService taskService = AdjutoraUtils.getConfigContext().getTaskService();
        Task task = new Task(null, parentTaskId, title, description, null, dueDate, null);
        taskService.insertSingleTask(task);


    }
}
