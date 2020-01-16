package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts


import androidx.room.ColumnInfo

data class CustomerMinimal(
    @ColumnInfo(name = "customername") val customerName: String?,
    @ColumnInfo(name = "customercompany") val customerCompany: String?
)
