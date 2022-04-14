import CertificatePurpose from "./enum/CerificatePurpose";
import X500PrincipalData from "./X500PrincipalData";

export default interface Csr {
  id: number,
  x500Name: X500PrincipalData,
  purpose: CertificatePurpose
}
