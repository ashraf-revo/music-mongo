import {Song} from "../domain/Song";
export class Upload {
  data: Song;
  value: number = 0;
  timeId: number = 0;
  state: string = "binding";

  width(): string {
    return this.value + "%";
  }
}
