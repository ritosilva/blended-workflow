app.controller('GoalModelController', function($rootScope, $scope,
		$routeParams, specRepository, goalRepository) {
	var specId = $routeParams.specId;

	specRepository.getSpecification(specId).then(function(response) {
		$rootScope.spec = response.data;
	});

	goalRepository.getGoals(specId).then(function(response) {
		$scope.goals = response.data;
	});

	$scope.subgoals = new Map();
	$scope.getSubGoals = function(goalName) {
		goalRepository.getSubGoals(specId, goalName).then(function(response) {
			$scope.subgoals.set(goalName, response.data);
		});
	};

	$scope.actconditions = new Map();
	$scope.getActivationConditions = function(goalName) {
		goalRepository.getActivationConditions(specId, goalName).then(function(response) {
			$scope.actconditions.set(goalName, response.data);
		});
	};

	$scope.succonditions = new Map();
	$scope.getSuccessConditions = function(goalName) {
		goalRepository.getEntitySuccessConditions(specId, goalName).then(function(response) {
			$scope.succonditions.set(goalName, response.data);
		});
		goalRepository.getAttributeSuccessConditions(specId, goalName).then(function(response) {
			$scope.succonditions.set(goalName, $scope.succonditions.get(goalName).concat(response.data));
		});
	};

	$scope.mulconditions = new Map();
	$scope.getMulConditions = function(goalName) {
		goalRepository.getMulConditions(specId, goalName).then(function(response) {
			$scope.mulconditions.set(goalName, response.data);
		});
	};

	$scope.ruleconditions = new Map();
	$scope.getRuleConditions = function(goalName) {
		goalRepository.getRuleConditions(specId, goalName).then(function(response) {
			$scope.ruleconditions.set(goalName, response.data);
		});
	};

})