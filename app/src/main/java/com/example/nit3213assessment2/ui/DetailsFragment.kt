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
        textView = view.findViewById(R.id.detailsTextView)

        // Retrieve the passed data from the arguments
        val ClickedItemText = arguments?.getString("ClickedItemText")
        Log.v("s8093929", "Clicked item text: $ClickedItemText")

        viewModel.getAllObjects()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.entitiesState.collect { itemsInApiResponse ->
                    if (itemsInApiResponse.isNotEmpty()) {
                        // Find the index based on exercise name
                        val index = itemsInApiResponse.indexOfFirst { entity ->
                            entity.exerciseName == ClickedItemText
                        }

                        // Log the index found
                        Log.v("s8093929", "Index of clicked exercise name: $index")

                        if (index != -1) {
                            // Set the clicked item text in the TextView
                            textView.text = itemsInApiResponse[index].exerciseName

                            // Create a list of detailed information for the selected exercise
                            val exerciseDetails = listOf(
                                "Muscle Group: ${itemsInApiResponse[index].muscleGroup}",
                                "Equipment: ${itemsInApiResponse[index].equipment}",
                                "Difficulty: ${itemsInApiResponse[index].difficulty}",
                                "Calories Burned Per Hour: ${itemsInApiResponse[index].caloriesBurnedPerHour}",
                                "Description: ${itemsInApiResponse[index].description}"
                            )

                            Log.v("s8093929", "List of Details: $exerciseDetails")

                            detailsRecyclerView = view.findViewById(R.id.detailsRecyclerView)
                            adapter = myAdapter(exerciseDetails,clicklambdafunction)
                            detailsRecyclerView.adapter=adapter
                            adapter.updateData(exerciseDetails)

                        } else {
                            textView.text = "Item not found"
                        }

                    }
                }
            }
        }
    }
}