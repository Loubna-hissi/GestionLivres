package ma.maroc.bookappversion10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  loginBtn:Button
    private lateinit var  email: EditText
    private lateinit var  password:EditText
    private lateinit var  sign:TextView
    private lateinit var btnExit :Button
    private val activity = this@LoginActivity

    private lateinit var dataBaseHandler: MyDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        //variables
        initViews()
        // initializing the listeners
            initListeners()

        // initializing the objects
        initObjects()

    }
    fun initViews() {
        loginBtn = findViewById(R.id.login)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        sign=findViewById(R.id.signbtnfirst)
        btnExit=findViewById(R.id.Exituser)
    }
    /**
     * This method is to initialize listeners
     */
    fun initListeners() {
        loginBtn.setOnClickListener(this)
        sign.setOnClickListener(this)
        btnExit.setOnClickListener(this)


    }
    private fun initObjects() {
        dataBaseHandler = MyDBHelper(activity)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login -> verifyFromSQLite()
            R.id.signbtnfirst -> {
                // Navigate to RegisterActivity
                val intentRegister = Intent(this, SignActivity::class.java)
                startActivity(intentRegister)
            }
            R.id.Exituser->{
                val intentRegister1 = Intent(this, MainActivity::class.java)
                startActivity(intentRegister1)
            }
        }
    }
    private fun verifyFromSQLite() {

        if (DataManager!!.checkUser(dataBaseHandler,email!!.text.toString().trim { it <= ' ' }, password!!.text.toString().trim { it <= ' ' })) {
            val accountsIntent = Intent(activity, HomeActivity::class.java)
            val id=DataManager!!.readId(dataBaseHandler,email!!.text.toString())
            accountsIntent.putExtra("userId",id)
            startActivity(accountsIntent)
        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(this,"Email or password incorrect!!", Toast.LENGTH_LONG).show()
        }
    }

}