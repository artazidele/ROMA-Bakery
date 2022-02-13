package com.example.romabakery.bekereja.views

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import com.example.romabakery.R
import com.example.romabakery.bekereja.Navigation

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        setTitle("Jauns izstrādājums")


        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Navigation().fromTo(this, ItemListActivity())
        return true
//        return super.onOptionsItemSelected(item)
    }



}