var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/statistics/minimalPrice',{
            templateUrl: '/views/min_price.html',
            controller: 'minPriceController'
        })
        .when('/statistics/averagePrice',{
            templateUrl: '/views/avg_price.html',
            controller: 'avgPriceController'
        })
        .when('/login',{
            templateUrl: '/views/login.html',
            controller: 'loginController'
        })
        .otherwise(
            { redirectTo: '/'}
        );
});
