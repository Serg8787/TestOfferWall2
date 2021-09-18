package com.tsybulnik.testofferwall2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel() : ViewModel() {

    val str : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}