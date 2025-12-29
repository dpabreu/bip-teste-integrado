angular.module('App').controller('Controller', ['$http','$scope',
	function($http, $scope) {

        $scope = this;

        if($scope.beneficio == undefined){
            $scope.beneficio = {};
        }

        $scope.Beneficios = [];
        $scope.beneficioFromSelecionado = {};
        $scope.beneficioToSelecionado = {};
        
        $http.defaults.headers.common['Authorization'] = 'Basic ' + btoa('amas:amas');
        $http.defaults.headers.common['Content-Type'] = 'application/json';

        var urlBase = "http://localhost:8080/api/v1/beneficios";

        $scope.readAll = function(){
            $http({
                method: 'POST',
                url: urlBase+"/listaBeneficios"
            }).then(function (response){
                $scope.Beneficios = response.data;
                $scope.carregaFromList();
                $scope.carregaToList();
            },function myError(response) {
                $scope.errorResponse(response);
            });            
        }

        $scope.carregaFromList = function(){
            $http({
                method: 'POST',
                url: urlBase+"/listaBeneficios"
            }).then(function (response){  
                $scope.fromList = response.data;		                
            },function myError(response) {
                $scope.errorResponse(response);
            });            
        }

        $scope.carregaToList = function(){
            $http({
                method: 'POST',
                url: urlBase+"/listaBeneficios"
            }).then(function (response){  
                $scope.toList = response.data;		                
            },function myError(response) {
                $scope.errorResponse(response);
            });            
        }

        $scope.editar = function(beneficio, modalView){
            $scope.beneficio = beneficio;
            
            if(modalView===true){
                $('#view-modal').modal('show');
            }else{
                $('#create-modal').modal('show');
            }
        }

        $scope.salvar = function(){
            if(!$scope.validaCampos()){
                return;
            }

            var path = null;

            if($scope.beneficio.id == undefined){
                path = "/criaBeneficio";
            }else{
                path = "/atualizaBeneficio"
            }

            $http({
                method: 'POST',
                url: urlBase + path,
                data: $scope.beneficio
            }).then(function (response){

                if(response.data.codigo == 1){
                    alert("Sucesso: " + response.data.msg);
                    $('#create-modal').modal('hide');
                    $scope.beneficio = {};
                    $scope.readAll();
                } else {
                    alert("Erro: " + response.data.msg);
                }
                
            },function myError(response) {
                $scope.errorResponse(response);
            });
        }

        $scope.mostrarExcluir = function(beneficio){
            $scope.beneficio = beneficio;
            $('#delete-modal').modal('show');
        }

        $scope.fecharExcluir = function(){
            $scope.beneficio = {};
            $('#delete-modal').modal('hide');
        }

        $scope.excluir = function(id){
            $http({
                method: 'POST',
                url: urlBase+"/excluirBeneficio/"+ id
            }).then(function (response){

                if(response.data.codigo == 1){
                    alert("Sucesso: " + response.data.msg);
                    $('#delete-modal').modal('hide');
                    $scope.beneficio = {};
                    $scope.readAll();
                } else {
                    alert("Erro: " + response.data.msg);
                }
            },function myError(response) {
                $scope.errorResponse(response);
            });
        }

        $scope.cancelar = function(){
            $('#create-modal').modal('hide');
            $('#view-modal').modal('hide');
            $('#transfer-modal').modal('hide');
            
            $scope.beneficio = {};
        }

        $scope.transferir = function(){

            console.log("Beneficio from selecionado: " + $scope.beneficioFromSelecionado.id);
            console.log("Beneficio to selecionado: " + $scope.beneficioToSelecionado.id);

        }

        $scope.readAll();

        $scope.validaCampos = function(){
            
            let nome = $scope.beneficio.nome;
            
            if(nome == "" || nome == undefined){
                alert("O campo NOME não pode ser nulo");
                return;
            }
			
			let valor = $scope.beneficio.valor;
			
			if(valor == "" || valor == undefined){
                alert("O campo VALOR não pode ser nulo");
                return;
			}			

            return true;
        }

        $scope.ordenar = function(keyname){
            $scope.sortKey = keyname;
            $scope.reverse = !$scope.reverse;
        };

        $scope.errorResponse = function(response){
            var status = response.data.status;
            var error = response.data.error;
            var msg = response.data.message;

            alert("Erro: " + status +" - " + error +": " + msg);            
        };
        
    }    
]);