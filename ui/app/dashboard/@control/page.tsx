import {Center, Paper, SimpleGrid, Stack, Switch, Text, ThemeIcon, Title} from "@mantine/core";
import {fetchHeatingState} from "@/app/api/heat-pump/heating";
import {HeatingState} from "@/app/types/heating";
import {fetchTemperatureSnapshot} from "@/app/api/heat-pump/temperatures";
import {IconFlame} from "@tabler/icons-react";

const DashboardControlPage = async () => {
  const heatingStatePromise = fetchHeatingState();
  const temperatureSnapshotPromise = fetchTemperatureSnapshot();
  const [heatingState, temperatureSnapshot] = await Promise.all([heatingStatePromise, temperatureSnapshotPromise])
  const heatingActive: boolean = heatingState === HeatingState.ACTIVE
  return (
      <Stack h="100%" justify="center" gap="xl">
        <Stack align="center" gap="xs">
          <ThemeIcon
              size={64}
              radius="xl"
              variant="light"
              color={heatingActive ? "orange" : "gray"}
          >
            <IconFlame size={34}/>
          </ThemeIcon>

          <Title order={2}>Heating</Title>
        </Stack>
        <Center>
          <Switch
              checked={heatingActive}
              size="xl"
              color="green"
              onLabel="ON"
              offLabel="OFF"
          />
        </Center>
        <SimpleGrid cols={2} spacing="md">
          <Paper withBorder radius="md" p="md">
            <Stack gap={2}>
              <Text size="xs" c="dimmed">
                Indoor
              </Text>
              <Text fw={700} size="xl" c="green">
                {temperatureSnapshot.indoorC.toFixed(1)}°C
              </Text>
            </Stack>
          </Paper>
          <Paper withBorder radius="md" p="md">
            <Stack gap={2}>
              <Text size="xs" c="dimmed">
                Outdoor
              </Text>
              <Text fw={700} size="xl" c="blue">
                {temperatureSnapshot.outdoorC.toFixed(1)}°C
              </Text>
            </Stack>
          </Paper>
        </SimpleGrid>
      </Stack>
  )
}

export default DashboardControlPage;
