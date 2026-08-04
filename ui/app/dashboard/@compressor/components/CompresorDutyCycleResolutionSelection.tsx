'use client';

import {Group, SegmentedControl} from '@mantine/core';
import {useMediaQuery} from "@mantine/hooks";
import {useMemo} from "react";

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
  const isMobile = useMediaQuery('(max-width: 48em)');

  const data = useMemo(() =>
          isMobile
              ? values.map((item) => ({
                ...item,
                label: shortenLabel(item.label),
              }))
              : values,
      [isMobile, values]
  );

  return (
      <Group h="10%" justify="center" m="xs">
        <SegmentedControl
            data={data}
            disabled={disabled}
            onChange={onSelectionChange}
            size={isMobile ? 'xs' : 'sm'}
            value={selection}
        />
      </Group>
  );
};

export default ResolutionSelection;

const shortenLabel = (label: string): string =>
    label
        .replace(/\bhours?\b/gi, 'h')
        .replace(/\bminutes?\b/gi, 'm')
        .replace(/\bdays?\b/gi, 'd')
        .replace(/\bseconds?\b/gi, 's')
        .replace(/\s+/g, ' ')
        .trim();
