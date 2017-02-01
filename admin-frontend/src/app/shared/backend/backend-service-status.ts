export interface BackendServiceStatus {
  name?:string;
  alive?:boolean;
  dbUp?:boolean;
  info?:string[];
}
