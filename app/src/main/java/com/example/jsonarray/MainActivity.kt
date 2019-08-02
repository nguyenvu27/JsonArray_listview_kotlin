package com.example.jsonarray

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class MainActivity : AppCompatActivity() {

    var arrayCourse : ArrayList<String> = ArrayList()
    var adapterCourse: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlJSON = "https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json"
        ReadJson().execute(urlJSON)

        adapterCourse = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayCourse)
        lv.adapter = adapterCourse
    }
    inner class ReadJson : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {
            var content : StringBuilder = StringBuilder()
            val url : URL = URL(params[0])
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStreamReader: InputStreamReader = InputStreamReader(urlConnection.inputStream)
            val bufferedReader : BufferedReader = BufferedReader(inputStreamReader)

            var line : String = ""

            try {
                do {
                    line = bufferedReader.readLine()
                    if (line !=null){
                        content.append(line)
                    }
                }while (line != null)
                bufferedReader.close()
            }catch (e: Exception){
                Log.d("AAA", e.toString())
            }
            return content.toString()

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val jSonObj : JSONObject = JSONObject(result)
            val jsonArray : JSONArray = jSonObj.getJSONArray("danhsach")
            var tenKH : String = ""
            for (course in 0..jsonArray.length() -1 ){
                var objCourse : JSONObject = jsonArray.getJSONObject(course)
                tenKH = objCourse.getString("khoahoc")
                arrayCourse.add(tenKH)
            }
            adapterCourse?.notifyDataSetChanged()
//            Toast.makeText(this@MainActivity, tenKH, Toast.LENGTH_LONG).show()
        }
    }
}
