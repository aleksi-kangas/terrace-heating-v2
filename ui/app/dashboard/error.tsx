'use client';

import {Alert, Text} from "@mantine/core";
import {IconWifiOff} from "@tabler/icons-react";
import {useEffect} from "react";

const Error = ({error}: { error: Error & { digest?: string } }) => {
  useEffect(() => {
    console.error(error);
  }, [error]);

  return (
      <Alert variant="filled" color="red" icon={<IconWifiOff/>} title="Error">
        <Text>Server connection failure</Text>
      </Alert>
  )
}

export default Error;
