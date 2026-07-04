# Bill VPN

[简体中文](./README_zh.md)

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
