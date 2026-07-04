package com.bill.vpn.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bill.vpn.ui.ConfigViewModel
import com.bill.vpn.ui.theme.LocalThemePreferences
import com.bill.vpn.ui.theme.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: ConfigViewModel) {
    val config by viewModel.currentConfig.collectAsState()
    val themePreferences = LocalThemePreferences.current
    val currentThemeMode by themePreferences.themeMode.collectAsState()

    var dnsAddr by remember { mutableStateOf(config.dnsAddr) }
    var logLevel by remember { mutableStateOf(config.logLevel) }
    LaunchedEffect(config.dnsAddr, config.logLevel) {
        dnsAddr = config.dnsAddr
        logLevel = config.logLevel
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("全局设置") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val updatedConfig = config.copy(
                            dnsAddr = dnsAddr,
                            logLevel = logLevel
                        )
                        viewModel.updateConfig(updatedConfig)
                        viewModel.saveConfig()
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text("核心设置", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = dnsAddr,
                onValueChange = { dnsAddr = it },
                label = { Text("上游 DNS") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("https://... 或 1.1.1.1:53") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("日志级别", style = MaterialTheme.typography.labelLarge)
            val levels = listOf("DEBUG", "INFO", "WARN", "ERROR")
            levels.forEach { level ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(selected = (logLevel == level), onClick = { logLevel = level })
                    Text(level, modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("主题模式", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(4.dp))
            val themeOptions = listOf(
                ThemeMode.Light to "日间模式",
                ThemeMode.Dark to "夜间模式",
                ThemeMode.System to "跟随系统"
            )
            themeOptions.forEach { (mode, label) ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(
                        selected = (currentThemeMode == mode),
                        onClick = { themePreferences.setThemeMode(mode) }
                    )
                    Text(label, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}
