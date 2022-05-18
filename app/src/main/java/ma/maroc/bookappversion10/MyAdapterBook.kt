package ma.maroc.bookappversion10

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapterBook(var data: ArrayList<Books>): RecyclerView.Adapter<MyAdapterBook.MyViewHolder>() {

    class MyViewHolder(val row: View): RecyclerView.ViewHolder(row){
        val title=row.findViewById<TextView>(R.id.namebook)
        val desc=row.findViewById<TextView>(R.id.desbook)
        val img=row.findViewById<ImageView>(R.id.imagebook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterBook.MyViewHolder {
        val layout= LayoutInflater.from(parent.context).inflate(R.layout.card,parent,false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyAdapterBook.MyViewHolder, position: Int) {
        holder.title.text=data.get(position).name.toString()
        holder.desc.text=data.get(position).des.toString()
        val bitmap=getBimap(data.get(position).image)
        holder.img.setImageBitmap(bitmap)
    }

    private fun getBimap(image: ByteArray): Bitmap? {
           val bitmap=BitmapFactory.decodeByteArray(image,0,image.size)
           return bitmap
    }

    override fun getItemCount(): Int=data.size

}