import React from "react";
import {Card, Grid, GridCol, Stack} from "@mantine/core";

interface DashboardLayoutProps {
  charts: React.ReactNode;
  compressor: React.ReactNode;
  control: React.ReactNode;
}

const DashboardLayout = ({
                           charts,
                           compressor,
                           control,
                         }: DashboardLayoutProps) => {
  return (
      <Grid>
        <GridCol span={{base: 12, lg: 8}}>
          <Stack h="80vh">
            <Card withBorder shadow="sm" radius="md" p="xs" style={{flex: 1}}>
              {charts}
            </Card>
            <Card withBorder shadow="sm" radius="md" p="xs" style={{flex: 1}}>
              {compressor}
            </Card>
          </Stack>
        </GridCol>
        <GridCol span={{base: 12, lg: 4}}>
          <Stack h="80vh">
            <Card withBorder shadow="sm" radius="md" style={{flex: 1}}>
              {control}
            </Card>
            <Card withBorder shadow="sm" radius="md" style={{flex: 1}}>
              {control}
            </Card>
          </Stack>
        </GridCol>
      </Grid>
  );
};

export default DashboardLayout;
