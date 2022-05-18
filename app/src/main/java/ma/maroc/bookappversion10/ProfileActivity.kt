package ma.maroc.bookappversion10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import ma.maroc.bookappversion10.DataManager
import ma.maroc.bookappversion10.MyDBHelper
import ma.maroc.bookappversion10.R
import ma.maroc.bookappversion10.User

class ProfileActivity :  AppCompatActivity(), View.OnClickListener{

    private lateinit var  update: Button
    private lateinit var  name: EditText
    private lateinit var  email: EditText
    private lateinit var  age: EditText
    private lateinit var  password: EditText
    private lateinit var  phone: EditText
    private lateinit var dataBaseHandler: MyDBHelper

    private val activity = this@ProfileActivity
    private var  UserId: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_profile)
        val intent = getIntent()
        UserId = intent.getIntExtra("userId",0)
        initObjects()
        initViews()
        initChamps()
        initListeners()
    }

    private fun initViews() {
        name = findViewById(R.id.name)
        age = findViewById(R.id.age)
        phone = findViewById(R.id.phone)
        password = findViewById(R.id.password)
        email = findViewById(R.id.email)
        update = findViewById(R.id.UpdateProfile)
        Toast.makeText(this,"idUser : ${UserId}", Toast.LENGTH_LONG).show()

    }

    private fun initChamps() {
        var UserDetails=DataManager!!.getUserData(dataBaseHandler,UserId)
        name.setText(UserDetails.name)
        age.setText(UserDetails.age.toString())
        phone.setText(UserDetails.phone)
        email.setText(UserDetails.email)
        password.setText(UserDetails.password)
    }
    private fun initListeners() {
        update!!.setOnClickListener(this)
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
            R.id.UpdateProfile -> UpdateDataInSQLite()
        }
    }
    private fun UpdateDataInSQLite() {
        var userData= User(UserId,name.text.toString(),age.text.toString().toInt(),phone.text.toString()
            ,email.text.toString(),password.text.toString())
        DataManager!!.updateProfileUser(dataBaseHandler,userData)
        Toast.makeText(this,"Your profile has been updated successfully!!", Toast.LENGTH_LONG).show()

    }
}