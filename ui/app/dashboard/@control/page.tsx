import {SimpleGrid, Stack, ThemeIcon, Title} from "@mantine/core";
import {fetchHeatingState} from "@/app/api/heat-pump/heating";
import {HeatingState} from "@/app/types/heating";
import {fetchTemperatureSnapshot} from "@/app/api/heat-pump/temperatures";
import {
  IconArrowDown,
  IconArrowUp,
  IconFlame,
  IconHexagonNumber1,
  IconHexagonNumber2,
  IconHexagonNumber3,
  IconHome,
  IconRectangleRoundedBottom,
  IconRectangleRoundedTop,
  IconSun
} from "@tabler/icons-react";
import TemperatureCard from "@/app/dashboard/@control/components/TemperatureCard";
import HeatingSwitch from "@/app/dashboard/@control/components/HeatingSwitch";

const DashboardControlPage = async () => {
  const heatingStatePromise = fetchHeatingState();
  const temperatureSnapshotPromise = fetchTemperatureSnapshot();
  const [heatingState, temperatureSnapshot] = await Promise.all([heatingStatePromise, temperatureSnapshotPromise])

  return (
      <Stack align="center" gap="xl" h="100%" justify="center">
        <Stack align="center" gap="md" justify="center">
          <ThemeIcon
              color="orange"
              radius="xl"
              size={48}
              variant="light"
          >
            <IconFlame size={24}/>
          </ThemeIcon>
          <Title order={1}>Heating</Title>
          <HeatingSwitch initialState={heatingState}/>
        </Stack>
        <SimpleGrid cols={{base: 2, sm: 3}} spacing="md">
          <TemperatureCard
              label="Indoor"
              value={temperatureSnapshot.indoorC}
              color="green"
              icon={<IconHome/>}
          />
          <TemperatureCard
              label="Outdoor"
              value={temperatureSnapshot.outdoorC}
              color="blue"
              icon={<IconSun/>}
          />
          <TemperatureCard
              label="Ground Circuit In"
              value={temperatureSnapshot.groundCircuitInC}
              color="orange"
              icon={<IconArrowDown/>}
          />
          <TemperatureCard
              label="Ground Circuit Out"
              value={temperatureSnapshot.groundCircuitOutC}
              color="cyan"
              icon={<IconArrowUp/>}
          />
          <TemperatureCard
              label="Lower Tank"
              value={temperatureSnapshot.lowerStorageTankC}
              color="teal"
              icon={<IconRectangleRoundedBottom/>}
          />
          <TemperatureCard
              label="Upper Tank"
              value={temperatureSnapshot.upperStorageTankC}
              color="indigo"
              icon={<IconRectangleRoundedTop/>}
          />
          <TemperatureCard
              label="Heat Distribution Circuit 1"
              value={temperatureSnapshot.heatDistributionCircuit1C}
              color="lime"
              icon={<IconHexagonNumber1/>}
          />
          <TemperatureCard
              label="Heat Distribution Circuit 2"
              value={temperatureSnapshot.heatDistributionCircuit2C}
              color="yellow"
              icon={<IconHexagonNumber2/>}
          />
          <TemperatureCard
              label="Heat Distribution Circuit 3"
              value={temperatureSnapshot.heatDistributionCircuit3C}
              color="grape"
              icon={<IconHexagonNumber3/>}
          />
        </SimpleGrid>
      </Stack>
  )
}

export default DashboardControlPage;
