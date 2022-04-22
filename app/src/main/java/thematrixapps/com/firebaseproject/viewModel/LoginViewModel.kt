package thematrixapps.com.firebaseproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    private  val TAG="LoginViewModel"
    //live data to update UI part
    private val _authState by lazy { MutableLiveData<AuthState>(AuthState.Idle) }

    lateinit var auth:FirebaseAuth
    init {
        // Initialize Firebase Auth
        auth = Firebase.auth
    }
    val authState: LiveData<AuthState> = _authState
    fun handleSignIn(email: String, password: String) {
        if (email.isBlank()) {
            _authState.value = AuthState.AuthError("Invalid email")
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG,"Email Login is successful")
                    _authState.value = AuthState.Success
                    auth= FirebaseAuth.getInstance().apply { this.currentUser }

                } else {
                    task.exception?.let {
                        Log.i(TAG,"Email Login failed with error ${it.localizedMessage}")
                        _authState.value = AuthState.AuthError(it.localizedMessage)
                        auth= FirebaseAuth.getInstance().apply { this.currentUser }
                    }
                }
            }
    }

    fun handleSignUp(email: String, password: String, confirmPassword: String) {
        if (email.isBlank()) {
            _authState.value = AuthState.AuthError("Invalid email")
            return
        }
        if (password != confirmPassword) {
            _authState.value = AuthState.AuthError("Password does not match")
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG,"Email signup is successful")
                    _authState.value = AuthState.Success
                    auth= FirebaseAuth.getInstance().apply { this.currentUser }
                } else {
                    task.exception?.let {
                        Log.i(TAG,"Email signup failed with error ${it.localizedMessage}")
                        _authState.value = AuthState.AuthError(it.localizedMessage)
                        auth= FirebaseAuth.getInstance().apply { this.currentUser }
                    }
                }
            }
    }

}
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    class AuthError(val message: String? = null) : AuthState()
}
