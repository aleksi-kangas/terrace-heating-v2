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
import Navbar from './components/navbar';

const RootLayout = ({children}: Readonly<{ children: React.ReactNode }>) => {
  return (
      <html lang="en" {...mantineHtmlProps}>
      <head>
        <title>Terrace Heating</title>
        <ColorSchemeScript/>
      </head>
      <body>
      <MantineProvider>
        <AppShell header={{height: 60}} padding="md">
          <AppShellHeader>
            <Navbar/>
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
