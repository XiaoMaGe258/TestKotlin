package com.max.test.testkotlin.entity

/**
 * MContent 用 object修饰，相当于单例
 * 这里把类声明时的class关键字改成了object，这个类里面的成员默认都是static的.
 */
object MContent {

    //静态属性
    val telephone: String = "13888888888"

    fun getTel(): String{
        return telephone
    }
}