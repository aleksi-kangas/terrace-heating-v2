import {Suspense} from "react";
import {Divider, Text} from "@mantine/core";

const ControlPage = async () => {
  return (
      <Suspense>
        <Text>TODO1</Text>
        <Divider my="md"/>
        <Text>TODO2</Text>
      </Suspense>
  )
}

export default ControlPage;
