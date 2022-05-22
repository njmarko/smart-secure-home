import RealEstate from "./RealEstate";
import Role from "./Role";

export default interface User {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  enabled: boolean;
  lastPasswordResetDate: Date;
  realEstates: RealEstate[];
  roles: Role[];
};
