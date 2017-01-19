// Feel free to extend this interface
// depending on your app specific config.
import {environment} from "../../../environments/environment";

export interface EnvConfig {
  API?: string;
  ENV?: string;
}

export const Config: EnvConfig = environment.bsEnv;
