import {DateTime} from "luxon";

export const tickLabelFormatter = (epochSeconds: number, isMobile = false) => {
  const dt = DateTime.fromSeconds(epochSeconds);
  if (isMobile) {
    return dt.toFormat("EEE HH:mm");
  }
  return toLocaleString(dt)
};

export const tooltipLabelFormatter = (value?: number | string) => {
  if (value == null) return "";
  const epochSeconds = typeof value === "string" ? Number(value) : value;
  if (Number.isNaN(epochSeconds)) return "";
  return toLocaleString(DateTime.fromSeconds(epochSeconds))
};

export const tooltipDatetimeRangeFormatter = (start?: DateTime, end?: DateTime) => {
  if (!start || !end) return "";
  if (!start.isValid || !end.isValid) return "";
  return `${toLocaleString(start)} – ${toLocaleString(end)}`;
};

const toLocaleString = (dt: DateTime) => {
  return dt.toLocaleString({
    weekday: "short",
    day: "numeric",
    hour: "numeric",
    minute: "2-digit",
    hourCycle: "h24",
  });
}
