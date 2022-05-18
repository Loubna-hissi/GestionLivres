package ma.maroc.bookappversion10

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import java.io.ByteArrayOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AjoutbookFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AjoutbookFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var gallery:ImageView
    lateinit var myHelper: MyDBHelper
    lateinit var s:String
    var bitmap:Bitmap?=null
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
        val view=inflater.inflate(R.layout.fragment_ajoutbook, container, false)
        gallery=view.findViewById<ImageView>(R.id.gallery)
        //appload images from gallery
        gallery.setOnClickListener {
            val intentImage= Intent(Intent.ACTION_GET_CONTENT)
            intentImage.type="image/*"
            startActivityForResult(intentImage,100)
        }
        myHelper = MyDBHelper(activity)
        val btnAjout=view.findViewById<Button>(R.id.AddBook)
        val name=view.findViewById<EditText>(R.id.name)
        val auteur=view.findViewById<EditText>(R.id.auteur)
        val nbpages=view.findViewById<EditText>(R.id.pages)
        val stock=view.findViewById<EditText>(R.id.stock)
        val des=view.findViewById<EditText>(R.id.description)
        val category=view.findViewById<Spinner>(R.id.spinner)
        val categoryList= arrayListOf<String>("Science","History","Fiction","Philosophy")
        val arrayAdapter= ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,categoryList)
        category.adapter=arrayAdapter
        category.onItemSelectedListener=object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                s=adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        //save data
        try {


            btnAjout.setOnClickListener {
                val imagesBlob: ByteArray = getBytes(bitmap!!)
                if(
                    name.text.toString().isEmpty()  || auteur.text.toString().isEmpty() || nbpages.text.toString().isEmpty()
                    || stock.text.toString().isEmpty()  || des.text.toString().isEmpty() || imagesBlob.isEmpty()  || s.isEmpty() ){
                    Toast.makeText(activity,"All information are required!",Toast.LENGTH_LONG).show()
                }
                else{
                    try {
                        DataManager.insererBookInformation(
                            myHelper,
                            name.text.toString(),
                            auteur.text.toString(),
                            nbpages.text.toString().toInt(),
                            stock.text.toString().toInt(),
                            des.text.toString(),
                            imagesBlob,
                            s
                        )
                        Toast.makeText(activity,auteur.text.toString(),Toast.LENGTH_LONG).show()

                        findNavController().navigate(R.id.action_ajoutbookFragment_to_bookListAdminFragment)
                    } catch (e: Exception) {
                        Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
        }catch (e:Exception){
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode== RESULT_OK){
            val Uri=data?.data
            val inputStearm = getActivity()?.contentResolver?.openInputStream(Uri!!)
            bitmap=BitmapFactory.decodeStream(inputStearm)
            gallery.setImageBitmap(bitmap)
        }
    }
    fun getBytes(bitmap: Bitmap):ByteArray{
        val stream=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
        return stream.toByteArray()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AjoutbookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AjoutbookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


