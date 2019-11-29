firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name block_igmp_lan_to_kpn {
        default-action drop
        description ""
        rule 1 {
            action drop
            description "block igmp"
            destination {
                group {
                    address-group ADDRv4_eth0.4
                }
            }
            log disable
            protocol igmp
        }
    }
    name igmp {
        default-action drop
        description ""
        rule 1 {
            action drop
            description igmp
            destination {
                group {
                    address-group ADDRv4_eth2
                }
            }
            log disable
            protocol all
            source {
                group {
                    address-group ADDRv4_eth0.4
                }
            }
        }
    }
    name igmp_reject_lan {
        default-action reject
        description ""
        rule 1 {
            action reject
            description "Reject all from VLAN 6"
            destination {
                group {
                    address-group NETv4_eth0.6
                }
            }
            log disable
            protocol all
            source {
                group {
                    address-group NETv4_eth0.4
                }
            }
        }
        rule 2 {
            action reject
            description "NO eth0.4 to eth2"
            destination {
                group {
                    address-group ADDRv4_eth2
                }
            }
            log disable
            protocol all
            source {
                group {
                    address-group ADDRv4_eth0.4
                }
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
        mtu 1512
        speed auto
        vif 4 {
            address dhcp
            description IPTV
            dhcp-options {
                client-option "send vendor-class-identifier &quot;IPTV_RG&quot;;"
                client-option "request subnet-mask, routers, rfc3442-classless-static-routes;"
                default-route no-update
                default-route-distance 210
                name-server update
            }
            firewall {
                out {
                    name igmp
                }
            }
            mtu 1500
        }
        vif 6 {
            description "eth0.6 - Internet"
            mtu 1508
            pppoe 0 {
                default-route auto
                idle-timeout 180
                mtu 1500
                name-server auto
                password kpn
                user-id kpn
            }
        }
    }
    ethernet eth1 {
        address 192.168.2.1/24
        duplex auto
        speed auto
    }
    ethernet eth2 {
        address 192.168.3.1/24
        duplex auto
        speed auto
    }
    ethernet eth3 {
        duplex auto
        speed auto
    }
    loopback lo {
    }
}
port-forward {
    auto-firewall enable
    hairpin-nat enable
    lan-interface eth2
    rule 1 {
        description "5901 VNC"
        forward-to {
            address 192.168.3.101
            port 5901
        }
        original-port 5901
        protocol tcp_udp
    }
    rule 2 {
        description "5905 VNC"
        forward-to {
            address 192.168.3.105
            port 5905
        }
        original-port 5905
        protocol tcp_udp
    }
    rule 3 {
        description "5906 VNC"
        forward-to {
            address 192.168.3.106
            port 5906
        }
        original-port 5906
        protocol tcp_udp
    }
    rule 4 {
        description "5903 VNC"
        forward-to {
            address 192.168.3.103
            port 5903
        }
        original-port 5903
        protocol tcp_udp
    }
    rule 5 {
        description "DSM FTP"
        forward-to {
            address 192.168.3.114
            port 5910
        }
        original-port 5910
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
        interface eth1 {
            alt-subnet 0.0.0.0/0
            role downstream
            threshold 1
        }
        interface eth2 {
            role disabled
            threshold 1
        }
    }
    static {
        route 213.75.112.0/21 {
            next-hop 10.39.128.1 {
            }
        }
    }
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name LAN2 {
            authoritative disable
            subnet 192.168.2.0/24 {
                default-router 192.168.2.1
                dns-server 208.67.222.222
                dns-server 1.1.1.1
                lease 86400
                start 192.168.2.2 {
                    stop 192.168.2.250
                }
            }
        }
        shared-network-name LAN3 {
            authoritative disable
            subnet 192.168.3.0/24 {
                default-router 192.168.3.1
                dns-server 208.67.222.222
                dns-server 1.1.1.1
                lease 86400
                start 192.168.3.2 {
                    stop 192.168.3.250
                }
            }
        }
        static-arp disable
        use-dnsmasq disable
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
            type masquerade
        }
        rule 5010 {
            description Internet
            log disable
            outbound-interface pppoe0
            protocol all
            source {
                address 192.168.2.1/24
            }
            type masquerade
        }
        rule 5011 {
            description Internet
            log disable
            outbound-interface pppoe0
            protocol all
            source {
                address 192.168.3.1/24
            }
            type masquerade
        }
    }
    ssh {
        port 22
        protocol-version v2
    }
}
system {
    host-name ubnt
    login {
        user ubnt {
            authentication {
                encrypted-password $1$zKNoUbAo$gomzUbYvgyUMcD436Wo66.
            }
            level admin
        }
    }
    name-server 8.8.8.8
    name-server 1.1.1.1
    ntp {
        server 0.ubnt.pool.ntp.org {
        }
        server 1.ubnt.pool.ntp.org {
        }
        server 2.ubnt.pool.ntp.org {
        }
        server 3.ubnt.pool.ntp.org {
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
    time-zone UTC
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "config-management@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@5:nat@3:qos@1:quagga@2:suspend@1:system@4:ubnt-pptp@1:ubnt-udapi-server@1:ubnt-unms@1:ubnt-util@1:vrrp@1:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: v1.10.10.5210357.190714.1229 */
