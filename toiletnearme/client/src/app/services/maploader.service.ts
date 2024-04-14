import { Injectable } from "@angular/core";

declare var window: any;

@Injectable()
export class MapLoaderService {

    private static promise:any
    map!: google.maps.Map;

    public static load(apiKey:string) {
        if (!MapLoaderService.promise) { // load once
            MapLoaderService.promise = new Promise((resolve) => {
                window['__onGapiLoaded'] = (ev:any) => {
                    console.log('gapi loaded', ev)
                    resolve(window.gapi);
                }
                console.log('loading..')
                const node = document.createElement('script');
                node.src = 'https://maps.googleapis.com/maps/api/js?key='+apiKey+'&callback=__onGapiLoaded';
                node.type = 'text/javascript';
                document.getElementsByTagName('head')[0].appendChild(node);
            });
        }

        return MapLoaderService.promise;
    }

    initMap(apiKey:string, gmapElement:any, lat:any, lng:any) {

        return MapLoaderService.load(apiKey).then((gapi:any) => {
            this.map = new google.maps.Map(gmapElement.nativeElement, {
                center: new google.maps.LatLng(lat, lng),
                zoom: 12
            });
        });
    }
}