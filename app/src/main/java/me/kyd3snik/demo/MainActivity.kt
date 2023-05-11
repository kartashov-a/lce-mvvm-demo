package me.kyd3snik.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.kyd3snik.demo.media.ui.MediaFeedActivity
import me.kyd3snik.demo.simple.SimpleFeatureActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.simple_feature).setOnClickListener {
            startActivity(Intent(this, SimpleFeatureActivity::class.java))
        }

        findViewById<View>(R.id.media_feed_feature).setOnClickListener {
            startActivity(Intent(this, MediaFeedActivity::class.java))
        }
    }
}