import {Song} from "./Song";
import {User} from "./User";
export class Like {
  id: string;
  user: User;
  song: Song;
  createdDate: Date;
}
