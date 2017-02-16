import {Component, OnInit, NgZone, OnDestroy} from "@angular/core";
import {UploadService} from "../../../services/upload.service";
import {Upload} from "../../../domain/upload";
import {Song} from "../../../domain/Song";
import {Subscription} from "rxjs";

@Component({
  selector: 'm-upload',
  templateUrl: 'upload.component.html',
  styleUrls: ['upload.component.css']
})
export class UploadComponent implements OnInit,OnDestroy {

  uploading: Upload[] = [];
  song: Song = new Song();
  subscription: Subscription;

  constructor(private zone: NgZone, private _uploadService: UploadService) {

  }

  ngOnInit() {
    this.subscription = this._uploadService.getUploading().subscribe((u: Upload)=> {
      this.zone.run(() => {
        let v = this.exist(u);
        if (v != -1) {
          if (u.state=="fail"){
            this.uploading[v].state="fail"
          }
          else
          this.uploading[v] = u;

        }
        else
          this.uploading.push(u)
      });
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

  exist(upload: Upload): number {
    let v = -1;
    for (let i = 0; i < this.uploading.length; i++) {
      if (this.uploading[i].timeId == upload.timeId) {
        v = i;
        break;
      }
    }

    return v;
  }

  upload(image: any, file: any, imagev: any, filev: any) {
    if (image.files.length > 0) {
      this.song.image = image.files[0];
    }
    if (file.files.length > 0) {
      this.song.file = file.files[0];
    }
    this._uploadService.upload(this.song).subscribe();
    this.reset(image, file, imagev, filev);
  }

  reset(image: any, file: any, imagev: any, filev: any) {
    if (image.files.length > 0) {
      image.files = null;
    }
    if (file.files.length > 0) {
      file.files = null;
    }
    imagev.value = "";
    filev.value = "";
    this.song = new Song();
  }

  name(ref1: any, ref2: any) {
    if (ref1.files.length == 1) {
      ref2.value = ref1.files[0].name;
    } else {
      ref2.value = '';
    }
  }
}
