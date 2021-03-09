package com.example.firstappkotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.runtime.toMutableStateList
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firstappkotlin.R
import com.example.firstappkotlin.models.ItemPokemon
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    val compositeDisposable = CompositeDisposable()
    private var navController: NavController? = null
    lateinit var listPokemon: MutableList<ItemPokemon>
    lateinit var listPokemonFilter: MutableList<ItemPokemon>
    lateinit var context: Context

    fun ItemAdapter(
        listPokemon: List<ItemPokemon>,
        listPokemonFilter: List<ItemPokemon>,
        context: Context,
        navController: NavController?
    ) {
        this.listPokemon = listPokemon.toMutableStateList()
        this.listPokemonFilter = listPokemonFilter.toMutableStateList()
        this.context = context
        this.navController = navController
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val namePokemon: TextView = view.findViewById(R.id.txt_name)
        val select: LinearLayout = view.findViewById(R.id.ly_select)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.namePokemon.text = listPokemon[position].name
        holder.select.setOnClickListener{ selectItem(listPokemon[position])}
    }

    private fun selectItem(itemPokemon: ItemPokemon) {
        Log.e("url", itemPokemon.url.toString())
        val bundle = bundleOf("url" to itemPokemon.url.toString())
        navController?.navigate(R.id.action_home_to_detail, bundle)
    }

    override fun getItemCount(): Int {
        return listPokemon.count()
    }

    fun filter(value: String) {
        var chartText:String = value.toLowerCase(Locale.getDefault())

        listPokemon.clear()

        var observable = Observable
            .fromArray(listPokemonFilter)
            .map {

                if (chartText.isEmpty()) {

                    listPokemon.addAll(it)
                } else {

                    for (item: ItemPokemon in it) {
                        if (item.name?.toLowerCase(Locale.getDefault())?.contains(chartText)!!) {
                            listPokemon.add(item)
                        }
                    }
                }

                return@map listPokemon
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe({
            },{
                error -> Log.e("ERRROR", error.toString())
            },{
                notifyDataSetChanged()
            })


        compositeDisposable.add(observable)
    }




}