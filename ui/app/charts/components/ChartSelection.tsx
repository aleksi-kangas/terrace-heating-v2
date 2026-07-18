'use client'
import {SegmentedControl} from "@mantine/core";
import React from "react";

interface ChartSelectionProps {
  selection: string;
  onSelectionChange: (selection: string) => void;
  values: {
    label: string;
    value: string;
  }[]
}

const ChartSelection = ({selection, onSelectionChange, values}: ChartSelectionProps) => {
  return (
      <SegmentedControl
          fullWidth
          size="md"
          value={selection}
          onChange={onSelectionChange}
          data={values}
      />
  )
}

export default ChartSelection;
