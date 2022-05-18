package ma.maroc.bookappversion10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view= inflater.inflate(R.layout.fragment_list_category, container, false)
        val categories=ArrayList<category>()
        val e1=category(R.drawable.cat_science,"Science")
        val e2=category(R.drawable.cat_history,"History")
        val e3=category(R.drawable.cat_fiction,"Fiction")
        val e4=category(R.drawable.cat_philo,"Philosophy")

        categories.add(e1)
        categories.add(e2)
        categories.add(e3)
        categories.add(e4)


        view.findViewById<ListView>(R.id.listView).adapter=CategoryAdapter(activity,R.layout.itemcategory,categories)
        view.findViewById<ListView>(R.id.listView).setOnItemClickListener { _, _, position, _ ->

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
         * @return A new instance of fragment ListCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}