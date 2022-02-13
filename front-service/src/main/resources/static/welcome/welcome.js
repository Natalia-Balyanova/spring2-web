angular.module('market-front').controller('welcomeController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/recommendation/';

    $scope.loadMostBoughtProducts = function () {
        $http({
            url: contextPath + 'api/v1/products/most_bought',
            method: 'GET'
        }).then(function (response) {
            $scope.mostBoughtProducts = response.data;
            console.log(response.data)
        });
    }

    $scope.loadMostAddedProducts = function () {
        $http({
            url: contextPath + 'api/v1/products/most_added',
            method: 'GET'
        }).then(function (response) {
            $scope.mostAddedProductsToCart = response.data;
            console.log(response.data)
        });
    }

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.springWebGuestCartId + '/add/' + productId)
            .then(function (response) {
        });
    }

    $scope.loadMostBoughtProducts();
    $scope.loadMostAddedProducts();
});