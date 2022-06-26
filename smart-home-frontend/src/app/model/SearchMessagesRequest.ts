export interface SearchMessagesRequest {
    realEstateId: number;
    regex: string;
    messageType: string;
    from: string;
    to: string;
};