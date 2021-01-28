package com.example.DogWiki

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.DogWiki.databinding.EditorFragmentBinding


//The code that deals with the editing screen of the app.
class EditorFragment : Fragment() {


    //References the model, arguments and binding for the editor view.
    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs()
    private lateinit var binding: EditorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Shows the phone's action bar.
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }
        setHasOptionsMenu(true)

        requireActivity().title =
            if (args.breedId == NEW_BREED_ID) {
                getString(R.string.new_breed)
            } else {
                getString(R.string.edit_breed)
            }

        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)

        //Binds the editors for the three fields.
        binding = EditorFragmentBinding.inflate(inflater, container, false)
        binding.editor.setText("")
        binding.editor2.setText("")
        binding.editor3.setText("")

        //When the user clicks back, save the note and return to the list view.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )



        viewModel.currentBreed.observe(viewLifecycleOwner, Observer {

            //Gets the existing string from the database.
            val savedString = savedInstanceState?.getString(BREED_TEXT_KEY)
            val savedString2 = savedInstanceState?.getString(BREED_TYPE_KEY)
            val savedString3 = savedInstanceState?.getString(BREED_DATA_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0

            //Sets the string to the edited text.
            binding.editor.setText(savedString ?: it.text)
            binding.editor2.setText(savedString2 ?: it.text2)
            binding.editor3.setText(savedString3 ?: it.text3)

            //Selects from whee the cursor is within the editor.
            binding.editor.setSelection(cursorPosition)
            binding.editor2.setSelection(cursorPosition)
            binding.editor3.setSelection(cursorPosition)
        })
        viewModel.getBreedById(args.breedId)

        return binding.root
    }

    //Displays the options menu when clicked.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    //The function save and return will save the note and return to the list view when the back arrow is clicked.
    private fun saveAndReturn(): Boolean {

        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        //Saves the inputted data to the note.
        viewModel.currentBreed.value?.text = binding.editor.text.toString()
        viewModel.currentBreed.value?.text2 = binding.editor2.text.toString()
        viewModel.currentBreed.value?.text3 = binding.editor3.text.toString()
        viewModel.updateBreed()

        //Goes back to the list view.
        findNavController().navigateUp()
        return true
    }

    //When a save instance is made, saves the data in whe editor to the database.
    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.editor) {
            outState.putString(BREED_TEXT_KEY, text.toString())
            outState.putString(BREED_TYPE_KEY, text.toString())
            outState.putString(BREED_DATA_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

}