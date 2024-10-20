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

    // Comparing the inputted username and password with the correct username and password
    fun checkCredentials(usernameinput:String,
                                 passwordinput:String,
                                 correctUsername:String,
                                 correctPassword:String) : Boolean {
        if (usernameinput != correctUsername || passwordinput != correctPassword) {
            return false
        }
        return true
    }

    // Get the reason for the error when logging in
    fun loginErrorReason(usernameinput:String,
                         passwordinput:String,
                         correctUsername:String,
                         correctPassword:String) : String {
        return when {
            usernameinput != correctUsername && passwordinput != correctPassword -> "Both username and password are incorrect"
            passwordinput != correctPassword -> "Password is incorrect"
            usernameinput != correctUsername -> "Username is incorrect"
            else -> "Unknown Error"
        }
    }

    // Injecting the correct username
    @Inject
    @Named("Username")
    lateinit var usernameCorrect:String

    // Injecting the correct password
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

    // Function for when the button is clicked
    override fun onClick(v: View?) {
        // getting inputted username and password
        var usernameinput = view?.findViewById<TextView>(R.id.username_input)?.getText().toString()
        var passwordinput = view?.findViewById<TextView>(R.id.password_input)?.getText().toString()

        // Checking the credentials
        if (checkCredentials(usernameinput, passwordinput, usernameCorrect, passwordCorrect)) {
            Log.v("s8093929", "Correct Credentials Entered")
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.objectsState.collect { itemsInApiResponse ->
                        if (itemsInApiResponse == LoginResponse(keypass = "fitness")) {
                            navc?.navigate(R.id.action_loginFragment_to_dashboardFragment)
                        } else {
                            view?.findViewById<TextView>(R.id.loginErrorTextView)?.setText("Failed to connect to API")
                        }
                    }
                }
            }


        } else {
            Log.v("s8093929", "Incorrect Username or Password,\n" +
                    "Current Entered Username is: $usernameinput \n" +
                    "Current Entered Password is: $passwordinput"
            )
            // Getting the reason for the error when logging in and setting the loginErrorMessage to the error
            var loginErrorMessage = loginErrorReason(usernameinput, passwordinput, usernameCorrect, passwordCorrect)
            view?.findViewById<TextView>(R.id.loginErrorTextView)?.setText(loginErrorMessage)

        }
    }
}