import androidx.compose.ui.window.ComposeUIViewController
import chaintech.network.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
