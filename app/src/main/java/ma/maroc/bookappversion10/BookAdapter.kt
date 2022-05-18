package ma.maroc.bookappversion10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminAdapters(var cont: Context, var admins: ArrayList<Books>): RecyclerView.Adapter<AdminAdapters.MyViewHolder>() {

    class MyViewHolder(val row: View): RecyclerView.ViewHolder(row){
        val name=row.findViewById<TextView>(R.id.namebook)
        val des=row.findViewById<TextView>(R.id.desbook)
        val image=row.findViewById<ImageView>(R.id.imagebook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminAdapters.MyViewHolder {
        val layout= LayoutInflater.from(parent.context).inflate(R.layout.card,parent,false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AdminAdapters.MyViewHolder, position: Int) {
        holder.name.text=admins.get(position).name.toString()
        holder.des.text=admins.get(position).des.toString()
       // holder.image.ima(admins.get(position).image)
    }

    override fun getItemCount(): Int {
        return admins.size
    }
}
