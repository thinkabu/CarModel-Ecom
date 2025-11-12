package com.example.ltdd_dacs3.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val product: Product,
    val quantity: Int,
    val selectedColor: Int? = null,
    val selectedRatio: String? = null
): Parcelable {
    constructor() : this(Product(), 1, null, null)
}
