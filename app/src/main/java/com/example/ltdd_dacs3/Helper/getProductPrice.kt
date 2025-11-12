package com.example.ltdd_dacs3.Helper

fun Float?.getProductPrice(price: Float): Float{
    //this --> Percentage
    if (this == null)
        return price
    val remainingPricePercentage = 1f - this/100
    val priceAfterOffer = remainingPricePercentage * price

    return priceAfterOffer
}