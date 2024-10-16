package com.example.nit3213assessment2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nit3213assessment2.R
import com.example.nit3213assessment2.data.LoginResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    private var navc: NavController ?= null

    @Inject
    @Named("Username")
    lateinit var usernameCorrect:String

    @Inject
    @Named("Password")
    lateinit var passwordCorrect: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navc = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.loginButton)?.setOnClickListener(this)

        viewModel.getkeypass()
    }

    override fun onClick(v: View?) {

        var usernameinput = view?.findViewById<TextView>(R.id.username_input)?.getText().toString()
        var passwordinput = view?.findViewById<TextView>(R.id.password_input)?.getText().toString()

        if (usernameinput == usernameCorrect && passwordinput == passwordCorrect) {
            Log.v("s8093929", "Correct Credentials Entered")

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.objectsState.collect { itemsInApiResponse ->
                        if (itemsInApiResponse == LoginResponse(keypass = "fitness")) {
                            navc?.navigate(R.id.action_loginFragment_to_dashboardFragment)
                        }
                    }
                }
            }


        } else {
            Log.v("s8093929", "Incorrect Username or Password,\n" +
                    "Current Entered Username is: $usernameinput \n" +
                    "Current Entered Password is: $passwordinput"
            )
        }
    }
}