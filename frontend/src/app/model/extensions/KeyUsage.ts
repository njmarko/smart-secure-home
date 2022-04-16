import KeyUsageEnum from "./enum/KeyUsageEnum";
import Extension from "./Extension";

export interface KeyUsage extends Extension {
  keyUsages: KeyUsageEnum[]
}
