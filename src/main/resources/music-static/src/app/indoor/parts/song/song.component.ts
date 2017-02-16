import {Component, OnInit, Input} from "@angular/core";
import {Song} from "../../../domain/Song";
import {PlayerService} from "../../../services/player.service";
import {SongService} from "../../../services/song.service";

@Component({
  selector: 'm-song',
  templateUrl: './song.component.html',
  styleUrls: ['./song.component.css']
})
export class SongComponent implements OnInit {

  @Input()
  song: Song;
  @Input()
  from: Song[];

  constructor(private _playerService: PlayerService, private _songService: SongService) {

  }

  ngOnInit() {
  }

  play() {
    this.song.from = this.from;
    this._playerService.setPlayer(this.song)
  }

  like() {
    if (this.song.liked != null) {
      this._songService.unlike(this.song.liked).subscribe((res) => {
        this.song.liked = null;
      }, error => {
      }, () => {
      })
    }
    else {
      this._songService.like(this.song).subscribe((like) => {
        this.song.liked = like;
      }, error => {
      }, () => {
      })
    }
  }
}
