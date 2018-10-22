package io.github.hunachi.user

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.model.User
import io.github.hunachi.shared.network.NetWorkError
import io.github.hunachi.usernetwork.UserClient
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class UserRepository internal constructor(
        private val client: UserClient,
        private val localRepository: UserLocalRepository) {

    private val _userState: MutableLiveData<User> = MutableLiveData()

    private val _errorState: MutableLiveData<NetWorkError> = MutableLiveData()

    fun setUp(userName: String?, token: String, isForceUpdate: Boolean = false): UserResult {
        try {
            CoroutineScope(Dispatchers.IO).launch {

                val user = userName?.let { localRepository.owner(it) }.let { user ->
                    if (user != null && !isForceUpdate) user
                    else client.owner(token).await().also {
                        localRepository.insertUser(it)
                    }
                }
                _userState.postValue(user)
            }
        } catch (e: Exception) {
            _errorState.value = NetWorkError.NORMAL
        }
        return UserResult(_userState, _errorState)
    }
}