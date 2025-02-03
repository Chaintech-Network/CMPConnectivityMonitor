package chaintech.network.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chaintech.network.app.theme.connected_bg
import chaintech.network.app.theme.connected_primary
import chaintech.network.app.theme.disconnected_bg
import chaintech.network.app.theme.disconnected_primary
import chaintech.network.app.theme.disconnected_secondory
import chaintech.network.app.theme.try_again_bg
import chaintech.network.connectivitymonitor.ConnectivityStatus
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import networkmonitor.composeapp.generated.resources.Res
import networkmonitor.composeapp.generated.resources.SF_Pro_Display_Regular
import networkmonitor.composeapp.generated.resources.SF_Pro_Text_Semibold
import networkmonitor.composeapp.generated.resources.connected
import networkmonitor.composeapp.generated.resources.icn_checkmark
import networkmonitor.composeapp.generated.resources.icn_connected
import networkmonitor.composeapp.generated.resources.icn_disconnected
import networkmonitor.composeapp.generated.resources.icn_internet_checking
import networkmonitor.composeapp.generated.resources.offline
import networkmonitor.composeapp.generated.resources.oops
import networkmonitor.composeapp.generated.resources.something_went_wrong
import networkmonitor.composeapp.generated.resources.try_again
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun App() {
    val viewModel = remember { ConnectivityViewModel() }
    val connectivityStatus by viewModel.connectivityStatus.collectAsState()

    fun bgColor(): androidx.compose.ui.graphics.Color {
        return when (connectivityStatus) {
            ConnectivityStatus.CONNECTED,
            ConnectivityStatus.CONNECTED_VIA_CELLULAR,
            ConnectivityStatus.CONNECTED_VIA_WIFI,
            ConnectivityStatus.DETERMINING -> connected_bg
            else -> disconnected_bg
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = bgColor())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(connectivityStatus){
                ConnectivityStatus.CONNECTED,
                ConnectivityStatus.CONNECTED_VIA_CELLULAR,
                ConnectivityStatus.CONNECTED_VIA_WIFI -> InternetOnline()
                ConnectivityStatus.DETERMINING -> InternetChecking()
                else -> InternetOffline(tryAgain = { viewModel.refresh() })
            }
        }
    }

}

@Composable
fun InternetOnline() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(2f))

        Image(
            painter = painterResource(Res.drawable.icn_checkmark),
            contentDescription = null,
            modifier = Modifier.size(50.sdp),
            contentScale = ContentScale.Fit
        )


        Text(
            text = stringResource(Res.string.connected),
            color = connected_primary,
            fontFamily = FontFamily(Font(Res.font.SF_Pro_Text_Semibold)),
            fontSize = 22.ssp,
            modifier = Modifier.padding(top = 14.sdp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(Res.drawable.icn_connected),
            contentDescription = null,
            modifier = Modifier
                .height(260.sdp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.weight(3f))
    }
}
@Composable
fun InternetOffline(
    tryAgain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(Res.string.oops),
            color = disconnected_primary,
            fontFamily = FontFamily(Font(Res.font.SF_Pro_Text_Semibold)),
            fontSize = 30.ssp,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(Res.drawable.icn_disconnected),
            contentDescription = null,
            modifier = Modifier
                .size(220.sdp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(Res.string.offline),
            color = disconnected_primary,
            fontFamily = FontFamily(Font(Res.font.SF_Pro_Text_Semibold)),
            fontSize = 22.ssp,
            modifier = Modifier
        )

        Text(
            text = stringResource(Res.string.something_went_wrong),
            color = disconnected_secondory,
            fontFamily = FontFamily(Font(Res.font.SF_Pro_Display_Regular)),
            textAlign = TextAlign.Center,
            fontSize = 12.ssp,
            modifier = Modifier.padding(top = 20.sdp).padding(horizontal = 20.sdp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(1f))

        ElevatedButton(
            modifier = Modifier
                .padding(horizontal = 20.sdp)
                .padding(bottom = 20.sdp)
                .height(35.sdp)
                .fillMaxWidth(),
            onClick = { tryAgain() },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = try_again_bg
            ),
            content = {
                Text(
                    text = stringResource(Res.string.try_again),
                    color = disconnected_primary,
                    fontFamily = FontFamily(Font(Res.font.SF_Pro_Text_Semibold)),
                    fontSize = 14.ssp,
                    modifier = Modifier
                )
            }
        )

    }
}

@Composable
fun InternetChecking() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(Res.drawable.icn_internet_checking),
            contentDescription = null,
            modifier = Modifier
                .size(120.sdp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Checking Network...",
            color = disconnected_secondory,
            fontFamily = FontFamily(Font(Res.font.SF_Pro_Text_Semibold)),
            fontSize = 22.ssp,
            modifier = Modifier.padding(top = 14.sdp)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
