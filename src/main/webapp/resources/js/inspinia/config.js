/**
 * INSPINIA - Responsive Admin Theme
 * Copyright 2014 Webapplayers.com
 *
 * Inspinia theme use AngularUI Router to manage routing and views
 * Each view are defined as state.
 * Initial there are written stat for all view in theme.
 *
 */
function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("main");
    $stateProvider

        .state('coa', {
            url: '/coa',
            templateUrl: '/admin/coa',
            controller: 'treeGridCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "coa",
                            files: [
                                "/resources/js/app/module/coa.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/services/account-service.js",
                                "/resources/js/app/directives/tree-grid-directive.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('coa.new', {
            url: '/new',
            templateUrl: 'admin/coa/new-account-page'
        })

        .state('coa.account', {
            url: '/account/{accountId}',
            templateUrl: 'admin/coa/account-details-page',
            controller: 'accountDetailsCtrl'
        })

        .state('coa.edit', {
            url: '/account/{accountId}/edit',
            templateUrl: 'admin/coa/new-account-page'
        })

        .state('item', {
            url: '/items',
            templateUrl: '/admin/item',
            controller: 'itemListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "item",
                            files: [
                                "/resources/js/app/module/item.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/account-browser-s.js",
                                "/resources/js/app/factories/item-factory.js",
                                "/resources/js/app/factories/unit-factory.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('item.detail', {
            url: '/{itemId}/detail',
            templateUrl: 'admin/item/item-details-page',
            controller: 'itemDetailsCtrl'
        })

        .state('item.new', {
            url: '/new',
            templateUrl: 'admin/item/new-item-page'
        })

        .state('item.edit', {
            url: '/{itemId}/edit',
            templateUrl: 'admin/item/new-item-page'
        })

        .state('supplier', {
            url: '/suppliers',
            templateUrl: '/admin/supplier',
            controller: 'supplierListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "supplier",
                            files: [
                                "/resources/js/app/module/supplier.js",
                                "/resources/js/app/factories/supplier-factory.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('supplier.detail', {
            url: '/{supplierId}/detail',
            templateUrl: 'admin/supplier/supplier-details-page',
            controller: 'supplierDetailsCtrl'
        })

        .state('supplier.new', {
            url: '/new',
            templateUrl: 'admin/supplier/new-supplier-page'
        })

        .state('supplier.edit', {
            url: '/{supplierId}/edit',
            templateUrl: 'admin/supplier/new-supplier-page'
        })

        .state('user', {
            url: '/users-and-roles',
            templateUrl: '/admin/user',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "user",
                            files: getUserDependencies("/resources/js/app/module/user.js")
                        }
                    )
                }
            }
        })

        .state('user.list', {
            url: '/users',
            templateUrl: '/admin/user/user-list-page',
            controller: 'userListCtrl'
        })

        .state('user.new', {
            url: '/user/new',
            templateUrl: 'admin/user/new-user-page'
        })

        .state('user.detail', {
            url: '/user/{userId}/detail',
            templateUrl: 'admin/user/user-details-page',
            controller: 'userDetailsCtrl'
        })

        .state('user.edit', {
            url: '/user/{userId}/edit',
            views : {
                '': {
                    templateUrl: 'admin/user/new-user-page'
                }
            }
        })

        .state('user.roles', {
            url: '/roles',
            templateUrl: '/admin/user/role-list-page',
            controller: 'roleListCtrl'
        })

        .state('user.newRole', {
            url: '/role/new',
            templateUrl: 'admin/user/new-role-page'
        })

        .state('user.roleDetail', {
            url: '/role/{roleId}/detail',
            templateUrl: 'admin/user/role-details-page',
            controller: 'roleDetailsCtrl'
        })

        .state('user.roleEdit', {
            url: '/role/{roleId}/edit',
            templateUrl: 'admin/user/new-role-page'
        })

        .state('showcase', {
            url: '/showcase',
            templateUrl: '/admin/showcase',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "showcase",
                            files: [
                                "/resources/js/app/module/showcase.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/directives/account-browser-s.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/factories/item-factory.js",
                                "/resources/js/app/factories/requisition-voucher-factory.js",
                                "/resources/js/app/factories/rv-detail-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/directives/item-browser.js",
                                "/resources/js/app/directives/requisition-voucher-browser.js",
                                "/resources/js/app/directives/rv-detail-for-canvass-browser.js",
                                "/resources/js/app/directives/voucher-sl-setter.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('notification', {
            url: '/notifications',
            templateUrl: '/common//notifications-page'
        })

        .state('profile', {
            url:  '/user/profile',
            templateUrl:  '/user/profile-page',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "profile",
                            files: getUserDependencies("/resources/js/app/module/profile.js")
                        }
                    )
                }
            }
        })

        .state('check', {
            url: '/checks',
            templateUrl: '/admin/check',
            controller: 'checkListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "check",
                            files: [
                                "/resources/js/app/module/check.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/account-browser-s.js",
                                "/resources/js/app/factories/check-factory.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('check.detail', {
            url: '/{checkId}/detail',
            templateUrl: '/admin/check/check-details-page',
            controller: 'checkDetailsCtrl'
        })

        .state('check.edit', {
            url: '/{checkId}/edit',
            templateUrl: '/admin/check/edit-check-page',
            controller: 'addEditCheckCtrl'
        })

        .state('check.new', {
            url: '/new',
            templateUrl: '/admin/check/edit-check-page',
            controller: 'addEditCheckCtrl'
        })

        .state('profile.edit', {
            url:  '/edit',
            templateUrl:  '/user/edit-profile-page'
        })

        .state('unit', {
            url: '/unit-measures',
            templateUrl: '/admin/unit-measures',
            controller: 'unitListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "unit",
                            files: [
                                "/resources/js/app/module/unit.js",
                                "/resources/js/app/factories/unit-factory.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('unit.detail', {
            url: '/{unitId}/detail',
            templateUrl: '/admin/unit-measures/unit-details-page',
            controller: 'unitDetailsCtrl'
        })

        .state('unit.edit', {
            url: '/{unitId}/edit',
            templateUrl: '/admin/unit-measures/new-unit-page'
        })

        .state('unit.new', {
            url: '/new',
            templateUrl: '/admin/unit-measures/new-unit-page'
        })

        .state('dashboard', {
            url:  '/main'
        })


        .state('apv', {
            url:  '/accounts-payable',
            templateUrl:  '/accounts-payable',
            controller: 'apvMainCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "apv",
                            files: [
                                "/resources/js/app/module/apv2.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/apv-factory.js",
                                "/resources/js/app/factories/ledger-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                // start: should be loaded together
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/journal-entry-setter-modal.js",
                                // end: should be loaded together
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/directives/voucher-sl-setter2.js",
                                "/resources/js/app/factories/workflow-factory.js",
                                "/resources/js/app/directives/journal-entries.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('apv.new', {
            url:  '/new',
            templateUrl:  '/accounts-payable/new-apv-page'
        })

        .state('apv.edit', {
            url: '/{apvId}/edit',
            templateUrl: '/accounts-payable/new-apv-page'
        })

        .state('apv.detail', {
            url: '/{apvId}/detail',
            templateUrl: '/accounts-payable/apv-details-page',
            controller: 'apvDetailsCtrl'
        })

        .state('cv', {
            url:  '/check-vouchers',
            templateUrl:  '/check-voucher',
            controller: 'cvMainCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "cv",
                            files: [
                                "/resources/js/app/module/cv2.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/cv-factory.js",
                                "/resources/js/app/factories/ledger-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                // start: should be loaded together
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/journal-entry-setter-modal.js",
                                // end: should be loaded together
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/directives/voucher-sl-setter2.js",
                                "/resources/js/app/factories/workflow-factory.js",
                                "/resources/js/app/directives/journal-entries.js",
                                "/resources/js/app/directives/assign-check2.js",
                                // start group
                                "/resources/js/app/factories/apv-factory.js",
                                "/resources/js/app/directives/apv-browser-modal.js"
                                // end group
                            ]
                        }
                    )
                }
            }
        })

        .state('cv.new', {
            url:  '/new',
            templateUrl:  '/check-voucher/new-cv-page'
        })

        .state('cv.edit', {
            url: '/{cvId}/edit',
            templateUrl: '/check-voucher/new-cv-page'
        })

        .state('cv.detail', {
            url: '/{cvId}/detail',
            templateUrl: '/check-voucher/cv-details-page',
            controller: 'cvDetailsCtrl'
        })


        .state('jv', {
            url:  '/journal-voucher',
            templateUrl:  '/journal-voucher',
            controller: 'jvMainCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "jv",
                            files: [
                                "/resources/js/app/module/jv.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/jv-factory.js",
                                "/resources/js/app/factories/ledger-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                // start: should be loaded together
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/journal-entry-setter-modal.js",
                                // end: should be loaded together
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/directives/voucher-sl-setter2.js",
                                "/resources/js/app/factories/workflow-factory.js",
                                "/resources/js/app/directives/journal-entries.js",
                                "/resources/js/app/directives/temp-ledger-entry-selector-modal.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('jv.new', {
            url:  '/new',
            templateUrl:  '/journal-voucher/new-jv-page'
        })

        .state('jv.edit', {
            url: '/{jvId}/edit',
            templateUrl:  '/journal-voucher/new-jv-page'
        })

        .state('jv.detail', {
            url: '/{jvId}/detail',
            templateUrl: '/journal-voucher/jv-details-page',
            controller: 'jvDetailsCtrl'
        })

        .state('rv', {
            url:  '/requisition-voucher',
            templateUrl:  '/requisition-voucher',
            controller: 'rvListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "rv",
                            files: [
                                "/resources/js/app/module/rv.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/item-factory.js",
                                "/resources/js/app/factories/requisition-voucher-factory.js",
                                "/resources/js/app/factories/rv-detail-factory.js",
                                "/resources/js/app/factories/unit-factory.js",
                                "/resources/js/app/directives/item-browser.js",
                                "/resources/js/app/factories/workflow-factory.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('rv.newPO', {
            url:  '/newPO',
            templateUrl:  '/requisition-voucher/new-rvPO-page'
        })

        .state('rv.newIT', {
            url:  '/newIT',
            templateUrl:  '/requisition-voucher/new-rvIT-page'
        })

        .state('rv.newRep', {
            url:  '/newRep',
            templateUrl:  '/requisition-voucher/new-rvRep-page'
        })

        .state('rv.newLab', {
            url:  '/newLab',
            templateUrl:  '/requisition-voucher/new-rvLab-page'
        })

        .state('rv.editPO', {
            url: '/{id}/editPO',
            templateUrl: '/requisition-voucher/new-rvPO-page'
        })

        .state('rv.editIT', {
            url: '/{id}/editIT',
            templateUrl: '/requisition-voucher/new-rvIT-page'
        })

        .state('rv.editRep', {
            url: '/{id}/editRep',
            templateUrl: '/requisition-voucher/new-rvRep-page'
        })

        .state('rv.editLab', {
            url: '/{id}/editLab',
            templateUrl: '/requisition-voucher/new-rvLab-page'
        })

        .state('rv.detail', {
            url: '/{id}/detail',
            templateUrl: '/requisition-voucher/rv-details-page',
            controller: 'rvDetailsCtrl'
        })

        .state('canvass', {
            url:  '/canvass-rv',
            templateUrl:  '/canvass-rv',
            controller: 'canvassListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "canvass",
                            files: [
                                "/resources/js/app/module/canvass.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/factories/canvass-factory.js",
                                "/resources/js/app/factories/requisition-voucher-factory.js",
                                "/resources/js/app/factories/rv-detail-factory.js",
                                "/resources/js/app/factories/canvass-detail-factory.js",
                                "/resources/js/app/directives/rv-detail-for-canvass-browser.js",
                                "/resources/js/app/directives/sl-entity-browser.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('canvass.new', {
            url:  '/new',
            templateUrl:  '/canvass-rv/new-canvass-page'
        })

        .state('canvass.edit', {
            url: '/{id}/edit',
            templateUrl: '/canvass-rv/new-canvass-page'
        })

        .state('canvass.detail', {
            url: '/{id}/detail',
            templateUrl: '/canvass-rv/cnvs-details-page',
            controller: 'cnvsDetailsCtrl'
        })

        .state('processVoucher', {
            url:  '/process-voucher',
            templateUrl:  '/common/process-voucher'
        })

        .state('po', {
            url:  '/purchase-order',
            templateUrl:  '/purchase-order',
            controller: 'poListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "po",
                            files: [
                                "/resources/js/app/module/po.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/factories/purchase-order-factory.js",
                                "/resources/js/app/factories/rv-detail-factory.js",
                                "/resources/js/app/factories/po-detail-factory.js",
                                "/resources/js/app/directives/rv-detail-for-po-browser.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/workflow-factory.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('po.new', {
            url:  '/new',
            templateUrl:  '/purchase-order/new-po-page'
        })

        .state('po.edit', {
            url: '/{id}/edit',
            templateUrl:  '/purchase-order/new-po-page'
        })

        .state('po.detail', {
            url: '/{id}/detail',
            templateUrl: '/purchase-order/po-details-page',
            controller: 'poDetailsCtrl'
        })

        .state('jo', {
            url:  '/job-order',
            templateUrl:  '/job-order',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "jo",
                            files: [
                                "/resources/js/app/module/jo.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('jo.new', {
            url:  '/new',
            templateUrl:  '/job-order/new-jo-page'
        })

        .state('allocationFactor', {
            url:  '/allocation-factor',
            templateUrl:  '/admin/allocation-factor',
            controller: 'apvMainCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "allocationFactor",
                            files: [
                                "/resources/js/app/module/allocation-factor.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/services/account-service.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('allocationFactor.new', {
            url:  '/new',
            templateUrl:  '/admin/allocation-factor/new-factor-page'
        })

        .state('allocationFactor.edit', {
            url:  '/{accountId}/{effectId}/edit',
            templateUrl:  '/admin/allocation-factor/new-factor-page'
        })

        .state('allocationFactor.detail', {
            url:  '/{accountId}/{effectId}/detail',
            templateUrl: '/admin/allocation-factor/factor-details-page',
            controller: 'factorDetailsCtrl'
        })

        .state('pp', {
            url:  '/pre-payment',
            templateUrl:  '/pre-payment',
            controller: 'prepaymentListCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "pp",
                            files: [
                                "/resources/js/app/module/pp.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/factories/prepayment-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/directives/account-browser-s.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('pp.new', {
            url:  '/new',
            templateUrl:  '/pre-payment/new-pp-page'
        })

        .state('pp.edit', {
            url: '/{id}/edit',
            templateUrl:  '/pre-payment/new-pp-page'
        })

        .state('ca', {
            url:  '/cash-advance',
            templateUrl:  '/cash-advance',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "ca",
                            files: [
                                "/resources/js/app/module/ca.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('ca.new', {
            url:  '/new',
            templateUrl:  '/cash-advance/new-ca-page'
        })

        .state('reportsAccounting', {
            url:  '/reports/accounting',
            templateUrl:  '/reports/accounting',
            controller: 'accountingReportsMainCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "reportAccounting",
                            files: [
                                "/resources/js/app/module/rep-accounting.js",
                                "/resources/js/app/factories/jv-factory.js",
                                "/resources/js/app/factories/cv-factory.js"
                            ]
                        }
                    )
                }
            }

        })

        .state('reportsAccounting.jvRegister', {
            url:  '/jv-register',
            templateUrl:  '/reports/accounting/jv-register',
            params: {
                title: "Journal Voucher Register",
                'exportUrl': 'jv-register'
            }
        })

        .state('reportsAccounting.cvRegister', {
            url:  '/cv-register',
            templateUrl:  '/reports/accounting/cv-register',
            params: {
                title: "Check Voucher Register",
                'exportUrl': 'cv-register'
            }
        })

        .state('reportsAccounting.trialBalance', {
            url:  '/trial-balance',
            templateUrl:  '/reports/accounting/trial-balance'
        })

        .state('pcv', {
            url:  '/petty-cash-voucher',
            templateUrl:  '/petty-cash-voucher',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "pcv",
                            files: [
                                "/resources/js/app/module/pcv.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('pcv.new', {
            url:  '/new',
            templateUrl:  '/petty-cash-voucher/new-pcv-page'
        })

        .state('cpr', {
            url:  '/continuing-property-record',
            templateUrl:  '/continuing-property-record',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "cpr",
                            files: [
                                "/resources/js/app/module/cpr.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/services/account-service.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('cpr.new', {
            url:  '/new',
            templateUrl:  '/continuing-property-record/new-cpr-page'
        })

        .state('mir', {
            url:  '/mir',
            templateUrl:  '/mir-mgt',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "mir",
                            files: [
                                "/resources/js/app/module/mir.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/mir-factory.js",
                                "/resources/js/app/factories/ledger-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                // start: should be loaded together
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/journal-entry-setter-modal.js",
                                // end: should be loaded together
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/directives/voucher-sl-setter2.js",
                                "/resources/js/app/factories/workflow-factory.js",
                                "/resources/js/app/directives/journal-entries.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('mir.mrctList',{
            url: '/mrct-list',
            templateUrl: '/mir-mgt/mrct-list'
        })

        .state('mir.new',{
            url: '/new',
            templateUrl: '/mir-mgt/add-edit'
        })

        .state('mir.detail',{
            url: '/{id}/detail',
            templateUrl: '/mir-mgt/mir-details-page',
            controller: 'mirDetailsCtrl'
        })

        .state('mir.edit',{
            url: '/{id}/edit',
            templateUrl: '/mir-mgt/add-edit',
            controller: 'addEditMirCtrl'
        })

        .state('cashflow',{
            url: '/cashflow',
            templateUrl: '/common/cashflow'
        })


        .state('salesVoucher', {
            url:  '/sales-voucher',
            templateUrl:  '/sales-voucher-mgt',
            controller: 'salesVoucherMainCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "salesVoucher",
                            files: [
                                "/resources/js/app/module/sales-voucher2.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/sales-voucher-factory.js",
                                "/resources/js/app/factories/ledger-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                // start: should be loaded together
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/journal-entry-setter-modal.js",
                                // end: should be loaded together
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/directives/voucher-sl-setter2.js",
                                "/resources/js/app/factories/workflow-factory.js",
                                "/resources/js/app/directives/journal-entries.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('salesVoucher.new', {
            url:  '/new',
            templateUrl:  '/sales-voucher-mgt/new-sales-voucher-page'
        })

        .state('salesVoucher.edit', {
            url: '/{salesVoucherId}/edit',
            templateUrl: '/sales-voucher-mgt/new-sales-voucher-page'
        })

        .state('salesVoucher.detail', {
            url: '/{salesVoucherId}/detail',
            templateUrl: '/sales-voucher-mgt/sales-voucher-details-page',
            controller: 'salesVoucherDetailsCtrl'
        })

        .state('cashReceipts',{
            url: '/cash-receipts',
            templateUrl: '/cash-receipts-mgt',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "cashReceipts",
                            files: [
                                "/resources/js/app/module/cash-receipts.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/cash-receipts-factory.js",
                                "/resources/js/app/factories/ledger-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                // start: should be loaded together
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/journal-entry-setter-modal.js",
                                // end: should be loaded together
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/directives/voucher-sl-setter2.js",
                                "/resources/js/app/factories/workflow-factory.js",
                                "/resources/js/app/directives/journal-entries.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('cashReceipts.new', {
            url:  '/new',
            templateUrl:  '/cash-receipts-mgt/new-cash-receipts-page'
        })

        .state('cashReceipts.edit', {
            url: '/{cashReceiptsId}/edit',
            templateUrl: '/cash-receipts-mgt/new-cash-receipts-page'
        })

        .state('cashReceipts.detail', {
            url: '/{cashReceiptsId}/detail',
            templateUrl: '/cash-receipts-mgt/cash-receipts-details-page',
            controller: 'cashReceiptsDetailsCtrl'
        })

        .state('bankDeposit',{
            url: '/bank-deposit',
            templateUrl: '/bank-deposit-mgt',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "bankDeposit",
                            files: [
                                "/resources/js/app/module/bank-deposit.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js",
                                "/resources/js/app/factories/bank-deposit-factory.js",
                                "/resources/js/app/factories/ledger-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                // start: should be loaded together
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/directives/journal-entry-setter-modal.js",
                                // end: should be loaded together
                                "/resources/js/app/factories/allocation-factor-factory.js",
                                "/resources/js/app/directives/voucher-sl-setter2.js",
                                "/resources/js/app/factories/workflow-factory.js",
                                "/resources/js/app/directives/journal-entries.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('bankDeposit.new', {
            url:  '/new',
            templateUrl:  '/bank-deposit-mgt/new-bank-deposit-page'
        })

        .state('bankDeposit.edit', {
            url: '/{bankDepositId}/edit',
            templateUrl: '/bank-deposit-mgt/new-bank-deposit-page'
        })

        .state('bankDeposit.detail', {
            url: '/{bankDepositId}/detail',
            templateUrl: '/bank-deposit-mgt/bank-deposit-details-page',
            controller: 'bankDepositDetailsCtrl'
        })

        .state('di', {
            url:  '/document-inquiry',
            templateUrl:  '/document-inquiry',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "di",
                            files: [
                                "/resources/js/app/module/di.js",
                                "/resources/js/app/factories/entity-factory.js",
                                "/resources/js/app/directives/sl-entity-browser.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('br', {
            url:  '/bank-reconciliation',
            templateUrl:  '/bank-reconciliation',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "br",
                            files: [
                                "/resources/js/app/module/br.js",
                                "/resources/js/app/factories/account-factory.js",
                                "/resources/js/app/factories/business-segment-factory.js",
                                "/resources/js/app/directives/account-browser-s.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('br.new', {
            url:  '/new',
            templateUrl:  '/bank-reconciliation/new-br-page'
        })

        .state('checkReleasing', {
            url: '/check-releasing',
            templateUrl: '/check-releasing',
            controller: 'mainCtrl',
            resolve: {
                store: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                        {
                            name: "checkReleasing",
                            files: [
                                "/resources/js/app/module/check-releasing.js",
                                "/resources/js/app/factories/cv-factory.js"
                            ]
                        }
                    )
                }
            }
        })

        .state('checkReleasing.release', {
            url: '/{cvId}/release',
            templateUrl: '/check-releasing/release-page'
        })

        .state('forbidden', {
            url: '/forbidden/{ref}',
            templateUrl: function(params) {
                return '/403?ref=' + params.ref;
            }
        });


}

function getUserDependencies(moduleJs) {
    return [
        moduleJs,
        "/resources/js/app/factories/user-factory.js",
        "/resources/js/app/factories/role-factory.js",
        "/resources/js/app/factories/menu-factory.js",
        "/resources/js/app/factories/page-factory.js"
    ]
}

function decorator($provide) {
    $provide.decorator('$state', function ($delegate) {

        // let's locally use 'state' name
        var state = $delegate;

        console.log(state);

        // let's extend this object with new function
        // 'baseGo', which in fact, will keep the reference
        // to the original 'go' function
        state.baseGo = state.go;

        // here comes our new 'go' decoration
        var go = function (to, params, options) {
            options = options || {};

            // only in case of missing 'reload'
            // append our explicit 'true'
            if (angular.isUndefined(options.reload)) {

                options.reload = true;
            }

            // return processing to the 'baseGo' - original
            this.baseGo(to, params, options);
        };

        // assign new 'go', right now decorating the old 'go'
        state.go = go;

        return $delegate;
    });
}

angular
    .module('inspinia')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });
