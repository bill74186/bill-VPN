package main

import (
	"flag"
	"fmt"

	bill "github.com/moi-si/bill/internal"
)

func main() {
	fmt.Println("moi-si/bill v0.7.9")
	fmt.Println("")
	flag.Usage = func() {
		flag.PrintDefaults()
	}
	configPath := flag.String("c", "config.json", "Config file path")
	addr := flag.String("b", "", "SOCKS5 bind address (default: address from config file)")
	hAddr := flag.String("hb", "", "HTTP bind address (default: address from config file)")
	flag.Parse()

	socks5Addr, httpAddr, err := bill.LoadConfig(*configPath)
	if err != nil {
		fmt.Println("Failed to load config:", err)
		return
	}

	done := make(chan struct{})
	go bill.SOCKS5Accept(addr, socks5Addr, done)
	go bill.HTTPAccept(hAddr, httpAddr, done)
	select {}
}
