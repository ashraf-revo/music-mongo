import {Component, OnInit, Input, EventEmitter, Output, OnDestroy} from "@angular/core";
import {PageableSong} from "../../../domain/PageableSong";
import {Subscription} from "rxjs";

@Component({
  selector: 'm-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit,OnDestroy {
  @Input()
  receiver: EventEmitter<PageableSong>;
  @Output()
  currentUpdate: EventEmitter<number> = new EventEmitter<number>();
  subscription: Subscription;
  pages: number[] = [];

  constructor() {
  }

  ngOnInit() {
    this.subscription = this.receiver.subscribe(ps => {
    })
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

  go(page: number) {
    this.currentUpdate.emit(page);
  }
}
