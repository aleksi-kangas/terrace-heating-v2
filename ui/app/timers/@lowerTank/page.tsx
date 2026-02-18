import {fetchTimerSchedule} from "@/app/api/heat-pump/timers";
import {TimerType} from "@/app/types/timer";
import TimerScheduleCard from "@/app/timers/components/timer-schedule-card";

const Circuit3TimersPage = async () => {
  const lowerTankTimerSchedule = await fetchTimerSchedule(TimerType.LOWER_STORAGE_TANK);
  return (
      <TimerScheduleCard title={"Lower Storage Tank"} timerSchedule={lowerTankTimerSchedule} />
  )
}

export default Circuit3TimersPage;
