package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * The Room database that contains the tables for all entities (e.g. Customer), Customertask, etc.).
 *
 * Get an instance of created database using following code:
 *   val db = Room.databaseBuilder(applicationContext, CustomersDatabase::class.java, "Customers.db").build()
 *
 */
@Database(
    entities = [Customer::class, CustomerTask::class],
    exportSchema = false,
    views = [CustomerTaskDetailView::class],
    version = 10

)
abstract class CustomersDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun customerTaskDao(): CustomerTaskDao
    abstract fun customerTaskDetailViewDao(): CustomerTaskDetailViewDao

    //Behaves like a static attribute
    companion object {

        @Volatile
        private var INSTANCE: CustomersDatabase? = null

        fun getCustomerDatabaseInstance(context: Context): CustomersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, CustomersDatabase::class.java, "Customers.db")
                .fallbackToDestructiveMigration()
                //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `tasktypes` (`id` INTEGER, `tasktitle` TEXT, " +
                    "PRIMARY KEY(`id`))")
        }
    }

    val MIGRATION_2_3: Migration
        get() = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasktypes ADD COLUMN taskpriority INTEGER")
            }
        }
}
