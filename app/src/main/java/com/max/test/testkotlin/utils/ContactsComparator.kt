package com.max.test.testkotlin.utils

import com.max.test.testkotlin.entity.Contact

import java.util.Comparator

/**
 * 联系人比较器，按姓名排序
 */
class ContactsComparator: Comparator<Contact> {

    override fun compare(o1: Contact?, o2: Contact?): Int {
        /**
         * compareTo()：大于0表示前一个数据比后一个数据大， 0表示相等，小于0表示前一个数据小于后一个数据
         * 相等时会走到equals()，这里讲姓名年龄都一样的对象当作一个对象
         */
        return o1!!.index.compareTo(o2!!.index)
    }
}