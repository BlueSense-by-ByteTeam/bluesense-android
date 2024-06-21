import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.data.source.remote.response.water_supplier_detail.LogsItem
import com.byteteam.bluesense.core.helper.formatUIDate
import com.byteteam.bluesense.core.helper.parseDateStringWithTimeZoneGMT7

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SupplierWaterQualities(list: List<LogsItem>) {

    Column(Modifier.padding(vertical = 12.dp)) {
        Text(text = "Riwayat kualitas air")
        LazyRow {
            items(list){
                Box(
                    Modifier
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Blue.copy(alpha = 0.2f))
                        .padding(12.dp)) {
                    Column {
                    Text(text = "ph: ${it.ph ?: "-"}")
                    Text(text = "tds:  ${it.tds ?: "-"}")
                    Text(text = "tanggal:  ${it.datetime?.parseDateStringWithTimeZoneGMT7() ?: "-"}")
                    }
                }
            }
        }
    }
}

fun isGoodQuality(tds: Int, ph: Int): Boolean {
    if (tds > 1500 || ph < 6.5 || ph > 9.0) {
        return false
    }
    return true
}


fun isDrinkable(tds: Int, ph: Int): Boolean {
    if (tds > 500 || ph < 6.5 || ph > 8.5) {
        return false
    }
    return true
}