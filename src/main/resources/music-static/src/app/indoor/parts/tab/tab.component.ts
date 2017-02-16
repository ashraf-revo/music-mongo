import {Component, OnInit, Input} from "@angular/core";
import {Song} from "../../../domain/Song";
import {PlayerService} from "../../../services/player.service";

@Component({
  selector: 'm-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css']
})
export class TabComponent implements OnInit {
  @Input()
  songs: Song[] = [];

  constructor(private _playerService: PlayerService) {
  }

  ngOnInit() {
  }

  play(song: Song) {
    song.from = this.songs;
    this._playerService.setPlayer(song)
  }
}
