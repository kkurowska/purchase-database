var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/statistics/minimalPrice',{
            templateUrl: '/views/min_price.html',
            controller: 'statisticsController'
        })
        .when('/statistics/averagePrice',{
            templateUrl: '/views/avg_price.html',
            controller: 'statisticsController'
        })
        .otherwise(
            { redirectTo: '/'}
        );
});
