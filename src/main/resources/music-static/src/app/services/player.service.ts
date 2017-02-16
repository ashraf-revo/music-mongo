import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {Song} from "../domain/Song";
import {SongState} from "../domain/SongState";
import {UserService} from "./user.service";
import {View} from "../domain/View";
import {AuthService} from "./auth.service";
import {SongService} from "./song.service";

@Injectable()
export class PlayerService {
  private audio: any;
  private song: Song;
  private songStateSubject: Subject<SongState> = new Subject<SongState>();


  constructor(private _songService:SongService) {
    this.audio = new Audio();
  }

  setPlayer(song: Song) {
    this._songService.view(song).subscribe();
    this.song = song;
    this.audio.src = this.song.fileUrl;
    this.audio.oncanplaythrough = () => {
      this.audio.play();
      this.songStateSubject.next(this.copyInfo());
    };
    this.audio.ontimeupdate = () => {
      this.songStateSubject.next(this.copyInfo());
    };
  }

  getSongStateSubject(): Subject<SongState> {
    return this.songStateSubject;
  }

  toggle() {
    if (this.audio.paused) {
      this.audio.play();
    } else {
      this.audio.pause();
    }
    this.songStateSubject.next(this.copyInfo());
  }

  copyInfo(): SongState {
    let songState: SongState = new SongState();
    songState.song = this.song;
    songState.currentTime = this.audio.currentTime;
    songState.duration = this.audio.duration;
    songState.isPlaying = !this.audio.paused;
    songState.muted = this.audio.muted;
    songState.ended = this.audio.ended;
    songState.volume = this.audio.volume * 10;
    return songState;
  }

  mute() {
    this.audio.muted = !this.audio.muted;
    this.songStateSubject.next(this.copyInfo());
  }

  seek(seek: number) {
    this.audio.currentTime = seek;
    this.songStateSubject.next(this.copyInfo());
  }

  volume(seek: number) {
    this.audio.volume = seek / 10;
    this.songStateSubject.next(this.copyInfo());
  }

  stop() {
    if (!this.audio.paused)      this.audio.pause();
  }
}
