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
            zoom: 18,
            minZoom: 18,
            maxZoom: 20,
            extent: [675880, 4616998, 676000, 4616750]
        })
    });
    map.addControl(new ol.control.LayerSwitcher({
        tipLabel: 'Visualización de plantas'
    }));

    // Popup showing the position the user clicked
    var popup = new ol.Overlay({
        element: document.getElementById('popup'),
        autoPan: true
    });
    map.addOverlay(popup);

    document.getElementById('popup-closer').onclick = function() {
        popup.setPosition(undefined);
        document.getElementById('popup-closer').blur();

        return false;
    }

    var espacio = {};

    map.on('singleclick', function(evt) {
        var coordinate = evt.coordinate;
        espacio.nombre = "espacio";

        document.getElementById('popup-content').innerHTML = '<p><b>Nombre: </b>' + espacio.nombre + '</p>' +
            '<button ng-click="crearIncidencia()" class="btn btn-success">Crear incidencia</button>';
        popup.setPosition(coordinate);

        console.log(coordinate);
    });

    $scope.crearIncidencia = function() {
        console.log("crear incidencia");
    }

}]);