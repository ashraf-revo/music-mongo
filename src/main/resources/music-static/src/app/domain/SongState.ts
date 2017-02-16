import {Song} from "./Song";
export class SongState {
  song: Song;
  currentTime: number = 0;
  duration: number = 0;
  isPlaying: boolean = false;
  muted: boolean = false;
  ended: boolean = false;
  volume: number;

  currentConvert(): string {
    var minutes = Math.floor(this.currentTime / 60);
    var seconds = this.currentTime - (minutes * 60);
    return minutes + ":" + Math.floor(seconds);
  }

  durationConvert(): string {
    var minutes = Math.floor(this.duration / 60);
    var seconds = this.duration - (minutes * 60);
    return minutes + ":" + Math.floor(seconds);
  }
}
