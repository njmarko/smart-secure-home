export default interface CheckValidityResponse {
    serialNumber: number;
    valid: boolean;
    expired: boolean;
    started: boolean;
};