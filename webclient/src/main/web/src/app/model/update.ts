export interface Update{
  content: string;
  start: number;
  end: number;
  appending: boolean;
}

export function createUpdate(content: string, start: number, end: number, appending: boolean): Update {
  return {
    content: content,
    start: start,
    end: end,
    appending: appending
  }
}
