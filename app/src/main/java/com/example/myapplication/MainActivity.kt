package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post = HttpPost("http://wwww.testserver.com/userAddMoney")

        post.addHeader("X-Testing-Auth-Secret", "kI7wGju76kjhJHGklk76")
    }
}