package com.koleychik.currencyvalue.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.koleychik.currencyvalue.R
import com.koleychik.currencyvalue.additions.GetAbbreviationAndName
import com.koleychik.currencyvalue.additions.Keys
import com.koleychik.currencyvalue.model.Favorites
import kotlinx.android.synthetic.main.item_rv.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MainViewHolder>() {

    var list: SortedList<Favorites>
    private val listDuplicate = mutableListOf<Favorites>()

    val set = mutableSetOf<String>()

    init {
        list = SortedList(
            Favorites::class.java,
            object : SortedListAdapterCallback<Favorites>(this){
                override fun compare(o1: Favorites?, o2: Favorites?): Int = 1

                override fun areContentsTheSame(oldItem: Favorites?, newItem: Favorites?): Boolean =
                    newItem!!.valueToday == oldItem!!.valueToday

                override fun areItemsTheSame(item1: Favorites?, item2: Favorites?): Boolean =
                    item1 == item2
            })
    }

    fun submitList(newList: List<Favorites>, listFavoritesId: List<String>){
        val setUsingEl = mutableSetOf<Int>()
        for (i in (newList.indices)){
            setUsingEl.add(listFavoritesId[i].toInt())

            if (set.contains(listFavoritesId[i])) continue
            list.add(newList[i])
            listDuplicate.add(newList[i])
            set.add(listFavoritesId[i])
        }
        checkToDeleteList(setUsingEl)
    }

    private fun checkToDeleteList(setUsingEl : Set<Int>){
        for (i in listDuplicate){
            if (!setUsingEl.contains(i.id)){
                list.remove(i)
                listDuplicate.remove(i)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(model = list[position])
    }

    override fun getItemCount(): Int = list.size()

    class MainViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model : Favorites){
            Log.d(Keys.APP, "model.nameSecond = ${model.nameSecond}")
            itemView.flag1.setImageResource(GetAbbreviationAndName.getFlag(model.nameFirst))
            itemView.flag2.setImageResource(GetAbbreviationAndName.getFlag(model.nameSecond))
            itemView.abbreviation1.setText(GetAbbreviationAndName.getAbbreviation(model.nameFirst))
            itemView.abbreviation2.setText(GetAbbreviationAndName.getAbbreviation(model.nameSecond))
            itemView.equals.text = model.valueToday.toString()
            itemView.course.setImageResource(R.drawable.equals)
        }

    }

}