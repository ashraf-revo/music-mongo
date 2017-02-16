import {Component, OnInit} from "@angular/core";
import {PlayerService} from "../../../services/player.service";
import {Song} from "../../../domain/Song";
import {SongService} from "../../../services/song.service";

@Component({
  selector: 'm-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  songs: Song[] = [];

  constructor(private _playerService: PlayerService, private _songService: SongService) {
  }

  ngOnInit() {
    this._songService.songs().subscribe(songs=>this.songs = songs);
  }

  play(song: Song) {
    song.from = this.songs;
    this._playerService.setPlayer(song)
  }
}
