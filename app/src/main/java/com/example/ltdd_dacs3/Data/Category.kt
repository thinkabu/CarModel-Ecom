package com.example.ltdd_dacs3.Data

sealed class Category(val category: String) {

    object BMW: Category("BMW")
    object Honda: Category("Honda")
    object Ferrari: Category("Ferrari")
    object Lamborghini: Category("Lamborghini")
    object Ducati: Category("Ducati")
    object Furniture: Category("Furniture")
}