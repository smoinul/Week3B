package msayed.example.testingweek3b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewDataActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)

        var textView: TextView = findViewById(R.id.textViewViewDataJSON)
        textView.text  = intent.extras?.getString("mypeoplelist", "")

        // setting up the recyclerView
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(MainActivity.people)  // pass in data to be displayed
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPeople).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter  }
    }

    fun onButtonClose(view: View) {
        finish()
    }
}