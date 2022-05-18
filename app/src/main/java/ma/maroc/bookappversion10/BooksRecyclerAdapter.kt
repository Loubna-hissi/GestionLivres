package ma.maroc.bookappversion10

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class BooksRecyclerAdapter(var cont: Context, private val listBooks: ArrayList<BookN>,var userId:Int) : RecyclerView.Adapter<BooksRecyclerAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_recycler, parent, false)
        return BookViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.BookName.text = listBooks[position].name
        holder.BookDescription.text = listBooks[position].des
        val bitmap=getBimap(listBooks[position].image)
        holder.BookImage.setImageBitmap(bitmap)
        holder.itemView.setOnClickListener {
            val myintent = Intent(cont,BookDetailsActivity::class.java)
            myintent.putExtra("IdBook",listBooks.get(position).id)
            myintent.putExtra("IdUser",userId)
            myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            cont.startActivity(myintent)
        }

    }
    override fun getItemCount(): Int {
        return listBooks.size
    }
    /**
     * ViewHolder class
     */
    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var BookName = view.findViewById<TextView>(R.id.bookName)
        var BookDescription=view.findViewById<TextView>(R.id.author)
        var BookImage=view.findViewById<ImageView>(R.id.imageBookuser)

    }
    private fun getBimap(image: ByteArray): Bitmap? {
        val bitmap= BitmapFactory.decodeByteArray(image,0,image.size)
        return bitmap
    }
}