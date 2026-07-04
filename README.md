# Bill VPN

[简体中文](./README_zh.md)

[![License](https://img.shields.io/github/license/bill74186/bill-VPN)](https://github.com/bill74186/bill-VPN/blob/main/LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2-purple?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Go](https://img.shields.io/github/go-mod/go-version/bill74186/bill-VPN?logo=go&logoColor=white&label=Go)](https://go.dev)
[![Android](https://img.shields.io/badge/Android-7.0+-green?logo=android&logoColor=white)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-24+-brightgreen?logo=android&logoColor=white)](https://developer.android.com)
[![Release](https://img.shields.io/github/v/release/bill74186/bill-VPN?logo=github&logoColor=white)](https://github.com/bill74186/bill-VPN/releases)
[![CI](https://img.shields.io/github/actions/workflow/status/bill74186/bill-VPN/android-release.yml?branch=main&logo=github-actions&logoColor=white)](https://github.com/bill74186/bill-VPN/actions)

Bill VPN is an Android VPN proxy application built on top of the [enimul](https://github.com/lzpls/enimul) core, providing stable and fast proxy service with intelligent traffic routing.

## Features

- Native Android implementation built with Kotlin and gomobile
- Proxying and routing powered by enimul core
- Clash-style configuration management UI
- Subscription URL import and rule switching
- Dedicated rule page for viewing, editing, and creating rules
- Blacklist-driven routing based on GFWlist for smarter traffic splitting
- Flexible Fake IP implementation
- Day/Night/System theme mode support
- In-app update checking

## Download

Get the latest release from [GitHub Releases](https://github.com/bill74186/bill-VPN/releases/latest).

## Build

```bash
git clone https://github.com/bill74186/bill-VPN.git
cd bill-VPN
```

Build Go mobile core:

```bash
make android
```

Build Android APK:

```bash
cd android
./gradlew assembleDebug
```

## Upstream

- enimul (formerly lumine): <https://github.com/lzpls/enimul>

This project uses enimul as its core and includes a number of local modifications. The configuration file syntax remains compatible with upstream. To optimize mobile performance, some original IP-range rules were removed and the routing behavior was adjusted.

## Notes

This repository is not the official upstream enimul repository. It is an Android-focused implementation and adaptation layer.

## License

[GPL-3.0](./LICENSE)
