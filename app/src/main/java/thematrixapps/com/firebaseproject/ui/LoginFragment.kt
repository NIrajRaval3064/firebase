package thematrixapps.com.firebaseproject.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.login_fragment.*
import thematrixapps.com.firebaseproject.LoginViewModel
import thematrixapps.com.firebaseproject.R

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    //view Model all the fireabse Logic code there
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        //Observe the live data and navigate the Home screen
        viewModel.authState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                t ->viewModel.authState
            println("Current user is ${viewModel.auth.currentUser?.email.toString()}")
            if (viewModel.auth.currentUser!=null){
                //Navigation home page
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

            }else{
                //Message display here please login
            }
        })
        println(viewModel.authState.value)
        btnLogin.setOnClickListener {
            viewModel.handleSignIn("ravalnnn@gmail.com","1234567")
        }
        btnSignup.setOnClickListener {
            viewModel.handleSignUp("ravalnnn@gmail.com","1234567","1234567")
        }
    }



}


