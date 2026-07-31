'use client';

import {Group, SegmentedControl} from '@mantine/core';

interface ResolutionSelectionProps {
  selection: string;
  onSelectionChange: (selection: string) => void;
  values: { label: string; value: string }[];
  disabled?: boolean;
}

const ResolutionSelection = ({
                               selection,
                               onSelectionChange,
                               values,
                               disabled,
                             }: ResolutionSelectionProps) => {
  return (
      <Group h="10%" justify="center" m="xs">
        <SegmentedControl
            value={selection}
            onChange={onSelectionChange}
            data={values}
            disabled={disabled}
        />
      </Group>
  );
};

export default ResolutionSelection;
