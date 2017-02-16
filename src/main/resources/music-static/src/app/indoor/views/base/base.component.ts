import {Component, OnInit} from "@angular/core";
import {PlayerService} from "../../../services/player.service";
import {UploadService} from "../../../services/upload.service";
import {SongService} from "../../../services/song.service";
@Component({
  selector: 'm-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css'], providers: [PlayerService, UploadService, SongService]
})
export class BaseComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }
}
