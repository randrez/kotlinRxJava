package com.example.firstappkotlin

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.firstappkotlin.api.RetrofitInstance
import com.example.firstappkotlin.models.Login
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import org.json.JSONStringer


class Login : Fragment() {

    //Evita fugas de memoria
    private var disposable: CompositeDisposable? = null
    private var navController: NavController? = null

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var enter: Button

    val TAG: String = "Login"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = CompositeDisposable()
        navController = Navigation.findNavController(view)

        email = view.findViewById(R.id.edt_email)
        password = view.findViewById(R.id.edt_pass)
        enter = view.findViewById(R.id.enter)

        enter.setOnClickListener { validateData() }
    }

    private fun validateData() {

        val gson = Gson()

        var loginString: String = ""

        val validate: String? = if (email.text.toString() == "") {
            getString(R.string.no_email)
        } else if (password.text.toString() == "") {
            getString(R.string.no_pass)
        } else {
            null
        }

        if (validate == null) {
            disposable?.add(RetrofitInstance.api().login(password.text.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    it.email = email.text.toString()
                    loginString = gson.toJson(it)
                }, { error ->
                    onError(error)
                }, {
                    val bundle = bundleOf("user" to loginString)
                    navController?.navigate(R.id.action_login_to_home, bundle)
                })
            )
        } else {

            Toast.makeText(activity, validate, Toast.LENGTH_LONG).show()
        }
    }


    private fun onError(error: Throwable) {
        Log.e(TAG, error.toString())
        Toast.makeText(activity, error.message, Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.clear()
    }
}



