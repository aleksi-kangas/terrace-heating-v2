'use client';

import {HeatingState} from "@/app/types/heating";
import {useState, useTransition} from "react";
import {Switch} from "@mantine/core";
import {notifications} from "@mantine/notifications";
import {putHeatingState} from "@/app/api/heat-pump/heating";

interface HeatingSwitchProps {
  initialState: HeatingState;
}

const HeatingSwitch = ({initialState}: HeatingSwitchProps) => {
  const [heatingState, setHeatingState] = useState<HeatingState>(initialState);
  const [isPending, startTransition] = useTransition();

  const handleHeatingStateChange = (checked: boolean) => {
    const previousHeatingState = heatingState;
    const newHeatingState = checked ? HeatingState.ACTIVE : HeatingState.INACTIVE;
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
      <Switch
          checked={heatingState === HeatingState.ACTIVE}
          onChange={(event) => handleHeatingStateChange(event.currentTarget.checked)}
          disabled={isPending}
          size="lg"
          color="green"
          onLabel="ON"
          offLabel="OFF"
      />
  );
}

export default HeatingSwitch;
