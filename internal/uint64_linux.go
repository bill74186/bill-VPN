//go:build (amd64 || arm64) && linux

package bill

func toUint(n int) uint64 {
	return uint64(n)
}
