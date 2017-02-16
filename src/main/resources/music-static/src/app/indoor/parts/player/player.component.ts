import {Component, OnInit, OnDestroy} from "@angular/core";
import {PlayerService} from "../../../services/player.service";
import {Subscription} from "rxjs";
import {SongState} from "../../../domain/SongState";
import {Song} from "../../../domain/Song";
@Component({
  selector: 'm-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit,OnDestroy {
  songStateSubscription: Subscription;
  songState: SongState;

  constructor(private _playerService: PlayerService) {

  }

  ngOnInit() {
    this.songStateSubscription = this._playerService.getSongStateSubject().subscribe(songState=> {
      this.songState = songState;
      if (this.songState.ended)this.next()
    });
  }

  ngOnDestroy() {
    this.songStateSubscription.unsubscribe();
  }

  toggle() {
    this._playerService.toggle();
  }

  mute() {
    this._playerService.mute();
  }

  next() {
    let from: Song[] = this.songState.song.from;
    let index: number = (from.indexOf(this.songState.song) + 1 ) % from.length;
    from[index].from = from;
    this._playerService.setPlayer(from[index]);
  }

  previous() {
    let from: Song[] = this.songState.song.from;
    let index: number = (from.indexOf(this.songState.song) - 1 ) % from.length;
    if (index < 0) {
      index += from.length;
    }
    from[index].from = from;
    this._playerService.setPlayer(from[index]);
  }

  seek(n: number) {
    this._playerService.seek(n);
  }

  volume(n: number) {
    this._playerService.volume(n);
  }
}
