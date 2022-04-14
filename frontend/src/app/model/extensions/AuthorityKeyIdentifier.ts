import Extension from "./Extension";

export default interface AuthorityKeyIdentifier extends Extension {
  keyIdentifier: string,
}
