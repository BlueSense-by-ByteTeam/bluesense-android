package com.byteteam.bluesense.core.presentation.views.chatbot

import Chat
import ChatBubble
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.ChatEntity
import com.byteteam.bluesense.core.presentation.views.chatbot.widgets.InitialTemplate
import com.byteteam.bluesense.core.presentation.views.chatbot.widgets.MessageTextField
import com.byteteam.bluesense.core.presentation.views.chatbot.widgets.NewChatUIHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

@Composable
fun ChatBotScreen(
    chats: List<ChatEntity>,
    onPostNewPrompt: (String) -> Unit,
    onRetryPrompt: (Int) -> Unit,
    newChatUiState: StateFlow<Resource<ChatEntity>>,
    modifier: Modifier = Modifier,
) {
    val TAG = "chat_screen"

    val chatListState = rememberLazyListState()

    LaunchedEffect(chats.size){
        chatListState.animateScrollToItem(chatListState.layoutInfo.totalItemsCount)
    }

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
                state = chatListState,
                contentPadding = PaddingValues(top = 12.dp)
            ) {
                itemsIndexed(chats) {index, it->
                    ChatBubble(
                        modifier = Modifier.padding(bottom = 12.dp),
                        onRetry = {onRetryPrompt(index)},
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
        MessageTextField(onSend = { onPostNewPrompt("siapakah presiden pertama di Indonesia?") }, modifier = Modifier.padding(horizontal = 24.dp))
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
                chats = emptyUiState.collectAsState().value,
                onPostNewPrompt = {},
                onRetryPrompt = {},
                newChatUiState = loadingNewChatUiState,
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
                onRetryPrompt = {},
                onPostNewPrompt = {},
                newChatUiState = loadingNewChatUiState,
            )
        }
    }
}