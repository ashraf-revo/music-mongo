import {Song} from "./Song";
export class PageableSong {
  content: Song[] = [];
  last: boolean = false;
  totalPages: number = 0;
  totalElements: number = 0;
  first: boolean = false;
  numberOfElements: number = 0;
  size: number = 0;
  number: number = 0;
}
