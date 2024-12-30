package com.move.adjutora.controller;

import com.move.adjutora.controller.read.ReadTaskCommand;
import com.move.adjutora.controller.read.ReadTaskFilterCommand;
import org.jline.reader.LineReader;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

import java.io.PrintWriter;

/**
 * Top-level command that just prints help.
 */
@CommandLine.Command(name = "",
        description = {
                "Example interactive shell with completion and autosuggestions. " +
                        "Hit @|magenta <TAB>|@ to see available commands.",
                "Hit @|magenta ALT-S|@ to toggle tailtips.",
                ""},
        footer = {"", "Press Ctrl-D to exit."},
        subcommands = {
                ReadTaskCommand.class,
                ReadTaskFilterCommand.class,
                ExportImportCommand.class,
                DeleteTaskCommand.class,
                AddTaskCommand.class,
                UpdateTaskCommand.class,
                PicocliCommands.ClearScreen.class,
                CommandLine.HelpCommand.class
        })
public class CliCommands implements Runnable {
    PrintWriter out;

    public CliCommands() {
    }

    public void setReader(LineReader reader) {
        out = reader.getTerminal().writer();
    }

    @Override
    public void run() {
        out.println(new CommandLine(this).getUsageMessage());
    }
}
