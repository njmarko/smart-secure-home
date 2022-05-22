import Privilege from "./Privilege";

export default interface Role {
  name: string;
  privileges: Privilege[];
}
