import {fetchTimerSchedule} from "@/app/api/heat-pump/timers";
import {TimerType} from "@/app/types/timer";
import TimerScheduleCard from "@/app/timers/components/timer-schedule-card";

const Circuit3TimersPage = async () => {
  const heatDistributionCircuit3TimerSchedule = await fetchTimerSchedule(TimerType.HEAT_DISTRIBUTION_CIRCUIT_3);
  return (
      <TimerScheduleCard title={"Heat Distribution Circuit 3"} timerSchedule={heatDistributionCircuit3TimerSchedule} />
  )
}

export default Circuit3TimersPage;
