package com.example.ltdd_dacs3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ltdd_dacs3.Data.Product
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun searchProducts(query: String) {
        viewModelScope.launch {
            val result = try {
                firestore.collection("Products")
                    .whereGreaterThanOrEqualTo("name", query)
                    .whereLessThanOrEqualTo("name", "$query\uf8ff")
                    .get()
                    .await()
                    .toObjects(Product::class.java)
            } catch (e: Exception) {
                emptyList<Product>()
            }
            _products.value = result
        }
    }

}