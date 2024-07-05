package chaintech.network.app

import chaintech.network.connectivitymonitor.ConnectivityMonitor
import chaintech.network.connectivitymonitor.ConnectivityStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConnectivityViewModel {

    private val _connectivityStatus = MutableStateFlow(ConnectivityStatus.DETERMINING)
    val connectivityStatus: StateFlow<ConnectivityStatus> = _connectivityStatus.asStateFlow()

    private var viewModelJob: Job? = null

    init {
        startMonitoring()
    }

    private fun startMonitoring() {
        viewModelJob?.cancel() // Cancel any existing job to avoid multiple coroutines running
        viewModelJob = CoroutineScope(Dispatchers.Default).launch {
            ConnectivityMonitor.instance.status.collect { newStatus ->
                _connectivityStatus.value = newStatus
            }
        }
    }

    fun stopMonitoring() {
        viewModelJob?.cancel()
        ConnectivityMonitor.instance.stopMonitoring()
    }

    fun refresh() {
        ConnectivityMonitor.instance.refresh()
    }
}