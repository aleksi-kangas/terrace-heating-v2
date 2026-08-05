'use client'
import {Button, Group, NumberInput, Stack, Switch, Table, Text} from "@mantine/core";
import {getWeekdaySchedule, TimerSchedule, TimerType, Weekday, WEEKDAY_KEYS, WeekdaySchedule} from "@/app/types/timer";
import {useState, useTransition} from "react";
import {notifications} from "@mantine/notifications";
import {putTimerSchedule} from "@/app/api/heat-pump/timers";

const WEEKDAY_MAP: Record<string, Weekday> = {
  'Monday': Weekday.MONDAY,
  'Tuesday': Weekday.TUESDAY,
  'Wednesday': Weekday.WEDNESDAY,
  'Thursday': Weekday.THURSDAY,
  'Friday': Weekday.FRIDAY,
  'Saturday': Weekday.SATURDAY,
  'Sunday': Weekday.SUNDAY,
};

const WEEKDAYS = Object.keys(WEEKDAY_MAP);

interface TimerScheduleCardProps {
  title: string;
  timerType: TimerType;
  timerSchedule: TimerSchedule
}

const TimerScheduleCard = ({title, timerType, timerSchedule}: TimerScheduleCardProps) => {
  const [isEditing, setIsEditing] = useState(false);
  const [isPending, startTransition] = useTransition();
  const [editableTimerSchedule, setEditableTimerSchedule] = useState(timerSchedule);

  const updateWeekday = (
      weekday: Weekday,
      field: keyof WeekdaySchedule,
      value: number
  ) => {
    const key = WEEKDAY_KEYS[weekday];
    setEditableTimerSchedule((existingTimerSchedule) => ({
      ...existingTimerSchedule,
      [key]: {
        ...existingTimerSchedule[key],
        [field]: value,
      },
    }));
  };

  const handleSave = () => {
    startTransition(async () => {
      try {
        await putTimerSchedule(timerType, editableTimerSchedule);
        setIsEditing(false);
        notifications.show({
          color: 'green',
          title: 'Saved',
          message: 'Timer schedule updated successfully',
        });
      } catch (err) {
        setIsEditing(false);
        notifications.show({
          color: 'red',
          title: 'Save failed',
          message:
              err instanceof Error
                  ? err.message
                  : 'Unable to update the timer schedule',
        });
      }
    });
  };

  const rows = WEEKDAYS.map((dayName) => {
    const weekdayEnum = WEEKDAY_MAP[dayName];
    const weekdaySchedule = getWeekdaySchedule(editableTimerSchedule, weekdayEnum);
    const shortName = dayName.slice(0, 3).toUpperCase();

    return (
        <Table.Tr key={dayName}>
          <Table.Td>
            <Text size="sm" fw={500} hiddenFrom="sm">{shortName}</Text>
            <Text size="sm" fw={500} visibleFrom="sm">{dayName}</Text>
          </Table.Td>
          <Table.Td>
            <NumberInput
                size="xs"
                w={60}
                value={weekdaySchedule.startHour}
                readOnly={!isEditing}
                hideControls={!isEditing}
                onChange={(v) => {
                  if (typeof v === "number") {
                    updateWeekday(weekdayEnum, "startHour", v);
                  }
                }}
            />
          </Table.Td>
          <Table.Td>
            <NumberInput
                size="xs"
                w={60}
                value={weekdaySchedule.endHour}
                readOnly={!isEditing}
                hideControls={!isEditing}
                onChange={(v) => {
                  if (typeof v === "number") {
                    updateWeekday(weekdayEnum, "endHour", v);
                  }
                }}
            />
          </Table.Td>
          <Table.Td>
            <NumberInput
                size="xs"
                w={60}
                value={weekdaySchedule.temperatureDeltaC}
                readOnly={!isEditing}
                hideControls={!isEditing}
                onChange={(v) => {
                  if (typeof v === "number") {
                    updateWeekday(weekdayEnum, "temperatureDeltaC", v);
                  }
                }}
            />
          </Table.Td>
        </Table.Tr>
    );
  });
  return (
      <>
        <Stack align="center" mb="xl">
          <Text size="xl" fw={500}>{title}</Text>
        </Stack>

        <Table verticalSpacing="sm">
          <Table.Thead>
            <Table.Tr>
              <Table.Th style={{fontSize: '10px', color: 'gray'}}>
                <Text hiddenFrom="sm" inherit>DAY</Text>
                <Text visibleFrom="sm" inherit>WEEKDAY</Text>
              </Table.Th>
              <Table.Th style={{fontSize: '10px', color: 'gray'}}>
                <Text hiddenFrom="sm" inherit>START H</Text>
                <Text visibleFrom="sm" inherit>START HOUR</Text>
              </Table.Th>
              <Table.Th style={{fontSize: '10px', color: 'gray'}}>
                <Text hiddenFrom="sm" inherit>END H</Text>
                <Text visibleFrom="sm" inherit>END HOUR</Text>
              </Table.Th>
              <Table.Th style={{ fontSize: '10px', color: 'gray' }}>Δ °C</Table.Th>
            </Table.Tr>
          </Table.Thead>
          <Table.Tbody>{rows}</Table.Tbody>
        </Table>

        <Group justify="space-between" mt="xl" pt="md" style={{ borderTop: '1px solid #eee' }}>
          <Group gap="xs">
            <Text size="sm" fw={700}>Edit</Text>
            <Switch
                checked={isEditing}
                onChange={(event) => setIsEditing(event.currentTarget.checked)}
                size="md"
            />
          </Group>

          {isEditing && (
              <Button variant="filled" color="blue" size="xs" loading={isPending} onClick={handleSave}>
                Save Changes
              </Button>
          )}
        </Group>
      </>
  )
}

export default TimerScheduleCard;
