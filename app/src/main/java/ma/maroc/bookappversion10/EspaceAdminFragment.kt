package ma.maroc.bookappversion10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EspaceAdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EspaceAdminFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var   myHelper:MyDBHelper

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
        val view=inflater.inflate(R.layout.fragment_espace_admin, container, false)
        val btn=view.findViewById<ImageButton>(R.id.imageView3)
        val btncategory=view.findViewById<ImageButton>(R.id.bus)
        val btnUsers=view.findViewById<ImageButton>(R.id.train)
        val btnStatistic=view.findViewById<ImageButton>(R.id.bus1)

        btn.setOnClickListener {
            myHelper = MyDBHelper(activity)
           // val adminList= DataManager.recuper_books_Information(myHelper)
            findNavController().navigate(R.id.action_espaceAdminFragment2_to_bookListAdminFragment)
        }
        btncategory.setOnClickListener{
            myHelper = activity?.let { it1 -> MyDBHelper(it1) }!!
            // val adminList= DataManager.recuper_books_Information(myHelper)
            findNavController().navigate(R.id.action_espaceAdminFragment2_to_listCategoryFragment)
        }
        btnUsers.setOnClickListener {
            findNavController().navigate(R.id.action_espaceAdminFragment2_to_allUsersFragment)
        }
        btnStatistic.setOnClickListener {
            findNavController().navigate(R.id.action_espaceAdminFragment2_to_statisticFragment)
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
         * @return A new instance of fragment EspaceAdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EspaceAdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}