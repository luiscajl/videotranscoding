import { User } from './user.model';
import { Conversion } from './conversion.model';

export interface Original {
    id: number;
    name: string;
    path: string;
    userVideo: User;
    fileSize: string;
    conversions: Array<Conversion>;
    complete: boolean;
    active: boolean;

}
