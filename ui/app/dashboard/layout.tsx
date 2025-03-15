import React from "react";
import {Card, Grid, GridCol} from "@mantine/core";

const DashboardLayout = ({
                           charts,
                           control
                         }: {
  charts: React.ReactNode,
  control: React.ReactNode
}) => {
  return (
      <Grid>
        <GridCol span={{base: 12, lg: 8}}>
          <Card h="100%" radius="md" shadow="sm" withBorder>
            {charts}
          </Card>
        </GridCol>
        <GridCol span={{base: 12, lg: 4}}>
          <Card h="100%" radius="md" shadow="sm" withBorder>
            {control}
          </Card>
        </GridCol>
      </Grid>
  )
}

export default DashboardLayout;
