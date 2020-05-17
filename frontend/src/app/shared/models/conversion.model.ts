import { ConversionType } from './conversionType.model';
import { Original } from './original.model';

export interface Conversion {
    conversionId: number;
    name: string;
    fileSize: string;
    path: string;
    progress: string;
    finished: boolean;
    active: boolean;
    conversionType: ConversionType;
    parent: Original;
}