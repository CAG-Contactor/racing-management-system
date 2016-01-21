'use strict';

var integration = angular.module('integration', ['ngResource', 'uploadError']);

integration.factory('integrationRestService', ['$resource', 'modalErrorService', function ($resource, modalErrorService) {
  var integration = $resource('resources', null, {
    'saveClaim': {method: 'post', url: 'resources/saveClaim', isArray: false},
    'uploadFile': {method: 'post', url: 'resources/storeReceipt', isArray: false},
    'confirmCompensation': {method: 'post', url: 'resources/claimAcceptance', isArray: false},
    'applicationVersion': {method: 'get', url: 'resources/system/applicationVersion', isArray: false}
  });

  var modalErrorObjects = {

    'DEFAULT': {
      'title': 'ERROR.DEFAULT.TITLE',
      'message': 'ERROR.DEFAULT.MESSAGE'
    },
    'FRONTEND_TECHNICAL_ERROR': {
      'default': {
        'title': 'ERROR.DEFAULT.FRONTEND_TECHNICAL_ERROR.TITLE',
        'message': 'ERROR.DEFAULT.FRONTEND_TECHNICAL_ERROR.MESSAGE'
      },
      'resources/saveClaim': {
        'title': 'ERROR.SAVECLAIM.FRONTEND_TECHNICAL_ERROR.TITLE',
        'message': 'ERROR.SAVECLAIM.FRONTEND_TECHNICAL_ERROR.MESSAGE'
      },
      'resources/claimAcceptance': {
        'title': 'ERROR.CLAIM_ACCEPTANCE.FRONTEND_TECHNICAL_ERROR.TITLE',
        'message': 'ERROR.CLAIM_ACCEPTANCE.FRONTEND_TECHNICAL_ERROR.MESSAGE'
      }
    },
    'BACKEND_TECHNICAL_ERROR': {
      'default': {
        'title': 'ERROR.DEFAULT.BACKEND_TECHNICAL_ERROR.TITLE',
        'message': 'ERROR.DEFAULT.BACKEND_TECHNICAL_ERROR.MESSAGE'
      },
      'resources/saveClaim': {
        'title': 'SAVECLAIM.BACKEND_TECHNICAL_ERROR.TITLE',
        'message': 'SAVECLAIM.BACKEND_TECHNICAL_ERROR.MESSAGE'
      },
      'resources/claimAcceptance': {
        'title': 'CLAIM_ACCEPTANCE.BACKEND_TECHNICAL_ERROR.TITLE',
        'message': 'CLAIM_ACCEPTANCE.BACKEND_TECHNICAL_ERROR.MESSAGE'
      }
    },
    'BACKEND_CONNECTIVITY_ERROR': {
      'default': {
        'title': 'ERROR.DEFAULT.BACKEND_CONNECTIVITY_ERROR.TITLE',
        'message': 'ERROR.DEFAULT.BACKEND_CONNECTIVITY_ERROR.MESSAGE'
      },
      'resources/saveClaim': {
        'title': 'ERROR.SAVECLAIM.BACKEND_CONNECTIVITY_ERROR.TITLE',
        'message': 'ERROR.SAVECLAIM.BACKEND_CONNECTIVITY_ERROR.MESSAGE'
      },
      'resources/claimAcceptance': {
        'title': 'ERROR.CLAIM_ACCEPTANCE.BACKEND_CONNECTIVITY_ERROR.TITLE',
        'message': 'ERROR.CLAIM_ACCEPTANCE.BACKEND_CONNECTIVITY_ERROR.MESSAGE'
      }
    },
    'INTERNAL_TECHNICAL_ERROR': {
      'default': {
        'title': 'ERROR.DEFAULT.INTERNAL_TECHNICAL_ERROR.TITLE',
        'message': 'ERROR.DEFAULT.INTERNAL_TECHNICAL_ERROR.MESSAGE'
      },
      'resources/saveClaim': {
        'title': 'ERROR.SAVECLAIM.INTERNAL_TECHNICAL_ERROR.TITLE',
        'message': 'ERROR.SAVECLAIM.INTERNAL_TECHNICAL_ERROR.MESSAGE'
      },
      'resources/claimAcceptance': {
        'title': 'ERROR.CLAIM_ACCEPTANCE.INTERNAL_TECHNICAL_ERROR.TITLE',
        'message': 'ERROR.CLAIM_ACCEPTANCE.INTERNAL_TECHNICAL_ERROR.MESSAGE'
      }
    }
  };

  function getModalErrorObject(error) {
    if (typeof error === 'object' && error.data) {
      var errorgroup = modalErrorObjects[error.data.code];
      var errorobject;
      if (errorgroup) {
        errorobject = errorgroup[error.config.url];
      }
      return errorobject || errorgroup['default'];
    } else if (typeof error === 'object' && error.config) {
      return modalErrorObjects.DEFAULT[error.config.url] || modalErrorObjects.DEFAULT;
    } else {
      return modalErrorObjects.DEFAULT;
    }
  }

  function onError(error) {
    modalErrorService.showError(getModalErrorObject(error));
  }

  function onUploadError(error, $scope) {
    var modalErrorObject = getModalErrorObject(error);
    modalErrorObject.scope = $scope;
    modalErrorService.showErrorWithTemplate(modalErrorObject, 'errorMessages/uploadErrorModal.tpl.html', 'uploadErrorController');
  }

  var integrationRestService = {
    saveClaim: function (baggageModel, onSuccess) {
      integration.saveClaim(baggageModel, onSuccess, onError);
    },
    uploadFile: function (file, $scope, onSuccess) {
      var formData = new FormData();
      formData.append('file', file);
      integration.uploadFile(formData, onSuccess, function (error) {
        onUploadError(error, $scope);
      });
    },
    confirmCompensation: function (confirmation, onSuccess) {
      integration.confirmCompensation(confirmation, onSuccess, onError);
    },
    applicationVersion: function (onSucces) {
      integration.applicationVersion(null, onSucces, onError);
    }

  };
  return integrationRestService;
}]);