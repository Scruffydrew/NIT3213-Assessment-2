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
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.nit3213assessment2.R


class LoginFragment : Fragment(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    private var navc: NavController?= null

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
        val usernameCorrect = "Lachlan"
        val passwordCorrect = "s8093929"

        var usernameinput = view?.findViewById<TextView>(R.id.username_input)?.getText().toString()
        var passwordinput = view?.findViewById<TextView>(R.id.password_input)?.getText().toString()

        if (usernameinput == usernameCorrect && passwordinput == passwordCorrect) {
            Log.v("s8093929", "Correct Credentials Entered")
            navc?.navigate(R.id.action_loginFragment_to_dashboardFragment)

        } else {
            Log.v("s8093929", "Incorrect Username or Password,\n" +
                    "Current Entered Username is: $usernameinput \n" +
                    "Current Entered Password is: $passwordinput"
            )
        }
    }
}