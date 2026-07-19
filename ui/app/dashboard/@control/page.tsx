import {Center, Divider, Loader} from "@mantine/core";

const DashboardControlPage = async () => {
  return (
      <>
        <Center h="100%">
          <Loader/>
        </Center>
        <Divider my="md"/>
        <Center h="100%">
          <Loader/>
        </Center>
      </>
  )
}

export default DashboardControlPage;
