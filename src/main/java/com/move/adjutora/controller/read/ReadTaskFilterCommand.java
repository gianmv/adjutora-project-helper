package com.move.adjutora.controller.read;

import com.move.adjutora.converter.LocalDateTimeMxConverter;
import com.move.adjutora.model.TaskCollection;
import com.move.adjutora.model.TaskTimeField;
import com.move.adjutora.model.TaskViewType;
import com.move.adjutora.model.TimeClause;
import com.move.adjutora.utils.AdjutoraUtils;
import picocli.CommandLine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.move.adjutora.controller.read.ReadCommandUtility.printTaskTreeCollection;

@CommandLine.Command(name = "readFilter", mixinStandardHelpOptions = true, version = "1.0",
        description = {"Command that read info for task and filter it"},
        subcommands = {CommandLine.HelpCommand.class})
public class ReadTaskFilterCommand implements Runnable {

    @CommandLine.Option(names = {"-d", "--doneStatus"}, description = {"Recover all task that done status is de parameter"})
    private boolean doneStatus;
    @CommandLine.Option(names = {"-td", "--time"}, required = true, description = {"Recover all task with specified time"})
    private TimeClause timeClause;
    @CommandLine.Option(names = {"-tt", "--taskTimeField"}, description = {"Set field for look time"})
    private TaskTimeField taskTimeField;
    @CommandLine.Option(names = {"-ldt", "--localdatetime"}, description = {"Recover task with respective time"}, converter = LocalDateTimeMxConverter.class)
    private LocalDateTime specifiedTime;

    @Override
    public void run() {
            switch (timeClause) {
                case TODAY -> handleDateBetween(doneStatus, taskTimeField, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
                case AT_DATE -> {
                    if (specifiedTime == null) {
                        throw new IllegalArgumentException("date required to AT_DATE parameter");
                    }
                    TaskTimeField current = taskTimeField != null ? taskTimeField : TaskTimeField.DUEDATE;
                    handleDateBetween(doneStatus, current, specifiedTime, specifiedTime.toLocalDate().atTime(LocalTime.MAX));
                }
                case TOMORROW -> {
                    TaskTimeField current = taskTimeField != null ? taskTimeField : TaskTimeField.DUEDATE;
                    handleDateBetween(doneStatus,current, LocalDate.now().plusDays(1).atStartOfDay(), LocalDate.now().plusDays(1).atTime(LocalTime.MAX));
                }
            }
    }

    private void handleDateBetween(boolean doneStatus, TaskTimeField taskTimeField, LocalDateTime start, LocalDateTime end) {
        TaskCollection taskCollection = AdjutoraUtils.getConfigContext().getTaskService().getAllTasksWithFilter(doneStatus, taskTimeField, start, end);
        String printer = printTaskTreeCollection(taskCollection, TaskViewType.SHORT);
        System.out.println(printer);
    }
}
