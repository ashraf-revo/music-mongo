import {View} from "./View";
import {Like} from "./Like";
import {Song} from "./Song";
export class User {
  id: string;
  name: string;
  email: string;
  password: string;
  currentPassword: string;
  info: string;
  phone: string;
  remember: boolean = true;
  createdDate: Date = new Date();
  imageUrl: string = "/assets/images/a0.png";
  image: any;
  songs: Song[] = [];
  likes: Like[] = [];
  views: View[] = [];

}
