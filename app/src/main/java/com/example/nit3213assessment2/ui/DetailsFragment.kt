package com.example.nit3213assessment2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213assessment2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    @OptIn(DelicateCoroutinesApi::class)

    private lateinit var textView: TextView
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var adapter: myAdapter
    private lateinit var clicklambdafunction: () -> Unit

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("s8093929", "DetailsFragment Created")

        // Initialize the click lambda function with a default action
        clicklambdafunction = {}

        // Initialize the TextView and RecyclerView
        textView = view.findViewById(R.id.detailsTextViewTitle)

        // Retrieve the passed data from the arguments
        val selectedItemIndex = arguments?.getInt("SelectedItemIndex")
        Log.v("s8093929", "Clicked item text: $selectedItemIndex")

        viewModel.getAllObjects()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.entitiesState.collect { itemsInApiResponse ->
                    if (itemsInApiResponse.isNotEmpty()) {
                                                // Log the index found
                        Log.v("s8093929", "Index of clicked exercise name: $selectedItemIndex")

                        // Set the clicked item text in the TextView
                        textView.text = itemsInApiResponse[selectedItemIndex!!].exerciseName

                        // Create a list of detailed information for the selected exercise
                        val exerciseDetails = listOf(
                            "Muscle Group: ${itemsInApiResponse[selectedItemIndex].muscleGroup}",
                            "Equipment: ${itemsInApiResponse[selectedItemIndex].equipment}",
                            "Difficulty: ${itemsInApiResponse[selectedItemIndex].difficulty}",
                            "Calories Burned Per Hour: ${itemsInApiResponse[selectedItemIndex].caloriesBurnedPerHour}",
                            "Description: ${itemsInApiResponse[selectedItemIndex].description}"
                        )

                        view.findViewById<TextView>(R.id.muscleGroupTextView).setText(itemsInApiResponse[selectedItemIndex].muscleGroup)
                        view.findViewById<TextView>(R.id.equipmentTextView).setText(itemsInApiResponse[selectedItemIndex].equipment)
                        view.findViewById<TextView>(R.id.difficultyTextView).setText(itemsInApiResponse[selectedItemIndex].difficulty)
                        view.findViewById<TextView>(R.id.caloriesTextView).setText("${itemsInApiResponse[selectedItemIndex].caloriesBurnedPerHour}")
                        view.findViewById<TextView>(R.id.descriptionTextView).setText(itemsInApiResponse[selectedItemIndex].description)


                        Log.v("s8093929", "List of Details: $exerciseDetails")

                    }
                }
            }
        }
    }
}