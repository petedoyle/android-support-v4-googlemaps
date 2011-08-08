package com.example.android.apis;

public final class Locations {
    public static final class Entry {
        public Entry(double lat, double lng, int zoomLevel) {
            this.lat = lat;
            this.lng = lng;
            this.zoomLevel = zoomLevel;
        }
        
        public final double lat;
        public final double lng;
        
        /** Google Maps zoom level to display */
        public final int zoomLevel;
    }
    
    /**
     * Our data, part 1.
     */
    public static final String[] NAMES = 
    {
            "Seattle",   
            "Cabo San Lucas",
            "Bora Bora",
            "Eiffel Tower"
    };
    
    /**
     * Our data, part 2.
     */
    public static final Entry[] ENTRIES = 
    {
        new Entry(47.594991, -122.331182, 18),
        new Entry(22.878111, -109.896972, 15),
        new Entry(-16.499858, -151.737022, 12),
        new Entry(48.858334, 2.294254, 18)
    };
}
