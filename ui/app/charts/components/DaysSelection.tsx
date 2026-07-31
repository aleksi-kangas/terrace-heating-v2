'use client'
import {Group, SegmentedControl} from "@mantine/core";

interface DaysSelectionProps {
  selection: string;
  onSelectionChange: (selection: string) => void;
  values: {
    label: string;
    value: string;
  }[]
  disabled?: boolean;
}

const DaysSelection = ({selection, onSelectionChange, values, disabled}: DaysSelectionProps) => {
  return (
      <Group justify="center" mt="xl">
        <SegmentedControl
            size="xs"
            radius="xl"
            value={selection}
            onChange={onSelectionChange}
            data={values}
            disabled={disabled}
        />
      </Group>
  )
}

export default DaysSelection;
