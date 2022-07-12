let app = angular.module("online.book.store", [])

app.controller("sidebarController", function ($scope, $http) {
    $scope.categories = [];

    $http.get("/home/book/categories").success(function (data) {
        this.categories = data;
    })

})