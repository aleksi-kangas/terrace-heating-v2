import {DateTime} from "luxon";

export const tickLabelFormatter = (epochSeconds: number, isMobile = false) => {
  const dt = DateTime.fromSeconds(epochSeconds);
  if (isMobile) {
    return dt.toFormat("EEE HH:mm");
  }
  return dt.toLocaleString({
    weekday: "short",
    day: "numeric",
    hour: "numeric",
    minute: "2-digit",
    hourCycle: "h24",
  });
};

export const tooltipLabelFormatter = (value?: number | string) => {
  if (value == null) return '';
  const epochSeconds = typeof value === 'string' ? Number(value) : value;
  if (Number.isNaN(epochSeconds)) return '';
  return DateTime.fromSeconds(epochSeconds).toLocaleString({
    weekday: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    hourCycle: 'h24',
  });
};
