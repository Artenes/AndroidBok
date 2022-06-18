package io.github.artenes.utils;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

public class CompileDatabaseTask extends DefaultTask {

    @TaskAction
    public void doSomething() throws SQLException, IOException {
        Path path = getProject().getRootDir().toPath().resolve("database");
        CompileDatabase compileDatabase = new CompileDatabase(path);
        System.out.println("Compiling database...");
        Path newDb = compileDatabase.generate();
        System.out.println("Database generated at " + newDb.toAbsolutePath());
    }

}
