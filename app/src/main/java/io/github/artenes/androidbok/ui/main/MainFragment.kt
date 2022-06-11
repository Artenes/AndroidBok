package io.github.artenes.androidbok.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.artenes.androidbok.R
import io.github.artenes.androidbok.files.MimeType
import io.github.artenes.androidbok.files.WriteToFileActivity

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.buttonWriteToFile).setOnClickListener {
            val intent = Intent(context, WriteToFileActivity::class.java)
            intent.putExtra("content", "some test content")
            intent.putExtra("type", MimeType.PLAIN_TEXT.type)
            intent.putExtra("title", "testFile.txt")
            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}