export default interface CertificateExtensions {
    isCA: boolean;
    certificateSigning: boolean;
    crlSigning: boolean;
    digitalSignature: boolean;
    keyEnchiperment: boolean;
    serverAuth: boolean;
    clientAuth: boolean;
    codeSigning: boolean;
};