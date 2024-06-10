package com.byteteam.bluesense.core.presentation.views.chatbot

import Chat
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.ChatEntity
import com.byteteam.bluesense.core.domain.repositories.GeminiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {
    private var _chats: SnapshotStateList<ChatEntity> =
        mutableStateListOf()
    val chats: SnapshotStateList<ChatEntity> = _chats

    @RequiresApi(Build.VERSION_CODES.O)
    private var _newChatUi: MutableStateFlow<Resource<ChatEntity>> = MutableStateFlow(
        Resource.Success(
            ChatEntity("", created = LocalDateTime.now(), isMe = true)
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val newChat: StateFlow<Resource<ChatEntity>> = _newChatUi
    fun postNewPrompt(prompt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ChatBotViewModel", "postNewPrompt: $prompt")
            _newChatUi.value = Resource.Loading()
            try {
                val sentChat = ChatEntity(text = prompt, created = LocalDateTime.now(), isMe = true)
                _chats.add(sentChat)

                geminiRepository.postChat(prompt).catch { e ->
                    _newChatUi.value = Resource.Error(e.message ?: "Error when posting new prompt!")
                }.collect { data ->
                    val responseChat =
                        ChatEntity(text = data, created = LocalDateTime.now(), isMe = false, prompt = prompt)
                    _newChatUi.value = Resource.Success(responseChat)
                    _chats.add(responseChat)
                    Log.d("ChatBotViewModel", "postNewPrompt: $data")
                }
            } catch (e: Exception) {
                Log.d("ChatBotViewModel", "postNewPrompt: $e")
                _newChatUi.value = Resource.Error(e.message ?: "Error when posting new prompt!")
            }
        }
    }

    fun retryPromptChatAt(index: Int){
        try {

        if(_chats[index].prompt == null) throw Exception("Previous prompt is invalid!")
        val prompt = _chats[index].prompt
            postNewPrompt(prompt!!)
        }catch (e: Exception){
            _newChatUi.value = Resource.Error(e.message ?: "Error when retry this prompt chat bot!")
        }
    }
}