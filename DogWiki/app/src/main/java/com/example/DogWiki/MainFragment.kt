package com.example.DogWiki

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.DogWiki.data.BreedEntity
import com.example.DogWiki.databinding.MainFragmentBinding

//The main view or home page of the app.
class MainFragment : Fragment(),
    BreedsListAdapter.ListItemListener {

    //Calls the view, binding and adapter for the main page.
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: BreedsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        //Sets the tile for the app using a string in the res folder.
        requireActivity().title = getString(R.string.app_name)

        //Calls the binding an the view.
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Sets the size and decoration.
        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        //Sets the breeds list to be displayed.
        viewModel.breedsList?.observe(viewLifecycleOwner, Observer {
            Log.i("breedLogging", it.toString())
            adapter = BreedsListAdapter(it, this@MainFragment)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)

            //Gets the id of all the selected breeds and passes it through the adapter.
            val selectedBreeds =
                savedInstanceState?.getParcelableArrayList<BreedEntity>(SELECTED_BREEDS_KEY)
            adapter.selectedBreeds.addAll(selectedBreeds ?: emptyList())
        })

        //If the user clicks the floating + button, opens up the edit view with for a new breed.
        binding.floatingActionButton.setOnClickListener {
            editBreed(NEW_BREED_ID)
        }

        return binding.root
    }

    //Creates the options menu in the top right corner.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //If the user has selected dog breeds, displays the menu for selected breeds.
        val menuId =
            if (this::adapter.isInitialized &&
                adapter.selectedBreeds.isNotEmpty()
            ) {
                R.menu.menu_main_selected_items

            //Otherwise, displays the main menu.
            } else {
                R.menu.menu_main
            }
        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //When the user selects an item from the list, runs the function associated with that option.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sample_data -> addSampleData()
            R.id.action_delete -> deleteSelectedBreeds()
            R.id.action_delete_all -> deleteAllBreeds()
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Calls the delete all breeds function, which will delete all breeds.
    private fun deleteAllBreeds(): Boolean {
        viewModel.deleteAllBreeds()
        return true
    }

    //When called, it will delete all the breeds the user has selected.
    private fun deleteSelectedBreeds(): Boolean {
        viewModel.deleteBreeds(adapter.selectedBreeds)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedBreeds.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)
        return true
    }

    //This calls the function to populate the list with sample data.
    private fun addSampleData(): Boolean {
        viewModel.addSampleData()
        return true
    }

    //Navigates to the edit breed view with the id of the breed that the user has selected.
    override fun editBreed(BreedId: Int) {
        Log.i(TAG, "onItemClick: received breed id $BreedId")
        val action = MainFragmentDirections.actionEditBreed(BreedId)
        findNavController().navigate(action)
    }

    //If the user deselects the objects,
    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }

    //Puts the selected breeds into an array list upon save states.
    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized) {
            outState.putParcelableArrayList(
                SELECTED_BREEDS_KEY,
                adapter.selectedBreeds
            )
        }
        super.onSaveInstanceState(outState)
    }

}