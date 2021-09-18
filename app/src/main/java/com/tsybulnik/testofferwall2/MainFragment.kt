package com.tsybulnik.testofferwall2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.tsybulnik.testofferwall2.model.DataList
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import java.net.URL
import kotlin.coroutines.CoroutineContext


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(), CoroutineScope {
    private val viewModel: DataViewModel by activityViewModels()
    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(Dispatchers.Main) {
            val allIds = withContext(Dispatchers.Default) {
                val apiResponse =
                    URL("http://demo3005513.mockable.io/api/v1/entities/getAllIds").readText()
                return@withContext apiResponse
            }
            val gson = Gson()
            val dataList = gson.fromJson(allIds, DataList::class.java)

            val idList: ArrayList<Int> = arrayListOf()
            for (n in dataList.data.indices) {
                idList.add(dataList.data[n].id)
            }
                // Получили первый элемент
            launch(Dispatchers.Main) {
                val str = withContext(Dispatchers.IO){
                    val apiResponse =
                        URL("http://demo3005513.mockable.io/api/v1/object/${idList[0]}").readText()
                    return@withContext apiResponse
                }
                viewModel.str.value = str
            }
                // Закончили получать первый элемент


            // Получили массив всех запросов по id
            launch(Dispatchers.Main) {
                val allStringOfId = withContext(Dispatchers.IO){
                    val ss:ArrayList<String> = arrayListOf()
                    for (n in dataList.data.indices) {
                        ss.add(URL("http://demo3005513.mockable.io/api/v1/object/${idList[n]}").readText())
                    }
                    return@withContext ss
                }
                // Закончили получать массив стрингов, с типом View и url/ текстом


                 var i = 0
                btOnWard.setOnClickListener {
                    i+= 1
                    if (i < allStringOfId.size) {
                        viewModel.str.value = allStringOfId[i]
                    } else {
                        i = 0
                        viewModel.str.value = allStringOfId[i]
                    }
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        job.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

}