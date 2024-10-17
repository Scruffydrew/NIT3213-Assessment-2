package com.example.nit3213assessment2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213assessment2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(), View.OnClickListener {

    @OptIn(DelicateCoroutinesApi::class)

    private lateinit var textView: TextView
    private lateinit var detailsRecyclerView: RecyclerView
    private lateinit var adapter: myAdapter
    private lateinit var clicklambdafunction: () -> Unit

    private val viewModel: DetailsViewModel by viewModels()

    private var navc: NavController?= null

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

        navc = Navigation.findNavController(view)

        view.findViewById<ImageButton>(R.id.backImageButton)?.setOnClickListener(this)

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

                        // Set the Title for the Details Page based on the selected exercise
                        view.findViewById<TextView>(R.id.detailsTextViewTitle).setText(itemsInApiResponse[selectedItemIndex!!].exerciseName)

                        // Set text of each of the TextViews that are used to display the detailed information for the selected exercise
                        view.findViewById<TextView>(R.id.muscleGroupTextView).setText(itemsInApiResponse[selectedItemIndex].muscleGroup)
                        view.findViewById<TextView>(R.id.equipmentTextView).setText(itemsInApiResponse[selectedItemIndex].equipment)
                        view.findViewById<TextView>(R.id.difficultyTextView).setText(itemsInApiResponse[selectedItemIndex].difficulty)
                        view.findViewById<TextView>(R.id.caloriesTextView).setText("${itemsInApiResponse[selectedItemIndex].caloriesBurnedPerHour}")
                        view.findViewById<TextView>(R.id.descriptionTextView).setText(itemsInApiResponse[selectedItemIndex].description)

                        // Create a list of detailed information for the selected exercise
                        val exerciseDetails = listOf(
                            "Muscle Group: ${itemsInApiResponse[selectedItemIndex].muscleGroup}",
                            "Equipment: ${itemsInApiResponse[selectedItemIndex].equipment}",
                            "Difficulty: ${itemsInApiResponse[selectedItemIndex].difficulty}",
                            "Calories Burned Per Hour: ${itemsInApiResponse[selectedItemIndex].caloriesBurnedPerHour}",
                            "Description: ${itemsInApiResponse[selectedItemIndex].description}"
                        )

                        Log.v("s8093929", "List of Details: $exerciseDetails")
                    }
                }
            }
        }
    }

    // When the back button is pressed transition from details fragment to dashboard fragment
    override fun onClick(v: View?) {
        Log.v("s8093929", "Back button pressed")
        navc?.navigate(R.id.action_detailsFragment_to_dashboardFragment)
    }
}