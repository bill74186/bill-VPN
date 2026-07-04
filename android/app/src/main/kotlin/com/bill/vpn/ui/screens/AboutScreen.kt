package com.bill.vpn.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var appVersion by remember { mutableStateOf("1.0.0") }
    var buildNumber by remember { mutableStateOf("1") }
    var appIcon by remember { mutableStateOf<Bitmap?>(null) }
    var updateStatus by remember { mutableStateOf("检查更新") }
    var isChecking by remember { mutableStateOf(false) }
    var hasUpdate by remember { mutableStateOf(false) }
    var latestVersion by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            appVersion = packageInfo.versionName ?: "1.0.0"
            buildNumber = packageInfo.versionCode.toString()
            val drawable = context.packageManager.getApplicationIcon(context.packageName)
            val bitmap = android.graphics.Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            appIcon = bitmap
        } catch (_: Exception) {
        }
    }

    suspend fun checkUpdate() {
        isChecking = true
        updateStatus = "正在检查..."
        try {
            val url = URL("https://api.github.com/repos/bill74186/bill-VPN/releases/latest")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.setRequestProperty("Accept", "application/json")

            val response = conn.inputStream.bufferedReader().readText()
            val json = JSONObject(response)
            val tagName = json.getString("tag_name")
            latestVersion = tagName.removePrefix("v")

            if (compareVersions(latestVersion, appVersion) > 0) {
                hasUpdate = true
                updateStatus = "发现新版本 v$latestVersion"
            } else {
                hasUpdate = false
                updateStatus = "已是最新版本"
            }
        } catch (e: Exception) {
            updateStatus = "检查失败"
        } finally {
            isChecking = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("关于应用") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { shareApp(context) }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    appIcon?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Bill VPN",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "v$appVersion ($buildNumber)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "稳定快速的 VPN 代理服务",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoCard(
                title = "项目主页",
                subtitle = "bill-VPN | GitHub",
                onClick = { openUrl(context, "https://github.com/bill74186/bill-VPN") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                title = "关于作者",
                subtitle = "bill74186 | GitHub",
                onClick = { openUrl(context, "https://github.com/bill74186") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                title = "开源许可",
                subtitle = "GPL-3.0 | License",
                onClick = { openUrl(context, "https://github.com/bill74186/bill-VPN/blob/main/LICENSE") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                title = "检查更新",
                subtitle = updateStatus,
                onClick = {
                    if (hasUpdate) {
                        openUrl(context, "https://github.com/bill74186/bill-VPN/releases/latest")
                    } else if (!isChecking) {
                        coroutineScope.launch { checkUpdate() }
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "用心制作 By bill74186",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
fun InfoCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.labelLarge)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.ArrowForward, contentDescription = "Forward", modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

fun shareApp(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, "Bill VPN")
        putExtra(Intent.EXTRA_TEXT, "Bill VPN - 稳定快速的 VPN 代理服务\nhttps://github.com/bill74186/bill-VPN")
    }
    val chooser = Intent.createChooser(intent, "分享 Bill VPN")
    context.startActivity(chooser)
}

fun compareVersions(v1: String, v2: String): Int {
    val parts1 = v1.split(".")
    val parts2 = v2.split(".")
    val maxLength = maxOf(parts1.size, parts2.size)
    for (i in 0 until maxLength) {
        val p1 = if (i < parts1.size) parts1[i].toIntOrNull() ?: 0 else 0
        val p2 = if (i < parts2.size) parts2[i].toIntOrNull() ?: 0 else 0
        if (p1 > p2) return 1
        if (p1 < p2) return -1
    }
    return 0
}
