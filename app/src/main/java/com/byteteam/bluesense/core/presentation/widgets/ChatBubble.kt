import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.jeziellago.compose.markdowntext.MarkdownText


data class Chat(
    val id: String,
    val created: String,
    val text: String,
    val isMe: Boolean
)

@Composable
fun ChatBubble(
    data: Chat,
    onCopy: () -> Unit = {},
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val blueColor = MaterialTheme.colorScheme.primary;
    Column {
        Box(modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .align(if (data.isMe) Alignment.CenterEnd else Alignment.CenterStart)
                    .drawBehind {
                        val trianglePath = Path().apply {
                            val height = size.height
                            val width = size.width
                            moveTo(0f, 0f)
                            lineTo(0f, height + 24f)
                            lineTo(24f, height)
                            lineTo(width, height)
                            lineTo(width, 0f)
                            lineTo(0f, 0f)
                            lineTo(0f, height)
                        }

                        val trianglePathVariant = Path().apply {
                            val height = size.height
                            val width = size.width
                            moveTo(width, 0f)
                            lineTo(width, height + 24f)
                            lineTo(width - 24f, height)
                            lineTo(0f, height)
                            lineTo(0f, 0f)
                            lineTo(width, 0f)
                            lineTo(width, height)
                        }


                        drawIntoCanvas { canvas ->
                            canvas.drawOutline(
                                outline = Outline.Generic(if (data.isMe) trianglePathVariant else trianglePath),
                                paint = Paint().apply {
                                    color = if (data.isMe) blueColor else Color(0xFFF5F5F5)
                                    pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                                }
                            )
                        }
                    }
            ) {
                Column(
                    Modifier
                        .widthIn(max = 244.dp)
                        .padding(12.dp)
                ) {
                    MarkdownText(
                        markdown = data.text,
                        modifier = Modifier.align(
                            if (data.isMe) Alignment.End else Alignment.Start
                        ).background(Color.Transparent),
                        style = TextStyle(
                            color = if (data.isMe) Color.White else Color.Black
                        ),
                    )
                }
            }

            Canvas(modifier = Modifier.fillMaxWidth()) {
                val trianglePath = Path().apply {
                    val height = size.height
                    val width = size.width
                    moveTo(0f, 0f)
                    lineTo(width, height)
                    lineTo(width, 0f)
                }
                drawIntoCanvas { canvas ->
                    canvas.drawOutline(
                        outline = Outline.Generic(trianglePath),
                        paint = Paint().apply {
                            color = Color.Black
                            pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                        }
                    )
                }
            }
        }
        //
        if (!data.isMe) Row {
            IconButton(onClick = onCopy) {
                Icon(
                    imageVector = Icons.Filled.CopyAll,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
            IconButton(onClick = onRetry) {
                Icon(
                    imageVector = Icons.Default.Cached,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}