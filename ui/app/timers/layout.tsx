import {Card, SimpleGrid} from "@mantine/core";
import React from "react";

interface TimersLayoutProps {
  circuit3: React.ReactNode;
  lowerTank: React.ReactNode;
}

const TimersLayout = ({circuit3, lowerTank}: TimersLayoutProps) => {
  return (
      <SimpleGrid cols={{base: 1, md: 2}} spacing="xl" p="md">
        <Card h="100%" shadow="sm" padding="lg" radius="md" withBorder>
          {circuit3}
        </Card>
        <Card h="100%" shadow="sm" padding="lg" radius="md" withBorder>
          {lowerTank}
        </Card>
      </SimpleGrid>
  )
}

export default TimersLayout;
