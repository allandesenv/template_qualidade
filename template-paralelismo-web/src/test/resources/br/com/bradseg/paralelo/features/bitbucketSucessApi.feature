#Auto generated Octane revision tag
@TID19101REV0.13.0 @regressivo
Feature: Confluence Sucesso api

  Scenario: Pesquisar pagina api
    Given que acesso o Bitbucket api
    When pesquiso pelo repositorio api
    Then deve aparecer o repositorio pesquisado api