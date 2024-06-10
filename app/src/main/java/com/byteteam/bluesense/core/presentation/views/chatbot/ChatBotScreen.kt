package com.byteteam.bluesense.core.presentation.views.chatbot

import Chat
import ChatBubble
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.ChatEntity
import com.byteteam.bluesense.core.presentation.views.chatbot.widgets.InitialTemplate
import com.byteteam.bluesense.core.presentation.views.chatbot.widgets.MessageTextField
import com.byteteam.bluesense.core.presentation.views.chatbot.widgets.NewChatUIHandler
import com.byteteam.bluesense.core.presentation.views.chatbot.widgets.TemplateChat
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

@Composable
fun ChatBotScreen(
    chats: List<ChatEntity>,
    newChatUiState: StateFlow<Resource<ChatEntity>>,
    modifier: Modifier = Modifier
) {
    val TAG = "chat_screen"

    Column(modifier.fillMaxSize()) {
        if (chats.isEmpty()) {
            InitialTemplate(modifier = Modifier.weight(1f),
                templatePrompts = templatePrompts,
                onTemplatePress = {
                    Log.d(TAG, "template message: $it")
                })
        } else {
            LazyColumn(
                modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(top = 12.dp)
            ) {
                items(chats) {
                    ChatBubble(
                        data = Chat(
                            id = "", created = "", text = it.text, isMe = it.isMe
                        )
                    )
                }

                item {
                    NewChatUIHandler(
                        newChatUiState, {}, modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
        MessageTextField(onSend = {}, modifier = Modifier.padding(horizontal = 24.dp))
    }
}


private val templatePrompts = listOf(
    "Apa yang dimaksud dengan air bersih?",
    "Mengapa penting untuk mengonsumsi air bersih?",
    "Bagaimana cara memastikan air yang kita gunakan bersih?",
)


val emptyUiState: StateFlow<List<ChatEntity>> = MutableStateFlow(listOf())

@RequiresApi(Build.VERSION_CODES.O)
val loadingNewChatUiState: StateFlow<Resource<ChatEntity>> = MutableStateFlow(
    Resource.Success(
        ChatEntity(
            isMe = true, text = "", created = LocalDateTime.now()
        )
    )
)


@RequiresApi(Build.VERSION_CODES.O)
val withDataUiState: StateFlow<List<ChatEntity>> = MutableStateFlow(
    listOf(
        ChatEntity(
            text = "ini chatku", created = LocalDateTime.now(), isMe = true
        ),
        ChatEntity(
            text = "ini balasan chatku", created = LocalDateTime.now(), isMe = false
        ),
        ChatEntity(
            text = """  
# Sample  
* Markdown  panjang banget
* [Link](https://example.com)  
![Image](https://example.com/img.png)  
<a href="https://www.google.com/">Google</a>  
""", created = LocalDateTime.now(), isMe = false
        ),
    )
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview(device = Devices.PIXEL_4)
@Composable
private fun PreviewNull() {
    BlueSenseTheme {
        Surface {
            ChatBotScreen(
                chats = emptyUiState.collectAsState().value, newChatUiState = loadingNewChatUiState
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(device = Devices.PIXEL_4)
@Composable
private fun PreviewWithData() {
    BlueSenseTheme {
        Surface {
            ChatBotScreen(
                chats = withDataUiState.collectAsState().value,
                newChatUiState = loadingNewChatUiState
            )
        }
    }
}