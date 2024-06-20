package com.byteteam.bluesense.core.presentation.views.chatbot

import android.R.attr.label
import android.R.attr.text
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.ChatEntity
import com.byteteam.bluesense.core.domain.repositories.GeminiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject


@SuppressLint("NewApi")
@HiltViewModel
class ChatBotViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val geminiRepository: GeminiRepository
) : ViewModel() {
    private var _chats: SnapshotStateList<ChatEntity> =
        mutableStateListOf()
    val chats: SnapshotStateList<ChatEntity> = _chats

    private var _newChatUi: MutableStateFlow<Resource<ChatEntity>> = MutableStateFlow(
        Resource.Success(
            ChatEntity("", created = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time), isMe = true)
        )
    )

    val newChat: StateFlow<Resource<ChatEntity>> = _newChatUi
    fun postNewPrompt(prompt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ChatBotViewModel", "postNewPrompt: $prompt")
            _newChatUi.value = Resource.Loading()
            try {
                val nowSent = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())

                val sentChat = ChatEntity(text = prompt, created = nowSent, isMe = true)
                _chats.add(sentChat)

                geminiRepository.postChat(prompt).catch { e ->
                    _newChatUi.value = Resource.Error(e.message ?: "Error when posting new prompt!")
                }.collect { data ->
                    val now = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())
                    val responseChat =
                        ChatEntity(
                            text = data,
                            created = now,
                            isMe = false,
                            prompt = prompt
                        )
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

    fun retryPromptChatAt(index: Int) {
        try {

            if (_chats[index].prompt == null) throw Exception("Previous prompt is invalid!")
            val prompt = _chats[index].prompt
            postNewPrompt(prompt!!)
        } catch (e: Exception) {
            _newChatUi.value = Resource.Error(e.message ?: "Error when retry this prompt chat bot!")
        }
    }

    fun copyToClipBoard(text: String, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val clipboard: ClipboardManager? =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText("chatbot result", text)
                clipboard?.setPrimaryClip(clip)
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFail(e.message ?: "Error when copy this chat result")
                }
            }

        }
    }
}