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
      <Paper withBorder radius="lg" p="md" shadow="xs">
        <Group justify="space-between" align="center">
          <Stack gap={2}>
            <Text fw={500} size="xs" c="dimmed">
              {label}
            </Text>
            <Text fw={700} size="2rem" c={color}>
              {value.toFixed(1)}°C
            </Text>
          </Stack>
          <ThemeIcon
              size={48}
              radius="xl"
              color={color}
              variant="light"
          >
            {icon}
          </ThemeIcon>
        </Group>
      </Paper>
  );
}

export default TemperatureCard;
