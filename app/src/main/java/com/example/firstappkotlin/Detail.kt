package com.example.firstappkotlin

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.example.firstappkotlin.api.RetrofitInstance
import com.example.firstappkotlin.models.Ability
import com.example.firstappkotlin.models.Move
import com.example.firstappkotlin.models.Pokemon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


class Detail : Fragment() {

    private val TAG = "DETAIL"
    private var disposable: CompositeDisposable? = null

    private lateinit var title: TextView
    private lateinit var lyDetail: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = arguments?.getString("url")
        disposable = CompositeDisposable()
        loadData(url)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = view.findViewById(R.id.txt_title)
        lyDetail = view.findViewById(R.id.ly_detail)
    }

    private fun loadData(url: String?) {
        var pokemon: Pokemon? = null

        disposable?.add(
            RetrofitInstance.apiOther().getPokemon(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    pokemon = it
                }, { error ->
                    onError(error)
                }) {
                    handleResponse(pokemon)
                })
    }

    private fun handleResponse(pokemon: Pokemon?) {

        try{
            Log.e(TAG, pokemon.toString())

            title.text = pokemon!!.name

 //           generateAbilities(pokemon)

            generateMoves(pokemon)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun generateAbilities(pokemon: Pokemon) {
        val abilitiesCount: Int = pokemon.abilities.size

        generateTitles(getString(R.string.abilities))

        val linearLayout = LinearLayout(activity)

        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            abilitiesCount.toFloat()
        )

        linearLayout.orientation = LinearLayout.HORIZONTAL

        for (ability: Ability in pokemon.abilities) {
            val nameAbility = TextView(activity)
            val lyPtt = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT , 1f)
            lyPtt.gravity = Gravity.CENTER

            nameAbility.layoutParams = lyPtt
            nameAbility.text = ability.ability.name
            nameAbility.setPadding(10, 10, 10, 10)
            nameAbility.gravity = Gravity.CENTER
            nameAbility.setTextColor(resources.getColor(R.color.primaryTextColor))

            linearLayout.addView(nameAbility)
        }

        lyDetail.addView(linearLayout)
    }

    private fun generateMoves(pokemon: Pokemon){

        generateTitles(getString(R.string.moves))

        val scrollView = ScrollView(activity)
        scrollView.layoutParams =LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        val linearLayout = LinearLayout(activity)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL

        for (move:Move in pokemon.moves){

            val itemList = TextView(activity)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.gravity = Gravity.CENTER

            itemList.layoutParams = layoutParams
            itemList.text = move.move.name
            itemList.setPadding(10, 10, 10, 10)
            itemList.gravity = Gravity.CENTER
            itemList.setTextColor(resources.getColor(R.color.primaryTextColor))

            linearLayout.addView(itemList)
        }

        scrollView.addView(linearLayout)

        lyDetail.addView(scrollView)
    }

    private fun generateTitles(value:String) {

        val linearLayout = LinearLayout(activity)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        linearLayout.orientation = LinearLayout.HORIZONTAL

        val title = TextView(activity)
        val lyPtt = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        lyPtt.gravity = Gravity.CENTER

        title.layoutParams = lyPtt
        title.text = value
        title.setPadding(10, 10, 10, 10)
        title.gravity = Gravity.CENTER
        title.setTextColor(resources.getColor(R.color.secondaryColor))

        linearLayout.addView(title)

        lyDetail.addView(linearLayout)
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
        Log.e(TAG, error.message.toString())
        Toast.makeText(activity, error.message, Toast.LENGTH_LONG).show()
    }

}