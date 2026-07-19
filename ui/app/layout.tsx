import type {Metadata} from 'next';
import '@mantine/core/styles.css';
import '@mantine/charts/styles.css';
import '@mantine/notifications/styles.css';
import {
  AppShell,
  AppShellHeader,
  AppShellMain,
  ColorSchemeScript,
  Container,
  mantineHtmlProps,
  MantineProvider
} from '@mantine/core';
import React from "react";
import NavBar from './components/NavBar';

export const metadata: Metadata = {
  title: "Terrace Heating"
}

const RootLayout = ({children}: Readonly<{ children: React.ReactNode }>) => {
  return (
      <html lang="en" {...mantineHtmlProps}>
      <head>
        <ColorSchemeScript/>
      </head>
      <body>
      <MantineProvider>
        <AppShell header={{height: 60}} padding={{base: "xs", sm: "sm"}}>
          <AppShellHeader>
            <NavBar/>
          </AppShellHeader>
          <AppShellMain>
            <Container fluid h="calc(100vh - 60px - var(--mantine-spacing-md) * 2)">
              {children}
            </Container>
          </AppShellMain>
        </AppShell>
      </MantineProvider>
      </body>
      </html>
  )
}

export default RootLayout;
