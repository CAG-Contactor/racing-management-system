interface BackendEvent {
  readonly eventType: string
  readonly data: any;
  readonly uuid: string;
}