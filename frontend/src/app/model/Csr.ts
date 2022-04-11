export default interface Csr {
  country: string,
  state: string,
  locality: string,
  organization: string,
  organizationalUnit: string,
  commonName: string,
  keySize: number
}
