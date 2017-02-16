import {User} from "./User";
import {Like} from "./Like";
import {View} from "./View";
export class Song {
  id: string;
  title: string;
  imageUrl: string = "/assets/images/p1.jpg";
  fileUrl: string = "/assets/audio/a0.mp3";
  description: string;
  user: User;
  liked: Like;
  createdDate: Date = new Date();
  likes: Like[] = [];
  views: View[] = [];
  file: any;
  image: any;
  from: Song[] = [];
}
