
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import chaintech.network.app.App
import java.awt.Dimension

fun main() = application {
    Window(
        title = "Network Monitor",
        state = rememberWindowState(width = 900.dp, height = 700.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(600, 600)
        App()
    }
}
