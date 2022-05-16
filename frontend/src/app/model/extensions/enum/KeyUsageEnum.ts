enum KeyUsageEnum {
  CERTIFICATE_SIGNING,
  CRL_SIGN,
  DIGITAL_SIGNATURE,
  KEY_ENCIPHERMENT,
}

const kuLabels=["Certificate Signing", "CRL Signing", "Digital Signature", "Key Encipherment",]

export {KeyUsageEnum};
export {kuLabels};
