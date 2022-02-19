angular.module('market-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex,
                category_part: $scope.filter ? $scope.filter.category_part : null,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.ProductsPage.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    //проверка неудачи в постмане:
    //http://localhost:5555/cart/api/v1/cart/{"username":"bob","token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNjQ1MzI5NjIxLCJpYXQiOjE2NDUyOTM2MjF9.JdrOWZVC3UIgZsdDOTxzBCUMDIuHDxIdH7xi8i-Iuc0"}/add/0
    //response: "value": "Product not found, id: 0"
    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.springWebGuestCartId + '/add/' + productId)
            .then(function (response) {
                $scope.Response = response.data;
                console.log(response.data);
                alert($scope.Response.value);
            });
    }

   //здесь не получилось вшить success/errorCallback.
//      $scope.addToCart = function (productId) {
//         $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.springWebGuestCartId + '/add/' + productId)
//            .then(function successCallback(response) {
//               $scope.Response = response.data;
//               console.log(response.data);
//            }, function errorCallback(response) {
//                $scope.Response = response.data;
//                console.log(response.data);
//                alert($scope.Response.value);
//        });
//     }

     $scope.deleteProduct = function (productId) {
            $http.delete(contextPath + 'api/v1/products/' + productId)
                .then(function (response) {
                     console.log(response.data)
                     $scope.loadProducts();
                });
     }

      $scope.loadProductsDefault = function (pageIndex = 1) {
             $http.get(contextPath + 'api/v1/products')
                 .then(function (response) {
                     $scope.ProductsPage = response.data;
                     $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.ProductsPage.totalPages);
                 });
      }

    $scope.loadProducts();
});