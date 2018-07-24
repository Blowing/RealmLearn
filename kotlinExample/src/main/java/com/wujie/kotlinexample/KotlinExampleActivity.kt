package com.wujie.kotlinexample

import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import io.realm.Realm
import java.util.Collections.sort
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

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

        val demo = Outer.Nested().foo()
        showStatus("$demo")

        val demo1 = Outer1().Inner().foo()
        showStatus("$demo1")
        val demo2 = Outer1().Inner().innerTest()
        showStatus("$demo2")

        val test = Test()
        test.setInterFace(object : TestInterFace {
            override fun test() {
                showStatus("对象表达式创建匿名内部类的实例")
            }
        })


        val s = Student("猪八戒", 800, "10086", 99)
        showStatus(s.name)
        showStatus(s.age.toString())
        showStatus(s.no)
        showStatus(s.score.toString())

        showStatus(Student2().study())

        showStatus(Foo1().x.toString())

        showStatus(Child().bar())
        showStatus(Child().foo())

        showStatus(C().foo())
        showStatus(C().bar())
        showStatus(D().bar())
        showStatus(D().foo())
        showStatus(D().print())
        val l = mutableListOf<Int>(1, 2, 3)
        l.swap(0, 2)
        showStatus(l.toString())
        val last = listOf(1, 2, 3,4)
        showStatus("last:${last.lastIndex}")
        showStatus("no:${MyClass.no}")
        showStatus(MyClass.foo())
        showStatus(F().caller(E()))

        val tom = User(name = "tom", age = 12)
        val bigTom = tom.copy(age = 13)
        showStatus(tom.toString())
        showStatus(bigTom.toString())

        val jane = User("Jane", 25)
        val (name, age) = jane
        showStatus("$name, $age years of age")

        var boxInt = Box<Int>(10)
        var boxString = Box<String>("德玛西亚")
        showStatus(boxInt.value.toString())
        showStatus(boxString.value)

        doPrintln(23)
        doPrintln("wujie")
        doPrintln(true)
        sort(listOf<Int>(1,2,3))
        showStatus(Color.BLACK.name)
        var s1 = Site
        var s2 = Site
        s1.url = "www.baidu.com"
        showStatus(s1.url)
        showStatus(s2.url)
        showStatus(KotlinExampleActivity.Site.url)
        showStatus(KotlinExampleActivity.Site.name)

        val b = BaseImpl(10)
        showStatus(Derivedd(b).print())

        val site1 = Site1(mapOf("name" to "菜鸟教程", "url" to "www.runoob.com"))
        showStatus(site1.name)
        showStatus(site1.url)

        var artist: MediaStore.Audio.Artists ? = null
        artist!!.equals("ss")

    }
    private val List<Int>.lastIndex: Int
        get() = 1

    fun hasPrefix(x: Any) = when (x) {
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

        constructor(name: String, layout: LinearLayout, context: Context, url: String)
                : this(name, layout, context) {
            print("我的url" + url)
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

    abstract class Derived : Base() {
        override abstract fun f()
    }

    class Outer {
        private val bar: Int = 1
        class Nested {
            fun foo() = 2
        }
    }

    class Outer1 {
        private val bar: Int = 1
        var v = "成员属性"
        inner class Inner {
            fun foo() = bar
            fun innerTest() : Int {
                var o = this@Outer1
                return o.bar

            }
        }
    }

    class Test {
        var v = "成员属性"
        fun setInterFace(test: TestInterFace) {
            test.test()
        }
    }

    interface TestInterFace{
        fun test()
    }


    open class Person1(var name: String, var age: Int) {

    }
    class Student(name: String, age: Int, var no:String , var score: Int) : Person1(name, age) {

    }

    open class Person2 {
        open fun study() : String {
            return "我毕业了"
        }
    }

    class Student2 : Person2() {
        override fun study(): String {
            return "我在读大学"
        }
    }

    open class Foo {
        open val x: Int
        get() = 23
    }

    class Foo1 : Foo() {
         override val x: Int = 12
    }

    interface Fooo {
        val count: Int
    }

    class bar1(override val count: Int) : Fooo

    class bar2: Fooo {
        override val count: Int
            get() = 0

    }

    interface  MyInterface {
        val name: String
        fun bar() : String
        fun foo() : String {
            return "foo"
        }
    }

    class Child : MyInterface {
        override val name: String = "sss"
        override fun bar() : String {
            return "bar"
        }
    }

    interface A {
        fun foo(): String{
            return "A"
        }
        fun bar():String
    }

    interface B {
        fun foo() : String{
            return "B"
        }
        fun bar() : String {
            return "bar"
        }
    }

    class C : A {
        override fun bar() : String{
            return "bar"
        }
    }

    class D : A, B {
        override fun bar(): String {
           return super<B>.bar()
        }

        override fun foo(): String {
            return super<A>.foo() + super<B>.foo()
        }

    }

    fun D.print() : String{
        return "print"
    }

    fun MutableList<Int>.swap(index1 : Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }

    class MyClass {
        companion object {

        }
    }

    fun MyClass.Companion.foo(): String{
        return ("伴生对象的扩展函数")
    }

    val MyClass.Companion.no: Int
        get() = 89


    class E {
        fun bar():String = "E bar"
    }

    class F {
        fun baz(): String = "F baz"

        fun foo(): String = "F foo"
        fun E.foo() : String = bar() + baz() + this@F.foo()

        fun caller(e : E): String  = e.foo()
    }

    data class User (val name: String, val age: Int)

    class Box<T>(t: T) {
        val value = t
    }

    fun <T> doPrintln(content: T) {
        when (content) {
            is Int ->showStatus("整形数字为$content")
            is String ->showStatus("字符串转换为大写为${content.toUpperCase()}")
            else -> showStatus("T是其他类型")
        }
    }

    enum class Color{
        RED,
        BLACK,
        BLUE,
        GREEN,
        WHITE
    }

    class G {
        private fun foo() = object {
            val x: String = "x"
        }

        private fun publicFoo() =  {
            val x: String = "x"
        }

        fun bar() {
            val x1 = foo().x

        }
    }

    object Site {
        var url: String = ""
        val name: String = "菜鸟教程"
    }


    //类委托
    interface Basee {
        fun print() : String
    }

    class BaseImpl(val x: Int) : Basee {
        override fun print() : String {
            return x.toString()
        }

    }
    class Derivedd(a : Basee) : Basee by a

    class Example {
        var p: String by Delegate()
    }
    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, 这里委托了${property.name}属性"
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {

        }
    }

    class Userr {
        var name: String by Delegates.observable("初始值") {
            property, oldValue, newValue ->
            println("旧值：$oldValue ->新值：$newValue")
        }
    }

    class Site1(val map: Map<String, Any?>) {
        val name: String by map
        val url: String by map
    }
}
