import Extension from "../extensions/Extension";
import Extensions from "../extensions/Extensions";
import Csr from "./Csr";
import SignatureAlg from "./enum/SignatureAlg";

export default interface CsrSignData {
  csr: Csr,
  validityStart: Date,
  validityEnd: Date,
  signatureAlg: SignatureAlg | number,
  extensions: Extensions,
}
