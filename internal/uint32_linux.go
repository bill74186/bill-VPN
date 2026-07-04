//go:build (arm || 386) && linux

package bill

func toUint(n int) uint32 {
	return uint32(n)
}
