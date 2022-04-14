import Extension from "../extensions/Extension";
import SignatureAlg from "./enum/SignatureAlg";
import X500PrincipalData from "./X500PrincipalData";

export default interface CertificateData {
  subjectName: X500PrincipalData,
  issuerName: X500PrincipalData | null,
  serialNumber: BigInteger | null,
  validityStart: Date,
  validityEnd: Date,
  signatureAlg: SignatureAlg,
  extensions: Extension[],
}
