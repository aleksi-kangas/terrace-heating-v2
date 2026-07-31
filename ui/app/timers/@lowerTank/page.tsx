import {fetchTimerSchedule} from "@/app/api/heat-pump/timers";
import {TimerType} from "@/app/types/timer";
import TimerScheduleCard from "@/app/timers/components/TimerScheduleCard";

const LowerTankTimersPage = async () => {
  const lowerTankTimerSchedule = await fetchTimerSchedule(TimerType.LOWER_STORAGE_TANK);
  return (
      <TimerScheduleCard title={"Lower Storage Tank"} timerSchedule={lowerTankTimerSchedule} />
  )
}

export default LowerTankTimersPage;
