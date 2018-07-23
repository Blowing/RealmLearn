package com.wujie.kotlinexample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import io.realm.Realm

class KotlinExampleActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "KotlinExampleActivity"
    }

    private lateinit var rootLayout: LinearLayout
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_example)
        rootLayout = findViewById(R.id.container)

        var person: Person = Person()

        showStatus("lastName:${person.lastName}")
        person.no = 9
        showStatus("no:${person.no}")
        person.no = 20
        showStatus("no:${person.no}")


        val runoob = Runoob("菜鸟教程", rootLayout, this, "www.hehe.com")
        showStatus(runoob.siteName)
        showStatus(runoob.url)
        showStatus(runoob.country)
        runoob.print("大家有点小调皮")

        var test = Test()

        test.setInterFace(object : TextInterFace {
            override fun test() {
                showStatus("对象表达式创建匿名内部类的实例")
            }
        })

        val s = Student("Runoob", 18, "S12346", 89)
        showStatus("学生名${s.name}")
        showStatus("年龄：${s.age}")
        showStatus("学生号：${s.no}")
        showStatus("成绩: ${s.score}")

    }

    fun hasPrefix(x: Any) = when(x) {
        is String -> x.startsWith("prefix")
        else -> false
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun showStatus(text: String) {
        val textView = TextView(this)
        textView.text = text
        rootLayout.addView(textView)
    }

    private fun basicCRUD(realm: Realm) {
        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...")

    }

    class Person {
        var lastName: String = "zhang"
        get() = field.toUpperCase()
        set

        var no: Int = 100
        get() = field
        set(value) {
            if (value < 10) {
                field = value
            } else {
                field = -1
            }
        }

        var height: Float = 189.4f
        private set

    }

    class Runoob constructor(name: String, layout: LinearLayout, context: Context) {
        var url: String = "http://www.baidu.com"
        var country: String = "CN"
        var siteName = name
        var rootlayout = layout
        var context = context
        init {
            print("初始化网名：${url}")
        }

        constructor(name: String, layout:LinearLayout, context: Context, url: String)
                : this(name,layout, context) {
            print("我的url"+ url)
        }


         fun print(text: String) {
            val textView = TextView(context)
            textView.text = text
            rootlayout.addView(textView)
        }

    }

    open class Base {
        open fun f() {}
    }

    abstract class Derived: Base() {
        override abstract fun f()
    }

    class Outer{
        private val bar: Int = 1
        class Nested {
            fun foo() = 2
        }
    }

    fun main(args: Array<String>) {
        val demo = Outer.Nested().foo()
        showStatus("${demo}")
    }

    class Test {
        var v = "成员属性"
        fun setInterFace(test: TextInterFace) {
            test.test()
        }
    }

    interface TextInterFace {
        fun test()
    }


    open class Person1(var name: String, var age: Int) {
    }

    class Student(name : String, age: Int, var no : String ,var score: Int) : Person1(name, age) {

    }

    open class Person2(name: String) {
        constructor(name: String, age: Int):this(name) {
        }
    }

    class Student4: Person2 {
        constructor(name: String, age: Int, no: String, score: Int):super(name, age) {

        }

    }
}
