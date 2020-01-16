package com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pam.ufp.edu.pamandroidkotlin2019.R
import com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dbcontacts.LoaderCustomersContentDatabase
import com.example.pam.ufp.edu.pamandroidkotlin2019.masterdetail2019.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_itemcontact_detail.*
import kotlinx.android.synthetic.main.itemcontact_detail.view.*

/**
 * A fragment representing a single ItemContact detail screen.
 * This fragment is either contained in a [ItemContactListActivity]
 * in two-pane mode (on tablets) or a [ItemContactDetailActivity]
 * on handsets.
 */
class ItemContactDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: LoaderCustomersContentDatabase.CustomerItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = LoaderCustomersContentDatabase.CUSTOMER_ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.content
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.itemcontact_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.itemcontact_detail.text = it.details
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
