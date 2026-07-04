# Bill VPN

[English](./README.md)

[![License](https://img.shields.io/github/license/bill74186/bill-VPN)](https://github.com/bill74186/bill-VPN/blob/main/LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2-purple?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Go](https://img.shields.io/github/go-mod/go-version/bill74186/bill-VPN?logo=go&logoColor=white&label=Go)](https://go.dev)
[![Android](https://img.shields.io/badge/Android-7.0+-green?logo=android&logoColor=white)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-24+-brightgreen?logo=android&logoColor=white)](https://developer.android.com)
[![Release](https://img.shields.io/github/v/release/bill74186/bill-VPN?logo=github&logoColor=white)](https://github.com/bill74186/bill-VPN/releases)
[![CI](https://img.shields.io/github/actions/workflow/status/bill74186/bill-VPN/android-release.yml?branch=main&logo=github-actions&logoColor=white)](https://github.com/bill74186/bill-VPN/actions)

Bill VPN 是一个基于 [enimul](https://github.com/lzpls/enimul) 核心的 Android VPN 代理应用，提供稳定快速的代理服务与智能分流能力。

## 特性

- Android 原生实现，Kotlin + gomobile
- 基于 enimul 核心的代理与分流方案
- Clash 风格的配置管理界面
- 支持订阅 URL 拉取配置并切换规则
- 独立的规则页面，可查看、编辑和新建规则
- 基于 GFWlist 的黑名单驱动分流
- 灵活的 Fake IP 实现
- 支持日间/夜间/跟随系统主题模式
- 应用内检查更新

## 下载

从 [GitHub Releases](https://github.com/bill74186/bill-VPN/releases/latest) 获取最新版本。

## 编译

```bash
git clone https://github.com/bill74186/bill-VPN.git
cd bill-VPN
```

构建 Go Mobile Core：

```bash
make android
```

构建 Android APK：

```bash
cd android
./gradlew assembleDebug
```

## 上游

- enimul (前身 lumine): <https://github.com/lzpls/enimul>

本项目使用 enimul 作为核心并进行了一部分修改。配置文件语法兼容原版。为优化移动端性能，删除原版规则部分 IP 段，并修改了分流方式。

## 说明

本项目不是上游 enimul 官方仓库，而是面向 Android 平台的实现与适配版本。

## 开源许可

[GPL-3.0](./LICENSE)
