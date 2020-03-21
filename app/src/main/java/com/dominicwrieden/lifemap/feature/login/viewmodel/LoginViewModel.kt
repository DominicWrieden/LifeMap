package com.dominicwrieden.lifemap.feature.login.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.lifemap.usecase.DownloadUseCase
import com.dominicwrieden.lifemap.usecase.LoginUseCase
import io.reactivex.Single

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val downloadUseCase: DownloadUseCase
) : ViewModel() {

    val userName: MutableLiveData<String> = MutableLiveData<String>("test")
    val password: MutableLiveData<String> = MutableLiveData<String>("test")

    val progress: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun onLoginClicked() {

        loginUseCase.login(userName.value!!, password.value!!)
            .flatMap { loginResponse ->
                when (loginResponse) {
                    is Response.Success -> {
                        progress.postValue("1/7 Login: successful")
                        downloadUseCase.getAreas().flatMap { areaResponse ->
                            when (areaResponse) {
                                is Response.Success -> {
                                    progress.postValue("2/7 Download Areas: successful")
                                    downloadUseCase.getUsers()
                                        .flatMap { userResponse ->
                                            when (userResponse) {
                                                is Response.Success -> {
                                                    progress.postValue("3/7 Download Users: successful")
                                                    downloadUseCase.getStates()
                                                        .flatMap { stateResponse ->
                                                            when (stateResponse) {
                                                                is Response.Success -> {
                                                                    progress.postValue(
                                                                        "4/7 Download States: successful"
                                                                    )
                                                                    downloadUseCase.getPropertyConfigs()
                                                                        .flatMap { propertyConfigResponse ->
                                                                            when (propertyConfigResponse) {
                                                                                is Response.Success -> {
                                                                                    progress.postValue(
                                                                                        "5/7 Download PropertyConfigs: successful"
                                                                                    )
                                                                                    downloadUseCase.getItemTypes()
                                                                                        .flatMap { itemTypeResponse ->
                                                                                            when (itemTypeResponse) {
                                                                                                is Response.Success -> {
                                                                                                    progress.postValue(
                                                                                                        "6/7 Download ItemTypes: successful... bitte warten"
                                                                                                    )
                                                                                                    downloadUseCase.getItems(
                                                                                                        "5a7a015d72cd36801dbefaa6"
                                                                                                    )
                                                                                                }
                                                                                                else -> Single.just(
                                                                                                    itemTypeResponse
                                                                                                )
                                                                                            }
                                                                                        }
                                                                                }
                                                                                else -> Single.just(
                                                                                    propertyConfigResponse
                                                                                )
                                                                            }
                                                                        }
                                                                }
                                                                else -> Single.just(stateResponse)
                                                            }
                                                        }
                                                }
                                                else -> Single.just(userResponse)
                                            }
                                        }
                                }
                                else -> Single.just(areaResponse)
                            }
                        }
                    }
                    else -> Single.just(loginResponse)
                }
            }
            .subscribe({ response ->
                when (response) {
                    is Response.Success -> {
                        progress.postValue("7/7 Download Items: successful")
                        System.out.println(response.toString())
                    }
                    is Response.ServerError -> {
                        progress.postValue("Error ${response.javaClass}")
                        System.out.println(response.toString())
                    }
                    is Response.AuthenticationError -> {
                        progress.postValue("Error ${response.javaClass}")
                        System.out.println(response.toString())
                    }
                    is Response.NetworkError -> {
                        progress.postValue("Error ${response.javaClass}")
                        System.out.println(response.toString())
                    }
                    is Response.UnknownError -> {
                        progress.postValue("Error ${response.javaClass}")
                        System.out.println(response.toString())
                    }
                }
            }, { error ->
                progress.postValue("Error ${error.javaClass}")
                System.out.println(error.toString())

            })
    }

}