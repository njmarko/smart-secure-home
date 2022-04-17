import { Pageable } from "./Pageable";

export interface PaginatedResponse<T> {
    content: T[];
    empty: boolean;
    first: boolean;
    last: boolean;
    number: number;
    numberOfElements: number;
    pageable: Pageable;
    size: number;
    totalElements: number;
    totalPages: number;
}