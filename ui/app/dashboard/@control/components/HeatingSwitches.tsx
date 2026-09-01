'use client';

import {HeatingMode, HeatingState} from "@/app/types/heating";
import {useState, useTransition} from "react";
import {Divider, Group, Switch} from "@mantine/core";
import {notifications} from "@mantine/notifications";
import {putHeatingState} from "@/app/api/heat-pump/heating";

interface HeatingSwitchProps {
  initialState: HeatingState;
}

const HeatingSwitches = ({initialState}: HeatingSwitchProps) => {
  const [heatingState, setHeatingState] = useState<HeatingState>(initialState);
  const [isPending, startTransition] = useTransition();

  const handleHeatingSwitchChange = (checked: boolean) => {
    const previousHeatingState: HeatingState = heatingState;
    const newHeatingMode: HeatingMode = checked ? HeatingMode.ACTIVE : HeatingMode.INACTIVE;
    const newHeatingState: HeatingState = {...previousHeatingState, mode: newHeatingMode}
    setHeatingState(newHeatingState);
    startTransition(async () => {
      try {
        await putHeatingState(newHeatingState);
      } catch (err) {
        setHeatingState(previousHeatingState);
        notifications.show({
          color: 'red',
          title: 'Update failed',
          message:
              err instanceof Error
                  ? err.message
                  : 'Unable to update heating state',
        });
      }
    })
  }

  const handleTimersSwitchChange = (checked: boolean) => {
    const previousHeatingState: HeatingState = heatingState;
    const newHeatingState: HeatingState = {...previousHeatingState, timersInUse: checked}
    setHeatingState(newHeatingState);
    startTransition(async () => {
      try {
        await putHeatingState(newHeatingState);
      } catch (err) {
        setHeatingState(previousHeatingState);
        notifications.show({
          color: 'red',
          title: 'Update failed',
          message:
              err instanceof Error
                  ? err.message
                  : 'Unable to update heating state',
        });
      }
    })
  }

  return (
      <Group gap="md">
        <Switch
            checked={heatingState.mode === HeatingMode.ACTIVE}
            color="green"
            disabled={isPending}
            label="Heating"
            labelPosition="left"
            offLabel="OFF"
            onChange={(event) => handleHeatingSwitchChange(event.currentTarget.checked)}
            onLabel="ON"
            size="lg"
        />
        <Divider orientation="vertical"/>
        <Switch
            checked={heatingState.timersInUse}
            color="violet"
            disabled={isPending}
            label="Timers"
            labelPosition="left"
            offLabel="OFF"
            onChange={(event) => handleTimersSwitchChange(event.currentTarget.checked)}
            onLabel="ON"
            size="lg"
        />
      </Group>
  );
}

export default HeatingSwitches;
