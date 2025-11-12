package com.example.ltdd_dacs3.Data

data class User(val firstName:String,
                val lastName:String,
                val email:String,
                val imagePath:String = ""
){
    constructor():this("","","","")
}
