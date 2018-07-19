package com.wujie.kotlinexample

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

        val a = 19
        val b = 90

        val max = if(a > b) {
            b
        } else {
            a
        }
        val c = if (a > b) a else b
        var x = 90
        when (x) {
            0 -> {showStatus(x.toString())}
            1 -> {showStatus(x.toString())}
            else -> {showStatus(x.toString())}
        }
x = 9
        when (x) {
            in 1..10 -> print("x is in the range")
            !in 10..20 -> print("x is outside the range")
            else -> print("none of the above")
        }

        val cc  = arrayOf(1, 2, 3,5)
        for (i in cc.indices) {
            showStatus(cc[i].toString())
        }

        for (a in cc) {
            showStatus(a.toString())
        }



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
    class Runoob {

        var url:String = "ssdd"
        var city:String = "sssss"

        class Runoob constructor(url: String) {
        }
    }

}
