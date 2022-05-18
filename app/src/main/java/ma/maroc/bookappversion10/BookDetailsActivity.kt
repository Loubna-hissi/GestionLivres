package ma.maroc.bookappversion10


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class BookDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private  var  IdBook: Int = 0
    private  var  IdUser: Int = 0
    private lateinit var  BookName: TextView
    private lateinit var  BookStock: TextView
    private lateinit var  BookVoix: TextView
    private lateinit var  BookAuthor: TextView
    private lateinit var  BookCategory: TextView
    private lateinit var  BookPage: TextView
    private lateinit var  BookDesc: TextView
    private lateinit var Bookimage:ImageView
    private lateinit var  reserveBtn: Button
    private lateinit var  voteBtn: Button
    private lateinit var book:BookN
    private val activity = this@BookDetailsActivity

    private lateinit var dataBaseHandler: MyDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        val intent = getIntent()
        IdBook = intent.getIntExtra("IdBook",0)
        IdUser = intent.getIntExtra("IdUser",0)
        Toast.makeText(this, "${IdBook} and ${IdUser}", Toast.LENGTH_LONG).show()

        initViews()
        // initializing the listeners
        initListeners()
        // initializing the objects
        initObjects()
        //show the book details
        showBookDetails()
    }

    private fun showBookDetails() {
        book=dataBaseHandler!!getBookData(IdBook)
        BookName.text=book.name
        val bitmap=getBimap(book.image)
        Bookimage.setImageBitmap(bitmap)
        BookStock.text="In Stock : "+book.stock.toString()
        BookVoix.text="Number of like : "+book.voix.toString()
        BookDesc.text="Description :"+book.des.toString()
        BookPage.text="Number od Pages : "+book.nbpage.toString()
        BookAuthor.text="Number od Pages : "+book.auteur.toString()
        BookCategory.text="Category : "+book.category.toString()
    }

    private fun initViews() {
        BookName = findViewById(R.id.Name_Book)
        BookStock = findViewById(R.id.Stock)
        Bookimage=findViewById(R.id.image)
        BookPage = findViewById(R.id.Pages)
        BookAuthor= findViewById(R.id.Name_Author)
        BookCategory = findViewById(R.id.Category)
        BookDesc= findViewById(R.id.Description)
        BookVoix = findViewById(R.id.Voix)
        reserveBtn=findViewById(R.id.reserve)
        voteBtn=findViewById(R.id.vote)
    }

    private fun initObjects() {
        dataBaseHandler = MyDBHelper(activity)
    }

    fun initListeners() {
        reserveBtn!!.setOnClickListener(this)
        voteBtn!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.reserve -> reserveFromSQLite()
            R.id.vote -> voteFromSQLite()
        }
    }

    private fun voteFromSQLite() {
        if(!DataManager!!.checkVote(dataBaseHandler,IdUser,IdBook)){
            DataManager!!.voteBook(dataBaseHandler,IdBook,IdUser)
            DataManager!!.updateBookVoix(dataBaseHandler,book.voix.toString(),book.id.toString())
            Toast.makeText(this,"You Liked this book successfully!! Thank you!!", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"You have already voted on this book!! Try to Like another one!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun reserveFromSQLite() {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm")
        val dateRes: String = simpleDateFormat.format(Date())
        if(!DataManager!!.checkRes(dataBaseHandler,IdBook,IdUser)){
            DataManager!!.reserveBook(dataBaseHandler,IdBook, IdUser,dateRes,book.stock.toString())
            Toast.makeText(this,"Book reserved successfully!!", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"You have already reserved this book!!", Toast.LENGTH_LONG).show()

        }
    }
    private fun getBimap(image: ByteArray): Bitmap? {
        val bitmap= BitmapFactory.decodeByteArray(image,0,image.size)
        return bitmap
    }

}


