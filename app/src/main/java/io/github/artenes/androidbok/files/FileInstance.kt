package io.github.artenes.androidbok.files

import java.nio.file.Path
import java.nio.file.Paths

class FileInstance(first: String, vararg others: String) {

    private val path: Path

    init {
        var rest = Paths.get(first)
        others.forEach {
            rest = rest.resolve(it)
        }
        path = rest
    }

    fun root(): FileInstance {
        return FileInstance(path.getName(0).toString())
    }

    fun resolve(part: String): FileInstance {
        return FileInstance(path.resolve(part).toString())
    }

    fun createAsDirectory() {
        path.toFile().mkdirs()
    }

    fun delete() {
        path.toFile().deleteRecursively()
    }

    fun write(content: String) {
        createIfNecessary()
        path.toFile().writeText(content)
    }

    fun read(): String {
        createIfNecessary()
        return path.toFile().readText()
    }

    private fun createIfNecessary() {
        val file = path.toFile()
        if (file.exists()) {
            return
        }
        if (path.parent != null) {
            path.parent.toFile().mkdirs()
        }
        file.createNewFile()
    }

}