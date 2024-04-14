import { LazyMapsAPILoaderConfigLiteral } from '@agm/core';
import { GoogleMapsConfigService } from './googlemapsconfig.service';

export class GoogleMapsInitializer {
  constructor(private googleMapsConfigService: GoogleMapsConfigService) { }

  // assigns api key from backend to const var
  async initialize(config: LazyMapsAPILoaderConfigLiteral): Promise<void> {
    try {
      const apiKey = await this.googleMapsConfigService.getGoogleMapsApiKey();
      console.log("API KEY IN INITIALIZE SERVICE ==> ", apiKey)
        config.apiKey = apiKey;
    } catch (error) {
      console.error('Error setting Google Maps API key:', error);
    }
  }

}