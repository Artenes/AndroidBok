package io.github.artenes.androidbok.files

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class WriteToFileActivity : AppCompatActivity() {

    private val fileContent: String
        get() = intent?.getStringExtra("content") ?: ""

    private val fileTitle: String
        get() = intent?.getStringExtra("title") ?: ""

    private val fileType: String
        get() = intent?.getStringExtra("type") ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectPath()
    }

    private fun selectPath() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = fileType
            putExtra(Intent.EXTRA_TITLE, fileTitle)
        }
        writeResult.launch(intent)
    }

    private val writeResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) {
            val uri = it.data!!.data as Uri
            val outputStream = contentResolver.openOutputStream(uri)
            val writer = OutputStreamWriter(outputStream)
            val bufferedWriter = BufferedWriter(writer)
            bufferedWriter.write(fileContent)
            bufferedWriter.flush()
            finish()
        }
    }

}