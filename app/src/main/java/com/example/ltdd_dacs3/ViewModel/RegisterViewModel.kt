package com.example.ltdd_dacs3.ViewModel

import androidx.lifecycle.ViewModel
import com.example.ltdd_dacs3.Data.User
import com.example.ltdd_dacs3.Util.Constants.USER_COLLECTION
import com.example.ltdd_dacs3.Util.RegisterFieldsState
import com.example.ltdd_dacs3.Util.RegisterValidation
import com.example.ltdd_dacs3.Util.Resource
import com.example.ltdd_dacs3.Util.validateConfirmPassword
import com.example.ltdd_dacs3.Util.validateEmail
import com.example.ltdd_dacs3.Util.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
):ViewModel() {

    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecifiled())
    val register: Flow<Resource<User>> = _register

    private val _validation = Channel<RegisterFieldsState>()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithEmailAndPassword(user: User, password: String, confirmPassword: String ){
        if (checkValidation(user, password, confirmPassword)){
            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {
                it.user?.let {
                    saveUserInfo(it.uid, user)

                }
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
        }else{
            val registerFailedsState  = RegisterFieldsState(
                validateEmail(user.email),
                validatePassword(password),
                validateConfirmPassword(password,confirmPassword)
            )
            runBlocking {
                _validation.send(registerFailedsState)
            }
        }


    }

    private fun saveUserInfo(userUid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }

    // kiem tra xac thuc email va passsword
    private fun checkValidation(user: User, password: String, confirmPassword: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordVaidation = validatePassword(password)
        val confirmPassword = validateConfirmPassword(password, confirmPassword)
        val shouldValidation =
            emailValidation is RegisterValidation.Success && passwordVaidation is RegisterValidation.Success && confirmPassword is RegisterValidation.Success

        return shouldValidation
    }

}