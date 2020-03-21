package com.dominicwrieden.lifemap.feature.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.databinding.FragmentLoginBinding
import com.dominicwrieden.lifemap.feature.login.viewmodel.LoginViewModel
import com.dominicwrieden.lifemap.util.observeWith
import org.koin.android.viewmodel.ext.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {


        val binding:FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        var toast:Toast? = null

        viewModel.progress.observeWith(this) {
            toast?.cancel()

            toast=  Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG)

            toast?.show()
        }


        return binding.root
    }
}