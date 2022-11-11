export interface IImages {
  id?: string;
  guid?: string;
  cmd?: string;
  gantry?: number;
  base64ContentType?: string | null;
  base64?: string | null;
  creationDate?: string | null;
}

export const defaultValue: Readonly<IImages> = {};
