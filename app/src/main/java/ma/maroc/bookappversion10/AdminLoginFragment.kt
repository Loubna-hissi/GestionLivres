package ma.maroc.bookappversion10


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var myHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_admin_login, container, false)
        val nom=view.findViewById<EditText>(R.id.nom)
        val pass=view.findViewById<EditText>(R.id.password)
        myHelper = MyDBHelper(activity)
        view.findViewById<Button>(R.id.ExitAdmin).setOnClickListener {
            findNavController().navigate(R.id.action_adminLoginFragment_to_dashboardFragment)
        }
        view.findViewById<Button>(R.id.loginAdmin).setOnClickListener {
            DataManager.insererInformation(myHelper)
            val arr = DataManager.recuper_tt_Information(myHelper)
            if (arr != null) {
               if(nom ==null || pass==null){
                   Toast.makeText(activity,"Please enter name and password !!",Toast.LENGTH_LONG).show()
               }else if(nom.text.toString() != arr[0].nom || pass.text.toString() != arr[0].password){
                   Toast.makeText(activity,"Your name or password is incorrect!!",Toast.LENGTH_LONG).show()
               }else{
                   findNavController().navigate(R.id.action_adminLoginFragment_to_espaceAdminFragment2)
               }
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminLoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminLoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}