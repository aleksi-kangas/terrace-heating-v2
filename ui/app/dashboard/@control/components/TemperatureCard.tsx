import {Group, Paper, Stack, Text, ThemeIcon,} from "@mantine/core";
import React, {ReactNode} from "react";

interface TemperatureCardProps {
  label: string;
  value: number;
  color: string;
  icon: ReactNode;
}

const TemperatureCard = ({label, value, color, icon}: TemperatureCardProps) => {
  return (
      <Paper
          p={{base: "xs", lg: "md"}}
          radius="lg"
          shadow="xs"
          withBorder
      >
        <Stack h="100%" justify="space-between">
          <Group justify="space-between" align="center">
            <Text fw={700} size="1rem" c={color}>
              {value.toFixed(1)}°C
            </Text>
            <ThemeIcon size={32} radius="xl" color={color} variant="light">
              {icon}
            </ThemeIcon>
          </Group>
          <Text fw={500} size="xs" c="dimmed">
            {label}
          </Text>
        </Stack>
      </Paper>
  );
}

export default TemperatureCard;
