package ma.maroc.bookappversion10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  sign: Button
    private lateinit var  email: EditText
    private lateinit var  age: EditText
    private lateinit var  password: EditText
    private lateinit var  phone: EditText
    private lateinit var  name: EditText
    private lateinit var dataBaseHandler: MyDBHelper

    private val activity = this@SignActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign)
        // initializing the views
        initViews()
        // initializing the listeners
        try {
            initListeners()
        }catch (e:Exception){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
        }

        // initializing the objects
        initObjects()
    }
    private fun initViews() {
        name = findViewById(R.id.name)
        age = findViewById(R.id.age)
        phone = findViewById(R.id.phone)
        password = findViewById(R.id.password)
        sign = findViewById(R.id.sign)
        email = findViewById(R.id.email)

    }
    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        sign.setOnClickListener(this)
    }
    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        dataBaseHandler = MyDBHelper(activity)

    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.sign -> postDataToSQLite()
        }
    }
    private fun postDataToSQLite() {
        if(name!!.text.toString().trim()==""|| age!!.text.toString()=="" || phone!!.text.toString().trim()==""
            || phone!!.text.toString().trim()=="" || email!!.text.toString().trim()=="" || password!!.text.toString().trim()==""
        ){
            Toast.makeText(this,"All information are required!!", Toast.LENGTH_LONG).show()
        }
        else{
            if (!DataManager!!.checkUser(dataBaseHandler,email = email!!.text.toString().trim())) {
                var user = User(name = name!!.text.toString().trim(),
                    age = age!!.text.toString().toInt(),
                    phone = phone!!.text.toString().trim(),
                    email = email!!.text.toString().trim(),
                    password = password!!.text.toString().trim())
                DataManager!!.addUser(dataBaseHandler,user)
                // Snack Bar to show success message that record saved successfully
                Toast.makeText(this,"Account reated succefully! You can log in now!!", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this,"Email already exist!", Toast.LENGTH_LONG).show()
            }
        }

    }
}