package io.github.artenes.androidbok

import io.github.artenes.androidbok.files.FileInstance
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FileInstanceTest {

    private val root = FileInstance("unitTestTempFiles")

    @Before
    fun setUp() {
        root.createAsDirectory()
    }

    @Test
    fun writeAndRead_fromFileAtRoot() {
        val file = FileInstance("testTempFile.txt")
        file.write("something")
        assertEquals("something", file.read())
        file.delete()
    }

    @Test
    fun writeAndRead_fromFileWithNotCreatedLongPath() {
        val file = FileInstance("one", "two", "three", "file.txt")
        file.write("something")
        assertEquals("something", file.read())
        file.root().delete()
    }

    @Test
    fun writeAndRead_getsContentProperly() {
        val file = root.resolve("test.txt")
        file.write("test content")
        val content = file.read()
        assertEquals("test content", content)
    }

    @Test
    fun writeAndRead_withFullPathAsArgument() {
        val file = FileInstance("testFolder/a/b/c/file.txt")
        file.write("test content")
        assertEquals("test content", file.read())
        file.root().delete()
    }

    @After
    fun tearDown() {
        root.delete()
    }

}