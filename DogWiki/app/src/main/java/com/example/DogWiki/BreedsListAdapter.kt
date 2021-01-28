package com.example.DogWiki


//Importing recyclerview and BreedEntity.
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.DogWiki.data.BreedEntity
import com.example.DogWiki.databinding.ListItemBinding

//Initialises the adapter. This will allow the breed entities to be shown in a list for the user to go through.
class BreedsListAdapter(private val breedsList: List<BreedEntity>,
                        private val listener: ListItemListener
) :
    RecyclerView.Adapter<BreedsListAdapter.ViewHolder>() {

//The recyclerview lists an array of breed entities.
    val selectedBreeds = arrayListOf<BreedEntity>()


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    //Gets the amount of breeds.
    override fun getItemCount() = breedsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breed = breedsList[position]
        with(holder.binding) {

            //Calls the different fields. These will be displayed on the list.
            breedText.text = breed.text
            breedType.text = breed.text2


            //Listens for when the user clicks on a breed.
            //When they click on a breed, they will be taken to the editor for that breed.
            root.setOnClickListener{
                listener.editBreed(breed.id)
            }
            fab.setOnClickListener{

                if (selectedBreeds.contains(breed)) {
                    selectedBreeds.remove(breed)
                    fab.setImageResource(R.drawable.ic_pets_24px)
                } else {
                    selectedBreeds.add(breed)
                    fab.setImageResource(R.drawable.ic_check)
                }
                listener.onItemSelectionChanged()
            }
            fab.setImageResource(
                if (selectedBreeds.contains(breed)) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_pets_24px
                }
            )
        }
    }

    interface ListItemListener {
        fun editBreed(BreedId: Int)
        fun onItemSelectionChanged()
    }
}