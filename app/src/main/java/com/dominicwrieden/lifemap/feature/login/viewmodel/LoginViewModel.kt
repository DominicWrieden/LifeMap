package com.dominicwrieden.lifemap.feature.login.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dominicwrieden.data.model.Error
import com.dominicwrieden.data.model.Task
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.BaseViewModel
import com.dominicwrieden.lifemap.feature.login.model.LoginButtonState
import com.dominicwrieden.lifemap.feature.login.model.LoginMessageState
import com.dominicwrieden.lifemap.feature.login.model.LoginTextInputState
import com.dominicwrieden.lifemap.usecase.area.DownloadAreasUseCase
import com.dominicwrieden.lifemap.usecase.authentication.LoginUseCase
import com.dominicwrieden.lifemap.usecase.item.DownloadItemsUseCase
import com.dominicwrieden.lifemap.usecase.itemtype.DownloadItemTypesUseCase
import com.dominicwrieden.lifemap.usecase.propertyconfig.DownloadPropertyConfigsUseCase
import com.dominicwrieden.lifemap.usecase.state.DownloadStatesUseCase
import com.dominicwrieden.lifemap.usecase.user.DownloadUsersUseCase
import com.dominicwrieden.lifemap.util.toLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val downloadAreasUseCase: DownloadAreasUseCase,
    private val downloadStatesUseCase: DownloadStatesUseCase,
    private val downloadUsersUseCase: DownloadUsersUseCase,
    private val downloadItemTypesUseCase: DownloadItemTypesUseCase,
    private val downloadPropertyConfigsUseCase: DownloadPropertyConfigsUseCase,
    private val downloadItemsUseCase: DownloadItemsUseCase

) : BaseViewModel() {

    private val userNameInputStateRelay =
        BehaviorRelay.createDefault<LoginTextInputState>(LoginTextInputState.Initial)
    private val passwordInputStateRelay =
        BehaviorRelay.createDefault<LoginTextInputState>(LoginTextInputState.Initial)
    private val loginButtonStateRelay =
        BehaviorRelay.createDefault<LoginButtonState>(LoginButtonState.Initial)
    private val messageStateRelay =
        BehaviorRelay.createDefault<LoginMessageState>(LoginMessageState.Initial)

    val userName: MutableLiveData<String> = MutableLiveData<String>("")
    val password: MutableLiveData<String> = MutableLiveData<String>("")

    val userNameInputState: LiveData<LoginTextInputState> = userNameInputStateRelay.toLiveData()
    val passwordInputState: LiveData<LoginTextInputState> = passwordInputStateRelay.toLiveData()
    val loginButtonState: LiveData<LoginButtonState> = loginButtonStateRelay.toLiveData()
    val messageState: LiveData<LoginMessageState> = messageStateRelay.toLiveData()



    @SuppressLint("CheckResult")
    fun onLoginClicked() {

        if (evaluateInput()) {
            login()
        }
    }

    private fun evaluateInput(): Boolean {

        //check if user name is valid format
        var userNameValid = false

        userName.value?.let {
            if (it.isNotBlank()) {
                userNameValid = true
            } else {
                userNameInputStateRelay.accept(LoginTextInputState.Error(R.string.login_fragment_user_name_error_format))
            }
        }
            ?: userNameInputStateRelay.accept(LoginTextInputState.Error(R.string.login_fragment_user_name_error_format))


        //check if password is valid format
        var passwordValid = false

        password.value?.let {
            if (it.isNotBlank()) {
                passwordValid = true
            } else {
                passwordInputStateRelay.accept(LoginTextInputState.Error(R.string.login_fragment_password_error_format))
            }
        }
            ?: passwordInputStateRelay.accept(LoginTextInputState.Error(R.string.login_fragment_password_error_format))

        return userNameValid && passwordValid
    }

    private fun login() {
        userName.value?.let { userNameInput ->
            password.value?.let { passwordInput ->

                loginUseCase.invoke(userNameInput, passwordInput)
                    .doOnSubscribe {
                        loginButtonStateRelay.accept(LoginButtonState.Loading)
                        userNameInputStateRelay.accept(LoginTextInputState.Loading)
                        passwordInputStateRelay.accept(LoginTextInputState.Loading)
                        messageStateRelay.accept(LoginMessageState.Loading(R.string.login_fragment_message_loading_authentication))
                    }
                    .subscribe(
                        { result ->
                            when (result) {
                                Task.Success -> downloadContent()
                                is Task.Failure -> loginFailed(result.error)
                            }
                        },
                        { error ->
                            loginFailed(Error.UNKNOWN)
                        })
            }
        }
    }

    private fun downloadContent() {
        Single.concat(
            listOf(
                downloadAreasUseCase.invoke(),
                downloadUsersUseCase.invoke(),
                downloadStatesUseCase.invoke(),
                downloadPropertyConfigsUseCase.invoke(),
                downloadItemTypesUseCase.invoke(),
                downloadItemsUseCase.invoke()
            )
        )
            .doOnSubscribe {
                messageStateRelay.accept(LoginMessageState.Loading(R.string.login_fragment_message_loading_content))
            }
            .toList()
            .subscribe({ tasks ->
                if (tasks.all { it is Task.Success }) {
                    continueInApp()
                } else {
                    tasks.filter { it is Task.Failure }.first()?.let { failedTask ->
                        loginFailed((failedTask as Task.Failure).error)
                    }
                }
            }, {
            }).addTo(disposable)
    }

    private fun continueInApp() {
        //TODO and delete the following:
        userNameInputStateRelay.accept(LoginTextInputState.Initial)
        passwordInputStateRelay.accept(LoginTextInputState.Initial)
        loginButtonStateRelay.accept(LoginButtonState.Initial)
        messageStateRelay.accept(LoginMessageState.Initial)
    }

    private fun loginFailed(error: Error) {
        userNameInputStateRelay.accept(LoginTextInputState.Initial)
        passwordInputStateRelay.accept(LoginTextInputState.Initial)
        loginButtonStateRelay.accept(LoginButtonState.Initial)
        messageStateRelay.accept(
            LoginMessageState.Error(
                when (error) {
                    Error.NETWORK_ERROR -> R.string.login_fragment_message_error_no_network
                    Error.AUTHENTICATION_ERROR -> R.string.login_fragment_message_error_authentication
                    else -> R.string.login_fragment_message_error_unkown
                }
            )
        )
    }


    fun userNameChanged() {
        userNameInputStateRelay.accept(LoginTextInputState.Initial)
    }

    fun passwordChanged() {
        passwordInputStateRelay.accept(LoginTextInputState.Initial)
    }
}