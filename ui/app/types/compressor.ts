export interface CompressorDutyCycle {
  startTime: string;
  endTime: string;
  load: number;
  activeCount: number;
  count: number;
}

export const RESOLUTIONS = [
  {label: "1 hour", value: "PT1H"},
  {label: "2 hours", value: "PT2H"},
  {label: "3 hours", value: "PT3H"},
  {label: "6 hours", value: "PT6H"},
  {label: "12 hours", value: "PT12H"},
  {label: "1 day", value: "P1D"},
] as const;


export type Resolution = (typeof RESOLUTIONS)[number]["value"];

const DEFAULT_RESOLUTION: Resolution = "PT3H";

export const parseResolution = (value?: string): Resolution =>
    RESOLUTIONS.some((r) => r.value === value)
        ? (value as Resolution)
        : DEFAULT_RESOLUTION;
