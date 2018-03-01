package com.max.test.testkotlin.entity

import java.util.*

class Contact(val index: String, val name: String) {

    companion object {
        fun  getEnglishContacts(): ArrayList<Contact> {
            val contacts = ArrayList<Contact>()
            contacts.add(Contact ("A", "Abbey"))
            contacts.add(Contact ("A", "Alex"))
            contacts.add(Contact ("A", "Amy"))
            contacts.add(Contact ("A", "Anne"))
            contacts.add(Contact ("B", "Betty"))
            contacts.add(Contact ("B", "Bob"))
            contacts.add(Contact ("B", "Brian"))
            contacts.add(Contact ("C", "Carl"))
            contacts.add(Contact ("C", "Candy"))
            contacts.add(Contact ("C", "Carlos"))
            contacts.add(Contact ("C", "Charles"))
            contacts.add(Contact ("C", "Christina"))
            contacts.add(Contact ("D", "David"))
            contacts.add(Contact ("D", "Daniel"))
            contacts.add(Contact ("E", "Elizabeth"))
            contacts.add(Contact ("E", "Eric"))
            contacts.add(Contact ("E", "Eva"))
            contacts.add(Contact ("F", "Frances"))
            contacts.add(Contact ("F", "Frank"))
            contacts.add(Contact ("G", "Gol"))
            contacts.add(Contact ("G", "Goo"))
            contacts.add(Contact ("G", "GavV"))
            contacts.add(Contact ("H", "Hell"))
            contacts.add(Contact ("I", "Ivy"))
            contacts.add(Contact ("I", "IvyI"))
            contacts.add(Contact ("J", "James"))
            contacts.add(Contact ("J", "John"))
            contacts.add(Contact ("J", "Jessica"))
            contacts.add(Contact ("K", "Karen"))
            contacts.add(Contact ("K", "Karl"))
            contacts.add(Contact ("K", "Kim"))
            contacts.add(Contact ("L", "Leon"))
            contacts.add(Contact ("L", "Lisa"))
            contacts.add(Contact ("M", "Mix"))
            contacts.add(Contact ("N", "Nova"))
            contacts.add(Contact ("O", "Oni"))
            contacts.add(Contact ("P", "Paul"))
            contacts.add(Contact ("P", "Peter"))
            contacts.add(Contact ("Q", "Queue"))
            contacts.add(Contact ("R", "Ruby"))
            contacts.add(Contact ("R", "Robert"))
            contacts.add(Contact ("R", "Ryan"))
            contacts.add(Contact ("S", "Sarah"))
            contacts.add(Contact ("S", "Steven"))
            contacts.add(Contact ("MyToast", "Tom"))
            contacts.add(Contact ("MyToast", "Tony"))
            contacts.add(Contact ("W", "Wendy"))
            contacts.add(Contact ("W", "Will"))
            contacts.add(Contact ("W", "William"))
            contacts.add(Contact ("Z", "Zoe"))
            return contacts
        }

    }
}
