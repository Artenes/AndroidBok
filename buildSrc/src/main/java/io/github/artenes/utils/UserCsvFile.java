package io.github.artenes.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserCsvFile {

    private final Path path;

    public UserCsvFile(Path path) {
        this.path = path;
    }

    public List<User> getAllUsers() {
        try {
            return Files.readAllLines(path).stream().skip(1).map(this::fromLine).collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private User fromLine(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        String email = parts[1];
        return new User(name, email);
    }

}
