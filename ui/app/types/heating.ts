export enum HeatingMode {
  INACTIVE = "INACTIVE",
  ACTIVE = "ACTIVE",
}

export interface HeatingState {
  mode: HeatingMode;
  timersInUse: boolean;
}
