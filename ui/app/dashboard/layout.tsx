import React from "react";
import {Card, Grid, GridCol} from "@mantine/core";

const DashboardLayout = ({
                           children,
                           graphs,
                           control
                         }: {
  children: React.ReactNode
  graphs: React.ReactNode,
  control: React.ReactNode
}) => {
  return (
      <>
        {children}
        <Grid>
          <GridCol span={{base: 12, lg: 8}}>
            <Card h="100%" radius="md" shadow="sm" withBorder>
              {graphs}
            </Card>
          </GridCol>
          <GridCol span={{base: 12, lg: 4}}>
            <Card h="100%" radius="md" shadow="sm" withBorder>
              {control}
            </Card>
          </GridCol>
        </Grid>
      </>
  )
}

export default DashboardLayout;
