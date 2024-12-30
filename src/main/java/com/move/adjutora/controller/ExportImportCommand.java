package com.move.adjutora.controller;

import com.move.adjutora.model.expimp.ExpImpStatus;
import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "expimp", mixinStandardHelpOptions = true, version = "1.0",
        description = {"Command that export and import database for a task "},
        subcommands = {CommandLine.HelpCommand.class})
public class ExportImportCommand implements Runnable {

    @CommandLine.Option(names = {"-t", "--type"}, required = true, description = {"Use to decide export or import"})
    private ExpImpStatus type;
    @CommandLine.Option(names = {"-p", "--path"}, required = true, description = {"Path for export/import file"})
    private Path filePath;

    @Override
    public void run() {


    }
}
