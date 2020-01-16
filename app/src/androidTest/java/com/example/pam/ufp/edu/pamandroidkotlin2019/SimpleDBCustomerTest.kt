package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException




import org.hamcrest.Matchers.greaterThanOrEqualTo




@RunWith(AndroidJUnit4::class)
class SimpleDBCustomerTest {
    private lateinit var customerDao: CustomerDao
    private lateinit var customerTaskDao: CustomerTaskDao
    private lateinit var customerTaskDetailViewDao: CustomerTaskDetailViewDao
    private lateinit var db: CustomersDatabase //TestDatabase

    @Before
    fun createDb() {
        Log.e(this.javaClass.simpleName, "createDb(): going to create DB and get customerDao...")
        //val context = ApplicationProvider.getApplicationContext<Context>()
        val context = InstrumentationRegistry.getInstrumentation().context
        //Create a version of the DB in memory to speed up tests
        db = Room.inMemoryDatabaseBuilder(context, CustomersDatabase::class.java).build()
        customerDao = db.customerDao()
        customerTaskDao = db.customerTaskDao()
        customerTaskDetailViewDao = db.customerTaskDetailViewDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        Log.e(this.javaClass.simpleName, "closeDb(): going to close DB...")
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testeInsertCustomersAndListInserted() {
        Log.e(
            this.javaClass.simpleName,
            "testeInsertCustomersAndListInserted(): going to insert customers and list..."
        )

        val customerPatinhas: Customer =
            Customer(
                1, "Tio Patinhas", "Patinhas Lda",
                "Rua Sesamo 1", "Porto", "+351220000111"
            )
        val customerMikey: Customer =
            Customer(
                2, "Mikey", "Mikey Lda",
                "Rua Sesamo 2", "Aveiro", "+351220000222"
            )
        val customerDonald: Customer =
            Customer(
                3, "Donald", "Donald Lda",
                "Rua Sesamo 3", "Porto", "+351220000333"
            )
        val customerDaisy: Customer =
            Customer(
                4, "Daisy", "Daisy Lda",
                "Rua Sesamo 4", "Lisboa", "+351220000444"
            )

        val id: Long = customerDao.insertCustomer(customerPatinhas)
        Log.e(
            this.javaClass.simpleName,
            "testeInsertCustomersAndListInserted(): Patinhas id = ${id}"
        )
        assertThat(id, equalTo(1L))

        //val customerCompletable : Completable = customerDao.insertCustomerCompletable(customerPatinhas)
customerDao.insertCustomerCompletable(customerPatinhas).doOnComplete {
            Log.e(this.javaClass.simpleName, "doOnComplete(): insert completed")
        }


        //val listCustomers = listOf(customerPatinhas, customerDonald)
        val listInsertedCustomerIDs: List<Long> =
            customerDao.insertCustomers()
        Log.e(
            this.javaClass.simpleName,
            "testeInsertCustomersAndListInserted(): listCustomerIDs = ${listInsertedCustomerIDs}"
        )
        assertThat(listInsertedCustomerIDs.size, equalTo(3))

        val allCustomers = customerDao.loadAllCustomers()
        Log.e(
            this.javaClass.simpleName,
            "testeInsertCustomersAndListInserted(): allCustomers.size = ${allCustomers.size}"
        )
        assertThat(allCustomers.size, greaterThan(0))

        val customerByCity = customerDao.getCustomersByCity("Porto")
        Log.e(
            this.javaClass.simpleName,
            "testeInsertCustomersAndListInserted(): customerByCity.size = ${customerByCity.size}"
        )
        assertThat(customerByCity.size, greaterThanOrEqualTo(1))
        assertThat(customerByCity.get(0), equalTo(customerPatinhas))
    }

    @Test
    @Throws(Exception::class)
    fun testeDeletCustomers() {
        Log.e(
            this.javaClass.simpleName,
            "testeDeletCustomers(): going to list customers..."
        )

        val customerPatinhas: Customer =
            Customer(
                1, "Tio Patinhas", "Patinhas Lda",
                "Rua Sesamo 1", "Porto", "+351220000111"
            )
        val customerMikey: Customer =
            Customer(
                2, "Mikey", "Mikey Lda",
                "Rua Sesamo 2", "Aveiro", "+351220000222"
            )
        val customerDonald: Customer =
            Customer(
                3, "Donald", "Donald Lda",
                "Rua Sesamo 3", "Porto", "+351220000333"
            )
        val customerDaisy: Customer =
            Customer(
                4, "Daisy", "Daisy Lda",
                "Rua Sesamo 4", "Lisboa", "+351220000444"
            )
        customerDao.insertCustomers(customerPatinhas, customerMikey, customerDonald, customerDaisy)

        customerDonald.customerName = "Donald Updated"
        customerDao.updateCustomers(customerDonald)
        val updatedCustomerDonald = customerDao.getCustomerById("" + 3)
        assertThat(updatedCustomerDonald, equalTo(customerDonald))

        customerDao.deleteCustomers(customerPatinhas)
        //val allCustomers = customerDao.loadAllCustomers()
        var allCustomerMinimal: List<CustomerMinimal> = customerDao.loadFullCustomerMinimalInfo()

        Log.e(
            this.javaClass.simpleName,
            "testeDeletCustomers(): allCustomerMinimal = $allCustomerMinimal"
        )
        assertThat(allCustomerMinimal.size, equalTo(3))

        val deleted: Int = customerDao.deleteAllcustomers()
        Log.e(
            this.javaClass.simpleName,
            "testeDeletCustomers(): deleted = ${deleted}"
        )
        allCustomerMinimal = customerDao.loadFullCustomerMinimalInfo()
        Log.e(
            this.javaClass.simpleName,
            "testeDeletCustomers(): allCustomerMinimal.size = ${allCustomerMinimal.size}"
        )
        assertThat(allCustomerMinimal.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun testeCustomerDetailView() {
        Log.e(
            this.javaClass.simpleName,
            "testeCustomerDetailView(): going to use customer detail view..."
        )

        //Create customers
        val customerPatinhas: Customer =
            Customer(
                1, "Tio Patinhas", "Patinhas Lda",
                "Rua Sesamo 1", "Porto", "+351220000111"
            )
        val customerMikey: Customer =
            Customer(
                2, "Mikey", "Mikey Lda",
                "Rua Sesamo 2", "Aveiro", "+351220000222"
            )

        //Create CustomerTasks with foreign key from Customers
        val customerPatinhasTask1: CustomerTask =
            CustomerTask(
                0, "Patinhas Favourit task 1", "Swim on money",
                customerPatinhas.customerId
            )
        val customerPatinhasTask2: CustomerTask =
            CustomerTask(
                1, "Patinhas Favourit task 2", "Dive on the money",
                customerPatinhas.customerId
            )

        //Insert Customers
        customerDao.insertCustomers(customerPatinhas, customerMikey)
        //Insert CustomerTasks
        customerTaskDao.insertCustomerTask(customerPatinhasTask1)
        customerTaskDao.insertCustomerTask(customerPatinhasTask2)
        //Get CustomerTaskDetailView
        val tasksDetails: List<CustomerTaskDetailView> =
            customerTaskDetailViewDao.loadAllCustomerTaskDetailView()

        Log.e(
            this.javaClass.simpleName,
            "testeCustomerDetailView(): tasksDetails = $tasksDetails"
        )
        assertThat(tasksDetails.size, equalTo(2))
    }
}
