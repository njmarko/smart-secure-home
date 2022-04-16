import AuthorityKeyIdentifier from "./AuthorityKeyIdentifier";
import BasicConstraints from "./BasicConstraints";
import { ExtendedKeyUsage } from "./ExtendedKeyUsage";
import { KeyUsage } from "./KeyUsage";
import { SubjectAlternativeName } from "./SubjectAlternativeName";
import { SubjectKeyIdentifier } from "./SubjectKeyIdentifier";

export default interface Extensions {
  authorityKeyIdentifier : AuthorityKeyIdentifier,
  basicConstraints : BasicConstraints,
  extendedKeyUsage : ExtendedKeyUsage,
  keyUsage: KeyUsage,
  subjectKeyIdentifier : SubjectKeyIdentifier,
  subjectAlternativeName : SubjectAlternativeName,
}
