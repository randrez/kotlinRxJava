package com.example.firstappkotlin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstappkotlin.adapters.ItemAdapter
import com.example.firstappkotlin.api.RetrofitInstance
import com.example.firstappkotlin.models.ResponseData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.*


class Home : Fragment() {

    val TAG: String = "Home"

    private var user: String? = null
    private var disposable: CompositeDisposable? = null
    private var navController: NavController? = null

    private lateinit var showSearch: LinearLayout
    private lateinit var viewSearch: View
    private lateinit var edtFilter: EditText
    private lateinit var clear: LinearLayout
    private lateinit var listPokemon: RecyclerView

    private val mAdapter: ItemAdapter = ItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = arguments?.getString("user")

        disposable = CompositeDisposable()

        helloUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        showSearch = view.findViewById(R.id.ly_show_search)
        showSearch.setOnClickListener { showHideSearch() }

        viewSearch = view.findViewById(R.id.component_search)
        edtFilter = view.findViewById(R.id.edt_filter)

        edtFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                var text: String = edtFilter.text.toString().toLowerCase(Locale.getDefault())
                mAdapter.filter(text)
            }
        })

        clear = view.findViewById(R.id.clear)
        clear.setOnClickListener { clearSearch() }
        listPokemon = view.findViewById(R.id.list_pokemon)

        loadPokemons()
    }

    private fun clearSearch() {
        edtFilter.text.clear()
    }

    private fun helloUser() {

        try {
            val json = JSONObject(user)

            val email = json.getString("email")

            Toast.makeText(
                requireContext(),
                String.format(getString(R.string.welcome), email),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showHideSearch() {
        if (viewSearch.visibility == View.GONE) {
            viewSearch.visibility = View.VISIBLE
        } else {
            viewSearch.visibility = View.GONE
        }
    }

    private fun loadPokemons() {

        var responseData: ResponseData? = null

        disposable?.add(RetrofitInstance.apiOther().getPokemons(150)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                responseData = it
            }, { error ->
                onError(error)
            }) {
                handleResponse(responseData)
            })

    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
        Log.e(TAG, error.message.toString())
        Toast.makeText(activity, error.message, Toast.LENGTH_LONG).show()
    }

    private fun handleResponse(responseData: ResponseData?) {

        listPokemon.setHasFixedSize(true)
        listPokemon.layoutManager = LinearLayoutManager(activity)
        mAdapter.ItemAdapter(
            responseData!!.listPokemon,
            responseData!!.listPokemon,
            requireContext(),
            navController
        )
        listPokemon.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.clear()
    }
}