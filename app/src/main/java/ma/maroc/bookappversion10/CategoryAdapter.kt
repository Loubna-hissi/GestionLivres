package ma.maroc.bookappversion10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class CategoryAdapter(var appContext: FragmentActivity?, var layout:Int, var data:ArrayList<category>):
    ArrayAdapter<category>(appContext!!,layout,data){
    //context:contient des infos sur layout de l app
    //layout:itemlayout
    //getView:prend l item utilise pour le réutiliser
    //getView:position :indice de chaque element
    //adaptateur est un pont entre le

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //inflater un objet pour creer un item
        //viewGroup:la liste ou insere les donnees
        //layoutInflater:celui qui va construire les items de chaque itemlayout
        //convertView ne permet pas de personnaliser ce qui est a l interieur de la vue
        val view: View = LayoutInflater.from(appContext).inflate(layout,null)

        val imgv= view.findViewById<ImageView>(R.id.imageView4)
        val nom= view.findViewById<TextView>(R.id.textView8)


        nom.text= data.get(position).name
        imgv.setImageResource(data.get(position).image)
        return view
    }
}