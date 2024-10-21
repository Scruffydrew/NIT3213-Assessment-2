package com.example.nit3213assessment2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213assessment2.KeypassRepository
import com.example.nit3213assessment2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    @OptIn(DelicateCoroutinesApi::class)

    // Injecting keypass from KeypassRepository
    @Inject
    lateinit var keypassRepository: KeypassRepository

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: myAdapter
    private lateinit var clicklambdafunction: () -> Unit

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Getting the keypass from keypassRepository
        val keypass = keypassRepository.keypass

        Log.v("s8093929", "The Received Keypass is : $keypass")

        viewModel.getAllObjects(keypass)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.entitiesState.collect { itemsInApiResponse ->
                    if (itemsInApiResponse.isNotEmpty()) {

                        clicklambdafunction= { Log.v("s8093929","Item Clicked")}

                        val exerciseList = mutableListOf<String>()
                        itemsInApiResponse.map { entity ->
                            exerciseList += ("Exercise: ${entity.exerciseName}\n" +
                            "Muscle Group: ${entity.muscleGroup}\n" +
                            "Equipment: ${entity.equipment}\n" +
                            "Difficulty: ${entity.difficulty}\n" +
                            "Calories Burned Per Hour: ${entity.caloriesBurnedPerHour}")
                        }

                        recyclerView = view.findViewById(R.id.recyclerView)
                        adapter = myAdapter(exerciseList,clicklambdafunction)
                        recyclerView.adapter=adapter
                        adapter.updateData(exerciseList)
                    }
                }
            }
        }
    }
}