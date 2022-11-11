import dayjs from 'dayjs';

export interface IImageCollection {
  id?: string;
  guid?: string;
  cmd?: string;
  base64ContentType?: string | null;
  base64?: string | null;
  gantry?: number;
  creationDate?: string | null;
}

export const defaultValue: Readonly<IImageCollection> = {};
