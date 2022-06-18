package io.github.artenes.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompileDatabase {

    private final Path filesRoot;
    private final Path generatedRoot;
    private final Path compiledPath;
    private final UserDataRepository userDataRepository;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public CompileDatabase(Path root) throws SQLException {
        this.filesRoot = root.resolve("raw");
        this.generatedRoot = root.resolve("generated");
        compiledPath = generatedRoot.resolve("compiled.db");
        this.generatedRoot.toFile().mkdir();
        this.userDataRepository = new UserDataRepository(compiledPath.toString());
    }

    public Path generate() throws IOException, SQLException {
        List<User> users = getAllUsers();
        insertIntoDatabase(users);
        return moveCompiledDb();
    }

    public void deleteGeneratedFolder() throws IOException {
        File generatedFolder = generatedRoot.toFile();
        File[] files = generatedFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    throw new IOException("Impossible to delete " + file.getAbsolutePath());
                }
            }
        }

        if (!generatedFolder.delete()) {
            throw new IOException("Impossible to delete folder " + generatedFolder.getAbsolutePath());
        }
    }

    private List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        Files.list(filesRoot).map(UserCsvFile::new).forEach(userCsvFile -> users.addAll(userCsvFile.getAllUsers()));
        return users;
    }

    private void insertIntoDatabase(List<User> users) throws SQLException {
        userDataRepository.insertAll(users);
    }

    private Path moveCompiledDb() throws IOException {
        String newDbName = "user_data" + System.currentTimeMillis() + ".db";
        Path newDbPath = generatedRoot.resolve(newDbName);
        Files.move(compiledPath, newDbPath);
        return newDbPath;
    }

}
