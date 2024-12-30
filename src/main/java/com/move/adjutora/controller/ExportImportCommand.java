package com.move.adjutora.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move.adjutora.model.TaskCollection;
import com.move.adjutora.model.expimp.ExpImpStatus;
import com.move.adjutora.model.expimp.TaskCollectionExportImport;
import com.move.adjutora.service.TaskService;
import com.move.adjutora.utils.AdjutoraUtils;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

@CommandLine.Command(name = "expimp", mixinStandardHelpOptions = true, version = "1.0",
        description = {"Command that export and import database for a task "},
        subcommands = {CommandLine.HelpCommand.class})
public class ExportImportCommand implements Runnable {

    @CommandLine.Option(names = {"-t", "--type"}, required = true, description = {"Use to decide export or import"})
    private ExpImpStatus type = ExpImpStatus.EXPORT;
    @CommandLine.Option(names = {"-p", "--path"}, required = true, description = {"Path for export/import file"})
    private String filePathStr;

    @Override
    public void run() {
        try {
            TaskService taskService = AdjutoraUtils.getConfigContext().getTaskService();
            ObjectMapper mapper = AdjutoraUtils.getConfigContext().getMapper();
            Scanner scanner = new Scanner(System.in);
            Path filePath = Paths.get(filePathStr);
            switch (type) {
                case EXPORT -> {
                    System.out.println("Exporting to file: " + filePath.toAbsolutePath());
                    TaskCollection taskCollection = taskService.getAllTasks();
                    TaskCollectionExportImport taskCollectionExportImport = new TaskCollectionExportImport(taskCollection);
                    mapper.writeValue(filePath.toFile(), taskCollectionExportImport);
                }
                case IMPORT -> {
                    String ans = "";
                    while (!List.of("y", "n").contains(ans)) {
                        System.out.println("Importing file delete all current data, are you sure (y/n)? ");
                        ans = scanner.nextLine().toLowerCase();
                    }
                    if (ans.equals("y")) {
                        taskService.deleteDatabase();
                        TaskCollectionExportImport taskCollectionExportImport = mapper.readValue(filePath.toFile(), TaskCollectionExportImport.class);
                        taskService.importDatabase(taskCollectionExportImport);
                    } else {
                        System.out.println("Import cancelled by user");
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
