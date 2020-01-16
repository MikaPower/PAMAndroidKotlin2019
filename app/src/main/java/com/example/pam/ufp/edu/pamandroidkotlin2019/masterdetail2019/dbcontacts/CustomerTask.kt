package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "customertasks",
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("customerid"),
        childColumns = arrayOf("customerid")
    )],
    indices = [androidx.room.Index(value = ["customerid"])]
)
data class CustomerTask(
    @PrimaryKey
    @ColumnInfo(name = "customertaskid") var customerTaskId: Int,
    @ColumnInfo(name = "customertasktitle") var customerTaskTitle: String?,
    @ColumnInfo(name = "customertaskdesc") var customerTaskDesc: String?,
    @ColumnInfo(name = "customerid") var customerId: Int
)
