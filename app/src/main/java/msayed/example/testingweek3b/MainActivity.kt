package msayed.example.testingweek3b

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {

    companion object MainActivityData {
        var people:List<Person> = listOf<Person>()
    }

    lateinit var et:EditText
    lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et = findViewById(R.id.editTextText)
        tv = findViewById(R.id.textView)
    }

    fun onClickButton(view: View) {
        when(view.id){
            R.id.button -> {
                Toast.makeText(this, "READ FILE", Toast.LENGTH_SHORT).show()

                try {
                    // reading from data/data/packagename/File
                    val fin = openFileInput("test.txt")
                    val isr = InputStreamReader(fin)
                    val inputBuffer = CharArray(100)
                    var str: String? = ""
                    var charRead: Int
                    while (isr.read(inputBuffer).also { charRead = it } > 0) {
                        val readString = String(inputBuffer, 0, charRead)
                        str += readString
                    }
                    et.setText(str)
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }
            }
            R.id.button2 -> {
                Toast.makeText(this, "WRITE FILE", Toast.LENGTH_SHORT).show()

                try {
                    // to save to file "test.txt" in data/data/packagename/File
                    val ofile = openFileOutput("test.txt", MODE_PRIVATE)
                    val osw = OutputStreamWriter(ofile)
                    osw.write(et.getText().toString())
                    osw.flush()
                    osw.close()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }
            }
            R.id.button3 -> {
                Toast.makeText(this, "READ JSON", Toast.LENGTH_SHORT).show()

                //json initial file in assets folder
                val jsonStringFromFile = getJSONDataFromAsset(this, "test.json")
                val listPersonType = object: TypeToken<List<Person>>() {}.type
                people = Gson().fromJson(jsonStringFromFile, listPersonType)
                tv.setText(people.toString())
            }
            R.id.button4 -> {
                Toast.makeText(this, "WRITE JSON", Toast.LENGTH_SHORT).show()

                try {
                    // to save to file "test.txt" in data/data/packagename/File
                    val ofile: FileOutputStream = openFileOutput("test.json", MODE_PRIVATE)
                    val osw = OutputStreamWriter(ofile)
                    var jsonList = Gson().toJson(people)
                    for(person in jsonList)
                    {
                        osw.write(person.toString())
                    }
                    osw.flush()
                    osw.close()

                } catch(ioe:IOException) {
                    ioe.printStackTrace()
                }
            }
            R.id.buttonSend -> {
                Toast.makeText(this, "SEND JSON", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,ViewDataActivity::class.java).apply {
                    putExtra("mypeoplelist",people.toString())
                }
                startActivity(intent)
            }
        }
    }

    fun getJSONDataFromAsset(context: Context, filename:String):String? {
        val jsonString:String
        try {
            // Use bufferedReader
            // Closable.use will automatically close the input at the end of execution
            // it.reader.readText()  is automatically
            jsonString = this.assets.open(filename).bufferedReader().use {
                it.readText() }
        } catch(ioException:IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}