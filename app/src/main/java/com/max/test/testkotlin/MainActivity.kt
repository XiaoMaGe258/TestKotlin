package com.max.test.testkotlin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mContext: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_action1.setOnClickListener { tv_text.text = ""+getIfInt(1) } //直接使用view的id。不需要findViewById
        btn_action2.setOnClickListener { tv_text.text = ""+getIfInt("2") } //但是一定要在build.gradle中引入apply plugin: 'kotlin-android-extensions'
        btn_action3.setOnClickListener { tv_text.text = ""+getIfInt("a") } //直接在点击事件中写调用方法，不需要在new OnClick方法
        btn_action4.setOnClickListener { tv_text.text = ""+getIfInt("1112ws") }
        btn_action5.setOnClickListener {
            val intent = Intent(mContext, MainTabActivity::class.java)
            startActivity(intent)
        }
//        btn_action1.setOnClickListener { tv_text.text = ""+whenItems(1) }
//        btn_action2.setOnClickListener { tv_text.text = ""+whenItems("Hello") }
//        btn_action3.setOnClickListener { tv_text.text = ""+whenItems(100000L) }
//        btn_action4.setOnClickListener { tv_text.text = ""+whenItems(et_input.text) }
    }

    fun action1() {
        tv_text.text = "kotlin 1 + 2 = ${sum(1, 2)}" //可以在字符串中直接调用方法
    }

    fun action2() {
        var whatVal: Int  //不直接初始化，就要定义类型。 定义类型：变量名 + 冒号 + 变量类型
        whatVal = 2
        var intVal = 3 //如果直接赋值，就不需要声明变量类型
        tv_text.text = "kotlin 2 + 3 = ${whatVal + intVal}" //可以在字符串中直接写计算表达式
    }

    fun action3() {
        tv_text.text = "kotlin 4 and 5 max value is = ${maxVal(4, 5)}"
    }

    fun action4() {
        tv_text.text = "kotlin String value = ${getIfInt("6")}"
    }

    fun getIfInt(obj: Any): Int? { //？为可以返回空   Any为任意类型
        if (obj is String) { // is 等于 InstanceOf
            return try {
                Integer.parseInt(obj)
            } catch (e: Exception) {
                null
            }
        }else if(obj is Int){
            return obj
        }
        return null
    }

    fun maxVal(a: Int, b: Int): Int {
        return if (a > b) a else b //if表达式 可以替换3元运算符
    }

    fun sum(a: Int, b: Int): Int { //两个Int类型参数，并且有Int类型返回值
        return a + b
    }

    fun forItems() {
        val items = listOf("apple", "banana", "kiwi") //val
        for (item in items) {
            println(item)
        }
    }

    fun whileItems() {
        val items = listOf("apple", "banana", "kiwi")
        var index = 0
        while (index < items.size) {
            println("item at $index is ${items[index]}")
            index++
        }
    }

    fun whenItems(obj: Any): String =
            when (obj) {
                1 -> "One"
                "Hello" -> "Greeting"
                is Long -> "Long"
                !is String -> "Not a string"
                else -> "Unknown"
            }
}
