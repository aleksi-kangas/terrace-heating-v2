'use client';

import {Center} from "@mantine/core";
import {useEffect} from "react";
import ErrorNotification from "@/app/components/error-notification";

const Error = ({error}: { error: Error & { digest?: string } }) => {
  useEffect(() => {
    console.error(error);
  }, [error]);

  return (
      <Center h={850}>
        <ErrorNotification message="Server connection failure"/>
      </Center>
  )
}

export default Error;
