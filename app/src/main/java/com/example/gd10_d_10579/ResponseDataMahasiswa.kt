package com.example.gd10_d_10579

import com.example.gd10_d_10579.MahasiswaData
import com.google.gson.annotations.SerializedName

data class ResponseDataMahasiswa(
    @SerializedName("status") val stt:String,
    val totaldata: Int,
    val data:List<MahasiswaData>)