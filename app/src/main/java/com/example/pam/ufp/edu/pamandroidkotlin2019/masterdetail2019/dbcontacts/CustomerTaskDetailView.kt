package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts
import androidx.room.DatabaseView

@DatabaseView("SELECT customertasks.customertaskid, customertasks.customertasktitle, customertasks.customertaskdesc, "+
        "customertasks.customerid, customers.customername AS customername FROM customertasks " +
        "INNER JOIN customers ON customertasks.customerid = customers.customerid")
data class CustomerTaskDetailView(
    val customertaskid: Int,
    val customertasktitle: String?,
    val customertaskdesc: String?,
    val customerid: Int,
    val customername: String?
)
