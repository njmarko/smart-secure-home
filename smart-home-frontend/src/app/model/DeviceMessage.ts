export interface DeviceMessage {
    id: number;
    timestamp: string;
    deviceId: number;
    content: string;
    messageType: string;
    value: number;
};