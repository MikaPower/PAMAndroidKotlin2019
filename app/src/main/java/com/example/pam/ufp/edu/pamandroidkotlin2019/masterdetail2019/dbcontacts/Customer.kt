package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts

import android.graphics.Bitmap
import android.graphics.Picture
import androidx.room.*
import java.net.Inet4Address


@Entity(
    tableName = "customers",
    indices = arrayOf(Index(value = ["customername", "customercompany"]))
)
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customerid") var customerId: Int = 0,
    @ColumnInfo(name = "customername") var customerName: String? = "",
    @ColumnInfo(name = "customercompany") var customerCompany: String? = "",
    @ColumnInfo(name = "customeraddress") var customerAddress: String? = "",
    @ColumnInfo(name = "customercity") var customerCity: String? = "",
    @ColumnInfo(name = "customerphone") var customerPhone: String? = "",
    @Ignore var picture: Bitmap? = null
) {
    constructor() : this(0, "", "", "", "", "", null)
}