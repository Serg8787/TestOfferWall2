package com.tsybulnik.testofferwall2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        openFrag(MainFragment.newInstance(),R.id.frameLayout1)
        openFrag(ViewFragment.newInstance(),R.id.frameLayout2)
    }

    private fun openFrag(f: Fragment, id:Int){
        supportFragmentManager.beginTransaction().replace(id,f).commit()
    }
}