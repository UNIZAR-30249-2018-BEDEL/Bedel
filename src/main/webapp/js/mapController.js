var bedelApp = angular.module('bedelApp');

bedelApp.controller('mapController', ['$scope', function mapController($scope) {
    proj4.defs("EPSG:25830","+proj=utm +zone=30 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
    ol.proj.proj4.register(proj4);

    var map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            }),
            new ol.layer.Group({
                title: 'Plantas',
                layers: [
                    new ol.layer.Tile({
                        title: 'S1',
                        type: 'base',
                        visible: false,
                        source: new ol.source.TileWMS({
                            url: 'http://localhost:8080/geoserver/wms/bedel?',
                            params: {'LAYERS': 'ada_s1,betan_s1,torres_s1'},
                            serverType: 'geoserver'
                        })
                    }),
                    new ol.layer.Tile({
                        title: 'P0',
                        type: 'base',
                        visible: true,
                        source: new ol.source.TileWMS({
                            url: 'http://localhost:8080/geoserver/wms/bedel?',
                            params: {'LAYERS': 'ada_0,betan_0,torres_0'},
                            serverType: 'geoserver'
                        })
                    }),
                    new ol.layer.Tile({
                        title: 'P1',
                        type: 'base',
                        visible: false,
                        source: new ol.source.TileWMS({
                            url: 'http://localhost:8080/geoserver/wms/bedel?',
                            params: {'LAYERS': 'ada_1,betan_1,torres_1'},
                            serverType: 'geoserver'
                        })
                    }),
                    new ol.layer.Tile({
                        title: 'P2',
                        type: 'base',
                        visible: false,
                        source: new ol.source.TileWMS({
                            url: 'http://localhost:8080/geoserver/wms/bedel?',
                            params: {'LAYERS': 'ada_2,betan_2,torres_2'},
                            serverType: 'geoserver'
                        })
                    }),
                    new ol.layer.Tile({
                        title: 'P3',
                        type: 'base',
                        visible: false,
                        source: new ol.source.TileWMS({
                            url: 'http://localhost:8080/geoserver/wms/bedel?',
                            params: {'LAYERS': 'ada_3,betan_3,torres_3'},
                            serverType: 'geoserver'
                        })
                    }),
                    new ol.layer.Tile({
                        title: 'P4',
                        type: 'base',
                        visible: false,
                        source: new ol.source.TileWMS({
                            url: 'http://localhost:8080/geoserver/wms/bedel?',
                            params: {'LAYERS': 'ada_4'},
                            serverType: 'geoserver'
                        })
                    })
                ]
            })
        ],
        view: new ol.View({
            projection: ol.proj.get('EPSG:25830'),
            center: [675937, 4616796],
            zoom: 17,
            minZoom: 17,
            maxZoom: 20,
            extent: [675593, 4616898, 676298, 4616695]
        })
    });
    var layerSwitcher = new ol.control.LayerSwitcher({
        tipLabel: 'Visualización de plantas'
    });
    map.addControl(layerSwitcher);
    /*var crs = new L.Proj.CRS("EPSG:25830","+proj=utm +zone=30 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs", {
            resolutions: [53.125201382285255, 26.562600691142627, 14.062553307075499, 6.718775468936064,
                3.7500142152201517, 1.7187565153092277, 0.9375035538050343, 0.5000018953626857,
                0.26375099980381617, 0.131875499901908]
            //Origen de servicio tileado
            //origin:[4590500.0, 651500.0]
        });
    var map = new L.Map('map', {
        crs: crs,
        //center: [4616796, 675937],
        center: [0, 0],
        zoom: 0,
        minZoom: 0,
        maxZoom: 9,
        //maxBounds: [[4616898, 675593],[4616695, 676298]]
    });
    var baseMaps = {
        P4: L.tileLayer.wms('http://localhost:8080/geoserver/wms/bedel?', {
            layers: 'ada_4'
        }),
        P3: L.tileLayer.wms('http://localhost:8080/geoserver/wms/bedel?', {
            layers: 'ada_3,betan_3,torres_3'
        }),
        P2: L.tileLayer.wms('http://localhost:8080/geoserver/wms/bedel?', {
            layers: 'ada_2,betan_2,torres_2'
        }),
        P1: L.tileLayer.wms('http://localhost:8080/geoserver/wms/bedel?', {
            layers: 'ada_1,betan_1,torres_1'
        }),
        P0: L.tileLayer.wms('http://localhost:8080/geoserver/wms/bedel?', {
            layers: 'ada_0,betan_0,torres_0'
        }),
        S1: L.tileLayer.wms('http://localhost:8080/geoserver/wms/bedel?', {
            layers: 'ada_s1,betan_s1,torres_s1'
        }),
    }

    L.control.layers(baseMaps).addTo(map);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);
    baseMaps.P0.addTo(map);*/

}]);