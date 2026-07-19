'use client'
import {Button, Group} from "@mantine/core";
import {IconTemperature} from "@tabler/icons-react";
import Link from "next/link";
import React from "react";
import {usePathname} from "next/navigation";

interface NavbarItemProps {
    active: boolean;
    href: string;
    label: string;
}

const NavBarItem = ({active, href, label}: NavbarItemProps) => {
    return (
        <Link href={href}>
            <Button fw={500} size="compact-md" variant={active ? "default" : "subtle"}>
                {label}
            </Button>
        </Link>
    )
}

const NavBar = () => {
    const pathname = usePathname();

    const navbarItems = [
        {
            href: '/dashboard',
            label: "Dashboard",
        },
        {
            href: '/charts',
            label: "Charts",
        },
        {
            href: '/timers',
            label: "Timers",
        }
    ];

    return (
        <Group h="100%" px="md" style={{flex: 1}}>
            <IconTemperature size={30}/>
            <Group gap={10}>
                {navbarItems.map((item) => (
                    <NavBarItem key={item.href}
                                active={pathname.startsWith(item.href)}
                                href={item.href}
                                label={item.label}/>
                ))}
            </Group>
        </Group>
    )
}

export default NavBar;
