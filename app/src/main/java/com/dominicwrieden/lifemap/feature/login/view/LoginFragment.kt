package com.dominicwrieden.lifemap.feature.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.BaseFragment
import com.dominicwrieden.lifemap.databinding.FragmentLoginBinding
import com.dominicwrieden.lifemap.feature.login.model.LoginButtonState
import com.dominicwrieden.lifemap.feature.login.model.LoginMessageState
import com.dominicwrieden.lifemap.feature.login.model.LoginTextInputState
import com.dominicwrieden.lifemap.feature.login.viewmodel.LoginViewModel
import com.dominicwrieden.lifemap.util.observeWith
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()

    private lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        viewModel.userNameInputState.observeWith(this) { userNameInputState ->
            when (userNameInputState) {
                LoginTextInputState.Initial -> showUserNameInitialState()
                LoginTextInputState.Loading -> showUserNameLoadingState()
                is LoginTextInputState.Error -> showUserNameErrorState(userNameInputState.errorMessage)
            }
        }

        viewModel.passwordInputState.observeWith(this) { passwordInputState ->
            when (passwordInputState) {
                LoginTextInputState.Initial -> showPasswordInitialState()
                LoginTextInputState.Loading -> showPasswordLoadingState()
                is LoginTextInputState.Error -> showPasswordErrorState(passwordInputState.errorMessage)
            }
        }

        viewModel.loginButtonState.observeWith(this) { loginButtonStates ->
            when (loginButtonStates) {
                LoginButtonState.Initial -> showLoginButtonInitialState()
                LoginButtonState.Loading -> showLoginButtonLoadingState()
            }
        }

        viewModel.messageState.observeWith(this) { loginMessageState ->
            when (loginMessageState) {
                LoginMessageState.Initial -> showLoginMessageInitialState()
                is LoginMessageState.Loading -> showLoginMessageLoadingState(loginMessageState.progressMessage)
                is LoginMessageState.Error -> showLoginMessageErrorState(loginMessageState.errorMessage)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observe if user changed something in the user name input field
        binding.userName.textChanges()
            .skipInitialValue()
            .subscribe { viewModel.userNameChanged() }
            .addTo(disposable)

        //observe if user changed something in the password input field
        binding.password.textChanges()
            .skipInitialValue()
            .subscribe { viewModel.passwordChanged() }
            .addTo(disposable)
    }

    private fun showLoginMessageErrorState(@StringRes errorMessage: Int) {
        binding.loginMessage.visibility = View.VISIBLE
        binding.loginMessage.setTextColor(ContextCompat.getColor(requireContext(),R.color.design_default_color_error))
        binding.loginMessage.text = getString(errorMessage)
    }

    private fun showLoginMessageLoadingState(@StringRes progressMessage: Int) {
        binding.loginMessage.visibility = View.VISIBLE
        binding.loginMessage.setTextColor(ContextCompat.getColor(requireContext(),R.color.design_default_color_on_primary))
        binding.loginMessage.text = getString(progressMessage)
    }

    private fun showLoginMessageInitialState() {
        binding.loginMessage.visibility = View.INVISIBLE
    }

    private fun showLoginButtonLoadingState() {
        //binding.loginButton.startAnimation()
    }

    private fun showLoginButtonInitialState() {
        //binding.loginButton.revertAnimation()
        binding.loginButton.background = ContextCompat.getDrawable(requireContext(),R.drawable.button_shape_round)
    }

    private fun showPasswordErrorState(@StringRes errorMessage: Int) {
        binding.password.isEnabled = true
        binding.passwordLayout.error = getString(errorMessage)
    }


    private fun showPasswordLoadingState() {
        binding.password.isEnabled = false
        binding.passwordLayout.error = null
    }

    private fun showPasswordInitialState() {
        binding.password.isEnabled = true
        binding.passwordLayout.error = null
    }

    private fun showUserNameErrorState(@StringRes errorMessage: Int) {
        binding.userName.isEnabled = true
        binding.userNameLayout.error = getString(errorMessage)
    }

    private fun showUserNameLoadingState() {
        binding.userName.isEnabled = false
        binding.userNameLayout.error = null
    }

    private fun showUserNameInitialState() {
        binding.userName.isEnabled = true
        binding.userNameLayout.error = null
    }
}