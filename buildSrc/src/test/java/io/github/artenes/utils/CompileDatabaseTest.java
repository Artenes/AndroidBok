package io.github.artenes.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class CompileDatabaseTest {

    private final Path root = Paths.get("..", "database", "test");
    private CompileDatabase compileDatabase;

    @Before
    public void setUp() throws SQLException {
        compileDatabase = new CompileDatabase(root);
    }

    @Test
    public void generate_createsDbFromRawData() throws SQLException, IOException {
        Path generatedDbPath = compileDatabase.generate();
        UserDataRepository repo = new UserDataRepository(generatedDbPath.toAbsolutePath().toString());
        assertEquals(12, repo.getCount());
    }

    @After
    public void tearDown() throws IOException {
        compileDatabase.deleteGeneratedFolder();
    }

}