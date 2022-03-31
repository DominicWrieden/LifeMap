package com.dominicwrieden.lifemap.feature.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.BaseFragment
import com.dominicwrieden.lifemap.feature.login.model.LoginButtonState
import com.dominicwrieden.lifemap.feature.login.model.LoginMessageState
import com.dominicwrieden.lifemap.feature.login.model.LoginTextInputState
import com.dominicwrieden.lifemap.feature.login.viewmodel.LoginViewModel
import com.dominicwrieden.lifemap.ui.LifeMapTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

@ExperimentalAnimationApi
class LoginComposeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                LifeMapTheme {
                    Screen()
                }
            }
        }
    }

    @Composable
    fun Screen(viewModel: LoginViewModel = getViewModel()) {
        val userName: String by viewModel.userName.observeAsState("")
        val password: String by viewModel.password.observeAsState("")
        val isLoading: LoginButtonState? by viewModel.loginButtonState.observeAsState(
            LoginButtonState.Initial
        )
        val userNameInputState: LoginTextInputState by viewModel.userNameInputState.observeAsState(
            LoginTextInputState.Initial
        )
        val passwordInputState: LoginTextInputState by viewModel.passwordInputState.observeAsState(
            LoginTextInputState.Initial
        )
        val messageTextState: LoginMessageState by viewModel.messageState.observeAsState(
            LoginMessageState.Initial
        )


        LoginScreen(
            userName = userName ?: "",
            onUserNameChanged = { viewModel.userNameChanged(it) },
            userNameInputState = userNameInputState,
            password = password ?: "",
            onPasswordChanged = { viewModel.passwordChanged(it) },
            passwordInputState = passwordInputState,
            onLoginButtonClicked = { viewModel.onLoginClicked() },
            isLoading = when (isLoading) {
                LoginButtonState.Loading -> true
                else -> false
            },
            loginMessageState = messageTextState
        )
    }

    @Composable
    fun LoginScreen(
        userName: String,
        onUserNameChanged: (String) -> Unit = {},
        userNameInputState: LoginTextInputState = LoginTextInputState.Initial,
        password: String,
        onPasswordChanged: (String) -> Unit = { },
        passwordInputState: LoginTextInputState = LoginTextInputState.Initial,
        onLoginButtonClicked: () -> Unit = {},
        isLoading: Boolean,
        loginMessageState: LoginMessageState = LoginMessageState.Initial
    ) {


        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.1f))


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = userName,
                maxLines = 1,
                shape = RoundedCornerShape(50),
                isError = userNameInputState is LoginTextInputState.Error,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(stringResource(R.string.login_fragment_user_name_hint)) },
                onValueChange = onUserNameChanged
            )
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)
            ) {
                this@Column.AnimatedVisibility(
                    visible = userNameInputState is LoginTextInputState.Error,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.error,
                        text = if (userNameInputState is LoginTextInputState.Error) stringResource(
                            userNameInputState.errorMessage
                        ) else ""
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(50),
                value = password,
                maxLines = 1,
                isError = passwordInputState is LoginTextInputState.Error,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(stringResource(R.string.login_fragment_password_hint)) },
                onValueChange = onPasswordChanged
            )
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)
            ) {
                this@Column.AnimatedVisibility(
                    visible = passwordInputState is LoginTextInputState.Error,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.error,
                        text = if (passwordInputState is LoginTextInputState.Error) stringResource(
                            passwordInputState.errorMessage
                        ) else ""
                    )
                }

            }

            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
            ) {

                this@Column.AnimatedVisibility(
                    modifier = Modifier.align(Center),
                    visible = !isLoading,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(48.dp)
                            .shadow(elevation =20.dp, shape = RectangleShape, clip = false),
                        onClick = onLoginButtonClicked,
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Login")
                    }
                }

                this@Column.AnimatedVisibility(
                    modifier = Modifier.align(Center),
                    visible = isLoading,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    CircularProgressIndicator()
                }

            }

            Spacer(modifier = Modifier.fillMaxHeight(0.1f))

            Text(
                textAlign = TextAlign.Center,
                text = loginMessageState.let {
                when (it) {
                    is LoginMessageState.Error -> stringResource(it.errorMessage)
                    is LoginMessageState.Loading -> stringResource(it.progressMessage)
                    else -> ""
                }
            })

        }

    }

    @Preview(widthDp = 720, heightDp = 1080)
    @Composable
    fun PreviewLoginScreen() {
        LifeMapTheme {
            LoginScreen(
                userName = "Dominique",
                password = "Musterperson",
                isLoading = false
            )
        }
    }
}