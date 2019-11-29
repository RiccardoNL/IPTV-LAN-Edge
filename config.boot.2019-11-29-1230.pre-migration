firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-name WANv6_IN {
        default-action drop
        description "WAN IPv6 naar LAN"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
        rule 30 {
            action accept
            description "Allow IPv6 icmp"
            icmpv6 {
                type echo-request
            }
            protocol ipv6-icmp
        }
    }
    ipv6-name WANv6_LOCAL {
        default-action drop
        description "WAN IPv6 naar Router"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
        rule 30 {
            action accept
            description "Allow IPv6 icmp"
            protocol ipv6-icmp
        }
        rule 40 {
            action accept
            description "Allow dhcpv6"
            destination {
                port 546
            }
            protocol udp
            source {
                port 547
            }
        }
    }
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name ACCEPTALL {
        default-action accept
        description ""
        rule 1 {
            action accept
            description "ACCEPT ALL"
            log disable
            protocol all
        }
    }
    name WAN_IN {
        default-action accept
        description "WAN naar LAN"
        rule 10 {
            action accept
            description "Allow established/related"
            log disable
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    name WAN_LOCAL {
        default-action accept
        description "WAN naar Router"
        rule 10 {
            action accept
            description "Allow established/related"
            log disable
            state {
                established enable
                invalid disable
                new disable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                established disable
                invalid enable
                new disable
                related disable
            }
        }
    }
    receive-redirects disable
    send-redirects enable
    source-validation disable
    syn-cookies enable
}
interfaces {
    ethernet eth0 {
        description FTTH
        duplex auto
        firewall {
            in {
                name ACCEPTALL
            }
        }
        mtu 1512
        speed auto
        vif 4 {
            address dhcp
            description "KPN IPTV"
            dhcp-options {
                client-option "send vendor-class-identifier &quot;IPTV_RG&quot;;"
                client-option "request subnet-mask, routers, rfc3442-classless-static-routes;"
                default-route no-update
                default-route-distance 210
                name-server no-update
            }
            mtu 1500
        }
        vif 6 {
            description "KPN Internet"
            mtu 1508
            pppoe 0 {
                default-route auto
                dhcpv6-pd {
                    no-dns
                    pd 0 {
                        interface eth1 {
                            host-address ::1
                            no-dns
                            prefix-id :1
                            service slaac
                        }
                        prefix-length /48
                    }
                    rapid-commit enable
                }
                firewall {
                    in {
                        ipv6-name WANv6_IN
                        name WAN_IN
                    }
                    local {
                        ipv6-name WANv6_LOCAL
                        name WAN_LOCAL
                    }
                }
                idle-timeout 180
                ipv6 {
                    address {
                        autoconf
                    }
                    dup-addr-detect-transmits 1
                    enable {
                    }
                }
                mtu 1500
                name-server auto
                password ppp
                user-id fc-ec-da-45-73-ba@internet
            }
        }
    }
    ethernet eth1 {
        address 192.168.2.254/24
        description Thuis
        duplex auto
        ipv6 {
            dup-addr-detect-transmits 1
            router-advert {
                cur-hop-limit 64
                link-mtu 0
                managed-flag false
                max-interval 600
                name-server 2a02:a47f:e000::53
                name-server 2a02:a47f:e000::54
                other-config-flag false
                prefix ::/64 {
                    autonomous-flag true
                    on-link-flag true
                    valid-lifetime 2592000
                }
                radvd-options "RDNSS 2a02:a47f:e000::53 2a02:a47f:e000::54 {};"
                reachable-time 0
                retrans-timer 0
                send-advert true
            }
        }
        speed auto
    }
    ethernet eth2 {
        address 192.168.3.254/24
        description IPTV-NAT
        duplex auto
        ipv6 {
            dup-addr-detect-transmits 1
            router-advert {
                cur-hop-limit 64
                link-mtu 0
                managed-flag false
                max-interval 600
                name-server 2a02:a47f:e000::53
                name-server 2a02:a47f:e000::54
                other-config-flag false
                prefix ::/64 {
                    autonomous-flag true
                    on-link-flag true
                    valid-lifetime 2592000
                }
                radvd-options "RDNSS 2a02:a47f:e000::53 2a02:a47f:e000::54 {};"
                reachable-time 0
                retrans-timer 0
                send-advert true
            }
        }
        speed auto
    }
    loopback lo {
    }
}
port-forward {
    auto-firewall enable
    hairpin-nat enable
    lan-interface eth1
    rule 1 {
        description "5901 VNC"
        forward-to {
            address 192.168.2.1
            port 5901
        }
        original-port 5901
        protocol tcp_udp
    }
    rule 2 {
        description "5904 VNC"
        forward-to {
            address 192.168.2.4
            port 5904
        }
        original-port 5904
        protocol tcp_udp
    }
    rule 3 {
        description "5905 VNC"
        forward-to {
            address 192.168.2.5
            port 5905
        }
        original-port 5905
        protocol tcp_udp
    }
    rule 4 {
        description "5906 VNC"
        forward-to {
            address 192.168.2.6
            port 5906
        }
        original-port 5906
        protocol tcp_udp
    }
    rule 5 {
        description "vMix 8088"
        forward-to {
            address 192.168.2.42
            port 8088
        }
        original-port 554
        protocol tcp_udp
    }
    rule 6 {
        description "VNC 5903"
        forward-to {
            address 192.168.2.3
            port 5903
        }
        original-port 5903
        protocol tcp_udp
    }
    rule 7 {
        description "VNC 5902"
        forward-to {
            address 192.168.2.2
            port 5902
        }
        original-port 5902
        protocol tcp_udp
    }
    rule 8 {
        description "DSM FTP"
        forward-to {
            address 192.168.2.114
            port 5910
        }
        original-port 5910
        protocol tcp_udp
    }
    rule 9 {
        description "Groningen 2-3-4"
        forward-to {
            address 192.168.2.250
            port 5907
        }
        original-port 5907
        protocol tcp_udp
    }
    wan-interface pppoe0
}
protocols {
    igmp-proxy {
        interface eth0.4 {
            alt-subnet 0.0.0.0/0
            role upstream
            threshold 1
        }
        interface eth2 {
            alt-subnet 0.0.0.0/0
            role downstream
            threshold 1
        }
    }
    static {
        interface-route6 ::/0 {
            next-hop-interface pppoe0 {
            }
        }
    }
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name IPTV_NAT {
            authoritative enable
            subnet 192.168.3.0/24 {
                lease 86400
                start 192.168.3.2 {
                    stop 192.168.3.250
                }
            }
        }
        shared-network-name Thuis {
            authoritative enable
            subnet 192.168.2.0/24 {
                lease 86400
                start 192.168.2.2 {
                    stop 192.168.2.250
                }
            }
        }
        use-dnsmasq enable
    }
    dns {
        forwarding {
            cache-size 4000
            listen-on eth1
            listen-on eth2
            name-server 195.121.1.34
            name-server 195.121.1.66
            name-server 2a02:a47f:e000::53
            name-server 2a02:a47f:e000::54
            options listen-address=192.168.2.254
            options listen-address=192.168.3.254
        }
    }
    gui {
        http-port 80
        https-port 443
        older-ciphers enable
    }
    nat {
        rule 5000 {
            description IPTV
            destination {
                address 213.75.112.0/21
            }
            log disable
            outbound-interface eth0.4
            protocol all
            source {
                address 192.168.3.254/24
            }
            type masquerade
        }
        rule 5010 {
            description Internet
            log disable
            outbound-interface pppoe0
            protocol all
            type masquerade
        }
    }
    ssh {
        port 22
        protocol-version v2
    }
    telnet {
        port 23
    }
    unms {
        disable
    }
}
system {
    domain-name thuis.local
    host-name Thuis
    login {
        user ubnt {
            authentication {
                encrypted-password $6$Zg6QCEjtzr4/1XVL$PhZ1wTQJkqx0uImWZBvmUBZNo0Iid4Tauy3abc2KIYF1n9OFE2D4odMjOx3d1AAMwcm38OYDKrtn28XCE4fka/
                plaintext-password ""
            }
            level admin
        }
    }
    name-server 127.0.0.1
    ntp {
        server 0.nl.pool.ntp.org {
        }
        server 1.nl.pool.ntp.org {
        }
        server ntp0.nl.net {
        }
        server ntp1.nl.net {
        }
        server time.kpn.net {
        }
    }
    offload {
        hwnat enable
        ipsec enable
        ipv4 {
            forwarding enable
            gre enable
            pppoe enable
            vlan enable
        }
        ipv6 {
            forwarding enable
            pppoe disable
            vlan enable
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
    }
    time-zone Europe/Amsterdam
    traffic-analysis {
        dpi disable
        export disable
    }
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "config-management@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@5:nat@3:qos@1:quagga@2:system@4:ubnt-pptp@1:ubnt-unms@1:ubnt-util@1:vrrp@1:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: v1.9.8.5012183.170825.0258 */
