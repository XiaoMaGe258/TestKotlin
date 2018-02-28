package com.max.test.testkotlin.utils;

import com.max.test.testkotlin.entity.Contact;

import java.util.Comparator;

/**
 * 联系人比较器，按姓名排序
 */

public class ContactsComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact lhs, Contact rhs) {
        /**
         * compareTo()：大于0表示前一个数据比后一个数据大， 0表示相等，小于0表示前一个数据小于后一个数据
         * 相等时会走到equals()，这里讲姓名年龄都一样的对象当作一个对象
         */
        return lhs.getIndex().compareTo(rhs.getIndex());
    }

}