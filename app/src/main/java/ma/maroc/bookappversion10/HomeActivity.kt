package ma.maroc.bookappversion10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ma.maroc.bookappversion10.*

class HomeActivity : AppCompatActivity() ,View.OnClickListener{

    private val activity = this@HomeActivity
    private val context=this
    private lateinit var dataBaseHandler: MyDBHelper
    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var inputSearch: EditText
    private lateinit var searchBtn: ImageButton
    private lateinit var type: TextView
    private lateinit var listBooks: ArrayList<BookN>
    private var userId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        userId = intent.getIntExtra("userId",0)
        dataBaseHandler = MyDBHelper(activity)
        searchBtn=findViewById(R.id.search)
        inputSearch=findViewById(R.id.input)
        /* DataManager!!.addBook(dataBaseHandler)*/
        initViews()
        initObjects()
        // initializing the listeners
        initListeners()



    }

    private fun initListeners() {
        searchBtn!!.setOnClickListener(this)
    }
    private fun initObjects() {
        listBooks = DataManager!!.getAllBooks(dataBaseHandler)
        recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        recyclerViewBooks.adapter = BooksRecyclerAdapter(this,listBooks,userId)
    }

    private fun initViews() {
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)
        type=findViewById(R.id.type)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.search -> searchBook()
        }
    }
    private fun searchBook() {
        if(inputSearch.text.isNullOrEmpty()){
            Toast.makeText(this,"Enter the name of book!", Toast.LENGTH_LONG).show()
        }
        else{
            listBooks.clear()
            listBooks = DataManager!!.getBookByName(dataBaseHandler,inputSearch.text.toString())
            if(listBooks.size==0){
                type.text="No result Found!"
            }
            else{
                recyclerViewBooks.layoutManager = LinearLayoutManager(this)
                recyclerViewBooks.adapter = BooksRecyclerAdapter(this,listBooks,userId)
                type.text="result:"
            }

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {

            R.id.GoProfile -> {
                val profileIntent = Intent(activity, ProfileActivity::class.java)
                profileIntent.putExtra("userId",userId)
                startActivity(profileIntent)
                true
            }
            R.id.LogOut -> {
                val exitIntent = Intent(activity, LoginActivity::class.java)
                startActivity(exitIntent)
                true
            }

            R.id.AllBooks -> {
                type.text="All Books"
                listBooks.clear()
                listBooks = DataManager!!.getAllBooks(dataBaseHandler)
                recyclerViewBooks.layoutManager = LinearLayoutManager(this)
                recyclerViewBooks.adapter = BooksRecyclerAdapter(this,listBooks,userId)
                true
            }

            R.id.MoreLiked
            -> {
                type.text="More Liked"
                listBooks.clear()
                listBooks = DataManager!!.getBooksLiked(dataBaseHandler)
                recyclerViewBooks.layoutManager = LinearLayoutManager(this)
                recyclerViewBooks.adapter = BooksRecyclerAdapter(this,listBooks,userId)
                true
            }
            R.id.BookRes
            -> {
                type.text="Books Reserved"
                listBooks.clear()
                listBooks = DataManager!!.getBooksReserved(dataBaseHandler,userId)
                recyclerViewBooks.layoutManager = LinearLayoutManager(this)
                recyclerViewBooks.adapter = BooksRecyclerAdapter(this,listBooks,userId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}