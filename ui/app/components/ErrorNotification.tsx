import {Notification} from "@mantine/core";
import {IconX} from "@tabler/icons-react";

interface ErrorNotificationProps {
  message: string;
}

const ErrorNotification = ({message}: ErrorNotificationProps) => {
  return (
      <Notification color="red"
                    icon={<IconX/>}
                    title="An error occurred"
                    withCloseButton={false}>
        {message}
      </Notification>
  )
}

export default ErrorNotification;
