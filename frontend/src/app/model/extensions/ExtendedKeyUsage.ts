import ExtendedKeyUsageEnum from "./enums/ExtendedKeyUsageEnum";
import Extension from "./Extension";

export interface ExtendedKeyUsage extends Extension {
  keyUsages: ExtendedKeyUsageEnum[]
}
