import {Center, Divider, Loader} from "@mantine/core";

const LoadingGraphsPage = () => {
  return (
      <>
        <Center h={400}>
          <Loader/>
        </Center>
        <Divider my="md"/>
        <Center h={400}>
          <Loader/>
        </Center>
      </>
  )
}

export default LoadingGraphsPage;
