export interface AuthResponse {
    id: number;
    name: string;
    surname: string;
    email: string;
    accessToken: string;
    authorities: string[];
    expiresIn: number;
}