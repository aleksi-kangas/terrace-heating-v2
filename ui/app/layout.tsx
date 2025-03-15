'use client';
import '@mantine/core/styles.css';
import '@mantine/charts/styles.css';
import {
  AppShell,
  Burger,
  ColorSchemeScript,
  Container,
  Group,
  mantineHtmlProps,
  MantineProvider,
  UnstyledButton
} from '@mantine/core';
import {useDisclosure} from '@mantine/hooks';
import React from "react";
import classes from "./layout.module.css"
import {IconTemperature} from "@tabler/icons-react";

const RootLayout = ({children}: Readonly<{ children: React.ReactNode }>) => {
  const [opened, {toggle}] = useDisclosure();

  return (
      <html lang="en" {...mantineHtmlProps}>
      <head>
        <title>Terrace Heating</title>
        <ColorSchemeScript/>
      </head>
      <body>
      <MantineProvider>
        <AppShell
            header={{height: 60}}
            navbar={{width: 300, breakpoint: 'sm', collapsed: {desktop: true, mobile: !opened}}}
            padding="md"
        >
          <AppShell.Header>
            <Group h="100%" px="md">
              <Burger opened={opened} onClick={toggle} hiddenFrom="sm" size="sm"/>
              <Group style={{flex: 1}}>
                <IconTemperature size={30}/>
                <Group ml="xl" gap={0} visibleFrom="sm">
                  <UnstyledButton className={classes.control}>Dashboard</UnstyledButton>
                  <UnstyledButton className={classes.control}>Graphs</UnstyledButton>
                  <UnstyledButton className={classes.control}>Schedules</UnstyledButton>
                </Group>
              </Group>
            </Group>
          </AppShell.Header>
          <AppShell.Navbar py="md" px={4}>
            <UnstyledButton className={classes.control}>Dashboard</UnstyledButton>
            <UnstyledButton className={classes.control}>Graphs</UnstyledButton>
            <UnstyledButton className={classes.control}>Schedules</UnstyledButton>
          </AppShell.Navbar>
          <AppShell.Main>
            <Container fluid h="95%">
              {children}
            </Container>
          </AppShell.Main>
        </AppShell>
      </MantineProvider>
      </body>
      </html>
  )
}

export default RootLayout;
