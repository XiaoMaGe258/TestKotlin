package com.max.test.testkotlin.fragment;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gjiazhe.wavesidebar.WaveSideBar
import com.max.test.testkotlin.R
import com.max.test.testkotlin.adapter.ContactsAdapter
import com.max.test.testkotlin.entity.Contact
import com.max.test.testkotlin.utils.ContactsComparator
import kotlinx.android.synthetic.main.fragment_home_contacts.view.*
import java.util.*

class HomeContactsFragment : Fragment() {

    private val contacts = ArrayList<Contact>()

    companion object {
        fun getInstance(): HomeContactsFragment {
            return HomeContactsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_contacts, null)
        initView(v)
        return v
    }

    private fun initView(v: View) {
        contacts.addAll(Contact.getEnglishContacts())
        sortContacts()
        v.rv_contacts.layoutManager = LinearLayoutManager(context)
        v.rv_contacts.adapter = ContactsAdapter(contacts, R.layout.item_contacts)
        v.side_bar.setOnSelectIndexItemListener(WaveSideBar.OnSelectIndexItemListener { index ->
            for (i in contacts.indices) {
                if (contacts[i].index == index) {
                    (v.rv_contacts.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(i, 0)
                    return@OnSelectIndexItemListener
                }
            }
        })
    }

    //按字母排序
    private fun sortContacts(){
        val comparator = ContactsComparator()
        Collections.sort(contacts, comparator)
    }
}