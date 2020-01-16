package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts


import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Data Access Object for the customertasks table.
 */
@Dao
interface CustomerTaskDao {

    /**
     * Get all customertasks.
     * @return all customertasks from the table.
     */
    @Query("SELECT * FROM customertasks")
    fun loadAllCustomerTasks(): Array<CustomerTask>

    /**
     * Insert a customertask in the database (returns id), replace it if already exists.
     * @param customertask the customertask to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerTask(customertask: CustomerTask): Long

    /**
     * Delete 1+ customertasks from database (returns number of deleted rows).
     * @param customertaskss the set of customertasks to be deleted.
     */
    @Delete
    fun deleteCustomerTasks(vararg customertasks: CustomerTask): Int

    /**
     * Delete all customers.
     */
    @Query("DELETE FROM customertasks")
    fun deleteAllCustomerTasks(): Int
}
