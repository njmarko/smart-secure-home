import {ExtendedKeyUsageEnum} from "./enum/ExtendedKeyUsageEnum";
import Extension from "./Extension";

export interface ExtendedKeyUsage extends Extension {
  keyUsages: ExtendedKeyUsageEnum[]
}
