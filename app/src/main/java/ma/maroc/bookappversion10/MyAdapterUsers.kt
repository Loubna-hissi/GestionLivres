package ma.maroc.bookappversion10

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapterUsers(var data: ArrayList<User>): RecyclerView.Adapter<MyAdapterUsers.MyViewHolder>() {

    class MyViewHolder(val row: View): RecyclerView.ViewHolder(row){
        val nameuser=row.findViewById<TextView>(R.id.nameuser)
        val emailuser=row.findViewById<TextView>(R.id.emailuser)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterUsers.MyViewHolder {
        val layout= LayoutInflater.from(parent.context).inflate(R.layout.carduser,parent,false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyAdapterUsers.MyViewHolder, position: Int) {
        holder.nameuser.text=data.get(position).name.toString()
        holder.emailuser.text=data.get(position).email.toString()
    }

    override fun getItemCount(): Int=data.size

}