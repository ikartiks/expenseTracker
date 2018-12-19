package ikartiks.expensetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ikartiks.expensetracker.R
import ikartiks.expensetracker.entities.ViewTransactionDetails

import java.util.ArrayList

class GroupsRecyclerAdapter(internal var groupItems: List<ViewTransactionDetails>) : RecyclerView.Adapter<GroupsRecyclerAdapter.ViewHolder>() {

    internal var clickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {

        return groupItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val t = groupItems[position]
        holder.progress.text = " " + t.transactionDetailsAccountId + " " +t.transactionDetailsAmount + " " +
         t.transactionDetailsDate + " " + t.transactionTypeName+ " " +t.transactionTypeType
        holder.name.text = t.transactionDetailsNote

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.inflate_items, parent, false)
        return ViewHolder(v)
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickLickListener(listener: OnItemClickListener) {

        clickListener = listener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnClickListener {

        internal var name: TextView
        internal var progress: TextView


        init {
            name = itemView.findViewById(R.id.Name)
            progress = itemView.findViewById(R.id.AllOtherDetails)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

            clickListener?.onItemClick(v, this.adapterPosition) //OnItemClickListener mItemClickListener;
        }

    }

}
