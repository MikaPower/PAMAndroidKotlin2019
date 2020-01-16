package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.pam.ufp.edu.pamandroidkotlin2019.R
import com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts.LoaderCustomersContentDatabase

//import com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_itemcontact_list.*
import kotlinx.android.synthetic.main.itemcontact_list_content.view.*
import kotlinx.android.synthetic.main.itemcontact_list.*
import java.lang.ref.WeakReference

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemContactDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemContactListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemcontact_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (itemcontact_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

       // setupRecyclerView(itemcontact_list)
            CustomersDatabaseAsyncTask(this).execute("Nothing useful task");
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, LoaderCustomersContentDatabase.CUSTOMER_ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemContactListActivity,
        private val values: List<LoaderCustomersContentDatabase.CustomerItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as LoaderCustomersContentDatabase.CustomerItem
                if (twoPane) {
                    val fragment = ItemContactDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemContactDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.itemcontact_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemContactDetailActivity::class.java).apply {
                        putExtra(ItemContactDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.itemcontact_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.id
            holder.contentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
    companion object {

        /**
         * class SomeTask : AsyncTask<Params, Progress, Result>
         *      Params: type of the parameters sent to doInBackground()
         *      Progress: type of the progress parameter sent to onProgressUpdate()
         *      Result: type of result of the background computation passed to onPostExecute().
         */
        class CustomersDatabaseAsyncTask(activity: ItemContactListActivity) :
            AsyncTask<String, Int, String>() {

            //private val parentActivity: ContactDetailListActivity = activity
            private val activityReference: WeakReference<ItemContactListActivity> =
                WeakReference(activity)

            /**
             * Invoked on the UI thread before the task is executed.
             */
            override fun onPreExecute() {
                super.onPreExecute()
                Log.e(
                    this.javaClass.simpleName,
                    "onPreExecute(): going to do something before create CustomersDatabase..."
                )
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                //...
            }

            /**
             * invoked on the background thread immediately after onPreExecute().
             */
            override fun doInBackground(vararg params: String?): String {
                Log.e(
                    this.javaClass.simpleName,
                    "doInBackground(): going to create CustomersDatabase, params = ${params[0]}"
                )
                val parentActivity = activityReference.get()
                Log.e(
                    this.javaClass.simpleName,
                    "doInBackground(): LoaderCustomersContentDatabase.CUSTOMER_ITEMS.size = ${LoaderCustomersContentDatabase.CUSTOMER_ITEMS.size}"
                )
                if (parentActivity != null && LoaderCustomersContentDatabase.CUSTOMER_ITEMS.size == 0) {
                    //parentActivity.buildCustomersContentDatabase.createDb(parentActivity.applicationContext)
                    LoaderCustomersContentDatabase.createDb(parentActivity.applicationContext)
                    this.publishProgress(50)
                    LoaderCustomersContentDatabase.addSampleItemsToDatabase()
                    this.publishProgress(100)
                    //return "OK"
                }
                return "OK"
            }

            /**
             * invoked on the UI thread after a call to publishProgress().
             */
            override fun onProgressUpdate(vararg progress: Int?) {
                val parentActivity = activityReference.get()
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, "Progress: $progress%", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            /**
             * invoked on the UI thread after the background computation finishes.
             */
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.e(
                    this.javaClass.simpleName,
                    "onPostExecute(): after creating CustomersDatabase, result=$result"
                )
                val parentActivity = activityReference.get()
                if (parentActivity != null && result == "OK") {
                    parentActivity.setupRecyclerView(parentActivity.itemcontact_list)
                }
            }
        }
    }

}
