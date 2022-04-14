import KeyUsageEnum from "./enums/KeyUsageEnum";
import Extension from "./Extension";

export interface KeyUsage extends Extension {
  keyUsages: KeyUsageEnum[]
}
