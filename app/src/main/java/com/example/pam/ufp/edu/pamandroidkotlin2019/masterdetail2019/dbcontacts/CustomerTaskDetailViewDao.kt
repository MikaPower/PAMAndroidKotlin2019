package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts

import androidx.room.*


/**
 * Data Access Object for the CustomerTaskDetailView view.
 *
 * The CustomerTaskDetailView view is like a "table" that joins CustomerTask and Customer
 * (a CustomerTask receives a foreign key from Customer) and then may be queried as a normal table
 * and have filters.
 * Hence, we can have a DAO (CustomerTaskDetailViewDao) to query this CustomerTaskDetailView.
 *
 */
@Dao
interface CustomerTaskDetailViewDao {

    /**
     * Get all CustomerTaskDetailView.
     * @return all CustomerTaskDetailView from view "table" with taskid = id.
     */
    @Query("SELECT * FROM CustomerTaskDetailView WHERE customertaskid = :id")
    fun search(id: Int): List<CustomerTaskDetailView>

    /**
     * Get all CustomerTaskDetailView.
     * @return all CustomerTaskDetailView from view "table".
     */
    @Query("SELECT * FROM CustomerTaskDetailView")
    fun loadAllCustomerTaskDetailView(): List<CustomerTaskDetailView>
}
