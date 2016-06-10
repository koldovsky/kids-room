
'use strict';

function AllKidsTableService($http, $q) {

    return({
        getChildren: getChildren,
        addChild: addChild,
        searchChildren: searchChildren,
        searchParents: searchParents,
        getParent: getParent,
        getLocale: getLocale
    });

    function getChildren() {

        var request = $http({
            method: "get",
            url: "api/child",
            params: {
                action: "get"
            }
        });

        return ( request.then( handleSuccess, handleError ) );
    }

    function addChild( child ) {

        var request = $http.post("api/child", child);

        return ( request.then( handleSuccess, handleError ) );

    }

    function searchChildren( field ) {

        var request = $http({
            method: "get",
            url: "api/child/search",
            params: {
                action: "get",
                field: field
            }
        });

        return ( request.then( handleSuccess, handleError ) );
    }

    function getParent( childId ) {

        var request = $http({
            method: "get",
            url: "api/child/" + childId + "/parent",
            params: {
                action: "get"
            }
        });

        return ( request.then( handleSuccess, handleError ) );
    }

    function searchParents( field ) {

        var request = $http({
            method: "get",
            url: "api/user/search",
            params: {
                action: "get",
                field: field
            }
        });

        return ( request.then( handleSuccess, handleError ) );
    }

    function getLocale( locale ) {

            var request = $http({
                method: "get",
                url: "api/localization",
                params: {
                    action: "get",
                    locale: locale
                }
            });

            return ( request.then( handleSuccess, handleError ) );
        }

    function handleError( response ) {

        if (
            ! angular.isObject( response.data ) ||
            ! response.data.message
            ) {
            return( $q.reject( "An unknown error occurred." ) );
        }

        return( $q.reject( response.data.message ) );
    }

    function handleSuccess( response ) {
        return( response.data );
    }
}
