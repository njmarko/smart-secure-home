import { UserResponse } from "./UserResponse";

export interface RealEstate {
    id: number;
    name: string;
    stakeholders : UserResponse[]
}
