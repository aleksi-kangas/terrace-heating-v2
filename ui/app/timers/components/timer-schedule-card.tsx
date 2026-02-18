'use client'
import {Button, Group, NumberInput, Stack, Switch, Table, Text} from "@mantine/core";
import {getWeekdaySchedule, TimerSchedule, Weekday} from "@/app/types/timer";
import {useState} from "react";

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
  timerSchedule: TimerSchedule
}

const TimerScheduleCard = ({title, timerSchedule}: TimerScheduleCardProps) => {
  const [isEditing, setIsEditing] = useState(false);

  const handleSave = () => {
    // TODO
    setIsEditing(false);
  };

  const rows = WEEKDAYS.map((dayName) => {
    const weekdayEnum = WEEKDAY_MAP[dayName];
    const schedule = getWeekdaySchedule(timerSchedule, weekdayEnum);

    return (
        <Table.Tr key={dayName}>
          <Table.Td>
            <Text size="sm" fw={500}>{dayName}</Text>
          </Table.Td>
          <Table.Td>
            <NumberInput
                size="xs"
                w={70}
                value={schedule.startHour}
                disabled={!isEditing}
                hideControls={!isEditing}
            />
          </Table.Td>
          <Table.Td>
            <NumberInput
                size="xs"
                w={70}
                value={schedule.endHour}
                disabled={!isEditing}
                hideControls={!isEditing}
            />
          </Table.Td>
          <Table.Td>
            <NumberInput
                size="xs"
                w={70}
                value={schedule.temperatureDeltaC}
                disabled={!isEditing}
                hideControls={!isEditing}
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
              <Table.Th style={{ fontSize: '10px', color: 'gray' }}>WEEKDAY</Table.Th>
              <Table.Th style={{ fontSize: '10px', color: 'gray' }}>START HOUR</Table.Th>
              <Table.Th style={{ fontSize: '10px', color: 'gray' }}>END HOUR</Table.Th>
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
              <Button variant="filled" color="blue" size="xs" onClick={handleSave}>
                Save Changes
              </Button>
          )}
        </Group>
      </>
  )
}

export default TimerScheduleCard;
