import CertificateExtensions from "./CertificateExtenions";
import X500PrincipalData from "./X500PrincipalData";

export default interface ReadCertificateResponse {
    serialNumber: number,
    issuer: X500PrincipalData;
    subject: X500PrincipalData;
    extensions: CertificateExtensions;
    output: string;
};
