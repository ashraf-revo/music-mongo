import {Component, OnInit, EventEmitter} from "@angular/core";
import {Router, ActivatedRoute} from "@angular/router";
import {SongService} from "../../../services/song.service";
import {Song} from "../../../domain/Song";
import {SearchCriteria} from "../../../domain/SearchCriteria";
import {Page} from "../../../domain/Page";
import {PageableSong} from "../../../domain/PageableSong";

@Component({
  selector: 'm-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  search: string = '';
  total: number = 10;
  songs: Song[] = [];
  current: number = 0;
  sender: EventEmitter<PageableSong> = new EventEmitter<PageableSong>();


  constructor(private _router: Router, private _activatedRoute: ActivatedRoute, private _songService: SongService) {
  }

  ngOnInit() {
    this._activatedRoute.params.subscribe(v => {
      this.search = v['search'];
      this.current = 0;
      this.updateSearch(0)
    });
  }

  private  updateSearch(page: number) {
    let pageRequest: Page = new Page();
    let searchCriteria: SearchCriteria = new SearchCriteria();
    searchCriteria.search = this.search;
    pageRequest.number = page;
    searchCriteria.page = pageRequest;
    this._songService.songsSearch(searchCriteria).subscribe(ps => {
      this.songs = ps.content;
      this.sender.emit(ps)
    })
  }
}
