package thematrixapps.com.firebaseproject.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import thematrixapps.com.firebaseproject.model.User


class HomeViewModel : ViewModel() {
    private val TAG="HomeViewModel"
    private lateinit var firestore : FirebaseFirestore
    val userList= MutableLiveData<User>()

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    init {
        val user=User("Niraj","Raval",12)
        println("Init Home Model")
        //getUserData()
//        saveUser(user)
      //  getUserData()
    }
    fun saveUser (user:User) {
        user?.let {
                user ->
            val handle = firestore.collection("users").document().set(user)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it ") }
        }
    }
    fun addUserData(data:User){


        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Alan"
        user["middle"] = "Mathison"
        user["last"] = "Turing"
        user["born"] = 1912
// Add a new document with a generated ID

// Add a new document with a generated ID
        firestore.collection("users")
            .add(data)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            })
            .addOnFailureListener(OnFailureListener { e -> Log.w(TAG, "Error adding document", e) })
    }
    fun getUserData(){
        firestore.collection("users")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            })
    }
}